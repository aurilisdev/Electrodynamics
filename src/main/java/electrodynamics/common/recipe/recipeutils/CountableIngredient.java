package electrodynamics.common.recipe.recipeutils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.IngredientType;

public class CountableIngredient extends Ingredient {

    public static final Codec<CountableIngredient> CODEC = RecordCodecBuilder.create(
            //
            instance -> instance.group(
                    //
                    Ingredient.CODEC_NONEMPTY.fieldOf("ingreident").forGetter(instance0 -> instance0),
                    //
                    Codec.INT.fieldOf("count").forGetter(instance0 -> instance0.stackSize)

            )
                    //
                    .apply(instance, (ing, count) -> new CountableIngredient(ing, count))

    //
    );

    public static final Codec<List<CountableIngredient>> LIST_CODEC = CODEC.listOf();

    private int stackSize;

    @Nullable
    private ItemStack[] countedItems;

    public CountableIngredient(Ingredient ingredient, int stackCount) {
        super(Stream.of(ingredient.values));
        stackSize = stackCount;

    }

    @Override
    public boolean test(ItemStack stack) {
        return super.test(stack) && stackSize >= stack.getCount();
    }

    @Override
    public ItemStack[] getItems() {
        if (countedItems == null) {
            ItemStack[] items = super.getItems();
            for (ItemStack item : items) {
                item.setCount(stackSize);
            }
        }
        return countedItems;
    }

    @Override
    public IngredientType<?> getType() {
        return ElectrodynamicsRecipeInit.COUNTABLE_INGREDIENT_TYPE.get();
    }

    public int getStackSize() {
        return stackSize;
    }

    @Override
    public String toString() {
        return getItems()[0].toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CountableIngredient otherIng) {

            return otherIng.stackSize == stackSize && super.equals(obj);

        }
        return false;
    }

    public static CountableIngredient read(FriendlyByteBuf buffer) {
        Ingredient ingredient = Ingredient.fromNetwork(buffer);
        int stackSize = buffer.readInt();
        return new CountableIngredient(ingredient, stackSize);
    }

    public static List<CountableIngredient> readList(FriendlyByteBuf buffer) {
        int length = buffer.readInt();
        List<CountableIngredient> ings = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            ings.add(read(buffer));
        }
        return ings;
    }

    public void writeStack(FriendlyByteBuf buffer) {
        toNetwork(buffer);
        buffer.writeInt(stackSize);
    }

    public static void writeList(FriendlyByteBuf buffer, List<CountableIngredient> list) {
        buffer.writeInt(list.size());
        for (CountableIngredient ing : list) {
            ing.writeStack(buffer);
        }
    }

}
