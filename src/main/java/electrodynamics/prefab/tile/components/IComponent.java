package electrodynamics.prefab.tile.components;

import javax.annotation.Nullable;

import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public interface IComponent {
	IComponentType getType();

	default void holder(GenericTile holder) {
	}

	@Nullable
	default GenericTile getHolder() {
		return null;
	}

	default void loadFromNBT(CompoundNBT nbt) {
	}

	default void saveToNBT(CompoundNBT nbt) {
	}

	default void remove() {
	}

	default <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
		return LazyOptional.empty();
	}

	default void onLoad() {
		refresh();
	}

	default void refreshIfUpdate(BlockState oldState, BlockState newState) {

	}

	default void refresh() {

	}
}
