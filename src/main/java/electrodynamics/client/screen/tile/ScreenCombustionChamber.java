package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerCombustionChamber;
import electrodynamics.common.tile.electricitygrid.generators.TileCombustionChamber;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import voltaic.prefab.screen.component.ScreenComponentGeneric;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.gauges.ScreenComponentFluidGauge;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.screen.types.GenericMaterialScreen;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;

@OnlyIn(Dist.CLIENT)
public class ScreenCombustionChamber extends GenericMaterialScreen<ContainerCombustionChamber> {

	public ScreenCombustionChamber(ContainerCombustionChamber container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentFluidGauge(() -> {
			TileCombustionChamber boiler = container.getSafeHost();
			if (boiler != null) {
				return boiler.<ComponentFluidHandlerMulti>getComponent(IComponentType.FluidHandler).getInputTanks()[0];
			}
			return null;
		}, 98, 18));
		addComponent(new ScreenComponentGeneric(ScreenComponentProgress.ProgressTextures.ARROW_RIGHT_OFF, 69, 33));
		addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.COUNTDOWN_FLAME, () -> {
			TileCombustionChamber boiler = container.getSafeHost();
			if (boiler != null) {
				return boiler.burnTime.getValue() / (double) TileCombustionChamber.TICKS_PER_MILLIBUCKET;
			}
			return 0;
		}, 119, 34));
		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));
	}
}