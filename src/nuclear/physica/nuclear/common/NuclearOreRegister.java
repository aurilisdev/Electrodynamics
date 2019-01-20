package physica.nuclear.common;

import net.minecraftforge.oredict.OreDictionary;
import physica.api.core.IContent;

public class NuclearOreRegister implements IContent {

	@Override
	public void preInit() {
		OreDictionary.registerOre("antimatterGram", NuclearItemRegister.itemAntimatterCell1Gram);
		OreDictionary.registerOre("antimatter125Milligram", NuclearItemRegister.itemAntimatterCell125Milligram);
		OreDictionary.registerOre("darkMatter", NuclearItemRegister.itemDarkmatterCell);
		OreDictionary.registerOre("strangeMatter", NuclearItemRegister.itemDarkmatterCell);
		OreDictionary.registerOre("ingotUranium", NuclearItemRegister.itemUranium235);
		OreDictionary.registerOre("ingotUranium238", NuclearItemRegister.itemUranium238);
		OreDictionary.registerOre("oreUranium", NuclearBlockRegister.blockUraniumOre);
	}
}
