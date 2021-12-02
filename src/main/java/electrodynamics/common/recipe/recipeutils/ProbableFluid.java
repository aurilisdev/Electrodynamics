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

	private Fluid FLUID;
	private int MAX_AMOUNT;
	//0: 0% chance
	//1: 100% chance
	private double CHANCE;
	
	public ProbableFluid(Fluid fluid, int amount, double chance) {
		FLUID = fluid;
		MAX_AMOUNT = amount;
		setChance(chance);
	}
	
	public ProbableFluid(FluidStack stack, double chance) {
		FLUID = stack.getFluid();
		MAX_AMOUNT = stack.getAmount();
		setChance(chance);
	}
	
	public FluidStack getFullStack() {
		return new FluidStack(FLUID, MAX_AMOUNT);
	}
	
	private void setChance(double chance) {
		if(chance > 1) {
			chance = 1;
		} else if(chance < 0) {
			chance = 0;
		}
		CHANCE = chance;
	}
	
	public double getChance() {
		return CHANCE;
	}
	
	public FluidStack roll() {
		double random = Electrodynamics.RANDOM.nextDouble();
		if(random > (1 - CHANCE)) {
			double amount = MAX_AMOUNT * random;
			int fluidAmount = (int) Math.ceil(amount);
			return new FluidStack(FLUID, fluidAmount);
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
		for(int i = 0; i < count; i++) {
			fluids[i] = ProbableFluid.read(buf);
		}
		return fluids;
	}
	
	public void write(FriendlyByteBuf buf) {
		buf.writeFluidStack(getFullStack());
		buf.writeDouble(CHANCE);
	}
	
	public static void writeList(FriendlyByteBuf buf, ProbableFluid[] items) {
		buf.writeInt(items.length);
		for(ProbableFluid fluid : items) {
			fluid.write(buf);
		}
	}
	
}
