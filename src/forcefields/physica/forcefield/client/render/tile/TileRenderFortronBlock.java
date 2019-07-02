package physica.forcefield.client.render.tile;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.glu.Sphere;

import codechicken.lib.vec.Vector3;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import physica.CoreReferences;
import physica.api.core.ITileBase;
import physica.api.forcefield.IInvFortronTile;
import physica.forcefield.client.render.model.ModelCube;
import physica.forcefield.common.tile.TileFortronFieldConstructor;
import physica.library.client.render.TileRenderObjModel;
import physica.library.location.Location;

@SideOnly(Side.CLIENT)
public class TileRenderFortronBlock<T extends IInvFortronTile & ITileBase> extends TileRenderObjModel<T> {

	protected ResourceLocation model_texture2;
	public static final ResourceLocation previewTexture = new ResourceLocation(CoreReferences.DOMAIN, CoreReferences.TEXTURE_DIRECTORY + "blocks/forcePreviewTexture.png");

	public TileRenderFortronBlock(String objFile) {
		super(objFile, "fortronMachineBase.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY);
		model_texture2 = new ResourceLocation(CoreReferences.DOMAIN, CoreReferences.MODEL_TEXTURE_DIRECTORY + "fortronMachineBasePowerless.png");
	}

	@Override
	public void renderTileAt(T tile, double x, double y, double z, float deltaFrame)
	{
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
		GL11.glScaled(0.0625f, 0.0625f, 0.0625f);
		bindTexture(tile.isActivated() ? model_texture : model_texture2);
		model_base.renderAll();
		GL11.glScaled(1 / 0.0625f, 1 / 0.0625f, 1 / 0.0625f);
		GL11.glTranslated(-(x + 0.5), -(y + 0.5), -(z + 0.5));
		if (tile.isActivated() && tile.canSendBeam())
		{
			Location start = new Location((float) x + 0.5f, (float) y + 0.5f, (float) z + 0.5f);
			Location poolObject = new Location();
			EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
			float xx = (float) (player.prevPosX + (player.posX - player.prevPosX) * deltaFrame - EntityFX.interpPosX);
			float yy = (float) (player.prevPosY + (player.posY - player.prevPosY) * deltaFrame - EntityFX.interpPosY);
			float zz = (float) (player.prevPosZ + (player.posZ - player.prevPosZ) * deltaFrame - EntityFX.interpPosZ);
			Location playerPos = new Location(xx, yy, zz);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(1, 1);
			GL11.glColor3f(1.5f, 1.2f, 1.5f);
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(CoreReferences.DOMAIN, CoreReferences.TEXTURE_DIRECTORY + "blocks/fluids/fortron.png"));
			Tessellator.instance.startDrawingQuads();
			for (ITileBase base : tile.getFortronConnections())
			{
				TileEntity baseTile = (TileEntity) base;
				if (baseTile instanceof IInvFortronTile)
				{
					double diffX = baseTile.xCoord - tile.This().xCoord;
					double diffY = baseTile.yCoord - tile.This().yCoord;
					double diffZ = baseTile.zCoord - tile.This().zCoord;
					poolObject.set(start.x + diffX, start.y + diffY, start.z + diffZ);
					addBeamToTesselator(start, poolObject, playerPos, 0.15f);
				}
			}
			Tessellator.instance.draw();
			GL11.glDisable(GL11.GL_BLEND);
		}
		GL11.glPopMatrix();
		if (tile instanceof TileFortronFieldConstructor)
		{
			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5D, y + 1.35D, z + 0.5D);
			int mode = ((TileFortronFieldConstructor) tile).getProjectorMode();
			if (mode != -1)
			{
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glShadeModel(7425);
				GL11.glEnable(3042);
				GL11.glBlendFunc(770, 771);
				int colour = ((TileFortronFieldConstructor) tile).fieldColorMultiplier();
				int r = colour >> 16 & 0xff;
				int g = colour >> 8 & 0xff;
				int b = colour & 0xff;
				Minecraft.getMinecraft().renderEngine.bindTexture(previewTexture);
				GL11.glColor4f(r, g, b,
						(float) Math.sin(tile.getTicksRunning() / 10.0D) / 2.0F + 0.8F);
				GL11.glTranslatef(0.0F, (float) Math.sin(Math.toRadians(tile.getTicksRunning() * 3L)) / 7.0F, 0.0F);
				GL11.glRotatef(tile.getTicksRunning() * 4L, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(36.0F + tile.getTicksRunning() * 4L, 0.0F, 1.0F, 1.0F);
				if (mode == 0 || mode == 1)
				{
					Sphere sphere = new Sphere();
					sphere.setTextureFlag(true);
					sphere.draw(0.3f, 20, 10);

				} else if (mode == 2)
				{
					GL11.glScalef(0.5F, 0.5F, 0.5F);
					ModelCube.INSTANCE.render();
				} else if (mode == 3)
				{
					Tessellator tessellator = Tessellator.instance;
					GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
					float height = 0.4F;
					float width = 0.3F;
					int uvMaxX = 1;
					int uvMaxY = 1;
					Vector3 translation = new Vector3(0.0D, -(height / 1.33), 0.0D);
					tessellator.startDrawing(6);
					tessellator.addVertexWithUV(0.0D + translation.x, 0.0D + translation.y, 0.0D + translation.z, 0.0D, 0.0D);
					tessellator.addVertexWithUV(width + translation.x, height + translation.y, -width + translation.z, -uvMaxX, -uvMaxY);
					tessellator.addVertexWithUV(width + translation.x, height + translation.y, width + translation.z, -uvMaxX, uvMaxY);
					tessellator.addVertexWithUV(-width + translation.x, height + translation.y, width + translation.z, uvMaxX, uvMaxY);
					tessellator.addVertexWithUV(-width + translation.x, height + translation.y, -width + translation.z, uvMaxX, -uvMaxY);
					tessellator.addVertexWithUV(width + translation.x, height + translation.y, -width + translation.z, -uvMaxX, -uvMaxY);
					tessellator.draw();
					tessellator.startDrawing(6);
					tessellator.addVertexWithUV(width, height + translation.y, -width, uvMaxX, -uvMaxY);
					tessellator.addVertexWithUV(-width, height + translation.y, -width, -uvMaxX, -uvMaxY);
					tessellator.addVertexWithUV(-width, height + translation.y, width, -uvMaxX, uvMaxY);
					tessellator.addVertexWithUV(width, height + translation.y, width, uvMaxX, uvMaxY);
					tessellator.draw();
					GL11.glBegin(GL11.GL_QUADS);
					GL11.glVertex2f(100, 100);
					GL11.glVertex2f(100 + 200, 100);
					GL11.glVertex2f(100 + 200, 100 + 200);
					GL11.glVertex2f(100, 100 + 200);
					GL11.glEnd();

				}
				GL11.glShadeModel(7424);
				GL11.glDisable(2848);
				GL11.glDisable(2881);
				GL11.glDisable(3042);
			}
			GL11.glPopMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		}
	}

