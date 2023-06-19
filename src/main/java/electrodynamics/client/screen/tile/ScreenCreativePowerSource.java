package electrodynamics.client.screen.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.inventory.container.tile.ContainerCreativePowerSource;
import electrodynamics.common.tile.generators.TileCreativePowerSource;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.editbox.ScreenComponentEditBox;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenCreativePowerSource extends GenericScreen<ContainerCreativePowerSource> {

	private ScreenComponentEditBox voltage;
	private ScreenComponentEditBox power;

	private boolean needsUpdate = true;

	public ScreenCreativePowerSource(ContainerCreativePowerSource container, Inventory inv, Component titleIn) {
		super(container, inv, titleIn);
		addComponent(voltage = new ScreenComponentEditBox(80, 27, 49, 16, getFontRenderer()).setTextColor(-1).setTextColorUneditable(-1).setMaxLength(6).setFilter(ScreenComponentEditBox.POSITIVE_INTEGER).setResponder(this::setVoltage));
		addComponent(power = new ScreenComponentEditBox(80, 45, 49, 16, getFontRenderer()).setTextColor(-1).setTextColorUneditable(-1).setFilter(ScreenComponentEditBox.POSITIVE_INTEGER).setResponder(this::setPower));
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
