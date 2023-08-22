package electrodynamics.client.render.event.guipost;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.gas.GasStack;
import electrodynamics.common.item.gear.armor.types.ItemJetpack;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.ItemUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;

public class HandlerJetpackMode extends AbstractPostGuiOverlayHandler {

	@Override
	public void renderToScreen(NamedGuiOverlay overlay, GuiGraphics graphics, Window window, Minecraft minecraft, float partialTicks) {
		List<ItemStack> armor = new ArrayList<>();
		minecraft.player.getArmorSlots().forEach(armor::add);
		ItemStack chestSlot = armor.get(2);

		if (!ItemUtils.testItems(chestSlot.getItem(), ElectrodynamicsItems.ITEM_JETPACK.get(), ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get())) {
			return;
		}
		
		PoseStack stack = graphics.pose();
			
		stack.pushPose();

		Component mode = ItemJetpack.getModeText(chestSlot.hasTag() ? chestSlot.getTag().getInt(NBTUtils.MODE) : -1);

		int height = window.getGuiScaledHeight();

		chestSlot.getCapability(ElectrodynamicsCapabilities.GAS_HANDLER_ITEM).ifPresent(cap -> {
			GasStack gas = cap.getGasInTank(0);
			if (gas.isEmpty()) {
				graphics.drawString(minecraft.font, mode, 10, height - 30, 0);
				graphics.drawString(minecraft.font, ElectroTextUtils.ratio(Component.literal("0"), ChatFormatter.formatFluidMilibuckets(ItemJetpack.MAX_CAPACITY)), 10, height - 20, -1);
			} else {
				graphics.drawString(minecraft.font, mode, 10, height - 50, 0);
				graphics.drawString(minecraft.font, ElectroTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(gas.getAmount()), ChatFormatter.formatFluidMilibuckets(ItemJetpack.MAX_CAPACITY)), 10, height - 40, -1);
				graphics.drawString(minecraft.font, ChatFormatter.getChatDisplayShort(gas.getTemperature(), DisplayUnit.TEMPERATURE_KELVIN), 10, height - 30, -1);
				graphics.drawString(minecraft.font, ChatFormatter.getChatDisplayShort(gas.getPressure(), DisplayUnit.PRESSURE_ATM), 10, height - 20, -1);
			}

		});

		stack.popPose();

	}

}
