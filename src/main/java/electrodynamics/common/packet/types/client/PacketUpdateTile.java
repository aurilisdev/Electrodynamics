package electrodynamics.common.packet.types.client;

import java.util.function.Supplier;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PacketUpdateTile {

	private final CompoundNBT updateTag;
	private final BlockPos pos;
	private boolean isGUI;

	public PacketUpdateTile(ComponentPacketHandler component, BlockPos pos, boolean isGUI, CompoundNBT base) {
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

	private PacketUpdateTile(BlockPos pos, CompoundNBT updateTag, boolean isGUI) {
		this.pos = pos;
		this.updateTag = updateTag;
		this.isGUI = isGUI;
	}

	public static void handle(PacketUpdateTile message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			ClientWorld world = Minecraft.getInstance().level;
			if (world != null) {
				TileEntity tile = world.getBlockEntity(message.pos);
				if (tile instanceof GenericTile) {
					GenericTile generic = (GenericTile) tile;
					if (generic.hasComponent(IComponentType.PacketHandler)) {
						ComponentPacketHandler handler = generic.getComponent(IComponentType.PacketHandler);
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

	public static void encode(PacketUpdateTile pkt, PacketBuffer buf) {
		buf.writeBlockPos(pkt.pos);
		buf.writeNbt(pkt.updateTag);
		buf.writeBoolean(pkt.isGUI);
	}

	public static PacketUpdateTile decode(PacketBuffer buf) {
		return new PacketUpdateTile(buf.readBlockPos(), buf.readNbt(), buf.readBoolean());
	}
}