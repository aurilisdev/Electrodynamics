package electrodynamics.common.block.subtype;

import electrodynamics.api.ISubtype;
import electrodynamics.common.tags.ElectrodynamicsTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;

public enum SubtypeFluidPipe implements ISubtype {
	copper(5000, Tags.Items.INGOTS_COPPER),
	steel(10000, ElectrodynamicsTags.Items.INGOT_STEEL);

	public final long maxTransfer;
	public final TagKey<Item> sourceIngot;

	SubtypeFluidPipe(long maxTransfer, TagKey<Item> sourceIngot) {
		this.maxTransfer = maxTransfer;
		this.sourceIngot = sourceIngot;
	}

	@Override
	public String tag() {
		return "pipe" + name();
	}

	@Override
	public String forgeTag() {
		return tag();
	}

	@Override
	public boolean isItem() {
		return false;
	}
}
