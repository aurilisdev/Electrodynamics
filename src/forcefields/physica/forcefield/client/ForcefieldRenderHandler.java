package physica.forcefield.client;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import physica.CoreReferences;
import physica.api.core.tile.ITileBase;
import physica.api.forcefield.IInvFortronTile;
import physica.forcefield.ForcefieldReferences;
import physica.forcefield.client.render.tile.RenderFortronBlockInfo;
import physica.library.client.render.TessellatorWrapper;
import physica.library.location.VectorLocation;

@SideOnly(Side.CLIENT)
public class ForcefieldRenderHandler {

	public static List<RenderFortronBlockInfo> renderSet = new ArrayList<>();

	@SubscribeEvent
	public void renderLast(RenderWorldLastEvent event)
	{
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(1, 1);
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ForcefieldReferences.DOMAIN, CoreReferences.TEXTURE_DIRECTORY + "blocks/fluids/fortron.png"));
		TessellatorWrapper.instance.startDrawingQuads();
		for (RenderFortronBlockInfo render : renderSet)
		{
			IInvFortronTile tile = (IInvFortronTile) render.getTile();
			double x = render.getX();
			double y = render.getY();
			double z = render.getZ();
			VectorLocation start = new VectorLocation((float) x + 0.5f, (float) y + 0.5f, (float) z + 0.5f);
			VectorLocation poolObject = new VectorLocation();
			EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
			float xx = (float) (player.prevPosX + (player.posX - player.prevPosX) * render.getDeltaFrame() - EntityFX.interpPosX);
			float yy = (float) (player.prevPosY + (player.posY - player.prevPosY) * render.getDeltaFrame() - EntityFX.interpPosY);
			float zz = (float) (player.prevPosZ + (player.posZ - player.prevPosZ) * render.getDeltaFrame() - EntityFX.interpPosZ);
			VectorLocation playerPos = new VectorLocation(xx, yy, zz);

			for (ITileBase base : tile.getFortronConnections())
			{
				TileEntity baseTile = (TileEntity) base;
				if (baseTile instanceof IInvFortronTile)
				{
					double diffX = baseTile.xCoord - tile.This().xCoord;
					double diffY = baseTile.yCoord - tile.This().yCoord;
					double diffZ = baseTile.zCoord - tile.This().zCoord;
					poolObject.set(start.x + diffX, start.y + diffY, start.z + diffZ);
					addBeamToTesselator(start, poolObject, playerPos, 0.05f);
				}
			}
		}
		TessellatorWrapper.instance.draw();
		GL11.glDisable(GL11.GL_BLEND);
		renderSet.clear();
	}

	public static void addBeamToTesselator(VectorLocation Start, VectorLocation End, VectorLocation Player, float width)
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
		TessellatorWrapper.instance.addVertexWithUV(Start.getX(), Start.getY(), Start.getZ(), 0.0D, 0.0D);
		Start.sub(Player);

		End.add(Player);
		TessellatorWrapper.instance.addVertexWithUV(End.getX(), End.getY(), End.getZ(), 1.0D, 1.0D);
		End.sub(Player);

		End.sub(Player);
		TessellatorWrapper.instance.addVertexWithUV(End.getX(), End.getY(), End.getZ(), 0.0D, 1.0D);
		End.add(Player);

		Start.sub(Player);
		TessellatorWrapper.instance.addVertexWithUV(Start.getX(), Start.getY(), Start.getZ(), Math.random(), 0.0D);
		Start.add(Player);
		Player.set(cachedX, cachedY, cachedZ);
		Start.add(Player);
	}

}
