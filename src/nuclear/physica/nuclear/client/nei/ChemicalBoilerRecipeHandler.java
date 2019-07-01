package physica.nuclear.client.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
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
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import physica.core.client.nei.PhysicaRecipeHandlerBase;
import physica.nuclear.client.gui.GuiChemicalBoiler;
import physica.nuclear.common.NuclearFluidRegister;
import physica.nuclear.common.recipe.NuclearCustomRecipeHelper;
import physica.nuclear.common.recipe.type.ChemicalBoilerRecipe;
import physica.nuclear.common.tile.TileChemicalBoiler;

public class ChemicalBoilerRecipeHandler extends PhysicaRecipeHandlerBase {

	@Override
	public String getRecipeName()
	{
		return "Chemical Boiler";
	}

	public String getRecipeID()
	{
		return "Physica.ChemicalBoiler";
	}

	@Override
	public Class<GuiChemicalBoiler> getGuiClass()
	{
		return GuiChemicalBoiler.class;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		cycleticks += TileChemicalBoiler.TICKS_REQUIRED / 50;
	}

	@Override
	public void drawBackground(int i)
	{
		recipe theRecipe = (recipe) arecipes.get(i);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		changeTexture(getGuiTexture());
		drawTexturedModalRect(-1, 0, xOffset, yOffset, 169, 62);

		drawFluidTank(8, 8,
				new FluidStack(FluidRegistry.WATER, (int) (theRecipe.waterAmount * (1 - cycleticks % TileChemicalBoiler.TICKS_REQUIRED / (float) TileChemicalBoiler.TICKS_REQUIRED))));
		drawFluidTank(145, 8, new FluidStack(NuclearFluidRegister.LIQUID_HE, theRecipe.hexaAmount));

		renderFurnaceCookArrow(30, 24, 0, 1000 / 2);
		renderFurnaceCookArrow(118, 24, 0, 1000 / 2);

		drawSlot(68, 33, true);
		drawSlot(68, 13, false);
		drawSlot(88, 13, false);
	}

	@Override
	public int recipiesPerPage()
	{
		return 2;
	}

