package electrodynamics.common.block.connect;

import java.util.HashSet;

import electrodynamics.api.network.cable.IRefreshableConductor;
import electrodynamics.api.network.cable.type.IConductor;
import electrodynamics.common.block.connect.util.AbstractRefreshingConnectBlock;
import electrodynamics.common.block.connect.util.EnumConnectType;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.block.subtype.SubtypeWire.WireType;
import electrodynamics.common.tile.network.electric.TileLogisticalWire;
import electrodynamics.common.tile.network.electric.TileWire;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.Scheduler;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class BlockWire extends AbstractRefreshingConnectBlock {

	public static final HashSet<Block> WIRESET = new HashSet<>();

	public final SubtypeWire wire;

	public BlockWire(SubtypeWire wire) {
		super(Properties.of(wire.wireType.material).sound(wire.wireType.soundType).strength(0.15f).dynamicShape(), wire.wireType.radius);
		this.wire = wire;
		WIRESET.add(this);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	@Override
	public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return !wire.wireClass.fireProof;
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
	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		if (wire.wireClass.fireProof) {
			return 0;
		}

		return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) ? 0 : 150;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		if (wire.wireClass.fireProof) {
			return 0;
		}

		return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) ? 0 : 400;
	}

	@Override
	public void onCaughtFire(BlockState state, Level world, BlockPos pos, Direction face, LivingEntity igniter) {
		super.onCaughtFire(state, world, pos, face, igniter);
		Scheduler.schedule(5, () -> world.setBlockAndUpdate(pos, ElectrodynamicsBlocks.getBlock(SubtypeWire.getWireForType(WireType.UNINSULATED, wire.material)).defaultBlockState()));
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return ((BlockWire) state.getBlock()).wire.wireType == WireType.LOGISTICAL ? new TileLogisticalWire(pos, state) : new TileWire(pos, state);
	}

	@Override
	public BlockState refreshConnections(BlockEntity tile, BlockState state, Direction dir) {
		EnumProperty<EnumConnectType> property = FACING_TO_PROPERTY_MAP.get(dir);
		if (tile instanceof IConductor) {
			return state.setValue(property, EnumConnectType.WIRE);
		} else if (ElectricityUtils.isElectricReceiver(tile, dir.getOpposite())) {
			return state.setValue(property, EnumConnectType.INVENTORY);
		} else if (state.hasProperty(property)) {
			return state.setValue(property, EnumConnectType.NONE);
		} else {
			return state;
		}
	}

	@Override
	public IRefreshableConductor getCableIfValid(BlockEntity tile) {
		if (tile instanceof IConductor conductor) {
			return conductor;
		}
		return null;
	}

}
