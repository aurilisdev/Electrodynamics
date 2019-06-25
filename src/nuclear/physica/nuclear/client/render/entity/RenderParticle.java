package physica.nuclear.client.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderParticle extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		Tessellator tessellator = Tessellator.instance;

		float age = entity.ticksExisted;

		while (age > 150)
		{
			age -= 100;
		}

		RenderHelper.disableStandardItemLighting();
		final float f = (5 + age) / 200;
		float f1 = 0;

		if (f > 0.8)
		{
			f1 = (f - 0.8F) / 0.2F;
		}

		final Random random = new Random(432L);

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z + 0.3);
		GL11.glScaled(0.03, 0.03, 0.03);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDepthMask(false);
		GL11.glPushMatrix();
		GL11.glTranslatef(0, -1, -2);

		for (int i = 0; i < (f + f * f) / 2 * 60; i++)
		{
			GL11.glRotated(random.nextFloat() * 360, 1, 0, 0);
			GL11.glRotated(random.nextFloat() * 360, 0, 1, 0);
			GL11.glRotated(random.nextFloat() * 360, 0, 0, 1);
			GL11.glRotated(random.nextFloat() * 360, 1, 0, 0);
			GL11.glRotated(random.nextFloat() * 360, 0, 1, 0);
			GL11.glRotated(random.nextFloat() * 360 + f * 90, 0, 0, 1);
			tessellator.startDrawing(6);
			final float f2 = random.nextFloat() * 20 + 5 + f1 * 10;
			final float f3 = random.nextFloat() * 2 + 1 + f1 * 2;
			tessellator.setColorRGBA_I(16777215, (int) (255 * (1 - f1)));
			tessellator.addVertex(0, 0, 0);
			tessellator.setColorRGBA_I(0, 0);
			tessellator.addVertex(-0.866 * f3, f2, -0.5 * f3);
			tessellator.addVertex(0.866 * f3, f2, -0.5 * f3);
			tessellator.addVertex(0, f2, 1 * f3);
			tessellator.addVertex(-0.866 * f3, f2, -0.5 * f3);

			tessellator.draw();
		}

		GL11.glPopMatrix();
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		RenderHelper.enableStandardItemLighting();
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
}
