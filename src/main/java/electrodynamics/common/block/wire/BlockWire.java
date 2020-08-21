package electrodynamics.common.block.wire;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.network.conductor.IConductor;
import electrodynamics.api.tile.electric.IElectricTile;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.electricity.damage.ElectricDamageSource;
import electrodynamics.common.tile.TileWire;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
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

public class BlockWire extends Block {

	public static final EnumProperty<EnumConnectType> DOWN = EnumProperty.create("down", EnumConnectType.class);
	public static final EnumProperty<EnumConnectType> UP = EnumProperty.create("up", EnumConnectType.class);
	public static final EnumProperty<EnumConnectType> NORTH = EnumProperty.create("north", EnumConnectType.class);
	public static final EnumProperty<EnumConnectType> SOUTH = EnumProperty.create("south", EnumConnectType.class);
	public static final EnumProperty<EnumConnectType> WEST = EnumProperty.create("west", EnumConnectType.class);
	public static final EnumProperty<EnumConnectType> EAST = EnumProperty.create("east", EnumConnectType.class);
	public static final Map<Direction, EnumProperty<EnumConnectType>> FACING_TO_PROPERTY_MAP = Util.make(Maps.newEnumMap(Direction.class), (p) -> {
		p.put(Direction.NORTH, NORTH);
		p.put(Direction.EAST, EAST);
		p.put(Direction.SOUTH, SOUTH);
		p.put(Direction.WEST, WEST);
		p.put(Direction.UP, UP);
		p.put(Direction.DOWN, DOWN);
	});

	public static final HashSet<Block> WIRESET = new HashSet<>();

	protected final VoxelShape AABB;
	protected final VoxelShape AABB_UP;
	protected final VoxelShape AABB_DOWN;
	protected final VoxelShape AABB_NORTH;
	protected final VoxelShape AABB_SOUTH;
	protected final VoxelShape AABB_WEST;
	protected final VoxelShape AABB_EAST;

	protected HashMap<HashSet<Direction>, VoxelShape> AABBSTATES = new HashMap<>();

	public final SubtypeWire wire;

	public BlockWire(SubtypeWire wire) {
		super(Properties.create(wire.insulated ? Material.WOOL : Material.IRON).sound(wire.insulated ? SoundType.CLOTH : SoundType.METAL).hardnessAndResistance(0.15f).variableOpacity());
		this.wire = wire;
		double w = wire.insulated ? 2 : 1;
		double sm = 8 - w;
		double lg = 8 + w;
		AABB = Block.makeCuboidShape(sm, sm, sm, lg, lg, lg);
		AABB_UP = Block.makeCuboidShape(sm, sm, sm, lg, 16, lg);
		AABB_DOWN = Block.makeCuboidShape(sm, 0, sm, lg, lg, lg);
		AABB_NORTH = Block.makeCuboidShape(sm, sm, 0, lg, lg, lg);
		AABB_SOUTH = Block.makeCuboidShape(sm, sm, sm, lg, lg, 16);
		AABB_WEST = Block.makeCuboidShape(0, sm, sm, lg, lg, lg);
		AABB_EAST = Block.makeCuboidShape(sm, sm, sm, 16, lg, lg);
		WIRESET.add(this);
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {
		return Arrays.asList(new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(wire)));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
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
		for (HashSet<Direction> set : AABBSTATES.keySet()) {
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
		AABBSTATES.put(checked, shape);
		return shape;
	}

	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (!wire.insulated) {
			TileWire wire = (TileWire) worldIn.getTileEntity(pos);
			if (wire != null) {
				if (wire.getNetwork() != null) {
					if (wire.getNetwork().getSavedAmpsTransmissionBuffer() > 0) {
						entityIn.attackEntityFrom(ElectricDamageSource.ELECTRICITY, (float) (wire.getNetwork().getSavedAmpsTransmissionBuffer() / 120));
					}
				}
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState stateIn, @Nullable LivingEntity placer, ItemStack stack) {
		for (Direction d : Direction.values()) {
			TileEntity facingTile = worldIn.getTileEntity(pos.offset(d));
			if (facingTile instanceof IConductor) {
				stateIn = stateIn.with(FACING_TO_PROPERTY_MAP.get(d), EnumConnectType.WIRE);
				worldIn.setBlockState(pos, stateIn);
			} else if (facingTile instanceof IElectricTile && ((IElectricTile) facingTile).canConnectElectrically(d.getOpposite())) {
				stateIn = stateIn.with(FACING_TO_PROPERTY_MAP.get(d), EnumConnectType.INVENTORY);
				worldIn.setBlockState(pos, stateIn);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
		if (!worldIn.isRemote) {
			TileEntity tile = worldIn.getTileEntity(pos);
			if (tile instanceof IConductor) {
				((IConductor) tile).refreshNetwork();
			}
		}
	}

	@Override
	public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {
		super.onNeighborChange(state, world, pos, neighbor);
		if (!world.isRemote()) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof IConductor) {
				((IConductor) tile).refreshNetwork();
			}
		}
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		EnumProperty<EnumConnectType> property = FACING_TO_PROPERTY_MAP.get(facing);
		TileEntity tile = world.getTileEntity(facingPos);
		if (tile instanceof IConductor) {
			return stateIn.with(property, EnumConnectType.WIRE);
		} else if (tile instanceof IElectricTile && ((IElectricTile) tile).canConnectElectrically(facing.getOpposite())) {
			return stateIn.with(property, EnumConnectType.INVENTORY);
		} else {
			return stateIn.with(property, EnumConnectType.NONE);
		}
	}

	// TODO: Maybe add some random block tick update here which we use to
	// electrocute players in water
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileWire();
	}
}
