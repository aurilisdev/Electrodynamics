package electrodynamics.common.recipe.recipeutils;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import electrodynamics.Electrodynamics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.neoforged.neoforge.fluids.FluidStack;

public class ProbableFluid {

    public static final Codec<ProbableFluid> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            //
            BuiltInRegistries.FLUID.byNameCodec().fieldOf("fluid").forGetter(instance0 -> instance0.fluid.getFluid()),
            //
            Codec.INT.fieldOf("amount").forGetter(instance0 -> instance0.fluid.getAmount()),
            //
            Codec.DOUBLE.fieldOf("chance").forGetter(instance0 -> instance0.chance)

    )
            //
            .apply(instance, (fluid, amt, chance) -> new ProbableFluid(new FluidStack(fluid, amt), chance))

    //
    );

    public static final Codec<List<ProbableFluid>> LIST_CODEC = CODEC.listOf();

    private FluidStack fluid;
    // 0: 0% chance
    // 1: 100% chance
    private double chance;

    public ProbableFluid(FluidStack stack, double chance) {
        fluid = stack;
        setChance(chance);
    }

    public FluidStack getFullStack() {
        return fluid;
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
            double amount = chance >= 1 ? fluid.getAmount() : fluid.getAmount() * random;
            int fluidAmount = (int) Math.ceil(amount);
            return new FluidStack(fluid, fluidAmount);
        }
        return FluidStack.EMPTY;
    }

    public static ProbableFluid deserialize(JsonObject json) {
        ResourceLocation resourceLocation = new ResourceLocation(GsonHelper.getAsString(json, "fluid"));
        FluidStack fluid = new FluidStack(BuiltInRegistries.FLUID.get(resourceLocation), GsonHelper.getAsInt(json, "amount"));
        double chance = json.get("chance").getAsDouble();
        return new ProbableFluid(fluid, chance);
    }

    public static ProbableFluid read(FriendlyByteBuf buf) {
        return new ProbableFluid(buf.readFluidStack(), buf.readDouble());
    }

    public static List<ProbableFluid> readList(FriendlyByteBuf buf) {
        int count = buf.readInt();
        List<ProbableFluid> fluids = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            fluids.add(ProbableFluid.read(buf));
        }
        return fluids;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeFluidStack(getFullStack());
        buf.writeDouble(chance);
    }

    public static void writeList(FriendlyByteBuf buf, List<ProbableFluid> items) {
        buf.writeInt(items.size());
        for (ProbableFluid fluid : items) {
            fluid.write(buf);
        }
    }

}
