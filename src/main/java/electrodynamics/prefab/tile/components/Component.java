package electrodynamics.prefab.tile.components;

import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public interface Component {
	ComponentType getType();

	default void holder(GenericTile holder) {
	}

	default void loadFromNBT(CompoundTag nbt) {
	}

	default void saveToNBT(CompoundTag nbt) {
	}

	default void remove() {
	}

	default boolean hasCapability(Capability<?> capability, Direction side, CapabilityInputType inputType) {
		return false;
	}

	default <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side, CapabilityInputType inputType) {
		return LazyOptional.empty();
	}

	default void onLoad() {

	}

}
