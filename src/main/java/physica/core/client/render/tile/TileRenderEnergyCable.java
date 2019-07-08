package physica.core.client.render.tile;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cofh.api.energy.IEnergyConnection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import physica.CoreReferences;
import physica.core.common.block.BlockEnergyCable.EnumEnergyCable;

@SideOnly(Side.CLIENT)
public class TileRenderEnergyCable extends TileEntitySpecialRenderer {

	public static final ResourceLocation[] model_texture = new ResourceLocation[EnumEnergyCable.values().length];

	public static final float pixel = 1 / 16f;
	public static final float pixelElevenTwo = 11 * pixel / 2;
	public static final float texPixel = 1 / 32f;
	static
	{
		for (EnumEnergyCable type : EnumEnergyCable.values())
		{
			model_texture[type.ordinal()] = new ResourceLocation(CoreReferences.DOMAIN, CoreReferences.MODEL_TEXTURE_DIRECTORY + type.getName() + "Cable.png");
		}
	}

	@Override
	public final void renderTileEntityAt(TileEntity tile, double x, double y, double z, float deltaFrame)
	{
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		RenderHelper.disableStandardItemLighting();
		GL11.glTranslated(x, y, z);
		int meta = tile.getBlockMetadata();
		bindTexture(model_texture[Math.max(0, Math.min(model_texture.length - 1, meta))]);
		boolean isCross = false;
		boolean finished = false;
		int connections = 0;
		int differentLocations = 0;
		ForgeDirection last = ForgeDirection.UNKNOWN;
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
		{
			TileEntity sideTile = tile.getWorldObj().getTileEntity(tile.xCoord + dir.offsetX, tile.yCoord + dir.offsetY, tile.zCoord + dir.offsetZ);
			if (sideTile instanceof IEnergyConnection && ((IEnergyConnection) sideTile).canConnectEnergy(dir.getOpposite()))
			{
				drawConnection(dir);
				if (sideTile.getBlockMetadata() == meta)
				{
					connections++;
				} else
				{
					differentLocations++;
				}
				if (!finished)
				{
					if (isCross)
					{
						if (dir != last.getOpposite())
						{
							isCross = false;
							finished = true;
						}
					} else
					{
						last = dir;
						isCross = true;
					}
				}
			}
		}
		if (isCross && connections != 1 && differentLocations <= 0)
		{
			GL11.glTranslated(last.offsetX * pixelElevenTwo, last.offsetY * pixelElevenTwo, last.offsetZ * pixelElevenTwo);
			drawConnection(last.getOpposite());
		} else
		{
			drawCore();
		}
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	public static void drawConnection(ForgeDirection direction)
	{
		GL11.glTranslatef(0.5f, 0.5f, 0.5f);
		if (direction == ForgeDirection.DOWN)
		{
			GL11.glRotatef(180, 1, 0, 0);
		} else if (direction == ForgeDirection.NORTH)
		{
			GL11.glRotatef(-90, 1, 0, 0);
		} else if (direction == ForgeDirection.SOUTH)
		{
			GL11.glRotatef(90, 1, 0, 0);
		} else if (direction == ForgeDirection.EAST)
		{
			GL11.glRotatef(-90, 0, 0, 1);
		} else if (direction == ForgeDirection.WEST)
		{
			GL11.glRotatef(90, 0, 0, 1);
		}
		GL11.glTranslatef(-0.5f, -0.5f, -0.5f);

		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		float fiveTex = 5 * texPixel;

		tess.addVertexWithUV(1 - pixelElevenTwo, 1 - pixelElevenTwo, 1 - pixelElevenTwo, fiveTex, fiveTex);
		tess.addVertexWithUV(1 - pixelElevenTwo, 1, 1 - pixelElevenTwo, fiveTex * 2, fiveTex);
		tess.addVertexWithUV(pixelElevenTwo, 1, 1 - pixelElevenTwo, fiveTex * 2, 0);
		tess.addVertexWithUV(pixelElevenTwo, 1 - pixelElevenTwo, 1 - pixelElevenTwo, fiveTex, 0);

		tess.addVertexWithUV(pixelElevenTwo, 1 - pixelElevenTwo, pixelElevenTwo, fiveTex, 0);
		tess.addVertexWithUV(pixelElevenTwo, 1, pixelElevenTwo, fiveTex * 2, 0);
		tess.addVertexWithUV(1 - pixelElevenTwo, 1, pixelElevenTwo, fiveTex * 2, fiveTex);
		tess.addVertexWithUV(1 - pixelElevenTwo, 1 - pixelElevenTwo, pixelElevenTwo, fiveTex, fiveTex);

		tess.addVertexWithUV(1 - pixelElevenTwo, 1 - pixelElevenTwo, pixelElevenTwo, fiveTex, 0);
		tess.addVertexWithUV(1 - pixelElevenTwo, 1, pixelElevenTwo, fiveTex * 2, 0);
		tess.addVertexWithUV(1 - pixelElevenTwo, 1, 1 - pixelElevenTwo, fiveTex * 2, fiveTex);
		tess.addVertexWithUV(1 - pixelElevenTwo, 1 - pixelElevenTwo, 1 - pixelElevenTwo, fiveTex, fiveTex);

		tess.addVertexWithUV(pixelElevenTwo, 1 - pixelElevenTwo, 1 - pixelElevenTwo, fiveTex, fiveTex);
		tess.addVertexWithUV(pixelElevenTwo, 1, 1 - pixelElevenTwo, fiveTex * 2, fiveTex);
		tess.addVertexWithUV(pixelElevenTwo, 1, pixelElevenTwo, fiveTex * 2, 0);
		tess.addVertexWithUV(pixelElevenTwo, 1 - pixelElevenTwo, pixelElevenTwo, fiveTex, 0);

		tess.draw();
		GL11.glTranslatef(0.5f, 0.5f, 0.5f);
		if (direction == ForgeDirection.DOWN)
		{
			GL11.glRotatef(-180, 1, 0, 0);
		} else if (direction == ForgeDirection.NORTH)
		{
			GL11.glRotatef(90, 1, 0, 0);
		} else if (direction == ForgeDirection.SOUTH)
		{
			GL11.glRotatef(-90, 1, 0, 0);
		} else if (direction == ForgeDirection.EAST)
		{
			GL11.glRotatef(90, 0, 0, 1);
		} else if (direction == ForgeDirection.WEST)
		{
			GL11.glRotatef(-90, 0, 0, 1);
		}
		GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
	}

	public static void drawCore()
	{
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		float fiveTex = 5 * texPixel;
		tess.addVertexWithUV(1 - pixelElevenTwo, pixelElevenTwo, 1 - pixelElevenTwo, fiveTex, fiveTex);
		tess.addVertexWithUV(1 - pixelElevenTwo, 1 - pixelElevenTwo, 1 - pixelElevenTwo, fiveTex, 0);
		tess.addVertexWithUV(pixelElevenTwo, 1 - pixelElevenTwo, 1 - pixelElevenTwo, 0, 0);
		tess.addVertexWithUV(pixelElevenTwo, pixelElevenTwo, 1 - pixelElevenTwo, 0, fiveTex);

		tess.addVertexWithUV(1 - pixelElevenTwo, pixelElevenTwo, pixelElevenTwo, fiveTex, fiveTex);
		tess.addVertexWithUV(1 - pixelElevenTwo, 1 - pixelElevenTwo, pixelElevenTwo, fiveTex, 0);
		tess.addVertexWithUV(1 - pixelElevenTwo, 1 - pixelElevenTwo, 1 - pixelElevenTwo, 0, 0);
		tess.addVertexWithUV(1 - pixelElevenTwo, pixelElevenTwo, 1 - pixelElevenTwo, 0, fiveTex);

		tess.addVertexWithUV(pixelElevenTwo, pixelElevenTwo, pixelElevenTwo, 0, fiveTex);
		tess.addVertexWithUV(pixelElevenTwo, 1 - pixelElevenTwo, pixelElevenTwo, 0, 0);
		tess.addVertexWithUV(1 - pixelElevenTwo, 1 - pixelElevenTwo, pixelElevenTwo, fiveTex, 0);
		tess.addVertexWithUV(1 - pixelElevenTwo, pixelElevenTwo, pixelElevenTwo, fiveTex, fiveTex);

		tess.addVertexWithUV(pixelElevenTwo, pixelElevenTwo, 1 - pixelElevenTwo, 0, fiveTex);
		tess.addVertexWithUV(pixelElevenTwo, 1 - pixelElevenTwo, 1 - pixelElevenTwo, 0, 0);
		tess.addVertexWithUV(pixelElevenTwo, 1 - pixelElevenTwo, pixelElevenTwo, fiveTex, 0);
		tess.addVertexWithUV(pixelElevenTwo, pixelElevenTwo, pixelElevenTwo, fiveTex, fiveTex);

		tess.addVertexWithUV(1 - pixelElevenTwo, 1 - pixelElevenTwo, 1 - pixelElevenTwo, 0, fiveTex);
		tess.addVertexWithUV(1 - pixelElevenTwo, 1 - pixelElevenTwo, pixelElevenTwo, 0, 0);
		tess.addVertexWithUV(pixelElevenTwo, 1 - pixelElevenTwo, pixelElevenTwo, fiveTex, 0);
		tess.addVertexWithUV(pixelElevenTwo, 1 - pixelElevenTwo, 1 - pixelElevenTwo, fiveTex, fiveTex);

		tess.addVertexWithUV(pixelElevenTwo, pixelElevenTwo, 1 - pixelElevenTwo, fiveTex, fiveTex);
		tess.addVertexWithUV(pixelElevenTwo, pixelElevenTwo, pixelElevenTwo, fiveTex, 0);
		tess.addVertexWithUV(1 - pixelElevenTwo, pixelElevenTwo, pixelElevenTwo, 0, 0);
		tess.addVertexWithUV(1 - pixelElevenTwo, pixelElevenTwo, 1 - pixelElevenTwo, 0, fiveTex);

		tess.draw();
	}
}
