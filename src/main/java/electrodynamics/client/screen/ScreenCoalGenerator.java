package electrodynamics.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.References;
import electrodynamics.api.formatting.ElectricUnit;
import electrodynamics.api.utilities.ElectricityChatFormatter;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.client.screen.generic.GenericContainerScreen;
import electrodynamics.common.config.Constants;
import electrodynamics.common.inventory.container.ContainerCoalGenerator;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenCoalGenerator extends GenericContainerScreen<ContainerCoalGenerator> implements IHasContainer<ContainerCoalGenerator> {
	public static final ResourceLocation SCREEN_BACKGROUND = new ResourceLocation(References.ID + ":textures/gui/coalgenerator.png");

	public ScreenCoalGenerator(ContainerCoalGenerator container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title);
	}

	@Override
	public ResourceLocation getScreenBackground() {
		return SCREEN_BACKGROUND;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
		TransferPack output = TransferPack.ampsVoltage(Constants.COALGENERATOR_MAX_OUTPUT.getAmps() * Math.min(((double) (container.getHeat()-27.0) / (3000.0 - 27.0)), 1), Constants.COALGENERATOR_MAX_OUTPUT.getVoltage());
		font.func_243248_b(matrixStack, new TranslationTextComponent("gui.coalgenerator.timeleft", container.getBurnTicksLeft() / 20 + "s"), (float) playerInventoryTitleX + 60, (float) playerInventoryTitleY - 53,
				4210752);
		font.func_243248_b(matrixStack, new TranslationTextComponent("gui.coalgenerator.current", ElectricityChatFormatter.getDisplayShort(output.getAmps(), ElectricUnit.AMPERE)), (float) playerInventoryTitleX + 60,
				(float) playerInventoryTitleY - 40, 4210752);
		font.func_243248_b(matrixStack, new TranslationTextComponent("gui.coalgenerator.output", ElectricityChatFormatter.getDisplayShort(output.getWatts(), ElectricUnit.WATT)), (float) playerInventoryTitleX + 60,
				(float) playerInventoryTitleY - 27, 4210752);
		font.func_243248_b(matrixStack, new TranslationTextComponent("gui.coalgenerator.voltage", ElectricityChatFormatter.getDisplayShort(output.getVoltage(), ElectricUnit.VOLTAGE)), (float) playerInventoryTitleX + 60,
				(float) playerInventoryTitleY - 14, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(stack, partialTicks, mouseX, mouseY);
		if (container.isBurning()) {
			int k = container.getBurnLeftScaled();
			blit(stack, guiLeft + 25, guiTop + 25 + 12 - k, 212, 12 - k, 14, k + 1);
		}
	}
}