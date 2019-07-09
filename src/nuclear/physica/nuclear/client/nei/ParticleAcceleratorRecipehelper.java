package physica.nuclear.client.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;
import static codechicken.lib.gui.GuiDraw.fontRenderer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import physica.core.client.nei.PhysicaRecipeHandlerBase;
import physica.nuclear.client.gui.GuiParticleAccelerator;
import physica.nuclear.common.NuclearItemRegister;

public class ParticleAcceleratorRecipehelper extends PhysicaRecipeHandlerBase {

	@Override
	public String getRecipeName()
	{
		return "Particle Accelerator";
	}

	public String getRecipeID()
	{
		return "Physica.ParticleAccelerator";
	}

	@Override
	public Class<GuiParticleAccelerator> getGuiClass()
	{
		return GuiParticleAccelerator.class;
	}

	@Override
	public void drawBackground(int i)
	{
		recipe theRecipe = (recipe) arecipes.get(i);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		changeTexture(getGuiTexture());
		drawTexturedModalRect(-1, 0, xOffset, yOffset, 169, 62);

		drawSlot(117, 12, false);
		drawSlot(117, 39, false);
		drawSlot(137, 39, false);
		GuiDraw.drawString("Antimatter: " + theRecipe.antiMatterAmount + " mg", 8, 48, Color.GRAY.getRGB(), false);

		GL11.glScalef(0.7f, 0.7f, 0.7f);
		fontRenderer.drawSplitString(theRecipe.text, 8, 2, 157, Color.GRAY.getRGB());
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results)
	{
		if (outputId.equals("item") && results[0] instanceof ItemStack)
		{
			if (((ItemStack) results[0]).getItem() == NuclearItemRegister.itemAntimatterCell125Milligram)
			{
				arecipes.add(new recipe(NuclearItemRegister.itemEmptyElectromagneticCell, NuclearItemRegister.itemAntimatterCell125Milligram, 125,
						"To generate antimatter, you must accelerate any matter in a particle accelerator. An antimatter cell is generated when the amount of antimatter in the accelerator is at least 125mg."));
			} else if (((ItemStack) results[0]).getItem() == NuclearItemRegister.itemDarkmatterCell)
			{
				arecipes.add(new recipe(NuclearItemRegister.itemEmptyQuantumCell, NuclearItemRegister.itemDarkmatterCell, 100,
						"To generate dark matter, you must collide two particles while there is 100 mg of antimatter in the accelerator, this will have a chance of creating a dark matter cell."));
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients)
	{
		if (inputId.equals("item") && ingredients[0] instanceof ItemStack)
		{
			if (((ItemStack) ingredients[0]).getItem() == NuclearItemRegister.itemEmptyElectromagneticCell)
			{
				arecipes.add(new recipe(NuclearItemRegister.itemEmptyElectromagneticCell, NuclearItemRegister.itemAntimatterCell125Milligram, 125,
						"To generate antimatter, you must accelerate any matter in a particle accelerator. An antimatter cell is generated when the amount of antimatter in the accelerator is at least 125mg."));
			} else if (((ItemStack) ingredients[0]).getItem() == NuclearItemRegister.itemEmptyQuantumCell)
			{
				arecipes.add(new recipe(NuclearItemRegister.itemEmptyQuantumCell, NuclearItemRegister.itemDarkmatterCell, 100,
						"To generate dark matter, you must collide two particles while there is 100 mg of antimatter in the accelerator, this will have a chance of creating a dark matter cell."));
			}
		}
	}

	@Override
	public int recipiesPerPage()
	{
		return 2;
	}

	class recipe extends CachedRecipe {

		List<PositionedStack>	other	= new ArrayList<>();
		ItemStack				itemInput;
		ItemStack				itemOutput;
		int						antiMatterAmount;
		String					text;

		@Override
		public PositionedStack getResult()
		{
			return new PositionedStack(itemOutput, 138, 40);
		}

		@Override
		public PositionedStack getOtherStack()
		{
			return getCycledIngredients(cycleticks / 20, other).get(0);
		}

		recipe(Item Input, Item Output, int AntiMatterAmount, String Text) {
			List<ItemStack> items = new ArrayList<>();
			for (Object item : Item.itemRegistry)
			{
				items.add(new ItemStack((Item) item));
			}

			other.add(new PositionedStack(items, 118, 13, true));
			itemInput = new ItemStack(Input);
			itemOutput = new ItemStack(Output);
			antiMatterAmount = AntiMatterAmount;
			text = Text;
		}

		@Override
		public PositionedStack getIngredient()
		{
			return new PositionedStack(itemInput, 118, 40);
		}
	}
}
