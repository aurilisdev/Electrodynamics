package electrodynamics.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.References;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.api.electricity.formatting.ElectricityChatFormatter;
import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentElectrodynamic;
import electrodynamics.api.tile.components.type.ComponentProcessor;
import electrodynamics.client.screen.generic.GenericContainerScreenUpgradeable;
import electrodynamics.common.inventory.container.ContainerElectricFurnace;
import electrodynamics.common.tile.TileElectricFurnace;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenElectricFurnace extends GenericContainerScreenUpgradeable<ContainerElectricFurnace> {
    public static final ResourceLocation SCREEN_BACKGROUND = new ResourceLocation(References.ID + ":textures/gui/electricfurnace.png");

    public ScreenElectricFurnace(ContainerElectricFurnace container, PlayerInventory playerInventory, ITextComponent title) {
	super(container, playerInventory, title);
    }

    @Override
    public ResourceLocation getScreenBackground() {
	return SCREEN_BACKGROUND;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
	super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
	TileElectricFurnace furnace = container.getHostFromIntArray();
	if (furnace != null) {
	    ComponentElectrodynamic electro = furnace.getComponent(ComponentType.Electrodynamic);
	    ComponentProcessor processor = furnace.getComponent(ComponentType.Processor);
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
	TileElectricFurnace furnace = container.getHostFromIntArray();
	if (furnace != null) {
	    ComponentProcessor processor = furnace.getComponent(ComponentType.Processor);
	    if (processor.operatingTicks > 0) {
		int progress = 12;
		blit(stack, guiLeft + 39, guiTop + 36 + 12 - progress, 212, 12 - progress, 14, progress + 1);
	    }

	    blit(stack, guiLeft + 79, guiTop + 34, 212, 14, (int) (processor.operatingTicks * 24 / processor.requiredTicks + 1), 16);
	}
    }
}