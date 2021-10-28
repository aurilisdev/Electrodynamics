package electrodynamics.api.item;

import electrodynamics.prefab.item.TemperateItemProperties;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface IItemTemperate {

    static void setTemperature(ItemStack stack, double amount) {
	if (!stack.hasTag()) {
	    stack.setTag(new CompoundTag());
	}
	stack.getTag().putDouble("temperature", amount);
    }

    default TransferPack decreaseTemperature(ItemStack stack, double amount, boolean debug, double minTemp) {
	if (!stack.hasTag()) {
	    return TransferPack.EMPTY;
	}
	double currTemp = stack.getTag().getDouble("temperature");
	if (!debug && currTemp > minTemp) {
	    setTemperature(stack, currTemp - amount);
	}
	return TransferPack.temperature(amount);
    }

    default TransferPack recieveHeat(ItemStack stack, TransferPack amount, boolean debug) {
	if (!stack.hasTag()) {
	    stack.setTag(new CompoundTag());
	}
	double currTemp = stack.getTag().getDouble("temperature");
	if (!debug) {
	    setTemperature(stack, currTemp + amount.getTemperature());
	}
	return TransferPack.temperature(getTemperteProperties().temperature);
    }

    default double getTemperatureStored(ItemStack stack) {
	return stack.hasTag() ? stack.getTag().getDouble("temperature") : 0;
    }

    TemperateItemProperties getTemperteProperties();

}