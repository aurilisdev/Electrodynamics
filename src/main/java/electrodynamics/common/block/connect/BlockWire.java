package electrodynamics.common.block.connect;

import java.util.HashSet;

import electrodynamics.api.References;
import electrodynamics.api.network.cable.IRefreshableCable;
import electrodynamics.api.network.cable.type.IConductor;
import electrodynamics.common.block.connect.util.AbstractRefreshingConnectBlock;
import electrodynamics.common.block.connect.util.EnumConnectType;
import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.block.subtype.SubtypeWire.InsulationMaterial;
import electrodynamics.common.block.subtype.SubtypeWire.WireClass;
import electrodynamics.common.block.subtype.SubtypeWire.WireColor;
import electrodynamics.common.tile.network.electric.GenericTileWire;
import electrodynamics.common.tile.network.electric.TileLogisticalWire;
import electrodynamics.common.tile.network.electric.TileWire;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.Scheduler;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class BlockWire extends AbstractRefreshingConnectBlock {

	public static final HashSet<Block> WIRES = new HashSet<>();

	public final SubtypeWire wire;

	public BlockWire(SubtypeWire wire) {
		super(Properties.of(wire.insulation.material).sound(wire.insulation.soundType).strength(0.15f).dynamicShape().noOcclusion(), wire.insulation.radius);
		this.wire = wire;

		if (wire.wireClass != WireClass.LOGISTICAL) {
			WIRES.add(this);
		}
	}

	@Override
	public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return !wire.insulation.fireProof;
	}

	@Override
	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
		TileWire tile = (TileWire) worldIn.getBlockEntity(pos);
		if (tile != null && tile.getNetwork() != null && tile.getNetwork().getActiveTransmitted() > 0) {
			int shockVoltage = tile.wire.insulation.shockVoltage;
			if (shockVoltage == 0 || tile.getNetwork().getActiveVoltage() > shockVoltage) {
				ElectricityUtils.electrecuteEntity(entityIn, TransferPack.joulesVoltage(tile.getNetwork().getActiveTransmitted(), tile.getNetwork().getActiveVoltage()));
			}
		}
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

		ItemStack stack = player.getItemInHand(hand);

		if (stack.isEmpty() || state.isAir()) {
			return InteractionResult.FAIL;
		}

		Item item = stack.getItem();

		boolean isServerSide = !level.isClientSide;

		BlockPlaceContext newCtx = new BlockPlaceContext(player, hand, stack, hit);

		if (item == Items.SHEARS) {

			if (wire.insulation == InsulationMaterial.CERAMIC) {

				if (isServerSide) {

					Block newWire = ElectrodynamicsBlocks.getBlock(SubtypeWire.getWire(wire.conductor, InsulationMaterial.WOOL, wire.wireClass, WireColor.BLACK));

					handleDataCopyAndSet(newWire.getStateForPlacement(newCtx), level, pos, player, hand, stack, state);

					handlePlayerItemDrops(player, ElectrodynamicsItems.ITEM_CERAMICINSULATION.get());

					stack.hurtAndBreak(1, player, pl -> {
					});

					level.playSound(null, pos, SoundEvents.TUFF_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
				}

				return InteractionResult.CONSUME;

			}

			if (wire.insulation == InsulationMaterial.WOOL) {

				if (isServerSide) {

					Block newWire = ElectrodynamicsBlocks.getBlock(SubtypeWire.getWire(wire.conductor, InsulationMaterial.BARE, WireClass.BARE, WireColor.NONE));

					handleDataCopyAndSet(newWire.getStateForPlacement(newCtx), level, pos, player, hand, stack, state);

					handlePlayerItemDrops(player, ElectrodynamicsItems.ITEM_INSULATION.get());

					if (wire.wireClass == WireClass.LOGISTICAL) {

						handlePlayerItemDrops(player, Items.REDSTONE);

					}

					stack.hurtAndBreak(1, player, pl -> {
					});

					level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);

				}

				return InteractionResult.CONSUME;

			}

			return InteractionResult.FAIL;

		}

		if (item == ElectrodynamicsItems.ITEM_INSULATION.get()) {

			if (wire.insulation == InsulationMaterial.BARE) {

				if (isServerSide) {

					Block newWire = ElectrodynamicsBlocks.getBlock(SubtypeWire.getWire(wire.conductor, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLACK));

					handleDataCopyAndSet(newWire.getStateForPlacement(newCtx), level, pos, player, hand, stack, state);

					stack.shrink(1);

					player.setItemInHand(hand, stack);

					level.playSound(null, pos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
				}

				return InteractionResult.CONSUME;

			}

			return InteractionResult.FAIL;

		}

		if (item == ElectrodynamicsItems.ITEM_CERAMICINSULATION.get() && wire.insulation == InsulationMaterial.WOOL && wire.wireClass == WireClass.INSULATED) {

			if (isServerSide) {

				Block newWire = ElectrodynamicsBlocks.getBlock(SubtypeWire.getWire(wire.conductor, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BLACK));

				handleDataCopyAndSet(newWire.getStateForPlacement(newCtx), level, pos, player, hand, stack, state);

				stack.shrink(1);

				player.setItemInHand(hand, stack);

				level.playSound(null, pos, SoundEvents.TUFF_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
			}

			return InteractionResult.CONSUME;

		}

		if (item.builtInRegistryHolder().is(Tags.Items.DUSTS_REDSTONE) && wire.insulation == InsulationMaterial.WOOL && wire.wireClass == WireClass.INSULATED) {

			if (isServerSide) {

				Block newWire = ElectrodynamicsBlocks.getBlock(SubtypeWire.getWire(wire.conductor, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLACK));

				handleDataCopyAndSet(newWire.getStateForPlacement(newCtx), level, pos, player, hand, stack, state);

				stack.shrink(1);

				player.setItemInHand(hand, stack);

				level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
			}

			return InteractionResult.CONSUME;

		}

		WireColor dyeColor = WireColor.getColorFromDye(item);

		if (dyeColor != null && (wire.wireClass == WireClass.INSULATED || wire.wireClass == WireClass.THICK || wire.wireClass == WireClass.CERAMIC || wire.wireClass == WireClass.LOGISTICAL)) {

			if (isServerSide) {

				Block newWire = ElectrodynamicsBlocks.getBlock(SubtypeWire.getWire(wire.conductor, wire.insulation, wire.wireClass, dyeColor));

				handleDataCopyAndSet(newWire.getStateForPlacement(newCtx), level, pos, player, hand, stack, state);

				stack.shrink(1);

				player.setItemInHand(hand, stack);

				level.playSound(null, pos, SoundEvents.DYE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
			}

			return InteractionResult.CONSUME;

		}

		return super.use(state, level, pos, player, hand, hit);
	}

	private void handleDataCopyAndSet(BlockState newWire, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack, BlockState oldWire) {
		BlockState curCamo = Blocks.AIR.defaultBlockState();
		BlockState curScaffold = Blocks.AIR.defaultBlockState();
		BlockEntity entity = level.getBlockEntity(pos);
		if (entity != null && entity instanceof GenericTileWire generic) {
			curCamo = generic.getCamoBlock();
			curScaffold = generic.getScaffoldBlock();
		}
		newWire = Block.updateFromNeighbourShapes(newWire, level, pos);
		newWire = newWire.setValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING, oldWire.getValue(ElectrodynamicsBlockStates.HAS_SCAFFOLDING));
		level.setBlockAndUpdate(pos, newWire);
		if (level.getBlockEntity(pos) instanceof GenericTileWire generic) {
			generic.camoflaugedBlock.set(curCamo);
			if (!curScaffold.isAir()) {
				generic.scaffoldBlock.set(curScaffold);
			}
		}
	}

	private void handlePlayerItemDrops(Player player, Item... items) {

		for (Item item : items) {

			ItemStack stack = new ItemStack(item);

			if (!player.addItem(stack)) {

				player.level.addFreshEntity(new ItemEntity(player.level, (int) player.getX(), (int) player.getY(), (int) player.getZ(), stack));

			}

		}
	}

	@Override
	public boolean isSignalSource(BlockState state) {
		return ((BlockWire) state.getBlock()).wire.wireClass.conductsRedstone;
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
	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		if (wire.insulation.fireProof) {
			return 0;
		}

		return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) ? 0 : 150;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		if (wire.insulation.fireProof) {
			return 0;
		}

		return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) ? 0 : 400;
	}

	@Override
	public void onCaughtFire(BlockState state, Level world, BlockPos pos, Direction face, LivingEntity igniter) {
		super.onCaughtFire(state, world, pos, face, igniter);
		Scheduler.schedule(5, () -> {
			SubtypeWire wire = SubtypeWire.getWire(this.wire.conductor, InsulationMaterial.BARE, WireClass.BARE, WireColor.NONE);
			if (wire == null) {
				world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			} else {
				world.setBlockAndUpdate(pos, ElectrodynamicsBlocks.getBlock(wire).defaultBlockState());
			}

		});
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileWire(pos, state);
	}

	@Override
	public BlockState refreshConnections(BlockState otherState, BlockEntity tile, BlockState state, Direction dir) {
		EnumProperty<EnumConnectType> property = FACING_TO_PROPERTY_MAP.get(dir);
		if (tile instanceof IConductor conductor) {
			if (conductor.getWireType().isDefaultColor() || wire.isDefaultColor() || conductor.getWireColor() == wire.color) {
				return state.setValue(property, EnumConnectType.WIRE);
			} else {
				return state.setValue(property, EnumConnectType.NONE);
			}
		} else if (ElectricityUtils.isElectricReceiver(tile, dir.getOpposite()) || checkRedstone(otherState)) {
			return state.setValue(property, EnumConnectType.INVENTORY);
		} else if (state.hasProperty(property)) {
			return state.setValue(property, EnumConnectType.NONE);
		} else {
			return state;
		}
	}

	private boolean checkRedstone(BlockState otherState) {
		return otherState.isSignalSource() && wire.wireClass == WireClass.LOGISTICAL;
	}

	@Override
	public IRefreshableCable getCableIfValid(BlockEntity tile) {
		if (tile instanceof IConductor conductor && (conductor.getWireType().isDefaultColor() || wire.isDefaultColor() || conductor.getWireColor() == wire.color)) {
			return conductor;
		}
		return null;
	}

	@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = References.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
	private static class ColorHandlerInternal {

		@SubscribeEvent
		public static void registerColoredBlocks(RegisterColorHandlersEvent.Block event) {
			WIRES.forEach(block -> event.register((state, level, pos, tintIndex) -> {
				if (tintIndex == 0) {
					return ((BlockWire) block).wire.color.color;
				} else {
					return 0xFFFFFFFF;
				}
			}, block));
		}
	}

}
