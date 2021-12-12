package electrodynamics.api.capability.ceramicplate;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class CapabilityCeramicPlateStorage {

    public static final String PLATES_KEY = "plates";

    public static Capability<ICapabilityCeramicPlateStorage> CERAMIC_PLATE_HOLDER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static void register(RegisterCapabilitiesEvent event) {
	event.register(ICapabilityCeramicPlateStorage.class);
    }

}
