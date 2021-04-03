package improvedapi.core.tile;

import improvedapi.core.electricity.IElectricityNetwork;

public interface INetworkProvider {
    public IElectricityNetwork getNetwork();

    public void setNetwork(IElectricityNetwork network);
}
