package electrodynamics.datagen.utils.recipe.builders;

import java.util.ArrayList;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import electrodynamics.common.recipe.categories.item2fluid.Item2FluidRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;

public class Item2FluidBuilder<T extends Item2FluidRecipe> extends ElectrodynamicsRecipeBuilder<T, Item2FluidBuilder<T>> {

    private final Item2FluidRecipe.Factory<T> factory;

    private final FluidStack output;

    private List<ItemStack> itemIngredients = new ArrayList<>();
    private List<Pair<TagKey<Item>, Integer>> tagItemIngredients = new ArrayList<>();

    public Item2FluidBuilder(Item2FluidRecipe.Factory<T> factory, FluidStack output, RecipeCategory category, String parent, String name, String group, double experience, int processTime, double usagePerTick) {
        super(category, parent, name, group, experience, processTime, usagePerTick);
        this.factory = factory;
        this.output = output;
    }

    @Override
    public Item getResult() {
        return Items.AIR;
    }

    @Override
    public T makeRecipe() {
        List<CountableIngredient> itemIngs = new ArrayList<>();
        for (ItemStack item : itemIngredients) {
            itemIngs.add(new CountableIngredient(Ingredient.of(item), item.getCount()));
        }
        for (Pair<TagKey<Item>, Integer> pair : tagItemIngredients) {
            itemIngs.add(new CountableIngredient(Ingredient.of(pair.getFirst()), pair.getSecond()));
        }
        return factory.create(group, itemIngs, output, experience, processTime, usagePerTick, itemBiproducts, fluidBiproducts, gasBiproducts);
    }

    public Item2FluidBuilder<T> addItemStackInput(ItemStack stack) {
        itemIngredients.add(stack);
        return this;
    }

    public Item2FluidBuilder<T> addItemTagInput(TagKey<Item> tag, int count) {
        tagItemIngredients.add(Pair.of(tag, count));
        return this;
    }

}
