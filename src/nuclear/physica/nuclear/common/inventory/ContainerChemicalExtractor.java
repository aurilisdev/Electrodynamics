package physica.nuclear.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import physica.library.inventory.ContainerBase;
import physica.library.inventory.slot.SlotBase;
import physica.library.inventory.slot.SlotEnergyHolder;
import physica.nuclear.common.tile.TileChemicalExtractor;

public class ContainerChemicalExtractor extends ContainerBase<TileChemicalExtractor> {

	public ContainerChemicalExtractor(EntityPlayer player, TileChemicalExtractor tile) {
		super(player, tile);
		addSlotToContainer(new SlotEnergyHolder(tile, TileChemicalExtractor.SLOT_ENERGY, 131, 36));
		addSlotToContainer(new SlotBase(tile, TileChemicalExtractor.SLOT_INPUT, 81, 36));
		addSlotToContainer(new SlotBase(tile, TileChemicalExtractor.SLOT_OUTPUT, 101, 36));
		setSlotCount(TileChemicalExtractor.SLOT_OUTPUT + 1);
		addDefaultPlayerInventory(player, 0);
	}
}
