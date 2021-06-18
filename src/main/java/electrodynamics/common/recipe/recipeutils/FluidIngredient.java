package electrodynamics.common.recipe.recipeutils;

import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidIngredient extends Ingredient{

	private FluidStack FLUID_STACK;

	public FluidIngredient(FluidStack fluidStack){
		super(Stream.empty());
		this.FLUID_STACK = fluidStack;
	}
	
	public FluidIngredient(ResourceLocation resourceLocation, int amount) {
		super(Stream.empty());
		this.FLUID_STACK = new FluidStack(getFluidFromTag(resourceLocation),amount);
	}
	
	public static FluidIngredient deserialize(JsonObject jsonObject){
		
		Preconditions.checkArgument(jsonObject instanceof JsonObject, "FluidStack can only be deserialized from a JsonObject");
		try {
			if(JSONUtils.hasField(jsonObject, "fluid")) {
				ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "fluid"));
				int amount = JSONUtils.getInt(jsonObject, "amount");
				return new FluidIngredient(new FluidStack(getFluidFromTag(resourceLocation),amount));
			}else if(JSONUtils.hasField(jsonObject, "tag")) {
				ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "tag"));
				int amount = JSONUtils.getInt(jsonObject, "amount");
				return new FluidIngredient(new FluidStack(getFluidFromTag(resourceLocation),amount));
			}
		}catch(Exception e) {
			ElectrodynamicsRecipe.LOGGER.info("Invalid Fluid Type or Fluid amount entered in JSON file");
			return null;
		}
		return null;
	}

	public boolean testFluid(@Nullable FluidStack t) {
		if(t != null) {
			if(t.getAmount() < FLUID_STACK.getAmount()) {
				if(t.getFluid().isEquivalentTo(FLUID_STACK.getFluid())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static FluidIngredient read(PacketBuffer input) {
		return new FluidIngredient(input.readFluidStack());
	}
	
	public void writeStack(PacketBuffer output) {
		output.writeFluidStack(FLUID_STACK);
	}
	
	private static Fluid getFluidFromTag(ResourceLocation resourceLocation) {
		return ForgeRegistries.FLUIDS.getValue(resourceLocation);
	}
	
	public FluidStack getFluidStack() {
		return FLUID_STACK;
	}

}
