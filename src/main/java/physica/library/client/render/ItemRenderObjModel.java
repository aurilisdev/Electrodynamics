package physica.library.client.render;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ItemRenderObjModel implements IItemRenderer {

	protected IModelCustom model_base;
	protected ResourceLocation model_texture;

	public ItemRenderObjModel(String objFile, String textureFile, String domain, String modelDirectory, String modelTextureDirectory) {
		model_base = AdvancedModelLoader.loadModel(new ResourceLocation(domain, modelDirectory + objFile));
		model_texture = new ResourceLocation(domain, modelTextureDirectory + textureFile);
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glScaled(0.0625f, 0.0625f, 0.0625f);
		if (type.equals(ItemRenderType.INVENTORY)) {
			GL11.glTranslatef(-0.5f, -0.8f, -0.5f);
			GL11.glRotatef(180f, 0, 1, 0);
		} else if (type.equals(ItemRenderType.EQUIPPED_FIRST_PERSON)) {
			GL11.glRotatef(45f, 0, 1, 0);
			GL11.glTranslatef(1f, 12f, 9f);
		} else if (type.equals(ItemRenderType.EQUIPPED)) {
			GL11.glTranslatef(8f, 10f, 8f);
		}
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(model_texture);
		model_base.renderAll();
		GL11.glPopMatrix();
	}
}
