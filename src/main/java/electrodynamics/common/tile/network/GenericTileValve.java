package electrodynamics.common.tile.network;

import org.jetbrains.annotations.NotNull;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class GenericTileValve extends GenericTile {

	public static final Direction INPUT_DIR = Direction.SOUTH;
	public static final Direction OUTPUT_DIR = Direction.NORTH;

	public boolean isClosed = false;
	
	protected boolean isLocked = false;

	public GenericTileValve(BlockEntityType<?> tile, BlockPos pos, BlockState state) {
		super(tile, pos, state);
	}

	@Override
	public void onNeightborChanged(BlockPos neighbor) {
		super.onNeightborChanged(neighbor);

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
	public void saveAdditional(@NotNull CompoundTag compound) {
		super.saveAdditional(compound);

		compound.putBoolean("valveisclosed", isClosed);
	}

	@Override
	public void load(@NotNull CompoundTag compound) {
		super.load(compound);

		isClosed = compound.getBoolean("valveisclosed");
	}

}
