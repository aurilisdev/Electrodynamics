package electrodynamics.client.screen.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.common.inventory.container.tile.ContainerCoalGenerator;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.common.tile.electricitygrid.generators.TileCoalGenerator;
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
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentTemperature;
import voltaic.prefab.screen.component.types.wrapper.WrapperInventoryIO;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.utilities.object.TransferPack;
import voltaic.registers.VoltaicCapabilities;

@OnlyIn(Dist.CLIENT)
public class ScreenCoalGenerator extends GenericScreen<ContainerCoalGenerator> {
	public ScreenCoalGenerator(ContainerCoalGenerator container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.COUNTDOWN_FLAME, () -> {
			TileCoalGenerator box = container.getSafeHost();
			if (box != null) {
				return (double) box.burnTime.getValue() / (double) box.maxBurnTime.getValue();
			}
			return 0;
		}, 25, 25));
		addComponent(new ScreenComponentTemperature(this::getTemperatureInformation, -AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE));
		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));
		addComponent(new ScreenComponentMultiLabel(0, 0, poseStack -> {
			TileCoalGenerator coal = menu.getSafeHost();
			if (coal == null) {
				return;
			}
			TransferPack output = TransferPack.ampsVoltage(ElectroConstants.COALGENERATOR_AMPERAGE * Math.min((coal.heat.getValue() - 27.0) / (3000.0 - 27.0), 1), VoltaicCapabilities.DEFAULT_VOLTAGE);
			font.draw(poseStack, ElectroTextUtils.gui("coalgenerator.timeleft", ChatFormatter.getChatDisplayShort((double) coal.burnTime.getValue() / 20.0, DisplayUnits.TIME_SECONDS)), inventoryLabelX + 60, inventoryLabelY - 53, 4210752);
			font.draw(poseStack, ElectroTextUtils.gui("machine.current", ChatFormatter.getChatDisplayShort(output.getAmps(), DisplayUnits.AMPERE)), inventoryLabelX + 60, inventoryLabelY - 40, 4210752);
			font.draw(poseStack, ElectroTextUtils.gui("machine.output", ChatFormatter.getChatDisplayShort(output.getWatts(), DisplayUnits.WATT)), inventoryLabelX + 60, inventoryLabelY - 27, 4210752);
			font.draw(poseStack, ElectroTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(output.getVoltage(), DisplayUnits.VOLTAGE)), inventoryLabelX + 60, inventoryLabelY - 14, 4210752);
		}));

		new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE * 2 + 2, 75, 82, 8, 72);
	}

	private List<IReorderingProcessor> getTemperatureInformation() {
		ArrayList<IReorderingProcessor> list = new ArrayList<>();
		TileCoalGenerator box = menu.getSafeHost();
		if (box == null) {
			return list;
		}

		list.add(ElectroTextUtils.gui("coalgenerator.timeleft", ChatFormatter.getChatDisplayShort((double) box.burnTime.getValue() / 20.0, DisplayUnits.TIME_SECONDS).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
		list.add(ElectroTextUtils.gui("machine.temperature", ChatFormatter.getChatDisplayShort(box.heat.getValue() * (2500.0 / 3000.0), DisplayUnits.TEMPERATURE_CELCIUS).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
		list.add(ElectroTextUtils.gui("machine.heat", ChatFormatter.getChatDisplayShort((box.heat.getValue() - 27.0) / (3000.0 - 27.0) * 100, DisplayUnits.PERCENTAGE).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());

		return list;
	}

}