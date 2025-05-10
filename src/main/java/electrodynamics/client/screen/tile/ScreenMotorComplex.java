package electrodynamics.client.screen.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.common.inventory.container.tile.ContainerMotorComplex;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.common.tile.machines.quarry.TileMotorComplex;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.ScreenComponentSimpleLabel;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.utilities.math.Color;

public class ScreenMotorComplex extends GenericScreen<ContainerMotorComplex> {

	public ScreenMotorComplex(ContainerMotorComplex container, PlayerInventory inv, ITextComponent titleIn) {
		super(container, inv, titleIn);
		addComponent(new ScreenComponentElectricInfo(this::getElectricInformation, -AbstractScreenComponentInfo.SIZE + 1, 2));
		addComponent(new ScreenComponentSimpleLabel(30, 40, 10, Color.TEXT_GRAY, () -> {
			int blocksPerTick = 0;
			TileMotorComplex motor = menu.getSafeHost();
			if (motor != null && motor.isPowered.getValue()) {
				blocksPerTick = motor.speed.getValue();
			}
			return ElectroTextUtils.gui("motorcomplex.speed", blocksPerTick);
		}));
	}

	private List<? extends IReorderingProcessor> getElectricInformation() {
		ArrayList<IReorderingProcessor> list = new ArrayList<>();
		TileMotorComplex motor = menu.getSafeHost();
		if (motor == null) {
			return list;
		}

		ComponentElectrodynamic electro = motor.getComponent(IComponentType.Electrodynamic);
		list.add(ElectroTextUtils.gui("machine.usage", ChatFormatter.getChatDisplayShort(ElectroConstants.MOTORCOMPLEX_USAGE_PER_TICK * motor.powerMultiplier.getValue() * 20, DisplayUnits.WATT).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
		list.add(ElectroTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(electro.getVoltage(), DisplayUnits.VOLTAGE).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());

		return list;
	}

}
