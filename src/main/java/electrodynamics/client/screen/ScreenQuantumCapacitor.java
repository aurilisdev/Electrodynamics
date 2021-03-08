package electrodynamics.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.References;
import electrodynamics.api.formatting.ElectricUnit;
import electrodynamics.api.utilities.ElectricityChatFormatter;
import electrodynamics.client.screen.generic.GenericContainerScreenUpgradeable;
import electrodynamics.common.inventory.container.ContainerQuantumCapacitor;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.PacketSetQuantumCapacitorData;
import electrodynamics.common.tile.quantumcapacitor.TileQuantumCapacitor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenQuantumCapacitor extends GenericContainerScreenUpgradeable<ContainerQuantumCapacitor> {
    public static final ResourceLocation SCREEN_BACKGROUND = new ResourceLocation(
	    References.ID + ":textures/gui/quantumcapacitor.png");

    public ScreenQuantumCapacitor(ContainerQuantumCapacitor container, PlayerInventory playerInventory,
	    ITextComponent title) {
	super(container, playerInventory, title);
	xSize = 176;
    }

    @Override
    public ResourceLocation getScreenBackground() {
	return SCREEN_BACKGROUND;
    }

    private TextFieldWidget outputField;
    private TextFieldWidget frequencyField;

    @Override
    public void tick() {
	super.tick();
	outputField.tick();
	frequencyField.tick();
    }

    @Override
    protected void init() {
	super.init();
	initFields();
    }

    protected void initFields() {
	minecraft.keyboardListener.enableRepeatEvents(true);
	int i = (width - xSize) / 2;
	int j = (height - ySize) / 2;
	outputField = new TextFieldWidget(font, i + 120, j + 18, 46, 13,
		new TranslationTextComponent("container.quantumcapacitor.joulesoutput"));
	outputField.setTextColor(-1);
	outputField.setDisabledTextColour(-1);
	outputField.setEnableBackgroundDrawing(false);
	outputField.setMaxStringLength(6);
	outputField.setResponder(this::updateValues);

	frequencyField = new TextFieldWidget(font, i + 120, j + 18 + 20, 46, 13,
		new TranslationTextComponent("container.quantumcapacitor.frequency"));
	frequencyField.setTextColor(-1);
	frequencyField.setDisabledTextColour(-1);
	frequencyField.setEnableBackgroundDrawing(false);
	frequencyField.setMaxStringLength(6);
	frequencyField.setResponder(this::updateValues);

	children.add(outputField);
	children.add(frequencyField);
    }

    private boolean needsUpdate = true;

    private void updateValues(String coord) {
	if (!coord.isEmpty()) {
	    Double triedOutput = 0.0;
	    try {
		triedOutput = Double.parseDouble(outputField.getText());
	    } catch (Exception e) {
	    }
	    Integer frequency = 0;
	    try {
		frequency = Integer.parseInt(frequencyField.getText());
	    } catch (Exception e) {
	    }
	    if (container.getHostFromIntArray() != null) {
		NetworkHandler.CHANNEL.sendToServer(new PacketSetQuantumCapacitorData(
			container.getHostFromIntArray().getPos(), triedOutput, frequency));
	    }
	}
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
	String s = outputField.getText();
	String s1 = frequencyField.getText();
	this.init(minecraft, width, height);
	outputField.setText(s);
	frequencyField.setText(s1);
    }

    @Override
    public void onClose() {
	super.onClose();
	minecraft.keyboardListener.enableRepeatEvents(false);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
	if (keyCode == 256) {
	    minecraft.player.closeScreen();
	}
	boolean x = !outputField.keyPressed(keyCode, scanCode, modifiers) && !outputField.canWrite();
	boolean y = !frequencyField.keyPressed(keyCode, scanCode, modifiers) && !frequencyField.canWrite();
	if (x || y) {
	    return super.keyPressed(keyCode, scanCode, modifiers);
	}
	return true;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
	super.render(matrixStack, mouseX, mouseY, partialTicks);
	if (needsUpdate && container.getHostFromIntArray() != null) {
	    needsUpdate = false;
	    outputField.setText("" + container.getHostFromIntArray().outputJoules);
	    frequencyField.setText("" + container.getHostFromIntArray().frequency);
	}
	outputField.render(matrixStack, mouseX, mouseY, partialTicks);
	frequencyField.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
	super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
	TileQuantumCapacitor box = container.getHostFromIntArray();
	if (box != null) {
	    font.func_243248_b(matrixStack, new TranslationTextComponent("gui.quantumcapacitor.current",
		    ElectricityChatFormatter.getDisplayShort(
			    box.getOutputJoules() * 20.0 / TileQuantumCapacitor.DEFAULT_VOLTAGE, ElectricUnit.AMPERE)),
		    playerInventoryTitleX, (float) playerInventoryTitleY - 55, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.quantumcapacitor.transfer",
			    ElectricityChatFormatter.getDisplayShort(box.getOutputJoules() * 20.0, ElectricUnit.WATT)),
		    playerInventoryTitleX, (float) playerInventoryTitleY - 42, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.quantumcapacitor.voltage",
			    ElectricityChatFormatter.getDisplayShort(TileQuantumCapacitor.DEFAULT_VOLTAGE,
				    ElectricUnit.VOLTAGE)),
		    playerInventoryTitleX, (float) playerInventoryTitleY - 29, 4210752);
	    font.func_243248_b(matrixStack,
		    new TranslationTextComponent("gui.quantumcapacitor.stored",
			    ElectricityChatFormatter.getDisplayShort(box.joulesClient, ElectricUnit.JOULES) + " / "
				    + ElectricityChatFormatter.getDisplayShort(TileQuantumCapacitor.DEFAULT_MAX_JOULES,
					    ElectricUnit.JOULES)),
		    playerInventoryTitleX, (float) playerInventoryTitleY - 16, 4210752);
	}
    }
}