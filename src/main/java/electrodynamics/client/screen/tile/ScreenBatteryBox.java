package electrodynamics.client.screen.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.inventory.container.tile.ContainerBatteryBox;
import electrodynamics.common.tile.electricitygrid.batteries.TileBatteryBox;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentMultiLabel;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenBatteryBox extends GenericScreen<ContainerBatteryBox> {
	public ScreenBatteryBox(ContainerBatteryBox container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentElectricInfo(this::getElectricInformation, -AbstractScreenComponentInfo.SIZE + 1, 2));
		addComponent(new ScreenComponentMultiLabel(0, 0, stack -> {
			TileBatteryBox box = menu.getHostFromIntArray();
			if (box == null) {
				return;
			}
			ComponentElectrodynamic electro = box.getComponent(IComponentType.Electrodynamic);
			font.draw(stack, ElectroTextUtils.gui("machine.current", ChatFormatter.getChatDisplayShort(box.powerOutput.get() * 20.0 * box.currentCapacityMultiplier.get() / electro.getVoltage(), DisplayUnit.AMPERE)), inventoryLabelX, inventoryLabelY - 55, 4210752);
			font.draw(stack, ElectroTextUtils.gui("machine.transfer", ChatFormatter.getChatDisplayShort(box.powerOutput.get() * 20.0 * box.currentCapacityMultiplier.get(), DisplayUnit.WATT)), inventoryLabelX, inventoryLabelY - 42, 4210752);
			font.draw(stack, ElectroTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(electro.getVoltage(), DisplayUnit.VOLTAGE)), inventoryLabelX, inventoryLabelY - 29, 4210752);
			font.draw(stack, ElectroTextUtils.gui("machine.stored", ElectroTextUtils.ratio(ChatFormatter.getChatDisplayShort(electro.getJoulesStored(), DisplayUnit.JOULES), ChatFormatter.getChatDisplayShort(box.maxJoules.get() * box.currentCapacityMultiplier.get(), DisplayUnit.JOULES))), inventoryLabelX, inventoryLabelY - 16, 4210752);
		}));
	}

	private List<? extends IReorderingProcessor> getElectricInformation() {
		ArrayList<IReorderingProcessor> list = new ArrayList<>();
		TileBatteryBox box = menu.getHostFromIntArray();
		if (box == null) {
			return list;
		}

		ComponentElectrodynamic el = box.getComponent(IComponentType.Electrodynamic);
		list.add(ElectroTextUtils.gui("machine.current", ChatFormatter.getChatDisplayShort(box.powerOutput.get() * 20.0 * box.currentCapacityMultiplier.get() / el.getVoltage(), DisplayUnit.AMPERE).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
		list.add(ElectroTextUtils.gui("machine.transfer", ChatFormatter.getChatDisplayShort(box.powerOutput.get() * 20.0 * box.currentCapacityMultiplier.get(), DisplayUnit.WATT).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
		list.add(ElectroTextUtils.gui("machine.transfer", ElectroTextUtils.ratio(ChatFormatter.getChatDisplayShort(box.powerOutput.get() * box.currentCapacityMultiplier.get(), DisplayUnit.FORGE_ENERGY_UNIT).withStyle(TextFormatting.GRAY), DisplayUnit.TIME_TICKS.getSymbol())).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
		list.add(ElectroTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(el.getVoltage(), DisplayUnit.VOLTAGE).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
		list.add(ElectroTextUtils.gui("machine.stored", ElectroTextUtils.ratio(ChatFormatter.getChatDisplayShort(el.getJoulesStored(), DisplayUnit.JOULES), ChatFormatter.getChatDisplayShort(box.maxJoules.get() * box.currentCapacityMultiplier.get(), DisplayUnit.JOULES)).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());

		return list;
	}

}