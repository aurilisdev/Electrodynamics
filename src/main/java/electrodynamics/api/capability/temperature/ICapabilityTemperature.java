package electrodynamics.api.capability.temperature;

public interface ICapabilityTemperature {
	
	void increaseTemperature(int amount);
	
	void decreaseTemperature(int amount);
	
	void setTemperature(int amount);
	
	int getTemperature();

	
}
