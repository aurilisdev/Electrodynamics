package electrodynamics.api.gas;

import electrodynamics.registers.ElectrodynamicsGases;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

/**
 * An implementation of a FluidStack-like object for gases 
 * 
 * @author skip999
 *
 */
public class GasStack {
	
	public static final GasStack EMPTY = new GasStack(Gas.EMPTY);
	
	public static final int ABSOLUTE_ZERO = 1; //zero technically, but that makes volumes a pain in the ass
	public static final double VACUUM = 0.001; // zero technically, but that makes volumes a pain in the ass
	
	private Gas gas = Gas.EMPTY;
	private double amount = 0; //mB
	private double temperature = 257; //Kelvin room temperature (20 C)
	private double pressure = 1; //ATM
	
	private boolean isEmpty = false;
	
	public GasStack(Gas gas) {
		this.gas = gas;
		if(gas == Gas.EMPTY) {
			isEmpty = true;
		}
	}
	
	public GasStack(Gas gas, double amount, double temperature, double pressure) {
		this(gas);
		this.amount = amount;
		this.temperature = temperature;
		this.pressure = pressure;
	}
	
	public Gas getGas() {
		return gas;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public double getTemperature() {
		return temperature;
	}
	
	public double getPressure() {
		return pressure;
	}
	
	public GasStack copy() {
		return new GasStack(gas, amount, temperature, pressure);
	}
	
	public void setAmount(double amount) {
		if(isEmpty()) {
			throw new UnsupportedOperationException("An empty Gas Stack cannot be modified");
		}
		this.amount = amount;
	}
	
	public void shrink(double amount) {
		if(isEmpty()) {
			throw new UnsupportedOperationException("An empty Gas Stack cannot be modified");
		}
		if(this.amount >= amount) {
			this.amount -= amount;
		}
		if(amount == 0) {
			gas = Gas.EMPTY;
			isEmpty = true;
		}
	}
	
	public void grow(double amount) {
		if(isEmpty()) {
			throw new UnsupportedOperationException("An empty Gas Stack cannot be modified");
		}
		this.amount += amount;
	}
	
	/**
	 * A negative temperature is analogous to cooling
	 * 
	 * Temperatures cannot drop below 1 degree Kelvin
	 * 
	 * @param deltaTemp The change in temperature
	 */
	public void heat(double deltaTemp) {
		amount += getVolumeChangeFromHeating(deltaTemp);
		temperature += deltaTemp;
	}
	
	//A negative pressure value is analogous to depressurizing 
	public void pressurize(double deltaPressure) {
		amount += getVolumeChangeFromPressurizing(deltaPressure);
		pressure += deltaPressure;
	}
	
	public double getVolumeChangeFromHeating(double deltaTemp) {
		if(isEmpty()) {
			throw new UnsupportedOperationException("An empty Gas Stack cannot be modified");
		}
		if(isAbsoluteZero() && deltaTemp < 0 || temperature + deltaTemp < ABSOLUTE_ZERO) {
			throw new UnsupportedOperationException("The temperature cannot drop below absolute zero");
		}
		
		double change = deltaTemp / temperature;
		
		return amount * change;
		
	}
	
	public double getVolumeChangeFromPressurizing(double deltaPressure) {
		if(isEmpty()) {
			throw new UnsupportedOperationException("An empty Gas Stack cannot be modified");
		}
		
		if(isVacuum() && deltaPressure < 0 || pressure + deltaPressure < VACUUM) {
			throw new UnsupportedOperationException("You cannot have a negative pressure");
		}
		
		double change = -deltaPressure / pressure;
		
		return amount * change;
	}
	
	public boolean isEmpty() {
		return this == EMPTY || isEmpty;
	}
	
	public boolean isSameGas(GasStack other) {
		return this.gas.equals(other.gas);
	}
	
	public boolean isSameAmount(GasStack other) {
		return amount == other.amount;
	}
	
	public boolean isSameTemperature(GasStack other) {
		return temperature == other.temperature;
	}
	
	public boolean isSamePressure(GasStack other) {
		return pressure == other.pressure;
	}
	
	public boolean isAbsoluteZero() {
		return temperature == ABSOLUTE_ZERO;
	}
	
	public boolean isVacuum() {
		return pressure == VACUUM;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof GasStack other) {
			return other.gas.equals(gas) && other.amount == amount && other.temperature == temperature && other.pressure == pressure;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return gas.toString() + ", amount: " + amount + " mB, temp: " + temperature + " K, pressure: " + pressure + " ATM";
	}
	
	//This is assumed to be a new tag
	public CompoundTag writeToNbt() {
		CompoundTag tag = new CompoundTag();
		tag.putString("name", ElectrodynamicsGases.GASES.getKey(this.gas).toString());
		tag.putDouble("amount", amount);
		tag.putDouble("temperature", temperature);
		tag.putDouble("pressure", pressure);
		return tag;
	}
	
	public static GasStack readFromNbt(CompoundTag tag) {
		Gas gas = ElectrodynamicsGases.GASES.getValue(new ResourceLocation(tag.getString("name")));
		double amount = tag.getDouble("amount");
		double temperature = tag.getDouble("temperature");
		double pressure = tag.getDouble("pressure");
		return new GasStack(gas, amount, temperature, pressure);
	}
	
	public void writeToBuffer(FriendlyByteBuf buffer) {
		buffer.writeRegistryId(ElectrodynamicsGases.GASES, gas);
		buffer.writeDouble(amount);
		buffer.writeDouble(temperature);
		buffer.writeDouble(pressure);
	}
	
	public static GasStack readFromBuffer(FriendlyByteBuf buffer) {
		Gas gas = buffer.readRegistryId();
		double amount = buffer.readDouble();
		double temperature = buffer.readDouble();
		double pressure = buffer.readDouble();
		return new GasStack(gas, amount, temperature, pressure);
	}
	
	/**
	 * Equalizes the pressure and temperature of two gas stacks to their respective median values
	 * and adjusts the volume of the resulting stack accordingly
	 * 
	 * It is assumed you have checked none of the gas stacks are unmodifiable
	 * 
	 * @param stack1 : The first stack
	 * @param stack2 : The second stack
	 * @return A gas stack that has the average temperature and pressure of the two stacks with the corresponding volume 
	 */
	public static GasStack equalizePresrsureAndTemperature(GasStack stack1, GasStack stack2) {
		
		double medianPressure = (stack1.pressure + stack2.pressure) / 2.0;
		double medianTemperature = (double) (stack1.temperature + stack2.temperature) / 2.0;
		
		double deltaP1 = medianPressure - stack1.pressure;
		double deltaP2 = medianPressure - stack2.pressure;
		
		double deltaT1 = medianTemperature - stack1.temperature;
		double deltaT2 = medianTemperature - stack2.temperature;
		
		stack1.pressurize(deltaP1);
		stack2.pressurize(deltaP2);
		
		stack1.heat(deltaT1);
		stack2.heat(deltaT2);
		
		GasStack stack = new GasStack(stack1.getGas());
		
		stack.setAmount(stack1.getAmount() + stack2.getAmount());
		stack.temperature = medianTemperature;
		stack.pressure = medianPressure;
		
		return stack;
		
	}
	
	/**
	 * Determines how much gas from stack 2 could be accepted into a container once stack1 and stack2 have equalized temperatures and pressures
	 * 
	 * It is assumed you have checked none of the gas stacks are unmodifiable
	 * 
	 * @param stack1 : The existing GasStack in the container
	 * @param stack2 : The gas attempting to be inserted into the container
	 * @param maximumAccept : The capacity of the container
	 * @return How much of stack2 could be accepted before the temperatures and pressures equalize
	 */
	public static double getMaximumAcceptance(GasStack stack1, GasStack stack2, double maximumAccept) {
		
		double medianPressure = (stack1.pressure + stack2.pressure) / 2.0;
		double medianTemperature = (double) (stack1.temperature + stack2.temperature) / 2.0;
		
		double deltaP1 = medianPressure - stack1.pressure;
		double deltaP2 = medianPressure - stack2.pressure;
		
		double deltaT1 = medianTemperature - stack1.temperature;
		double deltaT2 = medianTemperature - stack2.temperature;

		double remaining = maximumAccept - (stack1.amount + stack1.amount * (-deltaP1 / stack1.pressure) + stack1.amount * (deltaT1 / stack1.temperature));
		
		if(remaining <= 0) {
			return 0;
		}
		
		double deltaT2Amt = stack2.amount * (deltaT2 / stack2.temperature);
		double deltaP2Amt = stack2.amount * (-deltaP2 / stack2.pressure);
		
		double newT2Volume = stack2.amount + deltaT2Amt + deltaP2Amt;
		
		if(newT2Volume <= remaining) {
			return stack2.amount;
		}
		
		//avoids a specific case for div by zero
		if(deltaT2Amt + deltaP2Amt == -1) {
			deltaP2Amt += 0.001;
		}
		
		return remaining / (1.0 + deltaP2Amt + deltaT2Amt);
		
	}

}
