package electrodynamics.common.tile.network.electric;

import org.jetbrains.annotations.NotNull;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic.LoadProfile;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileCircuitBreaker extends GenericTile {

	public static final int TRIP_CURVE = 20;
	
	private boolean recievedRedstoneSignal = false;
	private boolean tripped = false;
	
	private int tripCurveTimer = 0;

	private boolean isLocked = false;

	public TileCircuitBreaker(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_CIRCUITBREAKER.get(), worldPosition, blockState);
		addComponent(new ComponentDirection(this));
		addComponent(new ComponentElectrodynamic(this).receivePower(this::receivePower).getConnectedLoad(this::getConnectedLoad).relativeOutput(Direction.SOUTH).relativeInput(Direction.NORTH).voltage(-1));
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
	}

	public void tickServer(ComponentTickable tickable) {
		if(tripCurveTimer > 0) {
			tripCurveTimer--;
			return;
		}
		tripped = false;
	}
	
	// will not transfer power if is recieving redstone signal, voltage exceeds recieving end voltage, or if current exceeds recieving
	// end current if recieving end is wire
	public TransferPack receivePower(TransferPack transfer, boolean debug) {

		if (recievedRedstoneSignal || isLocked || tripped) {
			return TransferPack.EMPTY;
		}

		Direction output = BlockEntityUtils.getRelativeSide(this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection(), Direction.SOUTH);

		BlockEntity tile = level.getBlockEntity(worldPosition.relative(output));

		if (tile == null) {
			return TransferPack.EMPTY;
		}

		return tile.getCapability(ElectrodynamicsCapabilities.ELECTRODYNAMIC, output.getOpposite()).map(cap -> {

			if (cap.getMinimumVoltage() > -1 && cap.getMinimumVoltage() < transfer.getVoltage()) {
				tripped = true;
				tripCurveTimer = TRIP_CURVE;
				return TransferPack.EMPTY;
			}

			if (tile instanceof GenericTileWire wire && wire.electricNetwork != null && wire.electricNetwork.networkMaxTransfer < transfer.getAmps()) {

				tripped = true;
				tripCurveTimer = TRIP_CURVE;
				
				return TransferPack.EMPTY;

			}

			if (debug) {

				isLocked = true;

				TransferPack accepted = cap.receivePower(TransferPack.joulesVoltage(transfer.getJoules() * Constants.CIRCUITBREAKER_EFFICIENCY, transfer.getVoltage()), debug);

				isLocked = false;

				if (accepted.getJoules() > 0) {
					return accepted;
				}
				return TransferPack.EMPTY;

			}

			isLocked = true;

			TransferPack accepted = cap.receivePower(TransferPack.joulesVoltage(transfer.getJoules() * Constants.CIRCUITBREAKER_EFFICIENCY, transfer.getVoltage()), debug);

			isLocked = false;

			if (accepted.getJoules() > 0) {
				return accepted;
			}
			return TransferPack.EMPTY;

		}).orElse(TransferPack.EMPTY);
	}

	public TransferPack getConnectedLoad(LoadProfile loadProfile, Direction dir) {

		if (recievedRedstoneSignal || isLocked || tripped) {
			return TransferPack.EMPTY;
		}

		Direction output = BlockEntityUtils.getRelativeSide(this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection(), Direction.SOUTH);

		if (dir.getOpposite() != output) {
			return TransferPack.EMPTY;
		}

		BlockEntity tile = level.getBlockEntity(worldPosition.relative(output));

		if (tile == null) {
			return TransferPack.EMPTY;
		}

		isLocked = true;

		TransferPack load = tile.getCapability(ElectrodynamicsCapabilities.ELECTRODYNAMIC, output.getOpposite()).map(cap -> {

			if (cap.getMinimumVoltage() > -1 && (cap.getMinimumVoltage() < loadProfile.lastUsage().getVoltage() || cap.getMinimumVoltage() < loadProfile.maximumAvailable().getVoltage())) {
				tripped = true;
				tripCurveTimer = TRIP_CURVE;
				
				return TransferPack.EMPTY;
			}

			if (tile instanceof GenericTileWire wire && wire.electricNetwork != null && wire.electricNetwork.networkMaxTransfer < loadProfile.lastUsage().getAmps()) {

				tripped = true;
				tripCurveTimer = TRIP_CURVE;
				return TransferPack.EMPTY;

			}

			return cap.getConnectedLoad(loadProfile, output);
		}).orElse(TransferPack.EMPTY);

		isLocked = false;

		return load;
	}

	@Override
	public void saveAdditional(@NotNull CompoundTag compound) {
		super.saveAdditional(compound);
		compound.putBoolean("hasredstonesignal", recievedRedstoneSignal);
		compound.putBoolean("tripped", tripped);
		compound.putInt("timer", tripCurveTimer);
	}

	@Override
	public void load(@NotNull CompoundTag compound) {
		super.load(compound);
		recievedRedstoneSignal = compound.getBoolean("hasredstonesignal");
		tripped = compound.getBoolean("tripped");
		tripCurveTimer = compound.getInt("timer");
	}

	@Override
	public void onNeightborChanged(BlockPos neighbor) {
		if (level.isClientSide) {
			return;
		}
		recievedRedstoneSignal = level.hasNeighborSignal(getBlockPos());
		if (BlockEntityUtils.isLit(this) ^ recievedRedstoneSignal) {
			BlockEntityUtils.updateLit(this, recievedRedstoneSignal);
			if (recievedRedstoneSignal) {
				level.playSound(null, getBlockPos(), SoundEvents.IRON_TRAPDOOR_OPEN, SoundSource.BLOCKS);
			} else {
				level.playSound(null, getBlockPos(), SoundEvents.IRON_TRAPDOOR_CLOSE, SoundSource.BLOCKS);
			}
			setChanged();
		}
	}

	@Override
	public void onPlace(BlockState oldState, boolean isMoving) {
		if (level.isClientSide) {
			return;
		}
		recievedRedstoneSignal = level.hasNeighborSignal(getBlockPos());
		if (BlockEntityUtils.isLit(this) ^ recievedRedstoneSignal) {
			BlockEntityUtils.updateLit(this, recievedRedstoneSignal);
			if (recievedRedstoneSignal) {
				level.playSound(null, getBlockPos(), SoundEvents.IRON_TRAPDOOR_OPEN, SoundSource.BLOCKS);
			}
			setChanged();
		}
	}

}
