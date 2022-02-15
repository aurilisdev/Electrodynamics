package electrodynamics.api.capability;

import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic;
import electrodynamics.api.capability.types.locationstorage.ILocationStorage;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class ElectrodynamicsCapabilities {

	public static final double DEFAULT_VOLTAGE = 120.0;
	public static final String LOCATION_KEY = "location";

	public static Capability<ICapabilityElectrodynamic> ELECTRODYNAMIC = CapabilityManager.get(new CapabilityToken<>() {
	});
	public static Capability<ILocationStorage> LOCATION_STORAGE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
	});

	public static void register(RegisterCapabilitiesEvent event) {
		event.register(ICapabilityElectrodynamic.class);
	}
}
