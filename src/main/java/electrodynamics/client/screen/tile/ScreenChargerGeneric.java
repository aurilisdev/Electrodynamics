package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerChargerGeneric;
import electrodynamics.common.tile.machines.charger.GenericTileCharger;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.item.IItemElectric;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.ScreenComponentMultiLabel;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.types.wrapper.WrapperInventoryIO;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.utilities.VoltaicTextUtils;
import voltaic.prefab.utilities.math.Color;

public class ScreenChargerGeneric extends GenericScreen<ContainerChargerGeneric> {

	public ScreenChargerGeneric(ContainerChargerGeneric screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);

		addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.BATTERY_CHARGE_RIGHT, () -> {
			GenericTileCharger charger = menu.getSafeHost();
			if (charger != null) {
				ItemStack chargingItem = menu.getSlot(0).getItem();
				if (!chargingItem.isEmpty() && chargingItem.getItem() instanceof IItemElectric) {
					IItemElectric electricItem = (IItemElectric) chargingItem.getItem();
					return electricItem.getJoulesStored(chargingItem) / electricItem.getMaximumCapacity(chargingItem);
				}
			}
			return 0;
		}, 118, 37));

		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2).wattage(e -> e.getMaxJoulesStored() * 20));
		addComponent(new ScreenComponentMultiLabel(0, 0, poseStack -> {

			GenericTileCharger charger = menu.getSafeHost();

			if (charger == null) {
				return;
			}

			ItemStack chargingItem = menu.getSlot(0).getItem();

			double chargingPercentage = 0;
			double chargeCapable = 100.0;

			if (!chargingItem.isEmpty() && chargingItem.getItem() instanceof IItemElectric) {
				IItemElectric electricItem = (IItemElectric) chargingItem.getItem();

				chargingPercentage = electricItem.getJoulesStored(chargingItem) / electricItem.getElectricProperties().capacity * 100;
				chargingPercentage = electricItem.getJoulesStored(chargingItem) / electricItem.getMaximumCapacity(chargingItem) * 100;
			}

			font.draw(poseStack, ElectroTextUtils.gui("genericcharger.chargeperc", ChatFormatter.getChatDisplayShort(chargingPercentage, DisplayUnits.PERCENTAGE)).withStyle(TextFormatting.DARK_GRAY).withStyle(TextFormatting.DARK_GRAY), inventoryLabelX, 33, Color.BLACK.color());

			ITextComponent capable = VoltaicTextUtils.empty();

			if (chargeCapable < 33) {
				capable = getChargeCapableFormatted(chargeCapable, TextFormatting.RED);
			} else if (chargeCapable < 66) {
				capable = getChargeCapableFormatted(chargeCapable, TextFormatting.YELLOW);
			} else {
				capable = getChargeCapableFormatted(chargeCapable, TextFormatting.GREEN);
			}

			font.draw(poseStack, capable, inventoryLabelX, 43, Color.BLACK.color());

		}));

		new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82, 8, 72);
	}

	private ITextComponent getChargeCapableFormatted(double chargeCapable, TextFormatting formatColor) {
		return ElectroTextUtils.gui("genericcharger.chargecapable", ChatFormatter.getChatDisplayShort(chargeCapable, DisplayUnits.PERCENTAGE)).withStyle(formatColor).withStyle(TextFormatting.DARK_GRAY);
	}

}
