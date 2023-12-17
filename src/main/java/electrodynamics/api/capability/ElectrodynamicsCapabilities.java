package electrodynamics.api.capability;

import electrodynamics.api.capability.types.electrodynamic.ElectrodynamicStorage;
import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic;
import electrodynamics.api.capability.types.locationstorage.CapabilityLocationStorage;
import electrodynamics.api.capability.types.locationstorage.ILocationStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ElectrodynamicsCapabilities {

	public static final double DEFAULT_VOLTAGE = 120.0;
	public static final String LOCATION_KEY = "location";

	@CapabilityInject(ICapabilityElectrodynamic.class)
	public static Capability<ICapabilityElectrodynamic> ELECTRODYNAMIC;
	@CapabilityInject(ILocationStorage.class)
	public static Capability<ILocationStorage> LOCATION_STORAGE_CAPABILITY;

	public static void register() {
		CapabilityManager.INSTANCE.register(ICapabilityElectrodynamic.class, new IStorage<ICapabilityElectrodynamic>() {

			@Override
			public INBT writeNBT(Capability<ICapabilityElectrodynamic> capability, ICapabilityElectrodynamic instance, Direction side) {
				CompoundNBT tag = new CompoundNBT();
				tag.putDouble("stored", instance.getJoulesStored());
				return tag;
			}

			@Override
			public void readNBT(Capability<ICapabilityElectrodynamic> capability, ICapabilityElectrodynamic instance, Direction side, INBT nbt) {
				CompoundNBT tag = (CompoundNBT) nbt;
				instance.setJoulesStored(tag.getDouble("stored"));
			}
			
		}, () -> new ElectrodynamicStorage(1000, 0));
		CapabilityManager.INSTANCE.register(ILocationStorage.class, new IStorage<ILocationStorage>() {

			@Override
			public INBT writeNBT(Capability<ILocationStorage> capability, ILocationStorage instance, Direction side) {
				CompoundNBT tag = new CompoundNBT();
				tag.putInt("size", instance.getLocations().size());
				return tag;
			}

			@Override
			public void readNBT(Capability<ILocationStorage> capability, ILocationStorage instance, Direction side, INBT nbt) {
				// TODO Auto-generated method stub
				
			}
			
		}, () -> new CapabilityLocationStorage(0));
	}
}
