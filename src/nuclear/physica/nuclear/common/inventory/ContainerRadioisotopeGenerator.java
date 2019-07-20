package physica.nuclear.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import physica.core.common.tile.TileCoalGenerator;
import physica.library.inventory.ContainerBase;
import physica.library.inventory.slot.SlotBase;
import physica.nuclear.common.tile.TileRadioisotopeGenerator;

public class ContainerRadioisotopeGenerator extends ContainerBase<TileRadioisotopeGenerator> {

	public ContainerRadioisotopeGenerator(EntityPlayer player, TileRadioisotopeGenerator node) {
		super(player, node);
		addSlotToContainer(new SlotBase(node, TileCoalGenerator.SLOT_INPUT, 33, 34));
		setSlotCount(TileRadioisotopeGenerator.SLOT_INPUT + 1);
		addDefaultPlayerInventory(player, 0);
	}

}
