package physica.nuclear.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.SlotFurnace;
import physica.library.inventory.ContainerBase;
import physica.library.inventory.slot.SlotEnergyHolder;
import physica.nuclear.common.tile.TileGasCentrifuge;

public class ContainerCentrifuge extends ContainerBase<TileGasCentrifuge> {

	public ContainerCentrifuge(EntityPlayer player, TileGasCentrifuge tile) {
		super(player, tile);
		addSlotToContainer(new SlotEnergyHolder(tile, TileGasCentrifuge.SLOT_ENERGY, 131, 36));
		addSlotToContainer(new SlotFurnace(player, tile, TileGasCentrifuge.SLOT_OUTPUT1, 81, 36));
		addSlotToContainer(new SlotFurnace(player, tile, TileGasCentrifuge.SLOT_OUTPUT2, 101, 36));
		setSlotCount(TileGasCentrifuge.SLOT_OUTPUT2 + 1);
		addDefaultPlayerInventory(player, 10);
	}
}
