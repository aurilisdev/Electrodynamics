package electrodynamics.common.tile.electricitygrid;

import org.jetbrains.annotations.NotNull;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic.LoadProfile;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
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

public class TileRelay extends GenericTile {

	private boolean recievedRedstoneSignal = false;

	private boolean isLocked = false;

	public TileRelay(BlockPos worldPos, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_RELAY.get(), worldPos, blockState);
		addComponent(new ComponentDirection(this));
		addComponent(new ComponentElectrodynamic(this).receivePower(this::receivePower).getConnectedLoad(this::getConnectedLoad).relativeOutput(Direction.SOUTH).relativeInput(Direction.NORTH).voltage(-1).getAmpacity(this::getAmpacity).getMinimumVoltage(this::getMinimumVoltage));
	}

	public TransferPack receivePower(TransferPack transfer, boolean debug) {
		if (recievedRedstoneSignal || isLocked) {
			return TransferPack.EMPTY;
		}
		Direction output = BlockEntityUtils.getRelativeSide(this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection(), Direction.SOUTH);

		BlockEntity tile = level.getBlockEntity(worldPosition.relative(output));

		if (tile == null) {
			return TransferPack.EMPTY;
		}

		return tile.getCapability(ElectrodynamicsCapabilities.ELECTRODYNAMIC, output.getOpposite()).map(cap -> {

			isLocked = true;

			TransferPack accepted = cap.receivePower(TransferPack.joulesVoltage(transfer.getJoules() * Constants.RELAY_EFFICIENCY, transfer.getVoltage()), debug);

			isLocked = false;

			if (accepted.getJoules() > 0) {
				return accepted;
			}
			return TransferPack.EMPTY;

		}).orElse(TransferPack.EMPTY);
	}

	public TransferPack getConnectedLoad(LoadProfile lastEnergy, Direction dir) {

		if (recievedRedstoneSignal || isLocked) {
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

		TransferPack load = tile.getCapability(ElectrodynamicsCapabilities.ELECTRODYNAMIC, output.getOpposite()).map(cap -> cap.getConnectedLoad(lastEnergy, output.getOpposite())).orElse(TransferPack.EMPTY);

		isLocked = false;

		return load;

	}

	public double getMinimumVoltage() {
		Direction facing = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
		if (isLocked) {
			return 0;
		}
		BlockEntity output = level.getBlockEntity(worldPosition.relative(facing));
		if (output == null) {
			return -1;
		}
		isLocked = true;
		double minimumVoltage = output.getCapability(ElectrodynamicsCapabilities.ELECTRODYNAMIC, facing).map(cap -> cap.getMinimumVoltage()).orElse(-1.0);
		isLocked = false;
		return minimumVoltage;
	}

	public double getAmpacity() {
		Direction facing = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
		if (isLocked) {
			return 0;
		}
		BlockEntity output = level.getBlockEntity(worldPosition.relative(facing));
		if (output == null) {
			return -1;
		}
		isLocked = true;
		double ampacity = output.getCapability(ElectrodynamicsCapabilities.ELECTRODYNAMIC, facing).map(cap -> cap.getAmpacity()).orElse(-1.0);
		isLocked = false;
		return ampacity;
	}

	@Override
	public void saveAdditional(@NotNull CompoundTag compound) {
		super.saveAdditional(compound);
		compound.putBoolean("hasredstonesignal", recievedRedstoneSignal);
	}

	@Override
	public void load(@NotNull CompoundTag compound) {
		super.load(compound);
		recievedRedstoneSignal = compound.getBoolean("hasredstonesignal");
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
