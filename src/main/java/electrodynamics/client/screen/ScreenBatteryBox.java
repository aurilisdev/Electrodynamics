package electrodynamics.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.References;
import electrodynamics.api.formatting.ElectricUnit;
import electrodynamics.api.utilities.ElectricityChatFormatter;
import electrodynamics.client.screen.generic.GenericContainerScreenUpgradeable;
import electrodynamics.common.inventory.container.ContainerBatteryBox;
import electrodynamics.common.tile.TileBatteryBox;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenBatteryBox extends GenericContainerScreenUpgradeable<ContainerBatteryBox> implements IHasContainer<ContainerBatteryBox> {
	public static final ResourceLocation SCREEN_BACKGROUND = new ResourceLocation(References.ID + ":textures/gui/batterybox.png");

	public ScreenBatteryBox(ContainerBatteryBox container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title);
	}

	@Override
	public ResourceLocation getScreenBackground() {
		return SCREEN_BACKGROUND;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
		font.func_238422_b_(matrixStack,
				new TranslationTextComponent("gui.batterybox.current",
						ElectricityChatFormatter.getDisplayShort(TileBatteryBox.DEFAULT_OUTPUT_JOULES_PER_TICK * 20.0 / TileBatteryBox.DEFAULT_VOLTAGE, ElectricUnit.AMPERE)),
				playerInventoryTitleX, (float) playerInventoryTitleY - 55, 4210752);
		font.func_238422_b_(matrixStack,
				new TranslationTextComponent("gui.batterybox.transfer",
						ElectricityChatFormatter.getDisplayShort(TileBatteryBox.DEFAULT_OUTPUT_JOULES_PER_TICK * 20.0 * container.getCapacityMultiplier(), ElectricUnit.WATT)),
				playerInventoryTitleX, (float) playerInventoryTitleY - 42, 4210752);
		font.func_238422_b_(matrixStack, new TranslationTextComponent("gui.batterybox.voltage", ElectricityChatFormatter.getDisplayShort(TileBatteryBox.DEFAULT_VOLTAGE, ElectricUnit.VOLTAGE)), playerInventoryTitleX,
				(float) playerInventoryTitleY - 29, 4210752);
		font.func_238422_b_(matrixStack,
				new TranslationTextComponent("gui.batterybox.stored",
						ElectricityChatFormatter.getDisplayShort(container.getJoules(), ElectricUnit.JOULES) + " / "
								+ ElectricityChatFormatter.getDisplayShort(TileBatteryBox.DEFAULT_MAX_JOULES * container.getCapacityMultiplier(), ElectricUnit.JOULES)),
				playerInventoryTitleX, (float) playerInventoryTitleY - 16, 4210752);
	}
}