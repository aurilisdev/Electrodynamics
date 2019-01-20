package physica.forcefield.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import physica.forcefield.common.tile.TileCoercionDriver;
import physica.library.inventory.ContainerBase;
import physica.library.inventory.slot.SlotBase;

public class ContainerCoercionDriver extends ContainerBase<TileCoercionDriver> {

	public ContainerCoercionDriver(EntityPlayer player, TileCoercionDriver tile) {
		super(player, tile);
		addSlotToContainer(new SlotBase(host, TileCoercionDriver.SLOT_CARD, 9, 41).setToolTip("Frequency"));
		addSlotToContainer(new SlotBase(host, TileCoercionDriver.SLOT_MODULE1, 154, 67));
		addSlotToContainer(new SlotBase(host, TileCoercionDriver.SLOT_MODULE2, 154, 87));
		addSlotToContainer(new SlotBase(host, TileCoercionDriver.SLOT_MODULE3, 154, 47));
		setSlotCount(TileCoercionDriver.SLOT_MODULE3 + 1);
		addDefaultPlayerInventory(player, 51);
	}
}
