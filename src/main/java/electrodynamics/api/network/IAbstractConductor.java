package electrodynamics.api.network;

public interface IAbstractConductor {

    void removeFromNetwork();

    void setNetwork(AbstractNetwork<?, ?, ?, ?> aValueNetwork);

    Object getConductorType();

    double getMaxTransfer();
}
