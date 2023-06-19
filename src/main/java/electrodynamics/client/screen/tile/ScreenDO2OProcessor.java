package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerDO2OProcessor;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentProgress;
import electrodynamics.prefab.screen.component.ScreenComponentProgress.ProgressBars;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenDO2OProcessor extends GenericScreen<ContainerDO2OProcessor> {

	public ScreenDO2OProcessor(ContainerDO2OProcessor container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentProgress(ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
			GenericTile furnace = container.getHostFromIntArray();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getComponent(ComponentType.Processor);
				if (processor.isActive()) {
					return processor.operatingTicks.get() / processor.requiredTicks.get();
				}
			}
			return 0;
		}, 84 - ContainerDO2OProcessor.startXOffset, 34));
		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));
	}

}