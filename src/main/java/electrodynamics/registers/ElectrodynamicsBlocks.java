package electrodynamics.registers;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.BlockFrame;
import electrodynamics.common.block.BlockLogisticalManager;
import electrodynamics.common.block.BlockOre;
import electrodynamics.common.block.BlockSeismicMarker;
import electrodynamics.common.block.connect.BlockFluidPipe;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.connect.BlockLogisticalWire;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.block.subtype.SubtypeConcrete;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.block.subtype.SubtypeRawOreBlock;
import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.block.subtype.SubtypeWire.WireClass;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import voltaic.api.registration.BulkRegistryObject;
import voltaic.common.block.BlockCustomGlass;
import voltaic.common.block.BlockMachine;
import voltaic.common.block.connect.BlockScaffold;
import net.minecraft.world.level.material.Material;

public class ElectrodynamicsBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Electrodynamics.ID);

	public static final BulkRegistryObject<BlockOre, SubtypeOre> BLOCKS_ORE = new BulkRegistryObject<>(SubtypeOre.values(), subtype -> BLOCKS.register(subtype.tag(), () -> new BlockOre(subtype)));
    public static final BulkRegistryObject<BlockOre, SubtypeOreDeepslate> BLOCKS_DEEPSLATEORE = new BulkRegistryObject<>(SubtypeOreDeepslate.values(), subtype -> BLOCKS.register(subtype.tag(), () -> new BlockOre(subtype)));
    public static final BulkRegistryObject<Block, SubtypeRawOreBlock> BLOCKS_RAWORE = new BulkRegistryObject<>(SubtypeRawOreBlock.values(), subtype -> BLOCKS.register(subtype.tag(), () -> new Block(Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F))));
    public static final BulkRegistryObject<BlockMachine, SubtypeMachine> BLOCKS_MACHINE = new BulkRegistryObject<>(SubtypeMachine.values(), subtype -> BLOCKS.register(subtype.tag(), () -> new BlockMachine(subtype)));
    public static final BulkRegistryObject<BlockWire, SubtypeWire> BLOCKS_WIRE = new BulkRegistryObject<>(SubtypeWire.values(), subtype -> {
        if(subtype.getWireClass() == WireClass.LOGISTICAL) {
            return BLOCKS.register(subtype.tag(), () -> new BlockLogisticalWire(subtype));
        } else {
            return BLOCKS.register(subtype.tag(), () -> new BlockWire(subtype));
        }
    });
    public static final BulkRegistryObject<BlockFluidPipe, SubtypeFluidPipe> BLOCKS_FLUIDPIPE = new BulkRegistryObject<>(SubtypeFluidPipe.values(), subtype -> BLOCKS.register(subtype.tag(), () -> new BlockFluidPipe(subtype)));
    public static final BulkRegistryObject<BlockCustomGlass, SubtypeGlass> BLOCKS_CUSTOMGLASS = new BulkRegistryObject<>(SubtypeGlass.values(), subtype -> BLOCKS.register(subtype.tag(), () -> new BlockCustomGlass(subtype.hardness, subtype.resistance)));
    public static final BulkRegistryObject<Block, SubtypeResourceBlock> BLOCKS_RESOURCE = new BulkRegistryObject<>(SubtypeResourceBlock.values(), subtype -> BLOCKS.register(subtype.tag(), () -> new Block(subtype.getProperties().strength(subtype.getHardness(), subtype.getResistance()).sound(subtype.getSoundType()))));
    public static final BulkRegistryObject<Block, SubtypeConcrete> BLOCKS_CONCRETE = new BulkRegistryObject<>(SubtypeConcrete.values(), subtype -> BLOCKS.register(subtype.tag(), () -> new Block(Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(50F, 1200F))));

    public static final RegistryObject<BlockSeismicMarker> BLOCK_SEISMICMARKER = BLOCKS.register("seismicmarker", BlockSeismicMarker::new);
    public static final RegistryObject<BlockFrame> BLOCK_FRAME = BLOCKS.register("frame", () -> new BlockFrame(0));
    public static final RegistryObject<BlockFrame> BLOCK_FRAME_CORNER = BLOCKS.register("framecorner", () -> new BlockFrame(1));
    public static final RegistryObject<BlockLogisticalManager> BLOCK_LOGISTICALMANAGER = BLOCKS.register("logisticalmanager", BlockLogisticalManager::new);
    public static final RegistryObject<BlockScaffold> BLOCK_STEELSCAFFOLDING = BLOCKS.register("steelscaffold", () -> new BlockScaffold(Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(2.0F, 3.0F).sound(SoundType.METAL).noOcclusion()));

}
