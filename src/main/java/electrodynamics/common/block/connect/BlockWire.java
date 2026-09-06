package electrodynamics.common.block.connect;

import java.util.HashSet;

import javax.annotation.Nullable;

import com.mojang.serialization.MapCodec;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.block.subtype.SubtypeWire.InsulationMaterial;
import electrodynamics.common.block.subtype.SubtypeWire.WireClass;
import electrodynamics.common.block.subtype.SubtypeWire.WireColor;
import electrodynamics.common.network.type.ElectricNetwork;
import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.common.tile.electricitygrid.GenericTileWire;
import electrodynamics.common.tile.electricitygrid.TileLogisticalWire;
import electrodynamics.common.tile.electricitygrid.TileWire;
import electrodynamics.common.tile.electricitygrid.transformer.TileGenericTransformer;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.common.Tags;
import voltaic.api.electricity.IInsulator;
import voltaic.api.network.cable.type.IWire;
import voltaic.api.network.cable.type.IWire.IInsulationMaterial;
import voltaic.api.network.cable.type.IWire.IWireClass;
import voltaic.api.network.cable.type.IWire.IWireMaterial;
import voltaic.common.block.connect.AbstractRefreshingConnectBlock;
import voltaic.common.block.connect.EnumConnectType;
import voltaic.common.block.states.VoltaicBlockStates;
import voltaic.prefab.utilities.Scheduler;
import voltaic.prefab.utilities.math.Color;
import voltaic.prefab.utilities.object.TransferPack;

public class BlockWire extends AbstractRefreshingConnectBlock<GenericTileWire> {

    public static final HashSet<Block> WIRES = new HashSet<>();

    public final IWire wire;

