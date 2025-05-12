package electrodynamics.client.screen.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.common.inventory.container.tile.ContainerBatteryBox;
import electrodynamics.common.tile.electricitygrid.batteries.TileBatteryBox;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
	public ScreenBatteryBox(ContainerBatteryBox container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentElectricInfo(this::getElectricInformation, -AbstractScreenComponentInfo.SIZE + 1, 2));
		addComponent(new ScreenComponentMultiLabel(0, 0, poseStack -> {
			TileBatteryBox box = menu.getSafeHost();
			if (box == null) {
				return;
			}
			ComponentElectrodynamic electro = box.getComponent(IComponentType.Electrodynamic);
			font.draw(poseStack, ElectroTextUtils.gui("machine.current", ChatFormatter.getChatDisplayShort(box.powerOutput.getValue() * 20.0 * box.currentCapacityMultiplier.getValue() / electro.getVoltage(), DisplayUnits.AMPERE)), inventoryLabelX, inventoryLabelY - 55, 4210752);
			font.draw(poseStack, ElectroTextUtils.gui("machine.transfer", ChatFormatter.getChatDisplayShort(box.powerOutput.getValue() * 20.0 * box.currentCapacityMultiplier.getValue(), DisplayUnits.WATT)), inventoryLabelX, inventoryLabelY - 42, 4210752);
			font.draw(poseStack, ElectroTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(electro.getVoltage(), DisplayUnits.VOLTAGE)), inventoryLabelX, inventoryLabelY - 29, 4210752);
			font.draw(poseStack, ElectroTextUtils.gui("machine.stored", VoltaicTextUtils.ratio(ChatFormatter.getChatDisplayShort(electro.getJoulesStored(), DisplayUnits.JOULES), ChatFormatter.getChatDisplayShort(box.maxJoules.getValue() * box.currentCapacityMultiplier.getValue(), DisplayUnits.JOULES))), inventoryLabelX, inventoryLabelY - 16, 4210752);
		}));
	}

	private List<? extends IReorderingProcessor> getElectricInformation() {
		ArrayList<IReorderingProcessor> list = new ArrayList<>();
		TileBatteryBox box = menu.getSafeHost();
		if (box == null) {
			return list;
		}

		ComponentElectrodynamic el = box.getComponent(IComponentType.Electrodynamic);
		list.add(ElectroTextUtils.gui("machine.current", ChatFormatter.getChatDisplayShort(box.powerOutput.getValue() * 20.0 * box.currentCapacityMultiplier.getValue() / el.getVoltage(), DisplayUnits.AMPERE).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
		list.add(ElectroTextUtils.gui("machine.transfer", ChatFormatter.getChatDisplayShort(box.powerOutput.getValue() * 20.0 * box.currentCapacityMultiplier.getValue(), DisplayUnits.WATT).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
		list.add(ElectroTextUtils.gui("machine.transfer", VoltaicTextUtils.ratio(ChatFormatter.getChatDisplayShort(box.powerOutput.getValue() * box.currentCapacityMultiplier.getValue(), DisplayUnits.FORGE_ENERGY_UNIT).withStyle(TextFormatting.GRAY), DisplayUnits.TIME_TICKS.getSymbol())).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
		list.add(ElectroTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(el.getVoltage(), DisplayUnits.VOLTAGE).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
		list.add(ElectroTextUtils.gui("machine.stored", VoltaicTextUtils.ratio(ChatFormatter.getChatDisplayShort(el.getJoulesStored(), DisplayUnits.JOULES), ChatFormatter.getChatDisplayShort(box.maxJoules.getValue() * box.currentCapacityMultiplier.getValue(), DisplayUnits.JOULES)).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());

		return list;
	}

}