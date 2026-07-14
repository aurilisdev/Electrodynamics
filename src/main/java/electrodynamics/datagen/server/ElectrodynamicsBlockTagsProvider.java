package electrodynamics.datagen.server;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.BlockOre;
import electrodynamics.common.block.BlockRawOre;
import electrodynamics.common.block.connect.BlockFluidPipe;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.block.subtype.SubtypeRawOreBlock;
import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import voltaic.common.block.BlockCustomGlass;
import voltaic.common.block.BlockMachine;
import voltaic.common.tags.VoltaicTags;

public class ElectrodynamicsBlockTagsProvider extends BlockTagsProvider {

	public ElectrodynamicsBlockTagsProvider(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
		super(pGenerator, Electrodynamics.ID, existingFileHelper);
	}

	@Override
	protected void addTags() {

		tag(BlockTags.MINEABLE_WITH_PICKAXE)
				//
				.add(ElectrodynamicsBlocks.BLOCKS_ORE.getAllValuesArray(new BlockOre[0]))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_DEEPSLATEORE.getAllValuesArray(new BlockOre[0]))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_RAWORE.getAllValuesArray(new BlockRawOre[0]))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_MACHINE.getAllValuesArray(new BlockMachine[0]))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_WIRE.getAllValuesArray(new BlockWire[0]))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_FLUIDPIPE.getAllValuesArray(new BlockFluidPipe[0]))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getAllValuesArray(new BlockCustomGlass[0]))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_RESOURCE.getAllValuesArray(new Block[0]))
				//
				.add(ElectrodynamicsBlocks.BLOCK_SEISMICMARKER.get())
				//
				.add(ElectrodynamicsBlocks.BLOCK_LOGISTICALMANAGER.get())
				//
				.add(ElectrodynamicsBlocks.BLOCK_LOGISTICALMANAGER.get())
				//
				.add(ElectrodynamicsBlocks.BLOCK_STEELSCAFFOLDING.get());


		tag(BlockTags.MINEABLE_WITH_HOE)
				//
				.add(ElectrodynamicsBlocks.BLOCK_FRAME.get())
				//
				.add(ElectrodynamicsBlocks.BLOCK_FRAME_CORNER.get());

		tag(Tags.Blocks.NEEDS_WOOD_TOOL)
				//
				.add(ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getAllValuesArray(new BlockCustomGlass[0]))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_ORE.getSpecificValuesArray(new BlockOre[0], SubtypeOre.getOreForMiningLevel(0)))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_DEEPSLATEORE.getSpecificValuesArray(new BlockOre[0], SubtypeOreDeepslate.getOreForMiningLevel(0)))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_RAWORE.getSpecificValuesArray(new BlockRawOre[0], SubtypeRawOreBlock.getForMiningLevel(0)))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_RESOURCE.getSpecificValuesArray(new Block[0], SubtypeResourceBlock.getForMiningLevel(0)))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_WIRE.getAllValuesArray(new BlockWire[0]))
				//
				.add(ElectrodynamicsBlocks.BLOCK_FRAME.get())
				//
				.add(ElectrodynamicsBlocks.BLOCK_FRAME_CORNER.get());

		tag(BlockTags.NEEDS_STONE_TOOL)
				//
				.add(ElectrodynamicsBlocks.BLOCKS_MACHINE.getAllValuesArray(new BlockMachine[0]))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_FLUIDPIPE.getAllValuesArray(new BlockFluidPipe[0]))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_ORE.getSpecificValuesArray(new BlockOre[0], SubtypeOre.getOreForMiningLevel(1)))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_DEEPSLATEORE.getSpecificValuesArray(new BlockOre[0], SubtypeOreDeepslate.getOreForMiningLevel(1)))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_RAWORE.getSpecificValuesArray(new BlockRawOre[0], SubtypeRawOreBlock.getForMiningLevel(1)))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_RESOURCE.getSpecificValuesArray(new Block[0], SubtypeResourceBlock.getForMiningLevel(1)))
				//
				.add(ElectrodynamicsBlocks.BLOCK_SEISMICMARKER.get())
				//
				.add(ElectrodynamicsBlocks.BLOCK_LOGISTICALMANAGER.get())
				//
				.add(ElectrodynamicsBlocks.BLOCK_LOGISTICALMANAGER.get())
				//
				.add(ElectrodynamicsBlocks.BLOCK_STEELSCAFFOLDING.get());

		tag(BlockTags.NEEDS_IRON_TOOL)
				//
				.add(ElectrodynamicsBlocks.BLOCKS_ORE.getSpecificValuesArray(new BlockOre[0], SubtypeOre.getOreForMiningLevel(2)))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_DEEPSLATEORE.getSpecificValuesArray(new BlockOre[0], SubtypeOreDeepslate.getOreForMiningLevel(2)))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_RAWORE.getSpecificValuesArray(new BlockRawOre[0], SubtypeRawOreBlock.getForMiningLevel(2)))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_RESOURCE.getSpecificValuesArray(new Block[0], SubtypeResourceBlock.getForMiningLevel(2)));

		tag(BlockTags.NEEDS_DIAMOND_TOOL)
				//
				.add(ElectrodynamicsBlocks.BLOCKS_ORE.getSpecificValuesArray(new BlockOre[0], SubtypeOre.getOreForMiningLevel(3)))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_DEEPSLATEORE.getSpecificValuesArray(new BlockOre[0], SubtypeOreDeepslate.getOreForMiningLevel(3)))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_RAWORE.getSpecificValuesArray(new BlockRawOre[0], SubtypeRawOreBlock.getForMiningLevel(3)))
				//
				.add(ElectrodynamicsBlocks.BLOCKS_RESOURCE.getSpecificValuesArray(new Block[0], SubtypeResourceBlock.getForMiningLevel(3)));

		for (SubtypeOre ore : SubtypeOre.values()) {
			tag(ore.blockTag).add(ElectrodynamicsBlocks.BLOCKS_ORE.getValue(ore));
		}

		for (SubtypeOreDeepslate ore : SubtypeOreDeepslate.values()) {
			tag(ore.blockTag).add(ElectrodynamicsBlocks.BLOCKS_DEEPSLATEORE.getValue(ore));
		}

		for (SubtypeResourceBlock storage : SubtypeResourceBlock.values()) {
			tag(storage.blockTag).add(ElectrodynamicsBlocks.BLOCKS_RESOURCE.getValue(storage));
		}

		for (SubtypeRawOreBlock block : SubtypeRawOreBlock.values()) {
			tag(block.blockTag).add(ElectrodynamicsBlocks.BLOCKS_RAWORE.getValue(block));
		}

		TagAppender<Block> ores = tag(VoltaicTags.Blocks.ORES);

		for (SubtypeOre ore : SubtypeOre.values()) {
			ores.addTag(ore.blockTag);
		}
		
		tag(VoltaicTags.Blocks.CONCRETES).add(ElectrodynamicsBlocks.BLOCKS_CONCRETE.getAllValuesArray(new Block[0]));

	}

}
