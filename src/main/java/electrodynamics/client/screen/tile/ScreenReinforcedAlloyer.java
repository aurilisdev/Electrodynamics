package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerReinforcedAlloyer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenReinforcedAlloyer extends ScreenProcessorDO2O<ContainerReinforcedAlloyer> {

    public ScreenReinforcedAlloyer(ContainerReinforcedAlloyer container, Inventory playerInventory, Component title) {
	super(container, playerInventory, title);
    }
}
