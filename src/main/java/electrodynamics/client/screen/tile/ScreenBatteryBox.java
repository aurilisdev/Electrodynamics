package electrodynamics.client.screen.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.common.inventory.container.tile.ContainerBatteryBox;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.ScreenComponentMultiLabel;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.utilities.VoltaicTextUtils;

@OnlyIn(Dist.CLIENT)
public class ScreenBatteryBox extends GenericScreen<ContainerBatteryBox> {

    public ScreenBatteryBox(ContainerBatteryBox container, Inventory playerInventory, Component title) {
	super(container, playerInventory, title);
	addComponent(new ScreenComponentElectricInfo(this::getElectricInformation,
		-AbstractScreenComponentInfo.SIZE + 1, 2));
	addComponent(new ScreenComponentMultiLabel(0, 0, graphics -> menu.getSafeHost().ifPresent(box -> {
	    ComponentElectrodynamic electro = box
		    .<ComponentElectrodynamic>requireComponent(IComponentType.Electrodynamic);
	    double multiplier = box.currentCapacityMultiplier.getValue();
	    double watts = box.powerOutput.getValue() * 20.0 * multiplier;
	    graphics.drawString(font,
		    ElectroTextUtils.gui("machine.current",
			    ChatFormatter.getChatDisplayShort(watts / electro.getVoltage(), DisplayUnits.AMPERE)),
		    inventoryLabelX, inventoryLabelY - 55, 4210752, false);
	    graphics.drawString(font,
		    ElectroTextUtils.gui("machine.transfer",
			    ChatFormatter.getChatDisplayShort(watts, DisplayUnits.WATT)),
		    inventoryLabelX, inventoryLabelY - 42, 4210752, false);
	    graphics.drawString(font,
		    ElectroTextUtils.gui("machine.voltage",
			    ChatFormatter.getChatDisplayShort(electro.getVoltage(), DisplayUnits.VOLTAGE)),
		    inventoryLabelX, inventoryLabelY - 29, 4210752, false);
	    graphics.drawString(font,
		    ElectroTextUtils.gui("machine.stored",
			    VoltaicTextUtils.ratio(
				    ChatFormatter.getChatDisplayShort(electro.getJoulesStored(), DisplayUnits.JOULES),
				    ChatFormatter.getChatDisplayShort(box.maxJoules.getValue() * multiplier,
					    DisplayUnits.JOULES))),
		    inventoryLabelX, inventoryLabelY - 16, 4210752, false);
	})));
    }

    private List<? extends FormattedCharSequence> getElectricInformation() {
	ArrayList<FormattedCharSequence> list = new ArrayList<>();
	menu.getSafeHost().ifPresent(box -> {
	    ComponentElectrodynamic electro = box
		    .<ComponentElectrodynamic>requireComponent(IComponentType.Electrodynamic);
	    double multiplier = box.currentCapacityMultiplier.getValue();
	    double watts = box.powerOutput.getValue() * 20.0 * multiplier;
	    list.add(ElectroTextUtils
		    .gui("machine.current",
			    ChatFormatter.getChatDisplayShort(watts / electro.getVoltage(), DisplayUnits.AMPERE)
				    .withStyle(ChatFormatting.GRAY))
		    .withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
	    list.add(
		    ElectroTextUtils
			    .gui("machine.transfer",
				    ChatFormatter.getChatDisplayShort(watts, DisplayUnits.WATT)
					    .withStyle(ChatFormatting.GRAY))
			    .withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
	    list.add(ElectroTextUtils
		    .gui("machine.transfer",
			    VoltaicTextUtils.ratio(
				    ChatFormatter.getChatDisplayShort(watts / 20.0, DisplayUnits.FORGE_ENERGY_UNIT)
					    .withStyle(ChatFormatting.GRAY),
				    DisplayUnits.TIME_TICKS.getSymbol()))
		    .withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
	    list.add(ElectroTextUtils
		    .gui("machine.voltage",
			    ChatFormatter.getChatDisplayShort(electro.getVoltage(), DisplayUnits.VOLTAGE)
				    .withStyle(ChatFormatting.GRAY))
		    .withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
	    list.add(ElectroTextUtils
		    .gui("machine.stored",
			    VoltaicTextUtils.ratio(
				    ChatFormatter.getChatDisplayShort(electro.getJoulesStored(), DisplayUnits.JOULES),
				    ChatFormatter.getChatDisplayShort(box.maxJoules.getValue() * multiplier,
					    DisplayUnits.JOULES))
				    .withStyle(ChatFormatting.GRAY))
		    .withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
	});
	return list;
    }
}