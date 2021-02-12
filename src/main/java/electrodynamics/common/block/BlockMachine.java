package electrodynamics.common.block;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.multiblock.IMultiblockNode;
import electrodynamics.common.multiblock.IMultiblockTileNode;
import electrodynamics.common.multiblock.Subnode;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BlockMachine extends BlockGenericMachine implements IMultiblockNode {
	public static final HashSet<Subnode> advancedsolarpanelsubnodes = new HashSet<>();
	static {
		int radius = 1;
		for (int i = -radius; i <= radius; i++) {
			for (int j = -radius; j <= radius; j++) {
				if (i == 0 && j == 0) {
					advancedsolarpanelsubnodes.add(new Subnode(new BlockPos(i, 1, j), VoxelShapes.fullCube()));
				} else {
					advancedsolarpanelsubnodes.add(new Subnode(new BlockPos(i, 1, j), VoxelShapes.create(0, 13.0 / 16.0, 0, 1, 1, 1)));
				}
			}
		}
	}
	private static final VoxelShape transformershape = VoxelShapes.create(0, 0, 0, 1, 15.0 / 16.0, 1);
	private static final VoxelShape solargenshape = VoxelShapes.create(0, 0, 0, 1, 9.0 / 16.0, 1);

	public final SubtypeMachine machine;

	public BlockMachine(SubtypeMachine machine) {
		this.machine = machine;
	}

	@Deprecated
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return machine == SubtypeMachine.downgradetransformer || machine == SubtypeMachine.downgradetransformer ? transformershape
				: machine == SubtypeMachine.solarpanel ? solargenshape : super.getShape(state, worldIn, pos, context);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return isValidMultiblockPlacement(state, worldIn, pos, machine == SubtypeMachine.advancedsolarpanel ? advancedsolarpanelsubnodes : new HashSet<Subnode>());
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if (machine.showInItemGroup) {
			items.add(new ItemStack(this));
		}
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {
		return Arrays.asList(new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(machine == SubtypeMachine.coalgeneratorrunning ? SubtypeMachine.coalgenerator
				: machine == SubtypeMachine.electricfurnacerunning ? SubtypeMachine.electricfurnace : machine == SubtypeMachine.oxidationfurnacerunning ? SubtypeMachine.oxidationfurnace : machine)));
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return machine == SubtypeMachine.coalgeneratorrunning ? 12 : machine == SubtypeMachine.electricfurnacerunning ? 8 : machine == SubtypeMachine.oxidationfurnacerunning ? 6 : super.getLightValue(state, world, pos);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		if (hasMultiBlock()) {
			TileEntity tile = worldIn.getTileEntity(pos);
			if (tile instanceof IMultiblockTileNode) {
				IMultiblockTileNode multi = (IMultiblockTileNode) tile;
				multi.onNodePlaced(worldIn, pos, state, placer, stack);
			}
		}
	}

	@Deprecated
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		boolean update = machine.shouldBreakOnReplaced(state, newState);
		if (hasMultiBlock()) {
			TileEntity tile = worldIn.getTileEntity(pos);
			if (tile instanceof IMultiblockTileNode) {
				IMultiblockTileNode multi = (IMultiblockTileNode) tile;
				multi.onNodeReplaced(worldIn, pos, !update);
			}
		}
		if (update) {
			super.onReplaced(state, worldIn, pos, newState, isMoving);
		} else {
			worldIn.markBlockRangeForRenderUpdate(pos, state, newState);
		}
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return machine.createTileEntity(world);
	}

	@Override
	public boolean hasMultiBlock() {
		return machine == SubtypeMachine.advancedsolarpanel;
	}
}
