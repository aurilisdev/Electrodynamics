package electrodynamics.client.screen;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.inventory.container.ContainerCombustionChamber;
import electrodynamics.common.tile.TileCombustionChamber;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentFluid;
import electrodynamics.prefab.screen.component.ScreenComponentGauge;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;

@OnlyIn(Dist.CLIENT)
public class ScreenCombustionChamber extends GenericScreen<ContainerCombustionChamber> {
    public ScreenCombustionChamber(ContainerCombustionChamber container, Inventory playerInventory, Component title) {
	super(container, playerInventory, title);
	components.add(new ScreenComponentFluid(() -> {
	    TileCombustionChamber boiler = container.getHostFromIntArray();
	    if (boiler != null) {
		FluidTank tank = new FluidTank(TileCombustionChamber.TANK_CAPACITY);
		tank.fill(new FluidStack(DeferredRegisters.fluidEthanol, boiler.clientAmount), FluidAction.EXECUTE);
		return tank;
	    }
	    return null;
	}, this, width - getXPos() - ScreenComponentGauge.WIDTH / 2, 18)); // TODO: Should make this dynamic
    }
}