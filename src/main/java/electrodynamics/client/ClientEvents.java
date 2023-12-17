package electrodynamics.client;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.render.event.guipost.AbstractPostGuiOverlayHandler;
import electrodynamics.client.render.event.guipost.HandlerRailgunTemperature;
import electrodynamics.client.render.event.levelstage.AbstractLevelStageHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

	private static final List<AbstractPostGuiOverlayHandler> POST_GUI_OVERLAY_HANDLERS = new ArrayList<>();

	public static void init() {

		POST_GUI_OVERLAY_HANDLERS.add(new HandlerRailgunTemperature());

	}

	@SubscribeEvent
	public static void handlerGuiOverlays(RenderGameOverlayEvent.Post event) {
		POST_GUI_OVERLAY_HANDLERS.forEach(handler -> handler.renderToScreen(event.getType(), event.getMatrixStack(), event.getWindow(), Minecraft.getInstance(), event.getPartialTicks()));
	}

	@SubscribeEvent
	public static void wipeRenderHashes(ClientPlayerNetworkEvent.LoggedOutEvent event) {
		ScreenGuidebook.setInitNotHappened();
	}


}