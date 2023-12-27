package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerElectricFurnace;
import electrodynamics.common.tile.machines.furnace.TileElectricFurnace;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress.ProgressBars;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.types.wrapper.InventoryIOWrapper;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenElectricFurnace extends GenericScreen<ContainerElectricFurnace> {

	public ScreenElectricFurnace(ContainerElectricFurnace container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentProgress(ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
			TileElectricFurnace furnace = container.getHostFromIntArray();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getProcessor(0);
				if (processor.isActive()) {
					return processor.operatingTicks.get() / processor.requiredTicks.get();
				}
			}
			return 0;
		}, 84, 34));
		addComponent(new ScreenComponentProgress(ProgressBars.COUNTDOWN_FLAME, () -> {
			TileElectricFurnace furnace = container.getHostFromIntArray();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getProcessor(0);
				if (processor.isActive()) {
					return 1;
				}
			}
			return 0;
		}, 39, 36));
		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));

		new InventoryIOWrapper(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82, 8, 72);
	}
}