package physica.api.lib.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import physica.api.lib.tile.TileBaseRotateable;

public class TileRenderObjModel<T extends TileBaseRotateable> extends TileEntitySpecialRenderer {
	protected IModelCustom		model_base;
	protected ResourceLocation	model_texture;

	public TileRenderObjModel(String objFile, String textureFile, String domain, String modelDirectory, String modelTextureDirectory) {
		model_base = AdvancedModelLoader.loadModel(new ResourceLocation(domain, modelDirectory + objFile));
		model_texture = new ResourceLocation(domain, modelTextureDirectory + textureFile);
	}

	public void renderTileAt(T tile, double x, double y, double z, float deltaFrame) {
		GL11.glPushMatrix();
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
		GL11.glPopMatrix();
	}

	@Override
	public final void renderTileEntityAt(TileEntity tile, double x, double y, double z, float deltaFrame) {
		this.renderTileAt((T) tile, x, y, z, deltaFrame);
	}
}
