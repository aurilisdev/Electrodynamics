package physica.core.common.component;

import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.registries.IForgeRegistry;
import physica.core.common.item.ItemBase;
import physica.core.common.item.subtypes.EnumBlend;
import physica.core.common.item.subtypes.EnumGear;
import physica.core.common.item.subtypes.EnumImpureBlend;
import physica.core.common.item.subtypes.EnumIngot;
import physica.core.common.item.subtypes.EnumPlate;
import physica.core.common.item.tool.ItemAxeTool;
import physica.core.common.item.tool.ItemPickaxeTool;
import physica.core.common.item.tool.ItemSpadeTool;
import physica.core.common.item.tool.ItemSwordTool;
import physica.core.common.item.tool.ToolMaterialPhysica;

public class ComponentItems {
	public static ItemBase ingotBase = new ItemBase("ingotBase", EnumIngot.values().length)
			.setCreativeTab(ComponentsTab.BASICCOMPONENTS);

	public static ItemBase plateBase = new ItemBase("plateBase", EnumPlate.values().length)
			.setCreativeTab(ComponentsTab.BASICCOMPONENTS);
	public static ItemBase blendBase = new ItemBase("blendBase", EnumBlend.values().length)
			.setCreativeTab(ComponentsTab.BASICCOMPONENTS);
	public static ItemBase impureBlendBase = new ItemBase("impureBlendBase", EnumImpureBlend.values().length)
			.setCreativeTab(ComponentsTab.BASICCOMPONENTS);
	public static ItemBase gearBase = new ItemBase("gearBase", EnumGear.values().length)
			.setCreativeTab(ComponentsTab.BASICCOMPONENTS);
	public static HashMap<ToolMaterial, ItemAxeTool> axeMap = new HashMap<>();
	public static HashMap<ToolMaterial, ItemPickaxeTool> pickAxeMap = new HashMap<>();
	public static HashMap<ToolMaterial, ItemSpadeTool> spadeMap = new HashMap<>();
	public static HashMap<ToolMaterial, ItemSwordTool> swordMap = new HashMap<>();

	public static void register(IForgeRegistry<Item> registry) {
		ToolMaterialPhysica.init();
		axeMap.put(ToolMaterialPhysica.COPPER, new ItemAxeTool("axeCopper", ToolMaterialPhysica.COPPER));
		pickAxeMap.put(ToolMaterialPhysica.COPPER, new ItemPickaxeTool("pickaxeCopper", ToolMaterialPhysica.COPPER));
		spadeMap.put(ToolMaterialPhysica.COPPER, new ItemSpadeTool("spadeCopper", ToolMaterialPhysica.COPPER));
		swordMap.put(ToolMaterialPhysica.COPPER, new ItemSwordTool("swordCopper", ToolMaterialPhysica.COPPER));

		axeMap.put(ToolMaterialPhysica.TIN, new ItemAxeTool("axeTin", ToolMaterialPhysica.TIN));
		pickAxeMap.put(ToolMaterialPhysica.TIN, new ItemPickaxeTool("pickaxeTin", ToolMaterialPhysica.TIN));
		spadeMap.put(ToolMaterialPhysica.TIN, new ItemSpadeTool("spadeTin", ToolMaterialPhysica.TIN));
		swordMap.put(ToolMaterialPhysica.TIN, new ItemSwordTool("swordTin", ToolMaterialPhysica.TIN));

		axeMap.put(ToolMaterialPhysica.LEAD, new ItemAxeTool("axeLead", ToolMaterialPhysica.LEAD));
		pickAxeMap.put(ToolMaterialPhysica.LEAD, new ItemPickaxeTool("pickaxeLead", ToolMaterialPhysica.LEAD));
		spadeMap.put(ToolMaterialPhysica.LEAD, new ItemSpadeTool("spadeLead", ToolMaterialPhysica.LEAD));
		swordMap.put(ToolMaterialPhysica.LEAD, new ItemSwordTool("swordLead", ToolMaterialPhysica.LEAD));

		axeMap.put(ToolMaterialPhysica.SILVER, new ItemAxeTool("axeSilver", ToolMaterialPhysica.SILVER));
		pickAxeMap.put(ToolMaterialPhysica.SILVER, new ItemPickaxeTool("pickaxeSilver", ToolMaterialPhysica.SILVER));
		spadeMap.put(ToolMaterialPhysica.SILVER, new ItemSpadeTool("spadeSilver", ToolMaterialPhysica.SILVER));
		swordMap.put(ToolMaterialPhysica.SILVER, new ItemSwordTool("swordSilver", ToolMaterialPhysica.SILVER));

		axeMap.put(ToolMaterialPhysica.BRONZE, new ItemAxeTool("axeBronze", ToolMaterialPhysica.BRONZE));
		pickAxeMap.put(ToolMaterialPhysica.BRONZE, new ItemPickaxeTool("pickaxeBronze", ToolMaterialPhysica.BRONZE));
		spadeMap.put(ToolMaterialPhysica.BRONZE, new ItemSpadeTool("spadeBronze", ToolMaterialPhysica.BRONZE));
		swordMap.put(ToolMaterialPhysica.BRONZE, new ItemSwordTool("swordBronze", ToolMaterialPhysica.BRONZE));

		axeMap.put(ToolMaterialPhysica.STEEL, new ItemAxeTool("axeSteel", ToolMaterialPhysica.STEEL));
		pickAxeMap.put(ToolMaterialPhysica.STEEL, new ItemPickaxeTool("pickaxeSteel", ToolMaterialPhysica.STEEL));
		spadeMap.put(ToolMaterialPhysica.STEEL, new ItemSpadeTool("spadeSteel", ToolMaterialPhysica.STEEL));
		swordMap.put(ToolMaterialPhysica.STEEL, new ItemSwordTool("swordSteel", ToolMaterialPhysica.STEEL));

		registry.registerAll(ingotBase, plateBase, blendBase, impureBlendBase, gearBase);
		registry.registerAll(axeMap.values().toArray(new ItemAxeTool[0]));
		registry.registerAll(pickAxeMap.values().toArray(new ItemPickaxeTool[0]));
		registry.registerAll(spadeMap.values().toArray(new ItemSpadeTool[0]));
		registry.registerAll(swordMap.values().toArray(new ItemSwordTool[0]));
	}

	public static void registerModels() {
		ingotBase.registerItemModel("ingot");
		plateBase.registerItemModel("plate");
		blendBase.registerItemModel("blend");
		impureBlendBase.registerItemModel("impureblend");
		gearBase.registerItemModel("gear");
		for (Entry<ToolMaterial, ItemAxeTool> entry : axeMap.entrySet()) {
			entry.getValue().registerItemModel("tool/" + entry.getKey().name().toLowerCase().replace("phys_", ""));
		}
		for (Entry<ToolMaterial, ItemSwordTool> entry : swordMap.entrySet()) {
			entry.getValue().registerItemModel("tool/" + entry.getKey().name().toLowerCase().replace("phys_", ""));
		}
		for (Entry<ToolMaterial, ItemSpadeTool> entry : spadeMap.entrySet()) {
			entry.getValue().registerItemModel("tool/" + entry.getKey().name().toLowerCase().replace("phys_", ""));
		}
		for (Entry<ToolMaterial, ItemPickaxeTool> entry : pickAxeMap.entrySet()) {
			entry.getValue().registerItemModel("tool/" + entry.getKey().name().toLowerCase().replace("phys_", ""));
		}
	}

}