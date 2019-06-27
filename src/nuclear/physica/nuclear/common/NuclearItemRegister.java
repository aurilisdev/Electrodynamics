package physica.nuclear.common;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import physica.CoreReferences;
import physica.api.core.IContent;
import physica.library.item.ItemInformationHolder;
import physica.library.item.ItemUpdateable;
import physica.nuclear.common.configuration.ConfigNuclearPhysics;
import physica.nuclear.common.items.ItemGeigerCounter;
import physica.nuclear.common.items.armor.ItemHazmatArmor;
import physica.nuclear.common.items.update.ItemUpdateAntimatter;
import physica.nuclear.common.items.update.ItemUpdateDarkMatter;
import physica.nuclear.common.items.update.ItemUpdateUranium;

public class NuclearItemRegister implements IContent {

	public static ItemInformationHolder itemAntimatterCell125Milligram;
	public static ItemInformationHolder itemAntimatterCell1Gram;
	public static ItemInformationHolder itemDarkmatterCell;
	public static ItemInformationHolder itemHighEnrichedFuelCell;
	public static ItemInformationHolder itemLowEnrichedFuelCell;
	public static ItemInformationHolder itemHeavyWaterCell;
	public static ItemInformationHolder itemDeuteriumCell;
	public static ItemInformationHolder itemTritiumCell;
	public static ItemInformationHolder itemEmptyElectromagneticCell;
	public static ItemInformationHolder itemEmptyQuantumCell;
	public static ItemGeigerCounter itemGeigerCounter;

	public static ItemInformationHolder itemUranium235;
	public static ItemInformationHolder itemUranium238;
	public static ItemInformationHolder itemYellowcake;

	public static ItemHazmatArmor itemHazmatHelmet;
	public static ItemHazmatArmor itemHazmatPlate;
	public static ItemHazmatArmor itemHazmatLegs;
	public static ItemHazmatArmor itemHazmatBoots;

