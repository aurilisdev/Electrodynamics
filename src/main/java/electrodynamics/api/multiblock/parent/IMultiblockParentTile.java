package electrodynamics.api.multiblock.parent;

import electrodynamics.api.multiblock.Subnode;
import electrodynamics.api.multiblock.child.IMultiblockChildBlock;
import electrodynamics.common.tile.TileMultiSubnode;
import electrodynamics.prefab.utilities.object.Location;
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public interface IMultiblockParentTile {

	/*
	 * Okay so hear me out:
	 * 
	 * Yes, using an array is a little more confusing, however it gives a fixed index to a subnode
	 * 
	 * This is useful as it means a subnode can look itself up on the parent tile really quickly and reliably if there's a data point it needs to reference
	 * 
	 * 
	 */

	default void onNodeReplaced(World world, BlockPos pos, boolean update) {

		Subnode[] subnodes = getSubNodes();

		Subnode subnode;

		BlockPos offset, subnodePos;

		BlockState subnodeState;

		Block subnodeBlock;

		for (int i = 0; i < subnodes.length; i++) {

			subnode = subnodes[i];

			subnodePos = subnode.pos;
			offset = pos.offset(subnodePos);
			subnodeState = world.getBlockState(offset);
			subnodeBlock = subnodeState.getBlock();

			if (update) {
				if (subnodeState.getMaterial().isReplaceable()) {
					world.setBlockAndUpdate(offset, ElectrodynamicsBlocks.multi.defaultBlockState());
				}
				TileMultiSubnode subnodeTile = (TileMultiSubnode) world.getBlockEntity(offset);
				if (subnodeTile != null) {
					subnodeTile.setData(pos, i);
				}
			} else if (subnodeBlock instanceof IMultiblockChildBlock) {
				TileMultiSubnode subnodeTile = (TileMultiSubnode) world.getBlockEntity(offset);
				if (subnodeTile != null && subnodeTile.parentPos.get().equals(new Location(pos))) {
					world.setBlockAndUpdate(offset, Blocks.AIR.defaultBlockState());
				}
			}

		}

		/*
		 * 
		 * Iterator<Subnode> it = getSubNodes().iterator(); while (it.hasNext()) { BlockPos inPos = it.next().pos(); BlockPos offset = pos.offset(inPos); BlockState inState = world.getBlockState(offset); Block inBlock = inState.getBlock(); if (update) { if (inState.getMaterial().isReplaceable()) { world.setBlockAndUpdate(offset, ElectrodynamicsBlocks.multi.defaultBlockState()); } TileMultiSubnode subnode = (TileMultiSubnode) world.getBlockEntity(offset); if (subnode != null) { subnode.nodePos.set(pos); } } else if (inBlock instanceof IMultiblockChildBlock) { TileMultiSubnode subnode = (TileMultiSubnode) world.getBlockEntity(offset); if (subnode != null && subnode.nodePos.get().equals(pos)) { world.setBlockAndUpdate(offset, Blocks.AIR.defaultBlockState()); } } }
		 * 
		 */
	}

	default void onNodePlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		onNodeReplaced(world, pos, true);
	}

	Subnode[] getSubNodes();

	default void onSubnodeNeighborChange(TileMultiSubnode subnode, BlockPos subnodeChangingNeighbor, boolean blockStateTrigger) {

	}

	default ActionResultType onSubnodeUse(PlayerEntity player, Hand hand, BlockRayTraceResult hit, TileMultiSubnode subnode) {
		return ActionResultType.FAIL;
	}

	default void onSubnodePlace(TileMultiSubnode subnode, BlockState oldSubnodeState, boolean isSubnodeMoving) {

	}

	default int getSubdnodeComparatorSignal(TileMultiSubnode subnode) {
		return 0;
	}

	void onSubnodeDestroyed(TileMultiSubnode subnode);

	default Direction getFacingDirection() {
		return Direction.NORTH;
	}

	default int getDirectSignal(TileMultiSubnode subnode, Direction dir) {
		return 0;
	}

	default int getSignal(TileMultiSubnode subnode, Direction dir) {
		return 0;
	}
}
