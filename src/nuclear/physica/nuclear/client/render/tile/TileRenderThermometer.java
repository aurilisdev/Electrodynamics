package physica.nuclear.client.render.tile;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import physica.nuclear.common.tile.TileFissionReactor;
import physica.nuclear.common.tile.TileThermometer;

@SideOnly(Side.CLIENT)
public class TileRenderThermometer extends TileEntitySpecialRenderer {

	public void renderTileAt(TileThermometer tile, double x, double y, double z, float deltaFrame)
	{
		for (int side = 2; side < 6; side++)
		{
			TileEntity entity = tile.getWorldObj().getTileEntity(tile.xCoord, tile.yCoord + 1, tile.zCoord);
			int temp = Math.round(entity instanceof TileFissionReactor ? ((TileFissionReactor) entity).getTemperature() : 0);
			GL11.glPushMatrix();
			GL11.glPolygonOffset(-10.0F, -10.0F);
			GL11.glEnable(32823);

			float displayWidth = 0.875F;
			float displayHeight = 0.875F;
			GL11.glTranslatef((float) x, (float) y, (float) z);
			switch (side) {
			case 2:
				GL11.glTranslatef(1.0F, 1.0F, 1.0F);
				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				break;
			case 3:
				GL11.glTranslatef(0.0F, 1.0F, 0.0F);
				GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				break;
			case 4:
				GL11.glTranslatef(1.0F, 1.0F, 0.0F);
				GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				break;
			case 5:
				GL11.glTranslatef(0.0F, 1.0F, 1.0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				break;

			}
			GL11.glTranslatef(displayWidth / 2.0F, 1.0F, displayHeight / 2.0F);
			GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
			int maxWidth = 1;
			String wenDu = temp + " C";
			int meta = tile.getBlockMetadata();
			String zhuYiWenDu = "Warn: " + (meta == 0 ? "4500" : meta == 1 ? "4000" : meta == 2 ? "3500" : meta == 3 ? "3000" : "2500");
			maxWidth = Math.max(fontRenderer.getStringWidth(wenDu), maxWidth);
			maxWidth = Math.max(fontRenderer.getStringWidth(zhuYiWenDu), maxWidth);
			maxWidth += 4;
			int lineHeight = fontRenderer.FONT_HEIGHT + 2;
			int requiredHeight = lineHeight * 1;
			float scaleX = displayWidth / maxWidth;
			float scaleY = displayHeight / requiredHeight;
			float scale = (float) (Math.min(scaleX, scaleY) * 0.8D);
			GL11.glScalef(scale, -scale, scale);
			GL11.glDepthMask(false);

			int realHeight = (int) Math.floor(displayHeight / scale);
			int realWidth = (int) Math.floor(displayWidth / scale);

			int offsetX = (realWidth - maxWidth) / 2 + 2;
			int offsetY = (realHeight - requiredHeight) / 2;

			GL11.glDisable(2896);
			fontRenderer.drawString(wenDu, offsetX - realWidth / 2, 1 + offsetY - realHeight / 2 + 0 * lineHeight, 1);
			fontRenderer.drawString(zhuYiWenDu, offsetX - realWidth / 2, 1 + offsetY - realHeight / 2 + 1 * lineHeight, 1);
			GL11.glEnable(2896);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDepthMask(true);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(32823);
			GL11.glPopMatrix();
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float deltaFrame)
	{
		renderTileAt((TileThermometer) tile, x, y, z, deltaFrame);
	}
}
