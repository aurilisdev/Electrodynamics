package electrodynamics.common.recipe.recipeutils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
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
    
    public FluidIngredient(List<FluidStack> fluids) {
    	super(Stream.empty());
    	FLUID_STACKS = fluids;
    }

    public FluidIngredient(ResourceLocation resourceLocation, int amount, boolean isTag) {
		super(Stream.empty());
		if(isTag) {
			FLUID_STACKS = new ArrayList<>();
			List<Fluid> fluids = new ArrayList<>();
			for(INamedTag<Fluid> tag : FluidTags.getAllTags()) {
				if(tag.getName().equals(resourceLocation)) {
					fluids = tag.getAllElements();
					break;
				}
			}
			for(Fluid fluid : fluids) {
				FLUID_STACKS.add(new FluidStack(fluid,amount));
			}
		} else {
			FLUID_STACKS = new ArrayList<>();
			FLUID_STACKS.add(new FluidStack(ForgeRegistries.FLUIDS.getValue(resourceLocation), amount));
		}
		if(FLUID_STACKS.isEmpty()) {
			throw new UnsupportedOperationException("No fluids returned from tag" + resourceLocation);
		}
    }
    
    private FluidIngredient(ResourceLocation resourceLocation, int amount) {
    	super(Stream.empty());
    	FLUID_STACKS = new ArrayList<>();
    	for(Fluid fluid : TagCollectionManager.getManager().getFluidTags().get(resourceLocation).getAllElements()) {
    		FLUID_STACKS.add(new FluidStack(fluid,amount));
    	}
    	if(FLUID_STACKS.isEmpty()) {
    		throw new UnsupportedOperationException("No fluids returned from tag" + resourceLocation);
    	}
    }

    public static FluidIngredient deserialize(JsonObject jsonObject) {

	Preconditions.checkArgument(jsonObject != null, "FluidStack can only be deserialized from a JsonObject");
	try {
	    if (JSONUtils.hasField(jsonObject, "fluid")) {
	    	ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "fluid"));
			int amount = JSONUtils.getInt(jsonObject, "amount");
			return new FluidIngredient(resourceLocation, amount, false);
	    } else if (JSONUtils.hasField(jsonObject, "tag")) {
	    	ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "tag"));
	    	int amount = JSONUtils.getInt(jsonObject, "amount");
	    	return new FluidIngredient(resourceLocation, amount);
	    }
	} catch (Exception e) {
	    Electrodynamics.LOGGER.info(e.getMessage());
	    return null;
	}
	return null;
    }

    public boolean testFluid(@Nullable FluidStack t) {
	if (t != null) {
	    for(FluidStack fluid : FLUID_STACKS) {
	    	if (t.getAmount() <= fluid.getAmount()) {
				if (t.getFluid().isEquivalentTo(fluid.getFluid())) {
				    return true;
				}
		    }
	    }
	}
	return false;
    }

    public static FluidIngredient read(PacketBuffer input) {
    	List<FluidStack> stacks = new ArrayList<>();
    	int count = input.readInt();
    	for(int i = 0 ; i < count; i++) {
    		stacks.add(input.readFluidStack());
    	}
    	return new FluidIngredient(stacks);
    }

    public void writeStack(PacketBuffer output) {
    	output.writeInt(FLUID_STACKS.size());
    	for(FluidStack stack : FLUID_STACKS) {
    		output.writeFluidStack(stack);
    	}
    }
    
    public List<FluidStack> getMatchingFluidStacks(){
    	return FLUID_STACKS;
    }

    public FluidStack getFluidStack() {
    	return FLUID_STACKS.get(0);
    }

}
