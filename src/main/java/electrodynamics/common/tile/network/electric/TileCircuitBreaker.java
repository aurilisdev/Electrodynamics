package electrodynamics.common.tile.network.electric;

import org.jetbrains.annotations.NotNull;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileCircuitBreaker extends GenericTile {

	private boolean recievedRedstoneSignal = false;

	public TileCircuitBreaker(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_CIRCUITBREAKER.get(), worldPosition, blockState);
		addComponent(new ComponentDirection(this));
		addComponent(new ComponentElectrodynamic(this).receivePower(this::receivePower).relativeOutput(Direction.SOUTH).relativeInput(Direction.NORTH));
	}

	// will not transfer power if is recieving redstone signal, voltage exceeds recieving end voltage, or if current exceeds recieving
	// end current if recieving end is wire
	public TransferPack receivePower(TransferPack transfer, boolean debug) {

		if (recievedRedstoneSignal) {
			return TransferPack.EMPTY;
		}

		Direction output = BlockEntityUtils.getRelativeSide(this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection(), Direction.SOUTH);

		BlockEntity tile = level.getBlockEntity(worldPosition.relative(output));

		if(tile == null) {
			return TransferPack.EMPTY;
		}
		
		return tile.getCapability(ElectrodynamicsCapabilities.ELECTRODYNAMIC, output.getOpposite()).map(cap -> {

			if(transfer.getVoltage() <= 0 && debug) {
				TransferPack accepted = cap.receivePower(transfer, debug);

				if (accepted.getJoules() > 0) {
					return TransferPack.joulesVoltage(accepted.getJoules() + transfer.getJoules() * (1.0 - Constants.CIRCUITBREAKER_EFFICIENCY), transfer.getVoltage());
				} else {
					return TransferPack.EMPTY;
				}
			}
			
			if (cap.getVoltage() < transfer.getVoltage()) {
				return TransferPack.EMPTY;
			}

			if (tile instanceof GenericTileWire wire && wire.electricNetwork != null) {

				if (wire.electricNetwork.networkMaxTransfer < transfer.getAmps()) {
					return TransferPack.EMPTY;
				}

				TransferPack accepted = cap.receivePower(transfer, debug);

				if (accepted.getJoules() > 0) {
					return TransferPack.joulesVoltage(accepted.getJoules() + transfer.getJoules() * (1.0 - Constants.CIRCUITBREAKER_EFFICIENCY), transfer.getVoltage());
				} else {
					return TransferPack.EMPTY;
				}

			} else {
				return TransferPack.EMPTY;
			}

		}).orElse(TransferPack.EMPTY);
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
		recievedRedstoneSignal = level.hasNeighborSignal(getBlockPos());
	}

}
