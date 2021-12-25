package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerFluidVoid;
import electrodynamics.prefab.screen.GenericScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenFluidVoid extends GenericScreen<ContainerFluidVoid> {

	public ScreenFluidVoid(ContainerFluidVoid container, Inventory inv, Component titleIn) {
		super(container, inv, titleIn);
	}

}
