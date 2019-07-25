package physica.core.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.SlotFurnace;
import physica.core.common.tile.TileElectricFurnace;
import physica.library.inventory.ContainerBase;
import physica.library.inventory.slot.SlotBase;
import physica.library.inventory.slot.SlotEnergyHolder;

public class ContainerElectricFurnace extends ContainerBase<TileElectricFurnace> {

	public ContainerElectricFurnace(EntityPlayer player, TileElectricFurnace node) {
		super(player, node);
		addSlotToContainer(new SlotEnergyHolder(node, TileElectricFurnace.SLOT_ENERGY, 55, 49));
		addSlotToContainer(new SlotBase(node, TileElectricFurnace.SLOT_INPUT, 55, 25));
		addSlotToContainer(new SlotFurnace(player, node, TileElectricFurnace.SLOT_OUTPUT, 108, 25));
		setSlotCount(TileElectricFurnace.SLOT_OUTPUT + 1);
		addDefaultPlayerInventory(player, 0);
	}

}
