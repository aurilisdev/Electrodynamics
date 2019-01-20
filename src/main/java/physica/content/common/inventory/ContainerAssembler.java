package physica.content.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import physica.api.lib.inventory.ContainerBase;
import physica.api.lib.inventory.GuiContainerBase;
import physica.api.lib.inventory.slot.SlotBase;
import physica.content.common.tile.TileAssembler;

public class ContainerAssembler extends ContainerBase<TileAssembler> {
	public ContainerAssembler(EntityPlayer player, TileAssembler tile) {
		super(player, tile);
		addSlotToContainer(new SlotBase(tile, 0, 80, 40));
		addSlotToContainer(new SlotBase(tile, 1, 53, 56));
		addSlotToContainer(new SlotBase(tile, 2, 107, 56));
		addSlotToContainer(new SlotBase(tile, 3, 53, 88));
		addSlotToContainer(new SlotBase(tile, 4, 107, 88));
		addSlotToContainer(new SlotBase(tile, 5, 80, 103));
		addSlotToContainer(new SlotBase(tile, TileAssembler.SLOT_OUTPUT, 80, 72));
		setSlotCount(TileAssembler.SLOT_OUTPUT + 1);
		addDefaultPlayerInventory(player, 230 - GuiContainerBase.defaultYSize);
	}
}
	