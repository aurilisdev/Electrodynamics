package electrodynamics.common.event;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.Electrodynamics;
import electrodynamics.api.References;
import electrodynamics.common.event.types.living.equipmentchange.AbstractEquipmentChangeHandler;
import electrodynamics.common.event.types.living.equipmentchange.HandlerJetpackEquiped;
import electrodynamics.common.event.types.living.hurt.AbstractLivingHurtHandler;
import electrodynamics.common.event.types.living.hurt.HandlerCompositeArmor;
import electrodynamics.common.event.types.living.hurt.HandlerHydraulicBoots;
import electrodynamics.common.event.types.living.hurt.HandlerJetpackDamage;
import electrodynamics.common.event.types.living.knockback.AbstractLivingKnockbackHandler;
import electrodynamics.common.event.types.living.knockback.HandlerJetpackKnockbackImpulse;
import electrodynamics.common.event.types.player.rightclick.AbstractRightClickBlockHandler;
import electrodynamics.common.event.types.player.rightclick.HandlerWrench;
import electrodynamics.common.event.types.player.starttracking.AbstractPlayerStartTrackingHandler;
import electrodynamics.common.event.types.player.starttracking.HandlerJetpackSound;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.server.PacketPlayerInformation;
import electrodynamics.common.reloadlistener.CoalGeneratorFuelRegister;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.common.reloadlistener.ThermoelectricGeneratorHeatRegister;
import electrodynamics.registers.ElectrodynamicsGases;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class ServerEventHandler {

	private static final List<AbstractRightClickBlockHandler> RIGHT_CLICK_HANDLERS = new ArrayList<>();
	private static final List<AbstractLivingHurtHandler> LIVING_HURT_HANDLERS = new ArrayList<>();
	private static final List<AbstractLivingKnockbackHandler> LIVING_KNOCKBACK_HANDLERS = new ArrayList<>();
	private static final List<AbstractEquipmentChangeHandler> EQUIPMENT_CHANGE_HANDLERS = new ArrayList<>();
	private static final List<AbstractPlayerStartTrackingHandler> START_TRACKING_PLAYER_HANDLERS = new ArrayList<>();

	public static void init() {
		RIGHT_CLICK_HANDLERS.add(new HandlerWrench());

		LIVING_HURT_HANDLERS.add(new HandlerCompositeArmor());
		LIVING_HURT_HANDLERS.add(new HandlerHydraulicBoots());
		LIVING_HURT_HANDLERS.add(new HandlerJetpackDamage());

		LIVING_KNOCKBACK_HANDLERS.add(new HandlerJetpackKnockbackImpulse());

		EQUIPMENT_CHANGE_HANDLERS.add(new HandlerJetpackEquiped());

		START_TRACKING_PLAYER_HANDLERS.add(new HandlerJetpackSound());
	}

	@SubscribeEvent
	public static void handleRightClickBlock(RightClickBlock event) {
		RIGHT_CLICK_HANDLERS.forEach(handler -> handler.handle(event));
	}

	@SubscribeEvent
	public static void handlerLivingHurt(LivingHurtEvent event) {
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
	public static void tick(PlayerTickEvent event) {
		if (event.side == LogicalSide.CLIENT && event.player.level().getLevelData().getDayTime() % 50 == 10) {
			NetworkHandler.CHANNEL.sendToServer(new PacketPlayerInformation());
		}
	}

	@SubscribeEvent
	public static void addReloadListeners(AddReloadListenerEvent event) {
		event.addListener(CombustionFuelRegister.INSTANCE);
		event.addListener(CoalGeneratorFuelRegister.INSTANCE);
		event.addListener(ThermoelectricGeneratorHeatRegister.INSTANCE);
	}

	@SubscribeEvent
	public static void serverStartedHandler(ServerStartedEvent event) {
		CoalGeneratorFuelRegister.INSTANCE.generateTagValues();
		ThermoelectricGeneratorHeatRegister.INSTANCE.generateTagValues();
		Electrodynamics.LOGGER.info(ElectrodynamicsGases.EMPTY.get());
	}

	// TODO: Why was this commented?
	/*
	 * @SubscribeEvent public static void jetpackOwnerSoundHandler(PlayerLoggedInEvent event) { Player player = event.getPlayer(); if(player instanceof ServerPlayer server) { ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST); if(!chest.isEmpty() && (ItemUtils.testItems(chest.getItem(), DeferredRegisters.ITEM_JETPACK.get()) || ItemUtils.testItems(chest.getItem(), DeferredRegisters.ITEM_COMBATCHESTPLATE.get()))) { NetworkHandler.CHANNEL.sendTo(new PacketJetpackEquipedSound(player.getUUID()), server.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT); } } }
	 */

}
