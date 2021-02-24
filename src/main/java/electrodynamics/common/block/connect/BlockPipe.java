package electrodynamics.common.block.connect;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.network.pipe.IPipe;
import electrodynamics.common.block.subtype.SubtypePipe;
import electrodynamics.common.network.FluidUtilities;
import electrodynamics.common.tile.wire.TilePipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext.Builder;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BlockPipe extends Block {

    public static final EnumProperty<EnumConnectType> DOWN = EnumProperty.create("down", EnumConnectType.class);
    public static final EnumProperty<EnumConnectType> UP = EnumProperty.create("up", EnumConnectType.class);
    public static final EnumProperty<EnumConnectType> NORTH = EnumProperty.create("north", EnumConnectType.class);
    public static final EnumProperty<EnumConnectType> SOUTH = EnumProperty.create("south", EnumConnectType.class);
    public static final EnumProperty<EnumConnectType> WEST = EnumProperty.create("west", EnumConnectType.class);
    public static final EnumProperty<EnumConnectType> EAST = EnumProperty.create("east", EnumConnectType.class);
    public static final Map<Direction, EnumProperty<EnumConnectType>> FACING_TO_PROPERTY_MAP = Util
	    .make(Maps.newEnumMap(Direction.class), (p) -> {
		p.put(Direction.NORTH, NORTH);
		p.put(Direction.EAST, EAST);
		p.put(Direction.SOUTH, SOUTH);
		p.put(Direction.WEST, WEST);
		p.put(Direction.UP, UP);
		p.put(Direction.DOWN, DOWN);
	    });

    public static final HashSet<Block> PIPESET = new HashSet<>();

    protected final VoxelShape AABB;
    protected final VoxelShape AABB_UP;
    protected final VoxelShape AABB_DOWN;
    protected final VoxelShape AABB_NORTH;
    protected final VoxelShape AABB_SOUTH;
    protected final VoxelShape AABB_WEST;
    protected final VoxelShape AABB_EAST;

    protected HashMap<HashSet<Direction>, VoxelShape> AABBSTATES = new HashMap<>();

    public final SubtypePipe pipe;

    public BlockPipe(SubtypePipe pipe) {
	super(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(0.15f).variableOpacity());
	this.pipe = pipe;
	double w = 3;
	double sm = 8 - w;
	double lg = 8 + w;
	AABB = Block.makeCuboidShape(sm, sm, sm, lg, lg, lg);
	AABB_UP = Block.makeCuboidShape(sm, sm, sm, lg, 16, lg);
	AABB_DOWN = Block.makeCuboidShape(sm, 0, sm, lg, lg, lg);
	AABB_NORTH = Block.makeCuboidShape(sm, sm, 0, lg, lg, lg);
	AABB_SOUTH = Block.makeCuboidShape(sm, sm, sm, lg, lg, 16);
	AABB_WEST = Block.makeCuboidShape(0, sm, sm, lg, lg, lg);
	AABB_EAST = Block.makeCuboidShape(sm, sm, sm, 16, lg, lg);
	PIPESET.add(this);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, Builder builder) {
	return Arrays.asList(new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(pipe)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
	super.fillStateContainer(builder);
	builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
	return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
	VoxelShape shape = AABB;
	HashSet<Direction> checked = new HashSet<>();
	if (!state.get(UP).equals(EnumConnectType.NONE)) {
	    checked.add(Direction.UP);
	}
	if (!state.get(DOWN).equals(EnumConnectType.NONE)) {
	    checked.add(Direction.DOWN);
	}
	if (!state.get(WEST).equals(EnumConnectType.NONE)) {
	    checked.add(Direction.WEST);
	}
	if (!state.get(EAST).equals(EnumConnectType.NONE)) {
	    checked.add(Direction.EAST);
	}
	if (!state.get(NORTH).equals(EnumConnectType.NONE)) {
	    checked.add(Direction.NORTH);
	}
	if (!state.get(SOUTH).equals(EnumConnectType.NONE)) {
	    checked.add(Direction.SOUTH);
	}
	HashMap<HashSet<Direction>, VoxelShape> copy = (HashMap<HashSet<Direction>, VoxelShape>) AABBSTATES.clone();
	for (HashSet<Direction> set : new HashSet<>(copy.keySet())) {
	    if (set.equals(checked)) {
		return AABBSTATES.get(set);
	    }
	}
	for (Direction dir : checked) {
	    switch (dir) {
	    case DOWN:
		shape = VoxelShapes.combineAndSimplify(shape, AABB_DOWN, IBooleanFunction.OR);
		break;
	    case EAST:
		shape = VoxelShapes.combineAndSimplify(shape, AABB_EAST, IBooleanFunction.OR);
		break;
	    case NORTH:
		shape = VoxelShapes.combineAndSimplify(shape, AABB_NORTH, IBooleanFunction.OR);
		break;
	    case SOUTH:
		shape = VoxelShapes.combineAndSimplify(shape, AABB_SOUTH, IBooleanFunction.OR);
		break;
	    case UP:
		shape = VoxelShapes.combineAndSimplify(shape, AABB_UP, IBooleanFunction.OR);
		break;
	    case WEST:
		shape = VoxelShapes.combineAndSimplify(shape, AABB_WEST, IBooleanFunction.OR);
		break;
	    default:
		break;
	    }
	}
	copy.put(checked, shape);
	AABBSTATES = copy;
	if (shape == null) {
	    return VoxelShapes.empty();
	}
	return shape;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState stateIn, @Nullable LivingEntity placer,
	    ItemStack stack) {
	for (Direction d : Direction.values()) {
	    TileEntity facingTile = worldIn.getTileEntity(pos.offset(d));
	    if (FluidUtilities.isConductor(facingTile)) {
		worldIn.setBlockState(pos, stateIn.with(FACING_TO_PROPERTY_MAP.get(d), EnumConnectType.WIRE));
	    } else if (FluidUtilities.isFluidReceiver(facingTile, d.getOpposite())) {
		worldIn.setBlockState(pos, stateIn.with(FACING_TO_PROPERTY_MAP.get(d), EnumConnectType.INVENTORY));
	    }
	}
    }

    @Deprecated
    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
	super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
	if (!worldIn.isRemote) {
	    TileEntity tile = worldIn.getTileEntity(pos);
	    if (tile instanceof IPipe) {
		((IPipe) tile).refreshNetwork();
	    }
	}
    }

    @Override
    public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {
	super.onNeighborChange(state, world, pos, neighbor);
	if (!world.isRemote()) {
	    TileEntity tile = world.getTileEntity(pos);
	    if (tile instanceof IPipe) {
		((IPipe) tile).refreshNetwork();
	    }
	}
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld world,
	    BlockPos currentPos, BlockPos facingPos) {
	EnumProperty<EnumConnectType> property = FACING_TO_PROPERTY_MAP.get(facing);
	TileEntity tile = world.getTileEntity(facingPos);
	if (tile instanceof IPipe) {
	    return stateIn.with(property, EnumConnectType.WIRE);
	} else if (FluidUtilities.isFluidReceiver(tile, facing.getOpposite())) {
	    return stateIn.with(property, EnumConnectType.INVENTORY);
	} else {
	    return stateIn.with(property, EnumConnectType.NONE);
	}
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
	return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
	return new TilePipe();
    }
}
