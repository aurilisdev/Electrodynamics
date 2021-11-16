package electrodynamics.common.recipe.recipeutils;

import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidIngredient extends Ingredient {

    private FluidStack FLUID_STACK;

    public FluidIngredient(FluidStack fluidStack) {
	super(Stream.empty());
	FLUID_STACK = fluidStack;
    }

    public FluidIngredient(ResourceLocation resourceLocation, int amount) {
	super(Stream.empty());
	FLUID_STACK = new FluidStack(getFluidFromTag(resourceLocation), amount);
    }

    public static FluidIngredient deserialize(JsonObject jsonObject) {

	Preconditions.checkArgument(jsonObject != null, "FluidStack can only be deserialized from a JsonObject");
	try {
	    if (GsonHelper.isValidNode(jsonObject, "fluid")) {
		ResourceLocation resourceLocation = new ResourceLocation(GsonHelper.getAsString(jsonObject, "fluid"));
		int amount = GsonHelper.getAsInt(jsonObject, "amount");
		return new FluidIngredient(new FluidStack(getFluidFromTag(resourceLocation), amount));
	    } else if (GsonHelper.isValidNode(jsonObject, "tag")) {
		ResourceLocation resourceLocation = new ResourceLocation(GsonHelper.getAsString(jsonObject, "tag"));
		int amount = GsonHelper.getAsInt(jsonObject, "amount");
		return new FluidIngredient(new FluidStack(getFluidFromTag(resourceLocation), amount));
	    }
	} catch (Exception e) {
	    ElectrodynamicsRecipe.LOGGER.info("Invalid Fluid Type or Fluid amount entered in JSON file");
	}
	return null;
    }

    public boolean testFluid(@Nullable FluidStack t) {
	if (t != null) {
	    if (t.getAmount() < FLUID_STACK.getAmount()) {
		if (t.getFluid().isSame(FLUID_STACK.getFluid())) {
		    return true;
		}
	    }
	}
	return false;
    }

    public static FluidIngredient read(FriendlyByteBuf input) {
	return new FluidIngredient(input.readFluidStack());
    }

    public void writeStack(FriendlyByteBuf output) {
	output.writeFluidStack(FLUID_STACK);
    }

    private static Fluid getFluidFromTag(ResourceLocation resourceLocation) {
	return ForgeRegistries.FLUIDS.getValue(resourceLocation);
    }

    public FluidStack getFluidStack() {
	return FLUID_STACK;
    }

}
