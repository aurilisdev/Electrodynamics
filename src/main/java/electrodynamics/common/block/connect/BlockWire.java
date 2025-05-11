package electrodynamics.common.block.connect;

import java.util.HashSet;
import java.util.Random;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.block.subtype.SubtypeWire.InsulationMaterial;
import electrodynamics.common.block.subtype.SubtypeWire.WireClass;
import electrodynamics.common.block.subtype.SubtypeWire.WireColor;
import electrodynamics.common.network.type.ElectricNetwork;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.common.tile.electricitygrid.GenericTileWire;
import electrodynamics.common.tile.electricitygrid.TileLogisticalWire;
import electrodynamics.common.tile.electricitygrid.TileWire;
import electrodynamics.common.tile.electricitygrid.transformer.TileGenericTransformer;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import voltaic.api.electricity.IInsulator;
import voltaic.api.network.cable.type.IWire;
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
        super(wire.getInsulation().getProperties().sound(wire.getInsulation().getSoundType()).strength(0.15f).dynamicShape().noOcclusion().randomTicks(), wire.getInsulation().wireRadius());
        this.wire = wire;
        if (wire.getWireClass() != WireClass.LOGISTICAL) {
            WIRES.add(this);
        }
    }

	@Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return !wire.getInsulation().fireproof();
    }

    @Override
    public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        TileWire tile = (TileWire) worldIn.getBlockEntity(pos);
        if (tile != null && tile.getNetwork() != null && tile.getNetwork().getActiveTransmitted() > 0) {
            int shockVoltage = tile.wire.getInsulation().shockVoltage();
            if (shockVoltage == 0 || tile.getNetwork().getActiveVoltage() > shockVoltage) {
                ElectricityUtils.electrecuteEntity(entityIn, TransferPack.joulesVoltage(tile.getNetwork().getActiveTransmitted(), tile.getNetwork().getActiveVoltage()));
            }
        }
    }

    @Override
	public ActionResultType use(BlockState state, World level, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hitResult) {
    	ItemStack stack = player.getItemInHand(hand);
    	
        if (stack.isEmpty() || state.isAir(level, pos)) {
            return ActionResultType.FAIL;
        }

        Item item = stack.getItem();

        boolean isServerSide = !level.isClientSide;

        BlockItemUseContext newCtx = new BlockItemUseContext(player, hand, stack, hitResult);

        if (item == Items.SHEARS) {

            if (wire.getInsulation() == InsulationMaterial.CERAMIC) {

                BlockWire newWire = SubtypeWire.getWire(wire.getWireMaterial(), InsulationMaterial.WOOL, wire.getWireClass(), WireColor.BLACK);

                if (newWire == null) {
                    return ActionResultType.FAIL;
                }

                if (isServerSide) {

                    //Block newWire = ElectrodynamicsBlocks.BLOCKS_WIRE.getValue(SubtypeWire.getWire(wire.conductor, InsulationMaterial.WOOL, wire.wireClass, WireColor.BLACK));

                    handleDataCopyAndSet(newWire.getStateForPlacement(newCtx), level, pos, player, hand, stack, state);

                    if (!player.isCreative()) {

                        handlePlayerEntityItemDrops(player, ElectrodynamicsItems.ITEM_CERAMICINSULATION.get());

                        stack.hurtAndBreak(1, player, stk -> {});

                    }

                    level.playSound(null, pos, SoundEvents.GILDED_BLACKSTONE_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return ActionResultType.CONSUME;

            }

            if (wire.getInsulation() == InsulationMaterial.WOOL) {

                Block newWire = SubtypeWire.getWire(wire.getWireMaterial(), InsulationMaterial.BARE, WireClass.BARE, WireColor.NONE);

                if (newWire == null) {
                    return ActionResultType.FAIL;
                }

                if (isServerSide) {

                    //Block newWire = ElectrodynamicsBlocks.BLOCKS_WIRE.getValue(SubtypeWire.getWire(wire.conductor, InsulationMaterial.BARE, WireClass.BARE, WireColor.NONE));

                    handleDataCopyAndSet(newWire.getStateForPlacement(newCtx), level, pos, player, hand, stack, state);

                    if (!player.isCreative()) {

                        handlePlayerEntityItemDrops(player, ElectrodynamicsItems.ITEM_INSULATION.get());

                        if (wire.getWireClass() == WireClass.LOGISTICAL) {

                            handlePlayerEntityItemDrops(player, Items.REDSTONE);

                        }

                        stack.hurtAndBreak(1, player, stk -> {});

                    }

                    level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundCategory.BLOCKS, 1.0F, 1.0F);

                }

                return ActionResultType.CONSUME;

            }

            return ActionResultType.FAIL;

        }

        if (item == ElectrodynamicsItems.ITEM_INSULATION.get()) {

            if (wire.getInsulation() == InsulationMaterial.BARE) {

                Block newWire = SubtypeWire.getWire(wire.getWireMaterial(), InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLACK);

                if (newWire == null) {
                    return ActionResultType.FAIL;
                }

                if (isServerSide) {

                    //Block newWire = ElectrodynamicsBlocks.BLOCKS_WIRE.getValue(SubtypeWire.getWire(wire.conductor, InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.BLACK));

                    handleDataCopyAndSet(newWire.getStateForPlacement(newCtx), level, pos, player, hand, stack, state);

                    if (!player.isCreative()) {

                        stack.shrink(1);

                        player.setItemInHand(hand, stack);

                    }

                    level.playSound(null, pos, SoundEvents.WOOL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return ActionResultType.CONSUME;

            }

            return ActionResultType.FAIL;

        }

        if (item == ElectrodynamicsItems.ITEM_CERAMICINSULATION.get() && wire.getInsulation() == InsulationMaterial.WOOL && wire.getWireClass() == WireClass.INSULATED) {

            Block newWire = SubtypeWire.getWire(wire.getWireMaterial(), InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BROWN);

            if (newWire == null) {
                return ActionResultType.FAIL;
            }


            if (isServerSide) {

                //Block newWire = ElectrodynamicsBlocks.BLOCKS_WIRE.getValue(SubtypeWire.getWire(wire.conductor, InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.BLACK));

                handleDataCopyAndSet(newWire.getStateForPlacement(newCtx), level, pos, player, hand, stack, state);

                if (!player.isCreative()) {

                    stack.shrink(1);

                    player.setItemInHand(hand, stack);

                }

                level.playSound(null, pos, SoundEvents.GILDED_BLACKSTONE_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            return ActionResultType.CONSUME;

        }

        if (stack.getItem().is(Tags.Items.DUSTS_REDSTONE) && wire.getInsulation() == InsulationMaterial.WOOL && wire.getWireClass() == WireClass.INSULATED) {

            Block newWire = SubtypeWire.getWire(wire.getWireMaterial(), InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLACK);

            if (newWire == null) {
                return ActionResultType.FAIL;
            }

            if (isServerSide) {

                //Block newWire = ElectrodynamicsBlocks.BLOCKS_WIRE.getValue(SubtypeWire.getWire(wire.conductor, InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.BLACK));

                handleDataCopyAndSet(newWire.getStateForPlacement(newCtx), level, pos, player, hand, stack, state);

                if (!player.isCreative()) {

                    stack.shrink(1);

                    player.setItemInHand(hand, stack);

                }

                level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            return ActionResultType.CONSUME;

        }

        IWire.IWireColor dyeColor = WireColor.getColorFromDye(stack);

        if (dyeColor != null) {

            Block newWire = SubtypeWire.getWire(wire.getWireMaterial(), wire.getInsulation(), wire.getWireClass(), dyeColor);

            if (newWire == null) {
                return ActionResultType.FAIL;
            }

            if (isServerSide) {

                //Block newWire = ElectrodynamicsBlocks.BLOCKS_WIRE.getValue(SubtypeWire.getWire(wire.conductor, wire.insulation, wire.wireClass, dyeColor));

                handleDataCopyAndSet(newWire.getStateForPlacement(newCtx), level, pos, player, hand, stack, state);

                if (!player.isCreative()) {

                    stack.shrink(1);

                    player.setItemInHand(hand, stack);

                }

                level.playSound(null, pos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            return ActionResultType.CONSUME;

        }
        return super.use(state, level, pos, player, hand, hitResult);
    }

    private void handleDataCopyAndSet(BlockState newWire, World level, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, BlockState oldWire) {
        BlockState curCamo = Blocks.AIR.defaultBlockState();
        BlockState curScaffold = Blocks.AIR.defaultBlockState();
        TileEntity entity = level.getBlockEntity(pos);
        if (entity != null && entity instanceof GenericTileWire) {
        	GenericTileWire generic = (GenericTileWire) entity;
            curCamo = generic.getCamoBlock();
            curScaffold = generic.getScaffoldBlock();
        }
        newWire = Block.updateFromNeighbourShapes(newWire, level, pos);
        newWire = newWire.setValue(VoltaicBlockStates.HAS_SCAFFOLDING, oldWire.getValue(VoltaicBlockStates.HAS_SCAFFOLDING));
        level.setBlockAndUpdate(pos, newWire);
        TileEntity otherTile = level.getBlockEntity(pos);
        if (otherTile instanceof GenericTileWire) {
        	GenericTileWire generic = (GenericTileWire) otherTile;
            generic.camoflaugedBlock.setValue(curCamo);
            if (!curScaffold.isAir()) {
                generic.scaffoldBlock.setValue(curScaffold);
            }
        }
    }

    private void handlePlayerEntityItemDrops(PlayerEntity player, Item... items) {

        for (Item item : items) {

            ItemStack stack = new ItemStack(item);

            if (!player.addItem(stack)) {

                player.level.addFreshEntity(new ItemEntity(player.level, (int) player.getX(), (int) player.getY(), (int) player.getZ(), stack));

            }

        }
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return ((BlockWire) state.getBlock()).wire.getWireClass().conductsRedstone();
    }

    @Override
    public int getDirectSignal(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return blockState.getSignal(blockAccess, pos, side);
    }

    @Override
    public int getSignal(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        TileEntity tile = blockAccess.getBlockEntity(pos);
        if (tile instanceof TileLogisticalWire) {
            return ((TileLogisticalWire) tile).isPowered ? 15 : 0;
        }
        return 0;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        if (wire.getInsulation().fireproof()) {
            return 0;
        }

        return state.hasProperty(VoltaicBlockStates.WATERLOGGED) && state.getValue(VoltaicBlockStates.WATERLOGGED) ? 0 : 150;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        if (wire.getInsulation().fireproof()) {
            return 0;
        }

        return state.hasProperty(VoltaicBlockStates.WATERLOGGED) && state.getValue(VoltaicBlockStates.WATERLOGGED) ? 0 : 400;
    }

    @Override
    public void catchFire(BlockState state, World world, BlockPos pos, Direction face, LivingEntity igniter) {
        super.catchFire(state, world, pos, face, igniter);
        Scheduler.schedule(5, () -> {

            BlockWire wire = SubtypeWire.getWire(this.wire.getWireMaterial(), InsulationMaterial.BARE, WireClass.BARE, WireColor.NONE);

            //SubtypeWire wire = SubtypeWire.getWire(this.wire.conductor, InsulationMaterial.BARE, WireClass.BARE, WireColor.NONE);
            if (wire == null) {
                world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            } else {
                world.setBlockAndUpdate(pos, wire.defaultBlockState());
            }

        });
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileWire();
    }

    @Override
    public EnumConnectType getConnection(BlockState otherState, TileEntity otherTile, GenericTileWire thisConductor, Direction dir) {
        EnumConnectType connection = EnumConnectType.NONE;
        if (otherTile instanceof GenericTileWire) {
        	GenericTileWire conductor = (GenericTileWire) otherTile;
            if (conductor.getCableType().isDefaultColor() || wire.isDefaultColor() || conductor.getWireColor() == wire.getWireColor()) {
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
    public GenericTileWire getCableIfValid(TileEntity tile) {
        if (tile instanceof GenericTileWire) {
        	GenericTileWire conductor = (GenericTileWire) tile;
        	if(conductor.getCableType().isDefaultColor() || wire.isDefaultColor() || conductor.getWireColor() == wire.getWireColor()) {
        		return conductor;
        	}
        }
        return null;
    }


    @Override
    public void randomTick(BlockState state, ServerWorld level, BlockPos pos, Random random) {

        if (!ElectroConstants.CONDUCTORS_BURN_SURROUNDINGS) {
            return;
        }

        TileEntity blockentity = level.getBlockEntity(pos);
        
        if (blockentity instanceof GenericTileWire) {
        	
        	GenericTileWire tile = (GenericTileWire) blockentity;

            ElectricNetwork network = tile.getNetwork();
            
            if(network == null) {
            	return;
            }

            double voltage = network.getActiveVoltage();

            if (voltage <= 0 || voltage <= wire.getInsulation().shockVoltage() || network.getActiveTransmitted() <= 0) {
                return;
            }

            boolean overMaxVoltage = voltage > TileGenericTransformer.MAX_VOLTAGE_CAP;

            double wireShockVoltage = Math.max(wire.getInsulation().shockVoltage(), 1);

            BlockPos relativePos, firePos;
            BlockState relative;

            for (Direction dir : Direction.values()) {

                relativePos = pos.relative(dir);
                relative = level.getBlockState(relativePos);

                if (relative.isAir(level, relativePos)) {
                    continue;
                }

                boolean isFlammable = relative.isFlammable(level, relativePos, dir);

                if (relative.getBlock() instanceof BlockWire) {

                    continue;

                }
                if (relative.getBlock() instanceof IInsulator) {
                	
                	IInsulator insulator = (IInsulator) relative.getBlock();

                    if (overMaxVoltage && voltage > insulator.getMaximumVoltage()) {

                        level.playSound(null, relativePos, insulator.getBreakingSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                        level.destroyBlock(relativePos, false);

                    }

                    continue;

                }
                if (overMaxVoltage) {

                    if (isFlammable || relative.getBlock().getExplosionResistance() < ElectroConstants.BLOCK_VAPORIZATION_HARDNESS) {

                        level.playSound(null, relativePos, SoundEvents.LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
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

                int overvoltage = (int) Math.ceil((voltage / wireShockVoltage));

                if (flamability > overvoltage) {
                    continue;
                }

                boolean blockCaughtFire = false;

                for (Direction relDir : Direction.values()) {

                    firePos = relativePos.relative(relDir);

                    if (firePos.equals(pos) || !AbstractFireBlock.canBePlacedAt(level, firePos, (relDir == Direction.DOWN || relDir == Direction.UP) ? dir : relDir.getOpposite())) {
                        continue;
                    }

                    level.setBlock(firePos, AbstractFireBlock.getState(level, firePos), 11);

                    blockCaughtFire = true;

                    break;

                }

                if (blockCaughtFire) {
                    continue;
                }

                level.playSound(null, pos, SoundEvents.LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
                level.destroyBlock(pos, false);

                break;

            }

        }

    }

    @EventBusSubscriber(value = Dist.CLIENT, modid = Electrodynamics.ID, bus = EventBusSubscriber.Bus.MOD)
    private static class ColorHandlerInternal {

        @SubscribeEvent
        public static void registerColoredBlocks(ColorHandlerEvent.Block event) {
            WIRES.forEach(block -> event.getBlockColors().register((state, level, pos, tintIndex) -> {
                if (tintIndex == 0) {
                    return ((BlockWire) block).wire.getWireColor().getColor().color();
                }
                return Color.WHITE.color();
            }, block));
        }
    }


}
