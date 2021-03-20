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
import electrodynamics.common.inventory.container.ContainerDO2OProcessor;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenDO2OProcessor extends GenericContainerScreenUpgradeable<ContainerDO2OProcessor> {
    public static final ResourceLocation SCREEN_BACKGROUND = new ResourceLocation(References.ID + ":textures/gui/do2oprocessor.png");

    public ScreenDO2OProcessor(ContainerDO2OProcessor container, PlayerInventory playerInventory, ITextComponent title) {
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
		    new TranslationTextComponent("gui.do2oprocessor.usage",
			    ElectricityChatFormatter.getDisplayShort(processor.getUsage() * 20, ElectricUnit.WATT)),
		    (float) playerInventoryTitleX + 77, (float) playerInventoryTitleY - 11, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.do2oprocessor.voltage",
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