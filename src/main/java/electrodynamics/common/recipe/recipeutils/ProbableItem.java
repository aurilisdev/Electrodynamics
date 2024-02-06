package electrodynamics.common.recipe.recipeutils;

import java.util.ArrayList;
import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import electrodynamics.Electrodynamics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class ProbableItem {

    public static final Codec<ProbableItem> CODEC = RecordCodecBuilder.create(instance ->
    //
    instance.group(
            //
            ItemStack.CODEC.fieldOf("item").forGetter(instance0 -> instance0.item),
            //
            Codec.DOUBLE.fieldOf("chance").forGetter(instance0 -> instance0.chance)

    )
            //
            .apply(instance, ProbableItem::new)

    );

    public static final Codec<List<ProbableItem>> LIST_CODEC = CODEC.listOf();

    private ItemStack item;
    // 0: 0% chance
    // 1: 100% chance
    private double chance;

    public ProbableItem(ItemStack stack, double chance) {
        item = stack;
        setChance(chance);
    }

    public ItemStack getFullStack() {
        return item;
    }

    private void setChance(double chance) {
        this.chance = chance > 1 ? 1 : chance < 0 ? 0 : chance;
    }

    public double getChance() {
        return chance;
    }

    public ItemStack roll() {
        double random = Electrodynamics.RANDOM.nextDouble();
        if (random > 1 - chance) {
            double amount = chance >= 1 ? item.getCount() : item.getCount() * random;
            int itemCount = (int) Math.ceil(amount);
            return new ItemStack(item.getItem(), itemCount);
        }
        return ItemStack.EMPTY;
    }

    public static ProbableItem read(FriendlyByteBuf buf) {
        return new ProbableItem(buf.readItem(), buf.readDouble());
    }

    public static List<ProbableItem> readList(FriendlyByteBuf buf) {
        int count = buf.readInt();
        List<ProbableItem> items = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            items.add(ProbableItem.read(buf));
        }
        return items;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeItem(getFullStack());
        buf.writeDouble(chance);
    }

    public static void writeList(FriendlyByteBuf buf, List<ProbableItem> items) {
        buf.writeInt(items.size());
        for (ProbableItem item : items) {
            item.write(buf);
        }
    }

}
