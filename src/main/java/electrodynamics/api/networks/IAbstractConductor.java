package electrodynamics.api.networks;

public interface IAbstractConductor {

	void removeFromNetwork();

	void setNetwork(AbstractNetwork<?, ?, ?, ?> aValueNetwork);

	Object getConductorType();

	double getMaxTransfer();
}
