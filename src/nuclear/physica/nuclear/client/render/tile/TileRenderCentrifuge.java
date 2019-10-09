package physica.nuclear.client.render.tile;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;
import physica.CoreReferences;
import physica.library.client.render.TileRenderObjModel;
import physica.library.client.render.obj.PhysicaModelLoader;
import physica.library.client.render.obj.model.WavefrontObject;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.tile.TileGasCentrifuge;

@SideOnly(Side.CLIENT)
public class TileRenderCentrifuge extends TileRenderObjModel<TileGasCentrifuge> {

	protected WavefrontObject model_middle;

	public TileRenderCentrifuge(String objFile, String textureFile) {
		super(objFile, textureFile, NuclearReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY);
		model_middle = PhysicaModelLoader.loadWavefrontModel(new ResourceLocation(NuclearReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY + objFile.replace("stand.obj", "spin.obj")));
	}

	@Override
	public void renderTileAt(TileGasCentrifuge tile, double x, double y, double z, float deltaFrame)
	{
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

		bindTexture(resourceTexture);
		wavefrontObject.render();
		if (tile.getOperatingTicks() > 0)
		{
			if (tile.hasEnoughEnergy())
			{
				GL11.glRotatef(tile.getTicksRunning() * 24 % 360, 0.0f, 1, 0.0f);
			} else
			{
				GL11.glRotatef(tile.getOperatingTicks() * 24 % 360, 0.0f, 1, 0.0f);
			}
		}
		model_middle.render();
		GL11.glPopMatrix();
	}
}
