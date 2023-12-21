package electrodynamics.common.tile.machines;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerCobblestoneGenerator;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;

public class TileCobblestoneGenerator extends GenericTile {

	public final Property<Double> progress = property(new Property<>(PropertyType.Double, "generatorProgress", 0.0));
	public final Property<Boolean> isPowered = property(new Property<>(PropertyType.Boolean, "generatorIsPowered", false));
	public final Property<Double> speed = property(new Property<>(PropertyType.Double, "generatorSpeed", 0.0));
	public final Property<Double> usage = property(new Property<>(PropertyType.Double, "generatorUsage", 1.0));

	public static final int OUTPUT_SLOT = 0;

	public TileCobblestoneGenerator(BlockPos worldPos, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_COBBLESTONEGENERATOR.get(), worldPos, blockState);
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(Direction.DOWN).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE).maxJoules(Constants.COBBLE_GEN_USAGE_PER_TICK * 10));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().outputs(1).upgrades(3)).validUpgrades(ContainerCobblestoneGenerator.VALID_UPGRADES).valid(machineValidator()).setDirectionsBySlot(0, Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST));
		addComponent(new ComponentContainerProvider(SubtypeMachine.cobblestonegenerator, this).createMenu((id, player) -> new ContainerCobblestoneGenerator(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tick) {

		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

		if (electro.getJoulesStored() < usage.get()) {
			isPowered.set(false);
			return;
		}

		ComponentInventory inv = getComponent(IComponentType.Inventory);
		ItemStack output = inv.getOutputContents().get(0);

		if (!output.isEmpty() && output.getCount() >= output.getMaxStackSize()) {
			return;
		}

		if (progress.get() < Constants.COBBLE_GEN_REQUIRED_TICKS) {
			progress.set(progress.get() + speed.get());
			electro.joules(electro.getJoulesStored() - usage.get());
			return;
		}

		if (output.isEmpty()) {
			inv.setItem(OUTPUT_SLOT, new ItemStack(Items.COBBLESTONE, 1).copy());
		} else {
			inv.setItem(OUTPUT_SLOT, new ItemStack(Items.COBBLESTONE, inv.getItem(OUTPUT_SLOT).getCount() + 1));
		}

	}

	@Override
	public void onInventoryChange(ComponentInventory inv, int slot) {
		if (slot == -1 || slot > 0) {

			double speed = 1;

			for (ItemStack item : inv.getUpgradeContents()) {

				if (!item.isEmpty() && item.getItem() instanceof ItemUpgrade upg) {

					for (int i = 0; i < item.getCount(); i++) {

						if (upg.subtype == SubtypeItemUpgrade.advancedspeed) {

							speed = Math.min(speed * 2.25, Math.pow(2.25, 3));

						} else if (upg.subtype == SubtypeItemUpgrade.basicspeed) {

							speed = Math.min(speed * 1.5, Math.pow(2.25, 3));

						}
					}

				}
			}

			this.speed.set(speed);

			this.usage.set(Constants.COBBLE_GEN_USAGE_PER_TICK * speed);

		}
		super.onInventoryChange(inv, slot);
	}

}
