package electrodynamics.client.event.guipost;

import java.util.List;

import electrodynamics.common.item.gear.armor.types.ItemJetpack;
import electrodynamics.common.item.gear.armor.types.ItemServoLeggings;
import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.gas.GasStack;
import voltaic.api.gas.IGasHandlerItem;
import voltaic.api.item.IItemElectric;
import voltaic.client.event.AbstractPostGuiOverlayHandler;
import voltaic.prefab.screen.component.CachedComponent;
import voltaic.prefab.utilities.ItemUtils;
import voltaic.prefab.utilities.RenderingUtils;
import voltaic.prefab.utilities.VoltaicTextUtils;
import voltaic.prefab.utilities.math.Color;
import voltaic.registers.VoltaicCapabilities;
import voltaic.registers.VoltaicDataComponentTypes;

public class HandlerArmorData extends AbstractPostGuiOverlayHandler {

    private static final float SMALL_SCALE = 0.8F;
    private static final int ICON_X = 10;
    private static final int TEXT_X = 35;

    private final Component statusGogglesOn = ElectroTextUtils.tooltip("nightvisiongoggles.status")
	    .withStyle(ChatFormatting.GRAY)
	    .append(ElectroTextUtils.tooltip("nightvisiongoggles.on").withStyle(ChatFormatting.GREEN));
    private final Component statusGogglesOff = ElectroTextUtils.tooltip("nightvisiongoggles.status")
	    .withStyle(ChatFormatting.GRAY)
	    .append(ElectroTextUtils.tooltip("nightvisiongoggles.off").withStyle(ChatFormatting.RED));

    private final CachedComponent<Integer> jetpackModeCached = new CachedComponent<>(ItemJetpack::getModeText);
    private final CachedComponent<Integer> servoModeCached = new CachedComponent<>(ItemServoLeggings::getModeText);
    private final CachedComponent<Integer> ceramicPlateCountCached = new CachedComponent<>(plates -> ElectroTextUtils
	    .tooltip("ceramicplatecount", Component.literal(Integer.toString(plates))).withStyle(ChatFormatting.AQUA));

    private final CachedComponent<Double> joulesStorageCached = new CachedComponent<>(
	    joules -> ChatFormatter.getChatDisplayShort(joules, DisplayUnits.JOULES));

    @Override
    public void renderToScreen(GuiGraphics graphics, DeltaTracker tracker, Minecraft minecraft) {

	if (!ElectrodynamicsConfig.INSTANCE.RENDER_COMBAT_ARMOR_STATUS.get() || minecraft.player == null) {
	    return;
	}

	List<ItemStack> armor = minecraft.player.getInventory().armor;

	graphics.pose().pushPose();

	int heightOffset = graphics.guiHeight();

	if (!armor.get(0).isEmpty() && handleBoots(armor.get(0), graphics, minecraft, heightOffset)) {
	    heightOffset -= 30;
	}

	if (!armor.get(1).isEmpty() && handleLeggings(armor.get(1), graphics, minecraft, heightOffset)) {
	    heightOffset -= 30;
	}

	if (!armor.get(2).isEmpty() && handleChestplate(armor.get(2), graphics, minecraft, heightOffset)) {
	    heightOffset -= 30;
	}

	if (!armor.get(3).isEmpty() && handleHelmet(armor.get(3), graphics, minecraft, heightOffset)) {
	    heightOffset -= 30;
	}

	graphics.pose().popPose();

    }

    private boolean handleHelmet(ItemStack helmet, GuiGraphics graphics, Minecraft minecraft, int heightOffset) {

	if (!ItemUtils.testItems(helmet.getItem(), ElectrodynamicsItems.ITEM_NIGHTVISIONGOGGLES.get(),
		ElectrodynamicsItems.ITEM_COMBATHELMET.get())) {
	    return false;
	}

	Component status = helmet.getOrDefault(VoltaicDataComponentTypes.ON, false) ? statusGogglesOn
		: statusGogglesOff;
	double joules = ((IItemElectric) helmet.getItem()).getJoulesStored(helmet);

	graphics.drawString(minecraft.font, status, TEXT_X, heightOffset - 30, Color.BLACK.color());
	graphics.drawString(minecraft.font, joulesStorageCached.get(joules), TEXT_X, heightOffset - 20,
		Color.WHITE.color(), false);

	RenderingUtils.renderItemScaled(graphics, helmet.getItem(), ICON_X, heightOffset - 30, 1.5F);
	return true;

    }

    private boolean handleChestplate(ItemStack chestplate, GuiGraphics graphics, Minecraft minecraft,
	    int heightOffset) {

	// Jetpack
	if (ItemUtils.testItems(chestplate.getItem(), ElectrodynamicsItems.ITEM_JETPACK.get())) {

	    Component mode = jetpackModeCached.get(chestplate.getOrDefault(VoltaicDataComponentTypes.MODE, 0));
	    IGasHandlerItem handler = chestplate.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM);

	    if (handler != null) {
		GasStack gas = handler.getGasInTank(0);
		Component gasText = gas.isEmpty()
			? VoltaicTextUtils.ratio(Component.literal("0"),
				ChatFormatter.formatFluidMilibuckets(ItemJetpack.MAX_CAPACITY))
			: VoltaicTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(gas.getAmount()),
				ChatFormatter.formatFluidMilibuckets(ItemJetpack.MAX_CAPACITY));

		graphics.drawString(minecraft.font, mode, TEXT_X, heightOffset - 30, 0);
		graphics.drawString(minecraft.font, gasText, TEXT_X, heightOffset - 20, Color.WHITE.color(), false);
	    }