    public BlockWire(IWire wire) {
	super(wire.getInsulation().getProperties().sound(wire.getInsulation().getSoundType()).strength(0.15f)
		.dynamicShape().noOcclusion().randomTicks(), wire.getInsulation().wireRadius());
	this.wire = wire;
	if (wire.getWireClass() != WireClass.LOGISTICAL) {
	    WIRES.add(this);
	}
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	return !wire.getInsulation().fireproof();
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
	if (worldIn.getBlockEntity(pos) instanceof TileWire wire) {
	    ElectricNetwork network = wire.getNetwork();
	    if (network.getActiveTransmitted() > 0) {
		int shockVoltage = wire.getCableType().getInsulation().shockVoltage();
		if (shockVoltage == 0 || network.getActiveVoltage() > shockVoltage) {
		    ElectricityUtils.electrecuteEntity(entityIn,
			    TransferPack.joulesVoltage(network.getActiveTransmitted(), network.getActiveVoltage()));
		}
	    }
	}
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
	    Player player, InteractionHand hand, BlockHitResult hitResult) {
	if (stack.isEmpty())
	    return ItemInteractionResult.FAIL;

	BlockPlaceContext context = new BlockPlaceContext(player, hand, stack, hitResult);

	IInsulationMaterial insulation = wire.getInsulation();
	IWireMaterial material = wire.getWireMaterial();
	IWireClass wireClass = wire.getWireClass();

	if (stack.is(Items.SHEARS)) {
	    if (insulation == InsulationMaterial.CERAMIC)
		return replaceWire(SubtypeWire.getWire(material, InsulationMaterial.WOOL, wireClass, WireColor.BLACK),
			state, level, pos, player, context, () -> {
			    handlePlayerItemDrops(player, ElectrodynamicsItems.ITEM_CERAMICINSULATION.get());
			    damageHeldItem(stack, player, hand);
			}, SoundEvents.TUFF_BREAK);

	    if (insulation == InsulationMaterial.WOOL)
		return replaceWire(
			SubtypeWire.getWire(material, InsulationMaterial.BARE, WireClass.BARE, WireColor.NONE), state,
			level, pos, player, context, () -> {
			    handlePlayerItemDrops(player, SubtypeWire.getWoolFromWireColor(wire.getWireColor()));
			    if (wireClass == WireClass.LOGISTICAL) {
				handlePlayerItemDrops(player, Items.REDSTONE);
			    }
			    damageHeldItem(stack, player, hand);
			}, SoundEvents.SHEEP_SHEAR);

	    return ItemInteractionResult.FAIL;
	}

	if (stack.is(ElectrodynamicsItems.ITEM_INSULATION.get())) {
	    if (insulation != InsulationMaterial.BARE)
		return ItemInteractionResult.FAIL;

	    return replaceWire(
		    SubtypeWire.getWire(material, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLACK), state,
		    level, pos, player, context, () -> consumeHeldItem(stack, player, hand), SoundEvents.WOOL_PLACE);
	}

	if (stack.is(ElectrodynamicsItems.ITEM_CERAMICINSULATION.get()) && insulation == InsulationMaterial.WOOL
		&& wireClass == WireClass.INSULATED)
	    return replaceWire(
		    SubtypeWire.getWire(material, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BROWN),
		    state, level, pos, player, context, () -> consumeHeldItem(stack, player, hand),
		    SoundEvents.TUFF_PLACE);

	if (stack.is(Tags.Items.DUSTS_REDSTONE) && insulation == InsulationMaterial.WOOL
		&& wireClass == WireClass.INSULATED)
	    return replaceWire(
		    SubtypeWire.getWire(material, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLACK),
		    state, level, pos, player, context, () -> consumeHeldItem(stack, player, hand),
		    SoundEvents.STONE_PLACE);

	IWire.IWireColor dyeColor = WireColor.getColorFromDye(stack);

	if (dyeColor != null)
	    return replaceWire(SubtypeWire.getWire(wire.getWireMaterial(), insulation, wireClass, dyeColor), state,
		    level, pos, player, context, () -> consumeHeldItem(stack, player, hand), SoundEvents.DYE_USE);

	return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    private static ItemInteractionResult replaceWire(@Nullable BlockWire newWire, BlockState oldState, Level level,
	    BlockPos pos, Player player, BlockPlaceContext context, Runnable consume, SoundEvent dyeUse) {
	if (!level.isClientSide && newWire != null) {
	    BlockState newState = newWire.getStateForPlacement(context);

	    if (newState == null)
		return ItemInteractionResult.FAIL;

	    replaceWirePreservingData(newState, level, pos, oldState);

	    if (!player.isCreative()) {
		consume.run();
	    }

	    level.playSound(null, pos, dyeUse, SoundSource.BLOCKS, 1.0F, 1.0F);
	}

	return ItemInteractionResult.CONSUME;
    }

    private static void consumeHeldItem(ItemStack stack, Player player, InteractionHand hand) {
	stack.shrink(1);
	player.setItemInHand(hand, stack);
    }

    private static void damageHeldItem(ItemStack stack, Player player, InteractionHand hand) {
	stack.hurtAndBreak(1, player,
		hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
    }

    private static void replaceWirePreservingData(BlockState newWire, Level level, BlockPos pos, BlockState oldWire) {
	BlockState curCamo = Blocks.AIR.defaultBlockState();
	BlockState curScaffold = Blocks.AIR.defaultBlockState();
	BlockEntity entity = level.getBlockEntity(pos);
	if (entity != null && entity instanceof GenericTileWire generic) {
	    curCamo = generic.getCamoBlock();
	    curScaffold = generic.getScaffoldBlock();
	}
	newWire = Block.updateFromNeighbourShapes(newWire, level, pos);
	newWire = newWire.setValue(VoltaicBlockStates.HAS_SCAFFOLDING,
		oldWire.getValue(VoltaicBlockStates.HAS_SCAFFOLDING));
	level.setBlockAndUpdate(pos, newWire);
	if (level.getBlockEntity(pos) instanceof GenericTileWire generic) {
	    generic.camoflaugedBlock.setValue(curCamo);
	    if (!curScaffold.isAir()) {
		generic.scaffoldBlock.setValue(curScaffold);
	    }
	}
    }

    private static void handlePlayerItemDrops(Player player, Item... items) {
	for (Item item : items) {
	    ItemStack stack = new ItemStack(item);
	    if (!player.addItem(stack)) {
		player.level().addFreshEntity(new ItemEntity(player.level(), (int) player.getX(), (int) player.getY(),
			(int) player.getZ(), stack));
	    }
	}
    }

    @Override
    public boolean isSignalSource(BlockState state) {
	return ((BlockWire) state.getBlock()).wire.getWireClass().conductsRedstone();
    }

    @Override
    public int getDirectSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
	return blockState.getSignal(blockAccess, pos, side);
    }

    @Override
    public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
	BlockEntity tile = blockAccess.getBlockEntity(pos);
	if (tile instanceof TileLogisticalWire w)
	    return w.isPowered ? 15 : 0;
	return 0;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	if (wire.getInsulation().fireproof())
	    return 0;

	return state.hasProperty(VoltaicBlockStates.WATERLOGGED) && state.getValue(VoltaicBlockStates.WATERLOGGED) ? 0
		: 150;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	if (wire.getInsulation().fireproof())
	    return 0;

	return state.hasProperty(VoltaicBlockStates.WATERLOGGED) && state.getValue(VoltaicBlockStates.WATERLOGGED) ? 0
		: 400;
    }

    @Override
    public void onCaughtFire(BlockState state, Level world, BlockPos pos, Direction face, LivingEntity igniter) {
	super.onCaughtFire(state, world, pos, face, igniter);
	Scheduler.schedule(5, () -> {

	    BlockWire wire = SubtypeWire.getWire(this.wire.getWireMaterial(), InsulationMaterial.BARE, WireClass.BARE,
		    WireColor.NONE);

	    // SubtypeWire wire = SubtypeWire.getWire(this.wire.conductor,
	    // InsulationMaterial.BARE, WireClass.BARE, WireColor.NONE);
	    if (wire == null) {
		world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
	    } else {
		world.setBlockAndUpdate(pos, wire.defaultBlockState());
	    }

	});
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
	return new TileWire(pos, state);
    }

