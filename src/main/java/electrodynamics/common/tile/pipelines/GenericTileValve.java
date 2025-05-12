package electrodynamics.common.tile.pipelines;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.utilities.BlockEntityUtils;

public class GenericTileValve extends GenericTile {

	public static final BlockEntityUtils.MachineDirection INPUT_DIR = BlockEntityUtils.MachineDirection.FRONT;
	public static final BlockEntityUtils.MachineDirection OUTPUT_DIR = BlockEntityUtils.MachineDirection.BACK;

	public boolean isClosed = false;

	protected boolean isLocked = false;

	public GenericTileValve(TileEntityType<?> tile) {
		super(tile);
	}

	@Override
	public void onNeightborChanged(BlockPos neighbor, boolean blockStateTrigger) {
		if (level.isClientSide) {
			return;
		}

		if (level.hasNeighborSignal(worldPosition)) {
			isClosed = true;
		} else {
			isClosed = false;
		}

		if (BlockEntityUtils.isLit(this) ^ isClosed) {
			BlockEntityUtils.updateLit(this, isClosed);
		}

	}

	@Override
	public void onPlace(BlockState oldState, boolean isMoving) {
		super.onPlace(oldState, isMoving);
		if (level.isClientSide) {
			return;
		}
		if (level.hasNeighborSignal(worldPosition)) {
			isClosed = true;
		} else {
			isClosed = false;
		}

		if (BlockEntityUtils.isLit(this) ^ isClosed) {
			BlockEntityUtils.updateLit(this, isClosed);
		}
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		compound.putBoolean("valveisclosed", isClosed);
		return super.save(compound);
	}

	@Override
	public void load(BlockState state, CompoundNBT compound) {
		super.load(state, compound);
		isClosed = compound.getBoolean("valveisclosed");
	}
}
