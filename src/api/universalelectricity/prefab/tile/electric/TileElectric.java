package universalelectricity.prefab.tile.electric;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import universalelectricity.api.ElectricityAPI;
import universalelectricity.prefab.capabilities.ElectricityStorage;
import universalelectricity.prefab.tile.base.TileRotatable;

public class TileElectric extends TileRotatable {

	protected ElectricityStorage energyStorage;

	public TileElectric() {
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		if (energyStorage != null) {
			CapabilityEnergy.ENERGY.readNBT(energyStorage, null, tag.getTag(ElectricityAPI.NBT_STORED_ENERGY));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		if (energyStorage != null) {
			tag.setTag(ElectricityAPI.NBT_STORED_ENERGY, CapabilityEnergy.ENERGY.writeNBT(energyStorage, null));
		}

		return tag;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
	}

	@Override
	@Nonnull
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return (T) energyStorage;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void handlePacketData(ByteBuf dataStream) {
		super.handlePacketData(dataStream);
		if (world.isRemote) {
			energyStorage.setEnergyStored(dataStream.readInt());
		}
	}

	@Override
	public List<Object> getPacketData(List<Object> objects) {
		super.getPacketData(objects);
		objects.add(energyStorage.getEnergyStored());
		return objects;
	}

	public IEnergyStorage getEnergyStorage() {
		return energyStorage;
	}
}