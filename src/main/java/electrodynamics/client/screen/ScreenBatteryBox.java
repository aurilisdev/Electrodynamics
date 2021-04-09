package electrodynamics.client.screen;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.api.electricity.formatting.ElectricityChatFormatter;
import electrodynamics.api.gui.GenericScreen;
import electrodynamics.api.gui.component.GuiComponentElectricInfo;
import electrodynamics.api.gui.component.GuiComponentInfo;
import electrodynamics.api.gui.component.GuiComponentSlot;
import electrodynamics.api.gui.component.GuiComponentSlot.EnumSlotType;
import electrodynamics.common.inventory.container.ContainerBatteryBox;
import electrodynamics.common.inventory.container.slot.SlotRestricted;
import electrodynamics.common.tile.TileBatteryBox;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenBatteryBox extends GenericScreen<ContainerBatteryBox> {
    public ScreenBatteryBox(ContainerBatteryBox container, PlayerInventory playerInventory, ITextComponent title) {
	super(container, playerInventory, title);
	components.add(new GuiComponentElectricInfo(this::getElectricInformation, this, -GuiComponentInfo.SIZE + 1, 2));
    }

    private List<? extends ITextProperties> getElectricInformation() {
	ArrayList<ITextProperties> list = new ArrayList<>();
	TileBatteryBox box = container.getHostFromIntArray();
	if (box != null) {
	    list.add(new TranslationTextComponent("gui.batterybox.current",
		    new StringTextComponent(ElectricityChatFormatter.getDisplayShort(
			    TileBatteryBox.DEFAULT_OUTPUT_JOULES_PER_TICK * 20.0 * box.currentCapacityMultiplier / box.clientVoltage,
			    ElectricUnit.AMPERE)).mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	    list.add(new TranslationTextComponent("gui.batterybox.transfer",
		    new StringTextComponent(ElectricityChatFormatter
			    .getDisplayShort(TileBatteryBox.DEFAULT_OUTPUT_JOULES_PER_TICK * 20.0 * box.currentCapacityMultiplier, ElectricUnit.WATT))
				    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	    list.add(new TranslationTextComponent("gui.batterybox.voltage",
		    new StringTextComponent(ElectricityChatFormatter.getDisplayShort(box.clientVoltage, ElectricUnit.VOLTAGE))
			    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	    list.add(
		    new TranslationTextComponent("gui.batterybox.stored",
			    new StringTextComponent(
				    ElectricityChatFormatter.getDisplayShort(box.clientJoules, ElectricUnit.JOULES) + " / "
					    + ElectricityChatFormatter.getDisplayShort(
						    TileBatteryBox.DEFAULT_MAX_JOULES * box.currentCapacityMultiplier, ElectricUnit.JOULES))
							    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	}
	return list;
    }

    @Override
    protected GuiComponentSlot createGuiSlot(Slot slot) {
	return new GuiComponentSlot(slot instanceof SlotRestricted ? EnumSlotType.BATTERY : EnumSlotType.NORMAL, this, slot.xPos - 1, slot.yPos - 1);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
	super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
	TileBatteryBox box = container.getHostFromIntArray();
	if (box != null) {
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.batterybox.current",
			    ElectricityChatFormatter.getDisplayShort(
				    TileBatteryBox.DEFAULT_OUTPUT_JOULES_PER_TICK * 20.0 * box.currentCapacityMultiplier / box.clientVoltage,
				    ElectricUnit.AMPERE)),
		    playerInventoryTitleX, playerInventoryTitleY - 55f, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.batterybox.transfer",
			    ElectricityChatFormatter.getDisplayShort(
				    TileBatteryBox.DEFAULT_OUTPUT_JOULES_PER_TICK * 20.0 * box.currentCapacityMultiplier, ElectricUnit.WATT)),
		    playerInventoryTitleX, playerInventoryTitleY - 42f, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.batterybox.voltage",
			    ElectricityChatFormatter.getDisplayShort(box.clientVoltage, ElectricUnit.VOLTAGE)),
		    playerInventoryTitleX, playerInventoryTitleY - 29f, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.batterybox.stored",
			    ElectricityChatFormatter.getDisplayShort(box.clientJoules, ElectricUnit.JOULES) + " / " + ElectricityChatFormatter
				    .getDisplayShort(TileBatteryBox.DEFAULT_MAX_JOULES * box.currentCapacityMultiplier, ElectricUnit.JOULES)),
		    playerInventoryTitleX, playerInventoryTitleY - 16f, 4210752);
	}
    }
}