package electrodynamics.client.screen.tile;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.api.electricity.generator.IElectricGenerator;
import electrodynamics.common.inventory.container.tile.ContainerSolarPanel;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentInfo;
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
public class ScreenSolarPanel extends GenericScreen<ContainerSolarPanel> {

	public ScreenSolarPanel(ContainerSolarPanel container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		components.add(new ScreenComponentElectricInfo(this::getEnergyInformation, this, -ScreenComponentInfo.SIZE + 1, 2));
	}

	private List<? extends FormattedCharSequence> getEnergyInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		if (menu.getUnsafeHost() instanceof IElectricGenerator gen) {
			TransferPack transfer = gen.getProduced();
			list.add(new TranslatableComponent("gui.solarpanel.current",
					new TextComponent(ChatFormatter.getElectricDisplayShort(transfer.getAmps(), ElectricUnit.AMPERE)).withStyle(ChatFormatting.GRAY))
							.withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(new TranslatableComponent("gui.solarpanel.output",
					new TextComponent(ChatFormatter.getElectricDisplayShort(transfer.getWatts(), ElectricUnit.WATT)).withStyle(ChatFormatting.GRAY))
							.withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(new TranslatableComponent("gui.solarpanel.voltage",
					new TextComponent(ChatFormatter.getElectricDisplayShort(transfer.getVoltage(), ElectricUnit.VOLTAGE))
							.withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		}
		return list;
	}

	@Override
	protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
		super.renderLabels(matrixStack, mouseX, mouseY);
		if (menu.getUnsafeHost() instanceof IElectricGenerator gen) {
			TransferPack transfer = gen.getProduced();
			font.draw(matrixStack,
					new TranslatableComponent("gui.solarpanel.current",
							ChatFormatter.getElectricDisplayShort(transfer.getAmps(), ElectricUnit.AMPERE)),
					(float) inventoryLabelX + 60, (float) inventoryLabelY - 48, 4210752);
			font.draw(matrixStack,
					new TranslatableComponent("gui.solarpanel.output", ChatFormatter.getElectricDisplayShort(transfer.getWatts(), ElectricUnit.WATT)),
					(float) inventoryLabelX + 60, (float) inventoryLabelY - 35, 4210752);
			font.draw(matrixStack,
					new TranslatableComponent("gui.solarpanel.voltage",
							ChatFormatter.getElectricDisplayShort(transfer.getVoltage(), ElectricUnit.VOLTAGE)),
					(float) inventoryLabelX + 60, (float) inventoryLabelY - 22, 4210752);
		}
	}
}