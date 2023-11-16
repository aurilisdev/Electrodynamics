package electrodynamics.common.tile.electricitygrid;

import org.jetbrains.annotations.NotNull;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic;
import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic.LoadProfile;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileCurrentRegulator extends GenericTile {

	private boolean isLocked = false;

	public TileCurrentRegulator(BlockPos worldPos, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_CURRENTREGULATOR.get(), worldPos, blockState);
		addComponent(new ComponentElectrodynamic(this, true, true).receivePower(this::receivePower).getConnectedLoad(this::getConnectedLoad).setOutputDirections(Direction.SOUTH).setInputDirections(Direction.NORTH).voltage(-1).getAmpacity(this::getAmpacity).getMinimumVoltage(this::getMinimumVoltage));
	}

	public TransferPack receivePower(TransferPack transfer, boolean debug) {

		if (isLocked) {
			return TransferPack.EMPTY;
		}

		Direction output = BlockEntityUtils.getRelativeSide(getFacing(), Direction.SOUTH);

		BlockEntity tile = level.getBlockEntity(worldPosition.relative(output));

		if (tile == null) {
			return TransferPack.EMPTY;
		}

		return tile.getCapability(ElectrodynamicsCapabilities.ELECTRODYNAMIC, output.getOpposite()).map(cap -> {

			isLocked = true;

			TransferPack accepted = cap.receivePower(TransferPack.joulesVoltage(transfer.getJoules() * Constants.CURRENTREGULATOR_EFFICIENCY, transfer.getVoltage()), debug);

			isLocked = false;

			TransferPack adjusted = TransferPack.joulesVoltage(accepted.getJoules() / Constants.CURRENTREGULATOR_EFFICIENCY, accepted.getVoltage());

			double ampacityInTicks = cap.getAmpacity();

			if (ampacityInTicks < 0) {
				return adjusted;
			}

			double currentInTicks = adjusted.getAmpsInTicks();

			if (currentInTicks > ampacityInTicks) {

				adjusted = TransferPack.ampsVoltage(ampacityInTicks, adjusted.getVoltage());

			}

			return adjusted;

		}).orElse(TransferPack.EMPTY);
	}

	public TransferPack getConnectedLoad(LoadProfile lastEnergy, Direction dir) {

		if (isLocked) {
			return TransferPack.EMPTY;
		}

		Direction output = BlockEntityUtils.getRelativeSide(getFacing(), Direction.SOUTH);

		if (dir.getOpposite() != output) {
			return TransferPack.EMPTY;
		}

		BlockEntity tile = level.getBlockEntity(worldPosition.relative(output));

		if (tile == null) {
			return TransferPack.EMPTY;
		}

		isLocked = true;

		TransferPack load = tile.getCapability(ElectrodynamicsCapabilities.ELECTRODYNAMIC, output.getOpposite()).map(cap -> {

			LoadProfile transformed = new LoadProfile(TransferPack.joulesVoltage(lastEnergy.lastUsage().getJoules() * Constants.CIRCUITBREAKER_EFFICIENCY, lastEnergy.lastUsage().getVoltage()), TransferPack.joulesVoltage(lastEnergy.maximumAvailable().getJoules() * Constants.CIRCUITBREAKER_EFFICIENCY, lastEnergy.maximumAvailable().getVoltage()));

			isLocked = true;

			TransferPack returner = cap.getConnectedLoad(transformed, dir);

			isLocked = false;

			TransferPack adjusted = TransferPack.joulesVoltage(returner.getJoules() / Constants.CIRCUITBREAKER_EFFICIENCY, returner.getVoltage());

			double ampacityInTicks = cap.getAmpacity();

			if (ampacityInTicks < 0) {
				return adjusted;
			}

			double currentInTicks = adjusted.getAmpsInTicks();

			if (currentInTicks > ampacityInTicks) {

				adjusted = TransferPack.ampsVoltage(ampacityInTicks, adjusted.getVoltage());

			}

			return adjusted;
		}).orElse(TransferPack.EMPTY);

		isLocked = false;

		return load;
	}

	public double getMinimumVoltage() {
		Direction facing = getFacing();
		if (isLocked) {
			return 0;
		}
		BlockEntity output = level.getBlockEntity(worldPosition.relative(facing));
		if (output == null) {
			return -1;
		}
		isLocked = true;
		double minimumVoltage = output.getCapability(ElectrodynamicsCapabilities.ELECTRODYNAMIC, facing).map(@NotNull ICapabilityElectrodynamic::getMinimumVoltage).orElse(-1.0);
		isLocked = false;
		return minimumVoltage;
	}

	public double getAmpacity() {
		Direction facing = getFacing();
		if (isLocked) {
			return 0;
		}
		BlockEntity output = level.getBlockEntity(worldPosition.relative(facing));
		if (output == null) {
			return -1;
		}
		isLocked = true;
		double ampacity = output.getCapability(ElectrodynamicsCapabilities.ELECTRODYNAMIC, facing).map(@NotNull ICapabilityElectrodynamic::getAmpacity).orElse(-1.0);
		isLocked = false;
		return ampacity;
	}

}
