package electrodynamics.common.item;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.References;
import electrodynamics.common.item.subtype.SubtypeCeramic;
import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemCeramic extends Item {
	public SubtypeCeramic subtype;

	public ItemCeramic(SubtypeCeramic subtype) {
		super(new Item.Properties().stacksTo(64).tab(References.CORETAB));
		this.subtype = subtype;
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (!worldIn.isClientSide) {
			if (ItemStack.isSame(playerIn.getItemInHand(handIn), new ItemStack(ElectrodynamicsItems.getItem(SubtypeCeramic.plate)))) {
				List<ItemStack> armorPieces = new ArrayList<>();
				playerIn.getArmorSlots().forEach(armorPieces::add);

				ItemStack chestplate = armorPieces.get(2);
				if (ItemStack.isSameIgnoreDurability(chestplate, new ItemStack(ElectrodynamicsItems.ITEM_COMPOSITECHESTPLATE.get()))) {
					CompoundNBT tag = chestplate.getOrCreateTag();
					int stored = tag.getInt(NBTUtils.PLATES);
					if (stored < 2) {
						playerIn.playNotifySound(ElectrodynamicsSounds.SOUND_CERAMICPLATEADDED.get(), SoundCategory.PLAYERS, 1, 1);
						tag.putInt(NBTUtils.PLATES, stored + 1);
						playerIn.getItemInHand(handIn).shrink(1);
					}

				}
			}
		}
		return ActionResult.pass(playerIn.getItemInHand(handIn));
	}

}