package electrodynamics.common.item;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.SoundRegister;
import electrodynamics.api.References;
import electrodynamics.common.item.subtype.SubtypeCeramic;
import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.nbt.CompoundTag;
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
		if (!worldIn.isClientSide) {
			if (ItemStack.isSame(playerIn.getItemInHand(handIn), new ItemStack(ElectrodynamicsItems.SUBTYPEITEMREGISTER_MAPPINGS.get(SubtypeCeramic.plate).get()))) {
				List<ItemStack> armorPieces = new ArrayList<>();
				playerIn.getArmorSlots().forEach(armorPieces::add);

				ItemStack chestplate = armorPieces.get(2);
				if (ItemStack.isSameIgnoreDurability(chestplate, new ItemStack(ElectrodynamicsItems.ITEM_COMPOSITECHESTPLATE.get())) || ItemStack.isSameIgnoreDurability(chestplate, new ItemStack(ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get()))) {
					CompoundTag tag = chestplate.getOrCreateTag();
					int stored = tag.getInt(NBTUtils.PLATES);
					if (stored < 2) {
						playerIn.playNotifySound(SoundRegister.SOUND_CERAMICPLATEADDED.get(), SoundSource.PLAYERS, 1, 1);
						tag.putInt(NBTUtils.PLATES, stored + 1);
						playerIn.getItemInHand(handIn).shrink(1);
					}

				}
			}
		}
		return InteractionResultHolder.pass(playerIn.getItemInHand(handIn));
	}

}
