package physica.nuclear.common;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import physica.CoreReferences;
import physica.api.core.IContent;
import physica.library.item.ItemInformationHolder;
import physica.library.item.ItemUpdateable;
import physica.nuclear.common.items.armor.ItemHazmatArmor;
import physica.nuclear.common.items.update.ItemUpdateAntimatter;
import physica.nuclear.common.items.update.ItemUpdateDarkMatter;
import physica.nuclear.common.items.update.ItemUpdateUranium;

public class NuclearItemRegister implements IContent {

	public static Item itemAntimatterCell125Milligram;
	public static Item itemAntimatterCell1Gram;
	public static Item itemDarkmatterCell;
	public static Item itemHighEnrichedFuelCell;
	public static Item itemLowEnrichedFuelCell;
	public static Item itemHeavyWaterCell;
	public static Item itemDeuteriumCell;
	public static Item itemTritiumCell;
	public static Item itemEmptyElectromagneticCell;
	public static Item itemEmptyQuantumCell;

	public static Item itemUranium235;
	public static Item itemUranium238;
	public static Item itemYellowcake;

	public static ItemHazmatArmor itemHazmatHelmet;
	public static ItemHazmatArmor itemHazmatPlate;
	public static ItemHazmatArmor itemHazmatLegs;
	public static ItemHazmatArmor itemHazmatBoots;

	@Override
	public void preInit() {
		GameRegistry.registerItem(itemAntimatterCell125Milligram = ((ItemUpdateable) new ItemInformationHolder("antimatterCell100Milligram", "125 Milligrams")
				.setTextureName(CoreReferences.PREFIX + "antimatterCell")).setUpdate(new ItemUpdateAntimatter()).setCreativeTab(NuclearTabRegister.nuclearPhysicsTab),
				itemAntimatterCell125Milligram.getUnlocalizedName());
		GameRegistry.registerItem(itemAntimatterCell1Gram = ((ItemUpdateable) new ItemInformationHolder("antimatterCell1Gram", "1 Gram")
				.setTextureName(CoreReferences.PREFIX + "antimatterCell")).setUpdate(new ItemUpdateAntimatter().setScale(3))
						.setCreativeTab(NuclearTabRegister.nuclearPhysicsTab),
				itemAntimatterCell1Gram.getUnlocalizedName());
		GameRegistry.registerItem(itemDarkmatterCell =
				new ItemInformationHolder("darkmatterCell").setUpdate(new ItemUpdateDarkMatter()).setMaxDamage(8).setMaxStackSize(1).setCreativeTab(NuclearTabRegister.nuclearPhysicsTab),
				itemDarkmatterCell.getUnlocalizedName());
		GameRegistry.registerItem(itemHighEnrichedFuelCell = new ItemInformationHolder("fuelCell", "High-Enriched Uranium").setUpdate(new ItemUpdateUranium().setScale(4))
				.setMaxDamage(24000).setTextureName(CoreReferences.PREFIX + "fissileFuelCell").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab).setMaxStackSize(1),
				itemHighEnrichedFuelCell.getUnlocalizedName() + "Fissile");
		GameRegistry.registerItem(itemLowEnrichedFuelCell = new ItemInformationHolder("fuelCell", "Low-Enriched Uranium").setUpdate(new ItemUpdateUranium().setScale(1.75f))
				.setMaxDamage(26000).setTextureName(CoreReferences.PREFIX + "breederFuelCell").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab).setMaxStackSize(1),
				itemLowEnrichedFuelCell.getUnlocalizedName() + "Breeder");
		GameRegistry.registerItem(
				itemEmptyElectromagneticCell = new ItemInformationHolder("emptyElectromagneticCell").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab).setMaxStackSize(64),
				itemEmptyElectromagneticCell.getUnlocalizedName());
		GameRegistry.registerItem(
				itemEmptyQuantumCell = new ItemInformationHolder("emptyQuantumCell").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab).setMaxStackSize(64),
				itemEmptyQuantumCell.getUnlocalizedName());

		GameRegistry.registerItem(itemHeavyWaterCell = new ItemInformationHolder("heavyWaterCell", "125ml").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab),
				itemHeavyWaterCell.getUnlocalizedName());
		GameRegistry.registerItem(itemDeuteriumCell = new ItemInformationHolder("deuteriumCell", "25ml").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab),
				itemDeuteriumCell.getUnlocalizedName());
		GameRegistry.registerItem(itemTritiumCell = new ItemInformationHolder("tritiumCell", "12.5ml").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab),
				itemTritiumCell.getUnlocalizedName());
		GameRegistry.registerItem(itemUranium235 = new ItemInformationHolder("uranium", "Isotope 235").setUpdate(new ItemUpdateUranium().setScale(6))
				.setTextureName(CoreReferences.PREFIX + "uranium235").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab), itemUranium235.getUnlocalizedName() + "235");
		GameRegistry.registerItem(itemUranium238 = new ItemInformationHolder("uranium", "Isotope 238").setUpdate(new ItemUpdateUranium().setScale(2.25f))
				.setTextureName(CoreReferences.PREFIX + "uranium238").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab), itemUranium238.getUnlocalizedName() + "238");
		GameRegistry.registerItem(itemYellowcake = new ItemInformationHolder("yellowcake").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab), itemYellowcake.getUnlocalizedName());

		if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
			GameRegistry.registerItem(itemHazmatHelmet = new ItemHazmatArmor("HazmatHelmet", RenderingRegistry.addNewArmourRendererPrefix("HazmatHelmet"), 0), itemHazmatHelmet.getUnlocalizedName());
			GameRegistry.registerItem(itemHazmatPlate = new ItemHazmatArmor("HazmatPlate", RenderingRegistry.addNewArmourRendererPrefix("HazmatPlate"), 1), itemHazmatPlate.getUnlocalizedName());
			GameRegistry.registerItem(itemHazmatLegs = new ItemHazmatArmor("HazmatLegs", RenderingRegistry.addNewArmourRendererPrefix("HazmatLegs"), 2), itemHazmatLegs.getUnlocalizedName());
			GameRegistry.registerItem(itemHazmatBoots = new ItemHazmatArmor("HazmatBoots", RenderingRegistry.addNewArmourRendererPrefix("HazmatBoots"), 3), itemHazmatBoots.getUnlocalizedName());
		} else {
			GameRegistry.registerItem(itemHazmatHelmet = new ItemHazmatArmor("HazmatHelmet", 0, 0), itemHazmatHelmet.getUnlocalizedName());
			GameRegistry.registerItem(itemHazmatPlate = new ItemHazmatArmor("HazmatPlate", 0, 1), itemHazmatPlate.getUnlocalizedName());
			GameRegistry.registerItem(itemHazmatLegs = new ItemHazmatArmor("HazmatLegs", 0, 2), itemHazmatLegs.getUnlocalizedName());
			GameRegistry.registerItem(itemHazmatBoots = new ItemHazmatArmor("HazmatBoots", 0, 3), itemHazmatBoots.getUnlocalizedName());
		}

	}
}
