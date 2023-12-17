package electrodynamics.api.item;

import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public interface IItemElectric {

	public static final String JOULES_STORED = "joules";
	public static final String JOULES_CAPACITY = "maximumcapacity";
	public static final String VOLTAGE = "voltage";
	public static final String RECEIVE_LIMIT = "receivelimit";
	public static final String EXTRACT_LIMIT = "extractlimit";
	public static final String CURRENT_BATTERY = "currentbattery";

	default double getJoulesStored(ItemStack stack) {
		return stack.getOrCreateTag().getDouble(JOULES_STORED);
	}

	public static void setEnergyStored(ItemStack stack, double amount) {
		stack.getOrCreateTag().putDouble(JOULES_STORED, amount);
	}

	default double getMaximumCapacity(ItemStack item) {
		CompoundNBT tag = item.getOrCreateTag();
		if (!tag.contains(JOULES_CAPACITY)) {
			setMaximumCapacity(item, getElectricProperties().capacity);
		}
		return tag.getDouble(JOULES_CAPACITY);
	}

	static void setMaximumCapacity(ItemStack item, double amt) {
		item.getOrCreateTag().putDouble(JOULES_CAPACITY, amt);
	}

	default double getReceiveLimit(ItemStack item) {
		CompoundNBT tag = item.getOrCreateTag();
		if (!tag.contains(RECEIVE_LIMIT)) {
			setReceiveLimit(item, getElectricProperties().receive.getJoules());
		}
		return tag.getDouble(RECEIVE_LIMIT);
	}

	static void setReceiveLimit(ItemStack stack, double amount) {
		stack.getOrCreateTag().putDouble(RECEIVE_LIMIT, amount);
	}

	default double getExtractLimit(ItemStack item) {
		CompoundNBT tag = item.getOrCreateTag();
		if (!tag.contains(EXTRACT_LIMIT)) {
			setExtractLimit(item, getElectricProperties().extract.getJoules());
		}
		return tag.getDouble(EXTRACT_LIMIT);
	}

	static void setExtractLimit(ItemStack stack, double amount) {
		stack.getOrCreateTag().putDouble(EXTRACT_LIMIT, amount);
	}

	default boolean isEnergyStorageOnly() {
		return getElectricProperties().isEnergyStorageOnly;
	}

	default boolean cannotHaveBatterySwapped() {
		return getElectricProperties().cannotHaveBatterySwapped;
	}

	default TransferPack extractPower(ItemStack stack, double amount, boolean debug) {
		if (!stack.hasTag()) {
			return TransferPack.EMPTY;
		}
		double current = stack.getTag().getDouble(JOULES_STORED);
		double extracted = Math.min(current, Math.min(getExtractLimit(stack), amount));
		if (!debug) {
			setEnergyStored(stack, current - extracted);
		}
		return TransferPack.joulesVoltage(extracted, getElectricProperties().extract.getVoltage());
	}

	default TransferPack receivePower(ItemStack stack, TransferPack amount, boolean debug) {
		if (!stack.hasTag()) {
			stack.setTag(new CompoundNBT());
		}

		double current = getJoulesStored(stack);
		double received = Math.min(amount.getJoules(), getMaximumCapacity(stack) - current);
		if (!debug) {
			if (amount.getVoltage() == getElectricProperties().receive.getVoltage() || amount.getVoltage() == -1) {
				setEnergyStored(stack, current + received);
			}
			if (amount.getVoltage() > getElectricProperties().receive.getVoltage()) {
				overVoltage(amount);
				return TransferPack.EMPTY;
			}
		}
		return TransferPack.joulesVoltage(received, amount.getVoltage());
	}

	default void overVoltage(TransferPack attempt) {
	}

	ElectricItemProperties getElectricProperties();

}