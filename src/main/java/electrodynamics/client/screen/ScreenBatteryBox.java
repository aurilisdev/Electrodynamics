package electrodynamics.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.References;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.api.electricity.formatting.ElectricityChatFormatter;
import electrodynamics.client.screen.generic.GenericContainerScreenUpgradeable;
import electrodynamics.common.inventory.container.ContainerBatteryBox;
import electrodynamics.common.tile.TileBatteryBox;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenBatteryBox extends GenericContainerScreenUpgradeable<ContainerBatteryBox> {
    public static final ResourceLocation SCREEN_BACKGROUND = new ResourceLocation(References.ID + ":textures/gui/batterybox.png");

    public ScreenBatteryBox(ContainerBatteryBox container, PlayerInventory playerInventory, ITextComponent title) {
	super(container, playerInventory, title);
	xSize = 176;
    }

    @Override
    public ResourceLocation getScreenBackground() {
	return SCREEN_BACKGROUND;
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