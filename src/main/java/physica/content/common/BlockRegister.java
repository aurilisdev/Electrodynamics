package physica.content.common;

import cpw.mods.fml.common.registry.GameRegistry;
import physica.References;
import physica.api.base.IProxyContent;
import physica.api.lib.item.ItemBlockMetadata;
import physica.content.common.block.BlockAccelerator;
import physica.content.common.block.BlockAssembler;
import physica.content.common.block.BlockCentrifuge;
import physica.content.common.block.BlockChemicalBoiler;
import physica.content.common.block.BlockElectromagnet;
import physica.content.common.block.BlockUraniumOre;
import physica.content.common.tile.TileAccelerator;
import physica.content.common.tile.TileAssembler;
import physica.content.common.tile.TileCentrifuge;
import physica.content.common.tile.TileChemicalBoiler;
import physica.content.common.tile.TileElectromagnet;

public class BlockRegister implements IProxyContent {
	public static final BlockAccelerator	blockAccelerator	= new BlockAccelerator();
	public static final BlockAssembler		blockAssembler		= new BlockAssembler();
	public static final BlockCentrifuge		blockCentrifuge		= new BlockCentrifuge();
	public static final BlockChemicalBoiler	blockChemicalBoiler	= new BlockChemicalBoiler();

	public static final BlockElectromagnet	blockElectromagnet	= new BlockElectromagnet();

	public static final BlockUraniumOre		blockUraniumOre		= new BlockUraniumOre();

	@Override
	public void preInit() {
		GameRegistry.registerBlock(blockAccelerator, "accelerator");
		GameRegistry.registerTileEntity(TileAccelerator.class, References.PREFIX + "accelerator");
		GameRegistry.registerBlock(blockAssembler, "assembler");
		GameRegistry.registerTileEntity(TileAssembler.class, References.PREFIX + "assembler");
		GameRegistry.registerBlock(blockCentrifuge, "centrifuge");
		GameRegistry.registerTileEntity(TileCentrifuge.class, References.PREFIX + "centrifuge");
		GameRegistry.registerBlock(blockChemicalBoiler, "chemicalBoiler");
		GameRegistry.registerTileEntity(TileChemicalBoiler.class, References.PREFIX + "chemicalBoiler");

		GameRegistry.registerBlock(blockElectromagnet, ItemBlockMetadata.class, "electromagnet");
		GameRegistry.registerTileEntity(TileElectromagnet.class, References.PREFIX + "electromagnet");

		GameRegistry.registerBlock(blockUraniumOre, "uraniumOre");
	}
}
