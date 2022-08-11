package physica.library.client.gui;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.google.common.collect.Lists;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import physica.CoreReferences;
import physica.api.core.inventory.IPlayerUsing;
import physica.library.client.render.TessellatorWrapper;
import physica.library.inventory.slot.IRenderableSlot;
import physica.library.inventory.tooltip.IToolTipContainer;
import physica.library.inventory.tooltip.ToolTip;

@SideOnly(Side.CLIENT)
public class GuiContainerBase<T extends IPlayerUsing> extends GuiContainer {

	public static final ResourceLocation	GUI_COMPONENTS			= new ResourceLocation(CoreReferences.DOMAIN, CoreReferences.GUI_TEXTURE_DIRECTORY + "gui_components.png");
	public static final ResourceLocation	GUI_BASE				= new ResourceLocation(CoreReferences.DOMAIN, CoreReferences.GUI_TEXTURE_DIRECTORY + "gui_base.png");
	public static final int					defaultYSize			= 166;

	protected Set<ToolTip>					tooltips				= new HashSet<>();
	protected Set<GuiTextField>				fields					= new HashSet<>();

	protected int							meterHeight				= 49;
	protected int							meterWidth				= 14;
	protected int							electricityMeterHeight	= 11;
	protected int							electricityMeterWidth	= 107;
	protected int							containerWidth;
	protected int							containerHeight;

	protected T								host;

	public GuiContainerBase(Container container, T host) {
		super(container);
		this.host = host;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		buttonList.clear();
		this.fields.clear();
		this.tooltips.clear();

		inventorySlots.inventorySlots.stream().forEach(s ->
		{
			if (s instanceof IToolTipContainer)
			{
				ToolTip toolTip = ((IToolTipContainer) s).getToolTip();
				if (toolTip != null)
				{
					tooltips.add(toolTip);
				}
			}
		});
	}

	protected <E extends GuiButton> E addButton(E button)
	{
		buttonList.add(button);
		return button;
	}

	protected void addToolTip(int x, int y, int w, int h, String text)
	{
		addToolTip(new Rectangle(x, y, w, h), text);
	}

	protected void addToolTip(Rectangle triggerArea, String text)
	{
		addToolTip(new ToolTip(triggerArea, text));
	}

	protected void addToolTip(ToolTip toolTip)
	{
		tooltips.add(toolTip);
	}

	protected void drawString(String str, int x, int y, int color)
	{
		fontRendererObj.drawString(str, x, y, color);
	}

	protected void drawString(String str, int x, int y)
	{
		drawString(str, x, y, 4210752);
	}

	protected void drawString(String str, int x, int y, Color color)
	{
		drawString(str, x, y, color.getRGB());
	}

	protected void drawStringCentered(String str, int x, int y)
	{
		drawStringCentered(str, x, y, 4210752);
	}

	protected void drawStringCentered(String str, int x, int y, Color color)
	{
		drawStringCentered(str, x, y, color.getRGB());
	}

	protected void drawStringCentered(String str, int x, int y, int color)
	{
		drawString(str, x - fontRendererObj.getStringWidth(str) / 2, y, color);
	}

	protected GuiTextField newField(int x, int y, int w, String msg)
	{
		return this.newInputField(x, y, w, 20, msg);
	}

