package electrodynamics.client.screen.tile;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.inventory.container.tile.ContainerCarbyneBatteryBox;
import electrodynamics.common.tile.battery.TileCarbyneBatteryBox;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.TextUtils;
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
		components.add(new ScreenComponentElectricInfo(this::getElectricInformation, this, -AbstractScreenComponentInfo.SIZE + 1, 2));
	}

	private List<? extends FormattedCharSequence> getElectricInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		TileCarbyneBatteryBox box = menu.getHostFromIntArray();
		if (box != null) {
			ComponentElectrodynamic el = box.getComponent(ComponentType.Electrodynamic);
			list.add(TextUtils.gui("machine.current", Component.literal(ChatFormatter.getChatDisplayShort(box.powerOutput.get() * 20.0 * box.currentCapacityMultiplier.get() / el.getVoltage(), DisplayUnit.AMPERE)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(TextUtils.gui("machine.transfer", Component.literal(ChatFormatter.getChatDisplayShort(box.powerOutput.get() * 20.0 * box.currentCapacityMultiplier.get(), DisplayUnit.WATT)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(TextUtils.gui("machine.voltage", Component.literal(ChatFormatter.getChatDisplayShort(el.getVoltage(), DisplayUnit.VOLTAGE)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(TextUtils.gui("machine.stored", Component.literal(ChatFormatter.getChatDisplayShort(el.getJoulesStored(), DisplayUnit.JOULES) + " / " + ChatFormatter.getChatDisplayShort(box.maxJoules.get() * box.currentCapacityMultiplier.get(), DisplayUnit.JOULES)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		}
		return list;
	}

	@Override
	protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
		super.renderLabels(matrixStack, mouseX, mouseY);
		TileCarbyneBatteryBox box = menu.getHostFromIntArray();
		if (box != null) {
			ComponentElectrodynamic el = box.getComponent(ComponentType.Electrodynamic);
			font.draw(matrixStack, TextUtils.gui("machine.current", ChatFormatter.getChatDisplayShort(box.powerOutput.get() * 20.0 * box.currentCapacityMultiplier.get() / el.getVoltage(), DisplayUnit.AMPERE)), inventoryLabelX, inventoryLabelY - 55f, 4210752);
			font.draw(matrixStack, TextUtils.gui("machine.transfer", ChatFormatter.getChatDisplayShort(box.powerOutput.get() * 20.0 * box.currentCapacityMultiplier.get(), DisplayUnit.WATT)), inventoryLabelX, inventoryLabelY - 42f, 4210752);
			font.draw(matrixStack, TextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(el.getVoltage(), DisplayUnit.VOLTAGE)), inventoryLabelX, inventoryLabelY - 29f, 4210752);
			font.draw(matrixStack, TextUtils.gui("machine.stored", ChatFormatter.getChatDisplayShort(el.getJoulesStored(), DisplayUnit.JOULES) + " / " + ChatFormatter.getChatDisplayShort(box.maxJoules.get() * box.currentCapacityMultiplier.get(), DisplayUnit.JOULES)), inventoryLabelX, inventoryLabelY - 16f, 4210752);
		}
	}
}