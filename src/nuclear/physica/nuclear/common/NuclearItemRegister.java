package physica.nuclear.common;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import physica.api.core.abstraction.AbstractionLayer;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.library.item.ItemDescriptable;
import physica.library.item.ItemUpdateable;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.configuration.ConfigNuclearPhysics;
import physica.nuclear.common.items.ItemGeigerCounter;
import physica.nuclear.common.items.armor.ItemHazmatArmor;
import physica.nuclear.common.items.update.ItemUpdateAntimatter;
import physica.nuclear.common.items.update.ItemUpdateDarkMatter;
import physica.nuclear.common.items.update.ItemUpdateUranium;

public class NuclearItemRegister implements IContent {

	public static ItemDescriptable itemAntimatterCell125Milligram;
	public static ItemDescriptable itemAntimatterCell1Gram;
	public static ItemDescriptable itemDarkmatterCell;
	public static ItemDescriptable itemHighEnrichedFuelCell;
	public static ItemDescriptable itemLowEnrichedFuelCell;
	public static ItemDescriptable itemHeavyWaterCell;
	public static ItemDescriptable itemDeuteriumCell;
	public static ItemDescriptable itemTritiumCell;
	public static ItemDescriptable itemEmptyElectromagneticCell;
	public static ItemDescriptable itemEmptyQuantumCell;
	public static ItemGeigerCounter itemGeigerCounter;

	public static ItemDescriptable itemUranium235;
	public static ItemDescriptable itemUranium238;
	public static ItemDescriptable itemYellowcake;

	public static ItemHazmatArmor itemHazmatHelmet;
	public static ItemHazmatArmor itemHazmatPlate;
	public static ItemHazmatArmor itemHazmatLegs;
	public static ItemHazmatArmor itemHazmatBoots;
	public static ItemHazmatArmor itemReinforcedHazmatHelmet;
	public static ItemHazmatArmor itemReinforcedHazmatPlate;
	public static ItemHazmatArmor itemReinforcedHazmatLegs;
	public static ItemHazmatArmor itemReinforcedHazmatBoots;

