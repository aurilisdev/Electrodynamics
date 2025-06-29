package electrodynamics.common.event;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.Electrodynamics;
import electrodynamics.common.event.types.living.equipmentchange.HandlerJetpackEquiped;
import electrodynamics.common.event.types.living.incomingdamage.HandlerCombatArmor;
import electrodynamics.common.event.types.living.knockback.HandlerJetpackKnockbackImpulse;
import electrodynamics.common.event.types.living.livingdamage.HandlerCompositeArmor;
import electrodynamics.common.event.types.living.livingdamage.HandlerHydraulicBoots;
import electrodynamics.common.event.types.living.livingdamage.HandlerJetpackDamage;
import electrodynamics.common.event.types.player.rightclick.HandlerWrench;
import electrodynamics.common.event.types.player.starttracking.HandlerJetpackSound;
import electrodynamics.common.reloadlistener.CoalGeneratorFuelRegister;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.common.reloadlistener.GasCollectorChromoCardsRegister;
import electrodynamics.common.reloadlistener.ThermoelectricGeneratorHeatRegister;
import electrodynamics.compatibility.mekanism.MekanismHandler;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import voltaic.Voltaic;
import voltaic.api.item.IItemElectric;
import voltaic.common.event.type.AbstractEquipmentChangeHandler;
import voltaic.common.event.type.AbstractIncomingDamageHandler;
import voltaic.common.event.type.AbstractLivingDamageHandler;
import voltaic.common.event.type.AbstractLivingKnockbackHandler;
import voltaic.common.event.type.AbstractPlayerStartTrackingHandler;
import voltaic.common.event.type.AbstractRightClickBlockHandler;

@EventBusSubscriber(modid = Electrodynamics.ID, bus = EventBusSubscriber.Bus.GAME)
public class ServerEventHandler {

	private static final List<AbstractRightClickBlockHandler> RIGHT_CLICK_HANDLERS = new ArrayList<>();
	private static final List<AbstractLivingDamageHandler> LIVING_DAMAGE_HANDLERS = new ArrayList<>();
	private static final List<AbstractLivingKnockbackHandler> LIVING_KNOCKBACK_HANDLERS = new ArrayList<>();
	private static final List<AbstractEquipmentChangeHandler> EQUIPMENT_CHANGE_HANDLERS = new ArrayList<>();
	private static final List<AbstractPlayerStartTrackingHandler> START_TRACKING_PLAYER_HANDLERS = new ArrayList<>();
	private static final List<AbstractIncomingDamageHandler> INCOMING_DAMAGE_HANDLERS = new ArrayList<>();

	public static void init() {
		RIGHT_CLICK_HANDLERS.add(new HandlerWrench());

		LIVING_DAMAGE_HANDLERS.add(new HandlerCompositeArmor());
		LIVING_DAMAGE_HANDLERS.add(new HandlerHydraulicBoots());
		LIVING_DAMAGE_HANDLERS.add(new HandlerJetpackDamage());

		LIVING_KNOCKBACK_HANDLERS.add(new HandlerJetpackKnockbackImpulse());

		EQUIPMENT_CHANGE_HANDLERS.add(new HandlerJetpackEquiped());

		START_TRACKING_PLAYER_HANDLERS.add(new HandlerJetpackSound());

		INCOMING_DAMAGE_HANDLERS.add(new HandlerCombatArmor());
	}


	@SubscribeEvent
	public static void handleRightClickBlock(RightClickBlock event) {
		RIGHT_CLICK_HANDLERS.forEach(handler -> handler.handle(event));
	}

	@SubscribeEvent
	public static void handlerLivingHurt(LivingDamageEvent.Pre event) {
		LIVING_DAMAGE_HANDLERS.forEach(handler -> handler.handle(event));
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
	public static void handlerLivingIncomingDamage(LivingIncomingDamageEvent event) {
		INCOMING_DAMAGE_HANDLERS.forEach(handler -> handler.handle(event));
	}

	@SubscribeEvent
	public static void addReloadListeners(AddReloadListenerEvent event) {
		event.addListener(CombustionFuelRegister.INSTANCE);
		event.addListener(CoalGeneratorFuelRegister.INSTANCE);
		event.addListener(ThermoelectricGeneratorHeatRegister.INSTANCE);
		event.addListener(GasCollectorChromoCardsRegister.INSTANCE);

		if(ModList.get().isLoaded(Voltaic.MEKANISM_ID)) {
			MekanismHandler.addDataListener(event);
		}

	}

	@SubscribeEvent
	public static void serverStartedHandler(ServerStartedEvent event) {
		CoalGeneratorFuelRegister.INSTANCE.generateTagValues();
		ThermoelectricGeneratorHeatRegister.INSTANCE.generateTagValues();
		GasCollectorChromoCardsRegister.INSTANCE.generateTagValues();
	}

	// Fuck this game its becoming so ass to code in
	@SubscribeEvent
	public static void handleElectricToolDamage(AttackEntityEvent event) {
		ItemStack stack = event.getEntity().getWeaponItem();
		if(stack.isEmpty() || !(stack.is(ElectrodynamicsItems.ITEM_ELECTRICBATON) || stack.is(ElectrodynamicsItems.ITEM_ELECTRICCHAINSAW) || stack.is(ElectrodynamicsItems.ITEM_ELECTRICDRILL))) {
			return;
		}
		IItemElectric electric = (IItemElectric) stack.getItem();
		if(electric.getJoulesStored(stack) >= electric.getElectricProperties().extract.getJoules()) {
			return;
		}
		event.setCanceled(true);
	}

	// TODO: Why was this commented?
	/*
	 * @SubscribeEvent public static void jetpackOwnerSoundHandler(PlayerLoggedInEvent event) { Player player = event.getPlayer(); if(player instanceof ServerPlayer server) { ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST); if(!chest.isEmpty() && (ItemUtils.testItems(chest.getItem(), DeferredRegisters.ITEM_JETPACK.get()) || ItemUtils.testItems(chest.getItem(), DeferredRegisters.ITEM_COMBATCHESTPLATE.get()))) { NetworkHandler.CHANNEL.sendTo(new PacketJetpackEquipedSound(player.getUUID()), server.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT); } } }
	 */

}
