package physica.nuclear.common;

import net.minecraft.item.ItemStack;
import physica.api.core.abstraction.AbstractionLayer;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.library.item.ItemBlockMetadata;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.block.BlockChemicalBoiler;
import physica.nuclear.common.block.BlockChemicalExtractor;
import physica.nuclear.common.block.BlockControlRod;
import physica.nuclear.common.block.BlockElectromagnet;
import physica.nuclear.common.block.BlockFissionReactor;
import physica.nuclear.common.block.BlockFusionReactor;
import physica.nuclear.common.block.BlockGasCentrifuge;
import physica.nuclear.common.block.BlockInsertableControlRod;
import physica.nuclear.common.block.BlockMeltedReactor;
import physica.nuclear.common.block.BlockNeutronCaptureChamber;
import physica.nuclear.common.block.BlockParticleAccelerator;
import physica.nuclear.common.block.BlockPlasma;
import physica.nuclear.common.block.BlockQuantumAssembler;
import physica.nuclear.common.block.BlockRadioactiveDirt;
import physica.nuclear.common.block.BlockRadioactiveGrass;
import physica.nuclear.common.block.BlockRadioactiveStone;
import physica.nuclear.common.block.BlockReactorControlPanel;
import physica.nuclear.common.block.BlockSiren;
import physica.nuclear.common.block.BlockThermometer;
import physica.nuclear.common.block.BlockTurbine;
import physica.nuclear.common.block.BlockUraniumOre;
import physica.nuclear.common.tile.TileChemicalBoiler;
import physica.nuclear.common.tile.TileChemicalExtractor;
import physica.nuclear.common.tile.TileFissionReactor;
import physica.nuclear.common.tile.TileFusionReactor;
import physica.nuclear.common.tile.TileGasCentrifuge;
import physica.nuclear.common.tile.TileInsertableControlRod;
import physica.nuclear.common.tile.TileMeltedReactor;
import physica.nuclear.common.tile.TileNeutronCaptureChamber;
import physica.nuclear.common.tile.TileParticleAccelerator;
import physica.nuclear.common.tile.TilePlasma;
import physica.nuclear.common.tile.TileQuantumAssembler;
import physica.nuclear.common.tile.TileReactorControlPanel;
import physica.nuclear.common.tile.TileSiren;
import physica.nuclear.common.tile.TileTurbine;

public class NuclearBlockRegister implements IContent {

	public static BlockParticleAccelerator		blockParticleAccelerator;
	public static BlockQuantumAssembler			blockQuantumAssembler;
	public static BlockGasCentrifuge			blockCentrifuge;
	public static BlockChemicalBoiler			blockChemicalBoiler;
	public static BlockChemicalExtractor		blockChemicalExtractor;
	public static BlockFissionReactor			blockFissionReactor;
	public static BlockNeutronCaptureChamber	blockNeutronCaptureChamber;
	public static BlockFusionReactor			blockFusionReactor;
	public static BlockTurbine					blockTurbine;
	public static BlockSiren					blockSiren;

	public static BlockElectromagnet			blockElectromagnet;
	public static BlockThermometer				blockThermometer;
	public static BlockControlRod				blockControlRod;
	public static BlockUraniumOre				blockUraniumOre;
	public static BlockPlasma					blockPlasma;
	public static BlockInsertableControlRod		blockInsertableControlRod;
	public static BlockReactorControlPanel		blockReactorControlPanel;

	public static BlockMeltedReactor			blockMeltedReactor;
	public static BlockRadioactiveGrass			blockRadioactiveGrass;
	public static BlockRadioactiveStone			blockRadioactiveStone;
	public static BlockRadioactiveDirt			blockRadioactiveDirt;

