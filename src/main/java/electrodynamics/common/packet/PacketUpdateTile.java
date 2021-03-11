package electrodynamics.common.packet;

import java.util.function.Supplier;

import electrodynamics.api.tile.IUpdateableTile;
import electrodynamics.common.tile.generic.GenericTile;
import electrodynamics.common.tile.generic.component.ComponentType;
import electrodynamics.common.tile.generic.component.type.ComponentPacketHandler;
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

    public PacketUpdateTile(IUpdateableTile tile, boolean isGUI) {
	this(tile.getTile().getPos(), isGUI ? tile.writeGUIPacket() : tile.writeCustomPacket(), isGUI);
	this.isGUI = isGUI;
    }

    public PacketUpdateTile(ComponentPacketHandler component, BlockPos pos, boolean isGUI) {
	this(pos, isGUI ? component.getGuiPacketSupplier().get() : component.getCustomPacketSupplier().get(), isGUI);
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
	    ClientWorld world = Minecraft.getInstance().world;
	    if (world != null) {
		TileEntity tile = world.getTileEntity(message.pos);
		if (tile instanceof IUpdateableTile) {
		    IUpdateableTile updateable = (IUpdateableTile) tile;
		    if (message.isGUI) {
			updateable.readGUIPacket(message.updateTag);
		    } else {
			updateable.readCustomPacket(message.updateTag);
		    }
		} else if (tile instanceof GenericTile) {
		    GenericTile generic = (GenericTile) tile;
		    if (generic.hasComponent(ComponentType.PacketHandler)) {
			if (message.isGUI) {
			}
		    }
		}
	    }
	});
	ctx.setPacketHandled(true);
    }

    public static void encode(PacketUpdateTile pkt, PacketBuffer buf) {
	buf.writeBlockPos(pkt.pos);
	buf.writeCompoundTag(pkt.updateTag);
	buf.writeBoolean(pkt.isGUI);
    }

    public static PacketUpdateTile decode(PacketBuffer buf) {
	return new PacketUpdateTile(buf.readBlockPos(), buf.readCompoundTag(), buf.readBoolean());
    }
}