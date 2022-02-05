package electrodynamics.common.item;

import electrodynamics.api.References;
import electrodynamics.common.item.subtype.SubtypeDrillHead;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ItemDrillHead extends Item {

	public SubtypeDrillHead head;
	
	public ItemDrillHead(SubtypeDrillHead head) {
		super(new Item.Properties().defaultDurability(head.durability).durability(head.durability).rarity(head.isUnbreakable ? Rarity.UNCOMMON : Rarity.COMMON).tab(References.CORETAB));
		this.head = head;
	}

}