	@Override
	public void preInit()
	{
		GameRegistry.registerItem(itemAntimatterCell125Milligram = (ItemInformationHolder) ((ItemUpdateable) new ItemInformationHolder("antimatterCell100Milligram", "125 Milligrams")
				.setTextureName(CoreReferences.PREFIX + "antimatterCell")).setUpdate(new ItemUpdateAntimatter()).setCreativeTab(NuclearTabRegister.nuclearPhysicsTab),
				itemAntimatterCell125Milligram.getUnlocalizedName());
		GameRegistry.registerItem(itemAntimatterCell1Gram = (ItemInformationHolder) ((ItemUpdateable) new ItemInformationHolder("antimatterCell1Gram", "1 Gram")
				.setTextureName(CoreReferences.PREFIX + "antimatterCell")).setUpdate(new ItemUpdateAntimatter().setScale(3))
						.setCreativeTab(NuclearTabRegister.nuclearPhysicsTab),
				itemAntimatterCell1Gram.getUnlocalizedName());
		GameRegistry.registerItem(itemDarkmatterCell =
				(ItemInformationHolder) new ItemInformationHolder("darkmatterCell").setUpdate(new ItemUpdateDarkMatter()).setMaxDamage(ConfigNuclearPhysics.DARK_MATTER_USES).setMaxStackSize(1)
						.setCreativeTab(NuclearTabRegister.nuclearPhysicsTab),
				itemDarkmatterCell.getUnlocalizedName());
		GameRegistry.registerItem(itemHighEnrichedFuelCell = (ItemInformationHolder) new ItemInformationHolder("fuelCell", "High-Enriched Uranium").setUpdate(new ItemUpdateUranium().setScale(4.5f))
				.setMaxDamage(24000).setTextureName(CoreReferences.PREFIX + "fissileFuelCell").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab).setMaxStackSize(1),
				itemHighEnrichedFuelCell.getUnlocalizedName() + "Fissile");
		GameRegistry.registerItem(itemLowEnrichedFuelCell = (ItemInformationHolder) new ItemInformationHolder("fuelCell", "Low-Enriched Uranium").setUpdate(new ItemUpdateUranium().setScale(1.25f))
				.setMaxDamage(26000).setTextureName(CoreReferences.PREFIX + "breederFuelCell").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab).setMaxStackSize(1),
				itemLowEnrichedFuelCell.getUnlocalizedName() + "Breeder");
		GameRegistry.registerItem(
				itemEmptyElectromagneticCell = (ItemInformationHolder) new ItemInformationHolder("emptyElectromagneticCell").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab).setMaxStackSize(64),
				itemEmptyElectromagneticCell.getUnlocalizedName());
		GameRegistry.registerItem(
				itemEmptyQuantumCell = (ItemInformationHolder) new ItemInformationHolder("emptyQuantumCell").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab).setMaxStackSize(64),
				itemEmptyQuantumCell.getUnlocalizedName());

		GameRegistry.registerItem(
				itemGeigerCounter = new ItemGeigerCounter("geigerCounter"),
				itemGeigerCounter.getUnlocalizedName());

		GameRegistry.registerItem(itemHeavyWaterCell = (ItemInformationHolder) new ItemInformationHolder("heavyWaterCell", "125ml").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab),
				itemHeavyWaterCell.getUnlocalizedName());
		GameRegistry.registerItem(itemDeuteriumCell = (ItemInformationHolder) new ItemInformationHolder("deuteriumCell", "25ml").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab),
				itemDeuteriumCell.getUnlocalizedName());
		GameRegistry.registerItem(itemTritiumCell = (ItemInformationHolder) new ItemInformationHolder("tritiumCell", "12.5ml").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab),
				itemTritiumCell.getUnlocalizedName());
		GameRegistry.registerItem(itemUranium235 = (ItemInformationHolder) new ItemInformationHolder("uranium", "Isotope 235").setUpdate(new ItemUpdateUranium().setScale(8.1f))
				.setTextureName(CoreReferences.PREFIX + "uranium235").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab), itemUranium235.getUnlocalizedName() + "235");
		GameRegistry.registerItem(itemUranium238 = (ItemInformationHolder) new ItemInformationHolder("uranium", "Isotope 238").setUpdate(new ItemUpdateUranium().setScale(2.25f))
				.setTextureName(CoreReferences.PREFIX + "uranium238").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab), itemUranium238.getUnlocalizedName() + "238");
		GameRegistry.registerItem(itemYellowcake = (ItemInformationHolder) new ItemInformationHolder("yellowcake").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab),
				itemYellowcake.getUnlocalizedName());

		if (FMLCommonHandler.instance().getEffectiveSide().isClient())
		{
			GameRegistry.registerItem(itemHazmatHelmet = new ItemHazmatArmor("HazmatHelmet", RenderingRegistry.addNewArmourRendererPrefix("HazmatHelmet"), 0), itemHazmatHelmet.getUnlocalizedName());
			GameRegistry.registerItem(itemHazmatPlate = new ItemHazmatArmor("HazmatPlate", RenderingRegistry.addNewArmourRendererPrefix("HazmatPlate"), 1), itemHazmatPlate.getUnlocalizedName());
			GameRegistry.registerItem(itemHazmatLegs = new ItemHazmatArmor("HazmatLegs", RenderingRegistry.addNewArmourRendererPrefix("HazmatLegs"), 2), itemHazmatLegs.getUnlocalizedName());
			GameRegistry.registerItem(itemHazmatBoots = new ItemHazmatArmor("HazmatBoots", RenderingRegistry.addNewArmourRendererPrefix("HazmatBoots"), 3), itemHazmatBoots.getUnlocalizedName());
		} else
		{
			GameRegistry.registerItem(itemHazmatHelmet = new ItemHazmatArmor("HazmatHelmet", 0, 0), itemHazmatHelmet.getUnlocalizedName());
			GameRegistry.registerItem(itemHazmatPlate = new ItemHazmatArmor("HazmatPlate", 0, 1), itemHazmatPlate.getUnlocalizedName());
			GameRegistry.registerItem(itemHazmatLegs = new ItemHazmatArmor("HazmatLegs", 0, 2), itemHazmatLegs.getUnlocalizedName());
			GameRegistry.registerItem(itemHazmatBoots = new ItemHazmatArmor("HazmatBoots", 0, 3), itemHazmatBoots.getUnlocalizedName());
		}

		itemAntimatterCell125Milligram.addOreDictionaryInput("antimatter125Milligram", 0);
		itemAntimatterCell1Gram.addOreDictionaryInput("antimatterGram", 0);
		itemDarkmatterCell.addOreDictionaryInput("darkMatter", 0).addOreDictionaryInput("strangeMatter", 0);
		itemUranium235.addOreDictionaryInput("uranium235", 0);
		itemUranium238.addOreDictionaryInput("uranium238", 0);
		itemLowEnrichedFuelCell.addOreDictionaryInput("ingotUranium238", 0);
		itemHighEnrichedFuelCell.addOreDictionaryInput("ingotUranium235", 0);
	}
}
