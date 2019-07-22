package physica.core.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import physica.core.common.tile.TileCoalGenerator;
import physica.library.inventory.ContainerBase;
import physica.library.inventory.slot.SlotBase;

public class ContainerCoalGenerator extends ContainerBase<TileCoalGenerator> {

	public ContainerCoalGenerator(EntityPlayer player, TileCoalGenerator node) {
		super(player, node);
		addSlotToContainer(new SlotBase(node, TileCoalGenerator.SLOT_INPUT, 33, 34));
		setSlotCount(TileCoalGenerator.SLOT_INPUT + 1);
		addDefaultPlayerInventory(player, 0);
	}

}
