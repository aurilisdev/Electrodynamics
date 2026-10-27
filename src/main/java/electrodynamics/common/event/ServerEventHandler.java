package electrodynamics.common.event;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.Electrodynamics;
import electrodynamics.common.event.types.living.equipmentchange.HandlerJetpackEquiped;
import electrodynamics.common.event.types.living.hurt.HandlerCompositeArmor;
import electrodynamics.common.event.types.living.hurt.HandlerHydraulicBoots;
import electrodynamics.common.event.types.living.hurt.HandlerJetpackDamage;
import electrodynamics.common.event.types.living.knockback.HandlerJetpackKnockbackImpulse;
import electrodynamics.common.event.types.player.rightclick.HandlerWrench;
import electrodynamics.common.event.types.player.starttracking.HandlerJetpackSound;
import electrodynamics.common.reloadlistener.CoalGeneratorFuelRegister;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.common.reloadlistener.ThermoelectricGeneratorHeatRegister;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import voltaic.Voltaic;
import voltaic.api.item.IItemElectric;
import voltaic.prefab.item.CapabilityForgeEnergyItem;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import voltaic.common.event.type.AbstractEquipmentChangeHandler;
import voltaic.common.event.type.AbstractLivingDamageHandler;
import voltaic.common.event.type.AbstractLivingKnockbackHandler;
import voltaic.common.event.type.AbstractPlayerStartTrackingHandler;
import voltaic.common.event.type.AbstractRightClickBlockHandler;

@EventBusSubscriber(modid = Electrodynamics.ID, bus = Bus.FORGE)
public class ServerEventHandler {

    private static final ResourceLocation FE_ITEM_CAP_ID = new ResourceLocation(Electrodynamics.ID, "forge_energy_item");

    private static final List<AbstractRightClickBlockHandler> RIGHT_CLICK_HANDLERS = new ArrayList<>();
    private static final List<AbstractLivingDamageHandler> LIVING_HURT_HANDLERS = new ArrayList<>();
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
    public static void attachItemCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
	ItemStack stack = event.getObject();
	Item item = stack.getItem();
	if (!(item instanceof IItemElectric) || !isForgeEnergyItem(item)) {
	    return;
	}
	CapabilityForgeEnergyItem energy = new CapabilityForgeEnergyItem(stack, true, true);
	LazyOptional<IEnergyStorage> optional = LazyOptional.of(() -> energy);
	event.addCapability(FE_ITEM_CAP_ID, new ICapabilityProvider() {

	    @Override
	    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ForgeCapabilities.ENERGY && Voltaic.isForgeEnergyEnabled()) {
		    return optional.cast();
		}
		return LazyOptional.empty();
	    }
	});
    }

    private static boolean isForgeEnergyItem(Item item) {
	return item == ElectrodynamicsItems.ITEM_BATTERY.get() || item == ElectrodynamicsItems.ITEM_LITHIUMBATTERY.get()
		|| item == ElectrodynamicsItems.ITEM_CARBYNEBATTERY.get() || item == ElectrodynamicsItems.ITEM_ELECTRICDRILL.get()
		|| item == ElectrodynamicsItems.ITEM_ELECTRICCHAINSAW.get() || item == ElectrodynamicsItems.ITEM_ELECTRICBATON.get()
		|| item == ElectrodynamicsItems.ITEM_KINETICRAILGUN.get() || item == ElectrodynamicsItems.ITEM_PLASMARAILGUN.get()
		|| item == ElectrodynamicsItems.ITEM_SEISMICSCANNER.get() || item == ElectrodynamicsItems.ITEM_MECHANIZEDCROSSBOW.get()
		|| item == ElectrodynamicsItems.ITEM_NIGHTVISIONGOGGLES.get() || item == ElectrodynamicsItems.ITEM_SERVOLEGGINGS.get()
		|| item == ElectrodynamicsItems.ITEM_COMBATHELMET.get() || item == ElectrodynamicsItems.ITEM_COMBATLEGGINGS.get();
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
    public static void serverStartedHandler(ServerStartedEvent event) {
	CoalGeneratorFuelRegister.INSTANCE.generateTagValues();
	ThermoelectricGeneratorHeatRegister.INSTANCE.generateTagValues();
    }

    // TODO: Why was this commented?
    /*
     * @SubscribeEvent public static void
     * jetpackOwnerSoundHandler(PlayerLoggedInEvent event) { Player player =
     * event.getPlayer(); if(player instanceof ServerPlayer server) { ItemStack
     * chest = player.getItemBySlot(EquipmentSlot.CHEST); if(!chest.isEmpty() &&
     * (ItemUtils.testItems(chest.getItem(), DeferredRegisters.ITEM_JETPACK.get())
     * || ItemUtils.testItems(chest.getItem(),
     * DeferredRegisters.ITEM_COMBATCHESTPLATE.get()))) {
     * NetworkHandler.CHANNEL.sendTo(new
     * PacketJetpackEquipedSound(player.getUUID()),
     * server.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT); } } }
     */

}
