package electrodynamics.common.block.subtype;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import electrodynamics.common.item.subtype.SubtypeIngot;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag.INamedTag;
import voltaic.api.ISubtype;
import voltaic.common.tags.VoltaicTags;

public enum SubtypeResourceBlock implements ISubtype {
	tin(2.0f, 3.0f, Properties.copy(Blocks.IRON_BLOCK), SoundType.METAL, 1, VoltaicTags.Items.STORAGE_BLOCK_TIN, VoltaicTags.Blocks.STORAGE_BLOCK_TIN, VoltaicTags.Items.INGOT_TIN, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.tin)),
	copper(2.0f, 3.0f, Properties.copy(Blocks.IRON_BLOCK), SoundType.METAL, 1, VoltaicTags.Items.STORAGE_BLOCK_COPPER, VoltaicTags.Blocks.STORAGE_BLOCK_COPPER, VoltaicTags.Items.INGOT_COPPER, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.copper)),
    lead(2.0f, 3.0f, Properties.copy(Blocks.IRON_BLOCK), SoundType.METAL, 2, VoltaicTags.Items.STORAGE_BLOCK_LEAD, VoltaicTags.Blocks.STORAGE_BLOCK_LEAD, VoltaicTags.Items.INGOT_LEAD, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.lead)),
    silver(2.0f, 3.0f, Properties.copy(Blocks.IRON_BLOCK), SoundType.METAL, 2, VoltaicTags.Items.STORAGE_BLOCK_SILVER, VoltaicTags.Blocks.STORAGE_BLOCK_SILVER, VoltaicTags.Items.INGOT_SILVER, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.silver)),
    bronze(2.0f, 3.0f, Properties.copy(Blocks.IRON_BLOCK), SoundType.METAL, 1, VoltaicTags.Items.STORAGE_BLOCK_BRONZE, VoltaicTags.Blocks.STORAGE_BLOCK_BRONZE, VoltaicTags.Items.INGOT_BRONZE, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.bronze)),
    steel(2.0f, 3.0f, Properties.copy(Blocks.IRON_BLOCK), SoundType.METAL, 2, VoltaicTags.Items.STORAGE_BLOCK_STEEL, VoltaicTags.Blocks.STORAGE_BLOCK_STEEL, VoltaicTags.Items.INGOT_STEEL, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.steel)),
    aluminum(2.0f, 3.0f, Properties.copy(Blocks.IRON_BLOCK), SoundType.METAL, 2, VoltaicTags.Items.STORAGE_BLOCK_ALUMINUM, VoltaicTags.Blocks.STORAGE_BLOCK_ALUMINUM, VoltaicTags.Items.INGOT_ALUMINUM, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.aluminum)),
    chromium(2.0f, 3.0f, Properties.copy(Blocks.IRON_BLOCK), SoundType.METAL, 3, VoltaicTags.Items.STORAGE_BLOCK_CHROMIUM, VoltaicTags.Blocks.STORAGE_BLOCK_CHROMIUM, VoltaicTags.Items.INGOT_CHROMIUM, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.chromium)),
    stainlesssteel(2.0f, 3.0f, Properties.copy(Blocks.IRON_BLOCK), SoundType.METAL, 2, VoltaicTags.Items.STORAGE_BLOCK_STAINLESSSTEEL, VoltaicTags.Blocks.STORAGE_BLOCK_STAINLESSSTEEL, VoltaicTags.Items.INGOT_STAINLESSSTEEL, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.stainlesssteel)),
    vanadiumsteel(2.0f, 3.0f, Properties.copy(Blocks.IRON_BLOCK), SoundType.METAL, 2, VoltaicTags.Items.STORAGE_BLOCK_VANADIUMSTEEL, VoltaicTags.Blocks.STORAGE_BLOCK_VANADIUMSTEEL, VoltaicTags.Items.INGOT_VANADIUMSTEEL, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.vanadiumsteel)),
    hslasteel(2.0f, 3.0f, Properties.copy(Blocks.IRON_BLOCK), SoundType.METAL, 3, VoltaicTags.Items.STORAGE_BLOCK_HSLASTEEL, VoltaicTags.Blocks.STORAGE_BLOCK_HSLASTEEL, VoltaicTags.Items.INGOT_HSLASTEEL, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.hslasteel)),
    titanium(2.0f, 3.0f, Properties.copy(Blocks.IRON_BLOCK), SoundType.METAL, 3, VoltaicTags.Items.STORAGE_BLOCK_TITANIUM, VoltaicTags.Blocks.STORAGE_BLOCK_TITANIUM, VoltaicTags.Items.INGOT_TITANIUM, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.titanium)),
    titaniumcarbide(2.0f, 3.0f, Properties.copy(Blocks.IRON_BLOCK), SoundType.METAL, 3, VoltaicTags.Items.STORAGE_BLOCK_TITANIUMCARBIDE, VoltaicTags.Blocks.STORAGE_BLOCK_TITANIUMCARBIDE, VoltaicTags.Items.INGOT_TITANIUMCARBIDE, () -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.titaniumcarbide));

	private float hardness;
	private float resistance;
	private Properties material;
	private SoundType soundType;
	// 0 = wood, 1 = stone, 2 = iron, 3 = diamond
	public final int miningLevel;
	public final INamedTag<Item> itemTag;
	public final INamedTag<Block> blockTag;
	public final INamedTag<Item> sourceIngot;
	public final Supplier<Item> productIngot;

	SubtypeResourceBlock(float hardness, float resistance, Properties material, SoundType soundType, int miningLevel, INamedTag<Item> itemTag, INamedTag<Block> blockTag, INamedTag<Item> sourceIngot, Supplier<Item> productIngot) {
		this.hardness = hardness;
		this.resistance = resistance;
		this.material = material;
		this.soundType = soundType;
		this.miningLevel = miningLevel;
		this.itemTag = itemTag;
		this.blockTag = blockTag;
		this.sourceIngot = sourceIngot;
		this.productIngot = productIngot;
	}

	public float getHardness() {
		return hardness;
	}

	public float getResistance() {
		return resistance;
	}

	public Properties getProperties() {
		return material;
	}

	public SoundType getSoundType() {
		return soundType;
	}

	@Override
	public String tag() {
		return "resourceblock" + name();
	}

	@Override
	public String forgeTag() {
		return "blocks/" + name();
	}

	@Override
	public boolean isItem() {
		return false;
	}

	public static SubtypeResourceBlock[] getForMiningLevel(int level) {
		List<SubtypeResourceBlock> values = new ArrayList<>();
		for (SubtypeResourceBlock value : values()) {
			if (value.miningLevel == level) {
				values.add(value);
			}
		}
		return values.toArray(new SubtypeResourceBlock[] {});
	}

}
