package electrodynamics.api.capability.ceramicplate;

public interface ICapabilityCeramicPlateStorage {

	void increasePlateCount(int count);

	void decreasePlateCount(int count);

	void setPlateCount(int count);

	int getPlateCount();
}
