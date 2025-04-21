package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerChargerGeneric;
import electrodynamics.common.tile.machines.charger.GenericTileCharger;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.item.IItemElectric;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.ScreenComponentMultiLabel;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.types.wrapper.WrapperInventoryIO;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.utilities.math.Color;

public class ScreenChargerGeneric extends GenericScreen<ContainerChargerGeneric> {

	public ScreenChargerGeneric(ContainerChargerGeneric screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);

		addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.BATTERY_CHARGE_RIGHT, () -> {
			GenericTileCharger charger = menu.getSafeHost();
			if (charger != null) {
				ItemStack chargingItem = menu.getSlot(0).getItem();
				if (!chargingItem.isEmpty() && chargingItem.getItem() instanceof IItemElectric electricItem) {
					return electricItem.getJoulesStored(chargingItem) / electricItem.getMaximumCapacity(chargingItem);
				}
			}
			return 0;
		}, 118, 37));

		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2).wattage(e -> e.getMaxJoulesStored() * 20));
		addComponent(new ScreenComponentMultiLabel(0, 0, graphics -> {

			GenericTileCharger charger = menu.getSafeHost();

			if (charger == null) {
				return;
			}

			ItemStack chargingItem = menu.getSlot(0).getItem();

			double chargingPercentage = 0;
			double chargeCapable = 100.0;

			if (!chargingItem.isEmpty() && chargingItem.getItem() instanceof IItemElectric electricItem) {

				chargingPercentage = electricItem.getJoulesStored(chargingItem) / electricItem.getElectricProperties().capacity * 100;
				chargingPercentage = electricItem.getJoulesStored(chargingItem) / electricItem.getMaximumCapacity(chargingItem) * 100;
			}

			graphics.drawString(font, ElectroTextUtils.gui("genericcharger.chargeperc", ChatFormatter.getChatDisplayShort(chargingPercentage, DisplayUnits.PERCENTAGE)).withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.DARK_GRAY), inventoryLabelX, 33, Color.BLACK.color(), false);

			Component capable = Component.empty();

			if (chargeCapable < 33) {
				capable = getChargeCapableFormatted(chargeCapable, ChatFormatting.RED);
			} else if (chargeCapable < 66) {
				capable = getChargeCapableFormatted(chargeCapable, ChatFormatting.YELLOW);
			} else {
				capable = getChargeCapableFormatted(chargeCapable, ChatFormatting.GREEN);
			}

			graphics.drawString(font, capable, inventoryLabelX, 43, Color.BLACK.color(), false);

		}));

		new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82, 8, 72);
	}

	private Component getChargeCapableFormatted(double chargeCapable, ChatFormatting formatColor) {
		return ElectroTextUtils.gui("genericcharger.chargecapable", ChatFormatter.getChatDisplayShort(chargeCapable, DisplayUnits.PERCENTAGE)).withStyle(formatColor).withStyle(ChatFormatting.DARK_GRAY);
	}

}
