package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerWireMillDouble;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenWireMillDouble extends ScreenProcessorO2ODouble<ContainerWireMillDouble> {

    public ScreenWireMillDouble(ContainerWireMillDouble container, Inventory playerInventory, Component title) {
	super(container, playerInventory, title);
    }
}
