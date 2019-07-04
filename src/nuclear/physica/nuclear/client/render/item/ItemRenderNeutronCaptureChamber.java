package physica.nuclear.client.render.item;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.item.ItemStack;
import physica.library.client.render.ItemRenderObjModel;

public class ItemRenderNeutronCaptureChamber extends ItemRenderObjModel {

	public ItemRenderNeutronCaptureChamber(String objFile, String textureFile, String domain, String modelDirectory, String modelTextureDirectory) {
		super(objFile, textureFile, domain, modelDirectory, modelTextureDirectory);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glScaled(0.0625f, 0.0625f, 0.0625f);
		if (type.equals(ItemRenderType.INVENTORY))
		{
			GL11.glTranslatef(2.5f, -3.5f, -5.5f);
			GL11.glRotatef(180f, 0, 1, 0);
		} else if (type.equals(ItemRenderType.EQUIPPED_FIRST_PERSON))
		{
			GL11.glRotatef(45f, 0, 1, 0);
			GL11.glTranslatef(1f, 12f, 9f);
		} else if (type.equals(ItemRenderType.EQUIPPED))
		{
			GL11.glTranslatef(8f, 10f, 8f);
		}
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(model_texture);
		model_base.renderAll();
		GL11.glPopMatrix();
	}
}
