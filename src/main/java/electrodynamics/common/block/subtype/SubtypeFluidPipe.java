package electrodynamics.common.block.subtype;

import electrodynamics.api.ISubtype;
import electrodynamics.common.tags.ElectrodynamicsTags;
import net.minecraft.item.Item;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

public enum SubtypeFluidPipe implements ISubtype {
	steel(10000, ElectrodynamicsTags.Items.INGOT_STEEL);

	public final long maxTransfer;
	public final IOptionalNamedTag<Item> sourceIngot;

	SubtypeFluidPipe(long maxTransfer, IOptionalNamedTag<Item> sourceIngot) {
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
