package electrodynamics.client.screen.tile;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.common.inventory.container.tile.ContainerLithiumBatteryBox;
import electrodynamics.common.tile.TileLithiumBatteryBox;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenLithiumBatteryBox extends GenericScreen<ContainerLithiumBatteryBox> {
	public ScreenLithiumBatteryBox(ContainerLithiumBatteryBox container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		components.add(new ScreenComponentElectricInfo(this::getElectricInformation, this, -ScreenComponentInfo.SIZE + 1, 2));
	}

	private List<? extends FormattedCharSequence> getElectricInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		TileLithiumBatteryBox box = menu.getHostFromIntArray();
		if (box != null) {
			list.add(new TranslatableComponent("gui.lithiumbatterybox.current",
					new TextComponent(ChatFormatter
							.getElectricDisplayShort(box.powerOutput * 20.0 * box.currentCapacityMultiplier / box.clientVoltage, ElectricUnit.AMPERE))
									.withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(new TranslatableComponent("gui.lithiumbatterybox.transfer",
					new TextComponent(
							ChatFormatter.getElectricDisplayShort(box.powerOutput * 20.0 * box.currentCapacityMultiplier, ElectricUnit.WATT))
									.withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(new TranslatableComponent("gui.lithiumbatterybox.voltage",
					new TextComponent(ChatFormatter.getElectricDisplayShort(box.clientVoltage, ElectricUnit.VOLTAGE)).withStyle(ChatFormatting.GRAY))
							.withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(new TranslatableComponent("gui.lithiumbatterybox.stored",
					new TextComponent(ChatFormatter.getElectricDisplayShort(box.clientJoules, ElectricUnit.JOULES) + " / "
							+ ChatFormatter.getElectricDisplayShort(box.maxJoules * box.currentCapacityMultiplier, ElectricUnit.JOULES))
									.withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		}
		return list;
	}

	@Override
	protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
		super.renderLabels(matrixStack, mouseX, mouseY);
		TileLithiumBatteryBox box = menu.getHostFromIntArray();
		if (box != null) {
			font.draw(matrixStack,
					new TranslatableComponent("gui.lithiumbatterybox.current", ChatFormatter.getElectricDisplayShort(
							box.powerOutput * 20.0 * box.currentCapacityMultiplier / box.clientVoltage, ElectricUnit.AMPERE)),
					inventoryLabelX, inventoryLabelY - 55f, 4210752);
			font.draw(matrixStack,
					new TranslatableComponent("gui.lithiumbatterybox.transfer",
							ChatFormatter.getElectricDisplayShort(box.powerOutput * 20.0 * box.currentCapacityMultiplier, ElectricUnit.WATT)),
					inventoryLabelX, inventoryLabelY - 42f, 4210752);
			font.draw(matrixStack,
					new TranslatableComponent("gui.lithiumbatterybox.voltage",
							ChatFormatter.getElectricDisplayShort(box.clientVoltage, ElectricUnit.VOLTAGE)),
					inventoryLabelX, inventoryLabelY - 29f, 4210752);
			font.draw(matrixStack,
					new TranslatableComponent("gui.lithiumbatterybox.stored",
							ChatFormatter.getElectricDisplayShort(box.clientJoules, ElectricUnit.JOULES) + " / "
									+ ChatFormatter.getElectricDisplayShort(box.maxJoules * box.currentCapacityMultiplier, ElectricUnit.JOULES)),
					inventoryLabelX, inventoryLabelY - 16f, 4210752);
		}
	}
}