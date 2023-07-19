package electrodynamics.api.capability;

import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic;
import electrodynamics.api.capability.types.gas.IGasHandler;
import electrodynamics.api.capability.types.gas.IGasHandlerItem;
import electrodynamics.api.capability.types.locationstorage.ILocationStorage;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class ElectrodynamicsCapabilities {

	public static final double DEFAULT_VOLTAGE = 120.0;
	public static final String LOCATION_KEY = "location";

	public static final Capability<ICapabilityElectrodynamic> ELECTRODYNAMIC = CapabilityManager.get(new CapabilityToken<>() {
	});
	public static final Capability<ILocationStorage> LOCATION_STORAGE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
	});

	public static final Capability<IGasHandler> GAS_HANDLER = CapabilityManager.get(new CapabilityToken<>() {
	});

	public static final Capability<IGasHandlerItem> GAS_HANDLER_ITEM = CapabilityManager.get(new CapabilityToken<>() {
	});

	public static void register(RegisterCapabilitiesEvent event) {
		event.register(ICapabilityElectrodynamic.class);
		event.register(ILocationStorage.class);
		event.register(IGasHandler.class);
		event.register(IGasHandlerItem.class);
	}
}
