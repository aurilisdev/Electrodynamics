package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerCombustionChamber;
import electrodynamics.common.tile.generators.TileCombustionChamber;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentFluidGaugeInput;
import electrodynamics.prefab.screen.component.ScreenComponentGeneric;
import electrodynamics.prefab.screen.component.ScreenComponentProgress;
import electrodynamics.prefab.screen.component.ScreenComponentProgress.ProgressBars;
import electrodynamics.prefab.screen.component.ScreenComponentProgress.ProgressTextures;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.screen.types.GenericMaterialScreen;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenCombustionChamber extends GenericMaterialScreen<ContainerCombustionChamber> {

	public ScreenCombustionChamber(ContainerCombustionChamber container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentFluidGaugeInput(() -> {
			TileCombustionChamber boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler).getInputTanks()[0];
			}
			return null;
		}, 98, 18));
		addComponent(new ScreenComponentGeneric(ProgressTextures.ARROW_RIGHT_OFF, 69, 33));
		addComponent(new ScreenComponentProgress(ProgressBars.COUNTDOWN_FLAME, () -> {
			TileCombustionChamber boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.burnTime.get() / (double) TileCombustionChamber.TICKS_PER_MILLIBUCKET;
			}
			return 0;
		}, 119, 34));
		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));
	}
}