package physica.nuclear.client.render.tile;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import physica.CoreReferences;
import physica.api.core.abstraction.FaceDirection;
import physica.library.client.render.TileRenderObjModel;
import physica.nuclear.common.tile.TileInsertableControlRod;

@SideOnly(Side.CLIENT)
public class TileRenderControlRod extends TileRenderObjModel<TileInsertableControlRod> {

	protected IModelCustom modelRods;

	public TileRenderControlRod() {
		super("controlRodStation.obj", "fissionReactor.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY);
		modelRods = AdvancedModelLoader.loadModel(new ResourceLocation(CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY + "controlRodRods.obj"));
	}

	@Override
	public void renderTileAt(TileInsertableControlRod tile, double x, double y, double z, float deltaFrame)
	{
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslated(x + 0.5, y + 0.5 - (tile.getFacing() == FaceDirection.UP ? 1 - (100 - tile.getInsertion()) / 120.0 : -1 + (100 - tile.getInsertion()) / 120.0), z + 0.5);
		GL11.glScaled(0.0625, 0.0625, 0.0625);
		if (tile.getFacing() == FaceDirection.DOWN)
		{
			GL11.glRotatef(180, 1, 0, 0);
		}
		bindTexture(model_texture);
		model_base.renderAll();
		modelRods.renderAll();
		if (tile.getFacing() == FaceDirection.DOWN)
		{
			GL11.glRotatef(-180, -1, 0, 0);
		}
		GL11.glScaled(1 / 0.0625, 1 / 0.0625, 1 / 0.0625);
		GL11.glTranslated(-(x + 0.5), -(y + 0.5 - (tile.getFacing() == FaceDirection.UP ? 1 - (100 - tile.getInsertion()) / 120.0 : -1 + (100 - tile.getInsertion()) / 120.0)), -(z + 0.5));
	}
}
