package electrodynamics.common.block.connect.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.prefab.block.GenericEntityBlockWaterloggable;
import electrodynamics.prefab.tile.types.GenericConnectTile;
import electrodynamics.registers.ElectrodynamicsItems;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
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
		if (state.getValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING)) {
			return Shapes.block();
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
			return shapestates.get(checked);
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
		return shape;
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
		} else {
			superState = superState.setValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING, false);
		}
		if (!world.isClientSide() && set) {
			world.setBlockAndUpdate(context.getClickedPos(), Blocks.AIR.defaultBlockState());
		}
		return superState;
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.isEmpty()) {
			return InteractionResult.FAIL;
		}

		if (stack.getItem() instanceof BlockItem blockitem) {

			if (blockitem.getBlock() instanceof BlockScaffold && !state.getValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING)) {
				if (!level.isClientSide) {
					stack.shrink(1);
					player.setItemInHand(hand, stack);
					state = state.setValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING, true);
					level.setBlockAndUpdate(pos, state);
					level.playSound(null, pos, blockitem.getBlock().defaultBlockState().getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
				}
				return InteractionResult.CONSUME;
			}

			BlockEntity entity = level.getBlockEntity(pos);

			if (entity instanceof GenericConnectTile connect) {

				Block currBlock = connect.getBlock();
				if (Blocks.AIR.defaultBlockState().is(currBlock)) {
					if (!level.isClientSide) {
						connect.setBlock(blockitem.getBlock());
						stack.shrink(1);
						player.setItemInHand(hand, stack);
						level.playSound(null, pos, blockitem.getBlock().defaultBlockState().getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
					}
				} else if (!(blockitem.getBlock() instanceof BlockScaffold)) {
					if (!level.isClientSide) {
						if (!player.addItem(new ItemStack(currBlock))) {
							level.addFreshEntity(new ItemEntity(player.level, (int) player.getX(), (int) player.getY(), (int) player.getZ(), new ItemStack(currBlock)));
						}
						connect.setBlock(blockitem.getBlock());
						level.playSound(null, pos, blockitem.getBlock().defaultBlockState().getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
					}

					level.getChunkSource().getLightEngine().checkBlock(pos);

				}
				return InteractionResult.CONSUME;

			}

		}

		return InteractionResult.FAIL;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {
		List<ItemStack> drops = super.getDrops(state, builder);
		if (state.getValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING)) {
			drops.add(new ItemStack(ElectrodynamicsItems.ITEM_STEELSCAFFOLD.get()));
		}
		return drops;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
		if (!state.getValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING)) {
			return true;
		}
		BlockEntity entity = level.getBlockEntity(pos);
		if (entity != null && entity instanceof GenericConnectTile connect) {
			try {
				return connect.getBlock().defaultBlockState().propagatesSkylightDown(level, pos);
			} catch (Exception e) {
				return true;
			}
		} else {
			return true;
		}
	}

	@Override
	public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
		if (!state.getValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING)) {
			return 0;
		}
		BlockEntity entity = level.getBlockEntity(pos);
		if (entity != null && entity instanceof GenericConnectTile connect) {
			try {
				return connect.getBlock().getLightEmission(state, level, pos);
			} catch (Exception e) {
				return 0;
			}
		} else {
			return 0;
		}
	}

	@Override
	public int getLightBlock(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
		// TODO Auto-generated method stub
		return super.getLightBlock(pState, pLevel, pPos);
	}

}
