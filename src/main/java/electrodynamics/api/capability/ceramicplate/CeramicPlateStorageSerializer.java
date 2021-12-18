package electrodynamics.api.capability.ceramicplate;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CeramicPlateStorageSerializer implements ICapabilitySerializable<CompoundTag> {

	public static final String PLATES_KEY = "plates";
	private final CeramicPlateStorage plateHolder = new CeramicPlateStorage();
	private final LazyOptional<ICapabilityCeramicPlateStorage> lazyOptional = LazyOptional.of(() -> plateHolder);

	public void invalidate() {
		lazyOptional.invalidate();
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == CapabilityCeramicPlateStorage.CERAMIC_PLATE_HOLDER_CAPABILITY) {
			return lazyOptional.cast();
		}
		return LazyOptional.empty();

	}

	@Override
	public CompoundTag serializeNBT() {
		if (CapabilityCeramicPlateStorage.CERAMIC_PLATE_HOLDER_CAPABILITY != null) {
			CompoundTag nbt = new CompoundTag();
			nbt.putInt(PLATES_KEY, plateHolder.getPlateCount());
			return nbt;
		}
		return new CompoundTag();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		if (CapabilityCeramicPlateStorage.CERAMIC_PLATE_HOLDER_CAPABILITY != null) {
			plateHolder.setPlateCount(nbt.getInt(PLATES_KEY));
		}
	}

}
