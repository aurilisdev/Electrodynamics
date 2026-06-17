package electrodynamics.client.screen.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.common.inventory.container.tile.ContainerElectrolosisChamber;
import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.common.tile.machines.TileElectrolosisChamber;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.prefab.screen.component.types.ScreenComponentMultiLabel;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.gauges.ScreenComponentFluidGauge;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.screen.types.GenericMaterialScreen;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.utilities.VoltaicTextUtils;
import voltaic.prefab.utilities.math.Color;

public class ScreenElectrolosisChamber extends GenericMaterialScreen<ContainerElectrolosisChamber> {
    public ScreenElectrolosisChamber(ContainerElectrolosisChamber container, Inventory inv, Component titleIn) {
	super(container, inv, titleIn);

	addComponent(new ScreenComponentElectricInfo(this::getElectricInfo, -AbstractScreenComponentInfo.SIZE + 1, 2));

	addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.PROGRESS_ARROW_RIGHT_BIG, () -> {
	    TileElectrolosisChamber chamber = container.getSafeHost();
	    if (chamber == null || !chamber.isActive.getValue()) {
		return 0;
	    }
	    if (chamber.neededTicks.getValue() <= 0) {
		return 1;
	    }

	    return Math.min(1, chamber.operatingTicks.getValue() / chamber.neededTicks.getValue());
	}, 56, 31));

	addComponent(new ScreenComponentMultiLabel(0, 0, graphics -> {
	    TileElectrolosisChamber chamber = container.getSafeHost();
	    if (chamber == null) {
		return;
	    }
	    Component text = VoltaicTextUtils.ratio(
		    ChatFormatter.getChatDisplayShort((double) chamber.processAmount.getValue() / 1000.0,
			    DisplayUnits.BUCKETS),
		    ChatFormatter.getChatDisplayShort(chamber.neededTicks.getValue(), DisplayUnits.TIME_TICKS));
	    int width = getFontRenderer().width(text);
	    float scale = 1;
	    if (width > 70) {
		scale = 70.0F / width;
		width = 70;
	    }
	    int diff = 70 - width;
	    int half = diff / 2;
	    int x = (int) Math.ceil((52 + half) / scale) + 1;
	    int y = (int) Math.ceil(52.0F / scale);
	    graphics.pose().pushPose();
	    graphics.pose().scale(scale, scale, scale);
	    graphics.drawString(getFontRenderer(), text, x, y, Color.TEXT_GRAY.color(), false);
	    graphics.pose().popPose();
	}));

	addComponent(new ScreenComponentFluidGauge(() -> {
	    TileElectrolosisChamber boiler = container.getSafeHost();
	    if (boiler != null) {
		return boiler.<ComponentFluidHandlerMulti>getComponent(IComponentType.FluidHandler).getInputTanks()[0];
	    }
	    return null;
	}, 38, 18));
	addComponent(new ScreenComponentFluidGauge(() -> {
	    TileElectrolosisChamber boiler = container.getSafeHost();
	    if (boiler != null) {
		return boiler.<ComponentFluidHandlerMulti>getComponent(IComponentType.FluidHandler).getOutputTanks()[0];
	    }
	    return null;
	}, 124, 18));
    }

    private List<FormattedCharSequence> getElectricInfo() {
	List<FormattedCharSequence> list = new ArrayList<>();
	TileElectrolosisChamber chamber = menu.getSafeHost();
	if (chamber == null) {
	    return list;
	}
	ComponentElectrodynamic el = chamber.getComponent(IComponentType.Electrodynamic);
	list.add(ElectroTextUtils
		.gui("machine.usage",
			ChatFormatter.getChatDisplayShort(
				ElectrodynamicsConfig.INSTANCE.ELECTROLOSIS_CHAMBER_TARGET_JOULES.get() * 20,
				DisplayUnits.WATT).withStyle(ChatFormatting.GRAY))
		.withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
	list.add(ElectroTextUtils
		.gui("machine.voltage",
			ChatFormatter.getChatDisplayShort(el.getVoltage(), DisplayUnits.VOLTAGE)
				.withStyle(ChatFormatting.GRAY))
		.withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
	list.add(ElectroTextUtils
		.tooltip("electrolosischamber.satisfaction",
			ChatFormatter.getChatDisplayShort(el.getJoulesStored()
				/ ElectrodynamicsConfig.INSTANCE.ELECTROLOSIS_CHAMBER_TARGET_JOULES.get() * 100.0,
				DisplayUnits.PERCENTAGE).withStyle(ChatFormatting.GRAY))
		.withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
	return list;
    }
}
