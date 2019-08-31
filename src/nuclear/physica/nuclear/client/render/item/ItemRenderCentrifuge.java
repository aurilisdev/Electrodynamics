package physica.nuclear.client.render.item;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import physica.CoreReferences;
import physica.library.client.render.ItemRenderObjModel;
import physica.library.client.render.obj.PhysicaModelLoader;
import physica.library.client.render.obj.model.WavefrontObject;
import physica.nuclear.NuclearReferences;

@SideOnly(Side.CLIENT)
public class ItemRenderCentrifuge extends ItemRenderObjModel {

	protected WavefrontObject model_middle;

	public ItemRenderCentrifuge(String objFile, String textureFile) {
		super(objFile, textureFile, NuclearReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY);
		model_middle = PhysicaModelLoader.loadWavefrontModel(new ResourceLocation(NuclearReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY + objFile.replace("stand.obj", "spin.obj")));
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
		model_base.render();
		model_middle.render();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
}
