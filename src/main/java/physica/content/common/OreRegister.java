package physica.content.common;

import net.minecraftforge.oredict.OreDictionary;
import physica.api.base.IProxyContent;

public class OreRegister implements IProxyContent {
	@Override
	public void preInit() {
		OreDictionary.registerOre("antimatterGram", ItemRegister.itemAntimatterContainer1Gram);
		OreDictionary.registerOre("antimatter125Milligram", ItemRegister.itemAntimatterContainer125Milligram);
		OreDictionary.registerOre("darkMatter", ItemRegister.itemDarkmatterContainer);
		OreDictionary.registerOre("strangeMatter", ItemRegister.itemDarkmatterContainer);
		OreDictionary.registerOre("ingotUranium", ItemRegister.itemUranium235);
		OreDictionary.registerOre("ingotUranium238", ItemRegister.itemUranium238);
		OreDictionary.registerOre("oreUranium", BlockRegister.blockUraniumOre);
	}
}
