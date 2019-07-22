package physica.core.client.render.item;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import physica.core.client.render.tile.TileRenderEnergyCable;

@SideOnly(Side.CLIENT)
public class ItemRenderEnergyCable implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		GL11.glPushMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glScalef(1.25f, 1.25f, 1.25f);
		if (type.equals(ItemRenderType.INVENTORY))
		{
			GL11.glTranslatef(-0.5f, -2.3f, -0.5f);
			GL11.glRotatef(180f, 0, 1, 0);
		} else if (type.equals(ItemRenderType.EQUIPPED))
		{
			GL11.glTranslatef(0, -1f, 0);
		} else if (type.equals(ItemRenderType.EQUIPPED_FIRST_PERSON))
		{
			GL11.glTranslatef(0, -1f, 0);
		} else if (type.equals(ItemRenderType.ENTITY))
		{
			GL11.glTranslatef(-0.66f, -1.66f, -0.66F);
			GL11.glScalef(1.25f, 1.25f, 1.25f);
		}
		GL11.glScalef(1, 3f, 1f);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TileRenderEnergyCable.model_texture[Math.max(0, Math.min(TileRenderEnergyCable.model_texture.length - 1, item.getItemDamage()))]);
		render();
		RenderHelper.enableStandardItemLighting();
		GL11.glPopMatrix();
	}

	public void render()
	{
		TileRenderEnergyCable.drawCore();
	}
}
