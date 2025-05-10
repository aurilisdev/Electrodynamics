package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerWindmill;
import electrodynamics.common.tile.electricitygrid.generators.TileWindmill;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.ScreenComponentMultiLabel;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.utilities.math.Color;
import voltaic.prefab.utilities.object.TransferPack;

@OnlyIn(Dist.CLIENT)
public class ScreenWindmill extends GenericScreen<ContainerWindmill> {

    public ScreenWindmill(ContainerWindmill container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
        addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));
        addComponent(new ScreenComponentMultiLabel(0, 0, poseStack -> {
            TileWindmill windmill = menu.getSafeHost();
            if (windmill == null) {
                return;
            }
            TransferPack transfer = windmill.getProduced();
            font.draw(poseStack, ElectroTextUtils.gui("machine.current", ChatFormatter.getChatDisplayShort(transfer.getAmps(), DisplayUnits.AMPERE)), inventoryLabelX + 60, inventoryLabelY - 48, Color.TEXT_GRAY.color());
            font.draw(poseStack, ElectroTextUtils.gui("machine.output", ChatFormatter.getChatDisplayShort(transfer.getWatts(), DisplayUnits.WATT)), inventoryLabelX + 60, inventoryLabelY - 35, Color.TEXT_GRAY.color());
            font.draw(poseStack, ElectroTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(transfer.getVoltage(), DisplayUnits.VOLTAGE)), inventoryLabelX + 60, inventoryLabelY - 22, Color.TEXT_GRAY.color());
        }));
    }

}