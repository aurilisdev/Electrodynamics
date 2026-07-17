package electrodynamics.client.event.guipost;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.item.gear.armor.types.ItemJetpack;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.client.event.AbstractPostGuiOverlayHandler;
import voltaic.prefab.screen.component.CachedComponent;
import voltaic.prefab.utilities.ItemUtils;
import voltaic.prefab.utilities.NBTUtils;
import voltaic.prefab.utilities.VoltaicTextUtils;

public class HandlerJetpackMode extends AbstractPostGuiOverlayHandler {

    private static final int X = 10;
    private static final int MODE_Y_OFFSET = 30;
    private static final int FLUID_Y_OFFSET = 20;

    private static final CachedComponent<Integer> MODE_TEXT = new CachedComponent<>(ItemJetpack::getModeText);

    private static final CachedComponent<Integer> FLUID_RATIO_TEXT = new CachedComponent<>(
	    amount -> VoltaicTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(amount),
		    ChatFormatter.formatFluidMilibuckets(ItemJetpack.MAX_CAPACITY)));

    @Override
    public void renderToScreen(NamedGuiOverlay overlay, PoseStack stack, Window window, Minecraft minecraft,
	    float partialTicks) {

	Player player = minecraft.player;

	if (player == null || minecraft.level == null) {
	    return;
	}

	ItemStack chestSlot = player.getItemBySlot(EquipmentSlot.CHEST);

	if (!ItemUtils.testItems(chestSlot.getItem(), ElectrodynamicsItems.ITEM_JETPACK.get(),
		ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get())) {
	    return;
	}

	int modeValue = chestSlot.hasTag() ? chestSlot.getTag().getInt(NBTUtils.MODE) : -1;

	Component modeText = MODE_TEXT.get(modeValue);
	int height = window.getGuiScaledHeight();

	IFluidHandlerItem fluidHandler = chestSlot.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve()
		.orElse(null);

	if (fluidHandler == null || fluidHandler.getTanks() == 0) {
	    return;
	}

	int fluidAmount = fluidHandler.getFluidInTank(0).getAmount();
	Component fluidText = FLUID_RATIO_TEXT.get(fluidAmount);

	minecraft.font.draw(stack, modeText, X, height - MODE_Y_OFFSET, 0);

	minecraft.font.draw(stack, fluidText, X, height - FLUID_Y_OFFSET, -1);
    }
}