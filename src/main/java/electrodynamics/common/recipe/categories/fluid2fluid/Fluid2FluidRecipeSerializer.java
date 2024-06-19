package electrodynamics.common.recipe.categories.fluid2fluid;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import electrodynamics.common.recipe.ElectrodynamicsRecipeSerializer;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableGas;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.fluids.FluidStack;

public class Fluid2FluidRecipeSerializer<T extends Fluid2FluidRecipe> extends ElectrodynamicsRecipeSerializer<T> {

    private final Fluid2FluidRecipe.Factory<T> factory;
    private final Codec<T> codec;

    public Fluid2FluidRecipeSerializer(Fluid2FluidRecipe.Factory<T> factory) {
        this.factory = factory;
        codec = RecordCodecBuilder.create(instance -> instance.group(
                //
                Codec.STRING.fieldOf(GROUP).forGetter(instance0 -> instance0.getGroup()),
                //
                FluidIngredient.LIST_CODEC.fieldOf(FLUID_INPUTS).forGetter(instance0 -> instance0.getFluidIngredients()),
                //
                FluidStack.CODEC.fieldOf(OUTPUT).forGetter(instance0 -> instance0.getFluidRecipeOutput()),
                //
                Codec.DOUBLE.optionalFieldOf(EXPERIENCE, 0.0).forGetter(instance0 -> instance0.getXp()),
                //
                Codec.INT.fieldOf(TICKS).forGetter(instance0 -> instance0.getTicks()),
                //
                Codec.DOUBLE.fieldOf(USAGE_PER_TICK).forGetter(instance0 -> instance0.getUsagePerTick()),
                //
                ProbableItem.LIST_CODEC.optionalFieldOf(ITEM_BIPRODUCTS, null).forGetter(instance0 -> instance0.getItemBiproducts()),
                //
                ProbableFluid.LIST_CODEC.optionalFieldOf(FLUID_BIPRODUCTS, null).forGetter(instance0 -> instance0.getFluidBiproducts()),
                //
                ProbableGas.LIST_CODEC.optionalFieldOf(GAS_BIPRODUCTS, null).forGetter(instance0 -> instance0.getGasBiproducts())
        //

        )
                //
                .apply(instance, factory::create)

        );
    }

    @Override
    public Codec<T> codec() {
        return codec;
    }

    @Override
    public T fromNetwork(FriendlyByteBuf buffer) {
        String group = buffer.readUtf();
        boolean hasItemBi = buffer.readBoolean();
        boolean hasFluidBi = buffer.readBoolean();
        boolean hasGasBi = buffer.readBoolean();
        List<FluidIngredient> inputs = FluidIngredient.readList(buffer);
        FluidStack output = buffer.readFluidStack();
        double experience = buffer.readDouble();
        int ticks = buffer.readInt();
        double usagePerTick = buffer.readDouble();
        List<ProbableItem> itemBi = null;
        List<ProbableFluid> fluidBi = null;
        List<ProbableGas> gasBi = null;
        if (hasItemBi) {
            itemBi = ProbableItem.readList(buffer);
        }
        if (hasFluidBi) {
            fluidBi = ProbableFluid.readList(buffer);

        }
        if (hasGasBi) {
            gasBi = ProbableGas.readList(buffer);
        }
        return factory.create(group, inputs, output, experience, ticks, usagePerTick, itemBi, fluidBi, gasBi);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        buffer.writeUtf(recipe.getGroup());
        buffer.writeBoolean(recipe.hasItemBiproducts());
        buffer.writeBoolean(recipe.hasFluidBiproducts());
        buffer.writeBoolean(recipe.hasGasBiproducts());
        FluidIngredient.writeList(buffer, recipe.getFluidIngredients());
        buffer.writeFluidStack(recipe.getFluidRecipeOutput());
        buffer.writeDouble(recipe.getXp());
        buffer.writeInt(recipe.getTicks());
        buffer.writeDouble(recipe.getUsagePerTick());
        if (recipe.hasItemBiproducts()) {
            ProbableItem.writeList(buffer, recipe.getItemBiproducts());
        }
        if (recipe.hasFluidBiproducts()) {
            ProbableFluid.writeList(buffer, recipe.getFluidBiproducts());
        }
        if (recipe.hasGasBiproducts()) {
            ProbableGas.writeList(buffer, recipe.getGasBiproducts());
        }
    }
}
