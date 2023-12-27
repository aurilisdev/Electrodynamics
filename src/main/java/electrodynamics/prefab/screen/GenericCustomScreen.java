package electrodynamics.prefab.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class GenericCustomScreen<T extends GenericContainer> extends ContainerScreen<T> {
	protected GenericCustomScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}

	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(stack);
		super.render(stack, mouseX, mouseY, partialTicks);
		renderTooltip(stack, mouseX, mouseY);
	}

	@Override
	protected void renderBg(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1, 1, 1, 1);
		RenderingUtils.bindTexture(getScreenBackground());
		blit(stack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
		RenderingUtils.resetShaderColor();
	}

	public abstract ResourceLocation getScreenBackground();
}
