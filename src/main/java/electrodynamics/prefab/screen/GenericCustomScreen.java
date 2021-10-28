package electrodynamics.prefab.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public abstract class GenericCustomScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    protected GenericCustomScreen(T screenContainer, Inventory inv, Component titleIn) {
	super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
	this.renderBackground(matrixStack);
	super.render(matrixStack, mouseX, mouseY, partialTicks);
	renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
	RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	minecraft.getTextureManager().bind(getScreenBackground());
	blit(stack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

    public abstract ResourceLocation getScreenBackground();
}
