package electrodynamics.client.screen.tile;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.inventory.container.tile.ContainerLithiumBatteryBox;
import electrodynamics.common.tile.TileLithiumBatteryBox;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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
			list.add(Component.translatable("gui.machine.current", Component.literal(ChatFormatter.getChatDisplayShort(box.powerOutput.getValue() * 20.0 * box.currentCapacityMultiplier.getValue() / box.clientVoltage.getValue(), DisplayUnit.AMPERE)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(Component.translatable("gui.machine.transfer", Component.literal(ChatFormatter.getChatDisplayShort(box.powerOutput.getValue() * 20.0 * box.currentCapacityMultiplier.getValue(), DisplayUnit.WATT)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(Component.translatable("gui.machine.voltage", Component.literal(ChatFormatter.getChatDisplayShort(box.clientVoltage.getValue(), DisplayUnit.VOLTAGE)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(Component.translatable("gui.machine.stored", Component.literal(ChatFormatter.getChatDisplayShort(box.clientJoules.getValue(), DisplayUnit.JOULES) + " / " + ChatFormatter.getChatDisplayShort(box.maxJoules.getValue() * box.currentCapacityMultiplier.getValue(), DisplayUnit.JOULES)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		}
		return list;
	}

	@Override
	protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
		super.renderLabels(matrixStack, mouseX, mouseY);
		TileLithiumBatteryBox box = menu.getHostFromIntArray();
		if (box != null) {
			font.draw(matrixStack, Component.translatable("gui.machine.current", ChatFormatter.getChatDisplayShort(box.powerOutput.getValue() * 20.0 * box.currentCapacityMultiplier.getValue() / box.clientVoltage.getValue(), DisplayUnit.AMPERE)), inventoryLabelX, inventoryLabelY - 55f, 4210752);
			font.draw(matrixStack, Component.translatable("gui.machine.transfer", ChatFormatter.getChatDisplayShort(box.powerOutput.getValue() * 20.0 * box.currentCapacityMultiplier.getValue(), DisplayUnit.WATT)), inventoryLabelX, inventoryLabelY - 42f, 4210752);
			font.draw(matrixStack, Component.translatable("gui.machine.voltage", ChatFormatter.getChatDisplayShort(box.clientVoltage.getValue(), DisplayUnit.VOLTAGE)), inventoryLabelX, inventoryLabelY - 29f, 4210752);
			font.draw(matrixStack, Component.translatable("gui.machine.stored", ChatFormatter.getChatDisplayShort(box.clientJoules.getValue(), DisplayUnit.JOULES) + " / " + ChatFormatter.getChatDisplayShort(box.maxJoules.getValue() * box.currentCapacityMultiplier.getValue(), DisplayUnit.JOULES)), inventoryLabelX, inventoryLabelY - 16f, 4210752);
		}
	}
}