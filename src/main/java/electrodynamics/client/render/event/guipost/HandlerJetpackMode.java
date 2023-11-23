package electrodynamics.client.render.event.guipost;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.common.item.gear.armor.types.ItemJetpack;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.ItemUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;

public class HandlerJetpackMode extends AbstractPostGuiOverlayHandler {

	@Override
	public void renderToScreen(NamedGuiOverlay overlay, PoseStack stack, Window window, Minecraft minecraft, float partialTicks) {
		List<ItemStack> armor = new ArrayList<>();
		minecraft.player.getArmorSlots().forEach(armor::add);
		ItemStack chestSlot = armor.get(2);

		if (!ItemUtils.testItems(chestSlot.getItem(), ElectrodynamicsItems.ITEM_JETPACK.get(), ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get())) {
			return;
		}

		stack.pushPose();

		Component mode = ItemJetpack.getModeText(chestSlot.hasTag() ? chestSlot.getTag().getInt(NBTUtils.MODE) : -1);

		int height = window.getGuiScaledHeight();

		chestSlot.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(cap -> {
			FluidStack fluid = cap.getFluidInTank(0);
			minecraft.font.draw(stack, mode, 10, height - 30, 0);
			
			if (fluid.isEmpty()) {
				minecraft.font.draw(stack, ElectroTextUtils.ratio(Component.literal("0"), ChatFormatter.formatFluidMilibuckets(ItemJetpack.MAX_CAPACITY)), 10, height - 20, -1);
			} else {
				minecraft.font.draw(stack, ElectroTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(fluid.getAmount()), ChatFormatter.formatFluidMilibuckets(ItemJetpack.MAX_CAPACITY)), 10, height - 20, -1);
			}

		});

		stack.popPose();

	}

}
