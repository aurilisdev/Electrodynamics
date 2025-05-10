package electrodynamics.common.event;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.Electrodynamics;
import electrodynamics.common.event.types.living.hurt.HandlerCompositeArmor;
import electrodynamics.common.event.types.player.rightclick.HandlerWrench;
import electrodynamics.common.reloadlistener.CoalGeneratorFuelRegister;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.common.reloadlistener.ThermoelectricGeneratorHeatRegister;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import voltaic.common.event.type.AbstractEquipmentChangeHandler;
import voltaic.common.event.type.AbstractLivingDamageHandler;
import voltaic.common.event.type.AbstractLivingKnockbackHandler;
import voltaic.common.event.type.AbstractPlayerStartTrackingHandler;
import voltaic.common.event.type.AbstractRightClickBlockHandler;

@EventBusSubscriber(modid = Electrodynamics.ID, bus = Bus.FORGE)
public class ServerEventHandler {

	private static final List<AbstractRightClickBlockHandler> RIGHT_CLICK_HANDLERS = new ArrayList<>();
	private static final List<AbstractLivingDamageHandler> LIVING_HURT_HANDLERS = new ArrayList<>();
	private static final List<AbstractLivingKnockbackHandler> LIVING_KNOCKBACK_HANDLERS = new ArrayList<>();
	private static final List<AbstractEquipmentChangeHandler> EQUIPMENT_CHANGE_HANDLERS = new ArrayList<>();
	private static final List<AbstractPlayerStartTrackingHandler> START_TRACKING_PLAYER_HANDLERS = new ArrayList<>();

	public static void init() {
		RIGHT_CLICK_HANDLERS.add(new HandlerWrench());

		LIVING_HURT_HANDLERS.add(new HandlerCompositeArmor());
	}

	@SubscribeEvent
	public static void handleRightClickBlock(RightClickBlock event) {
		RIGHT_CLICK_HANDLERS.forEach(handler -> handler.handle(event));
	}

	@SubscribeEvent
	public static void handlerLivingHurt(LivingDamageEvent event) {
		LIVING_HURT_HANDLERS.forEach(handler -> handler.handle(event));
	}

	@SubscribeEvent
	public static void handleLivingKnockback(LivingKnockBackEvent event) {
		LIVING_KNOCKBACK_HANDLERS.forEach(handler -> handler.handle(event));
	}

	@SubscribeEvent
	public static void handleArmorEquiped(LivingEquipmentChangeEvent event) {
		EQUIPMENT_CHANGE_HANDLERS.forEach(handler -> handler.handler(event));
	}

	@SubscribeEvent
	public static void handlerStartTrackingPlayer(PlayerEvent.StartTracking event) {
		START_TRACKING_PLAYER_HANDLERS.forEach(handler -> handler.handle(event));
	}

	@SubscribeEvent
	public static void addReloadListeners(AddReloadListenerEvent event) {
		event.addListener(CombustionFuelRegister.INSTANCE);
		event.addListener(CoalGeneratorFuelRegister.INSTANCE);
		event.addListener(ThermoelectricGeneratorHeatRegister.INSTANCE);
	}

	@SubscribeEvent
	public static void serverStartedHandler(FMLServerStartedEvent event) {
		CoalGeneratorFuelRegister.INSTANCE.generateTagValues();
		ThermoelectricGeneratorHeatRegister.INSTANCE.generateTagValues();
	}

}
