package electrodynamics.common.recipe.recipeutils;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import electrodynamics.Electrodynamics;
import electrodynamics.api.gas.GasStack;
import electrodynamics.registers.ElectrodynamicsRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class ProbableGas {

    public static final Codec<ProbableGas> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            //
            ElectrodynamicsRegistries.GAS_REGISTRY.get().byNameCodec().fieldOf("gas").forGetter(instance0 -> instance0.gas.getGas()),
            //
            Codec.DOUBLE.fieldOf("amount").forGetter(instance0 -> instance0.gas.getAmount()),
            //

            Codec.DOUBLE.fieldOf("temp").forGetter(instance0 -> instance0.gas.getTemperature()),
            //
            Codec.INT.fieldOf("pressure").forGetter(instance0 -> instance0.gas.getPressure()),
            //
            Codec.DOUBLE.fieldOf("chance").forGetter(instance0 -> instance0.chance))
            //
            .apply(instance, (gas, amt, temp, pres, chance) -> new ProbableGas(new GasStack(gas, amt, temp, pres), chance))
    //
    );

    public static final Codec<List<ProbableGas>> LIST_CODEC = CODEC.listOf();

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
        GasStack gas = new GasStack(ElectrodynamicsRegistries.GAS_REGISTRY.get().get(resourceLocation), GsonHelper.getAsDouble(json, "amount"), GsonHelper.getAsDouble(json, "temp"), GsonHelper.getAsInt(json, "pressure"));
        double chance = json.get("chance").getAsDouble();
        return new ProbableGas(gas, chance);
    }

    public static ProbableGas read(FriendlyByteBuf buf) {
        return new ProbableGas(GasStack.readFromBuffer(buf), buf.readDouble());
    }

    public static List<ProbableGas> readList(FriendlyByteBuf buf) {
        int count = buf.readInt();
        List<ProbableGas> fluids = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            fluids.add(ProbableGas.read(buf));
        }
        return fluids;
    }

    public void write(FriendlyByteBuf buf) {
        getFullStack().writeToBuffer(buf);
        buf.writeDouble(chance);
    }

    public static void writeList(FriendlyByteBuf buf, List<ProbableGas> gases) {
        buf.writeInt(gases.size());
        for (ProbableGas fluid : gases) {
            fluid.write(buf);
        }
    }

}
