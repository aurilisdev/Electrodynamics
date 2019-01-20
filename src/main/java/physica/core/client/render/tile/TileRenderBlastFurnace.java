package physica.core.client.render.tile;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;
import physica.CoreReferences;
import physica.core.common.tile.TileBlastFurnace;
import physica.library.client.render.TileRenderObjModel;

@SideOnly(Side.CLIENT)
public class TileRenderBlastFurnace extends TileRenderObjModel<TileBlastFurnace> {

	protected ResourceLocation model_texture2;

	public TileRenderBlastFurnace(String objFile, String textureFile) {
		super(objFile, textureFile, CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY);
		model_texture2 = new ResourceLocation(CoreReferences.DOMAIN, CoreReferences.MODEL_TEXTURE_DIRECTORY + textureFile.replace(".png", "2.png"));
	}

	@Override
	public void renderTileAt(TileBlastFurnace tile, double x, double y, double z, float deltaFrame) {
		GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
		GL11.glScaled(0.0625, 0.0625, 0.0625);
		bindTexture(tile.isBurning() ? model_texture2 : model_texture);
		model_base.renderAll();
		GL11.glScaled(1 / 0.0625, 1 / 0.0625, 1 / 0.0625);
		GL11.glTranslated(-(x + 0.5), -(y + 0.5), -(z + 0.5));
	}
}
