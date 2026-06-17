package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerMineralWasher;
import electrodynamics.common.tile.machines.TileMineralWasher;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.gauges.ScreenComponentFluidGauge;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.types.wrapper.WrapperInventoryIO;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.screen.types.GenericMaterialScreen;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentProcessor;

@OnlyIn(Dist.CLIENT)
public class ScreenMineralWasher extends GenericMaterialScreen<ContainerMineralWasher> {
    public ScreenMineralWasher(ContainerMineralWasher container, Inventory playerInventory, Component title) {
	super(container, playerInventory, title);
	addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
	    GenericTile furnace = container.getSafeHost();
	    if (furnace != null) {
		ComponentProcessor processor = furnace.getComponent(IComponentType.Processor);
		if (processor.operatingTicks.getValue()[0] > 0) {
		    return Math.min(1.0,
			    processor.operatingTicks.getValue()[0] / (processor.requiredTicks.getValue()[0] / 2.0));
		}
	    }
	    return 0;
	}, 42, 30));
	addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
	    GenericTile furnace = container.getSafeHost();
	    if (furnace != null) {
		ComponentProcessor processor = furnace.getComponent(IComponentType.Processor);
		if (processor.operatingTicks.getValue()[0] > processor.requiredTicks.getValue()[0] / 2.0) {
		    return Math.min(1.0,
			    (processor.operatingTicks.getValue()[0] - processor.requiredTicks.getValue()[0] / 2.0)
				    / (processor.requiredTicks.getValue()[0] / 2.0));
		}
	    }
	    return 0;
	}, 98, 30));
	addComponent(new ScreenComponentFluidGauge(() -> {
	    TileMineralWasher boiler = container.getSafeHost();
	    if (boiler != null) {
		return boiler.<ComponentFluidHandlerMulti>getComponent(IComponentType.FluidHandler).getInputTanks()[0];
	    }
	    return null;
	}, 21, 18));
	addComponent(new ScreenComponentFluidGauge(() -> {
	    TileMineralWasher boiler = container.getSafeHost();
	    if (boiler != null) {
		return boiler.<ComponentFluidHandlerMulti>getComponent(IComponentType.FluidHandler).getOutputTanks()[0];
	    }
	    return null;
	}, 127, 18));
	addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));

	new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75,
		82, 8, 72);
    }
}