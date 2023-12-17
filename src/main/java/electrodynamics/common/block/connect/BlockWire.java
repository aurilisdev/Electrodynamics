package electrodynamics.common.block.connect;

import java.util.HashSet;

import electrodynamics.api.network.cable.IRefreshableCable;
import electrodynamics.api.network.cable.type.IConductor;
import electrodynamics.common.block.connect.util.AbstractRefreshingConnectBlock;
import electrodynamics.common.block.connect.util.EnumConnectType;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.block.subtype.SubtypeWire.InsulationMaterial;
import electrodynamics.common.block.subtype.SubtypeWire.WireClass;
import electrodynamics.common.tile.electricitygrid.TileLogisticalWire;
import electrodynamics.common.tile.electricitygrid.TileWire;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.Scheduler;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.ElectrodynamicsItems;
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
import net.minecraft.state.EnumProperty;
import net.minecraft.state.properties.BlockStateProperties;
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
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolType;

public class BlockWire extends AbstractRefreshingConnectBlock {
	public static final HashSet<Block> WIRES = new HashSet<>();

	public final SubtypeWire wire;

	public BlockWire(SubtypeWire wire) {
		super(wire.insulation.material.sound(wire.insulation.soundType).strength(0.15f).dynamicShape().noOcclusion().harvestTool(ToolType.PICKAXE).harvestLevel(0), wire.insulation.radius);
		this.wire = wire;

		if (wire.wireClass != WireClass.LOGISTICAL) {
			WIRES.add(this);
		}
	}
	
