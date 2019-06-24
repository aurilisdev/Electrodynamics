package physica.forcefield.client.render.tile;

import java.util.HashSet;
import java.util.Set;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

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
import physica.library.client.render.TileRenderObjModel;
import physica.library.location.Location;
import scala.util.Random;

@SideOnly(Side.CLIENT)
public class TileRenderFortronBlock<T extends IInvFortronTile & ITileBase> extends TileRenderObjModel<T> {

	protected ResourceLocation model_texture2;

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
		if (tile.isActivated() && tile.canSendBeam()) {
			Location start = new Location((float) x + 0.5f, (float) y + 0.5f, (float) z + 0.5f);
			Set<Location> renders = new HashSet<>();
			for (ITileBase base : tile.getFortronConnections()) {
				Location vector = base.getLocation().Location();
				TileEntity vectorTile = tile.This().getWorldObj().getTileEntity((int) vector.x, (int) vector.y, (int) vector.z);
				if (vectorTile instanceof IInvFortronTile) {
					double diffX = vector.x - tile.This().xCoord;
					double diffY = vector.y - tile.This().yCoord;
					double diffZ = vector.z - tile.This().zCoord;
					renders.add(new Location(start.x + diffX, start.y + diffY, start.z + diffZ));
				}
			}
			drawBeams(renders, start, 0.15f, deltaFrame);
		}
		GL11.glPopMatrix();

	}

	public static void drawBeams(Set<Location> beams, Location start, float width, float deltaFrame)
	{
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		float xx = (float) (player.prevPosX + (player.posX - player.prevPosX) * deltaFrame - EntityFX.interpPosX);
		float yy = (float) (player.prevPosY + (player.posY - player.prevPosY) * deltaFrame - EntityFX.interpPosY);
		float zz = (float) (player.prevPosZ + (player.posZ - player.prevPosZ) * deltaFrame - EntityFX.interpPosZ);
		Location playerPos = new Location(xx, yy, zz);

		Tessellator.instance.startDrawingQuads();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(1, 1);
		GL11.glColor3f(1.5f, 1.2f, 1.5f);
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(CoreReferences.DOMAIN, CoreReferences.TEXTURE_DIRECTORY + "blocks/fluids/fortron.png"));
		for (Location end : beams) {
			addBeamToTesselator(start, end, playerPos, width);
		}
		Tessellator.instance.draw();
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void addBeamToTesselator(Location Start, Location End, Location Player, float width)
	{
		Location PlayerStart = Location.Sub(Start, Player);
		Location StartEnd = Location.Sub(End, Start);

		Location normal = Location.Cross(PlayerStart, StartEnd);
		normal = normal.normalize();

		Location half = Location.Mul(normal, width);
		Location p1 = Location.Add(Start, half);
		Location p2 = Location.Sub(Start, half);
		Location p3 = Location.Add(End, half);
		Location p4 = Location.Sub(End, half);

		addQuadVertex(p1, p3, p4, p2);
		addQuadVertex(p1, p3, p4, p2); // Doubles beam strength
		addQuadVertex(p1, p3, p4, p2); // Adds 50% more after doubling

	}

	private static void addQuadVertex(Location p1, Location p2, Location p3, Location p4)
	{
		Random rand = new Random();
		Tessellator.instance.addVertexWithUV(p1.getX(), p1.getY(), p1.getZ(), 0.0D + rand.nextDouble(), 0.0D + rand.nextDouble());
		Tessellator.instance.addVertexWithUV(p2.getX(), p2.getY(), p2.getZ(), 1.0D - rand.nextDouble(), 0.0D + rand.nextDouble());
		Tessellator.instance.addVertexWithUV(p3.getX(), p3.getY(), p3.getZ(), 1.0D - rand.nextDouble(), 1.0D - rand.nextDouble());
		Tessellator.instance.addVertexWithUV(p4.getX(), p4.getY(), p4.getZ(), 0.0D + rand.nextDouble(), 1.0D - rand.nextDouble());
	}

}
