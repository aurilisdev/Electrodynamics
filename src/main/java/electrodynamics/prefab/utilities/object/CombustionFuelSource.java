package electrodynamics.prefab.utilities.object;

import java.util.List;

import com.google.gson.JsonObject;

import net.minecraft.fluid.Fluid;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import voltaic.common.recipe.recipeutils.FluidIngredient;

public class CombustionFuelSource {

	public static final String FLUID_KEY = "fluid_tag";
	public static final String USAGE_AMOUNT = "usage_per_burn";
	public static final String POWER_MULTIPLIER = "power_multiplier";

	public static final CombustionFuelSource EMPTY = new CombustionFuelSource(FluidTags.createOptional(new ResourceLocation("air")), 0, 0);

	private FluidIngredient fuel;
	private double powerMultiplier;
	private final INamedTag<Fluid> tag;

	private CombustionFuelSource(INamedTag<Fluid> tag, int usageAmount, double powerMultiplier) {
		fuel = new FluidIngredient(tag, usageAmount);
		this.powerMultiplier = powerMultiplier;
		this.tag = tag;
	}

	public double getPowerMultiplier() {
		return powerMultiplier;
	}

	public boolean isFuelSource(FluidStack stack) {
		return fuel.testFluid(stack);
	}

	public List<FluidStack> getFuels() {
		return fuel.getMatchingFluids();
	}

	public INamedTag<Fluid> getTag() {
		return tag;
	}

	public int getFuelUsage() {
		return fuel.getFluidStack().getAmount();
	}

	public boolean isEmpty() {
		return this == EMPTY;
	}

	public static CombustionFuelSource fromJson(JsonObject json) {
		INamedTag<Fluid> tag = FluidTags.createOptional(new ResourceLocation(json.get(FLUID_KEY).getAsString()));
		return new CombustionFuelSource(tag, json.get(USAGE_AMOUNT).getAsInt(), json.get(POWER_MULTIPLIER).getAsDouble());
	}

	public static JsonObject toJson(INamedTag<Fluid> fluid, int usageAmount, double powerMultiplier) {
		JsonObject json = new JsonObject();
		json.addProperty(FLUID_KEY, fluid.getName().toString());
		json.addProperty(USAGE_AMOUNT, usageAmount);
		json.addProperty(POWER_MULTIPLIER, powerMultiplier);
		return json;
	}

	public void writeToBuffer(PacketBuffer buffer) {
		buffer.writeUtf(fuel.tag.getName().toString());
		buffer.writeInt(fuel.getFluidStack().getAmount());
		buffer.writeDouble(powerMultiplier);
	}

	public static CombustionFuelSource readFromBuffer(PacketBuffer buffer) {
		INamedTag<Fluid> tag = FluidTags.createOptional(new ResourceLocation(buffer.readUtf()));
		return new CombustionFuelSource(tag, buffer.readInt(), buffer.readDouble());
	}

}