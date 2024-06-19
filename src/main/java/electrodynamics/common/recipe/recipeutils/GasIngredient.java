package electrodynamics.common.recipe.recipeutils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import electrodynamics.api.gas.Gas;
import electrodynamics.api.gas.GasStack;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.registers.ElectrodynamicsGases;
import electrodynamics.registers.ElectrodynamicsRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.IngredientType;

/**
 * An extension of the Ingredient class for Gases
 * 
 * @author skip999
 *
 */
public class GasIngredient extends Ingredient {

    public static final Codec<GasIngredient> CODEC_DIRECT_GAS = RecordCodecBuilder.create(instance ->
    //
    instance.group(

            //
            ElectrodynamicsGases.GAS_REGISTRY.byNameCodec().fieldOf("gas").forGetter(instance0 -> instance0.gas),
            //
            Codec.DOUBLE.fieldOf("amount").forGetter(instance0 -> instance0.amount),
            //
            Codec.DOUBLE.fieldOf("temp").forGetter(instance0 -> instance0.temperature),
            //
            Codec.INT.fieldOf("pressure").forGetter(instance0 -> instance0.pressure)

    //
    ).apply(instance, (gas, amount, temp, pressure) -> new GasIngredient(gas, amount, temp, pressure))
    //

    );

    public static final Codec<GasIngredient> CODEC_TAGGED_GAS = RecordCodecBuilder.create(instance ->
    //
    instance.group(

            //
            TagKey.codec(ElectrodynamicsGases.GAS_REGISTRY_KEY).fieldOf("tag").forGetter(instance0 -> instance0.tag),
            //
            Codec.DOUBLE.fieldOf("amount").forGetter(instance0 -> instance0.amount),
            //
            Codec.DOUBLE.fieldOf("temp").forGetter(instance0 -> instance0.temperature),
            //
            Codec.INT.fieldOf("pressure").forGetter(instance0 -> instance0.pressure)

    //
    ).apply(instance, (gas, amount, temp, pressure) -> new GasIngredient(gas, amount, temp, pressure))
    //

    );

    public static final Codec<GasIngredient> CODEC = ExtraCodecs.xor(CODEC_TAGGED_GAS, CODEC_DIRECT_GAS).xmap(either -> either.map(tag -> tag, gas -> gas), value -> {
        //

        if (value.tag != null) {
            return Either.left(value);
        } else if (value.gas != null) {
            return Either.right(value);
        } else {
            throw new UnsupportedOperationException("The Gas Ingredient neither has a tag nor a direct gas value defined!");
        }

    });

    public static final Codec<List<GasIngredient>> LIST_CODEC = CODEC.listOf();

    @Nonnull
    private List<GasStack> gasStacks;

    @Nullable
    private TagKey<Gas> tag;
    @Nullable
    private Gas gas;
    private double amount;
    private double temperature;
    private int pressure;

    public GasIngredient(Gas gas, double amount, double temperature, int pressure) {
        this(new GasStack(gas, amount, temperature, pressure));
    }

    public GasIngredient(GasStack gasStack) {
        super(Stream.empty());
        this.gas = gasStack.getGas();
        this.amount = gasStack.getAmount();
        this.temperature = gasStack.getTemperature();
        this.pressure = gasStack.getPressure();
    }

    public GasIngredient(List<GasStack> stacks) {
        super(Stream.empty());
        gasStacks = stacks;
        GasStack gas = getGasStack();
        this.gas = gas.getGas();
        this.amount = gas.getAmount();
        this.temperature = gas.getTemperature();
        this.pressure = gas.getPressure();
    }

    public GasIngredient(TagKey<Gas> tag, double amount, double temperature, int pressure) {
        super(Stream.empty());
        this.tag = tag;
        this.amount = amount;
        this.temperature = temperature;
        this.pressure = pressure;
    }

    @Override
    public IngredientType<?> getType() {
        return ElectrodynamicsRecipeInit.GAS_INGREDIENT_TYPE.get();
    }

    public boolean testGas(@Nullable GasStack gas, boolean checkTemperature, boolean checkPressure) {
        if (gas == null) {
            return false;
        }
        for (GasStack g : getMatchingGases()) {
            if (g.getAmount() >= gas.getAmount()) {
                if (g.isSameGas(gas)) {
                    if (!checkTemperature && !checkPressure) {
                        return true;
                    }
                    boolean sameTemp = g.isSameTemperature(gas);
                    boolean samePres = g.isSamePressure(gas);
                    if (!checkTemperature) {
                        return samePres;
                    }
                    if (checkPressure) {
                        return sameTemp && samePres;
                    }
                    return sameTemp;
                }
            }
        }
        return false;

    }

    public static GasIngredient read(FriendlyByteBuf input) {
        List<GasStack> stacks = new ArrayList<>();
        int count = input.readInt();
        for (int i = 0; i < count; i++) {
            stacks.add(GasStack.readFromBuffer(input));
        }
        return new GasIngredient(stacks);
    }

    public static List<GasIngredient> readList(FriendlyByteBuf buffer) {
        int length = buffer.readInt();
        List<GasIngredient> ings = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            ings.add(read(buffer));
        }
        return ings;
    }

    public void write(FriendlyByteBuf output) {
        gasStacks = getMatchingGases();
        output.writeInt(gasStacks.size());
        for (GasStack stack : gasStacks) {
            stack.writeToBuffer(output);
        }
    }

    public static void writeList(FriendlyByteBuf buffer, List<GasIngredient> list) {
        buffer.writeInt(list.size());
        for (GasIngredient ing : list) {
            ing.write(buffer);
        }
    }

    public GasStack getGasStack() {
        return getMatchingGases().size() < 1 ? GasStack.EMPTY : getMatchingGases().get(0);
    }

    public List<GasStack> getMatchingGases() {

        if (gasStacks == null) {
            gasStacks = new ArrayList<>();
            if (tag != null) {
                ElectrodynamicsGases.GAS_REGISTRY.getTag(tag).get().forEach(h -> {
                    gasStacks.add(new GasStack(h.value(), amount, temperature, pressure));
                });
            } else if (gas != null) {
                gasStacks.add(new GasStack(gas, amount, temperature, pressure));
            } else {
                throw new UnsupportedOperationException("Gas Ingredient has neither a gas nor a gas tag defined");
            }
        }

        return gasStacks;

    }

    @Override
    public String toString() {
        return getGasStack().toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GasIngredient ing) {
            return ing.getMatchingGases().equals(getMatchingGases()) && ing.amount == amount && ing.pressure == pressure && ing.temperature == temperature;
        }
        return false;
    }

}
