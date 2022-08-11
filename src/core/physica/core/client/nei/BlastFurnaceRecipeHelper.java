package physica.core.client.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import codechicken.nei.ItemList;
import codechicken.nei.PositionedStack;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import physica.core.client.gui.GuiBlastFurnace;
import physica.core.common.CoreItemRegister;

public class BlastFurnaceRecipeHelper extends PhysicaRecipeHandlerBase {

	public class SmeltingPair extends CachedRecipe {

		public SmeltingPair(ItemStack ingred, ItemStack result) {
			ingred.stackSize = 1;
			this.ingred = new PositionedStack(ingred, 51, 6);
			this.result = new PositionedStack(result, 111, 24);
		}

		@Override
		public List<PositionedStack> getIngredients()
		{
			return getCycledIngredients(cycleticks / 48, Arrays.asList(ingred));
		}

		@Override
		public PositionedStack getResult()
		{
			return result;
		}

		@Override
		public PositionedStack getOtherStack()
		{
			return afuels.get(cycleticks / 48 % afuels.size()).stack;
		}

		PositionedStack	ingred;
		PositionedStack	result;
	}

	public static class FuelPair {

		public FuelPair(ItemStack ingred, int burnTime) {
			stack = new PositionedStack(ingred, 51, 42, false);
			this.burnTime = burnTime;
		}

		public PositionedStack	stack;
		public int				burnTime;
	}

	public static ArrayList<FuelPair>	afuels;
	public static HashSet<Block>		efuels;

	@Override
	public void loadTransferRects()
	{
		transferRects.add(new RecipeTransferRect(new Rectangle(50, 23, 18, 18), "fuel"));
		transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 24, 18), getRecipeID()));
	}

	public String getRecipeID()
	{
		return "Physica.BlastFurnace";
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass()
	{
		return GuiBlastFurnace.class;
	}

	@Override
	public String getRecipeName()
	{
		return "Blast Furnace";
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results)
	{
		if (outputId.equals(getRecipeID())
				|| outputId.equals("item") && results[0] != null && results[0] instanceof ItemStack && ((ItemStack) results[0]).getUnlocalizedName().equals("item." + CoreItemRegister.itemMetaIngot.subItems.get(2)))
		{
			findFuels();
			arecipes.add(new SmeltingPair(new ItemStack(Items.iron_ingot), new ItemStack(CoreItemRegister.itemMetaIngot, 1, 2)));
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients)
	{
		if (inputId.equals(getRecipeID()) || inputId.equals("fuel")
				|| inputId.equals("item") && ingredients[0] != null && ingredients[0] instanceof ItemStack && ((ItemStack) ingredients[0]).getUnlocalizedName().equals("item." + CoreItemRegister.itemMetaIngot.subItems.get(2)))
		{
			findFuels();
			arecipes.add(new SmeltingPair(new ItemStack(Items.iron_ingot), new ItemStack(CoreItemRegister.itemMetaIngot, 1, 2)));
		}
	}

	@Override
	public String getGuiTexture()
	{
		return "textures/gui/container/furnace.png";
	}

	@Override
	public void drawExtras(int recipe)
	{
		drawProgressBar(51, 25, 176, 0, 14, 14, 48, 7);
		drawProgressBar(74, 23, 176, 14, 24, 16, 48, 0);
	}

	private static Set<Item> excludedFuels()
	{
		Set<Item> efuels = new HashSet<>();
		efuels.add(Item.getItemFromBlock(Blocks.brown_mushroom));
		efuels.add(Item.getItemFromBlock(Blocks.red_mushroom));
		efuels.add(Item.getItemFromBlock(Blocks.standing_sign));
		efuels.add(Item.getItemFromBlock(Blocks.wall_sign));
		efuels.add(Item.getItemFromBlock(Blocks.wooden_door));
		efuels.add(Item.getItemFromBlock(Blocks.trapped_chest));
		return efuels;
	}

	private static void findFuels()
	{
		afuels = new ArrayList<>();
		Set<Item> efuels = excludedFuels();
		for (ItemStack item : ItemList.items)
		{
			if (!efuels.contains(item.getItem()))
			{
				int burnTime = TileEntityFurnace.getItemBurnTime(item);
				if (burnTime > 0)
				{
					afuels.add(new FuelPair(item.copy(), burnTime));
				}
			}
		}
	}

	@Override
	public String getOverlayIdentifier()
	{
		return "smelting";
	}
}
