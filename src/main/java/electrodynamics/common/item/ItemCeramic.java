package electrodynamics.common.item;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.common.item.subtype.SubtypeCeramic;
import electrodynamics.registers.ElectrodynamicsCreativeTabs;
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
import voltaic.common.item.ItemVoltaic;
import voltaic.prefab.utilities.ItemUtils;
import voltaic.prefab.utilities.NBTUtils;

public class ItemCeramic extends ItemVoltaic {
	public SubtypeCeramic subtype;

	public ItemCeramic(SubtypeCeramic subtype) {
		super(new Item.Properties().stacksTo(64), () -> ElectrodynamicsCreativeTabs.MAIN);
		this.subtype = subtype;
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

		ItemStack handStack = player.getItemInHand(hand);

		if (world.isClientSide || !ItemUtils.testItems(handStack.getItem(), ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.plate))) {
			return ActionResult.pass(player.getItemInHand(hand));
		}

		List<ItemStack> armorPieces = new ArrayList<>();
		player.getArmorSlots().forEach(armorPieces::add);

		ItemStack chestplate = armorPieces.get(2);
		if (chestplate.getItem() == ElectrodynamicsItems.ITEM_COMPOSITECHESTPLATE.get()) {
			CompoundNBT tag = chestplate.getOrCreateTag();
			int stored = tag.getInt(NBTUtils.PLATES);
			if (stored < 2) {
				world.playSound(null, player.blockPosition(), ElectrodynamicsSounds.SOUND_CERAMICPLATEADDED.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
				tag.putInt(NBTUtils.PLATES, stored + 1);
				if (!player.isCreative()) {
					handStack.shrink(1);
					player.setItemInHand(hand, handStack);
				}
			}

		}
		return ActionResult.pass(player.getItemInHand(hand));
	}

}
