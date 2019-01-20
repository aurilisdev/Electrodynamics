package physica.nuclear.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import physica.library.inventory.ContainerBase;
import physica.library.inventory.slot.SlotBase;
import physica.nuclear.common.tile.TileParticleAccelerator;

public class ContainerParticleAccelerator extends ContainerBase<TileParticleAccelerator> {

	public ContainerParticleAccelerator(EntityPlayer player, TileParticleAccelerator tile) {
		super(player, tile);
		addSlotToContainer(new SlotBase(tile, TileParticleAccelerator.SLOT_INPUTMATTER, 117, 24));
		addSlotToContainer(new SlotBase(tile, TileParticleAccelerator.SLOT_INPUTCELLS, 117, 51));
		addSlotToContainer(new SlotBase(tile, TileParticleAccelerator.SLOT_OUTPUT, 137, 51));
		setSlotCount(TileParticleAccelerator.SLOT_OUTPUT + 1);
		addDefaultPlayerInventory(player, 0);
	}
}
