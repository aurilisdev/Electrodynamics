package electrodynamics.common.block.connect.util;

import javax.annotation.Nullable;

import electrodynamics.api.network.cable.IRefreshableCable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;

public abstract class AbstractRefreshingConnectBlock extends AbstractConnectBlock {

	public AbstractRefreshingConnectBlock(Properties properties, double radius) {
		super(properties, radius);
	}

	@Override
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState stateIn, @Nullable LivingEntity placer, ItemStack stack) {
		BlockPos relPos;
		for (Direction dir : Direction.values()) {
			relPos = pos.relative(dir);
			stateIn = refreshConnections(worldIn.getBlockState(relPos), worldIn.getBlockEntity(relPos), stateIn, dir);
		}
		worldIn.setBlockAndUpdate(pos, stateIn);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(BlockStateProperties.WATERLOGGED) == Boolean.TRUE) {
			world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		return refreshConnections(facingState, world.getBlockEntity(facingPos), stateIn, facing);
	}

	@Override
	public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onPlace(state, worldIn, pos, oldState, isMoving);
		if (worldIn.isClientSide) {
			return;
		}
		BlockEntity tile = worldIn.getBlockEntity(pos);
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
	public void onNeighborChange(BlockState state, LevelReader world, BlockPos pos, BlockPos neighbor) {
		super.onNeighborChange(state, world, pos, neighbor);
		if (world.isClientSide()) {
			return;
		}
		BlockEntity tile = world.getBlockEntity(pos);
		IRefreshableCable conductor = getCableIfValid(tile);
		if (conductor == null) {
			return;
		}
		conductor.refreshNetworkIfChange();
	}

	public abstract BlockState refreshConnections(BlockState otherState, BlockEntity tile, BlockState thisState, Direction dir);

	@Nullable
	public abstract IRefreshableCable getCableIfValid(BlockEntity tile);

}
