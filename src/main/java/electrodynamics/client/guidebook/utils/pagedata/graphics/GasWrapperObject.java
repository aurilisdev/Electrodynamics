package electrodynamics.client.guidebook.utils.pagedata.graphics;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.gas.Gas;
import electrodynamics.client.ClientRegister;
import electrodynamics.client.guidebook.utils.components.Page;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

public class GasWrapperObject extends AbstractGraphicWrapper<GasWrapperObject> {

	public final Gas gas;

	public GasWrapperObject(int xOffset, int yOffset, int width, int height, int trueHeight, Gas gas, GraphicTextDescriptor... descriptors) {
		super(xOffset, yOffset, xOffset, yOffset, width, height, trueHeight, descriptors);
		this.gas = gas;
	}

	@Override
	public void render(PoseStack stack, int wrapperX, int wrapperY, int xShift, int guiWidth, int guiHeight, Page page) {

		ResourceLocation texture = ClientRegister.TEXTURE_GAS;

		TextureAtlasSprite sprite = ClientRegister.CACHED_TEXTUREATLASSPRITES.get(texture);

		RenderSystem.setShaderTexture(0, sprite.atlas().getId());

		GuiComponent.blit(stack, guiWidth + wrapperX + xShift, guiHeight + wrapperY, 0, width, height, sprite);

	}

}
