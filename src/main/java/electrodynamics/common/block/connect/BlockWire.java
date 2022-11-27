package electrodynamics.common.block.connect;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import electrodynamics.api.network.conductor.IConductor;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.block.subtype.SubtypeWire.WireType;
import electrodynamics.common.tile.network.TileLogisticalWire;
import electrodynamics.common.tile.network.TileWire;
import electrodynamics.prefab.block.GenericEntityBlockWaterloggable;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.Scheduler;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockWire extends GenericEntityBlockWaterloggable {
	public static final Map<Direction, EnumProperty<EnumConnectType>> FACING_TO_PROPERTY_MAP = Util.make(Maps.newEnumMap(Direction.class), p -> {
		p.put(Direction.NORTH, EnumConnectType.NORTH);
		p.put(Direction.EAST, EnumConnectType.EAST);
		p.put(Direction.SOUTH, EnumConnectType.SOUTH);
		p.put(Direction.WEST, EnumConnectType.WEST);
		p.put(Direction.UP, EnumConnectType.UP);
		p.put(Direction.DOWN, EnumConnectType.DOWN);
	});

	public static final HashSet<Block> WIRESET = new HashSet<>();

	protected final VoxelShape cube;
	protected final VoxelShape cubeup;
	protected final VoxelShape cubedown;
	protected final VoxelShape cubenorth;
	protected final VoxelShape cubesouth;
	protected final VoxelShape cubewest;
	protected final VoxelShape cubeeast;

	protected HashMap<HashSet<Direction>, VoxelShape> shapestates = new HashMap<>();
	protected boolean locked = false;

	public final SubtypeWire wire;

	public BlockWire(SubtypeWire wire) {
		super(Properties.of(wire.wireType.material).sound(wire.wireType.soundType).strength(0.15f).dynamicShape());
		this.wire = wire;
		double w = wire.wireType.radius;
		double sm = 8 - w;
		double lg = 8 + w;
		cube = Block.box(sm, sm, sm, lg, lg, lg);
		cubeup = Block.box(sm, sm, sm, lg, 16, lg);
		cubedown = Block.box(sm, 0, sm, lg, lg, lg);
		cubenorth = Block.box(sm, sm, 0, lg, lg, lg);
		cubesouth = Block.box(sm, sm, sm, lg, lg, 16);
		cubewest = Block.box(0, sm, sm, lg, lg, lg);
		cubeeast = Block.box(sm, sm, sm, 16, lg, lg);
		WIRESET.add(this);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(EnumConnectType.UP, EnumConnectType.DOWN, EnumConnectType.NORTH, EnumConnectType.EAST, EnumConnectType.SOUTH, EnumConnectType.WEST);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	@Override
	public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return true;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		VoxelShape shape = cube;
		HashSet<Direction> checked = new HashSet<>();
		if (!EnumConnectType.NONE.equals(state.getValue(EnumConnectType.UP))) {
			checked.add(Direction.UP);
		}
		if (!EnumConnectType.NONE.equals(state.getValue(EnumConnectType.DOWN))) {
			checked.add(Direction.DOWN);
		}
		if (!EnumConnectType.NONE.equals(state.getValue(EnumConnectType.WEST))) {
			checked.add(Direction.WEST);
		}
		if (!EnumConnectType.NONE.equals(state.getValue(EnumConnectType.EAST))) {
			checked.add(Direction.EAST);
		}
		if (!EnumConnectType.NONE.equals(state.getValue(EnumConnectType.NORTH))) {
			checked.add(Direction.NORTH);
		}
		if (!EnumConnectType.NONE.equals(state.getValue(EnumConnectType.SOUTH))) {
			checked.add(Direction.SOUTH);
		}
		locked = true;
		if (shapestates.containsKey(checked)) {
			locked = false;
			return shapestates.get(checked);
		}
		locked = false;
		for (Direction dir : checked) {
			switch (dir) {
			case DOWN:
				shape = Shapes.join(shape, cubedown, BooleanOp.OR);
				break;
			case EAST:
				shape = Shapes.join(shape, cubeeast, BooleanOp.OR);
				break;
			case NORTH:
				shape = Shapes.join(shape, cubenorth, BooleanOp.OR);
				break;
			case SOUTH:
				shape = Shapes.join(shape, cubesouth, BooleanOp.OR);
				break;
			case UP:
				shape = Shapes.join(shape, cubeup, BooleanOp.OR);
				break;
			case WEST:
				shape = Shapes.join(shape, cubewest, BooleanOp.OR);
				break;
			default:
				break;
			}
		}
		while (locked) {
			System.out.println("Wire bounding boxes locked. This should never happen but still does for some reason!");
		}
		shapestates.put(checked, shape);
		if (shape == null) {
			return Shapes.empty();
		}
		return shape;
	}

	@Override
	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
		TileWire tile = (TileWire) worldIn.getBlockEntity(pos);
		if (tile != null && tile.getNetwork() != null && tile.getNetwork().getActiveTransmitted() > 0) {
			int shockVoltage = tile.wire.wireClass.shockVoltage;
			if (shockVoltage == 0 || tile.getNetwork().getActiveVoltage() > shockVoltage) {
				ElectricityUtils.electrecuteEntity(entityIn, TransferPack.joulesVoltage(tile.getNetwork().getActiveTransmitted(), tile.getNetwork().getActiveVoltage()));
			}
		}
	}

	@Override
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState stateIn, @Nullable LivingEntity placer, ItemStack stack) {
		BlockState acc = stateIn;
		for (Direction d : Direction.values()) {
			BlockEntity facingTile = worldIn.getBlockEntity(pos.relative(d));
			if (ElectricityUtils.isConductor(facingTile)) {
				acc = acc.setValue(FACING_TO_PROPERTY_MAP.get(d), EnumConnectType.WIRE);
			} else if (ElectricityUtils.isElectricReceiver(facingTile, d.getOpposite())) {
				acc = acc.setValue(FACING_TO_PROPERTY_MAP.get(d), EnumConnectType.INVENTORY);
			}
		}
		worldIn.setBlockAndUpdate(pos, acc);
	}

	@Override
	public boolean isSignalSource(BlockState state) {
		return ((BlockWire) state.getBlock()).wire.wireType.conductsRedstone;
	}

	@Override
	public int getDirectSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
		return blockState.getSignal(blockAccess, pos, side);
	}

	@Override
	public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
		BlockEntity tile = blockAccess.getBlockEntity(pos);
		if (tile instanceof TileLogisticalWire w) {
			return w.isPowered ? 15 : 0;
		}
		return 0;
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(BlockStateProperties.WATERLOGGED) == Boolean.TRUE) {
			world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		EnumProperty<EnumConnectType> property = FACING_TO_PROPERTY_MAP.get(facing);
		BlockEntity tile = world.getBlockEntity(facingPos);
		if (tile instanceof IConductor) {
			return stateIn.setValue(property, EnumConnectType.WIRE);
		} else if (ElectricityUtils.isElectricReceiver(tile, facing.getOpposite())) {
			return stateIn.setValue(property, EnumConnectType.INVENTORY);
		} else {
			return stateIn.setValue(property, EnumConnectType.NONE);
		}
	}

	@Override
	public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onPlace(state, worldIn, pos, oldState, isMoving);
		if (!worldIn.isClientSide) {
			BlockEntity tile = worldIn.getBlockEntity(pos);
			if (tile instanceof IConductor c) {
				c.refreshNetwork();
			}
		}
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return wire.wireClass.fireProof ? state.hasProperty(BlockStateProperties.WATERLOGGED) && Boolean.TRUE.equals(state.getValue(BlockStateProperties.WATERLOGGED)) ? 0 : 150 : 0;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return wire.wireClass.fireProof ? state.hasProperty(BlockStateProperties.WATERLOGGED) && Boolean.TRUE.equals(state.getValue(BlockStateProperties.WATERLOGGED)) ? 0 : 400 : 0;
	}

	@Override
	public void onNeighborChange(BlockState state, LevelReader world, BlockPos pos, BlockPos neighbor) {
		super.onNeighborChange(state, world, pos, neighbor);
		if (!world.isClientSide()) {
			BlockEntity tile = world.getBlockEntity(pos);
			if (tile instanceof IConductor c) {
				c.refreshNetworkIfChange();
			}
		}
	}

	@Override
	public void onCaughtFire(BlockState state, Level world, BlockPos pos, Direction face, LivingEntity igniter) {
		super.onCaughtFire(state, world, pos, face, igniter);
		Scheduler.schedule(5, () -> world.setBlock(pos, ElectrodynamicsBlocks.getBlock(SubtypeWire.getWireForType(WireType.UNINSULATED, wire.material)).defaultBlockState(), UPDATE_ALL));
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return ((BlockWire) state.getBlock()).wire.wireType == WireType.LOGISTICAL ? new TileLogisticalWire(pos, state) : new TileWire(pos, state);
	}
	
}
