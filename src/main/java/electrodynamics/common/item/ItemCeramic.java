/**
 * This removes the need for an event listener for the 
 * ceramic plate right click
 */
package electrodynamics.common.item;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.References;
import electrodynamics.api.capability.compositearmor.CapabilityCeramicPlate;
import electrodynamics.common.item.subtype.SubtypeCeramic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemCeramic extends Item {

    public ItemCeramic(SubtypeCeramic subtype) {
	super(new Item.Properties().maxStackSize(64).group(References.CORETAB));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

	if (ItemStack.areItemStackTagsEqual(playerIn.getHeldItem(handIn),
		new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeCeramic.plate)))) {
	    List<ItemStack> armorPieces = new ArrayList<>();
	    playerIn.getArmorInventoryList().forEach(armorPieces::add);

	    ItemStack chestplate = armorPieces.get(2);
	    if (ItemStack.areItemsEqualIgnoreDurability(chestplate, new ItemStack(DeferredRegisters.COMPOSITE_CHESTPLATE.get()))) {
		chestplate.getCapability(CapabilityCeramicPlate.CERAMIC_PLATE_HOLDER_CAPABILITY).ifPresent(h -> {
		    if (h.getPlateCount() < 2) {
			playerIn.playSound(SoundRegister.SOUND_CERAMICPLATEADDED.get(), SoundCategory.PLAYERS, 1, 1);
			h.increasePlateCount(1);
			playerIn.getHeldItem(handIn).shrink(1);
		    }
		});
	    }
	}

	return ActionResult.resultPass(playerIn.getHeldItem(handIn));
    }

}
