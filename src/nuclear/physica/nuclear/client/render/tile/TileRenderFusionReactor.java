package physica.nuclear.client.render.tile;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import physica.library.client.render.TileRenderObjModel;
import physica.nuclear.common.tile.TileFusionReactor;

@SideOnly(Side.CLIENT)
public class TileRenderFusionReactor extends TileRenderObjModel<TileFusionReactor> {

	public TileRenderFusionReactor(String objFile, String textureFile, String domain, String modelDirectory, String modelTextureDirectory) {
		super(objFile, textureFile, domain, modelDirectory, modelTextureDirectory);
	}

	@Override
	public void renderTileAt(TileFusionReactor tile, double x, double y, double z, float deltaFrame)
	{
		super.renderTileAt(tile, x, y, z, deltaFrame);
		if (Minecraft.getMinecraft().thePlayer.getDistanceSq(tile.xCoord, tile.yCoord, tile.zCoord) <= 64)
		{
			GL11.glPushMatrix();
			int deuterium = tile.getStackInSlot(TileFusionReactor.SLOT_DEUTERIUM) != null ? tile.getStackInSlot(TileFusionReactor.SLOT_DEUTERIUM).stackSize : 0;
			TileRenderObjModel.renderFloatingText("Deuterium: " + deuterium / 0.04 + "ml", (float) x + 0.5F, (float) y + 0.25F - 2.0F + 1.25f, (float) z + 0.5F, 16777215);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			int tritium = tile.getStackInSlot(TileFusionReactor.SLOT_TRITIUM) != null ? tile.getStackInSlot(TileFusionReactor.SLOT_TRITIUM).stackSize : 0;
			TileRenderObjModel.renderFloatingText("Tritium: " + tritium / 0.08 + "ml", (float) x + 0.5F, (float) y + 0.25F - 2.0F + 0.95f, (float) z + 0.5F, 16777215);
			GL11.glPopMatrix();
		}
	}

}
