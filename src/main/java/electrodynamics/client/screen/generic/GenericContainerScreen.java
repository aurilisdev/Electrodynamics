package electrodynamics.client.screen.generic;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class GenericContainerScreen<T extends Container> extends ContainerScreen<T> {
	public GenericContainerScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}

	@Override
	public void func_230430_a_(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		func_230446_a_(matrixStack);
		super.func_230430_a_(matrixStack, mouseX, mouseY, partialTicks);
		func_230459_a_(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void func_230451_b_(MatrixStack matrixStack, int mouseX, int mouseY) {
		super.func_230451_b_(matrixStack, mouseX, mouseY);
		drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void func_230450_a_(MatrixStack stack, float delta, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		field_230706_i_.getTextureManager().bindTexture(getScreenBackground());
		func_238474_b_(stack, guiLeft, guiTop, 0, 0, xSize, ySize);
		drawGuiContainerBackgroundLayer(stack, delta, mouseX, mouseY);
	}

	protected abstract void drawGuiContainerBackgroundLayer(MatrixStack stack, float delta, int mouseX, int mouseY);

	protected abstract void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY);

	public abstract ResourceLocation getScreenBackground();
}
