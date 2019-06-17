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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import physica.CoreReferences;
import physica.nuclear.client.gui.GuiChemicalExtractor;
import physica.nuclear.common.recipe.NuclearCustomRecipeHelper;
import physica.nuclear.common.recipe.type.ChemicalExtractorRecipe;
import physica.nuclear.common.tile.TileChemicalExtractor;

public class ChemicalExtractorRecipeHandler extends NuclearRecipeHandlerBase {

	@Override
	public String getGuiTexture() {
		return new ResourceLocation(CoreReferences.DOMAIN, CoreReferences.GUI_TEXTURE_DIRECTORY + "gui_base.png").toString();
	}

	@Override
	public String getRecipeName() {
		return "Chemical Extractor";
	}

	public String getRecipeID() {
		return "Physica.ChemicalExtractor";
	}

	@Override
	public Class<GuiChemicalExtractor> getGuiClass() {
		return GuiChemicalExtractor.class;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		cycleticks += TileChemicalExtractor.TICKS_REQUIRED / 50;
	}

	@Override
	public void drawBackground(int i) {
		recipe theRecipe = (recipe) arecipes.get(i);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		changeTexture(getGuiTexture());
		drawTexturedModalRect(-1, 0, xOffset, yOffset, 169, 62);

		drawFluidTank(8, 8,
				new FluidStack(FluidRegistry.WATER, (int) (theRecipe.waterAmount * (1 - cycleticks % TileChemicalExtractor.TICKS_REQUIRED / (float) TileChemicalExtractor.TICKS_REQUIRED))));
		renderFurnaceCookArrow(36, 24, 0, TileChemicalExtractor.TICKS_REQUIRED);

		drawSlot(131, 21, true);
		drawSlot(81, 21, false);
		drawSlot(101, 21, false);
	}

	@Override
	public void drawExtras(int recipe) {
		mc.renderEngine.bindTexture(GUI_COMPONENTS);
		drawProgressBar(36, 24, 18, 15, 22, 15, TileChemicalExtractor.TICKS_REQUIRED, 0);
	}

	@Override
	public void loadTransferRects() {
		this.transferRects.add(new RecipeTransferRect(new Rectangle(36, 24, 22, 15), getRecipeID(), new Object[0]));
	}

