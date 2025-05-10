package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerCreativeFluidSource;
import electrodynamics.common.tile.pipelines.fluid.TileCreativeFluidSource;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import voltaic.prefab.screen.component.ScreenComponentGeneric;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.ScreenComponentSimpleLabel;
import voltaic.prefab.screen.component.types.gauges.ScreenComponentFluidGauge;
import voltaic.prefab.screen.types.GenericMaterialScreen;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerSimple;
import voltaic.prefab.utilities.math.Color;

public class ScreenCreativeFluidSource extends GenericMaterialScreen<ContainerCreativeFluidSource> {

	public ScreenCreativeFluidSource(ContainerCreativeFluidSource container, PlayerInventory inv, ITextComponent titleIn) {
		super(container, inv, titleIn);
		addComponent(new ScreenComponentGeneric(ScreenComponentProgress.ProgressTextures.ARROW_RIGHT_OFF, 102, 33));
		addComponent(new ScreenComponentFluidGauge(() -> {
			TileCreativeFluidSource boiler = menu.getSafeHost();
			if (boiler != null) {
				return boiler.<ComponentFluidHandlerSimple>getComponent(IComponentType.FluidHandler);
			}
			return null;
		}, 81, 18));
		addComponent(new ScreenComponentSimpleLabel(13, 38, 10, Color.TEXT_GRAY, ElectroTextUtils.gui("creativefluidsource.setfluid")));
	}

}
