package electrodynamics.common.recipe.recipeutils;

import java.util.ArrayList;
import java.util.stream.Stream;

import com.google.gson.JsonElement;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;

public class CountableIngredient extends Ingredient {

    private Ingredient INGREDIENT;
    private int STACK_SIZE;

    protected CountableIngredient(Ingredient ingredient, int stackCount) {
	super(Stream.empty());
	INGREDIENT = ingredient;
	STACK_SIZE = stackCount;

    }

    public static CountableIngredient deserialize(JsonElement jsonElement) {
	Ingredient itemIngredient = Ingredient.deserialize(jsonElement);
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

    public static CountableIngredient read(PacketBuffer buffer) {
	Ingredient ingredient = Ingredient.read(buffer);
	int stackSize = buffer.readInt();
	return new CountableIngredient(ingredient, stackSize);
    }

    public void writeStack(PacketBuffer buffer) {
	INGREDIENT.write(buffer);
	buffer.writeInt(STACK_SIZE);
    }

    public ArrayList<ItemStack> fetchCountedStacks() {
	ArrayList<ItemStack> countedStacks = new ArrayList<>();
	for (ItemStack itemStack : INGREDIENT.getMatchingStacks()) {
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
