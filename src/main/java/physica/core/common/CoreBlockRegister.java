package physica.core.common;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import physica.CoreReferences;
import physica.api.core.IContent;
import physica.core.common.block.BlockBlastFurnace;
import physica.core.common.block.BlockCopperCable;
import physica.core.common.block.BlockFulmination;
import physica.core.common.block.BlockInfiniteEnergy;
import physica.core.common.block.BlockOre;
import physica.core.common.tile.TileBlastFurnace;
import physica.core.common.tile.TileCopperCable;
import physica.core.common.tile.TileFulmination;
import physica.core.common.tile.TileInfiniteEnergy;

public class CoreBlockRegister implements IContent {

	public static BlockInfiniteEnergy blockInfEnergy;
	public static BlockFulmination blockFulmination;
	public static BlockBlastFurnace blockBlastFurnace;
	public static BlockOre blockTinOre;
	public static BlockOre blockCopperOre;
	public static BlockCopperCable blockCable;

	@Override
	public void preInit()
	{
		GameRegistry.registerBlock(blockInfEnergy = new BlockInfiniteEnergy(), "infEnergy");
		GameRegistry.registerTileEntity(TileInfiniteEnergy.class, CoreReferences.PREFIX + "infEnergy");
		GameRegistry.registerBlock(blockFulmination = new BlockFulmination(), "fulmination");
		GameRegistry.registerTileEntity(TileFulmination.class, CoreReferences.PREFIX + "fulmination");
		GameRegistry.registerBlock(blockBlastFurnace = new BlockBlastFurnace(), "blastFurnace");
		GameRegistry.registerTileEntity(TileBlastFurnace.class, CoreReferences.PREFIX + "blastFurnace");
		GameRegistry.registerBlock(blockCable = new BlockCopperCable(), "energyCable");
		GameRegistry.registerTileEntity(TileCopperCable.class, CoreReferences.PREFIX + "energyCable");
		GameRegistry.registerBlock(blockTinOre = new BlockOre("tinOre"), "tinOre");
		GameRegistry.registerBlock(blockCopperOre = new BlockOre("copperOre"), "copperOre");

		OreDictionary.registerOre("oreTin", CoreBlockRegister.blockTinOre);
		OreDictionary.registerOre("oreCopper", CoreBlockRegister.blockCopperOre);
	}
}
