package electrodynamics.common.packet;

import java.util.HashMap;

import electrodynamics.Electrodynamics;
import electrodynamics.common.packet.types.client.PacketAddClientRenderInfo;
import electrodynamics.common.packet.types.client.PacketJetpackEquipedSound;
import electrodynamics.common.packet.types.client.PacketRenderJetpackParticles;
import electrodynamics.common.packet.types.client.PacketSetClientCoalGenFuels;
import electrodynamics.common.packet.types.client.PacketSetClientCombustionFuel;
import electrodynamics.common.packet.types.client.PacketSetClientGasCollectorCards;
import electrodynamics.common.packet.types.client.PacketSetClientThermoGenSources;
import electrodynamics.common.packet.types.server.PacketJetpackFlightServer;
import electrodynamics.common.packet.types.server.PacketModeSwitchServer;
import electrodynamics.common.packet.types.server.PacketPowerSetting;
import electrodynamics.common.packet.types.server.PacketSeismicScanner;
import electrodynamics.common.packet.types.server.PacketToggleOnServer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Electrodynamics.ID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkHandler {

    public static HashMap<String, String> playerInformation = new HashMap<>();
    private static final String PROTOCOL_VERSION = "1";

    @SubscribeEvent
    public static void registerPackets(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registry = event.registrar(Electrodynamics.ID).versioned(PROTOCOL_VERSION).optional();

        // CLIENT

        registry.playToClient(PacketAddClientRenderInfo.TYPE, PacketAddClientRenderInfo.CODEC, PacketAddClientRenderInfo::handle);
        registry.playToClient(PacketJetpackEquipedSound.TYPE, PacketJetpackEquipedSound.CODEC, PacketJetpackEquipedSound::handle);
        registry.playToClient(PacketRenderJetpackParticles.TYPE, PacketRenderJetpackParticles.CODEC, PacketRenderJetpackParticles::handle);
        registry.playToClient(PacketSetClientCoalGenFuels.TYPE, PacketSetClientCoalGenFuels.CODEC, PacketSetClientCoalGenFuels::handle);
        registry.playToClient(PacketSetClientCombustionFuel.TYPE, PacketSetClientCombustionFuel.CODEC, PacketSetClientCombustionFuel::handle);
        registry.playToClient(PacketSetClientGasCollectorCards.TYPE, PacketSetClientGasCollectorCards.CODEC, PacketSetClientGasCollectorCards::handle);
        registry.playToClient(PacketSetClientThermoGenSources.TYPE, PacketSetClientThermoGenSources.CODEC, PacketSetClientThermoGenSources::handle);

        // SERVER

        registry.playToServer(PacketJetpackFlightServer.TYPE, PacketJetpackFlightServer.CODEC, PacketJetpackFlightServer::handle);
        registry.playToServer(PacketModeSwitchServer.TYPE, PacketModeSwitchServer.CODEC, PacketModeSwitchServer::handle);
        registry.playToServer(PacketPowerSetting.TYPE, PacketPowerSetting.CODEC, PacketPowerSetting::handle);
        registry.playToServer(PacketToggleOnServer.TYPE, PacketToggleOnServer.CODEC, PacketToggleOnServer::handle);
        registry.playToServer(PacketSeismicScanner.TYPE, PacketSeismicScanner.CODEC, PacketSeismicScanner::handle);

    }

    public static String getPlayerInformation(String username) {
        return playerInformation.getOrDefault(username, "No Information");
    }

    public static ResourceLocation id(String name) {
        return Electrodynamics.rl(name);
    }


}
