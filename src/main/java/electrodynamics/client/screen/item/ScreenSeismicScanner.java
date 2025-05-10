package electrodynamics.client.screen.item;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.common.inventory.container.item.ContainerSeismicScanner;
import electrodynamics.common.item.gear.tools.electric.ItemSeismicScanner;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.ScreenComponentMultiLabel;
import voltaic.prefab.screen.component.types.ScreenComponentSimpleLabel;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.utilities.NBTUtils;
import voltaic.prefab.utilities.math.Color;
import voltaic.prefab.utilities.object.Location;

public class ScreenSeismicScanner extends GenericScreen<ContainerSeismicScanner> {

	public ScreenSeismicScanner(ContainerSeismicScanner screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		addComponent(new ScreenComponentElectricInfo(this::getElectricInformation, -AbstractScreenComponentInfo.SIZE + 1, 2));
		addComponent(new ScreenComponentSimpleLabel(15, 32, 10, Color.TEXT_GRAY, ElectroTextUtils.gui("seismicscanner.material")));
		addComponent(new ScreenComponentSimpleLabel(85, 25, 10, Color.TEXT_GRAY, ElectroTextUtils.gui("seismicscanner.dataheader")));
		addComponent(new ScreenComponentMultiLabel(0, 0, poseStack -> {
			ItemStack ownerItem = menu.getOwnerItem();

			Location playerLoc = ownerItem.hasTag() ? Location.readFromNBT(ownerItem.getTag(), NBTUtils.LOCATION + ItemSeismicScanner.PLAY_LOC) : new Location(0, 0, 0);
			Location blockLoc = ownerItem.hasTag() ? Location.readFromNBT(ownerItem.getTag(), NBTUtils.LOCATION + ItemSeismicScanner.BLOCK_LOC) : new Location(0, 0, 0);

			if (blockLoc.equals(playerLoc)) {
				font.draw(poseStack, ElectroTextUtils.gui("seismicscanner.xcoordna"), 95, 35, 4210752);
				font.draw(poseStack, ElectroTextUtils.gui("seismicscanner.ycoordna"), 95, 45, 4210752);
				font.draw(poseStack, ElectroTextUtils.gui("seismicscanner.zcoordna"), 95, 55, 4210752);
			} else {
				font.draw(poseStack, ElectroTextUtils.gui("seismicscanner.xcoord", blockLoc.intX()), 95, 35, 4210752);
				font.draw(poseStack, ElectroTextUtils.gui("seismicscanner.ycoord", blockLoc.intY()), 95, 45, 4210752);
				font.draw(poseStack, ElectroTextUtils.gui("seismicscanner.zcoord", blockLoc.intZ()), 95, 55, 4210752);
			}
		}));

	}

	private List<? extends IReorderingProcessor> getElectricInformation() {
        ArrayList<IReorderingProcessor> list = new ArrayList<>();
        ItemStack ownerItem = menu.getOwnerItem();
        if (ownerItem.getItem() instanceof ItemSeismicScanner) {
        	ItemSeismicScanner scanner = (ItemSeismicScanner) ownerItem.getItem();
            list.add(ElectroTextUtils.gui("machine.usage", ChatFormatter.getChatDisplayShort(ItemSeismicScanner.JOULES_PER_SCAN / 20.0, DisplayUnits.WATT).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
            list.add(ElectroTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(120, DisplayUnits.VOLTAGE).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
            list.add(ElectroTextUtils.gui("machine.stored", ChatFormatter.getChatDisplayShort(scanner.getJoulesStored(ownerItem), DisplayUnits.JOULES).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
        }
        return list;
    }

}
