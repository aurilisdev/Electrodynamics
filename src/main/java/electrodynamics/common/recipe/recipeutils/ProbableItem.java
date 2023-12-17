package electrodynamics.common.recipe.recipeutils;

import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.crafting.CraftingHelper;

public class ProbableItem {

	private ItemStack item;
	// 0: 0% chance
	// 1: 100% chance
	private double chance;

	public ProbableItem(ItemStack stack, double chance) {
		item = stack;
		setChance(chance);
	}

	public ItemStack getFullStack() {
		return item;
	}

	private void setChance(double chance) {
		this.chance = chance > 1 ? 1 : chance < 0 ? 0 : chance;
	}

	public double getChance() {
		return chance;
	}

	public ItemStack roll() {
		double random = Electrodynamics.RANDOM.nextDouble();
		if (random > 1 - chance) {
			double amount = chance >= 1 ? item.getCount() : item.getCount() * random;
			int itemCount = (int) Math.ceil(amount);
			return new ItemStack(item.getItem(), itemCount);
		}
		return ItemStack.EMPTY;
	}

	public static ProbableItem deserialize(JsonObject json) {
		return new ProbableItem(CraftingHelper.getItemStack(json, false), json.get("chance").getAsDouble());
	}

	public static ProbableItem read(PacketBuffer buf) {
		return new ProbableItem(buf.readItem(), buf.readDouble());
	}

	public static ProbableItem[] readList(PacketBuffer buf) {
		int count = buf.readInt();
		ProbableItem[] items = new ProbableItem[count];
		for (int i = 0; i < count; i++) {
			items[i] = ProbableItem.read(buf);
		}
		return items;
	}

	public void write(PacketBuffer buf) {
		buf.writeItem(getFullStack());
		buf.writeDouble(chance);
	}

	public static void writeList(PacketBuffer buf, ProbableItem[] items) {
		buf.writeInt(items.length);
		for (ProbableItem item : items) {
			item.write(buf);
		}
	}

}
