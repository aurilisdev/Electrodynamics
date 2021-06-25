package electrodynamics.common.item;

import electrodynamics.api.References;
import electrodynamics.common.item.subtype.SubtypeCanister;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;

public class ItemCanister extends BucketItem{

	public ItemCanister(SubtypeCanister canister) {
		super(() -> canister.getFluid(),new Item.Properties().group(References.CORETAB));
	}

}
