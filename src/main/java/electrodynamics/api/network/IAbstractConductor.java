package electrodynamics.api.network;

import electrodynamics.prefab.network.AbstractNetwork;
import net.minecraft.tileentity.TileEntity;

public interface IAbstractConductor {

    void removeFromNetwork();

    AbstractNetwork<?, ?, ?, ?> getAbstractNetwork();

    void setNetwork(AbstractNetwork<?, ?, ?, ?> aValueNetwork);

    TileEntity[] getAdjacentConnections();

    Object getConductorType();

    double getMaxTransfer();
}
