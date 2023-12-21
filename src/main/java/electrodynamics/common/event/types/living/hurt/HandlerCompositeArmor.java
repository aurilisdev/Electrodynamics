package electrodynamics.common.event.types.living.hurt;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class HandlerCompositeArmor extends AbstractLivingHurtHandler {

	private static final float LETHAL_DAMAGE_AMOUNT = 18.0f;

	private static final ItemStack[] COMPOSITE_ARMOR = new ItemStack[] { new ItemStack(ElectrodynamicsItems.ITEM_COMPOSITEHELMET.get()), new ItemStack(ElectrodynamicsItems.ITEM_COMPOSITECHESTPLATE.get()), new ItemStack(ElectrodynamicsItems.ITEM_COMPOSITELEGGINGS.get()), new ItemStack(ElectrodynamicsItems.ITEM_COMPOSITEBOOTS.get()) };
	private static final ItemStack[] COMBAT_ARMOR = new ItemStack[] { new ItemStack(ElectrodynamicsItems.ITEM_COMBATHELMET.get()), new ItemStack(ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get()), new ItemStack(ElectrodynamicsItems.ITEM_COMBATLEGGINGS.get()), new ItemStack(ElectrodynamicsItems.ITEM_COMBATBOOTS.get()) };

	@Override
	public void handle(LivingHurtEvent event) {
		if (event.getSource().isFall()) {
			return;
		}
		List<ItemStack> armorPieces = new ArrayList<>();
		event.getEntity().getArmorSlots().forEach(armorPieces::add);

		if (compareArmor(armorPieces, COMPOSITE_ARMOR) || compareArmor(armorPieces, COMBAT_ARMOR)) {
			ItemStack stack = armorPieces.get(2);
			CompoundTag tag = stack.getOrCreateTag();
			int stored = tag.getInt(NBTUtils.PLATES);
			if (event.getAmount() >= LETHAL_DAMAGE_AMOUNT && stored > 0) {
				event.setAmount((float) Math.sqrt(event.getAmount()));
				tag.putInt(NBTUtils.PLATES, stored - 1);
				event.getEntity().getCommandSenderWorld().playSound(null, event.getEntity().blockPosition(), ElectrodynamicsSounds.SOUND_CERAMICPLATEBREAKING.get(), SoundSource.PLAYERS, 1, 1);
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
