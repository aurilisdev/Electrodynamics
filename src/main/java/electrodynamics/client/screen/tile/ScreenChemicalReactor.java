package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerChemicalReactor;
import electrodynamics.common.tile.machines.chemicalreactor.TileChemicalReactor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import voltaic.prefab.screen.component.types.ScreenComponentCondensedFluid;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentGasPressure;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentGasTemperature;
import voltaic.prefab.screen.component.types.wrapper.WrapperCyclableFluidGauge;
import voltaic.prefab.screen.component.types.wrapper.WrapperCyclableGasGauge;
import voltaic.prefab.screen.component.types.wrapper.WrapperInventoryIO;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.screen.types.GenericMaterialScreen;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentProcessor;

public class ScreenChemicalReactor extends GenericMaterialScreen<ContainerChemicalReactor> {
    public ScreenChemicalReactor(ContainerChemicalReactor container, Inventory inv, Component titleIn) {
        super(container, inv, titleIn);

        imageHeight += 35;
        inventoryLabelY += 35;

        addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
            GenericTile furnace = container.getSafeHost();
            if (furnace != null) {
                ComponentProcessor processor = furnace.getComponent(IComponentType.Processor);
                if (processor.isActive(0)) {
                    return processor.operatingTicks.getValue()[0] / processor.requiredTicks.getValue()[0];
                }
            }
            return 0;
        }, 66, 52));

        WrapperCyclableFluidGauge fluidInput = new WrapperCyclableFluidGauge(6, 26, container, this, true);
        WrapperCyclableGasGauge gasInput = new WrapperCyclableGasGauge(26, 26, container, this, true);

        WrapperCyclableFluidGauge fluidOutput = new WrapperCyclableFluidGauge(112, 26, container, this, false);
        WrapperCyclableGasGauge gasOutput = new WrapperCyclableGasGauge(132, 26, container, this, false);

        new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE * 3 + 2, 75, 117, 8, 107)
                //
                .hideAdditional(show -> {
                    //
                    fluidInput.getComponents().forEach(component -> {
                        component.setActive(show);
                        component.setVisible(show);
                    });
                    //
                    gasInput.getComponents().forEach(component -> {
                        component.setActive(show);
                        component.setVisible(show);
                    });
                    //
                    fluidOutput.getComponents().forEach(component -> {
                        component.setActive(show);
                        component.setVisible(show);
                    });
                    //
                    gasOutput.getComponents().forEach(component -> {
                        component.setActive(show);
                        component.setVisible(show);
                    });
                    //
                });

        addComponent(new ScreenComponentGasTemperature(-AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE * 2));
        addComponent(new ScreenComponentGasPressure(-AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE));
        addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));
        addComponent(new ScreenComponentCondensedFluid(() -> {
            TileChemicalReactor electric = container.getSafeHost();
            if (electric == null) {
                return null;
            }

            return electric.condensedFluidFromGas;

        }, 153, 84));
    }
}