	@Override
	public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return !wire.insulation.fireProof;
	}

	@Override
	public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		TileWire tile = (TileWire) worldIn.getBlockEntity(pos);
		if (tile != null && tile.getNetwork() != null && tile.getNetwork().getActiveTransmitted() > 0) {
			int shockVoltage = tile.wire.insulation.shockVoltage;
			if (shockVoltage == 0 || tile.getNetwork().getActiveVoltage() > shockVoltage) {
				ElectricityUtils.electrecuteEntity(entityIn, TransferPack.joulesVoltage(tile.getNetwork().getActiveTransmitted(), tile.getNetwork().getActiveVoltage()));
			}
		}
	}

	@Override
	public ActionResultType use(BlockState state, World level, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {

		ItemStack stack = player.getItemInHand(hand);

		if (stack.isEmpty() || state.isAir()) {
			return ActionResultType.FAIL;
		}

		Item item = stack.getItem();

		boolean isServerSide = !level.isClientSide;

		BlockItemUseContext newCtx = new BlockItemUseContext(player, hand, stack, hit);

		if (item == Items.SHEARS) {

			if (wire.insulation == InsulationMaterial.CERAMIC) {

				if (isServerSide) {

					Block newWire = ElectrodynamicsBlocks.getBlock(SubtypeWire.getWire(wire.conductor, InsulationMaterial.WOOL, wire.wireClass));

					handleDataCopyAndSet(newWire.getStateForPlacement(newCtx), level, pos, player, hand, stack, state);

					if (!player.isCreative()) {

						handlePlayerItemDrops(player, ElectrodynamicsItems.ITEM_CERAMICINSULATION.get());

						stack.hurtAndBreak(1, player, pl -> {
						});

					}

					level.playSound(null, pos, SoundEvents.BASALT_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}

				return ActionResultType.CONSUME;

			}

			if (wire.insulation == InsulationMaterial.WOOL) {

				if (isServerSide) {

					Block newWire = ElectrodynamicsBlocks.getBlock(SubtypeWire.getWire(wire.conductor, InsulationMaterial.BARE, WireClass.BARE));

					handleDataCopyAndSet(newWire.getStateForPlacement(newCtx), level, pos, player, hand, stack, state);

					if (!player.isCreative()) {

						handlePlayerItemDrops(player, ElectrodynamicsItems.ITEM_INSULATION.get());

						if (wire.wireClass == WireClass.LOGISTICAL) {

							handlePlayerItemDrops(player, Items.REDSTONE);

						}

						stack.hurtAndBreak(1, player, pl -> {
						});

					}

					level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundCategory.BLOCKS, 1.0F, 1.0F);

				}

				return ActionResultType.CONSUME;

			}

			return ActionResultType.FAIL;

		}

		if (item == ElectrodynamicsItems.ITEM_INSULATION.get()) {

			if (wire.insulation == InsulationMaterial.BARE) {

				if (isServerSide) {

					Block newWire = ElectrodynamicsBlocks.getBlock(SubtypeWire.getWire(wire.conductor, InsulationMaterial.WOOL, WireClass.INSULATED));

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

		if (item == ElectrodynamicsItems.ITEM_CERAMICINSULATION.get() && wire.insulation == InsulationMaterial.WOOL && wire.wireClass == WireClass.INSULATED) {

			if (isServerSide) {

				Block newWire = ElectrodynamicsBlocks.getBlock(SubtypeWire.getWire(wire.conductor, InsulationMaterial.CERAMIC, WireClass.CERAMIC));

				handleDataCopyAndSet(newWire.getStateForPlacement(newCtx), level, pos, player, hand, stack, state);

				if (!player.isCreative()) {

					stack.shrink(1);

					player.setItemInHand(hand, stack);

				}

				level.playSound(null, pos, SoundEvents.BASALT_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}

			return ActionResultType.CONSUME;

		}

		if (item.is(Tags.Items.DUSTS_REDSTONE) && wire.insulation == InsulationMaterial.WOOL && wire.wireClass == WireClass.INSULATED) {

			if (isServerSide) {

				Block newWire = ElectrodynamicsBlocks.getBlock(SubtypeWire.getWire(wire.conductor, InsulationMaterial.WOOL, WireClass.LOGISTICAL));

				handleDataCopyAndSet(newWire.getStateForPlacement(newCtx), level, pos, player, hand, stack, state);

				if (!player.isCreative()) {

					stack.shrink(1);

					player.setItemInHand(hand, stack);

				}

				level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}

			return ActionResultType.CONSUME;

		}

		return super.use(state, level, pos, player, hand, hit);
	}
	
	private void handleDataCopyAndSet(BlockState newWire, World level, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, BlockState oldWire) {
		
		newWire = Block.updateFromNeighbourShapes(newWire, level, pos);
		
		level.setBlockAndUpdate(pos, newWire);
		
	}

	private void handlePlayerItemDrops(PlayerEntity player, Item... items) {

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
	public int getDirectSignal(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
		return blockState.getSignal(blockAccess, pos, side);
	}

	@Override
	public int getSignal(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
		TileEntity tile = blockAccess.getBlockEntity(pos);
		if (tile instanceof TileLogisticalWire) {
			TileLogisticalWire w = (TileLogisticalWire) tile;
			return w.isPowered ? 15 : 0;
		}
		return 0;
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		if (wire.insulation.fireProof) {
			return 0;
		}

		return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) ? 0 : 150;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		if (wire.insulation.fireProof) {
			return 0;
		}

		return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) ? 0 : 400;
	}

	@Override
	public void catchFire(BlockState state, World world, BlockPos pos, Direction face, LivingEntity igniter) {
		super.catchFire(state, world, pos, face, igniter);
		Scheduler.schedule(5, () -> {
			SubtypeWire wire = SubtypeWire.getWire(this.wire.conductor, InsulationMaterial.BARE, WireClass.BARE);
			if (wire == null) {
				world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			} else {
				world.setBlockAndUpdate(pos, ElectrodynamicsBlocks.getBlock(wire).defaultBlockState());
			}

		});
	}
	
	@Override
	public TileEntity newBlockEntity(IBlockReader reader) {
		return new TileWire();
	}

	@Override
	public BlockState refreshConnections(BlockState otherState, TileEntity tile, BlockState state, Direction dir) {
		EnumProperty<EnumConnectType> property = FACING_TO_PROPERTY_MAP.get(dir);
		if (tile instanceof IConductor) {
			return state.setValue(property, EnumConnectType.WIRE);
		}
		if (ElectricityUtils.isElectricReceiver(tile, dir.getOpposite()) || checkRedstone(otherState)) {
			return state.setValue(property, EnumConnectType.INVENTORY);
		}
		if (state.hasProperty(property)) {
			return state.setValue(property, EnumConnectType.NONE);
		}
		return state;
	}

	private boolean checkRedstone(BlockState otherState) {
		return otherState.isSignalSource() && wire.wireClass == WireClass.LOGISTICAL;
	}

	@Override
	public IRefreshableCable getCableIfValid(TileEntity tile) {
		if (tile instanceof IConductor) {
			return (IConductor) tile;
		}
		return null;
	}

}
