package electrodynamics.datagen.utils.recipe.builders;

import java.util.ArrayList;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import electrodynamics.common.recipe.categories.fluid2item.Fluid2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public class Fluid2ItemBuilder<T extends Fluid2ItemRecipe> extends ElectrodynamicsRecipeBuilder<T, Fluid2ItemBuilder<T>> {

    private final Fluid2ItemRecipe.Factory<T> factory;

    private final ItemStack output;

    private List<FluidStack> fluidIngredients = new ArrayList<>();
    private List<Pair<TagKey<Fluid>, Integer>> tagFluidIngredients = new ArrayList<>();

    public Fluid2ItemBuilder(Fluid2ItemRecipe.Factory<T> factory, ItemStack output, RecipeCategory category, String parent, String name, String group, double experience, int processTime, double usagePerTick) {
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
        List<FluidIngredient> ings = new ArrayList<>();
        for (FluidStack fluid : fluidIngredients) {
            ings.add(new FluidIngredient(fluid));
        }
        for (Pair<TagKey<Fluid>, Integer> pair : tagFluidIngredients) {
            ings.add(new FluidIngredient(pair.getFirst(), pair.getSecond()));
        }
        return factory.create(group, ings, output, experience, processTime, usagePerTick, itemBiproducts, fluidBiproducts, gasBiproducts);
    }

    public Fluid2ItemBuilder<T> addFluidStackInput(FluidStack stack) {
        fluidIngredients.add(stack);
        return this;
    }

    public Fluid2ItemBuilder<T> addFluidTagInput(TagKey<Fluid> tag, int count) {
        tagFluidIngredients.add(Pair.of(tag, count));
        return this;
    }

}
