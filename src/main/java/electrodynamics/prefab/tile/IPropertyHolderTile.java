package electrodynamics.prefab.tile;

import electrodynamics.prefab.properties.PropertyManager;
import net.minecraft.tileentity.TileEntity;

public interface IPropertyHolderTile {
	PropertyManager getPropertyManager();

	default TileEntity getTile() {
		return (TileEntity) this;
	}
}
