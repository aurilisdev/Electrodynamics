package electrodynamics.common.tile.electricitygrid;

import electrodynamics.Electrodynamics;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic;
import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic.LoadProfile;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class TileCircuitBreaker extends GenericTile {
	public static final int TRIP_CURVE = 20;

	private boolean recievedRedstoneSignal = false;
	private boolean tripped = false;

	private int tripCurveTimer = 0;

	private boolean isLocked = false;

	public TileCircuitBreaker() {
		super(ElectrodynamicsBlockTypes.TILE_CIRCUITBREAKER.get());
		addComponent(new ComponentElectrodynamic(this, true, true).receivePower(this::receivePower).getConnectedLoad(this::getConnectedLoad).setOutputDirections(Direction.SOUTH).setInputDirections(Direction.NORTH).voltage(-1).getAmpacity(this::getAmpacity).getMinimumVoltage(this::getMinimumVoltage));
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
	}

	public void tickServer(ComponentTickable tickable) {
		if (tripCurveTimer > 0) {
			tripCurveTimer--;
			return;
		}
		if (tripped) {
			level.playSound(null, getBlockPos(), SoundEvents.IRON_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, 1.0F);
		}
		tripped = false;
	}

	// will not transfer power if is recieving redstone signal, voltage exceeds recieving end voltage, or if current exceeds recieving
	// end current if recieving end is wire
	public TransferPack receivePower(TransferPack transfer, boolean debug) {

		if (recievedRedstoneSignal || isLocked || tripped) {
			return TransferPack.EMPTY;
		}

		Direction output = BlockEntityUtils.getRelativeSide(getFacing(), Direction.SOUTH);

		TileEntity tile = level.getBlockEntity(worldPosition.relative(output));

		if (tile == null) {
			return TransferPack.EMPTY;
		}

		return tile.getCapability(ElectrodynamicsCapabilities.ELECTRODYNAMIC, output.getOpposite()).map(cap -> {

			if (cap.getMinimumVoltage() > -1 && cap.getMinimumVoltage() < transfer.getVoltage()) {
				tripped = true;
				tripCurveTimer = TRIP_CURVE;
				level.playSound(null, getBlockPos(), SoundEvents.IRON_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0F, 1.0F);

				return transfer;
			}

			if (cap.getAmpacity() > 0 && cap.getAmpacity() < transfer.getAmpsInTicks()) {
				Electrodynamics.LOGGER.info("tripped");
				tripped = true;
				tripCurveTimer = TRIP_CURVE;
				level.playSound(null, getBlockPos(), SoundEvents.IRON_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0F, 1.0F);

				return transfer;

			}

			if (debug) {

				isLocked = true;

				TransferPack accepted = cap.receivePower(TransferPack.joulesVoltage(transfer.getJoules() * Constants.CIRCUITBREAKER_EFFICIENCY, transfer.getVoltage()), debug);

				isLocked = false;

				if (accepted.getJoules() > 0) {

					return TransferPack.joulesVoltage(accepted.getJoules() / Constants.CIRCUITBREAKER_EFFICIENCY, accepted.getVoltage());

				}
				return TransferPack.EMPTY;

			}

			isLocked = true;

			TransferPack accepted = cap.receivePower(TransferPack.joulesVoltage(transfer.getJoules() * Constants.CIRCUITBREAKER_EFFICIENCY, transfer.getVoltage()), debug);

			isLocked = false;

			return TransferPack.joulesVoltage(accepted.getJoules() / Constants.CIRCUITBREAKER_EFFICIENCY, accepted.getVoltage());

		}).orElse(TransferPack.EMPTY);
	}

	public TransferPack getConnectedLoad(LoadProfile lastEnergy, Direction dir) {

		if (recievedRedstoneSignal || isLocked || tripped) {
			return TransferPack.EMPTY;
		}

		Direction output = BlockEntityUtils.getRelativeSide(getFacing(), Direction.SOUTH);

		if (dir.getOpposite() != output) {
			return TransferPack.EMPTY;
		}

		TileEntity tile = level.getBlockEntity(worldPosition.relative(output));

		if (tile == null) {
			return TransferPack.EMPTY;
		}

		isLocked = true;

		TransferPack load = tile.getCapability(ElectrodynamicsCapabilities.ELECTRODYNAMIC, output.getOpposite()).map(cap -> {

			if (cap.getMinimumVoltage() > -1 && (cap.getMinimumVoltage() < lastEnergy.lastUsage.getVoltage() || cap.getMinimumVoltage() < lastEnergy.maximumAvailable.getVoltage())) {
				tripped = true;
				tripCurveTimer = TRIP_CURVE;
				level.playSound(null, getBlockPos(), SoundEvents.IRON_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0F, 1.0F);

				return TransferPack.EMPTY;
			}

			if (cap.getAmpacity() > 0 && cap.getAmpacity() <= lastEnergy.lastUsage.getAmpsInTicks()) {

				tripped = true;
				tripCurveTimer = TRIP_CURVE;
				level.playSound(null, getBlockPos(), SoundEvents.IRON_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0F, 1.0F);

				return TransferPack.EMPTY;

			}

			LoadProfile transformed = new LoadProfile(TransferPack.joulesVoltage(lastEnergy.lastUsage.getJoules() * Constants.CIRCUITBREAKER_EFFICIENCY, lastEnergy.lastUsage.getVoltage()), TransferPack.joulesVoltage(lastEnergy.maximumAvailable.getJoules() * Constants.CIRCUITBREAKER_EFFICIENCY, lastEnergy.maximumAvailable.getVoltage()));

			isLocked = true;
			TransferPack returner = cap.getConnectedLoad(transformed, dir);
			isLocked = false;
			return TransferPack.joulesVoltage(returner.getJoules() / Constants.CIRCUITBREAKER_EFFICIENCY, returner.getVoltage());
		}).orElse(TransferPack.EMPTY);

		isLocked = false;

		return load;
	}

	public double getMinimumVoltage() {
		Direction facing = getFacing();
		if (isLocked) {
			return 0;
		}
		TileEntity output = level.getBlockEntity(worldPosition.relative(facing));
		if (output == null) {
			return -1;
		}
		isLocked = true;
		double minimumVoltage = output.getCapability(ElectrodynamicsCapabilities.ELECTRODYNAMIC, facing).map(ICapabilityElectrodynamic::getMinimumVoltage).orElse(-1.0);
		isLocked = false;
		return minimumVoltage;
	}

	public double getAmpacity() {
		Direction facing = getFacing();
		if (isLocked) {
			return 0;
		}
		TileEntity output = level.getBlockEntity(worldPosition.relative(facing));
		if (output == null) {
			return -1;
		}
		isLocked = true;
		double ampacity = output.getCapability(ElectrodynamicsCapabilities.ELECTRODYNAMIC, facing).map(ICapabilityElectrodynamic::getAmpacity).orElse(-1.0);
		isLocked = false;
		return ampacity;
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		compound = super.save(compound);
		compound.putBoolean("hasredstonesignal", recievedRedstoneSignal);
		compound.putBoolean("tripped", tripped);
		compound.putInt("timer", tripCurveTimer);
		return compound;
	}
	
	@Override
	public void load(BlockState state, CompoundNBT compound) {
		super.load(state, compound);
		recievedRedstoneSignal = compound.getBoolean("hasredstonesignal");
		tripped = compound.getBoolean("tripped");
		tripCurveTimer = compound.getInt("timer");
	}

	@Override
	public void onNeightborChanged(BlockPos neighbor, boolean blockStateTrigger) {
		if (level.isClientSide) {
			return;
		}
		recievedRedstoneSignal = level.hasNeighborSignal(getBlockPos());
		if (BlockEntityUtils.isLit(this) ^ recievedRedstoneSignal) {
			BlockEntityUtils.updateLit(this, recievedRedstoneSignal);
			if (recievedRedstoneSignal) {
				level.playSound(null, getBlockPos(), SoundEvents.IRON_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
			} else {
				level.playSound(null, getBlockPos(), SoundEvents.IRON_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
		}
	}

	@Override
	public void onPlace(BlockState oldState, boolean isMoving) {
		super.onPlace(oldState, isMoving);
		if (level.isClientSide) {
			return;
		}
		recievedRedstoneSignal = level.hasNeighborSignal(getBlockPos());
		if (BlockEntityUtils.isLit(this) ^ recievedRedstoneSignal) {
			BlockEntityUtils.updateLit(this, recievedRedstoneSignal);
			if (recievedRedstoneSignal) {
				level.playSound(null, getBlockPos(), SoundEvents.IRON_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
		}
	}
}