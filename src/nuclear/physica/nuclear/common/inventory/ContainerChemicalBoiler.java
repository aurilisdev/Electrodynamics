package physica.nuclear.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import physica.library.inventory.ContainerBase;
import physica.library.inventory.slot.SlotBase;
import physica.library.inventory.slot.SlotEnergyHolder;
import physica.nuclear.common.tile.TileChemicalBoiler;

public class ContainerChemicalBoiler extends ContainerBase<TileChemicalBoiler> {

	public ContainerChemicalBoiler(EntityPlayer player, TileChemicalBoiler tile) {
		super(player, tile);
		addSlotToContainer(new SlotEnergyHolder(tile, TileChemicalBoiler.SLOT_ENERGY, 68, 46));
		addSlotToContainer(new SlotBase(tile, TileChemicalBoiler.SLOT_INPUT1, 68, 26));
		addSlotToContainer(new SlotBase(tile, TileChemicalBoiler.SLOT_INPUT2, 88, 26));
		setSlotCount(TileChemicalBoiler.SLOT_INPUT2 + 1);
		addDefaultPlayerInventory(player, 10);
	}
}
