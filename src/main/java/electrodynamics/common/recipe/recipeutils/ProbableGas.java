package electrodynamics.common.recipe.recipeutils;

import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import electrodynamics.api.gas.GasStack;
import electrodynamics.registers.ElectrodynamicsRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class ProbableGas {

	private GasStack gas;
	// 0: 0% chance
	// 1: 100% chance
	private double chance;

	public ProbableGas(GasStack stack, double chance) {
		gas = stack;
		setChance(chance);
	}

	public GasStack getFullStack() {
		return gas;
	}

	private void setChance(double chance) {
		this.chance = chance > 1 ? 1 : chance < 0 ? 0 : chance;
	}

	public double getChance() {
		return chance;
	}

	public GasStack roll() {
		double random = Electrodynamics.RANDOM.nextDouble();
		if (random > 1 - chance) {
			double amount = chance >= 1 ? gas.getAmount() : gas.getAmount() * random;
			return new GasStack(gas.getGas(), amount, gas.getTemperature(), gas.getPressure());
		}
		return GasStack.EMPTY;
	}

	public static ProbableGas deserialize(JsonObject json) {
		ResourceLocation resourceLocation = new ResourceLocation(GsonHelper.getAsString(json, "gas"));
		GasStack gas = new GasStack(ElectrodynamicsRegistries.gasRegistry().getValue(resourceLocation), GsonHelper.getAsDouble(json, "amount"), GsonHelper.getAsDouble(json, "temp"), GsonHelper.getAsInt(json, "pressure"));
		double chance = json.get("chance").getAsDouble();
		return new ProbableGas(gas, chance);
	}

	public static ProbableGas read(FriendlyByteBuf buf) {
		return new ProbableGas(GasStack.readFromBuffer(buf), buf.readDouble());
	}

	public static ProbableGas[] readList(FriendlyByteBuf buf) {
		int count = buf.readInt();
		ProbableGas[] fluids = new ProbableGas[count];
		for (int i = 0; i < count; i++) {
			fluids[i] = ProbableGas.read(buf);
		}
		return fluids;
	}

	public void write(FriendlyByteBuf buf) {
		getFullStack().writeToBuffer(buf);
		buf.writeDouble(chance);
	}

	public static void writeList(FriendlyByteBuf buf, ProbableGas[] gases) {
		buf.writeInt(gases.length);
		for (ProbableGas fluid : gases) {
			fluid.write(buf);
		}
	}

}
