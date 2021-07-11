package electrodynamics.api.capability.compositearmor;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityCeramicPlateHolderProvider implements ICapabilitySerializable<CompoundNBT> {

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
    public CompoundNBT serializeNBT() {
	if (CapabilityCeramicPlate.CERAMIC_PLATE_HOLDER_CAPABILITY == null) {
	    return new CompoundNBT();
	}
	return (CompoundNBT) CapabilityCeramicPlate.CERAMIC_PLATE_HOLDER_CAPABILITY.writeNBT(plateHolder, null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
	if (CapabilityCeramicPlate.CERAMIC_PLATE_HOLDER_CAPABILITY != null) {
	    CapabilityCeramicPlate.CERAMIC_PLATE_HOLDER_CAPABILITY.readNBT(plateHolder, null, nbt);
	}
    }

}
