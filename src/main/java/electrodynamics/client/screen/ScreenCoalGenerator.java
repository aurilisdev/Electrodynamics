package electrodynamics.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.References;
import electrodynamics.api.formatting.ElectricUnit;
import electrodynamics.api.utilities.ElectricityChatFormatter;
import electrodynamics.client.screen.generic.GenericContainerScreen;
import electrodynamics.common.inventory.container.ContainerCoalGenerator;
import electrodynamics.common.tile.TileCoalGenerator;
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
		font.func_238422_b_(matrixStack, new TranslationTextComponent("gui.coalgenerator.timeleft", container.getBurnTicksLeft() / 20 + "s"), (float) playerInventoryTitleX + 70, (float) playerInventoryTitleY - 53,
				4210752);
		font.func_238422_b_(matrixStack, new TranslationTextComponent("gui.coalgenerator.current", ElectricityChatFormatter.getDisplayShort(TileCoalGenerator.DEFAULT_OUTPUT.getAmps(), ElectricUnit.AMPERE)),
				(float) playerInventoryTitleX + 70, (float) playerInventoryTitleY - 40, 4210752);
		font.func_238422_b_(matrixStack, new TranslationTextComponent("gui.coalgenerator.output", ElectricityChatFormatter.getDisplayShort(TileCoalGenerator.DEFAULT_OUTPUT.getWatts(), ElectricUnit.WATT)),
				(float) playerInventoryTitleX + 70, (float) playerInventoryTitleY - 27, 4210752);
		font.func_238422_b_(matrixStack, new TranslationTextComponent("gui.coalgenerator.voltage", ElectricityChatFormatter.getDisplayShort(TileCoalGenerator.DEFAULT_OUTPUT.getVoltage(), ElectricUnit.VOLTAGE)),
				(float) playerInventoryTitleX + 70, (float) playerInventoryTitleY - 14, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(stack, partialTicks, mouseX, mouseY);
		if (container.isBurning()) {
			int k = container.getBurnLeftScaled();
			blit(stack, guiLeft + 35, guiTop + 25 + 12 - k, 212, 12 - k, 14, k + 1);
		}
	}
}