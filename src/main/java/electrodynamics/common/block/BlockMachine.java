package electrodynamics.common.block;

import java.util.Arrays;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.block.subtype.SubtypeMachine;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
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
import net.minecraft.world.World;

public class BlockMachine extends BlockGenericMachine {
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

	@Deprecated
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (machine.shouldBreakOnReplaced(state, newState)) {
			TileEntity tile = worldIn.getTileEntity(pos);
			if (tile instanceof IInventory) {
				InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tile);
			}
			super.onReplaced(state, worldIn, pos, newState, isMoving);
		} else {
			worldIn.markBlockRangeForRenderUpdate(pos, state, newState);
		}
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return machine.createTileEntity(world);
	}
}
