package electrodynamics.common.item;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.References;
import electrodynamics.api.capability.ceramicplate.CapabilityCeramicPlateStorage;
import electrodynamics.common.item.subtype.SubtypeCeramic;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemCeramic extends Item {
	public SubtypeCeramic subtype;

	public ItemCeramic(SubtypeCeramic subtype) {
		super(new Item.Properties().stacksTo(64).tab(References.CORETAB));
		this.subtype = subtype;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		if (ItemStack.tagMatches(playerIn.getItemInHand(handIn), new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeCeramic.plate)))) {
			List<ItemStack> armorPieces = new ArrayList<>();
			playerIn.getArmorSlots().forEach(armorPieces::add);

			ItemStack chestplate = armorPieces.get(2);
			if (ItemStack.isSameIgnoreDurability(chestplate, new ItemStack(DeferredRegisters.COMPOSITE_CHESTPLATE.get()))) {
				chestplate.getCapability(CapabilityCeramicPlateStorage.CERAMIC_PLATE_HOLDER_CAPABILITY).ifPresent(h -> {
					if (h.getPlateCount() < 2) {
						playerIn.playNotifySound(SoundRegister.SOUND_CERAMICPLATEADDED.get(), SoundSource.PLAYERS, 1, 1);
						h.increasePlateCount(1);
						playerIn.getItemInHand(handIn).shrink(1);
					}
				});
			}
		}

		return InteractionResultHolder.pass(playerIn.getItemInHand(handIn));
	}

}
