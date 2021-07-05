package electrodynamics.api.capability.compositearmor;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CeramicPlateHolderProvider implements ICapabilitySerializable<CompoundNBT>{

	private final DefaultCeramicPlateHolder plateHolder = new DefaultCeramicPlateHolder();
	private final LazyOptional<ICeramicPlateHolder> lazyOptional = LazyOptional.of(() -> plateHolder);
	
	
	public void invalidate() {
		lazyOptional.invalidate();
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return lazyOptional.cast();
	}

	@Override
	public CompoundNBT serializeNBT() {
		if(CapabilityCeramicPlate.CERAMIC_PLATE_HOLDER_CAPABILITY == null) {
			return new CompoundNBT();
		}else {
			return (CompoundNBT)CapabilityCeramicPlate.CERAMIC_PLATE_HOLDER_CAPABILITY.writeNBT(plateHolder, null);
		}
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		if(CapabilityCeramicPlate.CERAMIC_PLATE_HOLDER_CAPABILITY != null) {
			CapabilityCeramicPlate.CERAMIC_PLATE_HOLDER_CAPABILITY.readNBT(plateHolder, null, nbt);
		}
	}

}
