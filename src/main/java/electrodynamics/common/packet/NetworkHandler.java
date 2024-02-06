package electrodynamics.common.packet;

import java.util.HashMap;

import electrodynamics.api.References;
import electrodynamics.common.packet.types.client.PacketAddClientRenderInfo;
import electrodynamics.common.packet.types.client.PacketJetpackEquipedSound;
import electrodynamics.common.packet.types.client.PacketRenderJetpackParticles;
import electrodynamics.common.packet.types.client.PacketResetGuidebookPages;
import electrodynamics.common.packet.types.client.PacketSendUpdatePropertiesClient;
import electrodynamics.common.packet.types.client.PacketSetClientCoalGenFuels;
import electrodynamics.common.packet.types.client.PacketSetClientCombustionFuel;
import electrodynamics.common.packet.types.client.PacketSetClientThermoGenSources;
import electrodynamics.common.packet.types.client.PacketSpawnSmokeParticle;
import electrodynamics.common.packet.types.server.PacketJetpackFlightServer;
import electrodynamics.common.packet.types.server.PacketModeSwitchServer;
import electrodynamics.common.packet.types.server.PacketPlayerInformation;
import electrodynamics.common.packet.types.server.PacketPowerSetting;
import electrodynamics.common.packet.types.server.PacketSendUpdatePropertiesServer;
import electrodynamics.common.packet.types.server.PacketServerUpdateTile;
import electrodynamics.common.packet.types.server.PacketSwapBattery;
import electrodynamics.common.packet.types.server.PacketToggleOnServer;
import electrodynamics.common.packet.types.server.PacketUpdateCarriedItemServer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

@EventBusSubscriber(modid = References.ID, bus = Bus.MOD)
public class NetworkHandler {

    public static HashMap<String, String> playerInformation = new HashMap<>();
    private static final String PROTOCOL_VERSION = "1";

    //CLIENT IDS
    
    public static final ResourceLocation PACKET_ADDCLIENTRENDERINFO_PACKETID = id("packetaddclientrenderinfo");
    public static final ResourceLocation PACKET_JETPACKEQUIPEDSOUND_PACKETID = id("packetjetpackequipedsound");
    public static final ResourceLocation PACKET_RENDERJETPACKPARTICLES_PACKETID = id("packetrenderjetpackparticles");
    public static final ResourceLocation PACKET_RESETGUIDEBOOKPAGES_PACKETID = id("packetresetguidebookpages");
    public static final ResourceLocation PACKET_SENDUPDATEPROPERTIESCLIENT_PACKETID = id("packetsendupdatepropertiesclient");
    public static final ResourceLocation PACKET_SETCLIENTCOALGENFUELS_PACKETID = id("packetsetclientcoalgenfuels");
    public static final ResourceLocation PACKET_SETCLIENTCOMBUSTIONFUEL_PACKETID = id("packetsetclientcombusionfuel");
    public static final ResourceLocation PACKET_SETCLIENTTHERMOGENSOURCES_PACKETID = id("packetsetclientthermogensources");
    public static final ResourceLocation PACKET_SPAWNSMOKEPARTICLE_PACKETID = id("packetspawnsmokeparticle");
    
    //SERVER IDS
    
    public static final ResourceLocation PACKET_JETPACKFLIGHTSERVER_PACKETID = id("packetjetpackflightserver");
    public static final ResourceLocation PACKET_MODESWITCHSERVER_PACKETID = id("packetmodeswitchserver");
    public static final ResourceLocation PACKET_PLAYERINFORMATION_PACKETID = id("packetplayerinformation");
    public static final ResourceLocation PACKET_POWERSETTING_PACKETID = id("packetpowersetting");
    public static final ResourceLocation PACKET_SENDUPDATEPROPERTIESSERVER_PACKETID = id("packetsendupdatepropertiesserver");
    public static final ResourceLocation PACKET_SERVERUPDATETILE_PACKETID = id("packetserverupdatetile");
    public static final ResourceLocation PACKET_SWAPBATTER_PACKETID = id("packetswapbattery");
    public static final ResourceLocation PACKET_TOGGLEONSERVER_PACKETID = id("packettoggleonserver");
    public static final ResourceLocation PACKET_UPDATECARRIEDITEMSERVER_PACKETID = id("packetupdatecarrieditemserver");

