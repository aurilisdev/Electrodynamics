package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerCombustionChamber;
import electrodynamics.common.tile.TileCombustionChamber;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentFluid;
import electrodynamics.prefab.screen.component.ScreenComponentGauge;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenCombustionChamber extends GenericScreen<ContainerCombustionChamber> {
	public ScreenCombustionChamber(ContainerCombustionChamber container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		components.add(new ScreenComponentFluid(() -> {
			TileCombustionChamber boiler = container.getHostFromIntArray();
			if (boiler != null) {
				AbstractFluidHandler<?> handler = boiler.getComponent(ComponentType.FluidHandler);
				return handler.getInputTanks()[0];
			}
			return null;
		}, this, width - getXPos() - ScreenComponentGauge.WIDTH / 2, 18)); 
	}
}