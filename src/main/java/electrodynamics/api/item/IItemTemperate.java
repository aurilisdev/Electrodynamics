package electrodynamics.api.item;

import electrodynamics.prefab.item.TemperateItemProperties;
import electrodynamics.prefab.utilities.NBTUtils;
import net.minecraft.item.ItemStack;

public interface IItemTemperate {

	public static void setTemperature(ItemStack stack, double amount) {
		stack.getOrCreateTag().putDouble(NBTUtils.TEMPERATURE, amount);
	}

	public static double getTemperature(ItemStack stack) {
		return stack.getOrCreateTag().getDouble(NBTUtils.TEMPERATURE);
	}

	/**
	 * Decreases the temperature of the item and returns the amount the temperature actually decreased
	 * 
	 * @param stack:   The item being cooled
	 * @param amount:  The amount to decrease the temperature by. This value should always be positive
	 * @param debug:   If this should be simulated or not
	 * @param minTemp: The minimum temperature the item should be cooled to
	 * @return The actual amount the item was cooled
	 */
	default double loseHeat(ItemStack stack, double amount, double minTemp, boolean debug) {
		if (!stack.hasTag() || amount < 0) {
			return 0;
		}

		double currTemp = getTemperature(stack);

		double room = currTemp - minTemp;

		if (room < 0) {
			return 0;
		}

		double taken = room > amount ? amount : room;

		if (!debug) {
			setTemperature(stack, currTemp - taken);
		}
		return taken;
	}

	/**
	 * Increased the temperature of the item and returns the new temperature
	 * 
	 * @param stack:  The item being heated
	 * @param amount: The amount to heat the item by. This value should always be positive
	 * @param debug:  If this should be simulated or not
	 * @return The new temperature of the item
	 */
	default double recieveHeat(ItemStack stack, double amount, boolean debug) {

		double currTemp = getTemperature(stack);

		if (amount < 0) {
			return currTemp;
		}

		double newTemp = currTemp + amount;

		if (!debug) {
			setTemperature(stack, newTemp);
		}
		return newTemp;
	}

	TemperateItemProperties getTemperteProperties();

}