    @SubscribeEvent
    public static void registerPackets(final RegisterPayloadHandlerEvent event) {
        final IPayloadRegistrar registry = event.registrar(References.ID).versioned(PROTOCOL_VERSION).optional();

        // CLIENT
        
        registry.play(PACKET_ADDCLIENTRENDERINFO_PACKETID, buf -> PacketAddClientRenderInfo.read(buf), builder -> builder.client((packet, context) -> PacketAddClientRenderInfo.handle(packet, context)));
        registry.play(PACKET_JETPACKEQUIPEDSOUND_PACKETID, buf -> PacketJetpackEquipedSound.read(buf), builder -> builder.client((packet, context) -> PacketJetpackEquipedSound.handle(packet, context)));
        registry.play(PACKET_RENDERJETPACKPARTICLES_PACKETID, buf -> PacketRenderJetpackParticles.read(buf), builder -> builder.client((packet, context) -> PacketRenderJetpackParticles.handle(packet, context)));
        registry.play(PACKET_RESETGUIDEBOOKPAGES_PACKETID, buf -> PacketResetGuidebookPages.read(buf), builder -> builder.client((packet, context) -> PacketResetGuidebookPages.handle(packet, context)));
        registry.play(PACKET_SENDUPDATEPROPERTIESCLIENT_PACKETID, buf -> PacketSendUpdatePropertiesClient.read(buf), builder -> builder.client((packet, context) -> PacketSendUpdatePropertiesClient.handle(packet, context)));
        registry.play(PACKET_SETCLIENTCOALGENFUELS_PACKETID, buf -> PacketSetClientCoalGenFuels.read(buf), builder -> builder.client((packet, context) -> PacketSetClientCoalGenFuels.handle(packet, context)));
        registry.play(PACKET_SETCLIENTCOMBUSTIONFUEL_PACKETID, buf -> PacketSetClientCombustionFuel.read(buf), builder -> builder.client((packet, context) -> PacketSetClientCombustionFuel.handle(packet, context)));
        registry.play(PACKET_SETCLIENTTHERMOGENSOURCES_PACKETID, buf -> PacketSetClientThermoGenSources.read(buf), builder -> builder.client((packet, context) -> PacketSetClientThermoGenSources.handle(packet, context)));
        registry.play(PACKET_SPAWNSMOKEPARTICLE_PACKETID, buf -> PacketSpawnSmokeParticle.read(buf), builder -> builder.client((packet, context) -> PacketSpawnSmokeParticle.handle(packet, context)));

        // SERVER

        registry.play(PACKET_JETPACKFLIGHTSERVER_PACKETID, buf -> PacketJetpackFlightServer.read(buf), builder -> builder.server((packet, context) -> PacketJetpackFlightServer.handle(packet, context)));
        registry.play(PACKET_MODESWITCHSERVER_PACKETID, buf -> PacketModeSwitchServer.read(buf), builder -> builder.server((packet, context) -> PacketModeSwitchServer.handle(packet, context)));
        registry.play(PACKET_PLAYERINFORMATION_PACKETID, buf -> PacketPlayerInformation.read(buf), builder -> builder.server((packet, context) -> PacketPlayerInformation.handle(packet, context)));
        registry.play(PACKET_POWERSETTING_PACKETID, buf -> PacketPowerSetting.read(buf), builder -> builder.server((packet, context) -> PacketPowerSetting.handle(packet, context)));
        registry.play(PACKET_SENDUPDATEPROPERTIESSERVER_PACKETID, buf -> PacketSendUpdatePropertiesServer.read(buf), builder -> builder.server((packet, context) -> PacketSendUpdatePropertiesServer.handle(packet, context)));
        registry.play(PACKET_SERVERUPDATETILE_PACKETID, buf -> PacketServerUpdateTile.read(buf), builder -> builder.server((packet, context) -> PacketServerUpdateTile.handle(packet, context)));
        registry.play(PACKET_SWAPBATTER_PACKETID, buf -> PacketSwapBattery.read(buf), builder -> builder.server((packet, context) -> PacketSwapBattery.handle(packet, context)));
        registry.play(PACKET_TOGGLEONSERVER_PACKETID, buf -> PacketToggleOnServer.read(buf), builder -> builder.server((packet, server) -> PacketToggleOnServer.handle(packet, server)));
        registry.play(PACKET_UPDATECARRIEDITEMSERVER_PACKETID, buf -> PacketUpdateCarriedItemServer.read(buf), builder -> builder.server((packet, context) -> PacketUpdateCarriedItemServer.handle(packet, context)));
    }

