package electrodynamics.api.network.cable;

import electrodynamics.prefab.network.AbstractNetwork;
import net.minecraft.tileentity.TileEntity;

public interface IAbstractCable {

	void removeFromNetwork();

	AbstractNetwork<?, ?, ?, ?> getAbstractNetwork();

	void setNetwork(AbstractNetwork<?, ?, ?, ?> aValueNetwork);

	TileEntity[] getAdjacentConnections();

	Object getConductorType();

	double getMaxTransfer();
}