	@Override
	public void register(LoadPhase phase) {
		if (phase == LoadPhase.RegisterObjects) {
			GameRegistry.registerItem(itemAntimatterCell125Milligram = (ItemDescriptable) ((ItemUpdateable) new ItemDescriptable(NuclearReferences.PREFIX, "antimatterCell100Milligram", "125 Milligrams").setTextureName(NuclearReferences.PREFIX + "antimattercell")).setUpdate(new ItemUpdateAntimatter()).setCreativeTab(NuclearTabRegister.nuclearPhysicsTab), itemAntimatterCell125Milligram.getUnlocalizedName());
			AbstractionLayer.Registering.registerItem(itemAntimatterCell1Gram = (ItemDescriptable) ((ItemUpdateable) new ItemDescriptable(NuclearReferences.PREFIX, "antimatterCell1Gram", "1 Gram").setTextureName(NuclearReferences.PREFIX + "antimattercell")).setUpdate(new ItemUpdateAntimatter().setScale(3)).setCreativeTab(NuclearTabRegister.nuclearPhysicsTab), itemAntimatterCell1Gram.getUnlocalizedName());
			AbstractionLayer.Registering.registerItem(itemDarkmatterCell = (ItemDescriptable) new ItemDescriptable(NuclearReferences.PREFIX, "darkmatterCell").setUpdate(new ItemUpdateDarkMatter()).setMaxDamage(ConfigNuclearPhysics.DARK_MATTER_USES).setMaxStackSize(1).setCreativeTab(NuclearTabRegister.nuclearPhysicsTab), itemDarkmatterCell.getUnlocalizedName());
			AbstractionLayer.Registering.registerItem(itemHighEnrichedFuelCell = (ItemDescriptable) new ItemDescriptable(NuclearReferences.PREFIX, "fuelCell", "High-Enriched Uranium").setUpdate(new ItemUpdateUranium().setScale(4.5f)).setMaxDamage(24000).setTextureName(NuclearReferences.PREFIX + "fissilefuelcell").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab).setMaxStackSize(1), itemHighEnrichedFuelCell.getUnlocalizedName() + "Fissile");
			AbstractionLayer.Registering.registerItem(itemLowEnrichedFuelCell = (ItemDescriptable) new ItemDescriptable(NuclearReferences.PREFIX, "fuelCell", "Low-Enriched Uranium").setUpdate(new ItemUpdateUranium().setScale(1.25f)).setMaxDamage(26000).setTextureName(NuclearReferences.PREFIX + "breederfuelcell").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab).setMaxStackSize(1), itemLowEnrichedFuelCell.getUnlocalizedName() + "Breeder");
			AbstractionLayer.Registering.registerItem(itemEmptyElectromagneticCell = (ItemDescriptable) new ItemDescriptable(NuclearReferences.PREFIX, "emptyElectromagneticCell").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab).setMaxStackSize(64), itemEmptyElectromagneticCell.getUnlocalizedName());
			AbstractionLayer.Registering.registerItem(itemEmptyQuantumCell = (ItemDescriptable) new ItemDescriptable(NuclearReferences.PREFIX, "emptyQuantumCell").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab).setMaxStackSize(64), itemEmptyQuantumCell.getUnlocalizedName());

			AbstractionLayer.Registering.registerItem(itemHeavyWaterCell = (ItemDescriptable) new ItemDescriptable(NuclearReferences.PREFIX, "heavyWaterCell", "125ml").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab), itemHeavyWaterCell.getUnlocalizedName());
			AbstractionLayer.Registering.registerItem(itemDeuteriumCell = (ItemDescriptable) new ItemDescriptable(NuclearReferences.PREFIX, "deuteriumCell", "25ml").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab), itemDeuteriumCell.getUnlocalizedName());
			AbstractionLayer.Registering.registerItem(itemTritiumCell = (ItemDescriptable) new ItemDescriptable(NuclearReferences.PREFIX, "tritiumCell", "12.5ml").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab), itemTritiumCell.getUnlocalizedName());
			AbstractionLayer.Registering.registerItem(itemUranium235 = (ItemDescriptable) new ItemDescriptable(NuclearReferences.PREFIX, "uranium", "Isotope 235").setUpdate(new ItemUpdateUranium().setScale(8.1f)).setTextureName(NuclearReferences.PREFIX + "uranium235").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab), itemUranium235.getUnlocalizedName() + "235");
			AbstractionLayer.Registering.registerItem(itemUranium238 = (ItemDescriptable) new ItemDescriptable(NuclearReferences.PREFIX, "uranium", "Isotope 238").setUpdate(new ItemUpdateUranium().setScale(2.25f)).setTextureName(NuclearReferences.PREFIX + "uranium238").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab), itemUranium238.getUnlocalizedName() + "238");
			AbstractionLayer.Registering.registerItem(itemYellowcake = (ItemDescriptable) new ItemDescriptable(NuclearReferences.PREFIX, "yellowcake").setCreativeTab(NuclearTabRegister.nuclearPhysicsTab), itemYellowcake.getUnlocalizedName());

			if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
				AbstractionLayer.Registering.registerItem(itemHazmatHelmet = new ItemHazmatArmor("HazmatHelmet", RenderingRegistry.addNewArmourRendererPrefix("HazmatHelmet"), 0), itemHazmatHelmet.getUnlocalizedName());
				AbstractionLayer.Registering.registerItem(itemHazmatPlate = new ItemHazmatArmor("HazmatPlate", RenderingRegistry.addNewArmourRendererPrefix("HazmatPlate"), 1), itemHazmatPlate.getUnlocalizedName());
				AbstractionLayer.Registering.registerItem(itemHazmatLegs = new ItemHazmatArmor("HazmatLegs", RenderingRegistry.addNewArmourRendererPrefix("HazmatLegs"), 2), itemHazmatLegs.getUnlocalizedName());
				AbstractionLayer.Registering.registerItem(itemHazmatBoots = new ItemHazmatArmor("HazmatBoots", RenderingRegistry.addNewArmourRendererPrefix("HazmatBoots"), 3), itemHazmatBoots.getUnlocalizedName());

				AbstractionLayer.Registering.registerItem(itemReinforcedHazmatHelmet = new ItemHazmatArmor("ReinforcedHazmatHelmet", RenderingRegistry.addNewArmourRendererPrefix("ReinforcedHazmatHelmet"), 0), itemReinforcedHazmatHelmet.getUnlocalizedName());
				AbstractionLayer.Registering.registerItem(itemReinforcedHazmatPlate = new ItemHazmatArmor("ReinforcedHazmatPlate", RenderingRegistry.addNewArmourRendererPrefix("ReinforcedHazmatPlate"), 1), itemReinforcedHazmatPlate.getUnlocalizedName());
				AbstractionLayer.Registering.registerItem(itemReinforcedHazmatLegs = new ItemHazmatArmor("ReinforcedHazmatLegs", RenderingRegistry.addNewArmourRendererPrefix("ReinforcedHazmatLegs"), 2), itemReinforcedHazmatLegs.getUnlocalizedName());
				AbstractionLayer.Registering.registerItem(itemReinforcedHazmatBoots = new ItemHazmatArmor("ReinforcedHazmatBoots", RenderingRegistry.addNewArmourRendererPrefix("ReinforcedHazmatBoots"), 3), itemReinforcedHazmatBoots.getUnlocalizedName());
			} else {
				AbstractionLayer.Registering.registerItem(itemHazmatHelmet = new ItemHazmatArmor("HazmatHelmet", 0, 0), itemHazmatHelmet.getUnlocalizedName());
				AbstractionLayer.Registering.registerItem(itemHazmatPlate = new ItemHazmatArmor("HazmatPlate", 0, 1), itemHazmatPlate.getUnlocalizedName());
				AbstractionLayer.Registering.registerItem(itemHazmatLegs = new ItemHazmatArmor("HazmatLegs", 0, 2), itemHazmatLegs.getUnlocalizedName());
				AbstractionLayer.Registering.registerItem(itemHazmatBoots = new ItemHazmatArmor("HazmatBoots", 0, 3), itemHazmatBoots.getUnlocalizedName());

				AbstractionLayer.Registering.registerItem(itemReinforcedHazmatHelmet = new ItemHazmatArmor("ReinforcedHazmatHelmet", 0, 0), itemReinforcedHazmatHelmet.getUnlocalizedName());
				AbstractionLayer.Registering.registerItem(itemReinforcedHazmatPlate = new ItemHazmatArmor("ReinforcedHazmatPlate", 0, 1), itemReinforcedHazmatPlate.getUnlocalizedName());
				AbstractionLayer.Registering.registerItem(itemReinforcedHazmatLegs = new ItemHazmatArmor("ReinforcedHazmatLegs", 0, 2), itemReinforcedHazmatLegs.getUnlocalizedName());
				AbstractionLayer.Registering.registerItem(itemReinforcedHazmatBoots = new ItemHazmatArmor("ReinforcedHazmatBoots", 0, 3), itemReinforcedHazmatBoots.getUnlocalizedName());

			}

			AbstractionLayer.Registering.registerItem(itemGeigerCounter = new ItemGeigerCounter("geigerCounter"), itemGeigerCounter.getUnlocalizedName());

			itemAntimatterCell125Milligram.addOreDictionaryInput("antimatter125Milligram", 0);
			itemAntimatterCell1Gram.addOreDictionaryInput("antimatterGram", 0);
			itemDarkmatterCell.addOreDictionaryInput("darkMatter", 0).addOreDictionaryInput("strangeMatter", 0);
			itemUranium235.addOreDictionaryInput("uranium235", 0);
			itemUranium238.addOreDictionaryInput("uranium238", 0);
			itemLowEnrichedFuelCell.addOreDictionaryInput("ingotUranium238", 0);
			itemHighEnrichedFuelCell.addOreDictionaryInput("ingotUranium235", 0);
		}
	}
}
