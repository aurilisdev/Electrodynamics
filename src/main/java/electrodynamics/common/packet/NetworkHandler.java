package electrodynamics.common.packet;

import java.util.HashMap;
import java.util.Optional;

import electrodynamics.Electrodynamics;
import electrodynamics.common.packet.types.client.PacketAddClientRenderInfo;
import electrodynamics.common.packet.types.client.PacketSetClientCoalGenFuels;
import electrodynamics.common.packet.types.client.PacketSetClientCombustionFuel;
import electrodynamics.common.packet.types.client.PacketSetClientThermoGenSources;
import electrodynamics.common.packet.types.server.PacketPowerSetting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {
	public static HashMap<String, String> playerInformation = new HashMap<>();
	private static final String PROTOCOL_VERSION = "1";
	private static int disc = 0;
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(Electrodynamics.ID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

	public static void init() {
		// CLIENT
		CHANNEL.registerMessage(disc++, PacketAddClientRenderInfo.class, PacketAddClientRenderInfo::encode, PacketAddClientRenderInfo::decode, PacketAddClientRenderInfo::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(disc++, PacketSetClientCombustionFuel.class, PacketSetClientCombustionFuel::encode, PacketSetClientCombustionFuel::decode, PacketSetClientCombustionFuel::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(disc++, PacketSetClientCoalGenFuels.class, PacketSetClientCoalGenFuels::encode, PacketSetClientCoalGenFuels::decode, PacketSetClientCoalGenFuels::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(disc++, PacketSetClientThermoGenSources.class, PacketSetClientThermoGenSources::encode, PacketSetClientThermoGenSources::decode, PacketSetClientThermoGenSources::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));

		// SERVER
		CHANNEL.registerMessage(disc++, PacketPowerSetting.class, PacketPowerSetting::encode, PacketPowerSetting::decode, PacketPowerSetting::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
		
	}
}
