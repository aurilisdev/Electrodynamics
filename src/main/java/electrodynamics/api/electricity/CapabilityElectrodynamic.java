package electrodynamics.api.electricity;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class CapabilityElectrodynamic {

    public static final double DEFAULT_VOLTAGE = 120.0;

    public static Capability<IElectrodynamic> ELECTRODYNAMIC = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static void register(RegisterCapabilitiesEvent event) {
	event.register(IElectrodynamic.class);
    }
}
