package electrodynamics.api.capability.ceramicplate;

public interface ICapabilityCeramicPlateHolder {

    void increasePlateCount(int count);

    void decreasePlateCount(int count);

    void setPlateCount(int count);

    int getPlateCount();
}
