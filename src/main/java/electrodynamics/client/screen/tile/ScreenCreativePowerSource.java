package electrodynamics.client.screen.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.inventory.container.tile.ContainerCreativePowerSource;
import electrodynamics.common.tile.generators.TileCreativePowerSource;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.editbox.ScreenComponentEditBox;
import electrodynamics.prefab.screen.component.types.ScreenComponentSimpleLabel;
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
		addComponent(new ScreenComponentSimpleLabel(40, 31, 10, 4210752, TextUtils.gui("creativepowersource.voltage")));
		addComponent(new ScreenComponentSimpleLabel(40, 49, 10, 4210752, TextUtils.gui("creativepowersource.power")));
		addComponent(new ScreenComponentSimpleLabel(131, 31, 10, 4210752, TextUtils.gui("creativepowersource.voltunit")));
		addComponent(new ScreenComponentSimpleLabel(131, 49, 10, 4210752, TextUtils.gui("creativepowersource.powerunit")));
	}

	private void setVoltage(String val) {
		voltage.setFocus(true);
		power.setFocus(false);
		handleVoltage(val);
	}

	private void handleVoltage(String val) {
		if (val.isEmpty()) {
			return;
		}

		Integer voltage = 0;

		try {
			voltage = Integer.parseInt(val);
		} catch (Exception e) {

		}

		TileCreativePowerSource tile = menu.getHostFromIntArray();

		if (tile == null) {
			return;
		}

		tile.voltage.set(voltage);

		tile.voltage.updateServer();
	}

	private void setPower(String val) {
		voltage.setFocus(false);
		power.setFocus(true);
		handlePower(val);
	}

	private void handlePower(String val) {

		if (val.isEmpty()) {
			return;
		}

		Integer power = 0;

		try {
			power = Integer.parseInt(val);
		} catch (Exception e) {

		}

		TileCreativePowerSource tile = menu.getHostFromIntArray();

		if (tile == null) {
			return;
		}
		
		tile.power.set(power);
		
		tile.power.updateServer();

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

}
