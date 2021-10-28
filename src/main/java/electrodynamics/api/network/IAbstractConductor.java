package electrodynamics.api.network;

import electrodynamics.prefab.network.AbstractNetwork;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IAbstractConductor {

    void removeFromNetwork();

    AbstractNetwork<?, ?, ?, ?> getAbstractNetwork();

    void setNetwork(AbstractNetwork<?, ?, ?, ?> aValueNetwork);

    BlockEntity[] getAdjacentConnections();

    Object getConductorType();

    double getMaxTransfer();
}
