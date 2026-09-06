package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerChargerGeneric;
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
import voltaic.prefab.utilities.math.Color;

public class ScreenChargerGeneric extends GenericScreen<ContainerChargerGeneric> {

    public ScreenChargerGeneric(ContainerChargerGeneric screenContainer, Inventory inv, Component titleIn) {
	super(screenContainer, inv, titleIn);
	addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.BATTERY_CHARGE_RIGHT, () -> {
	    if (menu.getSafeHost().isEmpty())
		return 0.0;
	    ItemStack chargingItem = menu.getSlot(0).getItem();
	    if (!chargingItem.isEmpty() && chargingItem.getItem() instanceof IItemElectric electricItem)
		return electricItem.getJoulesStored(chargingItem) / electricItem.getMaximumCapacity(chargingItem);
	    return 0.0;
	}, 118, 37));
	addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2)
		.wattage(e -> e.getMaxJoulesStored() * 20));
	addComponent(new ScreenComponentMultiLabel(0, 0, graphics -> {
	    if (menu.getSafeHost().isEmpty())
		return;
	    ItemStack chargingItem = menu.getSlot(0).getItem();
	    double chargingPercentage = 0;
	    double chargeCapable = 100.0;
	    if (!chargingItem.isEmpty() && chargingItem.getItem() instanceof IItemElectric electricItem)
		chargingPercentage = electricItem.getJoulesStored(chargingItem)
			/ electricItem.getMaximumCapacity(chargingItem) * 100;
	    graphics.drawString(font,
		    ElectroTextUtils
			    .gui("genericcharger.chargeperc",
				    ChatFormatter.getChatDisplayShort(chargingPercentage, DisplayUnits.PERCENTAGE))
			    .withStyle(ChatFormatting.DARK_GRAY),
		    inventoryLabelX, 33, Color.BLACK.color(), false);
	    ChatFormatting colour = chargeCapable < 33 ? ChatFormatting.RED
		    : chargeCapable < 66 ? ChatFormatting.YELLOW : ChatFormatting.GREEN;
	    graphics.drawString(font, getChargeCapableFormatted(chargeCapable, colour), inventoryLabelX, 43,
		    Color.BLACK.color(), false);
	}));
	new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75,
		82, 8, 72);
    }

    private static Component getChargeCapableFormatted(double chargeCapable, ChatFormatting formatColor) {
	return ElectroTextUtils
		.gui("genericcharger.chargecapable",
			ChatFormatter.getChatDisplayShort(chargeCapable, DisplayUnits.PERCENTAGE))
		.withStyle(formatColor).withStyle(ChatFormatting.DARK_GRAY);
    }
}