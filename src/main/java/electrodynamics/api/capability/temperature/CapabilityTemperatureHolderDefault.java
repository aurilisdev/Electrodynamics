package electrodynamics.api.capability.temperature;

public class CapabilityTemperatureHolderDefault implements ICapabilityTemperature{

	private int TEMPERATURE;

	@Override
	public void setTemperature(int temp) {
		this.TEMPERATURE = temp;
	}

	@Override
	public int getTemperature() {
		return this.TEMPERATURE;
	}

	@Override
	public void increaseTemperature(int amount) {
		this.TEMPERATURE += amount;
	}

	@Override
	public void decreaseTemperature(int amount) {
		this.TEMPERATURE -= amount;
	}
	
	
}	
