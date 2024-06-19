package electrodynamics.prefab.tile.components;

import javax.annotation.Nullable;

import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

//renamed ever so slightly so it's not confused with the Vanilla class constantly when importing 
public interface IComponent {

	IComponentType getType();

	default void holder(GenericTile holder) {
	}

	@Nullable
	default GenericTile getHolder() {
		return null;
	}

	default void loadFromNBT(CompoundTag nbt) {
	}

	default void saveToNBT(CompoundTag nbt) {
	}

	default void remove() {
	}

	default void onLoad() {
		refresh();
	}

	default void refreshIfUpdate(BlockState oldState, BlockState newState) {

	}

	default void refresh() {

	}

}
