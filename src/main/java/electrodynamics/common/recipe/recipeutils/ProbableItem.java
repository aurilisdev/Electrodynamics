package electrodynamics.common.recipe.recipeutils;

import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;

public class ProbableItem {

	private Item item;
	private int maxCount;
	// 0: 0% chance
	// 1: 100% chance
	private double chance;

	public ProbableItem(Item item, int count, double chance) {
		this.item = item;
		maxCount = count;
		setChance(chance);
	}

	public ProbableItem(ItemStack stack, double chance) {
		item = stack.getItem();
		maxCount = stack.getCount();
		setChance(chance);
	}

	public ItemStack getFullStack() {
		return new ItemStack(item, maxCount);
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
			double amount = maxCount * random;
			int itemCount = (int) Math.ceil(amount);
			return new ItemStack(item, itemCount);
		}
		return ItemStack.EMPTY;
	}

	public static ProbableItem deserialize(JsonObject json) {
		return new ProbableItem(CraftingHelper.getItemStack(json, false), json.get("chance").getAsDouble());
	}

	public static ProbableItem read(FriendlyByteBuf buf) {
		return new ProbableItem(buf.readItem(), buf.readDouble());
	}

	public static ProbableItem[] readList(FriendlyByteBuf buf) {
		int count = buf.readInt();
		ProbableItem[] items = new ProbableItem[count];
		for (int i = 0; i < count; i++) {
			items[i] = ProbableItem.read(buf);
		}
		return items;
	}

	public void write(FriendlyByteBuf buf) {
		buf.writeItem(getFullStack());
		buf.writeDouble(chance);
	}

	public static void writeList(FriendlyByteBuf buf, ProbableItem[] items) {
		buf.writeInt(items.length);
		for (ProbableItem item : items) {
			item.write(buf);
		}
	}

}
