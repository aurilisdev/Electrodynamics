package electrodynamics.common.packet.types;

import java.util.function.Supplier;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.IUpdateableTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketUpdateTile {

	private final CompoundTag updateTag;
	private final BlockPos pos;
	private boolean isGUI;

	public PacketUpdateTile(IUpdateableTile tile, boolean isGUI) {
		this(tile.getTile().getBlockPos(), isGUI ? tile.writeGUIPacket() : tile.writeCustomPacket(), isGUI);
		this.isGUI = isGUI;
	}

	public PacketUpdateTile(ComponentPacketHandler component, BlockPos pos, boolean isGUI, CompoundTag base) {
		this(pos, base, isGUI);
		if (isGUI) {
			if (component.getGuiPacketSupplier() != null) {
				component.getGuiPacketSupplier().accept(base);
			}
		} else if (component.getCustomPacketSupplier() != null) {
			component.getCustomPacketSupplier().accept(base);
		}
		this.isGUI = isGUI;
	}

	private PacketUpdateTile(BlockPos pos, CompoundTag updateTag, boolean isGUI) {
		this.pos = pos;
		this.updateTag = updateTag;
		this.isGUI = isGUI;
	}

	public static void handle(PacketUpdateTile message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			ClientLevel world = Minecraft.getInstance().level;
			if (world != null) {
				BlockEntity tile = world.getBlockEntity(message.pos);
				if (tile instanceof IUpdateableTile updateable) {
					if (message.isGUI) {
						updateable.readGUIPacket(message.updateTag);
					} else {
						updateable.readCustomPacket(message.updateTag);
					}
				} else if (tile instanceof GenericTile generic) {
					if (generic.hasComponent(ComponentType.PacketHandler)) {
						ComponentPacketHandler handler = generic.getComponent(ComponentType.PacketHandler);
						if (message.isGUI) {
							if (handler.getGuiPacketConsumer() != null) {
								handler.getGuiPacketConsumer().accept(message.updateTag);
							}
						} else if (handler.getCustomPacketConsumer() != null) {
							handler.getCustomPacketConsumer().accept(message.updateTag);
						}
					}
				}
			}
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketUpdateTile pkt, FriendlyByteBuf buf) {
		buf.writeBlockPos(pkt.pos);
		buf.writeNbt(pkt.updateTag);
		buf.writeBoolean(pkt.isGUI);
	}

	public static PacketUpdateTile decode(FriendlyByteBuf buf) {
		return new PacketUpdateTile(buf.readBlockPos(), buf.readNbt(), buf.readBoolean());
	}
}