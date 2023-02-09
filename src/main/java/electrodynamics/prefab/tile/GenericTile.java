package electrodynamics.prefab.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.IWrenchItem;
import electrodynamics.api.References;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyManager;
import electrodynamics.prefab.tile.components.Component;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentName;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.prefab.utilities.ItemUtils;
import electrodynamics.prefab.utilities.Scheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.TriPredicate;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class GenericTile extends BlockEntity implements Nameable, IPropertyHolderTile {
	private Component[] components = new Component[ComponentType.values().length];
	private ComponentProcessor[] processors = new ComponentProcessor[5];
	private PropertyManager propertyManager = new PropertyManager();

	// use this for manually setting the change flag
	public boolean isChanged = false;

	public <T> Property<T> property(Property<T> prop) {
		for (Property<?> existing : propertyManager.getProperties()) {
			if (existing.getName().equals(prop.getName())) {
				throw new RuntimeException(prop.getName() + " is already being used by another property!");
			}
		}

		return propertyManager.addProperty(prop);
	}

	public boolean hasComponent(ComponentType type) {
		return components[type.ordinal()] != null;
	}

	@Override
	public PropertyManager getPropertyManager() {
		return propertyManager;
	}

	public <T extends Component> T getComponent(ComponentType type) {
		return !hasComponent(type) ? null : (T) components[type.ordinal()];
	}

	public ComponentProcessor getProcessor(int id) {
		return processors[id];
	}

	public ComponentProcessor[] getProcessors() {
		return processors;
	}

	public GenericTile addProcessor(ComponentProcessor processor) {
		for (int i = 0; i < processors.length; i++) {
			if (processors[i] == null) {
				processors[i] = processor;
				processor.holder(this);
				break;
			}
		}
		return this;
	}

	public GenericTile addComponent(Component component) {
		component.holder(this);
		if (hasComponent(component.getType())) {
			throw new ExceptionInInitializerError("Component of type: " + component.getType().name() + " already registered!");
		}
		components[component.getType().ordinal()] = component;
		return this;
	}

	@Deprecated(forRemoval = false, since = "Try not using this method.")
	public GenericTile forceComponent(Component component) {
		component.holder(this);
		components[component.getType().ordinal()] = component;
		return this;
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		for (Property<?> prop : propertyManager.getProperties()) {
			if (prop.shouldSave()) {
				prop.getType().load(prop, compound);
			}
		}
		for (Component component : components) {
			if (component != null) {
				component.holder(this);
				component.loadFromNBT(compound);
			}
		}
		for (ComponentProcessor pr : processors) {
			if (pr != null) {
				pr.holder(this);
				pr.loadFromNBT(compound);
			}
		}
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		for (Property<?> prop : propertyManager.getProperties()) {
			if (prop.shouldSave()) {
				prop.getType().save(prop, compound);
			}
		}
		for (Component component : components) {
			if (component != null) {
				component.holder(this);
				component.saveToNBT(compound);
			}
		}
		for (ComponentProcessor pr : processors) {
			if (pr != null) {
				pr.holder(this);
				pr.saveToNBT(compound);
			}
		}
		super.saveAdditional(compound);
	}

	protected GenericTile(BlockEntityType<?> tileEntityTypeIn, BlockPos worldPos, BlockState blockState) {
		super(tileEntityTypeIn, worldPos, blockState);
	}

	@Override
	public void onLoad() {
		super.onLoad();

		for (Component component : components) {
			if (component != null) {
				component.holder(this);
				component.onLoad();
			}
		}

		if (hasComponent(ComponentType.PacketHandler)) {
			Scheduler.schedule(1, () -> {
				this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
				this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
			});
		}

	}

	@Override
	public net.minecraft.network.chat.Component getName() {
		return hasComponent(ComponentType.Name) ? this.<ComponentName>getComponent(ComponentType.Name).getName() : net.minecraft.network.chat.Component.literal(References.ID + ".default.tile.name");
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ElectrodynamicsCapabilities.ELECTRODYNAMIC && components[ComponentType.Electrodynamic.ordinal()] != null) {
			return components[ComponentType.Electrodynamic.ordinal()].getCapability(cap, side);
		}
		if (cap == ForgeCapabilities.FLUID_HANDLER && components[ComponentType.FluidHandler.ordinal()] != null) {
			return components[ComponentType.FluidHandler.ordinal()].getCapability(cap, side);
		}
		if (cap == ForgeCapabilities.ITEM_HANDLER && components[ComponentType.Inventory.ordinal()] != null) {
			return components[ComponentType.Inventory.ordinal()].getCapability(cap, side);
		}
		return super.getCapability(cap, side);
	}

	@Override
	public void setRemoved() {
		super.setRemoved();
		for (Component component : components) {
			if (component != null) {
				component.holder(this);
				component.remove();
			}
		}
		for (ComponentProcessor pr : processors) {
			if (pr != null) {
				pr.holder(this);
				pr.remove();
			}
		}
	}

	@Override
	public CompoundTag getUpdateTag() {
		// sendAllProperties();
		CompoundTag tag = super.getUpdateTag();
		saveAdditional(tag);
		return tag;
	}

	@Override
	public void setChanged() {
		super.setChanged();
		if (!level.isClientSide) {
			if (hasComponent(ComponentType.PacketHandler)) {
				this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendProperties();
			}
		}
	}

	public SimpleContainerData getCoordsArray() {
		SimpleContainerData array = new SimpleContainerData(3);
		array.set(0, worldPosition.getX());
		array.set(1, worldPosition.getY());
		array.set(2, worldPosition.getZ());
		return array;
	}

	public boolean isPoweredByRedstone() {
		return level.getDirectSignalTo(worldPosition) > 0;
	}

	public boolean isProcessorActive() {
		for (ComponentProcessor pr : processors) {
			if (pr != null && pr.operatingTicks.get() > 0) {
				return true;
			}
		}
		return false;
	}

	protected static TriPredicate<Integer, ItemStack, ComponentInventory> machineValidator() {
		return (x, y, i) -> x < i.getOutputStartIndex() || x >= i.getInputBucketStartIndex() && x < i.getUpgradeSlotStartIndex() && CapabilityUtils.hasFluidItemCap(y) || x >= i.getUpgradeSlotStartIndex() && y.getItem() instanceof ItemUpgrade upgrade && i.isUpgradeValid(upgrade.subtype);
	}

	protected static TriPredicate<Integer, ItemStack, ComponentInventory> machineValidator(int[] ints) {
		List<Integer> list = new ArrayList<>();
		for (int i : ints) {
			list.add(i);
		}
		return (x, y, i) -> list.contains(x) || x >= i.getInputBucketStartIndex() && x < i.getUpgradeSlotStartIndex() && CapabilityUtils.hasFluidItemCap(y) || x >= i.getUpgradeSlotStartIndex() && y.getItem() instanceof ItemUpgrade upgrade && i.isUpgradeValid(upgrade.subtype);
	}

	public void onEnergyChange(ComponentElectrodynamic cap) {
		// hook method for now
	}

	// no more polling for upgrade effects :D
	public void onInventoryChange(ComponentInventory inv, int slot) {
		// this can be moved to a seperate tile class in the future
		if (hasComponent(ComponentType.Processor)) {
			this.<ComponentProcessor>getComponent(ComponentType.Processor).onInventoryChange(inv, slot);
		}
		for (ComponentProcessor processor : processors) {
			if (processor != null) {
				processor.onInventoryChange(inv, slot);
			}
		}
	}

	public void onFluidTankChange(FluidTank tank) {
		// hook method for now
	}

	// This is ceded to the tile to allow for greater control with the use function
	public InteractionResult use(Player player, InteractionHand handIn, BlockHitResult hit) {
		ItemStack stack = player.getItemInHand(handIn);
		if (stack.getItem() instanceof ItemUpgrade upgrade && hasComponent(ComponentType.Inventory)) {

			ComponentInventory inv = getComponent(ComponentType.Inventory);
			// null check for safety
			if (inv != null && inv.upgrades() > 0) {
				int upgradeIndex = inv.getUpgradeSlotStartIndex();
				for (int i = 0; i < inv.upgrades(); i++) {
					if (inv.canPlaceItem(upgradeIndex + i, stack)) {
						ItemStack upgradeStack = inv.getItem(upgradeIndex + i);
						if (upgradeStack.isEmpty()) {
							inv.setItem(upgradeIndex + i, stack.copy());
							stack.shrink(stack.getCount());
							return InteractionResult.CONSUME;
						} else if (ItemUtils.testItems(upgrade, upgradeStack.getItem())) {
							int room = upgradeStack.getMaxStackSize() - upgradeStack.getCount();
							if (room > 0) {
								int accepted = room > stack.getCount() ? stack.getCount() : room;
								upgradeStack.grow(accepted);
								stack.shrink(accepted);
								return InteractionResult.CONSUME;
							}
						}
					}
				}
			}

		} else if (!(stack.getItem() instanceof IWrenchItem)) {
			if (hasComponent(ComponentType.ContainerProvider)) {
				player.openMenu(getComponent(ComponentType.ContainerProvider));
			}
			player.awardStat(Stats.INTERACT_WITH_FURNACE);
			return InteractionResult.CONSUME;
		}
		return InteractionResult.PASS;
	}

	public void onBlockDestroyed() {

	}

	public void onNeightborChanged(BlockPos neighbor) {

	}

	public void onPlace(BlockState oldState, boolean isMoving) {

	}

}
