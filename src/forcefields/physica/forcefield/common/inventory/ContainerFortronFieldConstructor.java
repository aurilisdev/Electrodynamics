package physica.forcefield.common.inventory;

import java.awt.Color;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import physica.forcefield.common.tile.TileFortronFieldConstructor;
import physica.library.inventory.ContainerBase;
import physica.library.inventory.slot.SlotBase;

public class ContainerFortronFieldConstructor extends ContainerBase<TileFortronFieldConstructor> {

	public ContainerFortronFieldConstructor(EntityPlayer player, TileFortronFieldConstructor tile) {
		super(player, tile);
		addSlotToContainer(new SlotBase(host, TileFortronFieldConstructor.SLOT_FREQUENCY, 133, 120).setToolTip("Frequency"));

		addSlotToContainer(new SlotBase(host, TileFortronFieldConstructor.SLOT_TYPE, 118, 45).setEdgeColor(Color.DARK_GRAY).setBaseColor(Color.DARK_GRAY).setToolTip("Shape"));

		int slotIndex = 0;
		for (int xSlot = 0; xSlot < 4; xSlot++) {
			for (int ySlot = 0; ySlot < 4; ySlot++) {
				if ((xSlot != 1 || ySlot != 1) && (xSlot != 2 || ySlot != 2) && (xSlot != 1 || ySlot != 2) && (xSlot != 2 || ySlot != 1)) {
					String toolTip = "";
					for (List<Integer> slots : TileFortronFieldConstructor.SLOT_MAP.keySet()) {
						if (slots.contains(slotIndex)) {
							toolTip = TileFortronFieldConstructor.SLOT_MAP.get(slots);
						}
					}
					addSlotToContainer(new SlotBase(host, slotIndex, 91 + 18 * xSlot, 18 + 18 * ySlot).setToolTip(toolTip));
					slotIndex++;
				}
			}
		}
		for (int xSlot = 0; xSlot < 3; xSlot++) {
			for (int ySlot = 0; ySlot < 2; ySlot++) {
				addSlotToContainer(new SlotBase(host, slotIndex, 19 + 18 * xSlot, 48 + 18 * ySlot));
				slotIndex++;
			}
		}
		setSlotCount(TileFortronFieldConstructor.SLOT_TYPE + 1);
		addDefaultPlayerInventory(player, 71);
	}
}
