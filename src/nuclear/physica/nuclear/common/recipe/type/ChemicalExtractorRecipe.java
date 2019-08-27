package physica.nuclear.common.recipe.type;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import physica.library.recipe.SimpleRecipe;

public class ChemicalExtractorRecipe {

	private int			waterUse;
	private Item		inputItem;
	private ItemStack	outputItem;
	private String		oreDictName;

	public ChemicalExtractorRecipe(int waterUse, ItemStack outputItem, String oreDictName) {
		this.waterUse = waterUse;
		this.outputItem = outputItem;
		this.oreDictName = oreDictName;
	}

	public ChemicalExtractorRecipe(int waterUse, Item inputItem, ItemStack outputItem) {
		this.waterUse = waterUse;
		this.inputItem = inputItem;
		this.outputItem = outputItem;
	}

	public int getWaterUse()
	{
		return waterUse;
	}

	public Item getInput()
	{
		return inputItem;
	}

	public ItemStack getOutput()
	{
		return outputItem;
	}

	public String getOreDictName()
	{
		return oreDictName;
	}

}
