package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerElectricArcFurnaceDouble;
import electrodynamics.common.tile.machines.arcfurnace.TileElectricArcFurnace;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.types.wrapper.WrapperInventoryIO;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentProcessor;

@OnlyIn(Dist.CLIENT)
public class ScreenElectricArcFurnaceDouble extends GenericScreen<ContainerElectricArcFurnaceDouble> {

    public ScreenElectricArcFurnaceDouble(ContainerElectricArcFurnaceDouble container, Inventory playerInventory,
	    Component title) {
	super(container, playerInventory, title);
	addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
	    TileElectricArcFurnace furnace = container.getSafeHost();
	    if (furnace != null) {
		ComponentProcessor processor = furnace.getComponent(IComponentType.Processor);
		if (processor.isActive(0)) {
		    return processor.operatingTicks.getValue()[0] / processor.requiredTicks.getValue()[0];
		}
	    }
	    return 0;
	}, 84, 24));
	addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.COUNTDOWN_FLAME, () -> {
	    TileElectricArcFurnace furnace = container.getSafeHost();
	    if (furnace != null) {
		ComponentProcessor processor = furnace.getComponent(IComponentType.Processor);
		if (processor.isActive(0)) {
		    return 1;
		}
	    }
	    return 0;
	}, 39, 26));
	addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
	    TileElectricArcFurnace furnace = container.getSafeHost();
	    if (furnace != null) {
		ComponentProcessor processor = furnace.getComponent(IComponentType.Processor);
		if (processor.isActive(1)) {
		    return processor.operatingTicks.getValue()[0] / processor.requiredTicks.getValue()[0];
		}
	    }
	    return 0;
	}, 84, 44));
	addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.COUNTDOWN_FLAME, () -> {
	    TileElectricArcFurnace furnace = container.getSafeHost();
	    if (furnace != null) {
		ComponentProcessor processor = furnace.getComponent(IComponentType.Processor);
		if (processor.isActive(1)) {
		    return 1;
		}
	    }
	    return 0;
	}, 39, 46));
	addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));

	new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75,
		82, 8, 72);
    }
}