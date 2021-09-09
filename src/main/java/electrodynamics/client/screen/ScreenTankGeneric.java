package electrodynamics.client.screen;

import electrodynamics.common.inventory.container.ContainerTankGeneric;
import electrodynamics.common.tile.generic.TileGenericTank;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentFluid;
import electrodynamics.prefab.screen.component.ScreenComponentProgress;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class ScreenTankGeneric extends GenericScreen<ContainerTankGeneric> {

    public ScreenTankGeneric(ContainerTankGeneric screenContainer, PlayerInventory inv, ITextComponent titleIn) {
	super(screenContainer, inv, titleIn);

	components.add(new ScreenComponentProgress(() -> 0, this, 52, 33));
	components.add(new ScreenComponentProgress(() -> 0, this, 102, 33));
	components.add(new ScreenComponentFluid(() -> {
	    TileGenericTank boiler = container.getHostFromIntArray();
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
