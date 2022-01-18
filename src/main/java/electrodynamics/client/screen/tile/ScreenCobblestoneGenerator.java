package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerCobblestoneGenerator;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.TileCobblestoneGenerator;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.gui.ScreenComponentInfo;
import electrodynamics.prefab.screen.component.gui.type.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.gui.type.ScreenComponentFluid;
import electrodynamics.prefab.screen.component.gui.type.ScreenComponentProgress;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class ScreenCobblestoneGenerator extends GenericScreen<ContainerCobblestoneGenerator> {

	public ScreenCobblestoneGenerator(ContainerCobblestoneGenerator container, Inventory inv, Component titleIn) {
		super(container, inv, titleIn);
		components.add(new ScreenComponentFluid(() -> {
			TileCobblestoneGenerator cobble = container.getHostFromIntArray();
			if (cobble != null && cobble.isPoweredClient) {
				FluidTank tank = new FluidTank(1000);
				tank.fill(new FluidStack(Fluids.WATER, 1000), FluidAction.EXECUTE);
				return tank;
			}
			return null;
		}, this, 21, 18));
		components.add(new ScreenComponentFluid(() -> {
			TileCobblestoneGenerator cobble = container.getHostFromIntArray();
			if (cobble != null && cobble.isPoweredClient) {
				FluidTank tank = new FluidTank(1000);
				tank.fill(new FluidStack(Fluids.LAVA, 1000), FluidAction.EXECUTE);
				return tank;
			}
			return null;
		}, this, 117, 18));
		components.add(new ScreenComponentProgress(() -> {
			TileCobblestoneGenerator cobble = container.getHostFromIntArray();
			if (cobble != null) {
				return cobble.progressClient;
			}
			return 0;
		}, this, 40, 34));
		components.add(new ScreenComponentProgress(() -> {
			TileCobblestoneGenerator cobble = container.getHostFromIntArray();
			if (cobble != null) {
				return cobble.progressClient;
			}
			return 0;
		}, this, 90, 34).left());
		components.add(new ScreenComponentElectricInfo(this, -ScreenComponentInfo.SIZE + 1, 2).wattage(Constants.COBBLE_GEN_USAGE_PER_TICK * 20));
	}

}
