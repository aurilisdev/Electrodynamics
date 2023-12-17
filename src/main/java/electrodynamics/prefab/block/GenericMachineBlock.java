package electrodynamics.prefab.block;

import electrodynamics.common.block.voxelshapes.VoxelShapeRegistry;
import electrodynamics.prefab.utilities.object.TileEntitySupplier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class GenericMachineBlock extends GenericEntityBlockWaterloggable {

	protected TileEntitySupplier<TileEntity> blockEntitySupplier;

	public GenericMachineBlock(TileEntitySupplier<TileEntity> blockEntitySupplier) {
		super(Properties.copy(Blocks.IRON_BLOCK).strength(3.5F).sound(SoundType.METAL).noOcclusion().requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1));
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
		this.blockEntitySupplier = blockEntitySupplier;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
		if (state.hasProperty(GenericEntityBlock.FACING)) {

			return VoxelShapeRegistry.getShape(level.getBlockState(pos).getBlock(), state.getValue(GenericEntityBlock.FACING));

		}
		return super.getShape(state, level, pos, context);
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader reader) {
		return blockEntitySupplier.create(reader);
	}
	
	@Override
	public float getShadeBrightness(BlockState pState, IBlockReader pLevel, BlockPos pPos) {
		return 1.0F;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

}
