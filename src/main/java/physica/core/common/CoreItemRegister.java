package physica.core.common;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import physica.api.core.IContent;
import physica.core.common.items.ItemBattery;
import physica.core.common.items.ItemWrench;
import physica.library.item.ItemInformationHolder;
import physica.library.item.ItemMetaHolder;

public class CoreItemRegister implements IContent {

	public static Item itemEmptyContainer;

	public static Item itemMetaCircuit;

	public static Item itemMetaPlate;

	public static Item itemMetaIngot;

	public static Item itemMotor;

	public static ItemWrench itemWrench;

	public static Item itemBattery;

	@Override
	public void preInit() {
		GameRegistry.registerItem(itemEmptyContainer = new ItemInformationHolder("emptyCell").setMaxStackSize(64), itemEmptyContainer.getUnlocalizedName());

		GameRegistry.registerItem(itemMetaCircuit = new ItemMetaHolder("circuit_basic").addSubItem("circuit_advanced").addSubItem("circuit_elite"), "item.metaCircuit");

		GameRegistry.registerItem(itemMetaPlate = new ItemMetaHolder("plateIron").addSubItem("plateSteel"), "item.metaPlate");

		GameRegistry.registerItem(itemMetaIngot = new ItemMetaHolder("tinIngot").addSubItem("copperIngot").addSubItem("steelIngot"), "item.metaIngot");

		GameRegistry.registerItem(itemWrench = new ItemWrench(), itemWrench.getUnlocalizedName());

		GameRegistry.registerItem(itemMotor = new ItemInformationHolder("motor").setMaxStackSize(64), itemMotor.getUnlocalizedName());

		GameRegistry.registerItem(itemBattery = new ItemBattery("phyBattery"), itemBattery.getUnlocalizedName());

	}
}
