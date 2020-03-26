package physica.component;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ComponentRecipes {
	public static void init()
	{
		GameRegistry.addSmelting(Items.IRON_INGOT, new ItemStack(ComponentItems.ingotBase, 1, 3), 0.3f);

		GameRegistry.addSmelting(new ItemStack(ComponentBlocks.blockOre, 1, 0), new ItemStack(ComponentItems.ingotBase, 1, 0), 0.7f);
		GameRegistry.addSmelting(new ItemStack(ComponentBlocks.blockOre, 1, 1), new ItemStack(ComponentItems.ingotBase, 1, 1), 0.7f);
		GameRegistry.addSmelting(new ItemStack(ComponentBlocks.blockOre, 1, 2), new ItemStack(ComponentItems.ingotBase, 1, 2), 0.7f);
		GameRegistry.addSmelting(new ItemStack(ComponentBlocks.blockOre, 1, 3), new ItemStack(ComponentItems.ingotBase, 1, 4), 0.7f);

		GameRegistry.addSmelting(new ItemStack(ComponentItems.blendBase, 1, 0), new ItemStack(Items.IRON_INGOT), 0.3f);
		GameRegistry.addSmelting(new ItemStack(ComponentItems.blendBase, 1, 1), new ItemStack(Items.GOLD_INGOT), 0.3f); // Mod
																														// ingots
																														// start
																														// after
																														// gold
																														// dust
		GameRegistry.addSmelting(new ItemStack(ComponentItems.blendBase, 1, 2), new ItemStack(ComponentItems.ingotBase, 1, 0), 0.45f);
		GameRegistry.addSmelting(new ItemStack(ComponentItems.blendBase, 1, 3), new ItemStack(ComponentItems.ingotBase, 1, 1), 0.45f);
		GameRegistry.addSmelting(new ItemStack(ComponentItems.blendBase, 1, 4), new ItemStack(ComponentItems.ingotBase, 1, 2), 0.45f);
		GameRegistry.addSmelting(new ItemStack(ComponentItems.blendBase, 1, 5), new ItemStack(ComponentItems.ingotBase, 1, 3), 0.45f);
		GameRegistry.addSmelting(new ItemStack(ComponentItems.blendBase, 1, 6), new ItemStack(ComponentItems.ingotBase, 1, 4), 0.45f);
		GameRegistry.addSmelting(new ItemStack(ComponentItems.blendBase, 1, 7), new ItemStack(ComponentItems.ingotBase, 1, 6), 0.45f); // These last two are switched
		GameRegistry.addSmelting(new ItemStack(ComponentItems.blendBase, 1, 8), new ItemStack(ComponentItems.ingotBase, 1, 5), 0.45f);
	}

}
