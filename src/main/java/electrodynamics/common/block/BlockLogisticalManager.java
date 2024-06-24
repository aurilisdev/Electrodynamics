package electrodynamics.common.block;

import java.util.HashMap;

import javax.annotation.Nullable;

import electrodynamics.common.block.connect.util.EnumConnectType;
import electrodynamics.common.tile.machines.quarry.TileLogisticalManager;
import electrodynamics.prefab.block.GenericEntityBlockWaterloggable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * INVENTORY corresponds to a chest WIRE corresponds to a Quarry
 * 
 * @author skip999
 *
 */
public class BlockLogisticalManager extends GenericEntityBlockWaterloggable {

	protected final VoxelShape[] boundingBoxes = new VoxelShape[7];

	protected HashMap<EnumConnectType[], VoxelShape> shapestates = new HashMap<>();
	protected boolean locked = false;

	public BlockLogisticalManager() {
		super(Properties.copy(Blocks.IRON_BLOCK).strength(3.5F).sound(SoundType.METAL).noOcclusion().requiresCorrectToolForDrops());

		generateBoundingBoxes(3);
	}

	public void generateBoundingBoxes(double radius) {
		double w = radius;
		double sm = 8 - w;
		double lg = 8 + w;
		// down
		boundingBoxes[0] = Block.box(sm, 0, sm, lg, lg, lg);
		// up
		boundingBoxes[1] = Block.box(sm, sm, sm, lg, 16, lg);
		// north
		boundingBoxes[2] = Block.box(sm, sm, 0, lg, lg, lg);
		// south
		boundingBoxes[3] = Block.box(sm, sm, sm, lg, lg, 16);
		// west
		boundingBoxes[4] = Block.box(0, sm, sm, lg, lg, lg);
		// east
		boundingBoxes[5] = Block.box(sm, sm, sm, 16, lg, lg);
		// center
		boundingBoxes[6] = Block.box(sm, sm, sm, lg, lg, lg);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		VoxelShape shape = boundingBoxes[6];
		EnumConnectType[] checked = new EnumConnectType[6];

		BlockEntity entity = level.getBlockEntity(pos);

		if (!(entity instanceof TileLogisticalManager)) {
			return Shapes.empty();
		}

		EnumConnectType[] connections = ((TileLogisticalManager) entity).readConnections();

		for (int i = 0; i < 6; i++) {
			EnumConnectType connection = connections[i];

			if (connection != EnumConnectType.NONE) {
				checked[i] = connection;
			}

		}
		locked = true;
		if (shapestates.containsKey(checked)) {
			locked = false;
			return shapestates.get(checked);
		}
		locked = false;
		for (int i = 0; i < 6; i++) {

			EnumConnectType connection = checked[i];

			if (connection == null) {
				continue;
			}

			shape = Shapes.join(shape, boundingBoxes[i], BooleanOp.OR);
		}
		while (locked) {
			System.out.println("Bounding box collided with another block's bounding box!");
		}
		shapestates.put(checked, shape);
		if (shape == null) {
			return Shapes.empty();
		}
		return shape;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileLogisticalManager(pos, state);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	@Override
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState stateIn, @Nullable LivingEntity placer, ItemStack stack) {
		if (worldIn.isClientSide) {
			return;
		}
		BlockState currentState = stateIn;
		TileLogisticalManager tile = (TileLogisticalManager) worldIn.getBlockEntity(pos);
		if (tile == null) {
			return;
		}
		for (Direction dir : Direction.values()) {
			if (TileLogisticalManager.isQuarry(pos.relative(dir), worldIn)) {
				tile.writeConnection(dir, EnumConnectType.WIRE);
			} else if (TileLogisticalManager.isValidInventory(pos.relative(dir), worldIn, dir.getOpposite())) {
				tile.writeConnection(dir, EnumConnectType.INVENTORY);
			}
		}
		worldIn.setBlockAndUpdate(pos, currentState);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(BlockStateProperties.WATERLOGGED) == Boolean.TRUE) {
			world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		TileLogisticalManager tile = (TileLogisticalManager) world.getBlockEntity(currentPos);
		EnumConnectType connection = EnumConnectType.NONE;
		if (tile == null || world.isClientSide()) {
			return stateIn;
		}
		if (TileLogisticalManager.isQuarry(facingPos, world)) {
			connection = EnumConnectType.WIRE;
		} else if (TileLogisticalManager.isValidInventory(facingPos, world, facing.getOpposite())) {
			connection = EnumConnectType.INVENTORY;
		}
		tile.writeConnection(facing, connection);
		return stateIn;
	}

	@Override
	public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onPlace(state, worldIn, pos, oldState, isMoving);
		if (!worldIn.isClientSide) {
			BlockEntity tile = worldIn.getBlockEntity(pos);
			if (tile instanceof TileLogisticalManager manager) {
				manager.refreshConnections();
			}
		}
	}

	@Override
	public void onNeighborChange(BlockState state, LevelReader world, BlockPos pos, BlockPos neighbor) {
		super.onNeighborChange(state, world, pos, neighbor);
		if (!world.isClientSide()) {
			BlockEntity tile = world.getBlockEntity(pos);
			if (tile instanceof TileLogisticalManager manager) {
				manager.refreshConnections();
			}
		}
	}

}
