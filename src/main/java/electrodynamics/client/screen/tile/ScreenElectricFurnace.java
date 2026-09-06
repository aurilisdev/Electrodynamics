package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerElectricFurnace;
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
public class ScreenElectricFurnace extends GenericScreen<ContainerElectricFurnace> {

    public ScreenElectricFurnace(ContainerElectricFurnace container, Inventory playerInventory, Component title) {
	super(container, playerInventory, title);
	addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.PROGRESS_ARROW_RIGHT,
		() -> container.getSafeHost()
			.map(tile -> tile.<ComponentProcessor>requireComponent(IComponentType.Processor))
			.filter(processor -> processor.isActive(0))
			.map(processor -> processor.operatingTicks.getValue()[0]
				/ processor.requiredTicks.getValue()[0])
			.orElse(0.0),
		84, 34));
	addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.COUNTDOWN_FLAME,
		() -> container.getSafeHost()
			.map(tile -> tile.<ComponentProcessor>requireComponent(IComponentType.Processor))
			.filter(processor -> processor.isActive(0)).map(processor -> 1.0).orElse(0.0),
		39, 36));
	addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));
	new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75,
		82, 8, 72);
    }
}