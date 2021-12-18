package electrodynamics.api.tile;

import net.minecraft.nbt.CompoundTag;

public interface IPacketServerUpdateTile {
	void readCustomUpdate(CompoundTag nbt);
}
