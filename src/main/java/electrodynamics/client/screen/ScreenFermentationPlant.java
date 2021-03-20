package electrodynamics.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.api.electricity.formatting.ElectricityChatFormatter;
import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentElectrodynamic;
import electrodynamics.api.tile.components.type.ComponentFluidHandler;
import electrodynamics.api.tile.components.type.ComponentProcessor;
import electrodynamics.client.screen.generic.GenericContainerScreenUpgradeable;
import electrodynamics.common.inventory.container.ContainerFermentationPlant;
import electrodynamics.common.tile.TileFermentationPlant;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenFermentationPlant extends GenericContainerScreenUpgradeable<ContainerFermentationPlant> {
    public static final ResourceLocation SCREEN_BACKGROUND = new ResourceLocation(References.ID + ":textures/gui/fermentationplant.png");

    public ScreenFermentationPlant(ContainerFermentationPlant container, PlayerInventory playerInventory, ITextComponent title) {
	super(container, playerInventory, title);
    }

    @Override
    public ResourceLocation getScreenBackground() {
	return SCREEN_BACKGROUND;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
	font.func_243248_b(matrixStack, title, titleX, titleY, 4210752);
	TileFermentationPlant plant = container.getHostFromIntArray();
	if (plant != null) {
	    ComponentElectrodynamic electro = plant.getComponent(ComponentType.Electrodynamic);
	    ComponentProcessor processor = plant.getComponent(ComponentType.Processor);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.fermentationplant.usage",
			    ElectricityChatFormatter.getDisplayShort(processor.getUsage() * 20, ElectricUnit.WATT)),
		    playerInventoryTitleX, playerInventoryTitleY, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.fermentationplant.voltage",
			    ElectricityChatFormatter.getDisplayShort(electro.getVoltage(), ElectricUnit.VOLTAGE)),
		    (float) playerInventoryTitleX + 85, playerInventoryTitleY, 4210752);
	}
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
	super.drawGuiContainerBackgroundLayer(stack, partialTicks, mouseX, mouseY);
	TileFermentationPlant plant = container.getHostFromIntArray();
	if (plant != null) {
	    ComponentProcessor processor = plant.getComponent(ComponentType.Processor);
	    ComponentFluidHandler handler = plant.getComponent(ComponentType.FluidHandler);
	    int burnLeftScaled = (int) (processor.operatingTicks * 34.0 / processor.requiredTicks);
	    blit(stack, guiLeft + 44, guiTop + 30, 212, 14, Math.min(burnLeftScaled * 2 + 1, 34), 16);
	    if (burnLeftScaled > 17) {
		blit(stack, guiLeft + 44 + 60, guiTop + 30, 212, 14, Math.min(burnLeftScaled * 2 - 34 + 1, 34), 16);
	    }
	    blit(stack, guiLeft + 21,
		    guiTop + 68 - (int) (handler.getStackFromFluid(Fluids.WATER).getAmount() / (float) TileFermentationPlant.TANKCAPACITY_WATER * 50),
		    214 + 18, 31, 16,
		    (int) (handler.getStackFromFluid(Fluids.WATER).getAmount() / (float) TileFermentationPlant.TANKCAPACITY_WATER * 50));
	    blit(stack, guiLeft + 139,
		    guiTop + 68
			    - (int) (handler.getStackFromFluid(DeferredRegisters.fluidEthanol).getAmount()
				    / (float) TileFermentationPlant.TANKCAPACITY_ETHANOL * 50.0),
		    214, 31, 16, (int) (handler.getStackFromFluid(DeferredRegisters.fluidEthanol).getAmount()
			    / (float) TileFermentationPlant.TANKCAPACITY_ETHANOL * 50.0));
	}
    }

}