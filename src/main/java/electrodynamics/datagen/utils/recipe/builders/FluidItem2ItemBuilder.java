package electrodynamics.datagen.utils.recipe.builders;

import java.util.ArrayList;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import electrodynamics.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public class FluidItem2ItemBuilder<T extends FluidItem2ItemRecipe> extends ElectrodynamicsRecipeBuilder<T, FluidItem2ItemBuilder<T>> {

    private final FluidItem2ItemRecipe.Factory<T> factory;

    private final ItemStack output;

    private List<ItemStack> itemIngredients = new ArrayList<>();
    private List<Pair<TagKey<Item>, Integer>> tagItemIngredients = new ArrayList<>();

    private List<FluidStack> fluidIngredients = new ArrayList<>();
    private List<Pair<TagKey<Fluid>, Integer>> tagFluidIngredients = new ArrayList<>();

    public FluidItem2ItemBuilder(FluidItem2ItemRecipe.Factory<T> factory, ItemStack output, RecipeCategory category, String parent, String name, String group, double experience, int processTime, double usagePerTick) {
        super(category, parent, name, group, experience, processTime, usagePerTick);
        this.factory = factory;
        this.output = output;
    }

    @Override
    public Item getResult() {
        return output.getItem();
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

        List<FluidIngredient> fluidIngs = new ArrayList<>();
        for (FluidStack fluid : fluidIngredients) {
            fluidIngs.add(new FluidIngredient(fluid));
        }
        for (Pair<TagKey<Fluid>, Integer> pair : tagFluidIngredients) {
            fluidIngs.add(new FluidIngredient(pair.getFirst(), pair.getSecond()));
        }
        return factory.create(group, itemIngs, fluidIngs, output, experience, processTime, usagePerTick, itemBiproducts, fluidBiproducts, gasBiproducts);
    }

    public FluidItem2ItemBuilder<T> addItemStackInput(ItemStack stack) {
        itemIngredients.add(stack);
        return this;
    }

    public FluidItem2ItemBuilder<T> addItemTagInput(TagKey<Item> tag, int count) {
        tagItemIngredients.add(Pair.of(tag, count));
        return this;
    }

    public FluidItem2ItemBuilder<T> addFluidStackInput(FluidStack stack) {
        fluidIngredients.add(stack);
        return this;
    }

    public FluidItem2ItemBuilder<T> addFluidTagInput(TagKey<Fluid> tag, int count) {
        tagFluidIngredients.add(Pair.of(tag, count));
        return this;
    }

}