	@Override
	public void register(LoadPhase phase)
	{
		if (phase == LoadPhase.RegisterObjects)
		{
			AbstractionLayer.Registering.registerBlock(blockParticleAccelerator = new BlockParticleAccelerator(), "accelerator");
			AbstractionLayer.Registering.registerTileEntity(TileParticleAccelerator.class, NuclearReferences.PREFIX + "accelerator");
			AbstractionLayer.Registering.registerBlock(blockQuantumAssembler = new BlockQuantumAssembler(), "assembler");
			AbstractionLayer.Registering.registerTileEntity(TileQuantumAssembler.class, NuclearReferences.PREFIX + "assembler");
			AbstractionLayer.Registering.registerBlock(blockCentrifuge = new BlockGasCentrifuge(), "centrifuge");
			AbstractionLayer.Registering.registerTileEntity(TileGasCentrifuge.class, NuclearReferences.PREFIX + "centrifuge");
			AbstractionLayer.Registering.registerBlock(blockChemicalBoiler = new BlockChemicalBoiler(), "chemicalBoiler");
			AbstractionLayer.Registering.registerTileEntity(TileChemicalBoiler.class, NuclearReferences.PREFIX + "chemicalBoiler");
			AbstractionLayer.Registering.registerBlock(blockChemicalExtractor = new BlockChemicalExtractor(), "chemicalExtractor");
			AbstractionLayer.Registering.registerTileEntity(TileChemicalExtractor.class, NuclearReferences.PREFIX + "chemicalExtractor");
			AbstractionLayer.Registering.registerBlock(blockFissionReactor = new BlockFissionReactor(), "fissionReactor");
			AbstractionLayer.Registering.registerTileEntity(TileFissionReactor.class, NuclearReferences.PREFIX + "fissionReactor");
			AbstractionLayer.Registering.registerBlock(blockNeutronCaptureChamber = new BlockNeutronCaptureChamber(), "neutronCaptureChamber");
			AbstractionLayer.Registering.registerTileEntity(TileNeutronCaptureChamber.class, NuclearReferences.PREFIX + "neutronCaptureChamber");
			AbstractionLayer.Registering.registerBlock(blockThermometer = new BlockThermometer(), "thermometer");
			AbstractionLayer.Registering.registerBlock(blockControlRod = new BlockControlRod(), "controlRod");
			AbstractionLayer.Registering.registerBlock(blockUraniumOre = new BlockUraniumOre(), "uraniumOre");
			AbstractionLayer.Registering.registerBlock(blockFusionReactor = new BlockFusionReactor(), "fusionReactor");
			AbstractionLayer.Registering.registerTileEntity(TileFusionReactor.class, NuclearReferences.PREFIX + "fusionReactor");
			AbstractionLayer.Registering.registerBlock(blockTurbine = new BlockTurbine(), "turbine");
			AbstractionLayer.Registering.registerTileEntity(TileTurbine.class, NuclearReferences.PREFIX + "turbine");
			AbstractionLayer.Registering.registerBlock(blockElectromagnet = new BlockElectromagnet(), ItemBlockMetadata.class, "electromagnet");
			AbstractionLayer.Registering.registerBlock(blockPlasma = new BlockPlasma(), "plasma");
			AbstractionLayer.Registering.registerTileEntity(TilePlasma.class, NuclearReferences.PREFIX + "plasma");
			AbstractionLayer.Registering.registerBlock(blockSiren = new BlockSiren(), "siren");
			AbstractionLayer.Registering.registerTileEntity(TileSiren.class, NuclearReferences.PREFIX + "siren");

			AbstractionLayer.Registering.registerBlock(blockInsertableControlRod = new BlockInsertableControlRod(), "insertableControlRod");
			AbstractionLayer.Registering.registerTileEntity(TileInsertableControlRod.class, NuclearReferences.PREFIX + "insertableControlRod");

			AbstractionLayer.Registering.registerBlock(blockReactorControlPanel = new BlockReactorControlPanel(), "reactorControlPanel");
			AbstractionLayer.Registering.registerTileEntity(TileReactorControlPanel.class, NuclearReferences.PREFIX + "reactorControlPanel");

			AbstractionLayer.Registering.registerBlock(blockMeltedReactor = new BlockMeltedReactor(), "meltedReactor");
			AbstractionLayer.Registering.registerTileEntity(TileMeltedReactor.class, NuclearReferences.PREFIX + "meltedReactor");

			AbstractionLayer.Registering.registerBlock(blockRadioactiveGrass = new BlockRadioactiveGrass(), "radioactiveGrass");
			AbstractionLayer.Registering.registerBlock(blockRadioactiveStone = new BlockRadioactiveStone(), "radioactiveStone");
			AbstractionLayer.Registering.registerBlock(blockRadioactiveDirt = new BlockRadioactiveDirt(), "radioactiveDirt");

			AbstractionLayer.Registering.registerOre("oreUraniumPhysica", blockUraniumOre);
			AbstractionLayer.Registering.registerOre("blockRadioactive", new ItemStack(blockRadioactiveDirt, 1, 15));
			AbstractionLayer.Registering.registerOre("blockRadioactiveDirt", new ItemStack(blockRadioactiveDirt, 1, 15));
			AbstractionLayer.Registering.registerOre("blockRadioactiveStone", new ItemStack(blockRadioactiveStone, 1, 15));
			AbstractionLayer.Registering.registerOre("blockRadioactiveGrass", new ItemStack(blockRadioactiveGrass, 1, 15));
		}
	}
}