	protected GuiTextField newInputField(int x, int y, int w, int h, String msg)
	{
		GuiTextField textField = new GuiTextField(fontRendererObj, x, y, w, h);
		textField.setText("" + msg);
		textField.setMaxStringLength(15);
		textField.setTextColor(16777215);
		fields.add(textField);
		return textField;
	}

	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
		super.onGuiClosed();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float p_73863_3_)
	{
		super.drawScreen(mouseX, mouseY, p_73863_3_);

		if (fields != null && !fields.isEmpty())
		{
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_BLEND);
			for (GuiTextField field : fields)
			{
				field.drawTextBox();
			}
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		ToolTip fetched = null;

		for (ToolTip toolTip : this.tooltips)
		{
			if (toolTip.shouldShowAt(mouseX - guiLeft, mouseY - guiTop))
			{
				fetched = toolTip;
				break;
			}
		}

		if (fetched != null && fetched.shouldShow())
		{
			this.drawTooltip(mouseX, mouseY, fetched.getLocalizedTooltip().split(";"));
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_LIGHTING);
		RenderHelper.enableStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	}

	@Override
	protected void keyTyped(char c, int id)
	{
		boolean f = false;
		for (GuiTextField field : fields)
		{
			field.textboxKeyTyped(c, id);
			if (field.isFocused())
			{
				return;
			}
		}
		if (!f)
		{
			super.keyTyped(c, id);
		}
	}

	@Override
	protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_)
	{
		super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
		for (GuiTextField field : fields)
		{
			field.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
	{
		this.containerWidth = (width - xSize) / 2;
		this.containerHeight = (height - ySize) / 2;

		mc.renderEngine.bindTexture(GUI_BASE);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float scale = ySize / 166f;
		GL11.glScalef(1, scale, 1);
		drawTexturedModalRect(this.containerWidth, (int) (this.containerHeight / scale), 0, 0, xSize, ySize);
		GL11.glScalef(1, 1 / scale, 1);
		preDrawContainerSlots();
		drawContainerSlots();
	}

	protected void preDrawContainerSlots()
	{
	}

	protected void drawContainerSlots()
	{
		for (Object object : inventorySlots.inventorySlots)
		{
			drawSlot((Slot) object);
		}
	}

	protected void drawSlot(Slot slot)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if (slot instanceof IRenderableSlot)
		{
			((IRenderableSlot) slot).renderSlotOverlay(this, this.containerWidth + slot.xDisplayPosition - 1, this.containerHeight + slot.yDisplayPosition - 1);
		} else
		{
			drawSlot(slot.xDisplayPosition - 1, slot.yDisplayPosition - 1);
		}

	}

	protected void drawSlot(int x, int y)
	{
		mc.renderEngine.bindTexture(GUI_COMPONENTS);
		drawTexturedModalRect(this.containerWidth + x, this.containerHeight + y, 0, 0, 18, 18);
	}

	public void drawLargeBar(int x, int y, int w, float percent, Color color)
	{
		mc.renderEngine.bindTexture(GUI_COMPONENTS);

		int width = Math.round(percent * 138);
		setColor(null);
		drawRectWithScaledWidth(containerWidth + x, containerHeight + y, 54, 33, 140, 15, w);
		setColor(color);
		drawRectWithScaledWidth(containerWidth + x + 1, containerHeight + y + 1, 55, 65, width, 13, w - 2);
	}

	public void drawSmallBar(int x, int y, int w, float percent, Color color)
	{
		mc.renderEngine.bindTexture(GUI_COMPONENTS);

		final int width = Math.round(percent * 105);
		setColor(null);
		drawRectWithScaledWidth(containerWidth + x, containerHeight + y, 54, 0, 107, 11, w);

		setColor(color);
		drawRectWithScaledWidth(containerWidth + x + 1, containerHeight + y + 1, 55, 24, width, 9, w);
	}

	public void drawMicroBar(int x, int y, float percent, Color color)
	{
		drawMicroBar(x, y, -1, percent, color);
	}

	public void drawMicroBar(int x, int y, int w, float percent, Color color)
	{
		final int backgroundWidth = 56;
		final int fillBarWidth = 54;

		mc.renderEngine.bindTexture(GUI_COMPONENTS);

		setColor(null);
		drawRectWithScaledWidth(containerWidth + x, containerHeight + y, 54, 79, backgroundWidth, 7, w);

		final int width = Math.round(percent * fillBarWidth);
		setColor(color);
		drawRectWithScaledWidth(containerWidth + x + 1, containerHeight + y + 1, 55, 87, width, 5, (int) ((w - 2) * percent));
	}

	protected void drawRectWithScaledWidth(int x, int y, int u, int v, int width, int height, int newWidth)
	{
		if (width > 0)
		{
			if (newWidth <= 0 || width == newWidth)
			{
				drawTexturedModalRect(x, y, u, v, width, height);
			}

			final int midWidth = width - 6;

			drawTexturedModalRect(x, y, u, v, 3, height);
			x += 3;

			if (newWidth > 6)
			{
				int loops = newWidth / width;
				while (loops > 0)
				{
					drawTexturedModalRect(x, y, u + 3, v, midWidth, height);
					x += midWidth;
					loops -= 1;
				}

				loops = newWidth % width;
				if (loops != 0)
				{
					drawTexturedModalRect(x, y, u + 3, v, loops, height);
					x += loops;
				}
			}

			if (width > 3)
			{
				drawTexturedModalRect(x, y, u + width - 3, v, 3, height);
			}
		}
	}

	protected void setColor(Color color)
	{
		if (color == null)
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		} else
		{
			GL11.glColor3f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
		}
	}

	protected void drawElectricity(int x, int y, float scale)
	{
		mc.renderEngine.bindTexture(GUI_COMPONENTS);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		drawTexturedModalRect(this.containerWidth + x, this.containerHeight + y, 54, 0, electricityMeterWidth, electricityMeterHeight);

		if (scale > 0)
		{
			drawTexturedModalRect(this.containerWidth + x, this.containerHeight + y, 54, 22, (int) (scale * electricityMeterWidth), electricityMeterHeight);
		}
	}

	protected void drawFluidTank(int x, int y, IFluidTank tank)
	{
		drawFluidTank(x, y, tank, null);
	}

	protected void drawFluidTank(int x, int y, IFluidTank tank, Color edgeColor)
	{
		float scale = tank.getFluidAmount() / (float) tank.getCapacity();
		FluidStack fluidStack = tank.getFluid();
		mc.renderEngine.bindTexture(GUI_COMPONENTS);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		if (edgeColor != null)
		{
			GL11.glColor4f(edgeColor.getRed() / 255f, edgeColor.getGreen() / 255f, edgeColor.getBlue() / 255f, edgeColor.getAlpha() / 255f);
			drawTexturedModalRect(this.containerWidth + x, this.containerHeight + y, 40, 0, meterWidth, meterHeight);

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			drawTexturedModalRect(this.containerWidth + x + 1, this.containerHeight + y + 1, 41, 1, meterWidth - 2, meterHeight - 2);
		} else
		{
			drawTexturedModalRect(this.containerWidth + x, this.containerHeight + y, 40, 0, meterWidth, meterHeight);
		}

		if (fluidStack != null)
		{
			this.drawFluid(this.containerWidth + x, this.containerHeight + y, -10, 1, 12, (int) ((meterHeight - 1) * scale), fluidStack);
		}
		mc.renderEngine.bindTexture(GUI_COMPONENTS);
		drawTexturedModalRect(this.containerWidth + x, this.containerHeight + y, 40, 49 * 2, meterWidth, meterHeight);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public void drawTooltip(int x, int y, String... tooltips)
	{
		drawTooltip(x, y, Lists.newArrayList(tooltips));
	}

	public void drawTooltip(int x, int y, List<String> tooltips)
	{
		drawHoveringText(tooltips, x, y, fontRendererObj);
	}

	protected void drawFluid(int x, int y, int line, int col, int width, int drawSize, FluidStack fluidStack)
	{
		if (fluidStack != null)
		{
			Fluid fluid = fluidStack.getFluid();
			if (fluid != null)
			{
				drawSize -= 1;

				IIcon fluidIcon = null;

				if (fluid.getStillIcon() != null)
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

						drawTexturedModelRectFromIcon(x + col, y + line + 58 - renderY - start, fluidIcon, width, textureSize - (textureSize - renderY));
						start = start + textureSize;
					}
				}
			}
		}
	}

	@Override
	public void drawTexturedModelRectFromIcon(int x, int y, IIcon icon, int width, int height)
	{
		TessellatorWrapper tessellator = TessellatorWrapper.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0, y + height, zLevel, icon.getMinU(), icon.getMaxV());
		tessellator.addVertexWithUV(x + width, y + height, zLevel, icon.getMaxU(), icon.getMaxV());
		tessellator.addVertexWithUV(x + width, y + 0, zLevel, icon.getMaxU(), icon.getMinV());
		tessellator.addVertexWithUV(x + 0, y + 0, zLevel, icon.getMinU(), icon.getMinV());
		tessellator.draw();
	}

	protected void renderFurnaceCookArrow(int x, int y, float progress)
	{
		renderFurnaceCookArrow(x, y, progress, 1);
	}

	protected void renderFurnaceCookArrow(int x, int y, double cookTime, double maxCookTime)
	{
		cookTime = Math.min(cookTime, maxCookTime);
		drawTexturedModalRect(this.containerWidth + x, this.containerHeight + y, 18, 0, 22, 15);
		if (cookTime > 0)
		{
			double progress = cookTime / maxCookTime;
			drawTexturedModalRect(this.containerWidth + x, this.containerHeight + y, 18, 15, (int) Math.floor(22 * progress), 15);
		}
	}
}
