package electrodynamics.client;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.client.keys.event.AbstractKeyPressHandler;
import electrodynamics.client.keys.event.HandlerModeSwitchJetpack;
import electrodynamics.client.keys.event.HandlerToggleNVGoggles;
import electrodynamics.client.keys.event.HandlerToggleServoLegs;
import electrodynamics.client.render.event.levelstage.AbstractLevelStageHandler;
import electrodynamics.client.render.event.levelstage.HandlerMarkerLines;
import electrodynamics.client.render.event.levelstage.HandlerQuarryArm;
import electrodynamics.common.item.gear.tools.electric.utils.ItemRailgun;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.InputEvent.Key;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

	private static final List<AbstractKeyPressHandler> KEY_PRESS_HANDLERS = new ArrayList<>();
	private static final List<AbstractLevelStageHandler> LEVEL_STAGE_RENDER_HANDLERS = new ArrayList<>();
	
	public static void init() {
		KEY_PRESS_HANDLERS.add(new HandlerToggleNVGoggles());
		KEY_PRESS_HANDLERS.add(new HandlerToggleServoLegs());
		KEY_PRESS_HANDLERS.add(new HandlerToggleServoLegs());
		KEY_PRESS_HANDLERS.add(new HandlerModeSwitchJetpack());
		
		LEVEL_STAGE_RENDER_HANDLERS.add(HandlerQuarryArm.INSTANCE);
		LEVEL_STAGE_RENDER_HANDLERS.add(HandlerMarkerLines.INSTANCE);
	}
	
	@SubscribeEvent
	public static void renderRailgunTooltip(RenderGuiOverlayEvent.Post event) {
		Player player = Minecraft.getInstance().player;
		ItemStack gunStackMainHand = player.getItemBySlot(EquipmentSlot.MAINHAND);
		ItemStack gunStackOffHand = player.getItemBySlot(EquipmentSlot.OFFHAND);

		if (gunStackMainHand.getItem() instanceof ItemRailgun) {
			renderHeatToolTip(event, gunStackMainHand);
		} else if (gunStackOffHand.getItem() instanceof ItemRailgun) {
			renderHeatToolTip(event, gunStackOffHand);
		}
	}

	private static void renderHeatToolTip(RenderGuiOverlayEvent.Post event, ItemStack stack) {
		Minecraft minecraft = Minecraft.getInstance();
		ItemRailgun railgun = (ItemRailgun) stack.getItem();
		double temperature = railgun.getTemperatureStored(stack);
		String correction = "";

		event.getPoseStack().pushPose();

		if (temperature < 10) {
			correction = "00";
		} else if (temperature < 100) {
			correction = "0";
		} else {
			correction = "";
		}

		TextUtils.tooltip("railguntemp", Component.literal(temperature + correction + " C"));
		
		Component currTempText = TextUtils.tooltip("railguntemp", Component.literal(temperature + correction + " C")).withStyle(ChatFormatting.YELLOW);
		Component maxTempText = TextUtils.tooltip("railgunmaxtemp", Component.literal(railgun.getMaxTemp() + " C")).withStyle(ChatFormatting.YELLOW);

		GuiComponent.drawCenteredString(event.getPoseStack(), minecraft.font, currTempText, 55, 2, 0);
		GuiComponent.drawCenteredString(event.getPoseStack(), minecraft.font, maxTempText, 48, 11, 0);

		if (temperature >= railgun.getOverheatTemp()) {
			Component overheatWarn = TextUtils.tooltip("railgunoverheat").withStyle(ChatFormatting.RED, ChatFormatting.BOLD);
			GuiComponent.drawCenteredString(event.getPoseStack(), minecraft.font, overheatWarn, 70, 20, 0);
		}

		minecraft.getTextureManager().bindForSetup(GuiComponent.GUI_ICONS_LOCATION);

		event.getPoseStack().popPose();
	}

	@SubscribeEvent
	public static void handleRenderEvents(RenderLevelStageEvent event) {
		LEVEL_STAGE_RENDER_HANDLERS.forEach(handler -> {
			if(handler.shouldRender(event.getStage())) {
				handler.render(event, Minecraft.getInstance());
			}
		});
	}

	@SubscribeEvent
	public static void wipeRenderHashes(ClientPlayerNetworkEvent.LoggingOut event) {
		Player player = event.getPlayer();
		if (player != null) {
			LEVEL_STAGE_RENDER_HANDLERS.forEach(handler -> handler.clear());
		}
	}

	@SubscribeEvent
	public static void handleKeyPress(Key event) {
		KEY_PRESS_HANDLERS.forEach(handler -> handler.handler(event, Minecraft.getInstance()));
	}

}