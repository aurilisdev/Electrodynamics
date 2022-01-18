package electrodynamics.client.screen.tile;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.inventory.container.tile.ContainerCoalGenerator;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.TileCoalGenerator;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentInfo;
import electrodynamics.prefab.screen.component.ScreenComponentProgress;
import electrodynamics.prefab.screen.component.ScreenComponentTemperature;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenCoalGenerator extends GenericScreen<ContainerCoalGenerator> {
	public ScreenCoalGenerator(ContainerCoalGenerator container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		components.add(new ScreenComponentProgress(() -> {
			TileCoalGenerator box = container.getHostFromIntArray();
			if (box != null) {
				return box.clientBurnTime / box.clientMaxBurnTime;
			}
			return 0;
		}, this, 25, 25).flame());
		components.add(
				new ScreenComponentTemperature(this::getTemperatureInformation, this, -ScreenComponentInfo.SIZE + 1, 2 + ScreenComponentInfo.SIZE));
		components.add(new ScreenComponentElectricInfo(this, -ScreenComponentInfo.SIZE + 1, 2)); // TODO: For some reason this doesnt work
	}

	private List<FormattedCharSequence> getTemperatureInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		TileCoalGenerator box = menu.getHostFromIntArray();
		if (box != null) {
			list.add(new TranslatableComponent("gui.coalgenerator.timeleft",
					new TextComponent(box.clientBurnTime / 20 + "s").withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY)
							.getVisualOrderText());
			list.add(new TranslatableComponent("gui.machine.temperature",
					new TextComponent(ChatFormatter.roundDecimals(box.clientHeat * (2500.0 / 3000.0)) + " C").withStyle(ChatFormatting.GRAY))
							.withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(new TranslatableComponent("gui.machine.heat",
					new TextComponent(ChatFormatter.roundDecimals((box.clientHeat - 27.0) / (3000.0 - 27.0) * 100) + "%")
							.withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		}
		return list;
	}

	@Override
	protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
		super.renderLabels(matrixStack, mouseX, mouseY);
		TileCoalGenerator box = menu.getHostFromIntArray();
		if (box != null) {
			TransferPack output = TransferPack.ampsVoltage(
					Constants.COALGENERATOR_MAX_OUTPUT.getAmps() * Math.min((box.clientHeat - 27.0) / (3000.0 - 27.0), 1),
					Constants.COALGENERATOR_MAX_OUTPUT.getVoltage());
			font.draw(matrixStack, new TranslatableComponent("gui.coalgenerator.timeleft", box.clientBurnTime / 20 + "s"), inventoryLabelX + 60f,
					inventoryLabelY - 53f, 4210752);
			font.draw(matrixStack,
					new TranslatableComponent("gui.machine.current", ChatFormatter.getChatDisplayShort(output.getAmps(), DisplayUnit.AMPERE)),
					inventoryLabelX + 60f, inventoryLabelY - 40f, 4210752);
			font.draw(matrixStack,
					new TranslatableComponent("gui.machine.output", ChatFormatter.getChatDisplayShort(output.getWatts(), DisplayUnit.WATT)),
					inventoryLabelX + 60f, inventoryLabelY - 27f, 4210752);
			font.draw(matrixStack,
					new TranslatableComponent("gui.machine.voltage",
							ChatFormatter.getChatDisplayShort(output.getVoltage(), DisplayUnit.VOLTAGE)),
					inventoryLabelX + 60f, inventoryLabelY - 14f, 4210752);
		}
	}
}