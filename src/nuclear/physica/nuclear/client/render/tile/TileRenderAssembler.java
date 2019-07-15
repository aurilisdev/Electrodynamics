package physica.nuclear.client.render.tile;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import physica.CoreReferences;
import physica.api.core.utilities.IBaseUtilities;
import physica.library.client.render.TileRenderObjModel;
import physica.nuclear.common.tile.TileQuantumAssembler;

@SideOnly(Side.CLIENT)
public class TileRenderAssembler extends TileRenderObjModel<TileQuantumAssembler> implements IBaseUtilities {

	protected static final ResourceLocation	enderDragonCrystalBeamTextures	= new ResourceLocation("textures/entity/endercrystal/endercrystal_beam.png");
	protected static final RenderItem		renderItem						= (RenderItem) RenderManager.instance.getEntityClassRenderObject(EntityItem.class);

	public TileRenderAssembler(String objFile, String textureFile) {
		super(objFile, textureFile, CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY);
	}

	protected float		current		= 0, lastOperating = 0;
	protected boolean	goesDown	= false;

	@Override
	public void renderTileAt(TileQuantumAssembler tile, double x, double y, double z, float deltaFrame)
	{
		super.renderTileAt(tile, x, y, z, deltaFrame);
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y + 0.4, z + 0.5);
		GL11.glScalef(0.25f, 0.25f, 0.25f);
		EntityItem entity = tile.getEntityItem();
		if (entity != null)
		{
			entity.getEntityItem().stackSize = Math.max(1, Math.min(5, entity.getEntityItem().stackSize));
			entity.age = 1;
			entity.hoverStart = 0;
			renderItem.doRender(tile.getEntityItem(), 0, 0, 0, 0, 5);
			float down = 4f;
			float forward = 0.125f;
			GL11.glScalef(1 / down, 0.333f, 1 / down);
			if (tile.getOperatingTicks() > 0)
			{
				double centerX = tile.xCoord + tile.getFacing().offsetX * down * forward + 0.5 * down, centerY = tile.yCoord + 0.4 * down, centerZ = tile.xCoord + tile.getFacing().offsetX * down * forward + 0.5 * down;

				double targetX = tile.xCoord + tile.getFacing().offsetX * down * forward + 0.5 * down + tile.getWorldObj().rand.nextFloat() / down - 1 / down / 1.333, targetY = tile.yCoord + 0.9 * down * 2,
						targetZ = tile.xCoord + tile.getFacing().offsetX * down * forward + 0.5 * down + tile.getWorldObj().rand.nextFloat() / down - 1 / down / 1.333;

				double relX = tile.getFacing().offsetX * down * forward, relY = 0.6 + tile.getWorldObj().rand.nextFloat() / down, relZ = tile.getFacing().offsetZ * down * forward;
				float dirX = (float) (targetX - centerX);
				float dirY = (float) (targetY - centerY);
				float dirZ = (float) (targetZ - centerZ);
				float zCorner = MathHelper.sqrt_float(dirX * dirX + dirY * dirY + dirZ * dirZ);
				GL11.glPushMatrix();
				GL11.glTranslatef((float) relX, (float) relY, (float) relZ);
				GL11.glRotatef((float) -Math.atan2(dirZ, dirX) * 180.0F / (float) Math.PI - 90.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef((float) -Math.atan2(MathHelper.sqrt_float(dirX * dirX + dirZ * dirZ), dirY) * 180.0F / (float) Math.PI - 90.0F, 1.0F, 0.0F, 0.0F);
				Tessellator tessellator = Tessellator.instance;
				RenderHelper.disableStandardItemLighting();
				GL11.glDisable(GL11.GL_CULL_FACE);
				bindTexture(enderDragonCrystalBeamTextures);
				GL11.glColor3d(1F, 0.0F, 0.0F);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				for (int i = 0; i < 5; i++)
				{
					float u1 = -(tile.getOperatingTicks() * deltaFrame * tile.getWorldObj().rand.nextFloat() * (i + 1) * 0.005F);
					float u2 = MathHelper.sqrt_float(dirX * dirX + dirY * dirY + dirZ * dirZ) / 32.0F + u1;
					tessellator.startDrawing(5);
					byte total = 8;
					for (int j = 0; j <= total; ++j)
					{
						float xCorner = 0.2F * (MathHelper.sin(j % total * (float) Math.PI * 2.0F / total) * 0.75F);
						float yCorner = 0.2F * (MathHelper.cos(j % total * (float) Math.PI * 2.0F / total) * 0.75F);
						float u = j % total * 1.0F / total;
						tessellator.setColorOpaque(200 - Math.abs(120 / 2 - tile.getOperatingTicks() % 10 * 12), 0, 0);
						tessellator.addVertexWithUV(xCorner, yCorner, 0.0D, u, u2);
						tessellator.setColorOpaque_I(16777215);
						tessellator.addVertexWithUV(xCorner, yCorner, zCorner, u, u1);
					}
					tessellator.draw();
				}
				GL11.glEnable(GL11.GL_CULL_FACE);
				GL11.glShadeModel(GL11.GL_FLAT);
				GL11.glPopMatrix();

			}
		}
		GL11.glPopMatrix();
	}
}
