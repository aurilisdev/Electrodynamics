package physica.missiles.client.render.entity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import physica.CoreReferences;
import physica.library.client.render.TessellatorWrapper;
import physica.missiles.MissileReferences;
import physica.missiles.common.entity.EntityFragment;
import physica.missiles.common.explosive.blast.types.EnumShrapnel;

@SideOnly(Side.CLIENT)
public class RenderShrapnelFragment extends Render {

	public static final ResourceLocation TEXTURE_FILE = new ResourceLocation(MissileReferences.DOMAIN, CoreReferences.MODEL_TEXTURE_DIRECTORY + "fragment.png");

	public void renderFragment(EntityFragment fragment, double x, double y, double z, float par8, float par9)
	{
		TessellatorWrapper wrapper = TessellatorWrapper.instance;
		if (fragment.type == EnumShrapnel.ANVIL)
		{
			GL11.glPushMatrix();
			GL11.glTranslatef((float) x, (float) y, (float) z);
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(FMLClientHandler.instance().getClient().renderEngine.getResourceLocation(0));
			Block block = Blocks.anvil;
			World world = fragment.worldObj;
			GL11.glDisable(GL11.GL_LIGHTING);

			field_147909_c.blockAccess = world;

			wrapper.startDrawingQuads();
			wrapper.setTranslation((-MathHelper.floor_double(fragment.posX)) - 0.5F, (-MathHelper.floor_double(fragment.posY)) - 0.5F, (-MathHelper.floor_double(fragment.posZ)) - 0.5F);
			field_147909_c.renderBlockByRenderType(block, MathHelper.floor_double(fragment.posX), MathHelper.floor_double(fragment.posY), MathHelper.floor_double(fragment.posZ));
			wrapper.setTranslation(0.0D, 0.0D, 0.0D);
			wrapper.draw();

			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		} else
		{
			bindTexture(TEXTURE_FILE);
			GL11.glPushMatrix();
			GL11.glTranslatef((float) x, (float) y, (float) z);
			GL11.glRotatef(fragment.prevRotationYaw + (fragment.rotationYaw - fragment.prevRotationYaw) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(fragment.prevRotationPitch + (fragment.rotationPitch - fragment.prevRotationPitch) * par9, 0.0F, 0.0F, 1.0F);
			float u3 = 0.0F;
			float u4 = 0.5F;
			float v3 = 0;
			float v4 = 5 / 32.0F;
			float u1 = 0.0F;
			float u2 = 0.15625F;
			float v1 = 5 / 32.0F;
			float v2 = 10 / 32.0F;
			float var20 = 0.05625F;
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			float var21 = fragment.projectileSPin - par9;

			if (var21 > 0.0F)
			{
				float var22 = -MathHelper.sin(var21 * 3.0F) * var21;
				GL11.glRotatef(var22, 0.0F, 0.0F, 1.0F);
			}

			GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
			GL11.glScalef(var20, var20, var20);
			GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
			GL11.glNormal3f(var20, 0.0F, 0.0F);
			wrapper.startDrawingQuads();
			wrapper.addVertexWithUV(-7.0D, -2.0D, -2.0D, u1, v1);
			wrapper.addVertexWithUV(-7.0D, -2.0D, 2.0D, u2, v1);
			wrapper.addVertexWithUV(-7.0D, 2.0D, 2.0D, u2, v2);
			wrapper.addVertexWithUV(-7.0D, 2.0D, -2.0D, u1, v2);
			wrapper.draw();
			GL11.glNormal3f(-var20, 0.0F, 0.0F);
			wrapper.startDrawingQuads();
			wrapper.addVertexWithUV(-7.0D, 2.0D, -2.0D, u1, v1);
			wrapper.addVertexWithUV(-7.0D, 2.0D, 2.0D, u2, v1);
			wrapper.addVertexWithUV(-7.0D, -2.0D, 2.0D, u2, v2);
			wrapper.addVertexWithUV(-7.0D, -2.0D, -2.0D, u1, v2);
			wrapper.draw();

			for (int var23 = 0; var23 < 4; ++var23)
			{
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glNormal3f(0.0F, 0.0F, var20);
				wrapper.startDrawingQuads();
				wrapper.addVertexWithUV(-8.0D, -2.0D, 0.0D, u3, v3);
				wrapper.addVertexWithUV(8.0D, -2.0D, 0.0D, u4, v3);
				wrapper.addVertexWithUV(8.0D, 2.0D, 0.0D, u4, v4);
				wrapper.addVertexWithUV(-8.0D, 2.0D, 0.0D, u3, v4);
				wrapper.draw();
			}

			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
		}
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderFragment((EntityFragment) par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
}
