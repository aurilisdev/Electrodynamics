package physica.core.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.SlotFurnace;
import physica.core.common.tile.TileCircuitPress;
import physica.library.inventory.ContainerBase;
import physica.library.inventory.slot.SlotBase;

public class ContainerCircuitPress extends ContainerBase<TileCircuitPress> {

	public ContainerCircuitPress(EntityPlayer player, TileCircuitPress node) {
		super(player, node);
		addSlotToContainer(new SlotBase(node, TileCircuitPress.SLOT_INPUT, 56, 17));
		addSlotToContainer(new SlotBase(node, TileCircuitPress.SLOT_INPUT2, 56, 53));
		addSlotToContainer(new SlotFurnace(player, node, TileCircuitPress.SLOT_OUTPUT, 116, 35));
		setSlotCount(TileCircuitPress.SLOT_OUTPUT + 1);
		addDefaultPlayerInventory(player, 0);
	}

}
