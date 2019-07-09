package physica.nuclear.client.render.tile;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import physica.CoreReferences;
import physica.nuclear.common.tile.TileTurbine;

@SideOnly(Side.CLIENT)
public class TileRenderTurbine extends TileEntitySpecialRenderer {

	protected IModelCustom		model_middle;
	protected IModelCustom		model_big;
	protected IModelCustom		model_base;
	protected ResourceLocation	model_texture;

	public TileRenderTurbine(String objFile, String textureFile) {
		model_base = AdvancedModelLoader.loadModel(new ResourceLocation(CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY + objFile));
		model_texture = new ResourceLocation(CoreReferences.DOMAIN, CoreReferences.MODEL_TEXTURE_DIRECTORY + textureFile);
		model_middle = AdvancedModelLoader.loadModel(new ResourceLocation(CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY + objFile.replace(".obj", "_middle.obj")));
		model_big = AdvancedModelLoader.loadModel(new ResourceLocation(CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY + objFile.replace(".obj", "_big.obj")));
	}

	public void renderTileAt(TileTurbine tile, double x, double y, double z, float deltaFrame)
	{
		if (!tile.hasMain() || tile.isMain())
		{
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
			if (!tile.hasMain())
			{
				GL11.glScaled(0.0925, 0.0525, 0.0925);
				bindTexture(model_texture);
				model_base.renderAll();
				if (tile.hasClientSpin())
				{
					GL11.glRotatef(tile.getTicksRunning() % 360 * 10, 0.0f, 1.0f, 0.0f);
				}
				model_middle.renderAll();
				if (tile.hasClientSpin())
				{
					GL11.glRotatef(-(tile.getTicksRunning() % 360 * 10), 0.0f, 1.0f, 0.0f);
				}
				GL11.glScaled(1 / 0.0925, 1 / 0.0525, 1 / 0.0925);
			} else if (tile.isMain())
			{
				GL11.glScaled(0.0725 * 4, 0.0525, 0.0725 * 4);
				bindTexture(model_texture);
				model_base.renderAll();
				if (tile.hasClientSpin())
				{
					GL11.glRotatef(tile.getTicksRunning() % 360 * 5, 0.0f, 1.0f, 0.0f);
				}
				model_middle.renderAll();
				if (tile.hasClientSpin())
				{
					GL11.glRotatef(-(tile.getTicksRunning() % 360 * 5), 0.0f, 1.0f, 0.0f);
				}
				GL11.glScaled(1 / (0.0725 * 4), 1 / 0.0525, 1 / (0.0725 * 4));
			}
			GL11.glTranslated(-(x + 0.5), -(y + 0.5), -(z + 0.5));
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float deltaFrame)
	{
		renderTileAt((TileTurbine) tile, x, y, z, deltaFrame);
	}
}
