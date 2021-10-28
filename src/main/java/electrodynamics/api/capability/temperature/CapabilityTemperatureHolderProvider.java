package electrodynamics.api.capability.temperature;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityTemperatureHolderProvider implements ICapabilitySerializable<CompoundTag> {

    private final CapabilityTemperatureHolderDefault temperatureHolder = new CapabilityTemperatureHolderDefault();
    private final LazyOptional<ICapabilityTemperature> lazyOptional = LazyOptional.of(() -> temperatureHolder);

    public void invalidate() {
	lazyOptional.invalidate();
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
	if (cap == CapabilityTemperature.TEMPERATURE) {
	    return lazyOptional.cast();
	}
	return LazyOptional.empty();

    }

    @Override
    public CompoundTag serializeNBT() {
	if (CapabilityTemperature.TEMPERATURE == null) {
	    return new CompoundTag();
	}
	return (CompoundTag) CapabilityTemperature.TEMPERATURE.writeNBT(temperatureHolder, null);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
	if (CapabilityTemperature.TEMPERATURE != null) {
	    CapabilityTemperature.TEMPERATURE.readNBT(temperatureHolder, null, nbt);
	}
    }
}
