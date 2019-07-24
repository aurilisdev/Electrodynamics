package physica.core.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import physica.core.common.tile.TileBatteryBox;
import physica.library.inventory.ContainerBase;
import physica.library.inventory.slot.SlotEnergyHolder;

public class ContainerBatteryBox extends ContainerBase<TileBatteryBox> {

	public ContainerBatteryBox(EntityPlayer player, TileBatteryBox node) {
		super(player, node);
		addSlotToContainer(new SlotEnergyHolder(node, TileBatteryBox.SLOT_INPUT, 45, 24));
		addSlotToContainer(new SlotEnergyHolder(node, TileBatteryBox.SLOT_OUTPUT, 45, 48));
		setSlotCount(TileBatteryBox.SLOT_OUTPUT + 1);
		addDefaultPlayerInventory(player, 0);
	}

}
