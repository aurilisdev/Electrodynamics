package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerMineralGrinder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenMineralGrinder extends ScreenProcessorO2O<ContainerMineralGrinder> {

    public ScreenMineralGrinder(ContainerMineralGrinder container, Inventory playerInventory, Component title) {
	super(container, playerInventory, title);
    }
}
