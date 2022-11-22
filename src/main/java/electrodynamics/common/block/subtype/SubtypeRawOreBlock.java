package electrodynamics.common.block.subtype;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import electrodynamics.api.ISubtype;
import electrodynamics.common.item.subtype.SubtypeRawOre;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public enum SubtypeRawOreBlock implements ISubtype {
	tin(1, ElectrodynamicsTags.Items.RAW_ORE_TIN, () -> ElectrodynamicsItems.getItem(SubtypeRawOre.tin)),
	lead(2, ElectrodynamicsTags.Items.RAW_ORE_LEAD, () -> ElectrodynamicsItems.getItem(SubtypeRawOre.lead)),
	silver(2, ElectrodynamicsTags.Items.RAW_ORE_SILVER, () -> ElectrodynamicsItems.getItem(SubtypeRawOre.silver)),
	chromium(3, ElectrodynamicsTags.Items.RAW_ORE_CHROMIUM, () -> ElectrodynamicsItems.getItem(SubtypeRawOre.chromium)),
	titanium(3, ElectrodynamicsTags.Items.RAW_ORE_TITANIUM, () -> ElectrodynamicsItems.getItem(SubtypeRawOre.titanium)),
	uranium(3, ElectrodynamicsTags.Items.RAW_ORE_URANIUM, () -> ElectrodynamicsItems.getItem(SubtypeRawOre.uranium)),
	thorium(3, ElectrodynamicsTags.Items.RAW_ORE_THORIUM, () -> ElectrodynamicsItems.getItem(SubtypeRawOre.thorium));
	
	//0 = wood, 1 = stone, 2 = iron, 3 = diamond
	public final int miningLevel;
	public final TagKey<Item> sourceRawOre;
	public final Supplier<Item> productRawOre;
	
	private SubtypeRawOreBlock(int miningLevel, TagKey<Item> sourceRawOre, Supplier<Item> productRawOre) {
		this.miningLevel = miningLevel;
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
		for(SubtypeRawOreBlock value : values()) {
			if(value.miningLevel == level) {
				values.add(value);
			}
		}
		return values.toArray(new SubtypeRawOreBlock[] {});
	}

}
