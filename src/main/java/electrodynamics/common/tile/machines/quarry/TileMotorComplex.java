package electrodynamics.common.tile.machines.quarry;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerMotorComplex;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.registers.ElectrodynamicsSounds;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.item.ItemStack;
import voltaic.common.item.ItemUpgrade;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.sound.ITickableSound;
import voltaic.prefab.sound.SoundBarrierMethods;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.*;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileMotorComplex extends GenericTile implements ITickableSound {

	// 10 ticks per block
	public static final int DEFAULT_SPEED = Math.min(ElectroConstants.MIN_QUARRYBLOCKS_PER_TICK, 100);
	// 1 tick per block
	public static final int MAX_SPEED = Math.max(ElectroConstants.MAX_QUARRYBLOCKS_PER_TICK, 1);

	private boolean isSoundPlaying = false;

	public final SingleProperty<Integer> speed = property(new SingleProperty<>(PropertyTypes.INTEGER, "speed", DEFAULT_SPEED));
	public final SingleProperty<Double> powerMultiplier = property(new SingleProperty<>(PropertyTypes.DOUBLE, "powerMultiplier", 1.0));
	public final SingleProperty<Boolean> isPowered = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "isPowered", false));

	public TileMotorComplex() {
		super(ElectrodynamicsTiles.TILE_MOTORCOMPLEX.get());
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(BlockEntityUtils.MachineDirection.FRONT).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 2).maxJoules(ElectroConstants.MOTORCOMPLEX_USAGE_PER_TICK * 10000));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().upgrades(3)).validUpgrades(ContainerMotorComplex.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentContainerProvider(SubtypeMachine.motorcomplex.tag(), this).createMenu((id, player) -> new ContainerMotorComplex(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tick) {
		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

		if (electro.getJoulesStored() >= ElectroConstants.MOTORCOMPLEX_USAGE_PER_TICK * powerMultiplier.getValue()) {
			electro.joules(electro.getJoulesStored() - ElectroConstants.MOTORCOMPLEX_USAGE_PER_TICK * powerMultiplier.getValue());
			isPowered.setValue(true);
		} else {
			isPowered.setValue(false);
		}
	}

	private void tickClient(ComponentTickable tick) {
		if (shouldPlaySound() && !isSoundPlaying) {
			isSoundPlaying = true;
			SoundBarrierMethods.playTileSound(ElectrodynamicsSounds.SOUND_MOTORRUNNING.get(), this, true);
		}
	}

	@Override
	public void onInventoryChange(ComponentInventory inv, int slot) {
		super.onInventoryChange(inv, slot);
		if (inv.getUpgradeContents().size() > 0 && (slot >= inv.getUpgradeSlotStartIndex() || slot == -1)) {
			speed.setValue(DEFAULT_SPEED);
			powerMultiplier.setValue(1.0);
			for (ItemStack stack : inv.getUpgradeContents()) {
				if (!stack.isEmpty()) {
					for (int i = 0; i < stack.getCount(); i++) {
						switch (((ItemUpgrade) stack.getItem()).subtype) {
						case basicspeed:
							speed.setValue((int) Math.max((double) speed.getValue() * 0.8, MAX_SPEED));
							powerMultiplier.setValue(powerMultiplier.getValue() * 3);
							break;
						case advancedspeed:
							speed.setValue((int) Math.max((double) speed.getValue() * 0.5, MAX_SPEED));
							powerMultiplier.setValue(powerMultiplier.getValue() * 2);
							break;
						default:
							break;
						}
					}
				}
			}
		}
	}

	@Override
	public void setNotPlaying() {
		isSoundPlaying = false;
	}

	@Override
	public boolean shouldPlaySound() {
		return isPowered.getValue();
	}

	@Override
	public int getComparatorSignal() {
		return isPowered.getValue() ? 15 : 0;
	}

}