	@Override
	public void drawExtras(int recipe)
	{
		mc.renderEngine.bindTexture(GUI_COMPONENTS);
		drawDoubleProgressBar(30, 24, 18, 15, 22, 15, TileChemicalBoiler.TICKS_REQUIRED, 0, true);
		drawDoubleProgressBar(118, 24, 18, 15, 22, 15, TileChemicalBoiler.TICKS_REQUIRED, 0, false);

	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results)
	{
		if (outputId.equals(getRecipeID()))
		{
			for (ChemicalBoilerRecipe newRecipe : NuclearCustomRecipeHelper.getBoilerRecipes())
			{
				if (newRecipe.getInput() != null)
				{
					arecipes.add(new recipe(newRecipe.getWaterUse(), newRecipe.getHexafluorideGenerated(), newRecipe.getInput()));
				} else
				{
					arecipes.add(new recipe(newRecipe.getWaterUse(), newRecipe.getHexafluorideGenerated(), newRecipe.getOreDictName()));
				}
			}
		} else if (outputId.equals("fluid") && results[0] instanceof FluidStack && ((FluidStack) results[0]).getFluid() == NuclearFluidRegister.LIQUID_HE)
		{
			for (ChemicalBoilerRecipe newRecipe : NuclearCustomRecipeHelper.getBoilerRecipes())
			{
				if (newRecipe.getInput() != null)
				{
					arecipes.add(new recipe(newRecipe.getWaterUse(), newRecipe.getHexafluorideGenerated(), newRecipe.getInput()));
				} else
				{
					arecipes.add(new recipe(newRecipe.getWaterUse(), newRecipe.getHexafluorideGenerated(), newRecipe.getOreDictName()));
				}
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients)
	{
		if (inputId.equals("item") && ingredients[0] instanceof ItemStack && NuclearCustomRecipeHelper.getBoilerRecipe((ItemStack) ingredients[0]) != null)
		{
			ChemicalBoilerRecipe newRecipe = NuclearCustomRecipeHelper.getBoilerRecipe((ItemStack) ingredients[0]);
			if (newRecipe.getInput() != null)
			{
				arecipes.add(new recipe(newRecipe.getWaterUse(), newRecipe.getHexafluorideGenerated(), newRecipe.getInput()));
			} else
			{
				arecipes.add(new recipe(newRecipe.getWaterUse(), newRecipe.getHexafluorideGenerated(), newRecipe.getOreDictName()));
			}
		} else if (inputId.equals("fluid") && ingredients[0] instanceof FluidStack && ((FluidStack) ingredients[0]).getFluid() == FluidRegistry.WATER)
		{
			for (ChemicalBoilerRecipe newRecipe : NuclearCustomRecipeHelper.getBoilerRecipes())
			{
				if (newRecipe.getInput() != null)
				{
					arecipes.add(new recipe(newRecipe.getWaterUse(), newRecipe.getHexafluorideGenerated(), newRecipe.getInput()));
				} else
				{
					arecipes.add(new recipe(newRecipe.getWaterUse(), newRecipe.getHexafluorideGenerated(), newRecipe.getOreDictName()));
				}
			}
		} else
		{
			super.loadUsageRecipes(inputId, ingredients);
		}
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
			currenttip.add("Water: " + theRecipe.waterAmount + "/5000ml");
		} else if (relMouse.x > 145 && relMouse.x < 145 + meterWidth && relMouse.y > 8 && relMouse.y < 8 + meterHeight)
		{
			currenttip.add("Hexafluoride: " + theRecipe.hexaAmount + "/5000ml");
		}

		return super.handleTooltip(gui, currenttip, recipe);
	}

	@Override
	public void loadTransferRects()
	{
		this.transferRects.add(new RecipeTransferRect(new Rectangle(30, 24, 22, 15), getRecipeID(), new Object[0]));
		this.transferRects.add(new RecipeTransferRect(new Rectangle(118, 24, 22, 15), getRecipeID(), new Object[0]));
	}

	@Override
	public boolean mouseClicked(GuiRecipe gui, int button, int recipe)
	{
		Point point = GuiDraw.getMousePosition();
		Point offset = gui.getRecipePosition(recipe);
		Point relMouse = new Point(point.x - (gui.width - 176) / 2 - offset.x, point.y - (gui.height - 166) / 2 - offset.y);

		if (button == 0)
		{
			if (relMouse.x > 145 && relMouse.x < 145 + meterWidth && relMouse.y > 8 && relMouse.y < 8 + meterHeight)
			{
				GuiCraftingRecipe.openRecipeGui("fluid", new Object[] { new FluidStack(NuclearFluidRegister.LIQUID_HE, 1000) });
				return true;
			} else if (relMouse.x > 8 && relMouse.x < 8 + meterWidth && relMouse.y > 8 && relMouse.y < 8 + meterHeight)
			{
				GuiCraftingRecipe.openRecipeGui("fluid", new Object[] { new FluidStack(FluidRegistry.WATER, 1000) });
				return true;
			}
		} else if (button == 1)
		{
			if (relMouse.x > 145 && relMouse.x < 145 + meterWidth && relMouse.y > 8 && relMouse.y < 8 + meterHeight)
			{
				GuiUsageRecipe.openRecipeGui("fluid", new Object[] { new FluidStack(NuclearFluidRegister.LIQUID_HE, 1000) });
				return true;
			} else if (relMouse.x > 8 && relMouse.x < 8 + meterWidth && relMouse.y > 8 && relMouse.y < 8 + meterHeight)
			{
				GuiUsageRecipe.openRecipeGui("fluid", new Object[] { new FluidStack(FluidRegistry.WATER, 1000) });
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
			if (relMouse.x > 145 && relMouse.x < 145 + meterWidth && relMouse.y > 8 && relMouse.y < 8 + meterHeight)
			{
				GuiCraftingRecipe.openRecipeGui("fluid", new Object[] { new FluidStack(NuclearFluidRegister.LIQUID_HE, 1000) });
				return true;
			} else if (relMouse.x > 8 && relMouse.x < 8 + meterWidth && relMouse.y > 8 && relMouse.y < 8 + meterHeight)
			{
				GuiCraftingRecipe.openRecipeGui("fluid", new Object[] { new FluidStack(FluidRegistry.WATER, 1000) });
				return true;
			}
		} else if (keyCode == NEIClientConfig.getKeyBinding("gui.usage"))
		{
			if (relMouse.x > 145 && relMouse.x < 145 + meterWidth && relMouse.y > 8 && relMouse.y < 8 + meterHeight)
			{
				GuiUsageRecipe.openRecipeGui("fluid", new Object[] { new FluidStack(NuclearFluidRegister.LIQUID_HE, 1000) });
				return true;
			} else if (relMouse.x > 8 && relMouse.x < 8 + meterWidth && relMouse.y > 8 && relMouse.y < 8 + meterHeight)
			{
				GuiUsageRecipe.openRecipeGui("fluid", new Object[] { new FluidStack(FluidRegistry.WATER, 1000) });
				return true;
			}
		}

		return super.keyTyped(gui, keyChar, keyCode, recipe);
	}

	class recipe extends TemplateRecipeHandler.CachedRecipe {

		public int waterAmount;
		public int hexaAmount;
		public Item iteminput;
		public String oreDict;

		@Override
		public PositionedStack getResult()
		{
			return null;
		}

		public recipe(int WaterAmount, int HexaAmount, Item Input) {
			waterAmount = WaterAmount;
			hexaAmount = HexaAmount;
			iteminput = Input;
		}

		public recipe(int WaterAmount, int HexaAmount, String Input) {
			waterAmount = WaterAmount;
			hexaAmount = HexaAmount;
			oreDict = Input;
		}

		@Override
		public PositionedStack getIngredient()
		{
			return new PositionedStack(new ItemStack(iteminput), 89, 14);
		}

		@Override
		public List<PositionedStack> getIngredients()
		{
			List<PositionedStack> ingredients = new ArrayList<>();
			if (oreDict != null)
			{
				ingredients.add(new PositionedStack(OreDictionary.getOres(oreDict), 89, 14));
			} else
			{
				ingredients.add(new PositionedStack(new ItemStack(iteminput), 89, 14));
			}
			return getCycledIngredients(cycleticks / TileChemicalBoiler.TICKS_REQUIRED, ingredients);
		}
	}
}
