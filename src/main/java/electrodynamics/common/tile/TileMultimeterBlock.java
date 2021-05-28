package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.network.conductor.IConductor;
import electrodynamics.common.network.ElectricNetwork;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;

public class TileMultimeterBlock extends GenericTileTicking {
    public double voltage = 0.0;
    public double joules = 0;
    public double resistance = 0;
    public double loss = 0;
    public CachedTileOutput input;

    public TileMultimeterBlock() {
	super(DeferredRegisters.TILE_MULTIMETERBLOCK.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickServer));
	addComponent(new ComponentPacketHandler().customPacketWriter(this::createPacket).customPacketReader(this::readPacket));
	addComponent(new ComponentElectrodynamic(this).receivePower(this::receivePower).relativeInput(Direction.SOUTH));
    }

    protected void tickServer(ComponentTickable tickable) {
	if (tickable.getTicks() % 10 == 0) {
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
	}
    }

    protected void createPacket(CompoundNBT nbt) {
	nbt.putDouble("joules", joules);
	nbt.putDouble("voltage", voltage);
	nbt.putDouble("resistance", resistance);
	nbt.putDouble("loss", loss);
    }

    protected void readPacket(CompoundNBT nbt) {
	joules = nbt.getDouble("joules");
	voltage = nbt.getDouble("voltage");
	resistance = nbt.getDouble("resistance");
	loss = nbt.getDouble("loss");
    }

    protected TransferPack receivePower(TransferPack transfer, boolean debug) {
	Direction facing = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	if (input == null) {
	    input = new CachedTileOutput(world, pos.offset(facing));
	}
	if (input.getSafe() instanceof IConductor) {
	    IConductor cond = input.getSafe();
	    if (cond.getAbstractNetwork() instanceof ElectricNetwork) {
		ElectricNetwork net = (ElectricNetwork) cond.getAbstractNetwork();
		joules = net.getActiveTransmitted();
		voltage = net.getActiveVoltage();
		resistance = net.getResistance();
		loss = net.getLastEnergyLoss();
	    }
	}
	return TransferPack.EMPTY;
    }
}
