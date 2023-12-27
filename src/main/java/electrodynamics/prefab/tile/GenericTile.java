package electrodynamics.prefab.tile;

import electrodynamics.api.IWrenchItem;
import electrodynamics.api.References;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.prefab.block.GenericEntityBlock;
import electrodynamics.prefab.properties.IPropertyType.TagReader;
import electrodynamics.prefab.properties.IPropertyType.TagWriter;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyManager;
import electrodynamics.prefab.tile.components.IComponent;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.*;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.prefab.utilities.ItemUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.INameable;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.TriPredicate;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.UUID;

import javax.annotation.Nonnull;

public class GenericTile extends TileEntity implements INameable, IPropertyHolderTile, ITickableTileEntity {
	private final IComponent[] components = new IComponent[IComponentType.values().length];
	private final ComponentProcessor[] processors = new ComponentProcessor[5];
	private final PropertyManager propertyManager = new PropertyManager(this);

	// use this for manually setting the change flag
	public boolean isChanged = false;

	public GenericTile(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public <T> Property<T> property(Property<T> prop) {
		for (Property<?> existing : propertyManager.getProperties()) {
			if (existing.getName().equals(prop.getName())) {
				throw new RuntimeException(prop.getName() + " is already being used by another property!");
			}
		}

		return propertyManager.addProperty(prop);
	}

	public boolean hasComponent(IComponentType type) {
		return components[type.ordinal()] != null;
	}

	@Override
	public PropertyManager getPropertyManager() {
		return propertyManager;
	}

	public <T extends IComponent> T getComponent(IComponentType type) {
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

	public GenericTile addComponent(IComponent component) {
		component.holder(this);
		if (hasComponent(component.getType())) {
			throw new ExceptionInInitializerError("Component of type: " + component.getType().name() + " already registered!");
		}
		components[component.getType().ordinal()] = component;
		return this;
	}

	@Deprecated // "Try not using this method."
	public GenericTile forceComponent(IComponent component) {
		component.holder(this);
		components[component.getType().ordinal()] = component;
		return this;
	}

	@Override
	public void load(BlockState state, @Nonnull CompoundNBT compound) {
		super.load(state, compound);
		for (Property<?> prop : propertyManager.getProperties()) {
			if (prop.shouldSave()) {
				prop.load(prop.getType().readFromTag(new TagReader(prop, compound)));
				compound.remove(prop.getName());
			}
		}
		for (IComponent component : components) {
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
	public CompoundNBT save(@Nonnull CompoundNBT compound) {
		for (Property<?> prop : propertyManager.getProperties()) {
			if (prop.shouldSave()) {
				prop.getType().writeToTag(new TagWriter(prop, compound));
			}
		}
		for (IComponent component : components) {
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
		return super.save(compound);
	}

	@Override
	public void onLoad() {
		super.onLoad();

		for (IComponent component : components) {
			if (component != null) {
				component.holder(this);
				component.onLoad();
			}
		}
		/*
		 * if (hasComponent(ComponentType.PacketHandler)) {
		 * this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
		 * this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking(); }
		 */
	}

	@Override
	public ITextComponent getName() {
		return hasComponent(IComponentType.Name) ? this.<ComponentName>getComponent(IComponentType.Name).getName() : new StringTextComponent(References.ID + ".default.tile.name");
	}

	@Override
	@Nonnull
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
		if (cap == ElectrodynamicsCapabilities.ELECTRODYNAMIC && components[IComponentType.Electrodynamic.ordinal()] != null) {
			return components[IComponentType.Electrodynamic.ordinal()].getCapability(cap, side);
		}
		if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && components[IComponentType.FluidHandler.ordinal()] != null) {
			return components[IComponentType.FluidHandler.ordinal()].getCapability(cap, side);
		}
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && components[IComponentType.Inventory.ordinal()] != null) {
			return components[IComponentType.Inventory.ordinal()].getCapability(cap, side);
		}
		return LazyOptional.empty();
	}

	@Override
	public void setRemoved() {
		super.setRemoved();
		for (IComponent component : components) {
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
	public @Nonnull CompoundNBT getUpdateTag() {
		CompoundNBT tag = super.getUpdateTag();
		tag = save(tag);
		return tag;
	}

	@Override
	public void setChanged() {
		super.setChanged();
		if (!level.isClientSide) {
			if (hasComponent(IComponentType.PacketHandler)) {
				this.<ComponentPacketHandler>getComponent(IComponentType.PacketHandler).sendProperties();
			}
		}
	}

	public IntArray getCoordsArray() {
		IntArray array = new IntArray(3);
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
			if (pr != null && pr.isActive()) {
				return true;
			}
		}
		return false;
	}

	public int getNumActiveProcessors() {
		int count = 0;
		for (ComponentProcessor pr : processors) {
			if (pr != null && pr.isActive()) {
				count++;
			}
		}
		return count;
	}

	public int getNumProcessors() {
		int count = 0;
		for (ComponentProcessor pr : processors) {
			if (pr != null) {
				count++;
			}
		}
		return count;
	}

	/**
	 * NORTH is defined as the default direction
	 * 
	 * @return
	 */
	public Direction getFacing() {
		return getBlockState().hasProperty(GenericEntityBlock.FACING) ? getBlockState().getValue(GenericEntityBlock.FACING) : Direction.NORTH;
	}

	public void onEnergyChange(ComponentElectrodynamic cap) {
		// hook method for now
	}

	// no more polling for upgrade effects :D
	public void onInventoryChange(ComponentInventory inv, int slot) {
		// this can be moved to a seperate tile class in the future
		if (hasComponent(IComponentType.Processor)) {
			this.<ComponentProcessor>getComponent(IComponentType.Processor).onInventoryChange(inv, slot);
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
	public ActionResultType use(PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

		ItemStack stack = player.getItemInHand(handIn);
		if (stack.getItem() instanceof ItemUpgrade && hasComponent(IComponentType.Inventory)) {

			ItemUpgrade upgrade = (ItemUpgrade) stack.getItem();

			ComponentInventory inv = getComponent(IComponentType.Inventory);
			// null check for safety
			if (inv != null && inv.upgrades() > 0) {
				int upgradeIndex = inv.getUpgradeSlotStartIndex();
				for (int i = 0; i < inv.upgrades(); i++) {
					if (inv.canPlaceItem(upgradeIndex + i, stack)) {
						ItemStack upgradeStack = inv.getItem(upgradeIndex + i);
						if (upgradeStack.isEmpty()) {
							if (!level.isClientSide()) {
								inv.setItem(upgradeIndex + i, stack.copy());
								stack.shrink(stack.getCount());
							}
							return ActionResultType.CONSUME;
						}
						if (ItemUtils.testItems(upgrade, upgradeStack.getItem())) {
							int room = upgradeStack.getMaxStackSize() - upgradeStack.getCount();
							if (room > 0) {
								if (!level.isClientSide()) {
									int accepted = room > stack.getCount() ? stack.getCount() : room;
									upgradeStack.grow(accepted);
									stack.shrink(accepted);
								}
								return ActionResultType.CONSUME;
							}
						}
					}
				}
			}

		} else if (!(stack.getItem() instanceof IWrenchItem)) {

			if (hasComponent(IComponentType.ContainerProvider)) {

				if (!level.isClientSide()) {

					player.openMenu(getComponent(IComponentType.ContainerProvider));

					player.awardStat(Stats.INTERACT_WITH_FURNACE);
				}

				return ActionResultType.CONSUME;

			}

		}
		return ActionResultType.PASS;
	}

	public void onBlockDestroyed() {

	}

	public void onNeightborChanged(BlockPos neighbor, boolean blockStateTrigger) {

	}

	public void onPlace(BlockState oldState, boolean isMoving) {

		for (IComponent component : components) {
			if (component != null) {
				component.holder(this);
				component.onLoad();
			}
		}

	}

	public int getComparatorSignal() {
		return 0;
	}

	public int getDirectSignal(Direction dir) {
		return 0;
	}

	public int getSignal(Direction dir) {
		return 0;
	}

	public void onEntityInside(BlockState state, World level, BlockPos pos, Entity entity) {

	}

	public void updateCarriedItemInContainer(ItemStack stack, UUID playerId) {
		PlayerEntity player = getLevel().getPlayerByUUID(playerId);
		player.inventory.setCarried(stack);

	}

	protected static TriPredicate<Integer, ItemStack, ComponentInventory> machineValidator() {
		return (x, y, i) -> x < i.getOutputStartIndex() || x >= i.getInputBucketStartIndex() && x < i.getUpgradeSlotStartIndex() && CapabilityUtils.hasFluidItemCap(y) || x >= i.getUpgradeSlotStartIndex() && y.getItem() instanceof ItemUpgrade && i.isUpgradeValid(((ItemUpgrade) y.getItem()).subtype);
	}

	public static double[] arr(double... values) {
		return values;
	}

	public static int[] arr(int... values) {
		return values;
	}

	/**
	 * This method will never have air as the newState unless something has gone horribly horribly wrong!
	 * 
	 * @param oldState
	 * @param newState
	 */
	public void onBlockStateUpdate(BlockState oldState, BlockState newState) {
		for (IComponent component : components) {
			if (component != null) {
				component.refreshIfUpdate(oldState, newState);
			}
		}

		for (IComponent component : processors) {
			if (component != null) {
				component.refreshIfUpdate(oldState, newState);
			}
		}
	}

	@Override
	public void tick() {
		if (hasComponent(IComponentType.Tickable)) {
			ComponentTickable tickable = getComponent(IComponentType.Tickable);
			tickable.performTick(level);
		}
	}

}
