package physica.core.client.nei;

import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;
import static codechicken.lib.gui.GuiDraw.gui;

import org.lwjgl.opengl.GL11;

import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import physica.CoreReferences;

public class PhysicaRecipeHandlerBase extends TemplateRecipeHandler {

	public static final ResourceLocation GUI_COMPONENTS = new ResourceLocation(CoreReferences.DOMAIN, CoreReferences.GUI_TEXTURE_DIRECTORY + "gui_components.png");
	public static final String GUI_STRING = new ResourceLocation(CoreReferences.DOMAIN, CoreReferences.GUI_TEXTURE_DIRECTORY + "gui_base.png").toString();
	public int xOffset = 4;
	public int yOffset = 13;
	protected int meterHeight = 49;
	protected int meterWidth = 14;
	protected Minecraft mc = Minecraft.getMinecraft();

	@Override
	public String getGuiTexture()
	{
		return GUI_STRING;
	}

	@Override
	public String getRecipeName()
	{
		return "Physica";
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		cycleticks += 4;
	}

	protected void drawFluidTank(int x, int y, FluidStack fluidStack)
	{
		float scale = fluidStack.amount / 5000f;
		mc.renderEngine.bindTexture(GUI_COMPONENTS);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		drawTexturedModalRect(x, y, 40, 0, meterWidth, meterHeight);

		if (fluidStack != null)
		{
			this.drawFluid(x, y, -10, 1, 12, (int) ((meterHeight - 1) * scale), fluidStack);
		}

		mc.renderEngine.bindTexture(GUI_COMPONENTS);
		drawTexturedModalRect(x, y, 40, 49 * 2, meterWidth, meterHeight);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	protected void drawFluid(int x, int y, int line, int col, int width, int drawSize, FluidStack fluidStack)
	{
		if (fluidStack != null && fluidStack.getFluid() != null)
		{
			drawSize -= 1;

			IIcon fluidIcon = null;
			Fluid fluid = fluidStack.getFluid();

			if (fluid != null && fluid.getStillIcon() != null)
			{
				fluidIcon = fluid.getStillIcon();
			}

			FMLClientHandler.instance().getClient().renderEngine.bindTexture(FMLClientHandler.instance().getClient().renderEngine.getResourceLocation(fluid.getSpriteNumber()));

			int textureSize = 16;
			int start = 0;
			if (fluidIcon != null)
			{
				int renderY = textureSize;
				while (renderY != 0 && drawSize != 0)
				{
					if (drawSize > textureSize)
					{
						renderY = textureSize;
						drawSize -= textureSize;
					} else
					{
						renderY = drawSize;
						drawSize = 0;
					}

					gui.drawTexturedModelRectFromIcon(x + col, y + line + 58 - renderY - start, fluidIcon, width, textureSize - (textureSize - renderY));
					start = start + textureSize;
				}
			}
		}
	}

	protected void renderFurnaceCookArrow(int x, int y, double cookTime, double maxCookTime)
	{
		cookTime = Math.min(cookTime, maxCookTime);
		drawTexturedModalRect(x, y, 18, 0, 22, 15);
		if (cookTime > 0)
		{
			double progress = cookTime / maxCookTime;
			drawTexturedModalRect(x, y, 18, 15, (int) Math.floor(22 * progress), 15);
		}
	}

	protected void drawSlot(int x, int y, boolean powered)
	{
		mc.renderEngine.bindTexture(GUI_COMPONENTS);
		drawTexturedModalRect(x, y, 0, 0, 18, 18);
		if (powered)
		{
			drawTexturedModalRect(x, y, 0, 18, 18, 18);
		}
	}

	public void drawDoubleProgressBar(int x, int y, int tx, int ty, int w, int h, int ticks, int direction, boolean first)
	{
		if (first)
		{
			this.drawProgressBar(x, y, tx, ty, w, h, (float) Math.min(ticks / 2, this.cycleticks % ticks) / (float) (ticks / 2), direction);

		} else
		{
			this.drawProgressBar(x, y, tx, ty, w, h, (float) Math.max(0, this.cycleticks % ticks - ticks / 2) / (float) (ticks / 2), direction);
		}
	}
}
