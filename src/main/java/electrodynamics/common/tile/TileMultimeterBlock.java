package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.network.conductor.IConductor;
import electrodynamics.common.network.ElectricNetwork;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class TileMultimeterBlock extends GenericTile {
    public double voltage = 0.0;
    public double joules = 0;
    public double resistance = 0;
    public double loss = 0;
    public CachedTileOutput input;

    public TileMultimeterBlock(BlockPos worldPosition, BlockState blockState) {
	super(DeferredRegisters.TILE_MULTIMETERBLOCK.get(), worldPosition, blockState);
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().tickServer(this::tickServer));
	addComponent(new ComponentPacketHandler().customPacketWriter(this::createPacket).customPacketReader(this::readPacket));
	addComponent(new ComponentElectrodynamic(this).receivePower(this::receivePower).relativeInput(Direction.SOUTH));
    }

    protected void tickServer(ComponentTickable tickable) {
	if (tickable.getTicks() % (joules == 0 ? 20 : 2) == 0) {
	    Direction facing = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	    if (input == null) {
		input = new CachedTileOutput(level, worldPosition.relative(facing));
	    }
	    if (input.getSafe() instanceof IConductor) {
		IConductor cond = input.getSafe();
		if (cond.getAbstractNetwork()instanceof ElectricNetwork net) {
		    joules = net.getActiveTransmitted();
		    voltage = net.getActiveVoltage();
		    resistance = net.getResistance();
		    loss = net.getLastEnergyLoss();
		}
	    } else {
		joules = voltage = resistance = loss = 0;
	    }
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
	}
    }

    protected void createPacket(CompoundTag nbt) {
	nbt.putDouble("joules", joules);
	nbt.putDouble("voltage", voltage);
	nbt.putDouble("resistance", resistance);
	nbt.putDouble("loss", loss);
    }

    protected void readPacket(CompoundTag nbt) {
	joules = nbt.getDouble("joules");
	voltage = nbt.getDouble("voltage");
	resistance = nbt.getDouble("resistance");
	loss = nbt.getDouble("loss");
    }

    protected TransferPack receivePower(TransferPack transfer, boolean debug) {
	return TransferPack.EMPTY;
    }
}
