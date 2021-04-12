package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.api.electricity.IElectrodynamic;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.generic.GenericTileWire;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.LazyOptional;

public class TileCircuitBreaker extends GenericTile {
    public CachedTileOutput output;
    public CachedTileOutput output2;
    public double lastTransfer = 0;
    public boolean locked = false;

    public TileCircuitBreaker() {
	super(DeferredRegisters.TILE_CIRCUITBREAKER.get());
	addComponent(new ComponentDirection());
	addComponent(
		new ComponentElectrodynamic(this).receivePower(this::receivePower).relativeOutput(Direction.SOUTH).relativeInput(Direction.NORTH));
    }

    protected TransferPack receivePower(TransferPack transfer, boolean debug) {
	Direction facing = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	if (output == null) {
	    output = new CachedTileOutput(world, pos.offset(facing));
	}
	if (output2 == null) {
	    output2 = new CachedTileOutput(world, pos.offset(facing.getOpposite()));
	}
	if (locked || checkDirection(true, transfer, debug)) {
	    return TransferPack.EMPTY;
	}
	locked = true;
	TransferPack returner = ElectricityUtilities.receivePower(output.getSafe(), facing.getOpposite(),
		TransferPack.joulesVoltage(transfer.getJoules() * Constants.CIRCUITBREAKER_EFFICIENCY, transfer.getVoltage()), debug);
	locked = false;
	if (returner.getJoules() > 0) {
	    returner = TransferPack.joulesVoltage(returner.getJoules() + transfer.getJoules() * (1.0 - Constants.TRANSFORMER_EFFICIENCY),
		    returner.getVoltage());
	}
	lastTransfer = returner.getJoules();
	return returner;
    }

    protected boolean checkDirection(boolean facing, TransferPack transfer, boolean debug) {
	TileEntity tile = facing ? output.getSafe() : output2.getSafe();
	if (tile instanceof GenericTileWire) {
	    GenericTileWire wire = (GenericTileWire) tile;
	    if (wire.electricNetwork != null) {
		if (wire.electricNetwork.getNetworkMaxTransfer() <= transfer.getAmps() && !debug) {
		    return true;
		}
		for (TileEntity acceptor : wire.electricNetwork.getEnergyAcceptors()) {
		    LazyOptional<IElectrodynamic> el = acceptor.getCapability(CapabilityElectrodynamic.ELECTRODYNAMIC);
		    if (el.isPresent() && !(acceptor instanceof TileCircuitBreaker) && el.resolve().get().getVoltage() < transfer.getVoltage()) {
			return true;
		    }
		}
	    }
	}
	return false;
    }
}
