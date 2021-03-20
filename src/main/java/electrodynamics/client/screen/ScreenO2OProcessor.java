package electrodynamics.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.References;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.api.electricity.formatting.ElectricityChatFormatter;
import electrodynamics.api.tile.GenericTile;
import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentElectrodynamic;
import electrodynamics.api.tile.components.type.ComponentProcessor;
import electrodynamics.client.screen.generic.GenericContainerScreenUpgradeable;
import electrodynamics.common.inventory.container.ContainerO2OProcessor;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenO2OProcessor extends GenericContainerScreenUpgradeable<ContainerO2OProcessor> {
    public static final ResourceLocation SCREEN_BACKGROUND = new ResourceLocation(References.ID + ":textures/gui/o2oprocessor.png");

    public ScreenO2OProcessor(ContainerO2OProcessor container, PlayerInventory playerInventory, ITextComponent title) {
	super(container, playerInventory, title);
    }

    @Override
    public ResourceLocation getScreenBackground() {
	return SCREEN_BACKGROUND;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
	super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
	GenericTile tile = container.getHostFromIntArray();
	if (tile != null) {
	    ComponentElectrodynamic electro = tile.getComponent(ComponentType.Electrodynamic);
	    ComponentProcessor processor = tile.getComponent(ComponentType.Processor);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.o2oprocessor.usage",
			    ElectricityChatFormatter.getDisplayShort(processor.getUsage() * 20, ElectricUnit.WATT)),
		    (float) playerInventoryTitleX + 77, (float) playerInventoryTitleY - 11, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.o2oprocessor.voltage",
			    ElectricityChatFormatter.getDisplayShort(electro.getVoltage(), ElectricUnit.VOLTAGE)),
		    (float) playerInventoryTitleX + 77, playerInventoryTitleY, 4210752);

	}
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
	super.drawGuiContainerBackgroundLayer(stack, partialTicks, mouseX, mouseY);
	GenericTile tile = container.getHostFromIntArray();
	if (tile != null) {
	    ComponentProcessor processor = tile.getComponent(ComponentType.Processor);

	    blit(stack, guiLeft + 79, guiTop + 34, 212, 14, (int) (processor.operatingTicks * 24 / processor.requiredTicks + 1), 16);
	}
    }

}