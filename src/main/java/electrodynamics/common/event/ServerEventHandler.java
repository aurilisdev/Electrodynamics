package electrodynamics.common.event;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.References;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.server.PacketPlayerInformation;
import electrodynamics.common.reloadlistener.CoalGeneratorFuelRegister;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.common.reloadlistener.ThermoelectricGeneratorHeatRegister;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class ServerEventHandler {

	@SubscribeEvent
	public static void tick(PlayerTickEvent event) {
		if (event.side == LogicalSide.CLIENT && event.player.level.getLevelData().getDayTime() % 50 == 10) {
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
	public static void serverStartedHandler(FMLServerStartedEvent event) {
		CoalGeneratorFuelRegister.INSTANCE.generateTagValues();
		ThermoelectricGeneratorHeatRegister.INSTANCE.generateTagValues();
	}

}
