package electrodynamics.common.item.gear.tools;

import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.item.subtype.SubtypeCanister;
import electrodynamics.common.item.subtype.SubtypeLeadCanister;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemCanister extends BucketItem {

    public ItemCanister(SubtypeCanister canister) {
	super(() -> canister.getFluid(), new Item.Properties().maxStackSize(4).group(References.CORETAB)
		.containerItem(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeCanister.empty)));
    }

    public ItemCanister(SubtypeLeadCanister canister) {
	super(() -> canister.getFluid(), new Item.Properties().maxStackSize(2).group(References.CORETAB)
		.containerItem(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeLeadCanister.empty)));
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
	ItemCanister canister = (ItemCanister) stack.getItem();
	if (!canister.getFluid().isEquivalentTo(Fluids.EMPTY)) {
	    tooltip.add(new TranslationTextComponent("tooltip.electrodynamics.itemcanister"));
	}
	super.addInformation(stack, worldIn, tooltip, flagIn);
    }

}
