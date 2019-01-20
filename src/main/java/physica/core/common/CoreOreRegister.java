package physica.core.common;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import physica.api.core.IContent;

public class CoreOreRegister implements IContent {

	@Override
	public void postInit() {
		OreDictionary.registerOre("plateIron", new ItemStack(CoreItemRegister.itemMetaPlate, 1, 0));
		OreDictionary.registerOre("plateSteel", new ItemStack(CoreItemRegister.itemMetaPlate, 1, 1));
		OreDictionary.registerOre("ingotTin", new ItemStack(CoreItemRegister.itemMetaIngot, 1, 0));
		OreDictionary.registerOre("ingotCopper", new ItemStack(CoreItemRegister.itemMetaIngot, 1, 1));
		OreDictionary.registerOre("ingotSteel", new ItemStack(CoreItemRegister.itemMetaIngot, 1, 2));
		OreDictionary.registerOre("circuitBasic", new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 0));
		OreDictionary.registerOre("circuitAdvanced", new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 1));
		OreDictionary.registerOre("circuitElite", new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 2));

	}

	@Override
	public void preInit() {
		OreDictionary.registerOre("motor", CoreItemRegister.itemMotor);
		OreDictionary.registerOre("phyBattery", CoreItemRegister.itemBattery);
		OreDictionary.registerOre("oreTin", CoreBlockRegister.blockTinOre);
		OreDictionary.registerOre("oreCopper", CoreBlockRegister.blockCopperOre);
	}
}
