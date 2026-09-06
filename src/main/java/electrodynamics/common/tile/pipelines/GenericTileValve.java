package electrodynamics.common.tile.pipelines;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.utilities.BlockEntityUtils;

public class GenericTileValve extends GenericTile {

    public static final BlockEntityUtils.MachineDirection INPUT_DIR = BlockEntityUtils.MachineDirection.FRONT;
    public static final BlockEntityUtils.MachineDirection OUTPUT_DIR = BlockEntityUtils.MachineDirection.BACK;

    public boolean isClosed = false;

    protected boolean isLocked = false;

    public GenericTileValve(BlockEntityType<?> tile, BlockPos pos, BlockState state) {
	super(tile, pos, state);
    }

    @Override
    public void onNeighbourChanged(LevelReader reader, BlockPos neighbor, boolean blockStateTrigger) {
	if (reader instanceof Level level) {
	    if (level.isClientSide)
		return;

	    if (level.hasNeighborSignal(worldPosition)) {
		isClosed = true;
	    } else {
		isClosed = false;
	    }

	    if (BlockEntityUtils.isLit(this) ^ isClosed) {
		BlockEntityUtils.updateLit(this, isClosed);
	    }

	}
    }

    @Override
    public void onPlace(Level level, BlockState oldState, boolean isMoving) {
	super.onPlace(level, oldState, isMoving);
	if (level.isClientSide)
	    return;
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
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
	super.saveAdditional(compound, registries);

	compound.putBoolean("valveisclosed", isClosed);
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
	super.loadAdditional(compound, registries);
	isClosed = compound.getBoolean("valveisclosed");
    }
}
