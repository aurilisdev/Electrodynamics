package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerChemicalCrystallizer;
import electrodynamics.common.tile.machines.TileChemicalCrystallizer;
import electrodynamics.prefab.screen.component.types.ScreenComponentGeneric;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress.ProgressBars;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress.ProgressTextures;
import electrodynamics.prefab.screen.component.types.gauges.ScreenComponentFluidGaugeInput;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.types.wrapper.InventoryIOWrapper;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.screen.types.GenericMaterialScreen;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenChemicalCrystallizer extends GenericMaterialScreen<ContainerChemicalCrystallizer> {

	public ScreenChemicalCrystallizer(ContainerChemicalCrystallizer container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentProgress(ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
			GenericTile furnace = container.getHostFromIntArray();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getComponent(IComponentType.Processor);
				if (processor.isActive()) {
					return processor.operatingTicks.get() / processor.requiredTicks.get();
				}
			}
			return 0;
		}, 41, 31));
		addComponent(new ScreenComponentGeneric(ProgressTextures.ARROW_LEFT_OFF, 41, 51));
		addComponent(new ScreenComponentFluidGaugeInput(() -> {
			TileChemicalCrystallizer boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentFluidHandlerMulti>getComponent(IComponentType.FluidHandler).getInputTanks()[0];
			}
			return null;
		}, 21, 18));
		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));

		new InventoryIOWrapper(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82, 8, 72);
	}

}