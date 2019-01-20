package physica.nuclear.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import physica.library.inventory.ContainerBase;
import physica.library.inventory.slot.SlotBase;
import physica.nuclear.common.tile.TileNeutronCaptureChamber;

public class ContainerNeutronCaptureChamber extends ContainerBase<TileNeutronCaptureChamber> {

	public ContainerNeutronCaptureChamber(EntityPlayer player, TileNeutronCaptureChamber tile) {
		super(player, tile);
		addSlotToContainer(new SlotBase(tile, TileNeutronCaptureChamber.SLOT_INPUT, 38, 36));
		addSlotToContainer(new SlotBase(tile, TileNeutronCaptureChamber.SLOT_OUTPUT, 118, 36));
		setSlotCount(TileNeutronCaptureChamber.SLOT_OUTPUT + 1);
		addDefaultPlayerInventory(player, 0);
	}
}
