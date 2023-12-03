package electrodynamics.client.render.event.guipost;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.common.item.gear.armor.types.ItemJetpack;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.ItemUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class HandlerJetpackMode extends AbstractPostGuiOverlayHandler {

	@Override
	public void renderToScreen(ElementType type, PoseStack stack, Window window, Minecraft minecraft, float partialTicks) {
		
		if(type != ElementType.ALL) {
			return;
		}
		
		List<ItemStack> armor = new ArrayList<>();
		minecraft.player.getArmorSlots().forEach(armor::add);
		ItemStack chestSlot = armor.get(2);

		if (!ItemUtils.testItems(chestSlot.getItem(), ElectrodynamicsItems.ITEM_JETPACK.get(), ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get())) {
			return;
		}

		stack.pushPose();

		Component mode = ItemJetpack.getModeText(chestSlot.hasTag() ? chestSlot.getTag().getInt(NBTUtils.MODE) : -1);

		int height = window.getGuiScaledHeight();

		chestSlot.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(cap -> {
			FluidStack fluid = cap.getFluidInTank(0);
			minecraft.font.draw(stack, mode, 10, height - 30, 0);

			if (fluid.isEmpty()) {
				minecraft.font.draw(stack, ElectroTextUtils.ratio(new TextComponent("0"), ChatFormatter.formatFluidMilibuckets(ItemJetpack.MAX_CAPACITY)), 10, height - 20, -1);
			} else {
				minecraft.font.draw(stack, ElectroTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(fluid.getAmount()), ChatFormatter.formatFluidMilibuckets(ItemJetpack.MAX_CAPACITY)), 10, height - 20, -1);
			}

		});

		minecraft.getTextureManager().bindForSetup(GuiComponent.GUI_ICONS_LOCATION);

		stack.popPose();

	}

}