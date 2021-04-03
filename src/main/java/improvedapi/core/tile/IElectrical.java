package improvedapi.core.tile;

import improvedapi.core.electricity.ElectricityPack;

public interface IElectrical extends IConnector {
    public double receiveElectricity(ElectricityPack electricityPack, boolean doReceive);

    public double getRequest();

    public double getVoltage();

}
