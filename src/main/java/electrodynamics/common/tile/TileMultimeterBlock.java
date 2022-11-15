package electrodynamics.common.tile;

import electrodynamics.api.network.conductor.IConductor;
import electrodynamics.common.network.ElectricNetwork;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class TileMultimeterBlock extends GenericTile {
	public Property<Double> voltage = property(new Property<Double>(PropertyType.Double, "voltage")).set(0.0);
	public Property<Double> joules = property(new Property<Double>(PropertyType.Double, "joules")).set(0.0);
	public Property<Double> resistance = property(new Property<Double>(PropertyType.Double, "resistance")).set(0.0);
	public Property<Double> loss = property(new Property<Double>(PropertyType.Double, "loss")).set(0.0);

	public CachedTileOutput input;

	public TileMultimeterBlock(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_MULTIMETERBLOCK.get(), worldPosition, blockState);
		addComponent(new ComponentDirection());
		addComponent(new ComponentTickable().tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentElectrodynamic(this).receivePower(this::receivePower).relativeInput(Direction.SOUTH));
	}

	protected void tickServer(ComponentTickable tickable) {
		if (tickable.getTicks() % (joules.get() == 0 ? 20 : 2) == 0) {
			Direction facing = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
			if (input == null) {
				input = new CachedTileOutput(level, worldPosition.relative(facing));
			}
			if (input.getSafe() instanceof IConductor) {
				IConductor cond = input.getSafe();
				if (cond.getAbstractNetwork() instanceof ElectricNetwork net) {
					joules.set(net.getActiveTransmitted());
					voltage.set(net.getActiveVoltage());
					resistance.set(net.getResistance());
					loss.set(net.getLastEnergyLoss());
				}
			} else {
				joules.set(0.0);
				voltage.set(0.0);
				resistance.set(0.0);
				loss.set(0.0);
			}
			this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
		}
	}

	protected TransferPack receivePower(TransferPack transfer, boolean debug) {
		return TransferPack.EMPTY;
	}
}
