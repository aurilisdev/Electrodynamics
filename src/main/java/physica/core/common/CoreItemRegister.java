package physica.core.common;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import physica.api.core.IContent;
import physica.core.common.items.ItemBattery;
import physica.core.common.items.ItemMultimeter;
import physica.core.common.items.ItemWrench;
import physica.library.item.ItemInformationHolder;
import physica.library.item.ItemMetaHolder;

public class CoreItemRegister implements IContent {

	public static ItemInformationHolder	itemEmptyCell;
	public static ItemMetaHolder		itemMetaCircuit;
	public static ItemMetaHolder		itemMetaPlate;
	public static ItemMetaHolder		itemMetaIngot;
	public static ItemMetaHolder		itemMetaBlend;
	public static ItemInformationHolder	itemMotor;
	public static ItemWrench			itemWrench;
	public static ItemBattery			itemBattery;
	public static ItemMultimeter		itemMultimeter;

	@Override
	public void preInit()
	{
		GameRegistry.registerItem(itemEmptyCell = (ItemInformationHolder) new ItemInformationHolder("emptyCell").setMaxStackSize(64), itemEmptyCell.getUnlocalizedName());
		GameRegistry.registerItem(itemMetaCircuit = new ItemMetaHolder("circuit_basic").addSubItem("circuit_advanced").addSubItem("circuit_elite"), "item.metaCircuit");
		GameRegistry.registerItem(itemMetaPlate = new ItemMetaHolder("plateIron").addSubItem("plateSteel").addSubItem("plateLead"), "item.metaPlate");
		GameRegistry.registerItem(itemMetaIngot = new ItemMetaHolder("tinIngot").addSubItem("copperIngot").addSubItem("steelIngot").addSubItem("leadIngot").addSubItem("silverIngot").addSubItem("superConductiveIngot"), "item.metaIngot");
		GameRegistry.registerItem(itemMetaBlend = new ItemMetaHolder("blendSuperConductive"), "item.metaBlend");
		GameRegistry.registerItem(itemWrench = new ItemWrench(), itemWrench.getUnlocalizedName());
		GameRegistry.registerItem(itemMotor = (ItemInformationHolder) new ItemInformationHolder("motor").setMaxStackSize(64), itemMotor.getUnlocalizedName());
		GameRegistry.registerItem(itemBattery = new ItemBattery("phyBattery"), itemBattery.getUnlocalizedName());
		GameRegistry.registerItem(itemMultimeter = new ItemMultimeter(), itemMultimeter.getUnlocalizedName());
		itemMetaPlate.addOreDictionaryInput("plateIron", 0);
		itemMetaPlate.addOreDictionaryInput("plateSteel", 1);
		itemMetaPlate.addOreDictionaryInput("plateLead", 2);
		itemMetaIngot.addOreDictionaryInput("ingotTin", 0);
		itemMetaIngot.addOreDictionaryInput("ingotCopper", 1);
		itemMetaIngot.addOreDictionaryInput("ingotSteel", 2);
		itemMetaIngot.addOreDictionaryInput("ingotLead", 3);
		itemMetaIngot.addOreDictionaryInput("ingotSilver", 4);
		itemMetaIngot.addOreDictionaryInput("ingotSuperConductive", 5);
		itemMetaBlend.addOreDictionaryInput("blendSuperConductive", 0);
		itemMetaCircuit.addOreDictionaryInput("circuitBasic", 0);
		itemMetaCircuit.addOreDictionaryInput("circuitAdvanced", 1);
		itemMetaCircuit.addOreDictionaryInput("circuitElite", 2);
		itemMotor.addOreDictionaryInput("motor", 0);
		OreDictionary.registerOre("phyBattery", CoreItemRegister.itemBattery);
	}
}
