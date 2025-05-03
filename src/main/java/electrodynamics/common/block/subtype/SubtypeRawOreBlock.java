package electrodynamics.common.block.subtype;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import electrodynamics.common.item.subtype.SubtypeRawOre;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import voltaic.api.ISubtype;
import voltaic.common.tags.VoltaicTags;

public enum SubtypeRawOreBlock implements ISubtype {
	tin(1, VoltaicTags.Items.BLOCK_RAW_ORE_TIN, VoltaicTags.Blocks.BLOCK_RAW_ORE_TIN, VoltaicTags.Items.RAW_ORE_TIN, () -> ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.tin)),
    lead(2, VoltaicTags.Items.BLOCK_RAW_ORE_LEAD, VoltaicTags.Blocks.BLOCK_RAW_ORE_LEAD, VoltaicTags.Items.RAW_ORE_LEAD, () -> ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.lead)),
    silver(2, VoltaicTags.Items.BLOCK_RAW_ORE_SILVER, VoltaicTags.Blocks.BLOCK_RAW_ORE_SILVER, VoltaicTags.Items.RAW_ORE_SILVER, () -> ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.silver)),
    chromium(3, VoltaicTags.Items.BLOCK_RAW_ORE_CHROMIUM, VoltaicTags.Blocks.BLOCK_RAW_ORE_CHROMIUM, VoltaicTags.Items.RAW_ORE_CHROMIUM, () -> ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.chromium)),
    titanium(3, VoltaicTags.Items.BLOCK_RAW_ORE_TITANIUM, VoltaicTags.Blocks.BLOCK_RAW_ORE_TITANIUM, VoltaicTags.Items.RAW_ORE_TITANIUM, () -> ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.titanium)),
    uranium(3, VoltaicTags.Items.BLOCK_RAW_ORE_URANIUM, VoltaicTags.Blocks.BLOCK_RAW_ORE_URANIUM, VoltaicTags.Items.RAW_ORE_URANIUM, () -> ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.uranium)),
    thorium(3, VoltaicTags.Items.BLOCK_RAW_ORE_THORIUM, VoltaicTags.Blocks.BLOCK_RAW_ORE_THORIUM, VoltaicTags.Items.RAW_ORE_THORIUM, () -> ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.thorium));

	// 0 = wood, 1 = stone, 2 = iron, 3 = diamond
	public final int miningLevel;
	public final TagKey<Item> itemTag;
	public final TagKey<Block> blockTag;
	public final TagKey<Item> sourceRawOre;
	public final Supplier<Item> productRawOre;

	SubtypeRawOreBlock(int miningLevel, TagKey<Item> itemTag, TagKey<Block> blockTag, TagKey<Item> sourceRawOre, Supplier<Item> productRawOre) {
		this.miningLevel = miningLevel;
		this.itemTag = itemTag;
		this.blockTag = blockTag;
		this.sourceRawOre = sourceRawOre;
		this.productRawOre = productRawOre;
	}

	@Override
	public String tag() {
		return "raworeblock" + name();
	}

	@Override
	public String forgeTag() {
		return "raworeblocks/" + name();
	}

	@Override
	public boolean isItem() {
		return false;
	}

	public static SubtypeRawOreBlock[] getForMiningLevel(int level) {
		List<SubtypeRawOreBlock> values = new ArrayList<>();
		for (SubtypeRawOreBlock value : values()) {
			if (value.miningLevel == level) {
				values.add(value);
			}
		}
		return values.toArray(new SubtypeRawOreBlock[] {});
	}

}