    @Override
    public EnumConnectType getConnection(BlockState otherState, @Nullable BlockEntity otherTile,
	    GenericTileWire thisConductor, Direction dir) {
	EnumConnectType connection = EnumConnectType.NONE;
	if (otherTile instanceof GenericTileWire conductor) {
	    if (conductor.getCableType().isDefaultColor() || wire.isDefaultColor()
		    || conductor.getWireColor() == wire.getWireColor()) {
		connection = EnumConnectType.WIRE;
	    } else {
		connection = EnumConnectType.NONE;
	    }
	} else if (ElectricityUtils.isElectricReceiver(otherTile, dir.getOpposite()) || checkRedstone(otherState)) {
	    connection = EnumConnectType.INVENTORY;
	}
	return connection;
    }

    private boolean checkRedstone(BlockState otherState) {
	return otherState.isSignalSource() && wire.getWireClass() == WireClass.LOGISTICAL;
    }

    @Override
    public @Nullable GenericTileWire getCableIfValid(BlockEntity tile) {
	if (tile instanceof GenericTileWire conductor && (conductor.getCableType().isDefaultColor()
		|| wire.isDefaultColor() || conductor.getWireColor() == wire.getWireColor()))
	    return conductor;
	return null;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
	if (!ElectrodynamicsConfig.INSTANCE.CONDUCTORS_BURN_SURROUNDINGS.get())
	    return;

	if (level.getBlockEntity(pos) instanceof GenericTileWire tile) {
	    ElectricNetwork network = tile.getNetwork();

	    IInsulationMaterial insulation = wire.getInsulation();

	    double voltage = network.getActiveVoltage();
	    if (voltage <= 0 || voltage <= insulation.shockVoltage() || network.getActiveTransmitted() <= 0)
		return;

	    boolean overMaxVoltage = voltage > TileGenericTransformer.MAX_VOLTAGE_CAP;

	    double wireShockVoltage = Math.max(insulation.shockVoltage(), 1);

	    BlockPos relativePos, firePos;
	    BlockState relative;

	    for (Direction dir : Direction.values()) {

		relativePos = pos.relative(dir);
		relative = level.getBlockState(relativePos);

		if (relative.isAir()) {
		    continue;
		}

		boolean isFlammable = relative.isFlammable(level, relativePos, dir);

		if (relative.getBlock() instanceof BlockWire) {
		    continue;
		}

		if (relative.getBlock() instanceof IInsulator insulator) {
		    if (overMaxVoltage && voltage > insulator.getMaximumVoltage()) {
			level.playSound(null, relativePos, insulator.getBreakingSound(), SoundSource.BLOCKS, 1.0F,
				1.0F);
			level.destroyBlock(relativePos, false);
		    }
		    continue;
		}

		if (overMaxVoltage) {
		    if (isFlammable || relative.getBlock()
			    .getExplosionResistance() < ElectrodynamicsConfig.INSTANCE.BLOCK_VAPORIZATION_HARDNESS
				    .get()) {

			level.playSound(null, relativePos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
			level.destroyBlock(relativePos, false);
		    }
		    continue;
		} else if (!isFlammable) {
		    continue;
		}

		int flamability = relative.getFlammability(level, relativePos, dir);
		if (flamability <= 0) {
		    continue;
		}

		int overvoltage = (int) Math.ceil(voltage / wireShockVoltage);
		if (flamability > overvoltage) {
		    continue;
		}

		boolean blockCaughtFire = false;

		for (Direction relDir : Direction.values()) {
		    firePos = relativePos.relative(relDir);
		    if (firePos.equals(pos) || !BaseFireBlock.canBePlacedAt(level, firePos,
			    relDir == Direction.DOWN || relDir == Direction.UP ? dir : relDir.getOpposite())) {
			continue;
		    }
		    level.setBlock(firePos, BaseFireBlock.getState(level, firePos), 11);
		    blockCaughtFire = true;
		    break;
		}

		if (blockCaughtFire) {
		    continue;
		}

		level.playSound(null, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
		level.destroyBlock(pos, false);
		break;

	    }

	}

    }

    @EventBusSubscriber(value = Dist.CLIENT, modid = Electrodynamics.ID, bus = EventBusSubscriber.Bus.MOD)
    private static class ColorHandlerInternal {

	@SubscribeEvent
	public static void registerColoredBlocks(RegisterColorHandlersEvent.Block event) {
	    WIRES.forEach(block -> event.register((state, level, pos, tintIndex) -> {
		if (tintIndex == 0)
		    return ((BlockWire) block).wire.getWireColor().getColor().color();
		return Color.WHITE.color();
	    }, block));
	}
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
	throw new UnsupportedOperationException("Need to implement CODEC");
    }

}
