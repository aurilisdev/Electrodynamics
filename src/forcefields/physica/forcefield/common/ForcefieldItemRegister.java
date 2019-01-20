package physica.forcefield.common;

import java.util.HashMap;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import physica.api.core.IContent;
import physica.forcefield.common.item.ItemFrequency;
import physica.forcefield.common.item.ItemIdentifcationCard;
import physica.library.item.ItemInformationHolder;
import physica.library.item.ItemMetaHolder;

public class ForcefieldItemRegister implements IContent {

	public static ItemMetaHolder itemMetaShapeModule;
	public static ItemMetaHolder itemMetaManipulationModule;
	public static ItemMetaHolder itemMetaUpgradeModule;

	public static ItemInformationHolder itemFocusMatrix;

	public static ItemFrequency itemFrequency;
	public static ItemIdentifcationCard itemIdentifcationCard;

	public static HashMap<String, ItemStack> moduleMap = new HashMap<>();

	@Override
	public void preInit() {
		GameRegistry.registerItem(
				itemMetaShapeModule =
						(ItemMetaHolder) new ItemMetaHolder("moduleShapeSphere").addSubItem("moduleShapeHemisphere").addSubItem("moduleShapeCube").addSubItem("moduleShapePyramid")
								.setCreativeTab(ForcefieldTabRegister.forcefieldTab).setMaxStackSize(1),
				"item.metaShapeModule");
		GameRegistry.registerItem(
				itemMetaUpgradeModule =
						(ItemMetaHolder) new ItemMetaHolder("moduleUpgradeSpeed").addSubItem("moduleUpgradeCapacity").addSubItem("moduleUpgradeShock").addSubItem("moduleUpgradeDisintegration")
								.addSubItem("moduleUpgradeInterior").addSubItem("moduleUpgradeSponge").addSubItem("moduleUpgradeStabilize").addSubItem("moduleUpgradeColorChange")
								.addSubItem("moduleUpgradeAntiHostile").addSubItem("moduleUpgradeAntiFriendly")
								.addSubItem("moduleUpgradeAntiPersonnel").addSubItem("moduleUpgradeAntiSpawn").addSubItem("moduleUpgradeBlockAccess").addSubItem("moduleUpgradeBlockAlter")
								.addSubItem("moduleUpgradeConfiscate").addSubItem("moduleUpgradeCollection").setCreativeTab(ForcefieldTabRegister.forcefieldTab),
				"item.metaUpgradeModule");

		GameRegistry.registerItem(
				itemMetaManipulationModule =
						(ItemMetaHolder) new ItemMetaHolder("moduleManipulationScale").addSubItem("moduleManipulationTranslate").setCreativeTab(ForcefieldTabRegister.forcefieldTab),
				"item.metaManipulationModule");

		GameRegistry.registerItem(itemFocusMatrix = (ItemInformationHolder) new ItemInformationHolder("focusMatrix").setCreativeTab(ForcefieldTabRegister.forcefieldTab),
				itemFocusMatrix.getUnlocalizedName());

		GameRegistry.registerItem(itemFrequency = new ItemFrequency("frequencyCard"), itemFrequency.getUnlocalizedName());
		GameRegistry.registerItem(itemIdentifcationCard = new ItemIdentifcationCard("identificationCard"), itemIdentifcationCard.getUnlocalizedName());

		moduleMap.put("moduleShapeSphere", new ItemStack(itemMetaShapeModule, 1, 0));
		moduleMap.put("moduleShapeHemisphere", new ItemStack(itemMetaShapeModule, 1, 1));
		moduleMap.put("moduleShapeCube", new ItemStack(itemMetaShapeModule, 1, 2));
		moduleMap.put("moduleShapePyramid", new ItemStack(itemMetaShapeModule, 1, 3));
		moduleMap.put("moduleUpgradeSpeed", new ItemStack(itemMetaUpgradeModule, 1, 0));
		moduleMap.put("moduleUpgradeCapacity", new ItemStack(itemMetaUpgradeModule, 1, 1));
		moduleMap.put("moduleUpgradeShock", new ItemStack(itemMetaUpgradeModule, 1, 2));
		moduleMap.put("moduleUpgradeDisintegration", new ItemStack(itemMetaUpgradeModule, 1, 3));
		moduleMap.put("moduleUpgradeInterior", new ItemStack(itemMetaUpgradeModule, 1, 4));
		moduleMap.put("moduleUpgradeSponge", new ItemStack(itemMetaUpgradeModule, 1, 5));
		moduleMap.put("moduleUpgradeStabilize", new ItemStack(itemMetaUpgradeModule, 1, 6));
		moduleMap.put("moduleUpgradeColorChange", new ItemStack(itemMetaUpgradeModule, 1, 7));
		moduleMap.put("moduleUpgradeAntiHostile", new ItemStack(itemMetaUpgradeModule, 1, 8));
		moduleMap.put("moduleUpgradeAntiFriendly", new ItemStack(itemMetaUpgradeModule, 1, 9));
		moduleMap.put("moduleUpgradeAntiPersonnel", new ItemStack(itemMetaUpgradeModule, 1, 10));
		moduleMap.put("moduleUpgradeAntiSpawn", new ItemStack(itemMetaUpgradeModule, 1, 11));
		moduleMap.put("moduleUpgradeBlockAccess", new ItemStack(itemMetaUpgradeModule, 1, 12));
		moduleMap.put("moduleUpgradeBlockAlter", new ItemStack(itemMetaUpgradeModule, 1, 13));
		moduleMap.put("moduleUpgradeConfiscate", new ItemStack(itemMetaUpgradeModule, 1, 14));
		moduleMap.put("moduleUpgradeCollection", new ItemStack(itemMetaUpgradeModule, 1, 15));

		moduleMap.put("moduleManipulationScale", new ItemStack(itemMetaManipulationModule, 1, 0));
		moduleMap.put("moduleManipulationTranslate", new ItemStack(itemMetaManipulationModule, 1, 1));

	}
}
