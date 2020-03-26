package physica.component;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import physica.block.BlockOre;

public class ComponentBlocks {
	public static BlockOre blockOre = (BlockOre) new BlockOre("blockOre").setCreativeTab(ComponentsTab.BASICCOMPONENTS);

	public static void register(IForgeRegistry<Block> registry) {
		registry.registerAll(blockOre);
	}

	public static void registerItemBlocks(IForgeRegistry<Item> registry) {
		registry.registerAll(blockOre.createItemBlock());
	}

	public static void registerModels() {
		blockOre.registerItemModel(Item.getItemFromBlock(blockOre));
	}

}
