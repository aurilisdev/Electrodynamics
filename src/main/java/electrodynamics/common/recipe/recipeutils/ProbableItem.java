package electrodynamics.common.recipe.recipeutils;

import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;

public class ProbableItem {

	private Item ITEM;
	private int MAX_AMOUNT;
	//0: 0% chance
	//1: 100% chance
	private double CHANCE;
	
	public ProbableItem(Item item, int count, double chance) {
		ITEM = item;
		MAX_AMOUNT = count;
		setChance(chance);
	}
	
	public ProbableItem(ItemStack stack, double chance) {
		ITEM = stack.getItem();
		MAX_AMOUNT = stack.getCount();
		setChance(chance);
	}
	
	public ItemStack getFullStack() {
		return new ItemStack(ITEM, MAX_AMOUNT);
	}
	
	private void setChance(double chance) {
		if(chance > 1) {
			chance = 1;
		} else if(chance < 0) {
			chance = 0;
		}
		CHANCE = chance;
	}
	
	public double getChance() {
		return CHANCE;
	}
	
	public ItemStack roll() {
		double random = Electrodynamics.RANDOM.nextDouble();
		if(random > (1 - CHANCE)) {
			double amount = MAX_AMOUNT * random;
			int itemCount = (int) Math.ceil(amount);
			return new ItemStack(ITEM, itemCount);
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
		for(int i = 0; i < count; i++) {
			items[i] = ProbableItem.read(buf);
		}
		return items;
	}
	
	public void write(FriendlyByteBuf buf) {
		buf.writeItem(getFullStack());
		buf.writeDouble(CHANCE);
	}
	
	public static void writeList(FriendlyByteBuf buf, ProbableItem[] items) {
		buf.writeInt(items.length);
		for(ProbableItem item : items) {
			item.write(buf);
		}
	}
	
}
