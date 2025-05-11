package electrodynamics.common.tile.electricitygrid;

import electrodynamics.common.network.type.ElectricNetwork;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import voltaic.api.electricity.ICapabilityElectrodynamic;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.object.CachedTileOutput;
import voltaic.prefab.utilities.object.TransferPack;

public class TileMultimeterBlock extends GenericTile {

	//TODO Flip it so it places facing towards the player

	public SingleProperty<Double> voltage = property(new SingleProperty<>(PropertyTypes.DOUBLE, "voltageNew", 0.0));
	public SingleProperty<Double> minVoltage = property(new SingleProperty<>(PropertyTypes.DOUBLE, "minvoltage", 0.0));
	public SingleProperty<Double> joules = property(new SingleProperty<>(PropertyTypes.DOUBLE, "joulesNew", 0.0));
	public SingleProperty<Double> resistance = property(new SingleProperty<>(PropertyTypes.DOUBLE, "resistanceNew", 0.0));
	public SingleProperty<Double> loss = property(new SingleProperty<>(PropertyTypes.DOUBLE, "lossNew", 0.0));

	public CachedTileOutput input;

	public TileMultimeterBlock() {
		super(ElectrodynamicsTiles.TILE_MULTIMETERBLOCK.get());
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, false, false).receivePower(this::receivePower).getConnectedLoad(this::getConnectedLoad).setInputDirections(BlockEntityUtils.MachineDirection.FRONT).voltage(-1));
	}

	public void tickServer(ComponentTickable tickable) {

		if (tickable.getTicks() % (minVoltage.getValue() == 0 ? 20 : 2) == 0) {
			Direction facing = getFacing();
			if (input == null) {
				input = new CachedTileOutput(level, worldPosition.relative(facing));
			}
			TileEntity blockentity = input.getSafe();
			if (blockentity instanceof GenericTileWire) {
				GenericTileWire cond = (GenericTileWire) blockentity;
				ElectricNetwork network = cond.getNetwork();
				if(network == null) {
					return;
				}
				joules.setValue(network.getActiveTransmitted());
				voltage.setValue(network.getActiveVoltage());
				minVoltage.setValue(network.getMinimumVoltage());
				resistance.setValue(network.getResistance());
				loss.setValue(network.getLastEnergyLoss());
			} else {
				joules.setValue(0.0);
				voltage.setValue(0.0);
				minVoltage.setValue(0.0);
				resistance.setValue(0.0);
				loss.setValue(0.0);
			}
		}
	}

	protected TransferPack receivePower(TransferPack transfer, boolean debug) {
		return TransferPack.EMPTY;
	}

	protected TransferPack getConnectedLoad(ICapabilityElectrodynamic.LoadProfile loadProfile, Direction dir) {
		return TransferPack.EMPTY;
	}
}