    public static void init() {
        // CLIENT
        //CHANNEL.registerMessage(disc++, PacketSendUpdatePropertiesClient.class, PacketSendUpdatePropertiesClient::encode, PacketSendUpdatePropertiesClient::decode, PacketSendUpdatePropertiesClient::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        //CHANNEL.registerMessage(disc++, PacketSpawnSmokeParticle.class, PacketSpawnSmokeParticle::encode, PacketSpawnSmokeParticle::decode, PacketSpawnSmokeParticle::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        //CHANNEL.registerMessage(disc++, PacketAddClientRenderInfo.class, PacketAddClientRenderInfo::encode, PacketAddClientRenderInfo::decode, PacketAddClientRenderInfo::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        //CHANNEL.registerMessage(disc++, PacketJetpackEquipedSound.class, PacketJetpackEquipedSound::encode, PacketJetpackEquipedSound::decode, PacketJetpackEquipedSound::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        //CHANNEL.registerMessage(disc++, PacketRenderJetpackParticles.class, PacketRenderJetpackParticles::encode, PacketRenderJetpackParticles::decode, PacketRenderJetpackParticles::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        //CHANNEL.registerMessage(disc++, PacketSetClientCombustionFuel.class, PacketSetClientCombustionFuel::encode, PacketSetClientCombustionFuel::decode, PacketSetClientCombustionFuel::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        //CHANNEL.registerMessage(disc++, PacketSetClientCoalGenFuels.class, PacketSetClientCoalGenFuels::encode, PacketSetClientCoalGenFuels::decode, PacketSetClientCoalGenFuels::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        //CHANNEL.registerMessage(disc++, PacketResetGuidebookPages.class, PacketResetGuidebookPages::encode, PacketResetGuidebookPages::decode, PacketResetGuidebookPages::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        //CHANNEL.registerMessage(disc++, PacketSetClientThermoGenSources.class, PacketSetClientThermoGenSources::encode, PacketSetClientThermoGenSources::decode, PacketSetClientThermoGenSources::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        // SERVER
        //CHANNEL.registerMessage(disc++, PacketSendUpdatePropertiesServer.class, PacketSendUpdatePropertiesServer::encode, PacketSendUpdatePropertiesServer::decode, PacketSendUpdatePropertiesServer::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        //CHANNEL.registerMessage(disc++, PacketPlayerInformation.class, PacketPlayerInformation::encode, PacketPlayerInformation::decode, PacketPlayerInformation::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        //CHANNEL.registerMessage(disc++, PacketServerUpdateTile.class, PacketServerUpdateTile::encode, PacketServerUpdateTile::decode, PacketServerUpdateTile::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        //CHANNEL.registerMessage(disc++, PacketPowerSetting.class, PacketPowerSetting::encode, PacketPowerSetting::decode, PacketPowerSetting::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        //CHANNEL.registerMessage(disc++, PacketModeSwitchServer.class, PacketModeSwitchServer::encode, PacketModeSwitchServer::decode, PacketModeSwitchServer::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        // CHANNEL.registerMessage(disc++, PacketJetpackFlightServer.class, PacketJetpackFlightServer::encode, PacketJetpackFlightServer::decode, PacketJetpackFlightServer::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        //CHANNEL.registerMessage(disc++, PacketToggleOnServer.class, PacketToggleOnServer::encode, PacketToggleOnServer::decode, PacketToggleOnServer::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        //CHANNEL.registerMessage(disc++, PacketUpdateCarriedItemServer.class, PacketUpdateCarriedItemServer::encode, PacketUpdateCarriedItemServer::decode, PacketUpdateCarriedItemServer::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        //CHANNEL.registerMessage(disc++, PacketSwapBattery.class, PacketSwapBattery::encode, PacketSwapBattery::decode, PacketSwapBattery::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }

    public static String getPlayerInformation(String username) {
        return playerInformation.getOrDefault(username, "No Information");
    }

    private static ResourceLocation id(String name) {
        return new ResourceLocation(References.ID, name);
    }

}
