package physica.nuclear.common;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import physica.api.core.abstraction.AbstractionLayer;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.library.energy.ElectricityDisplay;
import physica.library.energy.ElectricityUtilities;
import physica.library.energy.base.Unit;
import physica.library.item.ItemBlockDescriptable;
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
import physica.nuclear.common.tile.TileThermometer;
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
			AbstractionLayer.Registering.registerBlock(blockParticleAccelerator = new BlockParticleAccelerator(), ItemBlockDescriptable.class, "accelerator");
			AbstractionLayer.Registering.registerTileEntity(TileParticleAccelerator.class, NuclearReferences.PREFIX + "accelerator");
			AbstractionLayer.Registering.registerBlock(blockQuantumAssembler = new BlockQuantumAssembler(), ItemBlockDescriptable.class, "assembler");
			AbstractionLayer.Registering.registerTileEntity(TileQuantumAssembler.class, NuclearReferences.PREFIX + "assembler");
			AbstractionLayer.Registering.registerBlock(blockCentrifuge = new BlockGasCentrifuge(), ItemBlockDescriptable.class, "centrifuge");
			AbstractionLayer.Registering.registerTileEntity(TileGasCentrifuge.class, NuclearReferences.PREFIX + "centrifuge");
			AbstractionLayer.Registering.registerBlock(blockChemicalBoiler = new BlockChemicalBoiler(), ItemBlockDescriptable.class, "chemicalBoiler");
			AbstractionLayer.Registering.registerTileEntity(TileChemicalBoiler.class, NuclearReferences.PREFIX + "chemicalBoiler");
			AbstractionLayer.Registering.registerBlock(blockChemicalExtractor = new BlockChemicalExtractor(), ItemBlockDescriptable.class, "chemicalExtractor");
			AbstractionLayer.Registering.registerTileEntity(TileChemicalExtractor.class, NuclearReferences.PREFIX + "chemicalExtractor");
			AbstractionLayer.Registering.registerBlock(blockFissionReactor = new BlockFissionReactor(), ItemBlockDescriptable.class, "fissionReactor");
			AbstractionLayer.Registering.registerTileEntity(TileFissionReactor.class, NuclearReferences.PREFIX + "fissionReactor");
			AbstractionLayer.Registering.registerBlock(blockNeutronCaptureChamber = new BlockNeutronCaptureChamber(), ItemBlockDescriptable.class, "neutronCaptureChamber");
			AbstractionLayer.Registering.registerTileEntity(TileNeutronCaptureChamber.class, NuclearReferences.PREFIX + "neutronCaptureChamber");
			AbstractionLayer.Registering.registerBlock(blockThermometer = new BlockThermometer(), ItemBlockDescriptable.class, "thermometer");
			AbstractionLayer.Registering.registerTileEntity(TileThermometer.class, NuclearReferences.PREFIX + "thermometer");
			AbstractionLayer.Registering.registerBlock(blockControlRod = new BlockControlRod(), ItemBlockDescriptable.class, "controlRod");
			AbstractionLayer.Registering.registerBlock(blockUraniumOre = new BlockUraniumOre(), "uraniumOre");
			AbstractionLayer.Registering.registerBlock(blockFusionReactor = new BlockFusionReactor(), ItemBlockDescriptable.class, "fusionReactor");
			AbstractionLayer.Registering.registerTileEntity(TileFusionReactor.class, NuclearReferences.PREFIX + "fusionReactor");
			AbstractionLayer.Registering.registerBlock(blockTurbine = new BlockTurbine(), ItemBlockDescriptable.class, "turbine");
			AbstractionLayer.Registering.registerTileEntity(TileTurbine.class, NuclearReferences.PREFIX + "turbine");
			AbstractionLayer.Registering.registerBlock(blockElectromagnet = new BlockElectromagnet(), ItemBlockMetadata.class, "electromagnet");
			AbstractionLayer.Registering.registerBlock(blockPlasma = new BlockPlasma(), "plasma");
			AbstractionLayer.Registering.registerTileEntity(TilePlasma.class, NuclearReferences.PREFIX + "plasma");
			AbstractionLayer.Registering.registerBlock(blockSiren = new BlockSiren(), ItemBlockDescriptable.class, "siren");
			AbstractionLayer.Registering.registerTileEntity(TileSiren.class, NuclearReferences.PREFIX + "siren");

			AbstractionLayer.Registering.registerBlock(blockInsertableControlRod = new BlockInsertableControlRod(), ItemBlockDescriptable.class, "insertableControlRod");
			AbstractionLayer.Registering.registerTileEntity(TileInsertableControlRod.class, NuclearReferences.PREFIX + "insertableControlRod");

			AbstractionLayer.Registering.registerBlock(blockReactorControlPanel = new BlockReactorControlPanel(), ItemBlockDescriptable.class, "reactorControlPanel");
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

			ItemBlockDescriptable.addDescription(blockParticleAccelerator, 0,
					EnumChatFormatting.GOLD + "Power Usage: " + EnumChatFormatting.GRAY + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(TileParticleAccelerator.POWER_USAGE, Unit.RF, Unit.WATT), Unit.WATT));
			ItemBlockDescriptable.addDescription(blockQuantumAssembler, 0,
					EnumChatFormatting.GOLD + "Power Usage: " + EnumChatFormatting.GRAY + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(TileQuantumAssembler.POWER_USAGE, Unit.RF, Unit.WATT), Unit.WATT));
			ItemBlockDescriptable.addDescription(blockCentrifuge, 0,
					EnumChatFormatting.GOLD + "Power Usage: " + EnumChatFormatting.GRAY + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(TileGasCentrifuge.POWER_USAGE, Unit.RF, Unit.WATT), Unit.WATT));
			ItemBlockDescriptable.addDescription(blockChemicalBoiler, 0,
					EnumChatFormatting.GOLD + "Power Usage: " + EnumChatFormatting.GRAY + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(TileChemicalBoiler.POWER_USAGE, Unit.RF, Unit.WATT), Unit.WATT));
			ItemBlockDescriptable.addDescription(blockChemicalExtractor, 0,
					EnumChatFormatting.GOLD + "Power Usage: " + EnumChatFormatting.GRAY + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(TileChemicalExtractor.POWER_USAGE, Unit.RF, Unit.WATT), Unit.WATT));
			ItemBlockDescriptable.addDescription(blockFusionReactor, 0,
					EnumChatFormatting.GOLD + "Power Usage: " + EnumChatFormatting.GRAY + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(TileFusionReactor.POWER_USAGE, Unit.RF, Unit.WATT), Unit.WATT));

			ItemBlockDescriptable.addDescriptionShifted(blockParticleAccelerator, 0, "Accelerates particles to high speeds", "in order to produce new exotic", "forms of matter.");
			ItemBlockDescriptable.addDescriptionShifted(blockQuantumAssembler, 0, "Assembles matter from dark matter", "using a template object.");
			ItemBlockDescriptable.addDescriptionShifted(blockCentrifuge, 0, "Separates uranium isotopes from", "uranium hexafluoride.");
			ItemBlockDescriptable.addDescriptionShifted(blockChemicalBoiler, 0, "Boils uranium-rich materials into", "uranium hexafluoride.");
			ItemBlockDescriptable.addDescriptionShifted(blockChemicalExtractor, 0, "Extracts chemicals from other substances.");
			ItemBlockDescriptable.addDescriptionShifted(blockFissionReactor, 0, "Emits heat generated by a fission reaction.");
			ItemBlockDescriptable.addDescriptionShifted(blockFusionReactor, 0, "Emits plasma generated using electricity", "and a mix of deuterium and tritium.");
			ItemBlockDescriptable.addDescriptionShifted(blockTurbine, 0, "Generates electricity using steam.");
			ItemBlockDescriptable.addDescriptionShifted(blockSiren, 0, "Emits sound based on an input redstone signal.");
			ItemBlockDescriptable.addDescriptionShifted(blockThermometer, 0, "Emits redstone signal when it ", "hits a configurable temperature.");
			ItemBlockDescriptable.addDescriptionShifted(blockControlRod, 0, "Shuts down a fission reactor when", "placed next to it.", "Can also be pistoned.");
			ItemBlockDescriptable.addDescriptionShifted(blockReactorControlPanel, 0, "Shows information about a fission reactor", "when opened.");
			ItemBlockDescriptable.addDescriptionShifted(blockNeutronCaptureChamber, 0, "Slowly turns deuterium into tritium when", "placed on a fission reactor.");
			ItemBlockDescriptable.addDescriptionShifted(blockInsertableControlRod, 0, "Placed either on top or below a fission reactor.", "Can be inserted in increments to control", "the reactor's reactivity.");
		}
	}
}
