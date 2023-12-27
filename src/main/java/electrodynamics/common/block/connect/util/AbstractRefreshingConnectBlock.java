package electrodynamics.common.block.connect.util;

import javax.annotation.Nullable;

import electrodynamics.api.network.cable.IRefreshableCable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public abstract class AbstractRefreshingConnectBlock extends AbstractConnectBlock {

	public AbstractRefreshingConnectBlock(Properties properties, double radius) {
		super(properties, radius);
	}

	@Override
	public void setPlacedBy(World worldIn, BlockPos pos, BlockState stateIn, @Nullable LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(worldIn, pos, stateIn, placer, stack);
		BlockPos relPos;
		for (Direction dir : Direction.values()) {
			relPos = pos.relative(dir);
			stateIn = refreshConnections(worldIn.getBlockState(relPos), worldIn.getBlockEntity(relPos), stateIn, dir);
		}
		worldIn.setBlockAndUpdate(pos, stateIn);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updateShape(stateIn, facing, facingState, world, currentPos, facingPos);
		if (stateIn.getValue(BlockStateProperties.WATERLOGGED)) {
			world.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		return refreshConnections(facingState, world.getBlockEntity(facingPos), stateIn, facing);
	}

	@Override
	public void onPlace(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onPlace(state, worldIn, pos, oldState, isMoving);
		if (worldIn.isClientSide) {
			return;
		}
		TileEntity tile = worldIn.getBlockEntity(pos);
		IRefreshableCable conductor = getCableIfValid(tile);
		if (conductor == null) {
			return;
		}
		conductor.refreshNetwork();
		BlockPos relPos;
		for (Direction dir : Direction.values()) {
			relPos = pos.relative(dir);
			state = refreshConnections(worldIn.getBlockState(relPos), worldIn.getBlockEntity(relPos), state, dir);
		}
		worldIn.setBlockAndUpdate(pos, state);
	}

	@Override
	public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {
		super.onNeighborChange(state, world, pos, neighbor);
		if (world.isClientSide()) {
			return;
		}
		TileEntity tile = world.getBlockEntity(pos);
		IRefreshableCable conductor = getCableIfValid(tile);
		if (conductor == null) {
			return;
		}
		conductor.refreshNetworkIfChange();
	}

	public abstract BlockState refreshConnections(BlockState otherState, TileEntity tile, BlockState thisState, Direction dir);

	@Nullable
	public abstract IRefreshableCable getCableIfValid(TileEntity tile);

}
