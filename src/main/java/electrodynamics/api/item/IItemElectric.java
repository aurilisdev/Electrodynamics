package electrodynamics.api.item;

import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public interface IItemElectric {

    default TransferPack extractPower(ItemStack stack, double amount, boolean debug) {
	if (!stack.hasTag()) {
	    return TransferPack.EMPTY;
	}
	double current = stack.getTag().getDouble("joules");
	double extracted = Math.min(current, Math.min(getElectricProperties().extract.getJoules(), amount));
	if (!debug) {
	    IItemElectric.setEnergyStored(stack, current - extracted);
	}
	return TransferPack.joulesVoltage(extracted, getElectricProperties().extract.getVoltage());
    }

    default TransferPack receivePower(ItemStack stack, TransferPack amount, boolean debug) {
	if (!stack.hasTag()) {
	    stack.setTag(new CompoundNBT());
	}

	double current = stack.getTag().getDouble("joules");
	double received = Math.min(amount.getJoules(), getElectricProperties().capacity - current);
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

    default double getJoulesStored(ItemStack stack) {
	return stack.hasTag() ? stack.getTag().getDouble("joules") : 0;
    }

    default void overVoltage(TransferPack attempt) {
    }

    static void setEnergyStored(ItemStack stack, double amount) {
	if (!stack.hasTag()) {
	    stack.setTag(new CompoundNBT());
	}
	stack.getTag().putDouble("joules", amount);
    }

    ElectricItemProperties getElectricProperties();

}