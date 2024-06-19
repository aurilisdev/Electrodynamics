package electrodynamics.datagen.utils.recipe;

import javax.annotation.Nullable;

import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.neoforged.neoforge.common.conditions.ICondition;

public abstract class ElectrodynamicsCookingRecipe<T extends ElectrodynamicsCookingRecipe<T, A>, A extends AbstractCookingRecipe> implements RecipeBuilder {

    private final String group;
    private final ResourceLocation id;
    private final CookingBookCategory category;
    private final Item result;
    private final float experience;
    private final int cookingTime;

    private Ingredient ingredient;

    @Nullable
    private ICondition[] conditions;

    private ElectrodynamicsCookingRecipe(ResourceLocation id, String group, Item result, float experience, int cookingTime) {
        this.id = id;
        this.group = group;
        this.category = CookingBookCategory.MISC;
        this.result = result;
        this.experience = experience;
        this.cookingTime = cookingTime;
    }

    @Override
    public RecipeBuilder unlockedBy(String pName, Criterion<?> pCriterion) {
        return this;
    }

    @Override
    public RecipeBuilder group(String pGroupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return result;
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation altName) {
        if (conditions != null) {
            output.withConditions(conditions).accept(id, makeRecipe(), null);
        } else {
            output.accept(id, makeRecipe(), null);
        }
    }

    @Override
    public void save(RecipeOutput output) {
        this.save(output, id);
    }

    @Override
    public void save(RecipeOutput output, String name) {
        this.save(output, id);
    }

    public T input(Item item) {
        return input(new ItemStack(item));
    }

    public T input(ItemStack item) {
        return input(Ingredient.of(item));
    }

    public T input(TagKey<Item> tag) {
        return input(Ingredient.of(tag));
    }

    public T input(Ingredient item) {
        ingredient = item;
        return (T) this;
    }

    public T addConditions(ICondition... conditions) {
        this.conditions = conditions;
        return (T) this;
    }

    public abstract A makeRecipe();

    public static SmeltingBuilder smeltingRecipe(ResourceLocation id, String group, Item result, float experience, int smeltTime) {
        return new SmeltingBuilder(id, group, result, experience, smeltTime);
    }

    public static SmokingBuilder smokingRecipe(ResourceLocation id, String group, Item result, float experience, int smeltTime) {
        return new SmokingBuilder(id, group, result, experience, smeltTime);
    }

    public static BlastingBuilder blastingRecipe(ResourceLocation id, String group, Item result, float experience, int smeltTime) {
        return new BlastingBuilder(id, group, result, experience, smeltTime);
    }

    public static class SmeltingBuilder extends ElectrodynamicsCookingRecipe<SmeltingBuilder, SmeltingRecipe> {

        private SmeltingBuilder(ResourceLocation id, String group, Item result, float experience, int smeltTime) {
            super(id, group, result, experience, smeltTime);
        }

        @Override
        public SmeltingRecipe makeRecipe() {
            return new SmeltingRecipe(super.group, super.category, super.ingredient, new ItemStack(super.result), super.experience, super.cookingTime);
        }

    }

    public static class SmokingBuilder extends ElectrodynamicsCookingRecipe<SmokingBuilder, SmokingRecipe> {

        private SmokingBuilder(ResourceLocation id, String group, Item result, float experience, int smeltTime) {
            super(id, group, result, experience, smeltTime);
        }

        @Override
        public SmokingRecipe makeRecipe() {
            return new SmokingRecipe(super.group, super.category, super.ingredient, new ItemStack(super.result), super.experience, super.cookingTime);
        }

    }

    public static class BlastingBuilder extends ElectrodynamicsCookingRecipe<BlastingBuilder, BlastingRecipe> {

        private BlastingBuilder(ResourceLocation id, String group, Item result, float experience, int smeltTime) {
            super(id, group, result, experience, smeltTime);
        }

        @Override
        public BlastingRecipe makeRecipe() {
            return new BlastingRecipe(super.group, super.category, super.ingredient, new ItemStack(super.result), super.experience, super.cookingTime);
        }

    }

}
