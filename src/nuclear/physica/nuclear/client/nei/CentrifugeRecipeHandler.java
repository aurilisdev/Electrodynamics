package physica.nuclear.client.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawString;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIClientConfig;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import physica.core.client.nei.PhysicaRecipeHandlerBase;
import physica.nuclear.client.gui.GuiCentrifuge;
import physica.nuclear.common.NuclearFluidRegister;
import physica.nuclear.common.NuclearItemRegister;
import physica.nuclear.common.tile.TileGasCentrifuge;

public class CentrifugeRecipeHandler extends PhysicaRecipeHandlerBase {

	@Override
	public String getRecipeName()
	{
		return "Gas Centrifuge";
	}

	public String getRecipeID()
	{
		return "Physica.GasCentrifuge";
	}

	@Override
	public Class<GuiCentrifuge> getGuiClass()
	{
		return GuiCentrifuge.class;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		cycleticks += TileGasCentrifuge.TICKS_REQUIRED / 50;
	}

	@Override
	public void drawBackground(int i)
	{
		recipe theRecipe = (recipe) arecipes.get(i);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		changeTexture(getGuiTexture());
		drawTexturedModalRect(-1, 0, xOffset, yOffset, 169, 62);

		drawFluidTank(8, 8, new FluidStack(NuclearFluidRegister.LIQUID_HE, theRecipe.hexaCost));
		renderFurnaceCookArrow(36, 24, 0, TileGasCentrifuge.TICKS_REQUIRED);

		drawSlot(131, 24, true);
		drawSlot(81, 24, false);
		drawSlot(101, 24, false);
		String text = ((recipe) arecipes.get(i)).chance * 100 + "% Chance";
		drawString(text, 81, 45, Color.GRAY.getRGB(), false);
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results)
	{

		if (outputId.equals(getRecipeID()))
		{
			arecipes.add(new recipe(2500, NuclearItemRegister.itemUranium238, .828f));
			arecipes.add(new recipe(2500, NuclearItemRegister.itemUranium235, .172f));
		} else if (outputId.equals("item") && results[0] instanceof ItemStack)
		{
			if (((ItemStack) results[0]).getItem() == NuclearItemRegister.itemUranium238)
			{
				arecipes.add(new recipe(2500, NuclearItemRegister.itemUranium238, .828f));
			} else if (((ItemStack) results[0]).getItem() == NuclearItemRegister.itemUranium235)
			{
				arecipes.add(new recipe(2500, NuclearItemRegister.itemUranium235, .172f));
			}
		}
	}

	@Override
	public void drawExtras(int recipe)
	{
		mc.renderEngine.bindTexture(GUI_COMPONENTS);
		drawProgressBar(36, 24, 18, 15, 22, 15, TileGasCentrifuge.TICKS_REQUIRED, 0);
	}

	@Override
	public void loadTransferRects()
	{
		transferRects.add(new RecipeTransferRect(new Rectangle(36, 24, 22, 15), getRecipeID(), new Object[0]));
	}

	@Override
	public int recipiesPerPage()
	{
		return 2;
	}

	@Override
	public java.util.List<String> handleTooltip(GuiRecipe gui, List<String> currenttip, int recipe)
	{
		Point point = GuiDraw.getMousePosition();
		Point offset = gui.getRecipePosition(recipe);
		Point relMouse = new Point(point.x - (gui.width - 176) / 2 - offset.x, point.y - (gui.height - 166) / 2 - offset.y);

		recipe theRecipe = (recipe) arecipes.get(recipe);

		if (relMouse.x > 8 && relMouse.x < 8 + meterWidth && relMouse.y > 8 && relMouse.y < 8 + meterHeight)
		{
			currenttip.add("Hexafluoride: " + theRecipe.hexaCost + "/5000ml");
		}

		return super.handleTooltip(gui, currenttip, recipe);
	}

	@Override
	public boolean mouseClicked(GuiRecipe gui, int button, int recipe)
	{
		Point point = GuiDraw.getMousePosition();
		Point offset = gui.getRecipePosition(recipe);
		Point relMouse = new Point(point.x - (gui.width - 176) / 2 - offset.x, point.y - (gui.height - 166) / 2 - offset.y);

		if (button == 0)
		{
			if (relMouse.x > 8 && relMouse.x < 8 + meterWidth && relMouse.y > 8 && relMouse.y < 8 + meterHeight)
			{
				GuiCraftingRecipe.openRecipeGui("fluid", new Object[] { new FluidStack(NuclearFluidRegister.LIQUID_HE, 1000) });
				return true;
			}
		} else if (button == 1)
		{
			if (relMouse.x > 8 && relMouse.x < 8 + meterWidth && relMouse.y > 8 && relMouse.y < 8 + meterHeight)
			{
				GuiUsageRecipe.openRecipeGui("fluid", new Object[] { new FluidStack(NuclearFluidRegister.LIQUID_HE, 1000) });
				return true;
			}
		}
		return super.mouseClicked(gui, button, recipe);
	}

	@Override
	public boolean keyTyped(GuiRecipe gui, char keyChar, int keyCode, int recipe)
	{
		Point point = GuiDraw.getMousePosition();
		Point offset = gui.getRecipePosition(recipe);
		Point relMouse = new Point(point.x - (gui.width - 176) / 2 - offset.x, point.y - (gui.height - 166) / 2 - offset.y);
		if (keyCode == NEIClientConfig.getKeyBinding("gui.recipe"))
		{
			if (relMouse.x > 8 && relMouse.x < 8 + meterWidth && relMouse.y > 8 && relMouse.y < 8 + meterHeight)
			{
				GuiCraftingRecipe.openRecipeGui("fluid", new Object[] { new FluidStack(NuclearFluidRegister.LIQUID_HE, 1000) });
				return true;
			}
		} else if (keyCode == NEIClientConfig.getKeyBinding("gui.usage"))
		{
			if (relMouse.x > 8 && relMouse.x < 8 + meterWidth && relMouse.y > 8 && relMouse.y < 8 + meterHeight)
			{
				GuiUsageRecipe.openRecipeGui("fluid", new Object[] { new FluidStack(NuclearFluidRegister.LIQUID_HE, 1000) });
				return true;
			}
		}

		return super.keyTyped(gui, keyChar, keyCode, recipe);
	}

	class recipe extends TemplateRecipeHandler.CachedRecipe {

		public int			hexaCost;
		public ItemStack	itemOutput;
		public float		chance;

		@Override
		public PositionedStack getResult()
		{
			if (itemOutput.getItem() == NuclearItemRegister.itemUranium238)
			{
				return new PositionedStack(itemOutput, 102, 25);
			} else
			{
				return new PositionedStack(itemOutput, 82, 25);
			}

		}

		public recipe(int HexaCost, Item Output, float Chance) {
			hexaCost = HexaCost;
			itemOutput = new ItemStack(Output);
			chance = Chance;
		}

		@Override
		public PositionedStack getIngredient()
		{
			return null;
		}
	}
}
