package electrodynamics.api.capability.temperature;

public class CapabilityTemperatureHolderDefault implements ICapabilityTemperature {

    private int temperature;

    @Override
    public void setTemperature(int temp) {
	temperature = temp;
    }

    @Override
    public int getTemperature() {
	return temperature;
    }

    @Override
    public void increaseTemperature(int amount) {
	temperature += amount;
    }

    @Override
    public void decreaseTemperature(int amount) {
	temperature -= amount;
    }

}
