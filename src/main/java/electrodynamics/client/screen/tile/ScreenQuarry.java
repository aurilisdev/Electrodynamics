package electrodynamics.client.screen.tile;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.inventory.container.tile.ContainerQuarry;
import electrodynamics.common.tile.TileQuarry;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentInfo;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;

public class ScreenQuarry extends GenericScreen<ContainerQuarry> {

	public ScreenQuarry(ContainerQuarry container, Inventory inv, Component titleIn) {
		super(container, inv, titleIn);
		imageHeight += 58;
		inventoryLabelY += 58;
		components.add(new ScreenComponentElectricInfo(this::getElectricInformation, this, -ScreenComponentInfo.SIZE + 1, 2));
	}

	private List<? extends FormattedCharSequence> getElectricInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		TileQuarry quarry = menu.getHostFromIntArray();
		if (quarry != null) {
			ComponentElectrodynamic electro = quarry.getComponent(ComponentType.Electrodynamic);
			list.add(TextUtils.gui("machine.usage", Component.literal(ChatFormatter.getChatDisplayShort(quarry.clientPowerUsage * 20, DisplayUnit.WATT)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(TextUtils.gui("machine.voltage", Component.literal(ChatFormatter.getChatDisplayShort(electro.getVoltage(), DisplayUnit.VOLTAGE)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		}
		return list;
	}

	@Override
	protected void renderLabels(PoseStack stack, int x, int y) {
		super.renderLabels(stack, x, y);
		TileQuarry quarry = menu.getHostFromIntArray();
		if (quarry != null) {
			if (quarry.clientItemVoid) {
				font.draw(stack, TextUtils.gui("quarry.voiditems"), 85, 14, 4210752);
			} else {
				font.draw(stack, TextUtils.gui("quarry.needvoidcard"), 85, 14, 4210752);
			}
			font.draw(stack, TextUtils.gui("quarry.status"), 5, 22, 4210752);
			if (quarry.hasClientCorners()) {
				font.draw(stack, TextUtils.gui("quarry.ringfound"), 10, 32, 4210752);
			} else {
				font.draw(stack, TextUtils.gui("quarry.noring"), 10, 32, 4210752);
			}
			if (quarry.clientMiningPos == null && quarry.hasClientCorners() && !quarry.clientFinished) {
				font.draw(stack, TextUtils.gui("quarry.setup"), 10, 42, 4210752);
			} else if (quarry.clientMiningPos != null && !quarry.clientFinished) {
				font.draw(stack, TextUtils.gui("quarry.mining"), 10, 42, 4210752);
			} else if (quarry.clientFinished) {
				font.draw(stack, TextUtils.gui("quarry.finished"), 10, 42, 4210752);
			} else {
				font.draw(stack, TextUtils.gui("quarry.error"), 10, 42, 4210752);
			}
			if (quarry.clientHead) {
				font.draw(stack, TextUtils.gui("quarry.hashead"), 10, 52, 4210752);
			} else {
				font.draw(stack, TextUtils.gui("quarry.nohead"), 10, 52, 4210752);
			}
			if (quarry.clientIsPowered) {
				font.draw(stack, TextUtils.gui("quarry.running"), 10, 62, 4210752);
			} else {
				font.draw(stack, TextUtils.gui("quarry.needspower"), 10, 62, 4210752);
			}

			font.draw(stack, TextUtils.gui("quarry.stats"), 5, 82, 4210752);
			font.draw(stack, TextUtils.gui("quarry.fortune", quarry.clientFortuneLevel), 10, 92, 4210752);
			font.draw(stack, TextUtils.gui("quarry.silktouch", quarry.clientSilkTouchLevel), 10, 102, 4210752);
			font.draw(stack, TextUtils.gui("quarry.unbreaking", quarry.clientUnbreakingLevel), 10, 112, 4210752);
		}
	}

}
