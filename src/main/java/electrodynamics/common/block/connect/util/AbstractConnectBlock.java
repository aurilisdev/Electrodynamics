package electrodynamics.common.block.connect.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.prefab.block.GenericEntityBlockWaterloggable;
import electrodynamics.prefab.tile.types.GenericConnectTile;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class AbstractConnectBlock extends GenericEntityBlockWaterloggable {

	public static final Map<Direction, EnumProperty<EnumConnectType>> FACING_TO_PROPERTY_MAP = Util.make(Maps.newEnumMap(Direction.class), map -> {
		map.put(Direction.NORTH, EnumConnectType.NORTH);
		map.put(Direction.EAST, EnumConnectType.EAST);
		map.put(Direction.SOUTH, EnumConnectType.SOUTH);
		map.put(Direction.WEST, EnumConnectType.WEST);
		map.put(Direction.UP, EnumConnectType.UP);
		map.put(Direction.DOWN, EnumConnectType.DOWN);
	});

	public static final Map<Direction, EnumProperty<EnumConnectType>> DIRECTION_TO_CONNECTTYPE_MAP = Util.make(Maps.newEnumMap(Direction.class), map -> {
		map.put(Direction.UP, EnumConnectType.UP);
		map.put(Direction.DOWN, EnumConnectType.DOWN);
		map.put(Direction.NORTH, EnumConnectType.NORTH);
		map.put(Direction.EAST, EnumConnectType.EAST);
		map.put(Direction.SOUTH, EnumConnectType.SOUTH);
		map.put(Direction.WEST, EnumConnectType.WEST);
	});

	protected final VoxelShape[] boundingBoxes = new VoxelShape[7];

	protected HashMap<HashSet<Direction>, VoxelShape> shapestates = new HashMap<>();
	protected boolean locked = false;

	public AbstractConnectBlock(Properties properties, double radius) {
		super(properties);
		generateBoundingBoxes(radius);
		stateDefinition.any().setValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING, false);
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
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {

		VoxelShape camoShape = Shapes.empty();

		if (state.getValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING) && worldIn.getBlockEntity(pos) instanceof GenericConnectTile connect) {

			if (connect.isCamoAir()) {
				camoShape = connect.getScaffoldBlock().getBlock().getShape(connect.getScaffoldBlock(), worldIn, pos, context);
			} else {
				camoShape = connect.getCamoBlock().getBlock().getShape(connect.getCamoBlock(), worldIn, pos, context);
			}

		}

		VoxelShape shape = boundingBoxes[6];
		HashSet<Direction> checked = new HashSet<>();

		for (Direction dir : Direction.values()) {

			if (!EnumConnectType.NONE.equals(state.getValue(DIRECTION_TO_CONNECTTYPE_MAP.get(dir)))) {
				checked.add(dir);
			}

		}
		locked = true;
		if (shapestates.containsKey(checked)) {
			locked = false;
			return Shapes.join(shapestates.get(checked), camoShape, BooleanOp.OR);
		}
		locked = false;
		for (Direction dir : checked) {
			if (dir != null) {
				shape = Shapes.join(shape, boundingBoxes[dir.ordinal()], BooleanOp.OR);
			}
		}
		while (locked) {
			System.out.println("Bounding box collided with another block's bounding box!");
		}
		shapestates.put(checked, shape);
		if (shape == null) {
			return Shapes.empty();
		}
		return Shapes.join(camoShape, shape, BooleanOp.OR);
	}

	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(EnumConnectType.UP, EnumConnectType.DOWN, EnumConnectType.NORTH, EnumConnectType.EAST, EnumConnectType.SOUTH, EnumConnectType.WEST);
		builder.add(ElectrodynamicsBlockStates.HAS_SCAFFOLDING);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		if (level.getBlockState(pos).getBlock() instanceof BlockScaffold) {
			return true;
		}
		return super.canSurvive(state, level, pos);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState superState = super.getStateForPlacement(context);
		Level world = context.getPlayer().level;
		boolean set = false;
		if (world.getBlockState(context.getClickedPos()).getBlock() instanceof BlockScaffold) {
			superState = superState.setValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING, true);
			set = true;
		} else {
			superState = superState.setValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING, false);
		}
		if (!world.isClientSide() && set) {
			world.setBlockAndUpdate(context.getClickedPos(), Blocks.AIR.defaultBlockState());
		}
		return superState;
	}

	@Override
	public void onPlace(BlockState newState, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onPlace(newState, level, pos, oldState, isMoving);
		if (newState.hasProperty(ElectrodynamicsBlockStates.HAS_SCAFFOLDING) && oldState.hasProperty(ElectrodynamicsBlockStates.HAS_SCAFFOLDING)) {
			newState = newState.setValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING, oldState.getValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING));
		}
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.isEmpty()) {
			return InteractionResult.FAIL;
		}

		if (stack.getItem() instanceof BlockItem blockitem && level.getBlockEntity(pos) instanceof GenericConnectTile connect) {

			BlockPlaceContext newCtx = new BlockPlaceContext(player, hand, stack, hit);

			if (blockitem.getBlock() instanceof BlockScaffold scaffold) {
				if (!state.getValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING)) {
					if (!level.isClientSide) {
						if (!player.isCreative()) {
							stack.shrink(1);
							player.setItemInHand(hand, stack);
						}
						state = state.setValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING, true);
						level.setBlockAndUpdate(pos, state);
						connect.setScaffoldBlock(scaffold.getStateForPlacement(newCtx));
						level.playSound(null, pos, blockitem.getBlock().defaultBlockState().getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
					}
					return InteractionResult.CONSUME;
				}

			} else if (!(blockitem.getBlock() instanceof AbstractConnectBlock) && state.getValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING)) {
				if (connect.isCamoAir()) {
					if (!level.isClientSide) {
						connect.setCamoBlock(blockitem.getBlock().getStateForPlacement(newCtx));
						if (!player.isCreative()) {
							stack.shrink(1);
							player.setItemInHand(hand, stack);
						}
						level.playSound(null, pos, blockitem.getBlock().defaultBlockState().getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
						level.getChunkSource().getLightEngine().checkBlock(pos);
					}
					return InteractionResult.CONSUME;
				} else if (!connect.getCamoBlock().is(blockitem.getBlock())) {
					if (!level.isClientSide) {
						if (!player.isCreative()) {
							if (!player.addItem(new ItemStack(connect.getCamoBlock().getBlock()))) {
								level.addFreshEntity(new ItemEntity(player.level, (int) player.getX(), (int) player.getY(), (int) player.getZ(), new ItemStack(connect.getCamoBlock().getBlock())));
							}
							stack.shrink(1);
							player.setItemInHand(hand, stack);
						}
						connect.setCamoBlock(blockitem.getBlock().getStateForPlacement(newCtx));
						level.playSound(null, pos, blockitem.getBlock().defaultBlockState().getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
					}
					return InteractionResult.CONSUME;

				}
			}

		}

		return InteractionResult.FAIL;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {
		ArrayList<ItemStack> drops = new ArrayList<>(super.getDrops(state, builder));
		if (state.getValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING) && builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY) instanceof GenericConnectTile connect) {
			drops.add(new ItemStack(connect.getScaffoldBlock().getBlock()));
			if (!connect.isCamoAir()) {
				drops.add(new ItemStack(connect.getCamoBlock().getBlock()));
			}
		}
		return drops;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
		if (!state.getValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING)) {
			return true;
		}

		if (level.getBlockEntity(pos) instanceof GenericConnectTile connect) {
			if (connect.isCamoAir()) {
				return connect.getScaffoldBlock().getBlock().propagatesSkylightDown(connect.getScaffoldBlock(), level, pos);
			}
			return connect.getCamoBlock().getBlock().propagatesSkylightDown(connect.getCamoBlock(), level, pos);
		}

		return true;
	}

	@Override
	public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
		if (!state.getValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING)) {
			return 0;
		}

		if (level.getBlockEntity(pos) instanceof GenericConnectTile connect) {
			if (connect.isCamoAir()) {
				return connect.getScaffoldBlock().getBlock().getLightEmission(connect.getScaffoldBlock(), level, pos);
			}
			return connect.getCamoBlock().getBlock().getLightEmission(connect.getCamoBlock(), level, pos);
		}

		return 0;
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		if (!state.getValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING)) {
			return super.getVisualShape(state, level, pos, context);
		}
		if (level.getBlockEntity(pos) instanceof GenericConnectTile connect) {
			if (connect.isCamoAir()) {
				return connect.getScaffoldBlock().getBlock().getVisualShape(connect.getScaffoldBlock(), level, pos, context);
			}
			return connect.getCamoBlock().getBlock().getVisualShape(connect.getCamoBlock(), level, pos, context);
		}
		return super.getVisualShape(state, level, pos, context);
	}

	@Override
	public void onRotate(ItemStack stack, BlockPos pos, Player player) {
		Level level = player.level;
		if (level.isClientSide()) {
			return;
		}
		if (level.getBlockEntity(pos) instanceof GenericConnectTile connect) {

			if (!connect.isCamoAir()) {
				Block camo = connect.getCamoBlock().getBlock();

				connect.setCamoBlock(Blocks.AIR.defaultBlockState());

				if (!player.isCreative()) {
					if (!player.addItem(new ItemStack(camo))) {
						level.addFreshEntity(new ItemEntity(player.level, (int) player.getX(), (int) player.getY(), (int) player.getZ(), new ItemStack(camo)));
					}
				}

				return;
			} else if (!connect.isScaffoldAir()) {
				Block scaffold = connect.getScaffoldBlock().getBlock();

				connect.setScaffoldBlock(Blocks.AIR.defaultBlockState());

				level.setBlockAndUpdate(pos, level.getBlockState(pos).setValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING, false));

				if (!player.isCreative()) {
					if (!player.addItem(new ItemStack(scaffold))) {
						level.addFreshEntity(new ItemEntity(player.level, (int) player.getX(), (int) player.getY(), (int) player.getZ(), new ItemStack(scaffold)));
					}
				}

				return;
			}

		}

		super.onRotate(stack, pos, player);

	}

}
