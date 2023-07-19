package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerGasVent;
import electrodynamics.prefab.screen.GenericScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenGasVent extends GenericScreen<ContainerGasVent> {

	public ScreenGasVent(ContainerGasVent container, Inventory inv, Component titleIn) {
		super(container, inv, titleIn);
	}

}
