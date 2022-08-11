package physica.forcefield.common;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.api.core.utilities.IBaseUtilities;
import physica.core.common.CoreBlockRegister;

public class ForcefieldRecipeRegister implements IContent, IBaseUtilities {

	@Override
	public void register(LoadPhase phase) {
		if (phase == LoadPhase.OnStartup) {
			// Focus Matrix
			addRecipe(new ItemStack(ForcefieldItemRegister.itemFocusMatrix, 4), "RMR", "MDM", "RMR", 'M', "plateSteel", 'D', Items.diamond, 'R', Items.redstone);

			// Different modes
			addRecipe(ForcefieldItemRegister.moduleMap.get("moduleShapeSphere"), " F ", "FFF", " F ", 'F', ForcefieldItemRegister.itemFocusMatrix);
			addRecipe(ForcefieldItemRegister.moduleMap.get("moduleShapeHemisphere"), " F ", "FFF", 'F', ForcefieldItemRegister.itemFocusMatrix);
			addRecipe(ForcefieldItemRegister.moduleMap.get("moduleShapeCube"), "FFF", "FFF", "FFF", 'F', ForcefieldItemRegister.itemFocusMatrix);
			addRecipe(ForcefieldItemRegister.moduleMap.get("moduleShapePyramid"), "F  ", "FF ", "FFF", 'F', ForcefieldItemRegister.itemFocusMatrix);

			// Modules Upgrade
			addRecipe(new ItemStack(ForcefieldItemRegister.moduleMap.get("moduleUpgradeSpeed").getItem(), 3, ForcefieldItemRegister.moduleMap.get("moduleUpgradeSpeed").getItemDamage()), "FFF", "RRR", "FFF", 'F', ForcefieldItemRegister.itemFocusMatrix, 'R', Items.redstone);
			addRecipe(new ItemStack(ForcefieldItemRegister.moduleMap.get("moduleUpgradeCapacity").getItem(), 2, ForcefieldItemRegister.moduleMap.get("moduleUpgradeCapacity").getItemDamage()), "FCF", 'F', ForcefieldItemRegister.itemFocusMatrix, 'C', "phyBattery");
			addRecipe(ForcefieldItemRegister.moduleMap.get("moduleUpgradeShock"), "FWF", 'F', ForcefieldItemRegister.itemFocusMatrix, 'W', Item.getItemFromBlock(CoreBlockRegister.blockCable));
			addRecipe(ForcefieldItemRegister.moduleMap.get("moduleUpgradeDisintegration"), " W ", "FBF", " W ", 'W', Item.getItemFromBlock(CoreBlockRegister.blockCable), 'B', "phyBattery", 'F', ForcefieldItemRegister.itemFocusMatrix);
			addRecipe(ForcefieldItemRegister.moduleMap.get("moduleUpgradeInterior"), "L", "F", "L", 'L', Blocks.lapis_block, 'F', ForcefieldItemRegister.itemFocusMatrix);
			addRecipe(ForcefieldItemRegister.moduleMap.get("moduleUpgradeSponge"), "BSB", "SFS", "BSB", 'B', Items.water_bucket, 'S', Item.getItemFromBlock(Blocks.sponge), 'F', ForcefieldItemRegister.itemFocusMatrix);
			addRecipe(ForcefieldItemRegister.moduleMap.get("moduleUpgradeStabilize"), "EFE", "FDF", "EFE", 'E', Items.ender_eye, 'D', Items.diamond, 'F', ForcefieldItemRegister.itemFocusMatrix);
			addRecipe(ForcefieldItemRegister.moduleMap.get("moduleUpgradeColorChange"), "FLF", "BWR", "FGF", 'F', ForcefieldItemRegister.itemFocusMatrix, 'L', new ItemStack(Items.dye, 1, 4), 'B', new ItemStack(Items.dye, 1, 12), 'R', new ItemStack(Items.dye, 1, 1), 'G', new ItemStack(Items.dye, 1, 2), 'W', Item.getItemFromBlock(Blocks.wool));

			addRecipe(ForcefieldItemRegister.moduleMap.get("moduleUpgradeAntiHostile"), " F ", "GFB", " G ", 'F', Items.rotten_flesh, 'G', Items.gunpowder, 'F', ForcefieldItemRegister.itemFocusMatrix, 'B', Items.bone, 'G', Items.ghast_tear);
			addRecipe(ForcefieldItemRegister.moduleMap.get("moduleUpgradeAntiFriendly"), " H ", "LFC", " S ", 'H', Item.getItemFromBlock(Blocks.hay_block), 'L', Items.leather, 'F', ForcefieldItemRegister.itemFocusMatrix, 'C', Items.cooked_porkchop, 'S', Items.slime_ball);
			addShapeless(ForcefieldItemRegister.moduleMap.get("moduleUpgradeAntiPersonnel"), ForcefieldItemRegister.moduleMap.get("moduleUpgradeAntiHostile"), ForcefieldItemRegister.itemFocusMatrix, ForcefieldItemRegister.moduleMap.get("moduleUpgradeAntiFriendly"));
			addRecipe(ForcefieldItemRegister.moduleMap.get("moduleUpgradeAntiSpawn"), " H ", "F F", " H ", 'H', ForcefieldItemRegister.moduleMap.get("moduleUpgradeAntiHostile"), 'F', ForcefieldItemRegister.moduleMap.get("moduleUpgradeAntiFriendly"));
			addRecipe(ForcefieldItemRegister.moduleMap.get("moduleUpgradeBlockAccess"), " C ", "IFI", " C ", 'C', Item.getItemFromBlock(Blocks.chest), 'I', Item.getItemFromBlock(Blocks.iron_block), 'F', ForcefieldItemRegister.itemFocusMatrix);
			addRecipe(ForcefieldItemRegister.moduleMap.get("moduleUpgradeBlockAlter"), " G ", "GAG", " G ", 'G', Item.getItemFromBlock(Blocks.gold_block), 'A', ForcefieldItemRegister.moduleMap.get("moduleUpgradeBlockAccess"));
			addRecipe(ForcefieldItemRegister.moduleMap.get("moduleUpgradeConfiscate"), "EPE", "PFP", "EPE", 'E', Items.ender_pearl, 'P', Items.ender_eye, 'F', ForcefieldItemRegister.itemFocusMatrix);
			addRecipe(ForcefieldItemRegister.moduleMap.get("moduleUpgradeCollection"), "F F", "PEP", "F F", 'E', Item.getItemFromBlock(Blocks.chest), 'P', Items.iron_pickaxe, 'F', ForcefieldItemRegister.itemFocusMatrix);

			// Modules Manipulation
			addRecipe(new ItemStack(ForcefieldItemRegister.moduleMap.get("moduleManipulationScale").getItem(), 2), "FRF", 'F', ForcefieldItemRegister.itemFocusMatrix, 'R', Items.redstone);
			addRecipe(new ItemStack(ForcefieldItemRegister.moduleMap.get("moduleManipulationTranslate").getItem(), 2, ForcefieldItemRegister.moduleMap.get("moduleManipulationTranslate").getItemDamage()), "FSF", 'F', ForcefieldItemRegister.itemFocusMatrix, 'S', ForcefieldItemRegister.moduleMap.get("moduleManipulationScale").getItem());

			// Frequency Card
			addRecipe(ForcefieldItemRegister.itemFrequency, "PPP", "PCP", "PPP", 'P', Items.paper, 'C', "circuitBasic");
			// Identification Card
			addRecipe(ForcefieldItemRegister.itemIdentifcationCard, "PCP", "PIP", "PCP", 'P', Items.paper, 'C', "circuitBasic", 'I', ForcefieldItemRegister.itemFrequency);
		}
	}
}
