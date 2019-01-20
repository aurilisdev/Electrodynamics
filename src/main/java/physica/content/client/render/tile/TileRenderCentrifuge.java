package physica.content.client.render.tile;

import org.lwjgl.opengl.GL11;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import physica.References;
import physica.api.lib.client.render.TileRenderObjModel;
import physica.api.lib.tile.TileBase;
import physica.content.common.tile.TileCentrifuge;

public class TileRenderCentrifuge extends TileRenderObjModel<TileCentrifuge> {
	protected IModelCustom model_middle;

	public TileRenderCentrifuge(String objFile, String textureFile) {
		super(objFile, textureFile, References.DOMAIN, References.MODEL_DIRECTORY, References.MODEL_TEXTURE_DIRECTORY);
		model_middle = AdvancedModelLoader.loadModel(new ResourceLocation(References.DOMAIN, References.MODEL_DIRECTORY + objFile.replace(".obj", "_middle.obj")));
	}

	@Override
	public void renderTileAt(TileCentrifuge tile, double x, double y, double z, float deltaFrame) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
		GL11.glScaled(0.0625f, 0.0625f, 0.0625f);
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
		if (tile.isRotating())
		{
			GL11.glRotatef(((TileBase) tile).getTicksRunning() * 18 % 360, 0.0f, 1.0f, 0.0f);
		}
		model_middle.renderAll();
		GL11.glEnable(GL11.GL_LIGHTING);

		GL11.glPopMatrix();
	}
}
