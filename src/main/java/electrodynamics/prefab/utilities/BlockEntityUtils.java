package electrodynamics.prefab.utilities;

import electrodynamics.common.block.BlockMachine;
import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEntityUtils {
	public static final int[][] RELATIVE_MATRIX = { { 3, 2, 1, 0, 5, 4 }, { 4, 5, 0, 1, 2, 3 }, { 0, 1, 3, 2, 4, 5 }, { 0, 1, 2, 3, 5, 4 }, { 0, 1, 5, 4, 3, 2 }, { 0, 1, 4, 5, 2, 3 } };

	public static Direction getRelativeSide(Direction main, Direction relative) {
		if (main == null || relative == null) {
			return Direction.UP;
		}
		return Direction.from3DDataValue(RELATIVE_MATRIX[main.ordinal()][relative.ordinal()]);
	}

	public static void updateLit(GenericTile tile, Boolean value) {
		World world = tile.getLevel();
		BlockPos pos = tile.getBlockPos();
		if (tile.getBlockState().hasProperty(BlockMachine.ON)) {
			world.setBlockAndUpdate(pos, world.getBlockState(pos).setValue(BlockMachine.ON, value));
		}
	}

	public static boolean isLit(GenericTile tile) {
		if (tile.getBlockState().hasProperty(BlockMachine.ON)) {
			return tile.getBlockState().getValue(BlockMachine.ON);
		}
		return false;
	}

	public static void saveToItem(TileEntity entity, ItemStack stack) {
		CompoundNBT compoundnbt = entity.save(new CompoundNBT());
		if (!compoundnbt.isEmpty()) {
			stack.addTagElement("BlockEntityTag", compoundnbt);
		}
	}

}