	public static void addBeamToTesselator(Location Start, Location End, Location Player, float width)
	{
		Start.sub(Player);
		double cachedX = Player.x;
		double cachedY = Player.y;
		double cachedZ = Player.z;
		double crossX = Start.y * End.z - Start.z * End.y;
		double crossY = Start.z * End.x - Start.x * End.z;
		double crossZ = Start.x * End.y - Start.y * End.x;
		Player.set(crossX, crossY, crossZ);
		float fl = Player.norm();
		Player.set(Player.x / fl, Player.y / fl, Player.z / fl);
		Player.mul(width);
		Start.add(Player);
		Tessellator.instance.addVertexWithUV(Start.getX(), Start.getY(), Start.getZ(), 0.0D, 0.0D);
		Start.sub(Player);

		End.add(Player);
		Tessellator.instance.addVertexWithUV(End.getX(), End.getY(), End.getZ(), 1.0D, 1.0D);
		End.sub(Player);

		End.sub(Player);
		Tessellator.instance.addVertexWithUV(End.getX(), End.getY(), End.getZ(), 0.0D, 1.0D);
		End.add(Player);

		Start.sub(Player);
		Tessellator.instance.addVertexWithUV(Start.getX(), Start.getY(), Start.getZ(), 1.0D, 0.0D);
		Start.add(Player);
		Player.set(cachedX, cachedY, cachedZ);
		Start.add(Player);
	}

}
