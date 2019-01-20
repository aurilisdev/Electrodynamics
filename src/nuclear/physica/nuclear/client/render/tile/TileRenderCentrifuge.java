package physica.nuclear.client.render.tile;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import physica.CoreReferences;
import physica.library.client.render.TileRenderObjModel;
import physica.nuclear.common.tile.TileCentrifuge;

@SideOnly(Side.CLIENT)
public class TileRenderCentrifuge extends TileRenderObjModel<TileCentrifuge> {

	protected IModelCustom model_middle;

	public TileRenderCentrifuge(String objFile, String textureFile) {
		super(objFile, textureFile, CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY);
		model_middle = AdvancedModelLoader.loadModel(new ResourceLocation(CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY + objFile.replace(".obj", "_middle.obj")));
	}

	@Override
	public void renderTileAt(TileCentrifuge tile, double x, double y, double z, float deltaFrame) {
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
		GL11.glScaled(0.0625, 0.0625, 0.0625);
		switch (tile.getFacing()) {
		case NORTH:
			GL11.glRotatef(-90, 0, 1, 0);
			break;
		case SOUTH:
			GL11.glRotatef(90, 0, 1, 0);
			break;
		case EAST:
			GL11.glRotatef(180, 0, 1, 0);
			break;
		default:
			break;
		}

		bindTexture(model_texture);
		model_base.renderAll();
		if (tile.getOperatingTicks() > 0) {
			GL11.glRotatef(tile.getTicksRunning() * 18 % 360, 0.0f, 1.0f, 0.0f);
		}
		model_middle.renderAll();
		GL11.glPopMatrix();
	}
}
