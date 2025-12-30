package electrodynamics.client.event.guipost;

import java.util.List;

import com.mojang.blaze3d.platform.Window;

import electrodynamics.common.item.gear.armor.types.ItemJetpack;
import electrodynamics.common.item.gear.armor.types.ItemServoLeggings;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.gas.GasStack;
import voltaic.api.item.IItemElectric;
import voltaic.client.event.AbstractPostGuiOverlayHandler;
import voltaic.prefab.screen.component.CachedComponent;
import voltaic.prefab.utilities.ItemUtils;
import voltaic.prefab.utilities.NBTUtils;
import voltaic.prefab.utilities.RenderingUtils;
import voltaic.prefab.utilities.VoltaicTextUtils;
import voltaic.prefab.utilities.math.Color;
import voltaic.registers.VoltaicCapabilities;

public class HandlerArmorData extends AbstractPostGuiOverlayHandler {

    private static final int ITEM_X = 10;
    private static final int TEXT_X = 35;
    private static final float SCALE_08 = 0.8F;
    private static final float SCALE_ITEM = 1.5F;

    private final Component statusGogglesOn = ElectroTextUtils.tooltip("nightvisiongoggles.status")
	    .withStyle(ChatFormatting.GRAY)
	    .append(ElectroTextUtils.tooltip("nightvisiongoggles.on").withStyle(ChatFormatting.GREEN));

    private final Component statusGogglesOff = ElectroTextUtils.tooltip("nightvisiongoggles.status")
	    .withStyle(ChatFormatting.GRAY)
	    .append(ElectroTextUtils.tooltip("nightvisiongoggles.off").withStyle(ChatFormatting.RED));

    private static final CachedComponent<Integer> JETPACK_MODE_TEXT = new CachedComponent<>(ItemJetpack::getModeText);

    private static final CachedComponent<Integer> SERVO_MODE_TEXT = new CachedComponent<>(
	    ItemServoLeggings::getModeText);

    private static final CachedComponent<Integer> CERAMIC_PLATES_TEXT = new CachedComponent<>(plates -> ElectroTextUtils
	    .tooltip("ceramicplatecount", Component.literal(Integer.toString(plates))).withStyle(ChatFormatting.AQUA));

    private static final CachedComponent<Long> JOULES_TEXT = new CachedComponent<>(
	    joules -> ChatFormatter.getChatDisplayShort(Double.longBitsToDouble(joules), DisplayUnits.JOULES));

    private static final CachedComponent<Integer> BOOTS_FLUID_TEXT = new CachedComponent<>(
	    amount -> ChatFormatter.formatFluidMilibuckets(amount));

    private static final CachedComponent<Integer> JETPACK_RATIO_EMPTY = new CachedComponent<>(
	    cap -> VoltaicTextUtils.ratio(Component.literal("0"), ChatFormatter.formatFluidMilibuckets(cap)));

