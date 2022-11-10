package electrodynamics.registers;

import static electrodynamics.registers.UnifiedElectrodynamicsRegister.supplier;

import java.util.HashMap;

import electrodynamics.api.ISubtype;
import electrodynamics.api.References;
import electrodynamics.common.block.BlockConcrete;
import electrodynamics.common.block.BlockCustomGlass;
import electrodynamics.common.block.BlockDeepslateOre;
import electrodynamics.common.block.BlockFrame;
import electrodynamics.common.block.BlockLogisticalManager;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.BlockMultiSubnode;
import electrodynamics.common.block.BlockOre;
import electrodynamics.common.block.BlockRawOre;
import electrodynamics.common.block.BlockResource;
import electrodynamics.common.block.BlockSeismicMarker;
import electrodynamics.common.block.connect.BlockPipe;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.subtype.SubtypeConcrete;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.block.subtype.SubtypePipe;
import electrodynamics.common.block.subtype.SubtypeRawOreBlock;
import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import electrodynamics.common.block.subtype.SubtypeWire;
import net.minecraft.world.level.block.Block;
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

	static {
		for (SubtypeOre subtype : SubtypeOre.values()) {
			SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(() -> new BlockOre(subtype), subtype)));
		}
		for (SubtypeOreDeepslate subtype : SubtypeOreDeepslate.values()) {
			SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(() -> new BlockDeepslateOre(subtype), subtype)));
		}
		for (SubtypeRawOreBlock subtype : SubtypeRawOreBlock.values()) {
			SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(() -> new BlockRawOre(subtype), subtype)));
		}
		for (SubtypeMachine subtype : SubtypeMachine.values()) {
			SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(() -> new BlockMachine(subtype), subtype)));
		}
		for (SubtypeWire subtype : SubtypeWire.values()) {
			SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(() -> new BlockWire(subtype), subtype)));
		}
		for (SubtypePipe subtype : SubtypePipe.values()) {
			SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(() -> new BlockPipe(subtype), subtype)));
		}
		for (SubtypeGlass subtype : SubtypeGlass.values()) {
			SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(() -> new BlockCustomGlass(subtype), subtype)));
		}
		for (SubtypeResourceBlock subtype : SubtypeResourceBlock.values()) {
			SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(() -> new BlockResource(subtype), subtype)));
		}
		for (SubtypeConcrete subtype : SubtypeConcrete.values()) {
			SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(() -> new BlockConcrete(subtype), subtype)));
		}
		BLOCKS.register("multisubnode", supplier(() -> multi = new BlockMultiSubnode()));
		BLOCKS.register("seismicmarker", supplier(() -> blockSeismicMarker = new BlockSeismicMarker()));
		BLOCKS.register("frame", supplier(() -> blockFrame = new BlockFrame()));
		BLOCKS.register("framecorner", supplier(() -> blockFrameCorner = new BlockFrame()));
		BLOCKS.register("logisticalmanager", supplier(() -> blockLogisticalManager = new BlockLogisticalManager()));
	}

}
