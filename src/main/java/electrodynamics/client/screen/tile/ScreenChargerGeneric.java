package electrodynamics.client.screen.tile;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.item.IItemElectric;
import electrodynamics.common.inventory.container.tile.ContainerChargerGeneric;
import electrodynamics.common.tile.charger.GenericTileCharger;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentProgress;
import electrodynamics.prefab.screen.component.ScreenComponentProgress.ProgressBars;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class ScreenChargerGeneric extends GenericScreen<ContainerChargerGeneric> {

	private static final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("#0.0");

	public ScreenChargerGeneric(ContainerChargerGeneric screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);

		addComponent(new ScreenComponentProgress(ProgressBars.BATTERY_CHARGE_RIGHT, () -> {
			GenericTileCharger charger = menu.getHostFromIntArray();
			if (charger != null) {
				ItemStack chargingItem = menu.getSlot(0).getItem();
				if (!chargingItem.isEmpty() && chargingItem.getItem() instanceof IItemElectric electricItem) {
					return electricItem.getJoulesStored(chargingItem) / electricItem.getElectricProperties().capacity;
				}
			}
			return 0;
		}, 118, 37));

		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2).wattage(e -> e.getMaxJoulesStored() * 20));
	}

	@Override
	protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
		super.renderLabels(matrixStack, mouseX, mouseY);
		List<? extends Component> screenOverlays = getChargerInfo();

		if (!screenOverlays.isEmpty()) {
			font.draw(matrixStack, screenOverlays.get(0), inventoryLabelX, 33f, 0);
			font.draw(matrixStack, screenOverlays.get(1), inventoryLabelX, 43f, 0);
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

			list.add(TextUtils.gui("genericcharger.chargeperc", Component.literal(DECIMAL_FORMATTER.format(chargingPercentage) + "%").withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.DARK_GRAY));

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

	private static Component getChargeCapableFormatted(double chargeCapable, ChatFormatting formatColor) {
		return TextUtils.gui("genericcharger.chargecapable", Component.literal(DECIMAL_FORMATTER.format(chargeCapable) + "%").withStyle(formatColor)).withStyle(ChatFormatting.DARK_GRAY);
	}

}
