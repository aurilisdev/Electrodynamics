package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerMineralGrinderDouble;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenMineralGrinderDouble extends ScreenProcessorO2ODouble<ContainerMineralGrinderDouble> {

    public ScreenMineralGrinderDouble(ContainerMineralGrinderDouble container, Inventory playerInventory,
	    Component title) {
	super(container, playerInventory, title);
    }
}
