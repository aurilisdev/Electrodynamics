package electrodynamics.api.network;

public interface IAbstractConductor {

    void removeFromNetwork();
    
    AbstractNetwork<?, ?, ?, ?> getAbstractNetwork();

    void setNetwork(AbstractNetwork<?, ?, ?, ?> aValueNetwork);

    Object getConductorType();

    double getMaxTransfer();
}
