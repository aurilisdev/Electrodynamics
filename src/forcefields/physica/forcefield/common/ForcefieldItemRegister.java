package physica.forcefield.common;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import physica.api.core.abstraction.AbstractionLayer;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.forcefield.common.item.ItemFrequency;
import physica.forcefield.common.item.ItemIdentificationCard;
import physica.library.item.ItemDescriptable;
import physica.library.item.ItemMetaHolder;

public class ForcefieldItemRegister implements IContent {

	public static ItemMetaHolder				itemMetaShapeModule;
	public static ItemMetaHolder				itemMetaManipulationModule;
	public static ItemMetaHolder				itemMetaUpgradeModule;

	public static ItemDescriptable				itemFocusMatrix;

	public static ItemFrequency					itemFrequency;
	public static ItemIdentificationCard		itemIdentifcationCard;

	public static HashMap<String, ItemStack>	moduleMap	= new HashMap<>();

	@Override
	public void register(LoadPhase phase)
	{
		if (phase == LoadPhase.RegisterObjects)
		{
			AbstractionLayer.Registering.registerItem(itemMetaShapeModule = (ItemMetaHolder) new ItemMetaHolder("moduleShapeSphere").addSubItem("moduleShapeHemisphere").addSubItem("moduleShapeCube").addSubItem("moduleShapePyramid")
					.setCreativeTab(ForcefieldTabRegister.forcefieldTab).setMaxStackSize(1), "item.metaShapeModule");
			AbstractionLayer.Registering.registerItem(itemMetaUpgradeModule = (ItemMetaHolder) new ItemMetaHolder("moduleUpgradeSpeed").addSubItem("moduleUpgradeCapacity").addSubItem("moduleUpgradeShock")
					.addSubItem("moduleUpgradeDisintegration").addSubItem("moduleUpgradeInterior").addSubItem("moduleUpgradeSponge").addSubItem("moduleUpgradeStabilize").addSubItem("moduleUpgradeColorChange")
					.addSubItem("moduleUpgradeAntiHostile").addSubItem("moduleUpgradeAntiFriendly").addSubItem("moduleUpgradeAntiPersonnel").addSubItem("moduleUpgradeAntiSpawn").addSubItem("moduleUpgradeBlockAccess")
					.addSubItem("moduleUpgradeBlockAlter").addSubItem("moduleUpgradeConfiscate").addSubItem("moduleUpgradeCollection").setCreativeTab(ForcefieldTabRegister.forcefieldTab), "item.metaUpgradeModule");

			AbstractionLayer.Registering.registerItem(itemMetaManipulationModule = (ItemMetaHolder) new ItemMetaHolder("moduleManipulationScale").addSubItem("moduleManipulationTranslate").setCreativeTab(ForcefieldTabRegister.forcefieldTab),
					"item.metaManipulationModule");

			AbstractionLayer.Registering.registerItem(itemFocusMatrix = (ItemDescriptable) new ItemDescriptable("focusMatrix").setCreativeTab(ForcefieldTabRegister.forcefieldTab), itemFocusMatrix.getUnlocalizedName());

			AbstractionLayer.Registering.registerItem(itemFrequency = new ItemFrequency("frequencyCard"), itemFrequency.getUnlocalizedName());
			AbstractionLayer.Registering.registerItem(itemIdentifcationCard = new ItemIdentificationCard("identificationCard"), itemIdentifcationCard.getUnlocalizedName());

			itemMetaShapeModule.setTextureFolder("modules");
			itemMetaManipulationModule.setTextureFolder("modules");
			itemMetaUpgradeModule.setTextureFolder("modules");

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
}
