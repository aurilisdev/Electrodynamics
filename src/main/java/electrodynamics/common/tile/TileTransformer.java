package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.generic.GenericTile;
import electrodynamics.common.tile.generic.component.ComponentType;
import electrodynamics.common.tile.generic.component.type.ComponentDirection;
import electrodynamics.common.tile.generic.component.type.ComponentElectrodynamic;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;

public class TileTransformer extends GenericTile {
    public CachedTileOutput output;
    public double lastTransfer = 0;
    public boolean locked = false;

    public TileTransformer() {
	super(DeferredRegisters.TILE_TRANSFORMER.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentElectrodynamic(this).setFunctionReceivePower(this::receivePower)
		.addRelativeOutputDirection(Direction.SOUTH).addRelativeInputDirection(Direction.NORTH));
    }

    protected TransferPack receivePower(TransferPack transfer, boolean debug) {
	Direction facing = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	if (locked) {
	    return TransferPack.EMPTY;
	}
	if (output == null) {
	    output = new CachedTileOutput(world, pos.offset(facing));
	}
	boolean shouldUpgrade = ((BlockMachine) getBlockState()
		.getBlock()).machine == SubtypeMachine.upgradetransformer;
	double resultVoltage = MathHelper.clamp(transfer.getVoltage() * (shouldUpgrade ? 2 : 0.5), 15.0, 1920.0);
	locked = true;
	TransferPack returner = debug ? TransferPack.ampsVoltage(1, 1)
		: ElectricityUtilities.receivePower(output.get(), facing.getOpposite(), TransferPack
			.joulesVoltage(transfer.getJoules() * Constants.TRANSFORMER_EFFICIENCY, resultVoltage), debug);
	locked = false;
	if (returner.getJoules() > 0) {
	    returner = TransferPack.joulesVoltage(
		    returner.getJoules() + transfer.getJoules() * (1.0 - Constants.TRANSFORMER_EFFICIENCY),
		    resultVoltage);
	}
	lastTransfer = returner.getJoules();
	return returner;
    }
}