	@Override
	public int recipiesPerPage() {
		return 2;
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals(getRecipeID())) {
			for (ChemicalExtractorRecipe newRecipe : NuclearCustomRecipeHelper.getExtractorRecipes()) {
				if (newRecipe.getInput() != null) {
					arecipes.add(new recipe(newRecipe.getWaterUse(), newRecipe.getInput(), newRecipe.getOutput()));
				} else {
					arecipes.add(new recipe(newRecipe.getWaterUse(), newRecipe.getOreDictName(), newRecipe.getOutput()));
				}
			}
		} else if (outputId.equals("item") && results[0] instanceof ItemStack) {
			for (ChemicalExtractorRecipe newRecipe : NuclearCustomRecipeHelper.getExtractorRecipes()) {
				if (newRecipe.getOutput().getItem() == ((ItemStack) results[0]).getItem()) {
					if (newRecipe.getInput() != null) {
						arecipes.add(new recipe(newRecipe.getWaterUse(), newRecipe.getInput(), newRecipe.getOutput()));
					} else {
						arecipes.add(new recipe(newRecipe.getWaterUse(), newRecipe.getOreDictName(), newRecipe.getOutput()));
					}
				}
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId.equals("item") && ingredients[0] instanceof ItemStack && NuclearCustomRecipeHelper.getExtractorRecipe(((ItemStack) ingredients[0]).getItem()) != null) {
			ChemicalExtractorRecipe newRecipe = NuclearCustomRecipeHelper.getExtractorRecipe(((ItemStack) ingredients[0]).getItem());
			if (newRecipe.getInput() != null) {
				arecipes.add(new recipe(newRecipe.getWaterUse(), newRecipe.getInput(), newRecipe.getOutput()));
			} else {
				arecipes.add(new recipe(newRecipe.getWaterUse(), newRecipe.getOreDictName(), newRecipe.getOutput()));
			}
		} else if (inputId.equals("fluid") && ingredients[0] instanceof FluidStack && ((FluidStack) ingredients[0]).getFluid() == FluidRegistry.WATER) {
			for (ChemicalExtractorRecipe newRecipe : NuclearCustomRecipeHelper.getExtractorRecipes()) {
				if (newRecipe.getInput() != null) {
					arecipes.add(new recipe(newRecipe.getWaterUse(), newRecipe.getInput(), newRecipe.getOutput()));
				} else {
					arecipes.add(new recipe(newRecipe.getWaterUse(), newRecipe.getOreDictName(), newRecipe.getOutput()));
				}
			}
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public java.util.List<String> handleTooltip(GuiRecipe gui, List<String> currenttip, int recipe) {
		Point point = GuiDraw.getMousePosition();
		Point offset = gui.getRecipePosition(recipe);
		Point relMouse = new Point(point.x - (gui.width - 176) / 2 - offset.x, point.y - (gui.height - 166) / 2 - offset.y);

		recipe theRecipe = (recipe) arecipes.get(recipe);

		if (relMouse.x > 8 && relMouse.x < 8 + meterWidth && relMouse.y > 8 && relMouse.y < 8 + meterHeight) {
			currenttip.add("Water: " + theRecipe.waterAmount + "/5000ml");
		}

		return super.handleTooltip(gui, currenttip, recipe);
	}

	@Override
	public boolean mouseClicked(GuiRecipe gui, int button, int recipe) {
		Point point = GuiDraw.getMousePosition();
		Point offset = gui.getRecipePosition(recipe);
		Point relMouse = new Point(point.x - (gui.width - 176) / 2 - offset.x, point.y - (gui.height - 166) / 2 - offset.y);

		if (button == 0) {
			if (relMouse.x > 8 && relMouse.x < 8 + meterWidth && relMouse.y > 8 && relMouse.y < 8 + meterHeight) {
				GuiCraftingRecipe.openRecipeGui("fluid", new Object[] { new FluidStack(FluidRegistry.WATER, 1000) });
				return true;
			}
		} else if (button == 1) {
			if (relMouse.x > 8 && relMouse.x < 8 + meterWidth && relMouse.y > 8 && relMouse.y < 8 + meterHeight) {
				GuiUsageRecipe.openRecipeGui("fluid", new Object[] { new FluidStack(FluidRegistry.WATER, 1000) });
				return true;
			}
		}
		return super.mouseClicked(gui, button, recipe);
	}

	@Override
	public boolean keyTyped(GuiRecipe gui, char keyChar, int keyCode, int recipe) {
		Point point = GuiDraw.getMousePosition();
		Point offset = gui.getRecipePosition(recipe);
		Point relMouse = new Point(point.x - (gui.width - 176) / 2 - offset.x, point.y - (gui.height - 166) / 2 - offset.y);
		if (keyCode == NEIClientConfig.getKeyBinding("gui.recipe")) {
			if (relMouse.x > 8 && relMouse.x < 8 + meterWidth && relMouse.y > 8 && relMouse.y < 8 + meterHeight) {
				GuiCraftingRecipe.openRecipeGui("fluid", new Object[] { new FluidStack(FluidRegistry.WATER, 1000) });
				return true;
			}
		} else if (keyCode == NEIClientConfig.getKeyBinding("gui.usage")) {
			if (relMouse.x > 8 && relMouse.x < 8 + meterWidth && relMouse.y > 8 && relMouse.y < 8 + meterHeight) {
				GuiUsageRecipe.openRecipeGui("fluid", new Object[] { new FluidStack(FluidRegistry.WATER, 1000) });
				return true;
			}
		}

		return super.keyTyped(gui, keyChar, keyCode, recipe);
	}

	class recipe extends TemplateRecipeHandler.CachedRecipe {

		public int waterAmount;
		public ItemStack itemoutput;
		public Item iteminput;
		public String oreDict;

		public recipe(int WaterAmount, Item Input, ItemStack Output) {
			waterAmount = WaterAmount;
			itemoutput = Output;
			iteminput = Input;
		}

		public recipe(int WaterAmount, String Input, ItemStack Output) {
			waterAmount = WaterAmount;
			itemoutput = Output;
			oreDict = Input;
		}

		@Override
		public PositionedStack getIngredient() {
			return new PositionedStack(new ItemStack(iteminput), 82, 22);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			List<PositionedStack> ingredients = new ArrayList<>();
			if (oreDict != null) {
				List<ItemStack> items = new ArrayList<>(OreDictionary.getOres(oreDict));
				ingredients.add(new PositionedStack(items, 82, 22));
			} else {
				ingredients.add(new PositionedStack(new ItemStack(iteminput), 82, 22));
			}
			return getCycledIngredients(cycleticks / 48, ingredients);
		}

		@Override
		public PositionedStack getResult() {
			return new PositionedStack(itemoutput, 102, 22);
		}
	}
}
