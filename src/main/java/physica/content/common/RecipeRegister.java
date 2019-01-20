package physica.content.common;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import physica.api.OreDictionaryUtilities;
import physica.api.base.IProxyContent;
import physica.content.common.block.BlockElectromagnet.EnumElectromagnet;

public class RecipeRegister implements IProxyContent {
	@Override
	public void init() {
		initItems();
		initBlocks();
	}

	protected void initItems() {
		// Motor
		GameRegistry.addRecipe(
				new ShapedOreRecipe(ItemRegister.itemMotor, "WSW", "SIS", "WSW", 'W', Items.redstone, 'S', OreDictionaryUtilities.getOreItem("ingotSteel", Items.iron_ingot), 'I', Items.gold_ingot));

		// Empty Container
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ItemRegister.itemEmptyContainer), " T ", "TGT", " T ", 'G', Blocks.glass, 'T', OreDictionaryUtilities.getOreItem("ingotTin", Items.iron_ingot)));

		// 1 Gram Antimatter Container
		GameRegistry.addRecipe(
				new ShapelessOreRecipe(new ItemStack(ItemRegister.itemAntimatterContainer1Gram), ItemRegister.itemAntimatterContainer125Milligram, ItemRegister.itemAntimatterContainer125Milligram,
						ItemRegister.itemAntimatterContainer125Milligram, ItemRegister.itemAntimatterContainer125Milligram, ItemRegister.itemAntimatterContainer125Milligram,
						ItemRegister.itemAntimatterContainer125Milligram, ItemRegister.itemAntimatterContainer125Milligram, ItemRegister.itemAntimatterContainer125Milligram));

		// Fissile Fuel Container
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegister.itemFissileFuelContainer), "CUC", "CUC", "CUC", 'U', ItemRegister.itemUranium235, 'C', ItemRegister.itemEmptyContainer));

		// Breeder Fuel Container
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegister.itemBreederFuelContainer), "CUC", "CUC", "CUC", 'U', ItemRegister.itemUranium238, 'C', ItemRegister.itemEmptyContainer));
	}

	protected void initBlocks() {
		// Electromagnetic block and glass
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockRegister.blockElectromagnet, 2), "BBB", "BMB", "BBB", 'B', OreDictionaryUtilities.getOreItem("ingotBronze", Items.gold_ingot),
				'M', ItemRegister.itemMotor));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlockRegister.blockElectromagnet, 1, EnumElectromagnet.GLASS.ordinal()), BlockRegister.blockElectromagnet, Blocks.glass));

		// Particle Accelerator
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockRegister.blockAccelerator), "PCP", "CMC", "PCP", 'M', ItemRegister.itemMotor, 'C', Items.diamond, 'P',
				OreDictionaryUtilities.getOreItem("plateSteel", ItemRegister.itemPlateIron)));

		// TODO: Recipe for Centrifuge
		// TODO: Recipe for Chemical Boiler
		// TODO: Recipe for Assembler
	}

	protected void addRecipe(Item output, Object... params) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output, params));
	}

	protected void addRecipe(ItemStack output, Object... params) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output, params));
	}

}
