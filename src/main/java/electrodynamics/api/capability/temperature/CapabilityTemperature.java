package electrodynamics.api.capability.temperature;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
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
	public Tag writeNBT(Capability<ICapabilityTemperature> capability, ICapabilityTemperature instance, Direction side) {
	    CompoundTag nbt = new CompoundTag();
	    nbt.putInt(TEMPERATURE_KEY, instance.getTemperature());
	    return nbt;
	}

	@Override
	public void readNBT(Capability<ICapabilityTemperature> capability, ICapabilityTemperature instance, Direction side, Tag nbt) {
	    int temperature = ((CompoundTag) nbt).getInt(TEMPERATURE_KEY);
	    instance.setTemperature(temperature);
	}

    }

}
