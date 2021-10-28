package electrodynamics.client.screen;

import electrodynamics.common.inventory.container.ContainerTankGeneric;
import electrodynamics.common.tile.generic.TileGenericTank;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentFluid;
import electrodynamics.prefab.screen.component.ScreenComponentProgress;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class ScreenTankGeneric extends GenericScreen<ContainerTankGeneric> {

    public ScreenTankGeneric(ContainerTankGeneric screenContainer, Inventory inv, Component titleIn) {
	super(screenContainer, inv, titleIn);

	components.add(new ScreenComponentProgress(() -> 0, this, 52, 33));
	components.add(new ScreenComponentProgress(() -> 0, this, 102, 33));
	components.add(new ScreenComponentFluid(() -> {
	    TileGenericTank boiler = menu.getHostFromIntArray();
	    if (boiler != null) {
		ComponentFluidHandlerSimple handler = (ComponentFluidHandlerSimple) boiler.getComponent(ComponentType.FluidHandler);
		FluidTank tank = handler.getTankFromFluid(null, true);
		if (tank.getFluidAmount() > 0) {
		    return tank;
		}
	    }
	    return null;
	}, this, 81, 18));
    }

}
