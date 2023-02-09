package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerChemicalCrystallizer;
import electrodynamics.common.tile.TileChemicalCrystallizer;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentFluid;
import electrodynamics.prefab.screen.component.ScreenComponentGeneric;
import electrodynamics.prefab.screen.component.ScreenComponentProgress;
import electrodynamics.prefab.screen.component.ScreenComponentProgress.ProgressBars;
import electrodynamics.prefab.screen.component.ScreenComponentProgress.ProgressTextures;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenChemicalCrystallizer extends GenericScreen<ContainerChemicalCrystallizer> {

	public ScreenChemicalCrystallizer(ContainerChemicalCrystallizer container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		components.add(new ScreenComponentProgress(ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
			GenericTile furnace = container.getHostFromIntArray();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getComponent(ComponentType.Processor);
				if (processor.operatingTicks.get() > 0) {
					return processor.operatingTicks.get() / processor.requiredTicks.get();
				}
			}
			return 0;
		}, this, 41, 31));
		components.add(new ScreenComponentGeneric(ProgressTextures.ARROW_LEFT_OFF, this, 41, 51));
		components.add(new ScreenComponentFluid(() -> {
			TileChemicalCrystallizer boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return  boiler.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler).getInputTanks()[0];
			}
			return null;
		}, this, 21, 18));
		components.add(new ScreenComponentElectricInfo(this, -AbstractScreenComponentInfo.SIZE + 1, 2));
	}

}