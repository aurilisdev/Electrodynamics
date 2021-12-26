package electrodynamics.api.capability;

import electrodynamics.api.capability.boolstorage.IBooleanStorage;
import electrodynamics.api.capability.dirstorage.IDirectionalStorage;
import electrodynamics.api.capability.electrodynamic.ICapabilityElectrodynamic;
import electrodynamics.api.capability.intstorage.IIntStorage;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class ElectrodynamicsCapabilities {

	public static final double DEFAULT_VOLTAGE = 120.0;
	
	public static final String INT_KEY = "integer";
	public static final String BOOLEAN_KEY = "boolean";
	public static final String DIR_KEY = "directions";
	
	public static Capability<IBooleanStorage> BOOLEAN_STORAGE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
	public static Capability<IIntStorage> INTEGER_STORAGE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
	public static Capability<ICapabilityElectrodynamic> ELECTRODYNAMIC = CapabilityManager.get(new CapabilityToken<>() {});
	public static Capability<IDirectionalStorage> DIR_STORAGE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
	
	public static void register(RegisterCapabilitiesEvent event) {
		event.register(IBooleanStorage.class);
		event.register(IIntStorage.class);
		event.register(ICapabilityElectrodynamic.class);
		event.register(IDirectionalStorage.class);
	}
}
