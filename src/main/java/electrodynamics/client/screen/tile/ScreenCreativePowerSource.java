package electrodynamics.client.screen.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.inventory.container.tile.ContainerCreativePowerSource;
import electrodynamics.common.tile.generators.TileCreativePowerSource;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentTextInputBar;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class ScreenCreativePowerSource extends GenericScreen<ContainerCreativePowerSource> {

	private EditBox voltage;
	private EditBox power;

	private boolean needsUpdate = true;

	public ScreenCreativePowerSource(ContainerCreativePowerSource container, Inventory inv, Component titleIn) {
		super(container, inv, titleIn);
		components.add(new ScreenComponentTextInputBar(this, 80, 27, 49, 16));
		components.add(new ScreenComponentTextInputBar(this, 80, 45, 49, 16));
	}

	@Override
	protected void containerTick() {
		super.containerTick();
		voltage.tick();
		power.tick();
	}

	@Override
	protected void init() {
		super.init();
		initFields();
	}

	private void initFields() {
		minecraft.keyboardHandler.setSendRepeatsToGui(true);
		int i = (width - imageWidth) / 2;
		int j = (height - imageHeight) / 2;
		voltage = new EditBox(font, i + 85, j + 31, 46, 13, TextUtils.gui("creativepowersource.voltage"));
		voltage.setTextColor(-1);
		voltage.setTextColorUneditable(-1);
		voltage.setBordered(false);
		voltage.setMaxLength(6);
		voltage.setResponder(this::setVoltage);

		power = new EditBox(font, i + 85, j + 31 + 18, 46, 13, TextUtils.gui("creativepowersource.power"));
		power.setTextColor(-1);
		power.setTextColorUneditable(-1);
		power.setBordered(false);
		power.setMaxLength(6);
		power.setResponder(this::setPower);

		addWidget(voltage);
		addWidget(power);
		setInitialFocus(voltage);
	}

	private void setVals(String vals) {
		if (!vals.isEmpty()) {
			menu.setValues(voltage.getValue(), power.getValue());
		}
	}

	private void setVoltage(String val) {
		voltage.setFocus(true);
		power.setFocus(false);
		setVals(val);
	}

	private void setPower(String val) {
		voltage.setFocus(false);
		power.setFocus(true);
		setVals(val);
	}

	@Override
	public void resize(@NotNull Minecraft minecraft, int width, int height) {
		String volt = voltage.getValue();
		String pow = power.getValue();
		init(minecraft, width, height);
		voltage.setValue(volt);
		power.setValue(pow);
	}

	@Override
	public void removed() {
		super.removed();
		minecraft.keyboardHandler.setSendRepeatsToGui(false);
	}

	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		if (needsUpdate) {
			needsUpdate = false;
			TileCreativePowerSource source = menu.getHostFromIntArray();
			if (source != null) {
				voltage.setValue("" + source.voltage.get());
				power.setValue("" + source.power.get());
			}
		}
		voltage.render(matrixStack, mouseX, mouseY, partialTicks);
		power.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	@Override
	protected void renderLabels(PoseStack stack, int x, int y) {
		super.renderLabels(stack, x, y);
		font.draw(stack, TextUtils.gui("creativepowersource.voltage"), 40, 31, 4210752);
		font.draw(stack, TextUtils.gui("creativepowersource.power"), 40, 49, 4210752);
		font.draw(stack, TextUtils.gui("creativepowersource.voltunit"), 131, 31, 4210752);
		font.draw(stack, TextUtils.gui("creativepowersource.powerunit"), 131, 49, 4210752);
	}

}
