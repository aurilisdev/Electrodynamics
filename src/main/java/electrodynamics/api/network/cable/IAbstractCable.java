package electrodynamics.api.network.cable;

import electrodynamics.prefab.network.AbstractNetwork;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IAbstractCable {

	void removeFromNetwork();

	AbstractNetwork<?, ?, ?, ?> getAbstractNetwork();

	void setNetwork(AbstractNetwork<?, ?, ?, ?> aValueNetwork);

	BlockEntity[] getAdjacentConnections();

	Object getConductorType();

	double getMaxTransfer();
}
