package electrodynamics.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.References;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.api.electricity.formatting.ElectricityChatFormatter;
import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentElectrodynamic;
import electrodynamics.api.tile.components.type.ComponentProcessor;
import electrodynamics.client.screen.generic.GenericContainerScreenUpgradeable;
import electrodynamics.common.inventory.container.ContainerChemicalCrystallizer;
import electrodynamics.common.tile.TileChemicalCrystallizer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenChemicalCrystallizer extends GenericContainerScreenUpgradeable<ContainerChemicalCrystallizer> {
    public static final ResourceLocation SCREEN_BACKGROUND = new ResourceLocation(References.ID + ":textures/gui/chemicalcrystallizer.png");

    public ScreenChemicalCrystallizer(ContainerChemicalCrystallizer container, PlayerInventory playerInventory, ITextComponent title) {
	super(container, playerInventory, title);
	xSize = 176;
    }

    @Override
    public ResourceLocation getScreenBackground() {
	return SCREEN_BACKGROUND;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
	font.func_243248_b(matrixStack, title, titleX, titleY, 4210752);
	TileChemicalCrystallizer plant = container.getHostFromIntArray();
	if (plant != null) {
	    ComponentElectrodynamic electro = plant.getComponent(ComponentType.Electrodynamic);
	    ComponentProcessor processor = plant.getComponent(ComponentType.Processor);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.chemicalcrystallizer.usage",
			    ElectricityChatFormatter.getDisplayShort(processor.getUsage() * 20, ElectricUnit.WATT)),
		    playerInventoryTitleX, playerInventoryTitleY, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.chemicalcrystallizer.voltage",
			    ElectricityChatFormatter.getDisplayShort(electro.getVoltage(), ElectricUnit.VOLTAGE)),
		    (float) playerInventoryTitleX + 85, playerInventoryTitleY, 4210752);
	}
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
	super.drawGuiContainerBackgroundLayer(stack, partialTicks, mouseX, mouseY);
	TileChemicalCrystallizer plant = container.getHostFromIntArray();
	if (plant != null) {
	    ComponentProcessor processor = plant.getComponent(ComponentType.Processor);
	    int burnLeftScaled = (int) (processor.operatingTicks * 34.0 / processor.requiredTicks);
	    blit(stack, guiLeft + 41, guiTop + 34, 210, 14, burnLeftScaled, 16);
	    blit(stack, guiLeft + 21, guiTop + 68 - (plant.hasFluid ? 50 : 0), 214, 31, 16, plant.hasFluid ? 50 : 0);
	}
    }

}