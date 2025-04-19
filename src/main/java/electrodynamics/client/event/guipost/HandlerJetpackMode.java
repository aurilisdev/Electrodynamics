package electrodynamics.client.event.guipost;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.item.gear.armor.types.ItemJetpack;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.gas.GasStack;
import voltaic.api.gas.IGasHandlerItem;
import voltaic.client.event.AbstractPostGuiOverlayHandler;
import voltaic.prefab.utilities.ItemUtils;
import voltaic.prefab.utilities.VoltaicTextUtils;
import voltaic.registers.VoltaicCapabilities;
import voltaic.registers.VoltaicDataComponentTypes;

public class HandlerJetpackMode extends AbstractPostGuiOverlayHandler {

    @Override
    public void renderToScreen(GuiGraphics graphics, DeltaTracker tracker, Minecraft minecraft) {
        List<ItemStack> armor = new ArrayList<>();
        minecraft.player.getArmorSlots().forEach(armor::add);
        ItemStack chestSlot = armor.get(2);

        if (!ItemUtils.testItems(chestSlot.getItem(), ElectrodynamicsItems.ITEM_JETPACK.get(), ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get())) {
            return;
        }

        PoseStack stack = graphics.pose();

        stack.pushPose();

        Component mode = ItemJetpack.getModeText(chestSlot.getOrDefault(VoltaicDataComponentTypes.MODE, -1));

        int height = graphics.guiHeight();

        IGasHandlerItem handler = chestSlot.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM);

        GasStack gas = handler.getGasInTank(0);
        if (gas.isEmpty()) {
            graphics.drawString(minecraft.font, mode, 10, height - 30, 0);
            graphics.drawString(minecraft.font, VoltaicTextUtils.ratio(Component.literal("0"), ChatFormatter.formatFluidMilibuckets(ItemJetpack.MAX_CAPACITY)), 10, height - 20, -1);
        } else {
            graphics.drawString(minecraft.font, mode, 10, height - 50, 0);
            graphics.drawString(minecraft.font, VoltaicTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(gas.getAmount()), ChatFormatter.formatFluidMilibuckets(ItemJetpack.MAX_CAPACITY)), 10, height - 40, -1);
            graphics.drawString(minecraft.font, ChatFormatter.getChatDisplayShort(gas.getTemperature(), DisplayUnits.TEMPERATURE_KELVIN), 10, height - 30, -1);
            graphics.drawString(minecraft.font, ChatFormatter.getChatDisplayShort(gas.getPressure(), DisplayUnits.PRESSURE_ATM), 10, height - 20, -1);
        }

        stack.popPose();

    }

}
