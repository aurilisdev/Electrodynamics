package electrodynamics.api.capability.ceramicplate;

public class CeramicPlateStorage implements ICapabilityCeramicPlateStorage {

    private int counted;

    @Override
    public void increasePlateCount(int count) {
	counted += count;
    }

    @Override
    public void decreasePlateCount(int count) {
	counted -= count;
    }

    @Override
    public void setPlateCount(int count) {
	counted = count;
    }

    @Override
    public int getPlateCount() {
	return counted;
    }
}
