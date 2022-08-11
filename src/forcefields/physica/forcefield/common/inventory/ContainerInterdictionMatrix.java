package physica.forcefield.common.inventory;

import java.awt.Color;

import net.minecraft.entity.player.EntityPlayer;
import physica.forcefield.common.tile.TileInterdictionMatrix;
import physica.library.inventory.ContainerBase;
import physica.library.inventory.slot.SlotBase;

public class ContainerInterdictionMatrix extends ContainerBase<TileInterdictionMatrix> {

	public ContainerInterdictionMatrix(EntityPlayer player, TileInterdictionMatrix tile) {
		super(player, tile);
		addSlotToContainer(new SlotBase(host, TileInterdictionMatrix.SLOT_FREQUENCY, 87, 89));
		for (int var3 = 0; var3 < 2; var3++) {
			for (int var4 = 0; var4 < 4; var4++) {
				addSlotToContainer(new SlotBase(host, var4 + var3 * 4 + 1, 99 + var4 * 18, 31 + var3 * 18));
			}
		}
		for (int var4 = 0; var4 < 9; var4++) {
			addSlotToContainer(new SlotBase(host, var4 + TileInterdictionMatrix.SLOT_STARTBANLIST, 9 + var4 * 18, 69).setBaseColor(tile.isBlackList ? Color.DARK_GRAY.darker() : Color.GRAY).setEdgeColor(tile.isBlackList ? Color.DARK_GRAY.darker() : Color.GRAY));
		}
		setSlotCount(TileInterdictionMatrix.SLOT_ENDBANLIST + 1);
		addDefaultPlayerInventory(player, 51);
	}
}
