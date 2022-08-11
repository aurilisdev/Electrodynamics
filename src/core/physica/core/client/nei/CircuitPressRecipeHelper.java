package physica.core.client.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import codechicken.nei.PositionedStack;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import physica.core.client.gui.GuiCircuitPress;
import physica.core.common.CoreItemRegister;
import physica.core.common.tile.TileCircuitPress;
import physica.nuclear.common.tile.TileNeutronCaptureChamber;

public class CircuitPressRecipeHelper extends PhysicaRecipeHandlerBase {

	@Override
	public String getRecipeName() {
		return "Circuit Press";
	}

	public String getRecipeID() {
		return "Physica.CircuitPress";
	}

	@Override
	public Class<GuiCircuitPress> getGuiClass() {
		return GuiCircuitPress.class;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		cycleticks += TileCircuitPress.TICKS_REQUIRED;
	}

	@Override
	public void drawBackground(int i) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		changeTexture(getGuiTexture());
		drawTexturedModalRect(-1, 0, xOffset, yOffset, 169, 62);

		mc.renderEngine.bindTexture(GUI_COMPONENTS);
		renderFurnaceCookArrow(80, 35 - 13, 0, 1000);

		drawSlot(56, 17 - 13, false);
		drawSlot(56, 53 - 13, false);
		drawSlot(116, 35 - 13, false);
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals(getRecipeID())) {
			arecipes.add(new recipe(new ItemStack(CoreItemRegister.itemMetaPlate, 1, 1), new ItemStack(Items.redstone), new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 0)));
			arecipes.add(new recipe(new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 0), new ItemStack(Items.gold_ingot), new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 1)));
			arecipes.add(new recipe(new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 1), new ItemStack(Items.diamond), new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 2)));
		} else if ("item".equals(outputId) && results[0] instanceof ItemStack && ((ItemStack) results[0]).getItem() == CoreItemRegister.itemMetaCircuit) {
			switch (((ItemStack) results[0]).getItemDamage()) {
			case 0:
				arecipes.add(new recipe(new ItemStack(CoreItemRegister.itemMetaPlate, 1, 1), new ItemStack(Items.redstone), new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 0)));
				break;
			case 1:
				arecipes.add(new recipe(new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 0), new ItemStack(Items.gold_ingot), new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 1)));
				break;
			case 2:
				arecipes.add(new recipe(new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 1), new ItemStack(Items.diamond), new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 2)));
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void drawExtras(int recipe) {
		mc.renderEngine.bindTexture(GUI_COMPONENTS);
		drawProgressBar(80, 35 - 13, 18, 15, 22, 15, TileNeutronCaptureChamber.TICKS_REQUIRED, 0);
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId.equals(getRecipeID())) {
			arecipes.add(new recipe(new ItemStack(CoreItemRegister.itemMetaPlate, 1, 1), new ItemStack(Items.redstone), new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 0)));
			arecipes.add(new recipe(new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 0), new ItemStack(Items.gold_ingot), new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 1)));
			arecipes.add(new recipe(new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 1), new ItemStack(Items.diamond), new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 2)));
		} else if ("item".equals(inputId) && ingredients[0] instanceof ItemStack) {
			if (((ItemStack) ingredients[0]).getItem() == CoreItemRegister.itemMetaCircuit) {
				switch (((ItemStack) ingredients[0]).getItemDamage()) {
				case 0:
					arecipes.add(new recipe(new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 0), new ItemStack(Items.gold_ingot), new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 1)));
					break;
				case 1:
					arecipes.add(new recipe(new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 1), new ItemStack(Items.diamond), new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 2)));
					break;
				default:
					break;
				}
			} else if (((ItemStack) ingredients[0]).getItem() == CoreItemRegister.itemMetaPlate && ((ItemStack) ingredients[0]).getItemDamage() == 1 || ((ItemStack) ingredients[0]).getItem() == Items.redstone) {
				arecipes.add(new recipe(new ItemStack(CoreItemRegister.itemMetaPlate, 1, 1), new ItemStack(Items.redstone), new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 0)));
			} else if (((ItemStack) ingredients[0]).getItem() == Items.gold_ingot) {
				arecipes.add(new recipe(new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 0), new ItemStack(Items.gold_ingot), new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 1)));
			}

		}
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(80, 35 - 13, 22, 15), getRecipeID(), new Object[0]));
	}

	@Override
	public int recipiesPerPage() {
		return 2;
	}

	class recipe extends CachedRecipe {

		public ItemStack itemInput;
		public ItemStack itemInputIngot;
		public ItemStack itemOutput;

		@Override
		public PositionedStack getResult() {
			return new PositionedStack(itemOutput, 117, 36 - 13);

		}

		public recipe(ItemStack Input, ItemStack Ingot, ItemStack Output) {
			itemInput = Input;
			itemInputIngot = Ingot;
			itemOutput = Output;
		}

		@Override
		public List<PositionedStack> getIngredients() {
			List<PositionedStack> stacks = new ArrayList<>();
			stacks.add(new PositionedStack(itemInput, 57, 18 - 13));
			stacks.add(new PositionedStack(itemInputIngot, 57, 54 - 13));
			return stacks;
		}
	}
}
