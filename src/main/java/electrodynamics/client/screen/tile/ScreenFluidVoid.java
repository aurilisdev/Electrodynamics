package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerFluidVoid;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import voltaic.prefab.screen.GenericScreen;

public class ScreenFluidVoid extends GenericScreen<ContainerFluidVoid> {

    public ScreenFluidVoid(ContainerFluidVoid container, Inventory inv, Component titleIn) {
	super(container, inv, titleIn);
    }

}
