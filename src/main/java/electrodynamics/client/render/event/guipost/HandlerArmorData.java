package electrodynamics.client.render.event.guipost;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.platform.Window;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.gas.GasStack;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.common.item.gear.armor.types.ItemJetpack;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.ItemUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.prefab.utilities.RenderingUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class HandlerArmorData extends AbstractPostGuiOverlayHandler {

	@Override
	public void renderToScreen(NamedGuiOverlay overlay, GuiGraphics graphics, Window window, Minecraft minecraft, float partialTicks) {

		List<ItemStack> armor = new ArrayList<>();

		minecraft.player.getArmorSlots().forEach(armor::add);

		graphics.pose().pushPose();

		int heightOffset = window.getGuiScaledHeight();

		if (!armor.get(0).isEmpty() && handleBoots(armor.get(0), overlay, graphics, window, minecraft, heightOffset)) {
			heightOffset -= 30;
		}

		if (!armor.get(1).isEmpty() && handleLeggings(armor.get(1), overlay, graphics, window, minecraft, heightOffset)) {
			heightOffset -= 30;
		}

		if (!armor.get(2).isEmpty() && handleChestplate(armor.get(2), overlay, graphics, window, minecraft, heightOffset)) {
			heightOffset -= 30;
		}

		if (!armor.get(3).isEmpty() && handleHelmet(armor.get(3), overlay, graphics, window, minecraft, heightOffset)) {
			heightOffset -= 30;
		}

		graphics.pose().popPose();

	}

	private boolean handleHelmet(ItemStack helmet, NamedGuiOverlay overlay, GuiGraphics graphics, Window window, Minecraft minecraft, int heightOffset) {

		boolean renderItem = false;

		if (ItemUtils.testItems(helmet.getItem(), ElectrodynamicsItems.ITEM_NIGHTVISIONGOGGLES.get(), ElectrodynamicsItems.ITEM_COMBATHELMET.get())) {
			renderItem = true;
			Component mode;
			if (helmet.hasTag() && helmet.getTag().getBoolean(NBTUtils.ON)) {
				mode = ElectroTextUtils.tooltip("nightvisiongoggles.status").withStyle(ChatFormatting.GRAY).append(ElectroTextUtils.tooltip("nightvisiongoggles.on").withStyle(ChatFormatting.GREEN));
			} else {
				mode = ElectroTextUtils.tooltip("nightvisiongoggles.status").withStyle(ChatFormatting.GRAY).append(ElectroTextUtils.tooltip("nightvisiongoggles.off").withStyle(ChatFormatting.RED));
			}
			graphics.drawString(minecraft.font, mode, 35, heightOffset - 30, 0);
			graphics.drawString(minecraft.font, ChatFormatter.getChatDisplayShort(helmet.getOrCreateTag().getDouble(IItemElectric.JOULES_STORED), DisplayUnit.JOULES), 35, heightOffset - 20, -1);
		}

		if (renderItem) {
			RenderingUtils.renderItemScaled(graphics, helmet.getItem(), 10, heightOffset - 30, 1.5F);
		}

		return renderItem;

	}

	private boolean handleChestplate(ItemStack chestplate, NamedGuiOverlay overlay, GuiGraphics graphics, Window window, Minecraft minecraft, int heightOffset) {

		boolean renderItem = false;

		if (ItemUtils.testItems(chestplate.getItem(), ElectrodynamicsItems.ITEM_JETPACK.get())) {
			renderItem = true;
			Component mode = ItemJetpack.getModeText(chestplate.hasTag() ? chestplate.getTag().getInt(NBTUtils.MODE) : -1);
			chestplate.getCapability(ElectrodynamicsCapabilities.GAS_HANDLER_ITEM).ifPresent(cap -> {
				GasStack gas = cap.getGasInTank(0);
				if (gas.isEmpty()) {
					graphics.drawString(minecraft.font, mode, 35, heightOffset - 30, 0);
					graphics.drawString(minecraft.font, ElectroTextUtils.ratio(Component.literal("0"), ChatFormatter.formatFluidMilibuckets(ItemJetpack.MAX_CAPACITY)), 35, heightOffset - 20, -1);
				} else {
					graphics.drawString(minecraft.font, mode, 35, heightOffset - 30, 0);
					graphics.drawString(minecraft.font, ElectroTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(gas.getAmount()), ChatFormatter.formatFluidMilibuckets(ItemJetpack.MAX_CAPACITY)), 35, heightOffset - 20, -1);
				}

			});
		}

		if (ItemUtils.testItems(chestplate.getItem(), ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get())) {
			graphics.pose().pushPose();
			graphics.pose().scale(0.8F, 0.8F, 0.8F);
			renderItem = true;
			Component mode = ItemJetpack.getModeText(chestplate.hasTag() ? chestplate.getTag().getInt(NBTUtils.MODE) : -1);
			chestplate.getCapability(ElectrodynamicsCapabilities.GAS_HANDLER_ITEM).ifPresent(cap -> {
				GasStack gas = cap.getGasInTank(0);
				int x = (int) (35 / 0.8F);
				if (gas.isEmpty()) {
					graphics.drawString(minecraft.font, mode, x, (int) ((heightOffset - 34) / 0.8F), 0);
					graphics.drawString(minecraft.font, ElectroTextUtils.ratio(Component.literal("0"), ChatFormatter.formatFluidMilibuckets(ItemJetpack.MAX_CAPACITY)), x, (int) ((heightOffset - 25) / 0.8F), -1);
					graphics.drawString(minecraft.font, ElectroTextUtils.tooltip("ceramicplatecount", Component.literal((chestplate.hasTag() ? chestplate.getTag().getInt(NBTUtils.PLATES) : 0) + "")).withStyle(ChatFormatting.AQUA), x, (int) ((heightOffset - 16) / 0.8F), -1);
				} else {
					graphics.drawString(minecraft.font, mode, x, (int) ((heightOffset - 34) / 0.8F), 0);
					graphics.drawString(minecraft.font, ElectroTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(gas.getAmount()), ChatFormatter.formatFluidMilibuckets(ItemJetpack.MAX_CAPACITY)), x, (int) ((heightOffset - 25) / 0.8F), -1);
					graphics.drawString(minecraft.font, ElectroTextUtils.tooltip("ceramicplatecount", Component.literal((chestplate.hasTag() ? chestplate.getTag().getInt(NBTUtils.PLATES) : 0) + "")).withStyle(ChatFormatting.AQUA), x, (int) ((heightOffset - 16) / 0.8F), -1);
				}

			});
			graphics.pose().popPose();
		}

		if (ItemUtils.testItems(chestplate.getItem(), ElectrodynamicsItems.ITEM_COMPOSITECHESTPLATE.get())) {
			renderItem = true;
			graphics.drawString(minecraft.font, ElectroTextUtils.tooltip("ceramicplatecount", Component.literal((chestplate.hasTag() ? chestplate.getTag().getInt(NBTUtils.PLATES) : 0) + "")).withStyle(ChatFormatting.AQUA), 35, heightOffset - 25, -1);
		}

		if (renderItem) {
			RenderingUtils.renderItemScaled(graphics, chestplate.getItem(), 10, heightOffset - 30, 1.5F);
		}

		return renderItem;
	}

	private boolean handleLeggings(ItemStack leggings, NamedGuiOverlay overlay, GuiGraphics graphics, Window window, Minecraft minecraft, int heightOffset) {

		boolean renderItem = false;

		if (ItemUtils.testItems(leggings.getItem(), ElectrodynamicsItems.ITEM_SERVOLEGGINGS.get(), ElectrodynamicsItems.ITEM_COMBATLEGGINGS.get())) {
			renderItem = true;
			Component on;
			if (leggings.hasTag() && leggings.getTag().getBoolean(NBTUtils.ON)) {
				on = ElectroTextUtils.tooltip("nightvisiongoggles.status").withStyle(ChatFormatting.GRAY).append(ElectroTextUtils.tooltip("nightvisiongoggles.on").withStyle(ChatFormatting.GREEN));
			} else {
				on = ElectroTextUtils.tooltip("nightvisiongoggles.status").withStyle(ChatFormatting.GRAY).append(ElectroTextUtils.tooltip("nightvisiongoggles.off").withStyle(ChatFormatting.RED));
			}
			int x = (int) (35 / 0.8F);
			graphics.pose().pushPose();
			graphics.pose().scale(0.8F, 0.8F, 0.8F);
			graphics.drawString(minecraft.font, on, x, (int) ((heightOffset - 34) / 0.8F), 0);
			graphics.drawString(minecraft.font, ItemJetpack.getModeText(leggings.hasTag() ? leggings.getTag().getInt(NBTUtils.MODE) : -1), x, (int) ((heightOffset - 25) / 0.8F), 0);
			graphics.drawString(minecraft.font, ChatFormatter.getChatDisplayShort(leggings.getOrCreateTag().getDouble(IItemElectric.JOULES_STORED), DisplayUnit.JOULES), x, (int) ((heightOffset - 16) / 0.8F), -1);
			graphics.pose().popPose();
		}

		if (renderItem) {
			RenderingUtils.renderItemScaled(graphics, leggings.getItem(), 10, heightOffset - 30, 1.5F);
		}

		return renderItem;
	}

	private boolean handleBoots(ItemStack boots, NamedGuiOverlay overlay, GuiGraphics graphics, Window window, Minecraft minecraft, int heightOffset) {

		boolean renderItem = false;

		if (ItemUtils.testItems(boots.getItem(), ElectrodynamicsItems.ITEM_HYDRAULICBOOTS.get(), ElectrodynamicsItems.ITEM_COMBATBOOTS.get())) {
			renderItem = true;
			boots.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(h -> graphics.drawString(minecraft.font, ChatFormatter.formatFluidMilibuckets(h.getFluidInTank(0).getAmount()), 35, heightOffset - 25, -1));
		}

		if (renderItem) {
			RenderingUtils.renderItemScaled(graphics, boots.getItem(), 10, heightOffset - 30, 1.5F);
		}

		return renderItem;
	}

}
