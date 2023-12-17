package electrodynamics.datagen.server;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ElectrodynamicsBlockTagsProvider extends BlockTagsProvider {

	public ElectrodynamicsBlockTagsProvider(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
		super(pGenerator, References.ID, existingFileHelper);
	}

	@Override
	protected void addTags() {

		for (SubtypeOre ore : SubtypeOre.values()) {
			tag(ore.blockTag).add(ElectrodynamicsBlocks.getBlock(ore));
		}

		for (SubtypeResourceBlock storage : SubtypeResourceBlock.values()) {
			tag(storage.blockTag).add(ElectrodynamicsBlocks.getBlock(storage));
		}

		Builder<Block> ores = tag(ElectrodynamicsTags.Blocks.ORES);

		for (SubtypeOre ore : SubtypeOre.values()) {
			ores.addTag(ore.blockTag);
		}

	}

}
