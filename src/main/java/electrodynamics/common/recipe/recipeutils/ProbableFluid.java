package electrodynamics.common.recipe.recipeutils;

import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class ProbableFluid {

    private Fluid fluid;
    private int maxCount;
    // 0: 0% chance
    // 1: 100% chance
    private double chance;

    public ProbableFluid(Fluid fluid, int amount, double chance) {
	this.fluid = fluid;
	maxCount = amount;
	setChance(chance);
    }

    public ProbableFluid(FluidStack stack, double chance) {
	fluid = stack.getFluid();
	maxCount = stack.getAmount();
	setChance(chance);
    }

    public FluidStack getFullStack() {
	return new FluidStack(fluid, maxCount);
    }

    private void setChance(double chance) {
	this.chance = chance > 1 ? 1 : chance < 0 ? 0 : chance;
    }

    public double getChance() {
	return chance;
    }

    public FluidStack roll() {
	double random = Electrodynamics.RANDOM.nextDouble();
	if (random > 1 - chance) {
	    double amount = maxCount * random;
	    int fluidAmount = (int) Math.ceil(amount);
	    return new FluidStack(fluid, fluidAmount);
	}
	return FluidStack.EMPTY;
    }

    public static ProbableFluid deserialize(JsonObject json) {
	ResourceLocation resourceLocation = new ResourceLocation(GsonHelper.getAsString(json, "fluid"));
	FluidStack fluid = new FluidStack(ForgeRegistries.FLUIDS.getValue(resourceLocation), GsonHelper.getAsInt(json, "amount"));
	double chance = json.get("chance").getAsDouble();
	return new ProbableFluid(fluid, chance);
    }

    public static ProbableFluid read(FriendlyByteBuf buf) {
	return new ProbableFluid(buf.readFluidStack(), buf.readDouble());
    }

    public static ProbableFluid[] readList(FriendlyByteBuf buf) {
	int count = buf.readInt();
	ProbableFluid[] fluids = new ProbableFluid[count];
	for (int i = 0; i < count; i++) {
	    fluids[i] = ProbableFluid.read(buf);
	}
	return fluids;
    }

    public void write(FriendlyByteBuf buf) {
	buf.writeFluidStack(getFullStack());
	buf.writeDouble(chance);
    }

    public static void writeList(FriendlyByteBuf buf, ProbableFluid[] items) {
	buf.writeInt(items.length);
	for (ProbableFluid fluid : items) {
	    fluid.write(buf);
	}
    }

}
