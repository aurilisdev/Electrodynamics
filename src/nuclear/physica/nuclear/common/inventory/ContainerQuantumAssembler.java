package physica.nuclear.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import physica.library.client.gui.GuiContainerBase;
import physica.library.inventory.ContainerBase;
import physica.library.inventory.slot.SlotBase;
import physica.nuclear.common.tile.TileQuantumAssembler;

public class ContainerQuantumAssembler extends ContainerBase<TileQuantumAssembler> {

	public ContainerQuantumAssembler(EntityPlayer player, TileQuantumAssembler tile) {
		super(player, tile);
		addSlotToContainer(new SlotBase(tile, 0, 80, 40));
		addSlotToContainer(new SlotBase(tile, 1, 53, 56));
		addSlotToContainer(new SlotBase(tile, 2, 107, 56));
		addSlotToContainer(new SlotBase(tile, 3, 53, 88));
		addSlotToContainer(new SlotBase(tile, 4, 107, 88));
		addSlotToContainer(new SlotBase(tile, 5, 80, 103));
		addSlotToContainer(new SlotBase(tile, TileQuantumAssembler.SLOT_INPUT, 80, 72));
		addSlotToContainer(new SlotBase(tile, TileQuantumAssembler.SLOT_OUTPUT, 20, 72));
		setSlotCount(TileQuantumAssembler.SLOT_OUTPUT + 1);
		addDefaultPlayerInventory(player, 230 - GuiContainerBase.defaultYSize);
	}
}
