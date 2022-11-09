package electrodynamics.client.screen.tile;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.inventory.container.tile.ContainerBatteryBox;
import electrodynamics.common.tile.TileBatteryBox;
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
public class ScreenBatteryBox extends GenericScreen<ContainerBatteryBox> {
	public ScreenBatteryBox(ContainerBatteryBox container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		components.add(new ScreenComponentElectricInfo(this::getElectricInformation, this, -ScreenComponentInfo.SIZE + 1, 2));
	}

	private List<? extends FormattedCharSequence> getElectricInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		TileBatteryBox box = menu.getHostFromIntArray();
		if (box != null) {
			list.add(Component.translatable("gui.machine.current", Component.literal(ChatFormatter.getChatDisplayShort(box.powerOutput * 20.0 * box.currentCapacityMultiplier / box.clientVoltage, DisplayUnit.AMPERE)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(Component.translatable("gui.machine.transfer", Component.literal(ChatFormatter.getChatDisplayShort(box.powerOutput * 20.0 * box.currentCapacityMultiplier, DisplayUnit.WATT)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(Component.translatable("gui.machine.voltage", Component.literal(ChatFormatter.getChatDisplayShort(box.clientVoltage, DisplayUnit.VOLTAGE)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(Component.translatable("gui.machine.stored", Component.literal(ChatFormatter.getChatDisplayShort(box.clientJoules, DisplayUnit.JOULES) + " / " + ChatFormatter.getChatDisplayShort(box.maxJoules * box.currentCapacityMultiplier, DisplayUnit.JOULES)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		}
		return list;
	}

	@Override
	protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
		super.renderLabels(matrixStack, mouseX, mouseY);
		TileBatteryBox box = menu.getHostFromIntArray();
		if (box != null) {
			font.draw(matrixStack, Component.translatable("gui.machine.current", ChatFormatter.getChatDisplayShort(box.powerOutput * 20.0 * box.currentCapacityMultiplier / box.clientVoltage, DisplayUnit.AMPERE)), inventoryLabelX, inventoryLabelY - 55f, 4210752);
			font.draw(matrixStack, Component.translatable("gui.machine.transfer", ChatFormatter.getChatDisplayShort(box.powerOutput * 20.0 * box.currentCapacityMultiplier, DisplayUnit.WATT)), inventoryLabelX, inventoryLabelY - 42f, 4210752);
			font.draw(matrixStack, Component.translatable("gui.machine.voltage", ChatFormatter.getChatDisplayShort(box.clientVoltage, DisplayUnit.VOLTAGE)), inventoryLabelX, inventoryLabelY - 29f, 4210752);
			font.draw(matrixStack, Component.translatable("gui.machine.stored", ChatFormatter.getChatDisplayShort(box.clientJoules, DisplayUnit.JOULES) + " / " + ChatFormatter.getChatDisplayShort(box.maxJoules * box.currentCapacityMultiplier, DisplayUnit.JOULES)), inventoryLabelX, inventoryLabelY - 16f, 4210752);
		}
	}
}