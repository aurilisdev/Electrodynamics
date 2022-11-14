package electrodynamics.prefab.tile;

import electrodynamics.prefab.properties.PropertyManager;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IPropertyHolderTile {
	public PropertyManager getPropertyManager();

	default BlockEntity getTile() {
		return (BlockEntity) this;
	}
}
