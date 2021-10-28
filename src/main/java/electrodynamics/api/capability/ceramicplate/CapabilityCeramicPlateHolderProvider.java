package electrodynamics.api.capability.ceramicplate;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityCeramicPlateHolderProvider implements ICapabilitySerializable<CompoundTag> {

    private final CapabilityCeramicPlateHolderDefault plateHolder = new CapabilityCeramicPlateHolderDefault();
    private final LazyOptional<ICapabilityCeramicPlateHolder> lazyOptional = LazyOptional.of(() -> plateHolder);

    public void invalidate() {
	lazyOptional.invalidate();
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
	if (cap == CapabilityCeramicPlate.CERAMIC_PLATE_HOLDER_CAPABILITY) {
	    return lazyOptional.cast();
	}
	return LazyOptional.empty();

    }

    @Override
    public CompoundTag serializeNBT() {
	if (CapabilityCeramicPlate.CERAMIC_PLATE_HOLDER_CAPABILITY == null) {
	    return new CompoundTag();
	}
	return (CompoundTag) CapabilityCeramicPlate.CERAMIC_PLATE_HOLDER_CAPABILITY.writeNBT(plateHolder, null);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
	if (CapabilityCeramicPlate.CERAMIC_PLATE_HOLDER_CAPABILITY != null) {
	    CapabilityCeramicPlate.CERAMIC_PLATE_HOLDER_CAPABILITY.readNBT(plateHolder, null, nbt);
	}
    }

}
