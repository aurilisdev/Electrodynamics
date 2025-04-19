package electrodynamics.compatibility.jei.recipecategories.misc;

import java.util.List;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.compatibility.jei.recipecategories.utils.psuedorecipes.types.PsuedoGasCollectorRecipe;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsBlocks;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import voltaic.api.gas.GasStack;
import voltaic.compatibility.jei.recipecategories.AbstractRecipeCategory;
import voltaic.compatibility.jei.utils.gui.ScreenObject;
import voltaic.compatibility.jei.utils.gui.types.BackgroundObject;
import voltaic.compatibility.jei.utils.gui.types.ItemSlotObject;
import voltaic.compatibility.jei.utils.gui.types.gasgauge.GasGaugeObject;
import voltaic.compatibility.jei.utils.label.AbstractLabelWrapper;
import voltaic.compatibility.jei.utils.label.types.PowerLabelWrapperConstant;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.ScreenComponentSlot;
import voltaic.prefab.utilities.math.Color;

public class GasCollectorRecipeCategory extends AbstractRecipeCategory<PsuedoGasCollectorRecipe> {

    public static final BackgroundObject BACK_WRAP = new BackgroundObject(132, 65);

    public static final ItemSlotObject INPUT_SLOT = new ItemSlotObject(ScreenComponentSlot.SlotType.NORMAL, 25, 26, RecipeIngredientRole.INPUT);

    public static final GasGaugeObject OUTPUT_GAUGE = new GasGaugeObject(90, 10);

    public static final PowerLabelWrapperConstant POWER_LABEL = new PowerLabelWrapperConstant(2, 55, ElectroConstants.GAS_COLLECTOR_USAGE_PER_TICK, 240);

    public static final AbstractLabelWrapper BIOME_LABEL = new AbstractLabelWrapper(Color.JEI_TEXT_GRAY, 2, 2, false) {
        @Override
        public Component getComponent(AbstractRecipeCategory<?> category, Object recipe) {
            PsuedoGasCollectorRecipe psuedo = (PsuedoGasCollectorRecipe) recipe;
            if(psuedo.output.biome() != null){
                return ElectroTextUtils.jeiTranslated("validbiome", Component.literal(psuedo.output.biome().location().getPath().toString()).withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.DARK_GRAY);
            } else if (psuedo.output.biomeTag() != null){
                return ElectroTextUtils.jeiTranslated("validbiome", Component.literal("#" + psuedo.output.biomeTag().location().getPath().toString()).withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.DARK_GRAY);
            } else {
                return ElectroTextUtils.jeiTranslated("validbiome", ElectroTextUtils.jeiTranslated("anybiome").withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.DARK_GRAY);
            }
        }
    };

    public static final ScreenObject FAN = new ScreenObject(ScreenComponentProgress.ProgressTextures.FAN_ON, 57, 26);

    public static final int ANIM_TIME = 50;

    public static ItemStack INPUT_MACHINE = new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.gascollector));

    public static final RecipeType<PsuedoGasCollectorRecipe> RECIPE_TYPE = RecipeType.create(Electrodynamics.ID, "gascollector", PsuedoGasCollectorRecipe.class);
    public GasCollectorRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, ElectroTextUtils.jeiTranslated("gascollector"), INPUT_MACHINE, BACK_WRAP, RECIPE_TYPE, ANIM_TIME);
        setInputSlots(guiHelper, INPUT_SLOT);
        setGasOutputs(guiHelper, OUTPUT_GAUGE);
        setScreenObjects(guiHelper, FAN);
        setLabels(POWER_LABEL, BIOME_LABEL);
    }


    @Override
    public List<List<ItemStack>> getItemInputs(PsuedoGasCollectorRecipe recipe) {
        return List.of(List.of(recipe.input));
    }

    @Override
    public List<GasStack> getGasOutputs(PsuedoGasCollectorRecipe recipe) {
        return List.of(recipe.output.stack());
    }
}
