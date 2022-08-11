package physica.nuclear.client.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.Color;
import java.awt.Rectangle;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import physica.core.client.nei.PhysicaRecipeHandlerBase;
import physica.nuclear.client.gui.GuiNeutronCaptureChamber;
import physica.nuclear.common.NuclearItemRegister;
import physica.nuclear.common.tile.TileNeutronCaptureChamber;

public class NeutronCaptureRecipeHandler extends PhysicaRecipeHandlerBase {

	@Override
	public String getRecipeName() {
		return "Neutron Capture Chamber";
	}

	public String getRecipeID() {
		return "Physica.NeutronCapture";
	}

	@Override
	public Class<GuiNeutronCaptureChamber> getGuiClass() {
		return GuiNeutronCaptureChamber.class;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		cycleticks += TileNeutronCaptureChamber.TICKS_REQUIRED / 50;
	}

	@Override
	public void drawBackground(int i) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		changeTexture(getGuiTexture());
		drawTexturedModalRect(-1, 0, xOffset, yOffset, 169, 62);

		mc.renderEngine.bindTexture(GUI_COMPONENTS);
		renderFurnaceCookArrow(38 + (118 - 38) / 2 - 3, 24, 0, 1000);

		drawSlot(38, 24, false);
		drawSlot(118, 24, false);
		GuiDraw.drawString("Progress: " + (int) ((float) (cycleticks % TileNeutronCaptureChamber.TICKS_REQUIRED) * 100 / TileNeutronCaptureChamber.TICKS_REQUIRED) + "%", 38 + (118 - 38) / 4 - 3, 10, Color.GRAY.getRGB(), false);
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals(getRecipeID()) || "item".equals(outputId) && results[0] instanceof ItemStack && ((ItemStack) results[0]).getItem() == NuclearItemRegister.itemTritiumCell) {
			arecipes.add(new recipe(NuclearItemRegister.itemDeuteriumCell, NuclearItemRegister.itemTritiumCell));
		}
	}

	@Override
	public void drawExtras(int recipe) {
		mc.renderEngine.bindTexture(GUI_COMPONENTS);
		drawProgressBar(38 + (118 - 38) / 2 - 3, 24, 18, 15, 22, 15, TileNeutronCaptureChamber.TICKS_REQUIRED, 0);
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId.equals(getRecipeID()) || "item".equals(inputId) && ingredients[0] instanceof ItemStack && ((ItemStack) ingredients[0]).getItem() == NuclearItemRegister.itemDeuteriumCell) {
			arecipes.add(new recipe(NuclearItemRegister.itemDeuteriumCell, NuclearItemRegister.itemTritiumCell));
		}
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(38 + (118 - 38) / 2 - 3, 24, 22, 15), getRecipeID(), new Object[0]));
	}

	@Override
	public int recipiesPerPage() {
		return 2;
	}

	class recipe extends CachedRecipe {

		public ItemStack itemInput;
		public ItemStack itemOutput;

		@Override
		public PositionedStack getResult() {
			return new PositionedStack(itemOutput, 119, 25);

		}

		public recipe(Item Input, Item Output) {
			itemInput = new ItemStack(Input);
			itemOutput = new ItemStack(Output);
		}

		@Override
		public PositionedStack getIngredient() {
			return new PositionedStack(new ItemStack(NuclearItemRegister.itemDeuteriumCell), 39, 25);
		}
	}
}
