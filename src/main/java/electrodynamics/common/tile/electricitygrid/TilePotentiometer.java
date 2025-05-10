package electrodynamics.common.tile.electricitygrid;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerPotentiometer;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.util.Direction;
import voltaic.api.electricity.ICapabilityElectrodynamic;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.object.TransferPack;

public class TilePotentiometer extends GenericTile {

	public final SingleProperty<Double> powerConsumption = property(new SingleProperty<>(PropertyTypes.DOUBLE, "consumption", -1.0));

	public TilePotentiometer() {
		super(ElectrodynamicsTiles.TILE_POTENTIOMETER.get());
		addComponent(new ComponentContainerProvider(SubtypeMachine.potentiometer.tag(), this).createMenu((id, player) -> new ContainerPotentiometer(id, player, getCoordsArray())));
		addComponent(new ComponentElectrodynamic(this, false, true).receivePower(this::receivePower).getConnectedLoad(this::getConnectedLoad)
				//
				.setInputDirections(BlockEntityUtils.MachineDirection.FRONT, BlockEntityUtils.MachineDirection.BACK, BlockEntityUtils.MachineDirection.LEFT, BlockEntityUtils.MachineDirection.RIGHT, BlockEntityUtils.MachineDirection.BOTTOM).voltage(-1.0D));
	}

	private TransferPack receivePower(TransferPack pack, boolean debug) {
		if (powerConsumption.getValue() < 0) {
			return pack;
		}
		double accepted = Math.min(pack.getJoules(), powerConsumption.getValue());
		return TransferPack.joulesVoltage(accepted, pack.getVoltage());
	}

	private TransferPack getConnectedLoad(ICapabilityElectrodynamic.LoadProfile loadProfile, Direction dir) {
		if (dir == Direction.DOWN) {
			return TransferPack.EMPTY;
		}
		return TransferPack.joulesVoltage(powerConsumption.getValue() < 0 ? Double.MAX_VALUE : powerConsumption.getValue(), -1);
	}

}
