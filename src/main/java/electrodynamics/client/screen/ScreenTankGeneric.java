package electrodynamics.client.screen;

import electrodynamics.common.inventory.container.ContainerTankGeneric;
import electrodynamics.common.tile.generic.TileTankGeneric;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentFluid;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentUniversalFluidHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class ScreenTankGeneric extends GenericScreen<ContainerTankGeneric>{

	public ScreenTankGeneric(ContainerTankGeneric screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		
		components.add(new ScreenComponentFluid(() -> {
			TileTankGeneric boiler = container.getHostFromIntArray();;
			if(boiler != null) {
				ComponentUniversalFluidHandler handler = boiler.getComponent(ComponentType.UniversalFluidHandler);
				for(Fluid fluid : handler.getValidInputFluids()) {
					FluidTank tank = handler.getTankFromFluid(fluid, true);
					if(tank.getFluidAmount() > 0) {
						return tank;
					}
				}
			}
			return null;
		}, this, 21, 18));
	}

}
