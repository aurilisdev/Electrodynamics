package electrodynamics.common.packet;

import java.util.HashMap;
import java.util.Optional;

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
import electrodynamics.common.packet.types.client.PacketUpdateTile;
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
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
	public static HashMap<String, String> playerInformation = new HashMap<>();
	private static final String PROTOCOL_VERSION = "1";
	private static int disc = 0;
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(References.ID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

	public static void init() {
		CHANNEL.registerMessage(disc++, PacketUpdateTile.class, PacketUpdateTile::encode, PacketUpdateTile::decode, PacketUpdateTile::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(disc++, PacketSendUpdatePropertiesClient.class, PacketSendUpdatePropertiesClient::encode, PacketSendUpdatePropertiesClient::decode, PacketSendUpdatePropertiesClient::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(disc++, PacketSendUpdatePropertiesServer.class, PacketSendUpdatePropertiesServer::encode, PacketSendUpdatePropertiesServer::decode, PacketSendUpdatePropertiesServer::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
		CHANNEL.registerMessage(disc++, PacketSpawnSmokeParticle.class, PacketSpawnSmokeParticle::encode, PacketSpawnSmokeParticle::decode, PacketSpawnSmokeParticle::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(disc++, PacketPlayerInformation.class, PacketPlayerInformation::encode, PacketPlayerInformation::decode, PacketPlayerInformation::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
		CHANNEL.registerMessage(disc++, PacketServerUpdateTile.class, PacketServerUpdateTile::encode, PacketServerUpdateTile::decode, PacketServerUpdateTile::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
		CHANNEL.registerMessage(disc++, PacketPowerSetting.class, PacketPowerSetting::encode, PacketPowerSetting::decode, PacketPowerSetting::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
		CHANNEL.registerMessage(disc++, PacketModeSwitchServer.class, PacketModeSwitchServer::encode, PacketModeSwitchServer::decode, PacketModeSwitchServer::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
		CHANNEL.registerMessage(disc++, PacketJetpackFlightServer.class, PacketJetpackFlightServer::encode, PacketJetpackFlightServer::decode, PacketJetpackFlightServer::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
		CHANNEL.registerMessage(disc++, PacketToggleOnServer.class, PacketToggleOnServer::encode, PacketToggleOnServer::decode, PacketToggleOnServer::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
		CHANNEL.registerMessage(disc++, PacketAddClientRenderInfo.class, PacketAddClientRenderInfo::encode, PacketAddClientRenderInfo::decode, PacketAddClientRenderInfo::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(disc++, PacketJetpackEquipedSound.class, PacketJetpackEquipedSound::encode, PacketJetpackEquipedSound::decode, PacketJetpackEquipedSound::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(disc++, PacketRenderJetpackParticles.class, PacketRenderJetpackParticles::encode, PacketRenderJetpackParticles::decode, PacketRenderJetpackParticles::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(disc++, PacketSetClientCombustionFuel.class, PacketSetClientCombustionFuel::encode, PacketSetClientCombustionFuel::decode, PacketSetClientCombustionFuel::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(disc++, PacketSetClientCoalGenFuels.class, PacketSetClientCoalGenFuels::encode, PacketSetClientCoalGenFuels::decode, PacketSetClientCoalGenFuels::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(disc++, PacketResetGuidebookPages.class, PacketResetGuidebookPages::encode, PacketResetGuidebookPages::decode, PacketResetGuidebookPages::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(disc++, PacketUpdateCarriedItemServer.class, PacketUpdateCarriedItemServer::encode, PacketUpdateCarriedItemServer::decode, PacketUpdateCarriedItemServer::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
		CHANNEL.registerMessage(disc++, PacketSetClientThermoGenSources.class, PacketSetClientThermoGenSources::encode, PacketSetClientThermoGenSources::decode, PacketSetClientThermoGenSources::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(disc++, PacketSwapBattery.class, PacketSwapBattery::encode, PacketSwapBattery::decode, PacketSwapBattery::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
	}

	public static String getPlayerInformation(String username) {
		return playerInformation.getOrDefault(username, "No Information");
	}
}