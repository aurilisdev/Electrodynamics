package electrodynamics.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.References;
import electrodynamics.api.formatting.ElectricUnit;
import electrodynamics.api.utilities.ElectricityChatFormatter;
import electrodynamics.client.screen.generic.GenericContainerScreenUpgradeable;
import electrodynamics.common.inventory.container.ContainerLAO2LProcessor;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenLAO2LProcessor extends GenericContainerScreenUpgradeable<ContainerLAO2LProcessor> implements IHasContainer<ContainerLAO2LProcessor> {
	public static final ResourceLocation SCREEN_BACKGROUND = new ResourceLocation(References.ID + ":textures/gui/lao2lprocessor.png");

	public ScreenLAO2LProcessor(ContainerLAO2LProcessor container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title);
	}

	@Override
	public ResourceLocation getScreenBackground() {
		return SCREEN_BACKGROUND;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
		font.func_243248_b(matrixStack, new TranslationTextComponent("gui.lao2lprocessor.usage", ElectricityChatFormatter.getDisplayShort(container.getJoulesPerTick() * 20, ElectricUnit.WATT)),
				(float) playerInventoryTitleX + 77, (float) playerInventoryTitleY - 11, 4210752);
		font.func_243248_b(matrixStack, new TranslationTextComponent("gui.lao2lprocessor.voltage", ElectricityChatFormatter.getDisplayShort(container.getVoltage(), ElectricUnit.VOLTAGE)),
				(float) playerInventoryTitleX + 77, playerInventoryTitleY, 4210752);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(stack, partialTicks, mouseX, mouseY);
		blit(stack, guiLeft + 79, guiTop + 34, 212, 14, container.getBurnLeftScaled() + 1, 16);
	}

}