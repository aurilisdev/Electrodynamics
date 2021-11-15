package electrodynamics.common.recipe.recipeutils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidIngredient extends Ingredient {

	@Nonnull
    private List<FluidStack> FLUID_STACKS;

    public FluidIngredient(FluidStack fluidStack) {
    	super(Stream.empty());
    	FLUID_STACKS = new ArrayList<>();
    	FLUID_STACKS.add(fluidStack);
    }
    public FluidIngredient(List<FluidStack> fluidStack) {
    	super(Stream.empty());
    	FLUID_STACKS = fluidStack;
    }

    public FluidIngredient(ResourceLocation resourceLocation, int amount, boolean isTag) {
		super(Stream.empty());
		if(isTag) {
			ElectrodynamicsRecipe.LOGGER.info(resourceLocation.toString());
			FLUID_STACKS = new ArrayList<>();
			List<Fluid> fluids = FluidTags.getAllTags().getTag(resourceLocation).getValues();
			for(Fluid fluid : fluids) {
				FLUID_STACKS.add(new FluidStack(fluid, amount));
			}
			Electrodynamics.LOGGER.info("Fluids " + FLUID_STACKS.get(0).getRawFluid().getRegistryName());
		} else {
			List<FluidStack> fluids = new ArrayList<>();
			fluids.add(new FluidStack(ForgeRegistries.FLUIDS.getValue(resourceLocation), amount));
			FLUID_STACKS= fluids;
		}
		if(FLUID_STACKS.isEmpty()) {
			throw new UnsupportedOperationException("No fluids returned from tag " + resourceLocation);
		}
    }

    public static FluidIngredient deserialize(JsonObject jsonObject) {

	Preconditions.checkArgument(jsonObject != null, "FluidStack can only be deserialized from a JsonObject");
	try {
	    if (GsonHelper.isValidNode(jsonObject, "fluid")) {
			ResourceLocation resourceLocation = new ResourceLocation(GsonHelper.getAsString(jsonObject, "fluid"));
			int amount = GsonHelper.getAsInt(jsonObject, "amount");
			return new FluidIngredient(resourceLocation, amount, false);
	    } else if (GsonHelper.isValidNode(jsonObject, "tag")) {
	    	ResourceLocation resourceLocation = new ResourceLocation(GsonHelper.getAsString(jsonObject, "tag"));
	    	int amount = GsonHelper.getAsInt(jsonObject, "amount");
	    	return new FluidIngredient(resourceLocation, amount, true);
	    }
	} catch (Exception e) {
		ElectrodynamicsRecipe.LOGGER.info("");
		ElectrodynamicsRecipe.LOGGER.info(e.getMessage());
	    ElectrodynamicsRecipe.LOGGER.info("");
		//ElectrodynamicsRecipe.LOGGER.info("Invalid Fluid Type or Fluid amount entered in JSON file");
	    return null;
	}
	
	return null;
    }

    public boolean testFluid(@Nullable FluidStack t) {
	if (t != null) {
		for(FluidStack stack : FLUID_STACKS) {
			if(t.getAmount() <= stack.getAmount()) {
				if(t.getFluid().isSame(stack.getFluid())) {
					return true;
				}
			}
		}
	}
	return false;
    }

    public static FluidIngredient read(FriendlyByteBuf input) {
    	List<FluidStack> stacks = new ArrayList<>();
    	int count = input.readInt();
    	for(int i = 0 ; i < count; i++) {
    		stacks.add(input.readFluidStack());
    	}
    	return new FluidIngredient(stacks);
    }

    public void write(FriendlyByteBuf output) {
    	output.writeInt(FLUID_STACKS.size());
    	for(FluidStack stack : FLUID_STACKS) {
    		output.writeFluidStack(stack);
    	}
    }
    
    public List<FluidStack> getMatchingFluids(){
    	return FLUID_STACKS;
    }
    
    public FluidStack getFluidStack() {
    	return FLUID_STACKS.get(0);
    }

}
