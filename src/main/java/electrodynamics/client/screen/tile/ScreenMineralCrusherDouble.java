package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerMineralCrusherDouble;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenMineralCrusherDouble extends ScreenProcessorO2ODouble<ContainerMineralCrusherDouble> {

    public ScreenMineralCrusherDouble(ContainerMineralCrusherDouble container, Inventory playerInventory,
	    Component title) {
	super(container, playerInventory, title);
    }
}