    private static final CachedComponent<Integer> JETPACK_RATIO_AMOUNT = new CachedComponent<>(
	    amount -> VoltaicTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(amount),
		    ChatFormatter.formatFluidMilibuckets(ItemJetpack.MAX_CAPACITY)));

    @Override
    public void renderToScreen(NamedGuiOverlay overlay, GuiGraphics graphics, Window window, Minecraft minecraft,
	    float partialTicks) {

	if (!ElectroConstants.RENDER_COMBAT_ARMOR_STATUS) {
	    return;
	}
	if (minecraft.player == null || minecraft.level == null) {
	    return;
	}

	List<ItemStack> armor = minecraft.player.getInventory().armor;

	graphics.pose().pushPose();

	int heightOffset = window.getGuiScaledHeight();

	ItemStack boots = armor.get(0);
	if (!boots.isEmpty() && handleBoots(boots, overlay, graphics, window, minecraft, heightOffset)) {
	    heightOffset -= 30;
	}

	ItemStack leggings = armor.get(1);
	if (!leggings.isEmpty() && handleLeggings(leggings, overlay, graphics, window, minecraft, heightOffset)) {
	    heightOffset -= 30;
	}

	ItemStack chestplate = armor.get(2);
	if (!chestplate.isEmpty() && handleChestplate(chestplate, overlay, graphics, window, minecraft, heightOffset)) {
	    heightOffset -= 30;
	}

	ItemStack helmet = armor.get(3);
	if (!helmet.isEmpty() && handleHelmet(helmet, overlay, graphics, window, minecraft, heightOffset)) {
	    heightOffset -= 30;
	}

	graphics.pose().popPose();
    }

    private boolean handleHelmet(ItemStack helmet, NamedGuiOverlay overlay, GuiGraphics graphics, Window window,
	    Minecraft minecraft, int heightOffset) {

	boolean renderItem = false;

	if (ItemUtils.testItems(helmet.getItem(), ElectrodynamicsItems.ITEM_NIGHTVISIONGOGGLES.get(),
		ElectrodynamicsItems.ITEM_COMBATHELMET.get())) {
	    renderItem = true;

	    boolean on = helmet.hasTag() && helmet.getTag().getBoolean(NBTUtils.ON);
	    Component mode = on ? statusGogglesOn : statusGogglesOff;

	    double joules = ((IItemElectric) helmet.getItem()).getJoulesStored(helmet);
	    Component joulesText = JOULES_TEXT.get(Double.doubleToLongBits(joules));

	    graphics.drawString(minecraft.font, mode, TEXT_X, heightOffset - 30, Color.BLACK.color());
	    graphics.drawString(minecraft.font, joulesText, TEXT_X, heightOffset - 20, Color.WHITE.color(), false);
	}

	if (renderItem) {
	    RenderingUtils.renderItemScaled(graphics, helmet.getItem(), ITEM_X, heightOffset - 30, SCALE_ITEM);
	}

	return renderItem;
    }

    private static boolean handleChestplate(ItemStack chestplate, NamedGuiOverlay overlay, GuiGraphics graphics,
	    Window window, Minecraft minecraft, int heightOffset) {

	boolean renderItem = false;
	if (ItemUtils.testItems(chestplate.getItem(), ElectrodynamicsItems.ITEM_JETPACK.get())) {
	    renderItem = true;

	    int modeVal = chestplate.hasTag() ? chestplate.getTag().getInt(NBTUtils.MODE) : -1;
	    Component mode = JETPACK_MODE_TEXT.get(modeVal);

	    chestplate.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM).ifPresent(cap -> {
		GasStack gas = cap.getGasInTank(0);

		graphics.drawString(minecraft.font, mode, TEXT_X, heightOffset - 30, 0);

		if (gas.isEmpty()) {
		    Component ratio = JETPACK_RATIO_EMPTY.get(ItemJetpack.MAX_CAPACITY);
		    graphics.drawString(minecraft.font, ratio, TEXT_X, heightOffset - 20, Color.WHITE.color());
		} else {
		    Component ratio = JETPACK_RATIO_AMOUNT.get(gas.getAmount());
		    graphics.drawString(minecraft.font, ratio, TEXT_X, heightOffset - 20, Color.WHITE.color(), false);
		}
	    });
	}

	if (ItemUtils.testItems(chestplate.getItem(), ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get())) {
	    renderItem = true;

	    graphics.pose().pushPose();
	    graphics.pose().scale(SCALE_08, SCALE_08, SCALE_08);

	    int x = (int) (TEXT_X / SCALE_08);

	    int modeVal = chestplate.hasTag() ? chestplate.getTag().getInt(NBTUtils.MODE) : -1;
	    Component mode = JETPACK_MODE_TEXT.get(modeVal);

	    int plates = chestplate.hasTag() ? chestplate.getTag().getInt(NBTUtils.PLATES) : 0;
	    Component platesText = CERAMIC_PLATES_TEXT.get(plates);

	    chestplate.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM).ifPresent(cap -> {
		GasStack gas = cap.getGasInTank(0);

		graphics.drawString(minecraft.font, mode, x, (int) ((heightOffset - 34) / SCALE_08), 0);

		if (gas.isEmpty()) {
		    Component ratio = JETPACK_RATIO_EMPTY.get(ItemJetpack.MAX_CAPACITY);
		    graphics.drawString(minecraft.font, ratio, x, (int) ((heightOffset - 25) / SCALE_08), -1);
		} else {
		    Component ratio = JETPACK_RATIO_AMOUNT.get(gas.getAmount());
		    graphics.drawString(minecraft.font, ratio, x, (int) ((heightOffset - 25) / SCALE_08), -1);
		}

		graphics.drawString(minecraft.font, platesText, x, (int) ((heightOffset - 16) / SCALE_08),
			Color.WHITE.color());
	    });

	    graphics.pose().popPose();
	}

	if (ItemUtils.testItems(chestplate.getItem(), ElectrodynamicsItems.ITEM_COMPOSITECHESTPLATE.get())) {
	    renderItem = true;

	    int plates = chestplate.hasTag() ? chestplate.getTag().getInt(NBTUtils.PLATES) : 0;
	    Component platesText = CERAMIC_PLATES_TEXT.get(plates);

	    graphics.drawString(minecraft.font, platesText, TEXT_X, heightOffset - 25, -1, false);
	}

	if (renderItem) {
	    RenderingUtils.renderItemScaled(graphics, chestplate.getItem(), ITEM_X, heightOffset - 30, SCALE_ITEM);
	}

	return renderItem;
    }

    private boolean handleLeggings(ItemStack leggings, NamedGuiOverlay overlay, GuiGraphics graphics, Window window,
	    Minecraft minecraft, int heightOffset) {

	boolean renderItem = false;

	if (ItemUtils.testItems(leggings.getItem(), ElectrodynamicsItems.ITEM_SERVOLEGGINGS.get(),
		ElectrodynamicsItems.ITEM_COMBATLEGGINGS.get())) {
	    renderItem = true;

	    boolean on = leggings.hasTag() && leggings.getTag().getBoolean(NBTUtils.ON);
	    Component onText = on ? statusGogglesOn : statusGogglesOff;

	    int modeVal = leggings.hasTag() ? leggings.getTag().getInt(NBTUtils.MODE) : -1;
	    Component modeText = SERVO_MODE_TEXT.get(modeVal);

	    double joules = ((IItemElectric) leggings.getItem()).getJoulesStored(leggings);
	    Component joulesText = JOULES_TEXT.get(Double.doubleToLongBits(joules));

	    int x = (int) (TEXT_X / SCALE_08);

	    graphics.pose().pushPose();
	    graphics.pose().scale(SCALE_08, SCALE_08, SCALE_08);

	    graphics.drawString(minecraft.font, onText, x, (int) ((heightOffset - 34) / SCALE_08), 0);
	    graphics.drawString(minecraft.font, modeText, x, (int) ((heightOffset - 25) / SCALE_08),
		    Color.BLACK.color(), false);
	    graphics.drawString(minecraft.font, joulesText, x, (int) ((heightOffset - 16) / SCALE_08),
		    Color.WHITE.color(), false);

	    graphics.pose().popPose();
	}

	if (renderItem) {
	    RenderingUtils.renderItemScaled(graphics, leggings.getItem(), ITEM_X, heightOffset - 30, SCALE_ITEM);
	}

	return renderItem;
    }

    private static boolean handleBoots(ItemStack boots, NamedGuiOverlay overlay, GuiGraphics graphics, Window window,
	    Minecraft minecraft, int heightOffset) {

	boolean renderItem = false;

	if (ItemUtils.testItems(boots.getItem(), ElectrodynamicsItems.ITEM_HYDRAULICBOOTS.get(),
		ElectrodynamicsItems.ITEM_COMBATBOOTS.get())) {
	    renderItem = true;

	    boots.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler -> {
		int amount = handler.getFluidInTank(0).getAmount();
		Component text = BOOTS_FLUID_TEXT.get(amount);
		graphics.drawString(minecraft.font, text, TEXT_X, heightOffset - 25, Color.WHITE.color());
	    });
	}

	if (renderItem) {
	    RenderingUtils.renderItemScaled(graphics, boots.getItem(), ITEM_X, heightOffset - 30, SCALE_ITEM);
	}

	return renderItem;
    }

}
