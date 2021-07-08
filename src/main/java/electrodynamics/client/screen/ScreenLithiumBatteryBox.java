package electrodynamics.client.screen;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.common.inventory.container.ContainerLithiumBatteryBox;
import electrodynamics.common.tile.TileLithiumBatteryBox;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentInfo;
import electrodynamics.prefab.screen.component.ScreenComponentSlot;
import electrodynamics.prefab.screen.component.ScreenComponentSlot.EnumSlotType;
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
public class ScreenLithiumBatteryBox extends GenericScreen<ContainerLithiumBatteryBox> {
    public ScreenLithiumBatteryBox(ContainerLithiumBatteryBox container, PlayerInventory playerInventory, ITextComponent title) {
	super(container, playerInventory, title);
	components.add(new ScreenComponentElectricInfo(this::getElectricInformation, this, -ScreenComponentInfo.SIZE + 1, 2));
    }

    private List<? extends ITextProperties> getElectricInformation() {
	ArrayList<ITextProperties> list = new ArrayList<>();
	TileLithiumBatteryBox box = container.getHostFromIntArray();
	if (box != null) {
	    list.add(new TranslationTextComponent("gui.lithiumbatterybox.current",
		    new StringTextComponent(ChatFormatter
			    .getElectricDisplayShort(box.powerOutput * 20.0 * box.currentCapacityMultiplier / box.clientVoltage, ElectricUnit.AMPERE))
				    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	    list.add(new TranslationTextComponent("gui.lithiumbatterybox.transfer",
		    new StringTextComponent(
			    ChatFormatter.getElectricDisplayShort(box.powerOutput * 20.0 * box.currentCapacityMultiplier, ElectricUnit.WATT))
				    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	    list.add(new TranslationTextComponent("gui.lithiumbatterybox.voltage",
		    new StringTextComponent(ChatFormatter.getElectricDisplayShort(box.clientVoltage, ElectricUnit.VOLTAGE))
			    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	    list.add(new TranslationTextComponent("gui.lithiumbatterybox.stored",
		    new StringTextComponent(ChatFormatter.getElectricDisplayShort(box.clientJoules, ElectricUnit.JOULES) + " / "
			    + ChatFormatter.getElectricDisplayShort(box.maxJoules * box.currentCapacityMultiplier, ElectricUnit.JOULES))
				    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	}
	return list;
    }

    @Override
    protected ScreenComponentSlot createScreenSlot(Slot slot) {
	return new ScreenComponentSlot(slot instanceof SlotRestricted ? EnumSlotType.BATTERY : EnumSlotType.NORMAL, this, slot.xPos - 1,
		slot.yPos - 1);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
	super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
	TileLithiumBatteryBox box = container.getHostFromIntArray();
	if (box != null) {
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.lithiumbatterybox.current", ChatFormatter.getElectricDisplayShort(
			    box.powerOutput * 20.0 * box.currentCapacityMultiplier / box.clientVoltage, ElectricUnit.AMPERE)),
		    playerInventoryTitleX, playerInventoryTitleY - 55f, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.lithiumbatterybox.transfer",
			    ChatFormatter.getElectricDisplayShort(box.powerOutput * 20.0 * box.currentCapacityMultiplier, ElectricUnit.WATT)),
		    playerInventoryTitleX, playerInventoryTitleY - 42f, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.lithiumbatterybox.voltage",
			    ChatFormatter.getElectricDisplayShort(box.clientVoltage, ElectricUnit.VOLTAGE)),
		    playerInventoryTitleX, playerInventoryTitleY - 29f, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.lithiumbatterybox.stored",
			    ChatFormatter.getElectricDisplayShort(box.clientJoules, ElectricUnit.JOULES) + " / "
				    + ChatFormatter.getElectricDisplayShort(box.maxJoules * box.currentCapacityMultiplier, ElectricUnit.JOULES)),
		    playerInventoryTitleX, playerInventoryTitleY - 16f, 4210752);
	}
    }
}