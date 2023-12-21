package electrodynamics.client.guidebook.utils.pagedata.graphics;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.client.guidebook.utils.components.Page;
import electrodynamics.prefab.utilities.RenderingUtils;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;

public class FluidWrapperObject extends AbstractGraphicWrapper<FluidWrapperObject> {

	public final Fluid fluid;

	public FluidWrapperObject(int xOffset, int yOffset, int width, int height, int trueHeight, Fluid fluid, GraphicTextDescriptor... descriptors) {
		super(xOffset, yOffset, xOffset, yOffset, width, height, trueHeight, descriptors);
		this.fluid = fluid;
	}

	@Override
	public void render(PoseStack stack, int wrapperX, int wrapperY, int xShift, int guiWidth, int guiHeight, Page page) {

		ResourceLocation texture = fluid.getAttributes().getStillTexture();

		TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture);

		RenderSystem.setShaderTexture(0, sprite.atlas().getId());

		RenderingUtils.setShaderColor(new Color(fluid.getAttributes().getColor()));

		GuiComponent.blit(stack, guiWidth + wrapperX + xShift, guiHeight + wrapperY, 0, width, height, sprite);

		RenderingUtils.resetShaderColor();

	}

}