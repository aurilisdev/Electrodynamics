package electrodynamics.api.capability.compositearmor;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
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
	public INBT writeNBT(Capability<ICapabilityCeramicPlateHolder> capability, ICapabilityCeramicPlateHolder instance, Direction side) {
	    CompoundNBT nbt = new CompoundNBT();
	    nbt.putInt(PLATES_KEY, instance.getPlateCount());
	    return nbt;
	}

	@Override
	public void readNBT(Capability<ICapabilityCeramicPlateHolder> capability, ICapabilityCeramicPlateHolder instance, Direction side, INBT nbt) {
	    int plateCount = ((CompoundNBT) nbt).getInt(PLATES_KEY);
	    instance.setPlateCount(plateCount);
	}

    }

}
