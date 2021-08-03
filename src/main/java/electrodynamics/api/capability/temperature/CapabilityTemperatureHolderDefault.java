package electrodynamics.api.capability.temperature;

public class CapabilityTemperatureHolderDefault implements ICapabilityTemperature {

    private int TEMPERATURE;

    @Override
    public void setTemperature(int temp) {
	TEMPERATURE = temp;
    }

    @Override
    public int getTemperature() {
	return TEMPERATURE;
    }

    @Override
    public void increaseTemperature(int amount) {
	TEMPERATURE += amount;
    }

    @Override
    public void decreaseTemperature(int amount) {
	TEMPERATURE -= amount;
    }

}
