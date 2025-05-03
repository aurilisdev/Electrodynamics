package electrodynamics.common.tile.machines;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerCobblestoneGenerator;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.common.item.ItemUpgrade;
import voltaic.common.item.subtype.SubtypeItemUpgrade;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils.MachineDirection;
import voltaic.registers.VoltaicCapabilities;

public class TileCobblestoneGenerator extends GenericTile {

	public final SingleProperty<Double> progress = property(new SingleProperty<>(PropertyTypes.DOUBLE, "generatorProgress", 0.0));
	public final SingleProperty<Boolean> isPowered = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "generatorIsPowered", false));
	public final SingleProperty<Double> speed = property(new SingleProperty<>(PropertyTypes.DOUBLE, "generatorSpeed", 0.0));
	public final SingleProperty<Double> usage = property(new SingleProperty<>(PropertyTypes.DOUBLE, "generatorUsage", 1.0));

	public static final int OUTPUT_SLOT = 0;

	public TileCobblestoneGenerator(BlockPos worldPos, BlockState blockState) {
		super(ElectrodynamicsTiles.TILE_COBBLESTONEGENERATOR.get(), worldPos, blockState);
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(MachineDirection.BOTTOM).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE).maxJoules(ElectroConstants.COBBLE_GEN_USAGE_PER_TICK * 10));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().outputs(1).upgrades(3)).validUpgrades(ContainerCobblestoneGenerator.VALID_UPGRADES).valid(machineValidator()).setDirectionsBySlot(0, MachineDirection.TOP, MachineDirection.FRONT, MachineDirection.LEFT, MachineDirection.BACK, MachineDirection.RIGHT));
		addComponent(new ComponentContainerProvider(SubtypeMachine.cobblestonegenerator.tag(), this).createMenu((id, player) -> new ContainerCobblestoneGenerator(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tick) {

		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

		if (electro.getJoulesStored() < usage.getValue()) {
			isPowered.setValue(false);
			return;
		}

		ComponentInventory inv = getComponent(IComponentType.Inventory);
		ItemStack output = inv.getOutputContents().get(0);

		if (!output.isEmpty() && output.getCount() >= output.getMaxStackSize()) {
			return;
		}

		if (progress.getValue() < ElectroConstants.COBBLE_GEN_REQUIRED_TICKS) {
			progress.setValue(progress.getValue() + speed.getValue());
			electro.joules(electro.getJoulesStored() - usage.getValue());
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

			this.speed.setValue(speed);

			this.usage.setValue(ElectroConstants.COBBLE_GEN_USAGE_PER_TICK * speed);

		}
		super.onInventoryChange(inv, slot);
	}

}
