package electrodynamics.datagen.server;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import voltaic.common.tags.VoltaicTags;

public class ElectrodynamicsBlockTagsProvider extends BlockTagsProvider {

	public ElectrodynamicsBlockTagsProvider(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
		super(pGenerator, Electrodynamics.ID, existingFileHelper);
	}

	@Override
	protected void addTags() {

		for (SubtypeOre ore : SubtypeOre.values()) {
			tag(ore.blockTag).add(ElectrodynamicsBlocks.BLOCKS_ORE.getValue(ore));
		}

		for (SubtypeResourceBlock storage : SubtypeResourceBlock.values()) {
			tag(storage.blockTag).add(ElectrodynamicsBlocks.BLOCKS_RESOURCE.getValue(storage));
		}
		
		Builder<Block> ores = tag(VoltaicTags.Blocks.ORES);

		for (SubtypeOre ore : SubtypeOre.values()) {
			ores.addTag(ore.blockTag);
		}
		
		tag(VoltaicTags.Blocks.CONCRETES).add(ElectrodynamicsBlocks.BLOCKS_CONCRETE.getAllValuesArray(new Block[0]));

	}

}
