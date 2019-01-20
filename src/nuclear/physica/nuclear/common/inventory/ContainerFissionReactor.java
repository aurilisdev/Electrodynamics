package physica.nuclear.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import physica.library.inventory.ContainerBase;
import physica.library.inventory.slot.SlotBase;
import physica.nuclear.common.tile.TileFissionReactor;

public class ContainerFissionReactor extends ContainerBase<TileFissionReactor> {

	public ContainerFissionReactor(EntityPlayer player, TileFissionReactor tile) {
		super(player, tile);
		addSlotToContainer(new SlotBase(tile, TileFissionReactor.SLOT_INPUT, 80, 36));
		setSlotCount(TileFissionReactor.SLOT_INPUT + 1);
		addDefaultPlayerInventory(player, 0);
	}
}
