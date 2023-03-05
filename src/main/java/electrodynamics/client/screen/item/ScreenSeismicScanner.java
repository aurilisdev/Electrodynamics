package electrodynamics.client.screen.item;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.inventory.container.item.ContainerSeismicScanner;
import electrodynamics.common.item.gear.tools.electric.ItemSeismicScanner;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.prefab.utilities.TextUtils;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class ScreenSeismicScanner extends GenericScreen<ContainerSeismicScanner> {

	public ScreenSeismicScanner(ContainerSeismicScanner screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
		components.add(new ScreenComponentElectricInfo(this::getElectricInformation, this, -AbstractScreenComponentInfo.SIZE + 1, 2));
	}

	@Override
	protected void renderLabels(PoseStack stack, int x, int y) {
		super.renderLabels(stack, x, y);

		font.draw(stack, TextUtils.gui("seismicscanner.material"), 15, 32, 4210752);
		font.draw(stack, TextUtils.gui("seismicscanner.dataheader"), 85, 25, 4210752);

		ItemStack ownerItem = menu.getOwnerItem();

		Location playerLoc = ownerItem.hasTag() ? Location.readFromNBT(ownerItem.getTag(), NBTUtils.LOCATION + ItemSeismicScanner.PLAY_LOC) : new Location(0, 0, 0);
		Location blockLoc = ownerItem.hasTag() ? Location.readFromNBT(ownerItem.getTag(), NBTUtils.LOCATION + ItemSeismicScanner.BLOCK_LOC) : new Location(0, 0, 0);

		if (blockLoc.equals(playerLoc)) {
			drawNotFound(stack);
		} else {
			font.draw(stack, TextUtils.gui("seismicscanner.xcoord", blockLoc.intX()), 95, 35, 4210752);
			font.draw(stack, TextUtils.gui("seismicscanner.ycoord", blockLoc.intY()), 95, 45, 4210752);
			font.draw(stack, TextUtils.gui("seismicscanner.zcoord", blockLoc.intZ()), 95, 55, 4210752);
		}

	}

	private List<? extends FormattedCharSequence> getElectricInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		ItemStack ownerItem = menu.getOwnerItem();
		if (ownerItem.getItem() instanceof ItemSeismicScanner scanner) {
			list.add(TextUtils.gui("machine.usage", Component.literal(ChatFormatter.getChatDisplayShort(ItemSeismicScanner.JOULES_PER_SCAN / 20.0, DisplayUnit.WATT)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(TextUtils.gui("machine.voltage", Component.literal(ChatFormatter.getChatDisplayShort(120, DisplayUnit.VOLTAGE)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(TextUtils.gui("machine.stored", Component.literal(ChatFormatter.getChatDisplayShort(scanner.getJoulesStored(ownerItem), DisplayUnit.JOULES) + " / " + ChatFormatter.getChatDisplayShort(ItemSeismicScanner.JOULES_PER_SCAN * 30, DisplayUnit.JOULES)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		}
		return list;
	}

	private void drawNotFound(PoseStack stack) {
		font.draw(stack, TextUtils.gui("seismicscanner.xcoordna"), 95, 35, 4210752);
		font.draw(stack, TextUtils.gui("seismicscanner.ycoordna"), 95, 45, 4210752);
		font.draw(stack, TextUtils.gui("seismicscanner.zcoordna"), 95, 55, 4210752);
	}

}
