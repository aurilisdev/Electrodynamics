package electrodynamics.client.guidebook.utils.pagedata.graphics;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.guidebook.utils.components.Page;
import electrodynamics.prefab.utilities.RenderingUtils;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;

public class FluidWrapperObject extends AbstractGraphicWrapper<FluidWrapperObject> {

	public final Fluid fluid;

	public FluidWrapperObject(int xOffset, int yOffset, int width, int height, int trueHeight, Fluid fluid, GraphicTextDescriptor... descriptors) {
		super(xOffset, yOffset, xOffset, yOffset, width, height, trueHeight, descriptors);
		this.fluid = fluid;
	}

	@Override
	public void render(MatrixStack stack, int wrapperX, int wrapperY, int xShift, int guiWidth, int guiHeight, Page page) {

		ResourceLocation texture = fluid.getAttributes().getStillTexture();

		TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(texture);

		RenderingUtils.bindTexture(sprite.atlas().location());

		RenderingUtils.setShaderColor(new Color(fluid.getAttributes().getColor()));

		AbstractGui.blit(stack, guiWidth + wrapperX + xShift, guiHeight + wrapperY, 0, width, height, sprite);

		RenderingUtils.resetShaderColor();

	}

}