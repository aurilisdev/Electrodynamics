package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;

public class TileTransformer extends GenericTile {
    public CachedTileOutput output;
    public TransferPack lastTransfer = TransferPack.EMPTY;
    public boolean locked = false;

    public TileTransformer() {
	super(DeferredRegisters.TILE_TRANSFORMER.get());
	addComponent(new ComponentDirection());
	addComponent(
		new ComponentElectrodynamic(this).receivePower(this::receivePower).relativeOutput(Direction.SOUTH).relativeInput(Direction.NORTH));
    }

    protected TransferPack receivePower(TransferPack transfer, boolean debug) {
	Direction facing = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	if (locked) {
	    return TransferPack.EMPTY;
	}
	if (output == null) {
	    output = new CachedTileOutput(level, worldPosition.relative(facing));
	}
	boolean shouldUpgrade = ((BlockMachine) getBlockState().getBlock()).machine == SubtypeMachine.upgradetransformer;
	double resultVoltage = Mth.clamp(transfer.getVoltage() * (shouldUpgrade ? 2 : 0.5), 15.0, 61440.0);
	locked = true;
	TransferPack returner = ElectricityUtilities.receivePower(output.getSafe(), facing.getOpposite(),
		TransferPack.joulesVoltage(transfer.getJoules() * Constants.TRANSFORMER_EFFICIENCY, resultVoltage), debug);
	locked = false;
	if (returner.getJoules() > 0) {
	    returner = TransferPack.joulesVoltage(returner.getJoules() + transfer.getJoules() * (1.0 - Constants.TRANSFORMER_EFFICIENCY),
		    resultVoltage);
	}
	lastTransfer = returner;
	return returner;
    }
}
