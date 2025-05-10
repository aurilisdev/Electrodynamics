package electrodynamics.common.event.types.living.hurt;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import voltaic.common.event.type.AbstractLivingDamageHandler;
import voltaic.prefab.utilities.NBTUtils;

public class HandlerCompositeArmor extends AbstractLivingDamageHandler {

	private static final float LETHAL_DAMAGE_AMOUNT = 18.0f;

	private static final ItemStack[] COMPOSITE_ARMOR = new ItemStack[] { new ItemStack(ElectrodynamicsItems.ITEM_COMPOSITEHELMET.get()), new ItemStack(ElectrodynamicsItems.ITEM_COMPOSITECHESTPLATE.get()), new ItemStack(ElectrodynamicsItems.ITEM_COMPOSITELEGGINGS.get()), new ItemStack(ElectrodynamicsItems.ITEM_COMPOSITEBOOTS.get()) };
	@Override
	public void handle(LivingDamageEvent event) {
		if (event.getSource() == DamageSource.FALL) {
			return;
		}
		List<ItemStack> armorPieces = new ArrayList<>();
		event.getEntity().getArmorSlots().forEach(armorPieces::add);

		if (compareArmor(armorPieces, COMPOSITE_ARMOR)) {
			ItemStack stack = armorPieces.get(2);
			CompoundNBT tag = stack.getOrCreateTag();
			int stored = tag.getInt(NBTUtils.PLATES);
			if (event.getAmount() >= LETHAL_DAMAGE_AMOUNT && stored > 0) {
				event.setAmount((float) Math.sqrt(event.getAmount()));
				tag.putInt(NBTUtils.PLATES, stored - 1);
				event.getEntity().getCommandSenderWorld().playSound(null, event.getEntity().blockPosition(), ElectrodynamicsSounds.SOUND_CERAMICPLATEBREAKING.get(), SoundCategory.PLAYERS, 1, 1);
			}
		}

	}

	private boolean compareArmor(List<ItemStack> set1, ItemStack[] set2) {
		if (set1.size() >= 3) {
			return set1.get(0).getItem() == set2[3].getItem() && set1.get(1).getItem() == set2[2].getItem() && set1.get(2).getItem() == set2[1].getItem() && set1.get(3).getItem() == set2[0].getItem();
		}
		return false;
	}

}
