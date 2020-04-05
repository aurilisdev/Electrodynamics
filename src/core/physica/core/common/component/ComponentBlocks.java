package physica.core.common.component;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import physica.core.References;
import physica.core.common.block.BlockMachine;
import physica.core.common.block.BlockOre;
import physica.core.common.tile.TileCoalGenerator;
import physica.core.common.tile.TileElectricFurnace;
import physica.core.common.tile.TileMineralCrusher;
import physica.core.common.tile.TileMineralGrinder;

public class ComponentBlocks {
	public static BlockOre blockOre = (BlockOre) new BlockOre("blockOre").setCreativeTab(ComponentsTab.BASICCOMPONENTS);
	public static BlockMachine blockMachine = (BlockMachine) new BlockMachine("blockMachine")
			.setCreativeTab(ComponentsTab.BASICCOMPONENTS);

	public static void register(IForgeRegistry<Block> registry) {
		registry.registerAll(blockOre, blockMachine);
		registerTile(TileElectricFurnace.class);
		registerTile(TileCoalGenerator.class);
		registerTile(TileMineralCrusher.class);
		registerTile(TileMineralGrinder.class);
	}

	@SuppressWarnings("deprecation")
	private static void registerTile(Class<? extends TileEntity> tileClass) {
		String name = tileClass.getSimpleName().replaceAll("(.)(\\p{Lu})", "$1_$2").toLowerCase();
		GameRegistry.registerTileEntity(tileClass, References.DOMAIN + ":" + name);
	}

	public static void registerItemBlocks(IForgeRegistry<Item> registry) {
		registry.registerAll(blockOre.createItemBlock());
		registry.registerAll(blockMachine.createItemBlock());
	}

	public static void registerModels() {
		blockOre.registerItemModel(Item.getItemFromBlock(blockOre));
		blockMachine.registerItemModel(Item.getItemFromBlock(blockMachine));
	}

}
