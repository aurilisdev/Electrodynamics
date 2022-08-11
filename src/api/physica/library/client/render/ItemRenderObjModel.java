package physica.library.client.render;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import physica.library.client.render.obj.PhysicaModelLoader;
import physica.library.client.render.obj.model.WavefrontObject;

public class ItemRenderObjModel implements IItemRenderer {

	protected WavefrontObject model_base;
	protected ResourceLocation model_texture;

	public ItemRenderObjModel(String objFile, String textureFile, String domain, String modelDirectory, String modelTextureDirectory) {
		model_base = PhysicaModelLoader.loadWavefrontModel(new ResourceLocation(domain, modelDirectory + objFile));
		model_texture = new ResourceLocation(domain, modelTextureDirectory + textureFile);
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glScaled(0.0625f, 0.0625f, 0.0625f);
		if (ItemRenderType.INVENTORY.equals(type)) {
			GL11.glTranslatef(-0.5f, -0.8f, -0.5f);
			GL11.glRotatef(180f, 0, 1, 0);
		} else if (ItemRenderType.EQUIPPED_FIRST_PERSON.equals(type)) {
			GL11.glRotatef(45f, 0, 1, 0);
			GL11.glTranslatef(1f, 12f, 9f);
		} else if (ItemRenderType.EQUIPPED.equals(type)) {
			GL11.glTranslatef(8f, 10f, 8f);
		}
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(model_texture);
		model_base.render();
		GL11.glPopMatrix();
	}
}
