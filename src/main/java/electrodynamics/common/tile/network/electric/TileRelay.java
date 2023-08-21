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

public class TileRelay extends GenericTile {

	private boolean recievedRedstoneSignal = false;
	
	private boolean isLocked = false;

	public TileRelay(BlockPos worldPos, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_RELAY.get(), worldPos, blockState);
		addComponent(new ComponentDirection(this));
		addComponent(new ComponentElectrodynamic(this).receivePower(this::receivePower).relativeOutput(Direction.SOUTH).relativeInput(Direction.NORTH).voltage(-1));
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
			
			TransferPack accepted = cap.receivePower(transfer, debug);

			isLocked = false;
			
			if (accepted.getJoules() > 0) {
				return TransferPack.joulesVoltage(accepted.getJoules() + transfer.getJoules() * (1.0 - Constants.RELAY_EFFICIENCY), transfer.getVoltage());
			}
			return TransferPack.EMPTY;

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
		if (BlockEntityUtils.isLit(this) ^ recievedRedstoneSignal) {
			BlockEntityUtils.updateLit(this, recievedRedstoneSignal);
		}
	}

	@Override
	public void onPlace(BlockState oldState, boolean isMoving) {
		recievedRedstoneSignal = level.hasNeighborSignal(getBlockPos());
		if (BlockEntityUtils.isLit(this) ^ recievedRedstoneSignal) {
			BlockEntityUtils.updateLit(this, recievedRedstoneSignal);
		}
	}

}
