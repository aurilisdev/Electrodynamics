package electrodynamics.api.capability.temperature;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityTemperature {

    public static final String TEMPERATURE_KEY = "plates";

    @CapabilityInject(ICapabilityTemperature.class)
    public static Capability<ICapabilityTemperature> TEMPERATURE = null;

    public static void register() {
	CapabilityManager.INSTANCE.register(ICapabilityTemperature.class, new Temperature(), CapabilityTemperatureHolderDefault::new);
    }

    public static class Temperature implements Capability.IStorage<ICapabilityTemperature> {

	@Override
	public INBT writeNBT(Capability<ICapabilityTemperature> capability, ICapabilityTemperature instance, Direction side) {
	    CompoundNBT nbt = new CompoundNBT();
	    nbt.putInt(TEMPERATURE_KEY, instance.getTemperature());
	    return nbt;
	}

	@Override
	public void readNBT(Capability<ICapabilityTemperature> capability, ICapabilityTemperature instance, Direction side, INBT nbt) {
	    int temperature = ((CompoundNBT) nbt).getInt(TEMPERATURE_KEY);
	    instance.setTemperature(temperature);
	}

    }

}
