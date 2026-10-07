package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerProcessorO2O;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.types.wrapper.WrapperInventoryIO;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentProcessor;

@OnlyIn(Dist.CLIENT)
public abstract class ScreenProcessorO2O<C extends ContainerProcessorO2O> extends GenericScreen<C> {

    protected ScreenProcessorO2O(C container, Inventory playerInventory, Component title) {
	super(container, playerInventory, title);
	addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.PROGRESS_ARROW_RIGHT,
		() -> getProgress(container, 0), 84 - ContainerProcessorO2O.START_X_OFFSET, 34));
	addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));
	new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75,
		82, 8, 72);
    }

    private static double getProgress(GenericContainerBlockEntity<GenericTile> container, int index) {
	return container.getSafeHost().map(tile -> tile.<ComponentProcessor>requireComponent(IComponentType.Processor))
		.filter(processor -> processor.isActive(index))
		.map(processor -> processor.operatingTicks.getValue()[index]
			/ processor.requiredTicks.getValue()[index])
		.orElse(0.0);
    }
}
