package universalelectricity.prefab.tile.electric;

import java.util.List;

import javax.annotation.Nonnull;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TileElectricMachine extends TileElectric implements ITickable {
	private static String NBT_OPERATING_TICKS = "operatingTicks";

	protected int energyUsed = 0;
	protected int operatingTicks = 0;
	protected int ticksloaded = 0;

	public TileElectricMachine() {
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		operatingTicks = tag.getInteger(NBT_OPERATING_TICKS);
	}

	@Override
	@Nonnull
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger(NBT_OPERATING_TICKS, operatingTicks);
		return tag;
	}

	@Override
	public final void update() {
		if (ticksloaded == 0) {
			firstUpdate(!getWorld().isRemote);
		} else if (getWorld().isRemote) {
			clientUpdate();
		} else {
			serverUpdate();
		}
		sharedUpdate();
	}

	public void firstUpdate(boolean isServer) {
	}

	public void clientUpdate() {
	}

	public void serverUpdate() {
	}

	public void sharedUpdate() {
	}

	@Override
	public void handlePacketData(ByteBuf dataStream) {
		super.handlePacketData(dataStream);
		if (world.isRemote) {
			energyUsed = dataStream.readInt();
			operatingTicks = dataStream.readInt();
		}
	}

	@Override
	public List<Object> getPacketData(List<Object> objects) {
		super.getPacketData(objects);
		objects.add(energyUsed);
		objects.add(operatingTicks);
		return objects;
	}

	public int getEnergyUsed() {
		return energyUsed;
	}

	public int getOperatingTicks() {
		return operatingTicks;
	}

	protected void reset() {
		operatingTicks = 0;
		energyUsed = 0;
	}
}