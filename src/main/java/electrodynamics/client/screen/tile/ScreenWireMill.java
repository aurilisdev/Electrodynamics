package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerWireMill;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenWireMill extends ScreenProcessorO2O<ContainerWireMill> {

    public ScreenWireMill(ContainerWireMill container, Inventory playerInventory, Component title) {
	super(container, playerInventory, title);
    }
}
