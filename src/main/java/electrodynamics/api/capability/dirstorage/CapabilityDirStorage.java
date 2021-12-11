package electrodynamics.api.capability.dirstorage;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class CapabilityDirStorage {

    public static final String DIR_KEY = "directions";
    public static final String BOOL_KEY = "boolean";

    public static Capability<ICapabilityDirStorage> DIR_STORAGE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static void register(RegisterCapabilitiesEvent event) {
	event.register(ICapabilityDirStorage.class);
    }
}
