package electrodynamics.client;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.client.event.guipost.HandlerRailgunTemperature;
import electrodynamics.client.event.levelstage.HandlerMarkerLines;
import electrodynamics.client.event.levelstage.HandlerQuarryArm;
import electrodynamics.client.event.levelstage.HandlerSeismicScanner;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import voltaic.client.event.AbstractLevelStageHandler;
import voltaic.client.event.AbstractPostGuiOverlayHandler;


@Mod.EventBusSubscriber(Dist.CLIENT)
public class ElectrodynamicsClientEvents {

	private static final List<AbstractLevelStageHandler> LEVEL_STAGE_RENDER_HANDLERS = new ArrayList<>();
	private static final List<AbstractPostGuiOverlayHandler> POST_GUI_OVERLAY_HANDLERS = new ArrayList<>();

	public static void init() {

		LEVEL_STAGE_RENDER_HANDLERS.add(HandlerQuarryArm.INSTANCE);
		LEVEL_STAGE_RENDER_HANDLERS.add(HandlerMarkerLines.INSTANCE);
		LEVEL_STAGE_RENDER_HANDLERS.add(HandlerSeismicScanner.INSTANCE);

		POST_GUI_OVERLAY_HANDLERS.add(new HandlerRailgunTemperature());
	}

	@SubscribeEvent
	public static void handlerGuiOverlays(RenderGameOverlayEvent.Post event) {
		POST_GUI_OVERLAY_HANDLERS.forEach(handler -> handler.renderToScreen(event.getType(), event.getMatrixStack(), event.getWindow(), Minecraft.getInstance(), event.getPartialTicks()));
	}

	@SubscribeEvent
	public static void handleRenderEvents(RenderWorldLastEvent event) {
		LEVEL_STAGE_RENDER_HANDLERS.forEach(handler -> {
			handler.render(event.getContext(), event.getMatrixStack(), event.getPartialTicks(), event.getProjectionMatrix(), event.getFinishTimeNano());
		});
	}

	@SubscribeEvent
	public static void wipeRenderHashes(ClientPlayerNetworkEvent.LoggedOutEvent event) {
		PlayerEntity player = event.getPlayer();
		if (player != null) {
			LEVEL_STAGE_RENDER_HANDLERS.forEach(AbstractLevelStageHandler::clear);
		}
	}

}