package physica.library.client.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import physica.api.core.tile.ITileBase;
import physica.library.client.render.obj.PhysicaModelLoader;
import physica.library.client.render.obj.model.WavefrontObject;

public class TileRenderObjModel<T extends ITileBase> extends TileEntitySpecialRenderer {

	protected WavefrontObject wavefrontObject;
	protected ResourceLocation resourceTexture;

	public TileRenderObjModel(String objFile, String textureFile, String domain, String modelDirectory, String modelTextureDirectory) {
		wavefrontObject = PhysicaModelLoader.loadWavefrontModel(new ResourceLocation(domain, modelDirectory + objFile));
		resourceTexture = new ResourceLocation(domain, modelTextureDirectory + textureFile);
	}

	public void renderTileAt(T tile, double x, double y, double z, float deltaFrame) {
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
		switch (tile.getFacing()) {
		case NORTH:
			GL11.glRotatef(90, 0, 1, 0);
			break;
		case SOUTH:
			GL11.glRotatef(-90, 0, 1, 0);
			break;
		case EAST:
			GL11.glRotatef(-180, 0, 1, 0);
			break;
		default:
			break;
		}
		GL11.glScaled(1.0 / 0.0625, 1.0 / 0.0625, 1.0 / 0.0625);
		GL11.glTranslated(-(x + 0.5), -(y + 0.5), -(z + 0.5));
	}

	@Override
	public final void renderTileEntityAt(TileEntity tile, double x, double y, double z, float deltaFrame) {
		this.renderTileAt((T) tile, x, y, z, deltaFrame);
	}

	public static void renderFloatingText(String text, float x, float y, float z, int color) {
		RenderManager renderManager = RenderManager.instance;
		FontRenderer fontRenderer = renderManager.getFontRenderer();
		float scale = 0.027F;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		GL11.glTranslatef(x + 0.0F, y + 2.3F, z);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(-scale, -scale, scale);
		GL11.glDisable(2896);
		GL11.glDepthMask(false);
		GL11.glDisable(2929);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		TessellatorWrapper tessellator = TessellatorWrapper.instance;
		int yOffset = 0;
		GL11.glDisable(3553);
		tessellator.startDrawingQuads();
		int stringMiddle = fontRenderer.getStringWidth(text) / 2;
		tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.5F);
		tessellator.addVertex(-stringMiddle - 1, -1 + yOffset, 0.0D);
		tessellator.addVertex(-stringMiddle - 1, 8 + yOffset, 0.0D);
		tessellator.addVertex(stringMiddle + 1, 8 + yOffset, 0.0D);
		tessellator.addVertex(stringMiddle + 1, -1 + yOffset, 0.0D);
		tessellator.draw();
		GL11.glEnable(3553);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		fontRenderer.drawString(text, -fontRenderer.getStringWidth(text) / 2, yOffset, color);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		fontRenderer.drawString(text, -fontRenderer.getStringWidth(text) / 2, yOffset, color);
		GL11.glEnable(2896);
		GL11.glDisable(3042);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
