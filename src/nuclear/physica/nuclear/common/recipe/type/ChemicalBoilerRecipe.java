package physica.nuclear.common.recipe.type;

import net.minecraft.item.Item;

public class ChemicalBoilerRecipe {

	private int		waterRequired;
	private Item	inputItem;
	private String	oreDictName;
	private int		hexaFluorideGenerated;

	public ChemicalBoilerRecipe(int waterRequired, Item inputItem, int hexaFluorideGenerated) {
		this.waterRequired = waterRequired;
		this.inputItem = inputItem;
		this.hexaFluorideGenerated = hexaFluorideGenerated;
	}

	public ChemicalBoilerRecipe(int waterRequired, String oreDictName, int hexaFluorideGenerated) {
		this.waterRequired = waterRequired;
		this.oreDictName = oreDictName;
		this.hexaFluorideGenerated = hexaFluorideGenerated;
	}

	public int getWaterUse()
	{
		return waterRequired;
	}

	public Item getInput()
	{
		return inputItem;
	}

	public int getHexafluorideGenerated()
	{
		return hexaFluorideGenerated;
	}

	public String getOreDictName()
	{
		return oreDictName;
	}
}
