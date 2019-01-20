package physica.forcefield.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import physica.forcefield.common.tile.TileBiometricIdentifier;
import physica.library.inventory.ContainerBase;
import physica.library.inventory.slot.SlotBase;

public class ContainerBiometricIndentifier extends ContainerBase<TileBiometricIdentifier> {

	public ContainerBiometricIndentifier(EntityPlayer player, TileBiometricIdentifier tile) {
		super(player, tile);
		addSlotToContainer(new SlotBase(host, 0, 8, 46 + 40).setToolTip("Configure Card"));
		for (int var4 = 0; var4 < 9; var4++) {
			addSlotToContainer(new SlotBase(host, 1 + var4, 8 + var4 * 18, 111 + 30));
		}
		addSlotToContainer(new SlotBase(host, 10, 8, 66 + 40).setToolTip("Master Card"));
		setSlotCount(11);
		addDefaultPlayerInventory(player, 81);
	}
}
