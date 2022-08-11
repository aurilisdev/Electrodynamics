package physica.forcefield.client.render.item;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.glu.Sphere;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import physica.forcefield.PhysicaForcefields;
import physica.forcefield.client.render.tile.TileRenderFortronBlock;
import physica.library.client.render.ItemRenderObjModel;

@SideOnly(Side.CLIENT)
public class ItemRenderConstructor extends ItemRenderObjModel {

	public ItemRenderConstructor(String objFile, String textureFile, String domain, String modelDirectory, String modelTextureDirectory) {
		super(objFile, textureFile, domain, modelDirectory, modelTextureDirectory);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		super.renderItem(type, item, data);
		if (type == ItemRenderType.INVENTORY) {
			GL11.glPushMatrix();
			GL11.glTranslated(0.5D, 0.85D, 0.5D);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glShadeModel(7425);
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			int colour = PhysicaForcefields.DEFAULT_COLOR;
			int r = colour >> 16 & 0xff;
			int g = colour >> 8 & 0xff;
			int b = colour & 0xff;
			Minecraft.getMinecraft().renderEngine.bindTexture(TileRenderFortronBlock.previewTexture);
			int ticks = (int) Minecraft.getMinecraft().theWorld.getTotalWorldTime();
			GL11.glColor4f(r, g, b, (float) Math.sin(ticks / 10.0D) / 2.0F + 0.8F);
			GL11.glTranslatef(0.0F, (float) Math.sin(Math.toRadians(ticks * 3L)) / 7.0F, 0.0F);
			GL11.glRotatef(36.0F + ticks * 4L, 0.0F, 1.0F, 1.0F);
			GL11.glRotatef(ticks, 0.0F, 1.0F, 0.0F);
			Sphere sphere = new Sphere();
			sphere.setTextureFlag(true);
			sphere.draw(0.3f, 30, 30);
			GL11.glShadeModel(7424);
			GL11.glDisable(2848);
			GL11.glDisable(2881);
			GL11.glDisable(3042);
			GL11.glPopMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		}
	}
}
