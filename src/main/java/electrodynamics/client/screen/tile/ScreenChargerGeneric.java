package electrodynamics.client.screen.tile;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.common.inventory.container.tile.ContainerChargerGeneric;
import electrodynamics.common.tile.generic.GenericTileCharger;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentCharge;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentInfo;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class ScreenChargerGeneric extends GenericScreen<ContainerChargerGeneric> {

	private static DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("#0.0");

	public ScreenChargerGeneric(ContainerChargerGeneric screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);

		components.add(new ScreenComponentCharge(() -> {
			GenericTileCharger charger = menu.getHostFromIntArray();
			if (charger != null) {
				ItemStack chargingItem = menu.getSlot(0).getItem();
				if (!chargingItem.isEmpty() && chargingItem.getItem() instanceof IItemElectric electricItem) {
					return electricItem.getJoulesStored(chargingItem) / electricItem.getElectricProperties().capacity;
				}
			}
			return 0;
		}, this, 118, 37));

		components.add(new ScreenComponentElectricInfo(this::getEnergyInformation, this, -ScreenComponentInfo.SIZE + 1, 2));
	}

	@Override
	protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
		super.renderLabels(matrixStack, mouseX, mouseY);
		List<? extends Component> screenOverlays = getChargerInfo();

		if (!screenOverlays.isEmpty()) {
			font.draw(matrixStack, screenOverlays.get(0), inventoryLabelX, 33f, 0);
			font.draw(matrixStack, screenOverlays.get(1), inventoryLabelX, 43f, 0);
		} else {
			onClose();
		}

	}

	private List<? extends Component> getChargerInfo() {
		ArrayList<Component> list = new ArrayList<>();
		GenericTileCharger charger = menu.getHostFromIntArray();
		if (charger != null) {

			ItemStack chargingItem = menu.getSlot(0).getItem();
			double chargingPercentage = 0;
			double chargeCapable = 100.0;
			if (!chargingItem.isEmpty() && chargingItem.getItem() instanceof IItemElectric electricItem) {

				ComponentElectrodynamic electro = charger.getComponent(ComponentType.Electrodynamic);

				chargingPercentage = electricItem.getJoulesStored(chargingItem) / electricItem.getElectricProperties().capacity * 100;
				chargeCapable = electro.getVoltage() / electricItem.getElectricProperties().receive.getVoltage() * 100;
			}

			list.add(new TranslatableComponent("gui.genericcharger.chargeperc",
					new TextComponent(DECIMAL_FORMATTER.format(chargingPercentage) + "%").withStyle(ChatFormatting.DARK_GRAY))
							.withStyle(ChatFormatting.DARK_GRAY));

			if (chargeCapable < 33) {
				list.add(getChargeCapableFormatted(chargeCapable, ChatFormatting.RED));
			} else if (chargeCapable < 66) {
				list.add(getChargeCapableFormatted(chargeCapable, ChatFormatting.YELLOW));
			} else {
				list.add(getChargeCapableFormatted(chargeCapable, ChatFormatting.GREEN));
			}

		}
		return list;
	}

	private List<? extends FormattedCharSequence> getEnergyInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		GenericTile box = menu.getHostFromIntArray();
		if (box != null) {
			ComponentElectrodynamic electro = box.getComponent(ComponentType.Electrodynamic);

			list.add(new TranslatableComponent("gui.o2oprocessor.usage",
					new TextComponent(ChatFormatter.getElectricDisplayShort(electro.getMaxJoulesStored() * 20, ElectricUnit.WATT))
							.withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(new TranslatableComponent("gui.o2oprocessor.voltage",
					new TextComponent(ChatFormatter.getElectricDisplayShort(electro.getVoltage(), ElectricUnit.VOLTAGE))
							.withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		}
		return list;
	}

	private static Component getChargeCapableFormatted(double chargeCapable, ChatFormatting formatColor) {
		return new TranslatableComponent("gui.genericcharger.chargecapable",
				new TextComponent(DECIMAL_FORMATTER.format(chargeCapable) + "%").withStyle(formatColor)).withStyle(ChatFormatting.DARK_GRAY);
	}

}
