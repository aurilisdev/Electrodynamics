package electrodynamics.client.screen.item;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.common.inventory.container.item.ContainerSeismicScanner;
import electrodynamics.common.item.gear.tools.electric.ItemSeismicScanner;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentInfo;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class ScreenSeismicScanner extends GenericScreen<ContainerSeismicScanner> {

	public ScreenSeismicScanner(ContainerSeismicScanner screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
		components.add(new ScreenComponentElectricInfo(this::getElectricInformation, this, -ScreenComponentInfo.SIZE + 1, 2));
	}

	@Override
	protected void renderLabels(PoseStack stack, int x, int y) {
		super.renderLabels(stack, x, y);
		ItemStack ownerItem = menu.getOwnerItem();
		Location blockLoc = ownerItem.getCapability(ElectrodynamicsCapabilities.LOCATION_STORAGE_CAPABILITY).map(m -> m.getLocation(0))
				.orElse(new Location(0, 0, 0));
		Location playerLoc = ownerItem.getCapability(ElectrodynamicsCapabilities.LOCATION_STORAGE_CAPABILITY).map(m -> m.getLocation(1))
				.orElse(new Location(0, 0, 0));
		font.draw(stack, new TranslatableComponent("gui.seismicscanner.material"), 15, 32, 4210752);
		font.draw(stack, new TranslatableComponent("gui.seismicscanner.dataheader"), 85, 25, 4210752);
		if (blockLoc.equals(playerLoc)) {
			font.draw(stack, new TranslatableComponent("gui.seismicscanner.xcoordna", blockLoc.intX()), 95, 35, 4210752);
			font.draw(stack, new TranslatableComponent("gui.seismicscanner.ycoordna", blockLoc.intY()), 95, 45, 4210752);
			font.draw(stack, new TranslatableComponent("gui.seismicscanner.zcoordna", blockLoc.intZ()), 95, 55, 4210752);
		} else {
			font.draw(stack, new TranslatableComponent("gui.seismicscanner.xcoord", blockLoc.intX()), 95, 35, 4210752);
			font.draw(stack, new TranslatableComponent("gui.seismicscanner.ycoord", blockLoc.intY()), 95, 45, 4210752);
			font.draw(stack, new TranslatableComponent("gui.seismicscanner.zcoord", blockLoc.intZ()), 95, 55, 4210752);
		}
	}

	private List<? extends FormattedCharSequence> getElectricInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		ItemStack ownerItem = menu.getOwnerItem();
		if (ownerItem.getItem() instanceof ItemSeismicScanner scanner) {
			list.add(new TranslatableComponent("gui.machine.usage",
					new TextComponent(ChatFormatter.getElectricDisplayShort(ItemSeismicScanner.JOULES_PER_SCAN / 20, ElectricUnit.WATT))
							.withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(new TranslatableComponent("gui.machine.voltage",
					new TextComponent(ChatFormatter.getElectricDisplayShort(120, ElectricUnit.VOLTAGE)).withStyle(ChatFormatting.GRAY))
							.withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(new TranslatableComponent("gui.machine.stored",
					new TextComponent(ChatFormatter.getElectricDisplayShort(scanner.getJoulesStored(ownerItem), ElectricUnit.JOULES) + " / "
							+ ChatFormatter.getElectricDisplayShort(ItemSeismicScanner.JOULES_PER_SCAN * 30, ElectricUnit.JOULES))
									.withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		}
		return list;
	}

}
