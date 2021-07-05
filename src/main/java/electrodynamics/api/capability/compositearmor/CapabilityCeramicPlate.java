package electrodynamics.api.capability.compositearmor;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityCeramicPlate {

	public static final String PLATES_KEY = "plates";
	
	@CapabilityInject(ICeramicPlateHolder.class)
	public static Capability<ICeramicPlateHolder> CERAMIC_PLATE_HOLDER_CAPABILITY = null;
	
	public static void register() {
		CapabilityManager.INSTANCE.register(ICeramicPlateHolder.class, new PlateStorage(), DefaultCeramicPlateHolder::new);
	}
	
	public static class PlateStorage implements Capability.IStorage<ICeramicPlateHolder>{

		@Override
		public INBT writeNBT(Capability<ICeramicPlateHolder> capability, ICeramicPlateHolder instance, Direction side) {
			CompoundNBT nbt = new CompoundNBT();
			nbt.putInt(PLATES_KEY, instance.getPlateCount());
			return nbt;
		}

		@Override
		public void readNBT(Capability<ICeramicPlateHolder> capability, ICeramicPlateHolder instance, Direction side, INBT nbt) {
			int plateCount = ((CompoundNBT)nbt).getInt(PLATES_KEY);
			instance.setPlateCount(plateCount);
		}
		
	}
	
}