	    RenderingUtils.renderItemScaled(graphics, chestplate.getItem(), ICON_X, heightOffset - 30, 1.5F);
	    return true;

	}

	// Combat chestplate (scaled)
	if (ItemUtils.testItems(chestplate.getItem(), ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get())) {

	    graphics.pose().pushPose();
	    graphics.pose().scale(SMALL_SCALE, SMALL_SCALE, SMALL_SCALE);

	    int scaledX = (int) (TEXT_X / SMALL_SCALE);
	    int scaledY0 = (int) ((heightOffset - 34) / SMALL_SCALE);
	    int scaledY1 = (int) ((heightOffset - 25) / SMALL_SCALE);
	    int scaledY2 = (int) ((heightOffset - 16) / SMALL_SCALE);

	    Component mode = jetpackModeCached.get(chestplate.getOrDefault(VoltaicDataComponentTypes.MODE, 0));
	    IGasHandlerItem handler = chestplate.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM);

	    if (handler != null) {
		GasStack gas = handler.getGasInTank(0);
		Component gasText = gas.isEmpty()
			? VoltaicTextUtils.ratio(Component.literal("0"),
				ChatFormatter.formatFluidMilibuckets(ItemJetpack.MAX_CAPACITY))
			: VoltaicTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(gas.getAmount()),
				ChatFormatter.formatFluidMilibuckets(ItemJetpack.MAX_CAPACITY));

		int plates = chestplate.getOrDefault(VoltaicDataComponentTypes.PLATES, 0);

		graphics.drawString(minecraft.font, mode, scaledX, scaledY0, 0);
		graphics.drawString(minecraft.font, gasText, scaledX, scaledY1, -1, false);
		graphics.drawString(minecraft.font, ceramicPlateCountCached.get(plates), scaledX, scaledY2,
			Color.WHITE.color());
	    }

	    graphics.pose().popPose();
	    RenderingUtils.renderItemScaled(graphics, chestplate.getItem(), ICON_X, heightOffset - 30, 1.5F);
	    return true;

	}

	// Composite chestplate
	if (ItemUtils.testItems(chestplate.getItem(), ElectrodynamicsItems.ITEM_COMPOSITECHESTPLATE.get())) {
	    int plates = chestplate.getOrDefault(VoltaicDataComponentTypes.PLATES, 0);
	    graphics.drawString(minecraft.font, ceramicPlateCountCached.get(plates), TEXT_X, heightOffset - 25,
		    Color.WHITE.color(), false);
	    RenderingUtils.renderItemScaled(graphics, chestplate.getItem(), ICON_X, heightOffset - 30, 1.5F);
	    return true;
	}

	return false;

    }

    private boolean handleLeggings(ItemStack leggings, GuiGraphics graphics, Minecraft minecraft, int heightOffset) {

	if (!ItemUtils.testItems(leggings.getItem(), ElectrodynamicsItems.ITEM_SERVOLEGGINGS.get(),
		ElectrodynamicsItems.ITEM_COMBATLEGGINGS.get())) {
	    return false;
	}

	Component status = leggings.getOrDefault(VoltaicDataComponentTypes.ON, false) ? statusGogglesOn
		: statusGogglesOff;
	Component mode = servoModeCached.get(leggings.getOrDefault(VoltaicDataComponentTypes.MODE, -1));
	double joules = ((IItemElectric) leggings.getItem()).getJoulesStored(leggings);

	int x = (int) (TEXT_X / SMALL_SCALE);

	graphics.pose().pushPose();
	graphics.pose().scale(SMALL_SCALE, SMALL_SCALE, SMALL_SCALE);
	graphics.drawString(minecraft.font, status, x, (int) ((heightOffset - 34) / SMALL_SCALE), 0);
	graphics.drawString(minecraft.font, mode, x, (int) ((heightOffset - 25) / SMALL_SCALE), Color.BLACK.color(),
		false);
	graphics.drawString(minecraft.font, joulesStorageCached.get(joules), x,
		(int) ((heightOffset - 16) / SMALL_SCALE), Color.WHITE.color(), false);
	graphics.pose().popPose();

	RenderingUtils.renderItemScaled(graphics, leggings.getItem(), ICON_X, heightOffset - 30, 1.5F);
	return true;

    }

    private boolean handleBoots(ItemStack boots, GuiGraphics graphics, Minecraft minecraft, int heightOffset) {

	if (!ItemUtils.testItems(boots.getItem(), ElectrodynamicsItems.ITEM_HYDRAULICBOOTS.get(),
		ElectrodynamicsItems.ITEM_COMBATBOOTS.get())) {
	    return false;
	}

	IFluidHandlerItem handler = boots.getCapability(Capabilities.FluidHandler.ITEM);
	if (handler != null) {
	    graphics.drawString(minecraft.font,
		    ChatFormatter.formatFluidMilibuckets(handler.getFluidInTank(0).getAmount()), TEXT_X,
		    heightOffset - 25, Color.WHITE.color());
	}

	RenderingUtils.renderItemScaled(graphics, boots.getItem(), ICON_X, heightOffset - 30, 1.5F);
	return true;

    }

}
