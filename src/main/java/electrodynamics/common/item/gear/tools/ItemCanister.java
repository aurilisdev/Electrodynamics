package electrodynamics.common.item.gear.tools;

import electrodynamics.api.References;
import electrodynamics.common.item.subtype.SubtypeCanister;
import electrodynamics.common.item.subtype.SubtypeLeadCanister;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;

public class ItemCanister extends BucketItem {

    public ItemCanister(SubtypeCanister canister) {
	super(() -> canister.getFluid(), new Item.Properties().maxStackSize(4).group(References.CORETAB));
    }

    public ItemCanister(SubtypeLeadCanister canister) {
	super(() -> canister.getFluid(), new Item.Properties().maxStackSize(2).group(References.CORETAB));
    }

}
