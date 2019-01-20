package physica.core.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.SlotFurnace;
import physica.core.common.tile.TileBlastFurnace;
import physica.library.inventory.ContainerBase;
import physica.library.inventory.slot.SlotBase;

public class ContainerBlastFurnace extends ContainerBase<TileBlastFurnace> {

	public ContainerBlastFurnace(EntityPlayer player, TileBlastFurnace node) {
		super(player, node);
		addSlotToContainer(new SlotBase(node, TileBlastFurnace.SLOT_INPUT, 56, 17));
		addSlotToContainer(new SlotBase(node, TileBlastFurnace.SLOT_INPUTFUEL, 56, 53));
		addSlotToContainer(new SlotFurnace(player, node, TileBlastFurnace.SLOT_OUTPUT, 116, 35));
		setSlotCount(TileBlastFurnace.SLOT_OUTPUT + 1);
		addDefaultPlayerInventory(player, 0);
	}

}
