package electrodynamics.client.screen;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.common.inventory.container.ContainerChargerGeneric;
import electrodynamics.common.tile.generic.TileGenericCharger;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentCharge;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentInfo;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ScreenChargerGeneric extends GenericScreen<ContainerChargerGeneric> {

    private static DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("#0.0");

    public ScreenChargerGeneric(ContainerChargerGeneric screenContainer, PlayerInventory inv, ITextComponent titleIn) {
	super(screenContainer, inv, titleIn);

	components.add(new ScreenComponentCharge(() -> {
	    TileGenericCharger charger = container.getHostFromIntArray();
	    if (charger != null) {
		ItemStack chargingItem = container.getSlot(0).getStack();
		if (!chargingItem.isEmpty() && chargingItem.getItem() instanceof IItemElectric) {
		    IItemElectric electricItem = (IItemElectric) chargingItem.getItem();
		    return electricItem.getJoulesStored(chargingItem) / electricItem.getProperties().capacity;
		}
	    }
	    return 0;
	}, this, 118, 37));

	components.add(new ScreenComponentElectricInfo(this::getEnergyInformation, this, -ScreenComponentInfo.SIZE + 1, 2));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
	super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
	List<? extends ITextComponent> screenOverlays = getChargerInfo();

	if (screenOverlays.size() > 0) {
	    font.func_243248_b(matrixStack, screenOverlays.get(0), playerInventoryTitleX, 33f, 0);
	    font.func_243248_b(matrixStack, screenOverlays.get(1), playerInventoryTitleX, 43f, 0);
	} else {
	    closeScreen();
	}

    }

    private List<? extends ITextComponent> getChargerInfo() {
	ArrayList<ITextComponent> list = new ArrayList<>();
	TileGenericCharger charger = container.getHostFromIntArray();
	if (charger != null) {

	    ItemStack chargingItem = container.getSlot(0).getStack();
	    double chargingPercentage = 0;
	    double chargeCapable = 100.0;
	    if (!chargingItem.isEmpty() && chargingItem.getItem() instanceof IItemElectric) {

		ComponentElectrodynamic electro = charger.getComponent(ComponentType.Electrodynamic);
		IItemElectric electricItem = (IItemElectric) chargingItem.getItem();

		chargingPercentage = electricItem.getJoulesStored(chargingItem) / electricItem.getProperties().capacity * 100;
		chargeCapable = electro.getVoltage() / electricItem.getProperties().receive.getVoltage() * 100;
	    }

	    list.add(new TranslationTextComponent("gui.genericcharger.chargeperc",
		    new StringTextComponent(DECIMAL_FORMATTER.format(chargingPercentage) + "%").mergeStyle(TextFormatting.DARK_GRAY))
			    .mergeStyle(TextFormatting.DARK_GRAY));

	    if (chargeCapable < 33) {
		list.add(getChargeCapableFormatted(chargeCapable, TextFormatting.RED));
	    } else if (chargeCapable < 66) {
		list.add(getChargeCapableFormatted(chargeCapable, TextFormatting.YELLOW));
	    } else {
		list.add(getChargeCapableFormatted(chargeCapable, TextFormatting.GREEN));
	    }

	}
	return list;
    }

    private List<? extends ITextProperties> getEnergyInformation() {
	ArrayList<ITextProperties> list = new ArrayList<>();
	GenericTile box = container.getHostFromIntArray();
	if (box != null) {
	    ComponentElectrodynamic electro = box.getComponent(ComponentType.Electrodynamic);

	    list.add(new TranslationTextComponent("gui.o2oprocessor.usage",
		    new StringTextComponent(ChatFormatter.getElectricDisplayShort(electro.getMaxJoulesStored() * 20, ElectricUnit.WATT))
			    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	    list.add(new TranslationTextComponent("gui.o2oprocessor.voltage",
		    new StringTextComponent(ChatFormatter.getElectricDisplayShort(electro.getVoltage(), ElectricUnit.VOLTAGE))
			    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	}
	return list;
    }

    private static ITextComponent getChargeCapableFormatted(double chargeCapable, TextFormatting formatColor) {
	return new TranslationTextComponent("gui.genericcharger.chargecapable",
		new StringTextComponent(DECIMAL_FORMATTER.format(chargeCapable) + "%").mergeStyle(formatColor)).mergeStyle(TextFormatting.DARK_GRAY);
    }

}
