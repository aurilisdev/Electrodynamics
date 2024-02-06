package electrodynamics.datagen.utils.recipe.builders;

import java.util.ArrayList;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import electrodynamics.common.recipe.categories.fluid2fluid.Fluid2FluidRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public class Fluid2FluidBuilder<T extends Fluid2FluidRecipe> extends ElectrodynamicsRecipeBuilder<T, Fluid2FluidBuilder<T>> {

    private final Fluid2FluidRecipe.Factory<T> factory;
    
    private final FluidStack output;

    private List<FluidStack> fluidIngredients = new ArrayList<>();
    private List<Pair<TagKey<Fluid>, Integer>> tagFluidIngredients = new ArrayList<>();

    public Fluid2FluidBuilder(Fluid2FluidRecipe.Factory<T> factory, FluidStack output, RecipeCategory category, String parent, String name, String group, double experience, int processTime, double usagePerTick) {
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
        List<FluidIngredient> ings = new ArrayList<>();
        for (FluidStack fluid : fluidIngredients) {
            ings.add(new FluidIngredient(fluid));
        }
        for (Pair<TagKey<Fluid>, Integer> pair : tagFluidIngredients) {
            ings.add(new FluidIngredient(pair.getFirst(), pair.getSecond()));
        }
        return factory.create(group, ings, output, experience, processTime, usagePerTick, itemBiproducts, fluidBiproducts, gasBiproducts);
    }

    public Fluid2FluidBuilder<T> addFluidStackInput(FluidStack stack) {
        fluidIngredients.add(stack);
        return this;
    }

    public Fluid2FluidBuilder<T> addFluidTagInput(TagKey<Fluid> tag, int count) {
        tagFluidIngredients.add(Pair.of(tag, count));
        return this;
    }

}
