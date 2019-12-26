package physica.missiles.client.render.entity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import physica.missiles.common.MissileBlockRegister;
import physica.missiles.common.entity.EntityPrimedExplosive;

@SideOnly(Side.CLIENT)
public class RenderEntityPrimedExplosive extends Render {

	private RenderBlocks blockRenderer = new RenderBlocks();

	public RenderEntityPrimedExplosive() {
		shadowSize = 0.5F;
	}

	@Override
	public void doRender(Entity par1Entity, double x, double y, double z, float par8, float par9)
	{
		EntityPrimedExplosive entityExplosive = (EntityPrimedExplosive) par1Entity;
		if (entityExplosive.fuse > 0)
		{
			GL11.glPushMatrix();
			GL11.glTranslatef((float) x, (float) y, (float) z);
			float f2;

			if (entityExplosive.fuse - par9 + 1.0F < 10.0F)
			{
				f2 = 1.0F - (entityExplosive.fuse - par9 + 1.0F) / 10.0F;

				if (f2 < 0.0F)
				{
					f2 = 0.0F;
				}

				if (f2 > 1.0F)
				{
					f2 = 1.0F;
				}

				f2 *= f2;
				f2 *= f2;
				float f3 = 1.0F + f2 * 0.3F;
				GL11.glScalef(f3, f3, f3);
			}

			f2 = (1.0F - (entityExplosive.fuse - par9 + 1.0F) / 100.0F) * 0.8F;
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(FMLClientHandler.instance().getClient().renderEngine.getResourceLocation(0));
			blockRenderer.renderBlockAsItem(MissileBlockRegister.primedBlock, entityExplosive.explosiveID, entityExplosive.getBrightness(par9));
			if (entityExplosive.fuse / 5 % 2 == 0)
			{
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, f2);
				blockRenderer.renderBlockAsItem(MissileBlockRegister.primedBlock, entityExplosive.explosiveID, 1.0F);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
			}

			GL11.glPopMatrix();
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}

}
