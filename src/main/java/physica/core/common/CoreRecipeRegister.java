package physica.core.common;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import physica.api.core.IBaseUtilities;
import physica.api.core.IContent;

public class CoreRecipeRegister implements IContent, IBaseUtilities {

	@Override
	public void loadComplete() {
		//Circuits
		addRecipe(new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 0), "CIC", "RPR", "GIG", 'C', new ItemStack(Items.dye, 1, 2), 'P', "plateIron", 'R', Items.redstone, 'I', Items.iron_ingot,
				'G',
				Items.gold_ingot);
		addRecipe(new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 1), "RGR", "PCP", "RGR", 'C', "circuitBasic", 'P', "plateSteel", 'R', Items.redstone, 'G',
				Items.gold_ingot);
		addRecipe(new ItemStack(CoreItemRegister.itemMetaCircuit, 1, 2), "LDL", "PCP", "RDR", 'C', "circuitAdvanced", 'L', new ItemStack(Items.dye, 1, 4), 'P', "plateSteel", 'R', Items.redstone,
				'D',
				Items.diamond);

		// Motor
		addRecipe(CoreItemRegister.itemMotor, "WSW", "SIS", "WSW", 'W', Items.redstone, 'S', "ingotSteel", 'I', "ingotCopper");

		// Empty Container
		addRecipe(new ItemStack(CoreItemRegister.itemEmptyContainer, 3), " T ", "TGT", " T ", 'G', Blocks.glass, 'T', "ingotTin");

		// Plates
		addShapeless(new ItemStack(CoreItemRegister.itemMetaPlate, 1, 0), Items.iron_ingot, Items.iron_ingot, Items.iron_ingot, Items.iron_ingot);
		addShapeless(new ItemStack(CoreItemRegister.itemMetaPlate, 1, 1), new ItemStack(CoreItemRegister.itemMetaIngot, 1, 2), new ItemStack(CoreItemRegister.itemMetaIngot, 1, 2),
				new ItemStack(CoreItemRegister.itemMetaIngot, 1, 2), new ItemStack(CoreItemRegister.itemMetaIngot, 1, 2));
		// Wrench
		addRecipe(CoreItemRegister.itemWrench, "SS ", "SS ", "  S", 'S', "ingotSteel");

		// Battery
		addRecipe(CoreItemRegister.itemBattery, " T ", "TRT", "TCT", 'T', "ingotTin", 'R', Items.redstone, 'C', Items.coal);

		// Smeltings
		GameRegistry.addSmelting(CoreBlockRegister.blockTinOre, new ItemStack(CoreItemRegister.itemMetaIngot, 1, 0), 0.7f);
		GameRegistry.addSmelting(CoreBlockRegister.blockCopperOre, new ItemStack(CoreItemRegister.itemMetaIngot, 1, 1), 0.7f);
	}
}
