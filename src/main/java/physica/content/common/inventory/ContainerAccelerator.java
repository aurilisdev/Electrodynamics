package physica.content.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import physica.api.lib.inventory.ContainerBase;
import physica.api.lib.inventory.slot.SlotBase;
import physica.content.common.tile.TileAccelerator;

public class ContainerAccelerator extends ContainerBase<TileAccelerator> {
	public ContainerAccelerator(EntityPlayer player, TileAccelerator tile) {
		super(player, tile);
		addSlotToContainer(new SlotBase(tile, TileAccelerator.SLOT_INPUTMATTER, 117, 24));
		addSlotToContainer(new SlotBase(tile, TileAccelerator.SLOT_INPUTCELLS, 117, 51));
		addSlotToContainer(new SlotBase(tile, TileAccelerator.SLOT_OUTPUT, 137, 51));
		setSlotCount(TileAccelerator.SLOT_OUTPUT);
		addDefaultPlayerInventory(player, 0);
	}
}
