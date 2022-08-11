package physica.nuclear.client.render.tile;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;
import physica.CoreReferences;
import physica.library.client.render.TileRenderObjModel;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.tile.TileNeutronCaptureChamber;

@SideOnly(Side.CLIENT)
public class TileRenderNeutronCaptureChamber extends TileRenderObjModel<TileNeutronCaptureChamber> {

	protected ResourceLocation filled_texture;

	public TileRenderNeutronCaptureChamber(String objFile, String textureFile) {
		super(objFile, textureFile.replace(".png", "empty.png"), NuclearReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY);
		filled_texture = new ResourceLocation(NuclearReferences.DOMAIN, CoreReferences.MODEL_TEXTURE_DIRECTORY + textureFile.replace(".png", "filled.png"));
	}

	@Override
	public void renderTileAt(TileNeutronCaptureChamber tile, double x, double y, double z, float deltaFrame) {
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
		bindTexture(tile.hasDeuterium() ? filled_texture : resourceTexture);
		wavefrontObject.render();
		GL11.glPopMatrix();
	}
}
