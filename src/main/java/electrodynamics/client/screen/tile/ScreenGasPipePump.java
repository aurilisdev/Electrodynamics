package electrodynamics.client.screen.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.inventory.container.tile.ContainerGasPipePump;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.server.PacketSendUpdatePropertiesServer;
import electrodynamics.common.tile.network.gas.TileGasPipePump;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentTextInputBar;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenGasPipePump extends GenericScreen<ContainerGasPipePump> {

	private EditBox priority;

	private boolean needsUpdate = true;

	public ScreenGasPipePump(ContainerGasPipePump screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);

		components.add(new ScreenComponentTextInputBar(this, 94, 35, 59, 16));
	}

	@Override
	protected void containerTick() {
		super.containerTick();
		priority.tick();
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
		priority = new EditBox(font, i + 120, j + 40, 30, 13, Component.empty());

		priority.setTextColor(-1);
		priority.setTextColorUneditable(-1);
		priority.setBordered(false);
		priority.setMaxLength(1);
		priority.setResponder(this::setPriority);
		priority.setFilter(ScreenComponentTextInputBar.getValidator(ScreenComponentTextInputBar.POSITIVE_INTEGER));

		addWidget(priority);

	}

	private void setPriority(String prior) {

		TileGasPipePump pump = menu.getHostFromIntArray();

		if (pump == null) {
			return;
		}

		if (prior.isEmpty()) {
			return;
		}

		int priority = 0;

		try {
			priority = Integer.parseInt(prior);
		} catch (Exception e) {

		}

		if (priority > 9) {
			priority = 9;
			this.priority.setValue(priority + "");
		} else if (priority < 0) {
			priority = 0;
			this.priority.setValue(priority + "");
		}

		pump.priority.set(priority);

		NetworkHandler.CHANNEL.sendToServer(new PacketSendUpdatePropertiesServer(pump.priority.getPropertyManager().getProperties().indexOf(pump.priority), pump.priority, pump.getBlockPos()));

	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String temp = priority.getValue();
		init(minecraft, width, height);
		priority.setValue(temp);
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
			TileGasPipePump pump = menu.getHostFromIntArray();
			if (pump != null) {
				priority.setValue("" + pump.priority.get());
			}
		}
		priority.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	@Override
	protected void renderLabels(PoseStack stack, int x, int y) {
		super.renderLabels(stack, x, y);
		font.draw(stack, TextUtils.gui("prioritypump.priority"), 20, 39, 4210752);
	}

}
