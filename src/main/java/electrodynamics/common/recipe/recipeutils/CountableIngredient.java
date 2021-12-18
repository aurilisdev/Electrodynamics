package electrodynamics.common.recipe.recipeutils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.google.gson.JsonElement;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class CountableIngredient extends Ingredient {

	private Ingredient INGREDIENT;
	private int STACK_SIZE;

	protected CountableIngredient(Ingredient ingredient, int stackCount) {
		super(Stream.empty());
		INGREDIENT = ingredient;
		STACK_SIZE = stackCount;

	}

	public static CountableIngredient deserialize(JsonElement jsonElement) {
		Ingredient itemIngredient = Ingredient.fromJson(jsonElement);
		int count;
		try {
			count = jsonElement.getAsJsonObject().get("count").getAsInt();
			return new CountableIngredient(itemIngredient, count);
		} catch (Exception e) {
			count = 1;
			return new CountableIngredient(itemIngredient, count);
		}
	}

	public boolean testStack(ItemStack itemStack) {
		if (INGREDIENT.test(itemStack)) {
			if (itemStack.getCount() >= STACK_SIZE) {
				return true;
			}
		}
		return false;
	}

	public int getStackSize() {
		return STACK_SIZE;
	}

	public static CountableIngredient read(FriendlyByteBuf buffer) {
		Ingredient ingredient = Ingredient.fromNetwork(buffer);
		int stackSize = buffer.readInt();
		return new CountableIngredient(ingredient, stackSize);
	}

	public static CountableIngredient[] readList(FriendlyByteBuf buffer) {
		int length = buffer.readInt();
		CountableIngredient[] ings = new CountableIngredient[length];
		for (int i = 0; i < length; i++) {
			ings[i] = read(buffer);
		}
		return ings;
	}

	public void writeStack(FriendlyByteBuf buffer) {
		INGREDIENT.toNetwork(buffer);
		buffer.writeInt(STACK_SIZE);
	}

	public static void writeList(FriendlyByteBuf buffer, List<CountableIngredient> list) {
		buffer.writeInt(list.size());
		for (CountableIngredient ing : list) {
			ing.writeStack(buffer);
		}
	}

	public ArrayList<ItemStack> fetchCountedStacks() {
		ArrayList<ItemStack> countedStacks = new ArrayList<>();
		for (ItemStack itemStack : INGREDIENT.getItems()) {
			itemStack.setCount(STACK_SIZE);
			countedStacks.add(itemStack);
		}
		return countedStacks;
	}

	@Override
	public String toString() {
		return fetchCountedStacks().get(0).toString();
	}
}
