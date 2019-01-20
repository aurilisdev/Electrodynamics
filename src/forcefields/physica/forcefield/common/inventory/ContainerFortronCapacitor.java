package physica.forcefield.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import physica.forcefield.common.tile.TileFortronCapacitor;
import physica.library.inventory.ContainerBase;
import physica.library.inventory.slot.SlotBase;

public class ContainerFortronCapacitor extends ContainerBase<TileFortronCapacitor> {

	public ContainerFortronCapacitor(EntityPlayer player, TileFortronCapacitor tile) {
		super(player, tile);
		addSlotToContainer(new SlotBase(host, TileFortronCapacitor.SLOT_CARD, 9, 41).setToolTip("Frequency"));
		addSlotToContainer(new SlotBase(host, TileFortronCapacitor.SLOT_MODULE1, 154, 67));
		addSlotToContainer(new SlotBase(host, TileFortronCapacitor.SLOT_MODULE2, 154, 87));
		addSlotToContainer(new SlotBase(host, TileFortronCapacitor.SLOT_MODULE3, 154, 47));
		setSlotCount(TileFortronCapacitor.SLOT_MODULE3 + 1);
		addDefaultPlayerInventory(player, 51);
	}
}
