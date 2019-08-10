package physica.core.common;

import physica.api.core.abstraction.AbstractionLayer;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.core.common.items.ItemBattery;
import physica.core.common.items.ItemMultimeter;
import physica.core.common.items.ItemWrench;
import physica.library.item.ItemDescriptable;
import physica.library.item.ItemMetaHolder;

public class CoreItemRegister implements IContent {

	public static ItemDescriptable	itemEmptyCell;
	public static ItemMetaHolder	itemMetaCircuit;
	public static ItemMetaHolder	itemMetaPlate;
	public static ItemMetaHolder	itemMetaIngot;
	public static ItemMetaHolder	itemMetaBlend;
	public static ItemDescriptable	itemMotor;
	public static ItemWrench		itemWrench;
	public static ItemBattery		itemBattery;
	public static ItemMultimeter	itemMultimeter;

	@Override
	public void register(LoadPhase phase)
	{
		if (phase == LoadPhase.RegisterObjects)
		{
			AbstractionLayer.Registering.registerItem(itemEmptyCell = (ItemDescriptable) new ItemDescriptable("emptyCell").setMaxStackSize(64), itemEmptyCell.getUnlocalizedName());
			AbstractionLayer.Registering.registerItem(itemMetaCircuit = new ItemMetaHolder("circuit_basic").addSubItem("circuit_advanced").addSubItem("circuit_elite"), "item.metaCircuit");
			AbstractionLayer.Registering.registerItem(itemMetaPlate = new ItemMetaHolder("plateIron").addSubItem("plateSteel").addSubItem("plateLead"), "item.metaPlate");
			AbstractionLayer.Registering.registerItem(itemMetaIngot = new ItemMetaHolder("tinIngot").addSubItem("copperIngot").addSubItem("steelIngot").addSubItem("leadIngot").addSubItem("silverIngot").addSubItem("superConductiveIngot"),
					"item.metaIngot");
			AbstractionLayer.Registering.registerItem(itemMetaBlend = new ItemMetaHolder("blendSuperConductive"), "item.metaBlend");
			AbstractionLayer.Registering.registerItem(itemWrench = new ItemWrench(), itemWrench.getUnlocalizedName());
			AbstractionLayer.Registering.registerItem(itemMotor = (ItemDescriptable) new ItemDescriptable("motor").setMaxStackSize(64), itemMotor.getUnlocalizedName());
			AbstractionLayer.Registering.registerItem(itemBattery = new ItemBattery("phyBattery"), itemBattery.getUnlocalizedName());
			AbstractionLayer.Registering.registerItem(itemMultimeter = new ItemMultimeter(), itemMultimeter.getUnlocalizedName());
			itemMetaPlate.setTextureFolder("plate");
			itemMetaIngot.setTextureFolder("ingot");
			itemMetaBlend.setTextureFolder("blend");
			itemMetaCircuit.setTextureFolder("circuit");
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
			AbstractionLayer.Registering.registerOre("phyBattery", CoreItemRegister.itemBattery);
		}
	}
}
