package electrodynamics.api.capability.ceramicplate;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityCeramicPlate {

    public static final String PLATES_KEY = "plates";

    @CapabilityInject(ICapabilityCeramicPlateHolder.class)
    public static Capability<ICapabilityCeramicPlateHolder> CERAMIC_PLATE_HOLDER_CAPABILITY = null;

    public static void register() {
	CapabilityManager.INSTANCE.register(ICapabilityCeramicPlateHolder.class, new PlateStorage(), CapabilityCeramicPlateHolderDefault::new);
    }

    public static class PlateStorage implements Capability.IStorage<ICapabilityCeramicPlateHolder> {

	@Override
	public Tag writeNBT(Capability<ICapabilityCeramicPlateHolder> capability, ICapabilityCeramicPlateHolder instance, Direction side) {
	    CompoundTag nbt = new CompoundTag();
	    nbt.putInt(PLATES_KEY, instance.getPlateCount());
	    return nbt;
	}

	@Override
	public void readNBT(Capability<ICapabilityCeramicPlateHolder> capability, ICapabilityCeramicPlateHolder instance, Direction side, Tag nbt) {
	    int plateCount = ((CompoundTag) nbt).getInt(PLATES_KEY);
	    instance.setPlateCount(plateCount);
	}

    }

}
