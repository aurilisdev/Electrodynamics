package electrodynamics.client.screen;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.api.electricity.formatting.ElectricityChatFormatter;
import electrodynamics.api.gui.GenericScreen;
import electrodynamics.api.gui.component.GuiComponentElectricInfo;
import electrodynamics.api.gui.component.GuiComponentInfo;
import electrodynamics.api.gui.component.GuiComponentProgress;
import electrodynamics.api.gui.component.GuiTemperatureInfo;
import electrodynamics.api.utilities.object.TransferPack;
import electrodynamics.common.inventory.container.ContainerCoalGenerator;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.TileCoalGenerator;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenCoalGenerator extends GenericScreen<ContainerCoalGenerator> {
    public ScreenCoalGenerator(ContainerCoalGenerator container, PlayerInventory playerInventory, ITextComponent title) {
	super(container, playerInventory, title);
	components.add(new GuiComponentProgress(() -> {
	    TileCoalGenerator box = container.getHostFromIntArray();
	    if (box != null) {
		return box.clientBurnTime / (TileCoalGenerator.COAL_BURN_TIME * 1.0);
	    }
	    return 0;
	}, this, 25, 25).flame());
	components.add(new GuiTemperatureInfo(this::getTemperatureInformation, this, -GuiComponentInfo.SIZE + 1, 2 + GuiComponentInfo.SIZE));
	components.add(new GuiComponentElectricInfo(this::getEnergyInformation, this, -GuiComponentInfo.SIZE + 1, 2));
    }

    private List<? extends ITextProperties> getTemperatureInformation() {
	ArrayList<ITextProperties> list = new ArrayList<>();
	TileCoalGenerator box = container.getHostFromIntArray();
	if (box != null) {
	    list.add(new TranslationTextComponent("gui.coalgenerator.timeleft",
		    new StringTextComponent(box.clientBurnTime / 20 + "s").mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	    list.add(new TranslationTextComponent("gui.coalgenerator.temperature",
		    new StringTextComponent(ElectricityChatFormatter.roundDecimals(box.clientHeat * (2500.0 / 3000.0)) + " C")
			    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	    list.add(new TranslationTextComponent("gui.coalgenerator.heat",
		    new StringTextComponent(ElectricityChatFormatter.roundDecimals((box.clientHeat - 27.0) / (3000.0 - 27.0) * 100) + "%")
			    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	}
	return list;
    }

    private List<? extends ITextProperties> getEnergyInformation() {
	ArrayList<ITextProperties> list = new ArrayList<>();
	TileCoalGenerator box = container.getHostFromIntArray();
	if (box != null) {
	    TransferPack output = TransferPack.ampsVoltage(
		    Constants.COALGENERATOR_MAX_OUTPUT.getAmps() * Math.min((box.clientHeat - 27.0) / (3000.0 - 27.0), 1),
		    Constants.COALGENERATOR_MAX_OUTPUT.getVoltage());
	    list.add(new TranslationTextComponent("gui.coalgenerator.current",
		    new StringTextComponent(ElectricityChatFormatter.getDisplayShort(output.getAmps(), ElectricUnit.AMPERE))
			    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	    list.add(new TranslationTextComponent("gui.coalgenerator.output",
		    new StringTextComponent(ElectricityChatFormatter.getDisplayShort(output.getWatts(), ElectricUnit.WATT))
			    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	    list.add(new TranslationTextComponent("gui.coalgenerator.voltage",
		    new StringTextComponent(ElectricityChatFormatter.getDisplayShort(output.getVoltage(), ElectricUnit.VOLTAGE))
			    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	}
	return list;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
	super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
	TileCoalGenerator box = container.getHostFromIntArray();
	if (box != null) {
	    TransferPack output = TransferPack.ampsVoltage(
		    Constants.COALGENERATOR_MAX_OUTPUT.getAmps() * Math.min((box.clientHeat - 27.0) / (3000.0 - 27.0), 1),
		    Constants.COALGENERATOR_MAX_OUTPUT.getVoltage());
	    font.func_243248_b(matrixStack, new TranslationTextComponent("gui.coalgenerator.timeleft", box.clientBurnTime / 20 + "s"),
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
}