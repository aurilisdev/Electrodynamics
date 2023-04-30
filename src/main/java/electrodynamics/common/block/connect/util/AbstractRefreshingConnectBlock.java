package electrodynamics.common.block.connect.util;

import javax.annotation.Nullable;

import electrodynamics.api.network.cable.IRefreshableConductor;
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
		for (Direction dir : Direction.values()) {
			stateIn = refreshConnections(worldIn.getBlockEntity(pos.relative(dir)), stateIn, dir);
		}
		worldIn.setBlockAndUpdate(pos, stateIn);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(BlockStateProperties.WATERLOGGED) == Boolean.TRUE) {
			world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		return refreshConnections(world.getBlockEntity(facingPos), stateIn, facing);
	}

	@Override
	public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onPlace(state, worldIn, pos, oldState, isMoving);
		if (worldIn.isClientSide) {
			return;
		}
		BlockEntity tile = worldIn.getBlockEntity(pos);
		IRefreshableConductor conductor = getCableIfValid(tile);
		if (conductor == null) {
			return;
		}
		conductor.refreshNetwork();
		for (Direction dir : Direction.values()) {
			state = refreshConnections(worldIn.getBlockEntity(pos.relative(dir)), state, dir);
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
		IRefreshableConductor conductor = getCableIfValid(tile);
		if (conductor == null) {
			return;
		}
		conductor.refreshNetworkIfChange();
	}

	public abstract BlockState refreshConnections(BlockEntity tile, BlockState state, Direction dir);

	@Nullable
	public abstract IRefreshableConductor getCableIfValid(BlockEntity tile);

}
