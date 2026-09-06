package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerChemicalCrystallizer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.gauges.ScreenComponentFluidGauge;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.types.wrapper.WrapperInventoryIO;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.screen.types.GenericMaterialScreen;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentProcessor;

@OnlyIn(Dist.CLIENT)
public class ScreenChemicalCrystallizer extends GenericMaterialScreen<ContainerChemicalCrystallizer> {

    public ScreenChemicalCrystallizer(ContainerChemicalCrystallizer container, Inventory playerInventory,
	    Component title) {
	super(container, playerInventory, title);
	addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.PROGRESS_ARROW_RIGHT_BIG,
		() -> container.getSafeHost()
			.map(tile -> tile.<ComponentProcessor>requireComponent(IComponentType.Processor))
			.filter(processor -> processor.isActive(0))
			.map(processor -> processor.operatingTicks.getValue()[0]
				/ processor.requiredTicks.getValue()[0])
			.orElse(0.0),
		42, 31));
	addComponent(new ScreenComponentFluidGauge(() -> container
		.getSafeHost().map(tile -> tile
			.<ComponentFluidHandlerMulti>requireComponent(IComponentType.FluidHandler).getInputTanks()[0])
		.orElse(null), 21, 18));
	addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));
	new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75,
		82, 8, 72);
    }
}