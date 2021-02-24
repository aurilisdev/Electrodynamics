package electrodynamics.packet;

import java.util.Optional;

import electrodynamics.api.References;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
	    new ResourceLocation(References.ID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals,
	    PROTOCOL_VERSION::equals);

    public static void init() {
	int disc = 0;
	CHANNEL.registerMessage(disc++, PacketUpdateTile.class, PacketUpdateTile::encode, PacketUpdateTile::decode,
		PacketUpdateTile::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
	CHANNEL.registerMessage(disc++, PacketSpawnSmokeParticle.class, PacketSpawnSmokeParticle::encode,
		PacketSpawnSmokeParticle::decode, PacketSpawnSmokeParticle::handle,
		Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }
}
