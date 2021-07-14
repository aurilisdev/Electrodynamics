package electrodynamics.api.capability.compositearmor;

public class CapabilityCeramicPlateHolderDefault implements ICapabilityCeramicPlateHolder{

	private int CERAMIC_PLATES_HELD;

	@Override
	public void increasePlateCount(int count) {
		this.CERAMIC_PLATES_HELD += count;
	}

	@Override
	public void decreasePlateCount(int count) {
		this.CERAMIC_PLATES_HELD -= count;
	}
	
	@Override
	public void setPlateCount(int count) {
		this.CERAMIC_PLATES_HELD = count;
	}

	@Override
	public int getPlateCount() {
		return this.CERAMIC_PLATES_HELD;
	}



}
