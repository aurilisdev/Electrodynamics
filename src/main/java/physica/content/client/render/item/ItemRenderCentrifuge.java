package physica.content.client.render.item;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import physica.References;
import physica.api.lib.client.render.ItemRenderObjModel;

public class ItemRenderCentrifuge extends ItemRenderObjModel {
	protected IModelCustom model_middle;

	public ItemRenderCentrifuge(String objFile, String textureFile) {
		super(objFile, textureFile, References.DOMAIN, References.MODEL_DIRECTORY, References.MODEL_TEXTURE_DIRECTORY);
		model_middle = AdvancedModelLoader.loadModel(new ResourceLocation(References.DOMAIN, References.MODEL_DIRECTORY + objFile.replace(".obj", "_middle.obj")));
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		GL11.glScaled(0.0625f, 0.0625f, 0.0625f);
		if (type.equals(ItemRenderType.INVENTORY))
		{
			GL11.glTranslatef(-0.5f, -0.8f, -0.5f);
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
		model_middle.renderAll();
		GL11.glPopMatrix();
	}
}
