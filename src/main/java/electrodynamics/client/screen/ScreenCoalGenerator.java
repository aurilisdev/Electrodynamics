package electrodynamics.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.References;
import electrodynamics.api.formatting.ElectricUnit;
import electrodynamics.api.utilities.ElectricityChatFormatter;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.client.screen.generic.GenericContainerScreen;
import electrodynamics.common.inventory.container.ContainerCoalGenerator;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.TileCoalGenerator;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenCoalGenerator extends GenericContainerScreen<ContainerCoalGenerator> {
    public static final ResourceLocation SCREEN_BACKGROUND = new ResourceLocation(
	    References.ID + ":textures/gui/coalgenerator.png");

    public ScreenCoalGenerator(ContainerCoalGenerator container, PlayerInventory playerInventory,
	    ITextComponent title) {
	super(container, playerInventory, title);
    }

    @Override
    public ResourceLocation getScreenBackground() {
	return SCREEN_BACKGROUND;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
	super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
	TileCoalGenerator box = container.getHostFromIntArray();
	if (box != null) {
	    TransferPack output = TransferPack.ampsVoltage(
		    Constants.COALGENERATOR_MAX_OUTPUT.getAmps()
			    * Math.min((box.clientHeat - 27.0) / (3000.0 - 27.0), 1),
		    Constants.COALGENERATOR_MAX_OUTPUT.getVoltage());
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.coalgenerator.timeleft", box.clientBurnTime / 20 + "s"),
		    playerInventoryTitleX + 60f, playerInventoryTitleY - 53f, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.coalgenerator.current",
			    ElectricityChatFormatter.getDisplayShort(output.getAmps(), ElectricUnit.AMPERE)),
		    playerInventoryTitleX + 60f, playerInventoryTitleY - 40f, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.coalgenerator.output",
			    ElectricityChatFormatter.getDisplayShort(output.getWatts(), ElectricUnit.WATT)),
		    playerInventoryTitleX + 60f, playerInventoryTitleY - 27f, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.coalgenerator.voltage",
			    ElectricityChatFormatter.getDisplayShort(output.getVoltage(), ElectricUnit.VOLTAGE)),
		    playerInventoryTitleX + 60f, playerInventoryTitleY - 14f, 4210752);
	}
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
	super.drawGuiContainerBackgroundLayer(stack, partialTicks, mouseX, mouseY);
	TileCoalGenerator box = container.getHostFromIntArray();
	if (box != null && box.clientBurnTime > 0) {
	    int k = (int) (box.clientBurnTime * 13 / TileCoalGenerator.COAL_BURN_TIME);
	    blit(stack, guiLeft + 25, guiTop + 25 + 12 - k, 212, 12 - k, 14, k + 1);
	}
    }
}