package electrodynamics.client.screen.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.inventory.container.tile.ContainerCarbyneBatteryBox;
import electrodynamics.common.tile.battery.TileCarbyneBatteryBox;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentMultiLabel;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenCarbyneBatteryBox extends GenericScreen<ContainerCarbyneBatteryBox> {
	public ScreenCarbyneBatteryBox(ContainerCarbyneBatteryBox container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentElectricInfo(this::getElectricInformation, -AbstractScreenComponentInfo.SIZE + 1, 2));

		addComponent(new ScreenComponentMultiLabel(0, 0, stack -> {
			TileCarbyneBatteryBox box = menu.getHostFromIntArray();
			if (box == null) {
				return;
			}
			ComponentElectrodynamic electro = box.getComponent(ComponentType.Electrodynamic);
			font.draw(stack, ElectroTextUtils.gui("machine.current", ChatFormatter.getChatDisplayShort(box.powerOutput.get() * 20.0 * box.currentCapacityMultiplier.get() / electro.getVoltage(), DisplayUnit.AMPERE)), inventoryLabelX, inventoryLabelY - 55f, 4210752);
			font.draw(stack, ElectroTextUtils.gui("machine.transfer", ChatFormatter.getChatDisplayShort(box.powerOutput.get() * 20.0 * box.currentCapacityMultiplier.get(), DisplayUnit.WATT)), inventoryLabelX, inventoryLabelY - 42f, 4210752);
			font.draw(stack, ElectroTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(electro.getVoltage(), DisplayUnit.VOLTAGE)), inventoryLabelX, inventoryLabelY - 29f, 4210752);
			font.draw(stack, ElectroTextUtils.gui("machine.stored", ElectroTextUtils.ratio(ChatFormatter.getChatDisplayShort(electro.getJoulesStored(), DisplayUnit.JOULES), ChatFormatter.getChatDisplayShort(box.maxJoules.get() * box.currentCapacityMultiplier.get(), DisplayUnit.JOULES))), inventoryLabelX, inventoryLabelY - 16f, 4210752);
		}));

	}

	private List<? extends FormattedCharSequence> getElectricInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		TileCarbyneBatteryBox box = menu.getHostFromIntArray();
		if (box == null) {
			return list;
		}

		ComponentElectrodynamic el = box.getComponent(ComponentType.Electrodynamic);
		list.add(ElectroTextUtils.gui("machine.current", ChatFormatter.getChatDisplayShort(box.powerOutput.get() * 20.0 * box.currentCapacityMultiplier.get() / el.getVoltage(), DisplayUnit.AMPERE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		list.add(ElectroTextUtils.gui("machine.transfer", ChatFormatter.getChatDisplayShort(box.powerOutput.get() * 20.0 * box.currentCapacityMultiplier.get(), DisplayUnit.WATT).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		list.add(ElectroTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(el.getVoltage(), DisplayUnit.VOLTAGE)).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		list.add(ElectroTextUtils.gui("machine.stored", ElectroTextUtils.ratio(ChatFormatter.getChatDisplayShort(el.getJoulesStored(), DisplayUnit.JOULES), ChatFormatter.getChatDisplayShort(box.maxJoules.get() * box.currentCapacityMultiplier.get(), DisplayUnit.JOULES)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());

		return list;
	}

}