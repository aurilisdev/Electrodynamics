package electrodynamics.client.screen.tile;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.common.inventory.container.tile.ContainerChargerGeneric;
import electrodynamics.common.tile.machines.charger.GenericTileCharger;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentMultiLabel;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress.ProgressBars;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.types.wrapper.InventoryIOWrapper;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public class ScreenChargerGeneric extends GenericScreen<ContainerChargerGeneric> {

	public ScreenChargerGeneric(ContainerChargerGeneric screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);

		addComponent(new ScreenComponentProgress(ProgressBars.BATTERY_CHARGE_RIGHT, () -> {
			GenericTileCharger charger = menu.getHostFromIntArray();
			if (charger != null) {
				ItemStack chargingItem = menu.getSlot(0).getItem();
				if (!chargingItem.isEmpty() && chargingItem.getItem() instanceof IItemElectric) {
					IItemElectric electricItem = (IItemElectric) chargingItem.getItem();
					return electricItem.getJoulesStored(chargingItem) / electricItem.getElectricProperties().capacity;
				}
			}
			return 0;
		}, 118, 37));

		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2).wattage(e -> e.getMaxJoulesStored() * 20));
		addComponent(new ScreenComponentMultiLabel(0, 0, stack -> {

			GenericTileCharger charger = menu.getHostFromIntArray();

			if (charger == null) {
				return;
			}

			ItemStack chargingItem = menu.getSlot(0).getItem();

			double chargingPercentage = 0;
			double chargeCapable = 100.0;

			if (!chargingItem.isEmpty() && chargingItem.getItem() instanceof IItemElectric) {
				IItemElectric electricItem = (IItemElectric) chargingItem.getItem();

				ComponentElectrodynamic electro = charger.getComponent(IComponentType.Electrodynamic);

				chargingPercentage = electricItem.getJoulesStored(chargingItem) / electricItem.getElectricProperties().capacity * 100;
				chargeCapable = electro.getVoltage() / electricItem.getElectricProperties().receive.getVoltage() * 100;
			}

			font.draw(stack, ElectroTextUtils.gui("genericcharger.chargeperc", ChatFormatter.getChatDisplayShort(chargingPercentage, DisplayUnit.PERCENTAGE)).withStyle(TextFormatting.DARK_GRAY).withStyle(TextFormatting.DARK_GRAY), inventoryLabelX, 33, 0);

			ITextComponent capable = ElectroTextUtils.empty();

			if (chargeCapable < 33) {
				capable = getChargeCapableFormatted(chargeCapable, TextFormatting.RED);
			} else if (chargeCapable < 66) {
				capable = getChargeCapableFormatted(chargeCapable, TextFormatting.YELLOW);
			} else {
				capable = getChargeCapableFormatted(chargeCapable, TextFormatting.GREEN);
			}

			font.draw(stack, capable, inventoryLabelX, 43, 0);

		}));

		new InventoryIOWrapper(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82, 8, 72);
	}

	private ITextComponent getChargeCapableFormatted(double chargeCapable, TextFormatting formatColor) {
		return ElectroTextUtils.gui("genericcharger.chargecapable", ChatFormatter.getChatDisplayShort(chargeCapable, DisplayUnit.PERCENTAGE)).withStyle(formatColor).withStyle(TextFormatting.DARK_GRAY);
	}

}