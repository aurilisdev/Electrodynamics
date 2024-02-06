package electrodynamics.api.capability.types.electrodynamic;

import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * CONVENTION NOTE: A VOLTAGE OF -1 INDICATES THIS ENTITY SHOULD NOT HAVE VOLTAGE CONSIDERED WHEN INTERACTING WITH IT
 * 
 * @author AurilisDev, skip999
 *
 */
public interface ICapabilityElectrodynamic {

	/**
	 * Returns the current joules stored by this Capability
	 * 
	 * @return
	 */
	public double getJoulesStored();

	/**
	 * Returns the maximum possible joules stored by this Capability
	 * 
	 * @return
	 */
	public double getMaxJoulesStored();

	/**
	 * Sets the joules stored for this Capability
	 * 
	 * @param joules
	 */
	public void setJoulesStored(double joules);

	/**
	 * Returns the current operating voltage of this Capability
	 * 
	 * Note this value will NEVER be LESS than the minimum voltage
	 * 
	 * @return
	 */
	default double getVoltage() {
		return ElectrodynamicsCapabilities.DEFAULT_VOLTAGE;
	}

	/**
	 * Returns the minimum possible voltage this Capability may accept
	 * 
	 * A return of -1 indicates there is no minimum
	 * 
	 * @return
	 */
	default double getMinimumVoltage() {
		return getVoltage();
	}

	/**
	 * Returns the maximum possible voltage this Capability may accept
	 * 
	 * A return of -1 indicates there is no maximum
	 * 
	 * @return
	 */
	default double getMaximumVoltage() {
		return getVoltage();
	}

	/**
	 * Returns the maximum ampacity this Capability may accept
	 * 
	 * A return of -1 indicates there is no maximum
	 * 
	 * @return
	 */
	default double getAmpacity() {
		return -1;
	}

	/**
	 * Returns whether or not this Capability can receive energy
	 * 
	 * @return whether or not energy can be accepted
	 */
	public boolean isEnergyReceiver();

	/**
	 * Returns whether or not this Capability can produce energy
	 * 
	 * @return whether or not energy can be produced
	 */
	public boolean isEnergyProducer();

	/**
	 * 
	 * @param transfer: The desired energy that is to be extracted
	 * @param debug:    Whether or not this should be simulated
	 * @return The actual energy that this Capability extracted
	 */
	default TransferPack extractPower(TransferPack transfer, boolean debug) {

		if (!isEnergyProducer()) {
			return TransferPack.EMPTY;
		}

		double taken = Math.min(transfer.getJoules(), getJoulesStored());
		if (!debug && taken > 0) {
			if (transfer.getVoltage() == getVoltage()) {
				setJoulesStored(getJoulesStored() - taken);
				onChange();
			}
			if (transfer.getVoltage() > getVoltage()) {
				overVoltage(transfer);
				return TransferPack.EMPTY;
			}
		}
		return TransferPack.joulesVoltage(taken, transfer.getVoltage());
	}

	/**
	 * 
	 * @param transfer: The energy that is to be accepted
	 * @param debug:    Whether or not this should be simulated
	 * @return The energy that this Capability accepted
	 */
	default TransferPack receivePower(TransferPack transfer, boolean debug) {

		if (!isEnergyReceiver()) {
			return TransferPack.EMPTY;
		}

		double received = Math.max(0, Math.min(transfer.getJoules(), getMaxJoulesStored() - getJoulesStored()));
		if (!debug && received > 0) {
			if (transfer.getVoltage() == getVoltage() || getVoltage() == -1) {
				setJoulesStored(getJoulesStored() + received);
				onChange();
			}
			if (transfer.getVoltage() > getVoltage() && getVoltage() != -1) {
				overVoltage(transfer);
				return TransferPack.EMPTY;
			}
		}
		return TransferPack.joulesVoltage(received, transfer.getVoltage());
	}

	/**
	 * The energy that this Capability received that triggered the over-voltage condition
	 * 
	 * @param transfer
	 */
	default void overVoltage(TransferPack transfer) {
		if (this instanceof BlockEntity tile) {
			Level world = tile.getLevel();
			BlockPos pos = tile.getBlockPos();
			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			world.explode(null, pos.getX(), pos.getY(), pos.getZ(), (float) Math.log10(10 + transfer.getVoltage() / getVoltage()), ExplosionInteraction.BLOCK);
		} else if (this instanceof ComponentElectrodynamic electro) {
			BlockEntity tile = electro.getHolder();
			if (tile != null) {
				Level world = tile.getLevel();
				BlockPos pos = tile.getBlockPos();
				world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
				world.explode(null, pos.getX(), pos.getY(), pos.getZ(), (float) Math.log10(10 + transfer.getVoltage() / getVoltage()), ExplosionInteraction.BLOCK);
			}
		}
	}

	/**
	 * A hook method that is called by convention when a value on this Capabiliy changes
	 */
	void onChange();

	/**
	 * Returns the load this Capability will cause
	 * 
	 * @param lastPower: The last amount of energy this Capability received
	 * @param dir:       The direction energy is being inserted into this Capability
	 * 
	 * @return The voltage of the return is assumed to be the operating voltage of this Capability when this is called
	 */
	TransferPack getConnectedLoad(LoadProfile loadProfile, Direction dir);

	/**
	 * @param lastUsage:        represents the last known energy usage of this device
	 * @param maximumAvailable: represents the theoretical maximum amount of energy this device could receive
	 * 
	 * @author skip999
	 *
	 */
	public static final record LoadProfile(TransferPack lastUsage, TransferPack maximumAvailable) {

	}

}
