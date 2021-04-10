package electrodynamics.prefab.tile.components;

import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public interface Component {
    ComponentType getType();

    default void holder(GenericTile holder) {
    }

    default void loadFromNBT(BlockState state, CompoundNBT nbt) {
    }

    default void saveToNBT(CompoundNBT nbt) {
    }

    default void remove() {
    }

    default boolean hasCapability(Capability<?> capability, Direction side) {
	return false;
    }

    default <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
	return null;
    }
}
