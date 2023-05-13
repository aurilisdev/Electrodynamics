package electrodynamics.registers;

import static electrodynamics.registers.UnifiedElectrodynamicsRegister.supplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import electrodynamics.api.ISubtype;
import electrodynamics.api.References;
import electrodynamics.common.block.BlockCustomGlass;
import electrodynamics.common.block.BlockFrame;
import electrodynamics.common.block.BlockLogisticalManager;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.BlockMultiSubnode;
import electrodynamics.common.block.BlockSeismicMarker;
import electrodynamics.common.block.BlockValve;
import electrodynamics.common.block.connect.BlockFluidPipe;
import electrodynamics.common.block.connect.BlockGasPipe;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.gastransformer.BlockGasTransformerSide;
import electrodynamics.common.block.gastransformer.compressor.BlockCompressor;
import electrodynamics.common.block.gastransformer.thermoelectricmanipulator.BlockThermoelectricManipulator;
import electrodynamics.common.block.gastransformer.BlockGasTransformerAddonTank;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.block.subtype.SubtypeGasPipe;
import electrodynamics.common.block.subtype.SubtypeRawOreBlock;
import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import electrodynamics.common.block.subtype.SubtypeWire;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ElectrodynamicsBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, References.ID);
	public static final HashMap<ISubtype, RegistryObject<Block>> SUBTYPEBLOCKREGISTER_MAPPINGS = new HashMap<>();

	public static BlockMultiSubnode multi;
	public static BlockSeismicMarker blockSeismicMarker;
	public static BlockFrame blockFrame;
	public static BlockFrame blockFrameCorner;
	public static BlockLogisticalManager blockLogisticalManager;
	
	public static BlockCompressor blockCompressor;
	public static BlockCompressor blockDecompressor;
	
	public static BlockThermoelectricManipulator blockThermoelectricManipulator;
	
	public static BlockGasTransformerSide blockGasTransformerSide;
	public static BlockGasTransformerAddonTank blockGasTransformerAddonTank;
	
	public static BlockValve blockGasValve;
	public static BlockValve blockFluidValve;

	static {
		for (SubtypeOre subtype : SubtypeOre.values()) {
			SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(() -> new DropExperienceBlock(Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(subtype.hardness, subtype.resistance), UniformInt.of(subtype.minXP, subtype.maxXP)), subtype)));
		}
		for (SubtypeOreDeepslate subtype : SubtypeOreDeepslate.values()) {
			SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(() -> new DropExperienceBlock(Properties.of(Material.STONE).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops().strength(subtype.hardness + 1.5f, subtype.resistance + 1.5f), UniformInt.of(subtype.minXP, subtype.maxXP)), subtype)));
		}
		for (SubtypeRawOreBlock subtype : SubtypeRawOreBlock.values()) {
			SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(() -> new Block(Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F)), subtype)));
		}
		for (SubtypeMachine subtype : SubtypeMachine.values()) {
			SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(() -> new BlockMachine(subtype), subtype)));
		}
		for (SubtypeWire subtype : SubtypeWire.values()) {
			SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(() -> new BlockWire(subtype), subtype)));
		}
		for (SubtypeFluidPipe subtype : SubtypeFluidPipe.values()) {
			SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(() -> new BlockFluidPipe(subtype), subtype)));
		}
		for (SubtypeGlass subtype : SubtypeGlass.values()) {
			SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(() -> new BlockCustomGlass(subtype), subtype)));
		}
		for (SubtypeResourceBlock subtype : SubtypeResourceBlock.values()) {
			SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(() -> new Block(Properties.of(subtype.getMaterial()).strength(subtype.getHardness(), subtype.getResistance()).sound(subtype.getSoundType())), subtype)));
		}
		for(SubtypeGasPipe pipe : SubtypeGasPipe.values()) {
			SUBTYPEBLOCKREGISTER_MAPPINGS.put(pipe, BLOCKS.register(pipe.tag(), () -> new BlockGasPipe(pipe)));
		}
	}

	public static final RegistryObject<Block> BLOCK_MULTISUBNODE = BLOCKS.register("multisubnode", supplier(() -> multi = new BlockMultiSubnode()));
	public static final RegistryObject<Block> BLOCK_SEISMICMARKER = BLOCKS.register("seismicmarker", supplier(() -> blockSeismicMarker = new BlockSeismicMarker()));
	public static final RegistryObject<Block> BLOCK_FRAME = BLOCKS.register("frame", supplier(() -> blockFrame = new BlockFrame(0)));
	public static final RegistryObject<Block> BLOCK_FRAME_CORNER = BLOCKS.register("framecorner", supplier(() -> blockFrameCorner = new BlockFrame(1)));
	public static final RegistryObject<Block> BLOCK_LOGISTICALMANAGER = BLOCKS.register("logisticalmanager", supplier(() -> blockLogisticalManager = new BlockLogisticalManager()));
	public static final RegistryObject<Block> BLOCK_COMPRESSOR = BLOCKS.register("compressor", () -> blockCompressor = new BlockCompressor(false));
	public static final RegistryObject<Block> BLOCK_DECOMPRESSOR = BLOCKS.register("decompressor", () -> blockDecompressor = new BlockCompressor(true));
	public static final RegistryObject<Block> BLOCK_COMPRESSOR_SIDE = BLOCKS.register("compressorside", () -> blockGasTransformerSide = new BlockGasTransformerSide());
	public static final RegistryObject<Block> BLOCK_COMPRESSOR_ADDONTANK = BLOCKS.register("compressoraddontank", () -> blockGasTransformerAddonTank = new BlockGasTransformerAddonTank());
	public static final RegistryObject<Block> BLOCK_THERMOELECTRICMANIPULATOR = BLOCKS.register("thermoelectricmanipulator", () -> blockThermoelectricManipulator = new BlockThermoelectricManipulator());
	public static final RegistryObject<Block> BLOCK_GASVALVE = BLOCKS.register("gasvalve", supplier(() -> blockGasValve = new BlockValve(true)));
	public static final RegistryObject<Block> BLOCK_FLUIDVALVE = BLOCKS.register("fluidvalve", () -> blockFluidValve = new BlockValve(false));

	public static Block[] getAllBlockForSubtype(ISubtype[] values) {
		List<Block> list = new ArrayList<>();
		for (ISubtype value : values) {
			list.add(SUBTYPEBLOCKREGISTER_MAPPINGS.get(value).get());
		}
		return list.toArray(new Block[] {});
	}

	public static Block getBlock(ISubtype value) {
		return SUBTYPEBLOCKREGISTER_MAPPINGS.get(value).get();
	}

}
