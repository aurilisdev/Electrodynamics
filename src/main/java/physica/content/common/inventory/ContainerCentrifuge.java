package physica.content.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.SlotFurnace;
import physica.api.lib.inventory.ContainerBase;
import physica.api.lib.inventory.slot.SlotEnergyHolder;
import physica.content.common.tile.TileCentrifuge;

public class ContainerCentrifuge extends ContainerBase<TileCentrifuge> {
	public ContainerCentrifuge(EntityPlayer player, TileCentrifuge tile) {
		super(player, tile);
		addSlotToContainer(new SlotEnergyHolder(tile, TileCentrifuge.SLOT_ENERGY, 131, 36));
		addSlotToContainer(new SlotFurnace(player, tile, TileCentrifuge.SLOT_OUTPUT1, 81, 36));
		addSlotToContainer(new SlotFurnace(player, tile, TileCentrifuge.SLOT_OUTPUT2, 101, 36));
		setSlotCount(TileCentrifuge.SLOT_OUTPUT2 + 1);
		addDefaultPlayerInventory(player, 0);
	}
}
