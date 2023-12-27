package electrodynamics.api.tile;

import net.minecraft.nbt.CompoundNBT;

public interface IPacketServerUpdateTile {
	void readCustomUpdate(CompoundNBT nbt);
}
