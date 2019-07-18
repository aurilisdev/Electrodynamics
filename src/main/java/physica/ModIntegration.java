package physica;

import cpw.mods.fml.common.Loader;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import physica.api.core.electricity.IElectricityProvider;
import physica.api.core.electricity.IElectricityReceiver;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.core.common.waila.HUDHandlerIElectricityHandler;

public class ModIntegration implements IContent {
	@Override
	public void register(LoadPhase phase)
	{
		if (phase == LoadPhase.OnStartup)
		{
			if (Loader.isModLoaded("Waila"))
			{
				ModuleRegistrar.instance().addConfigRemote("Physica", "physica.electricityhandler");
				ModuleRegistrar.instance().registerBodyProvider(new HUDHandlerIElectricityHandler(), IElectricityProvider.class);
				ModuleRegistrar.instance().registerNBTProvider(new HUDHandlerIElectricityHandler(), IElectricityProvider.class);
				ModuleRegistrar.instance().registerBodyProvider(new HUDHandlerIElectricityHandler(), IElectricityReceiver.class);
				ModuleRegistrar.instance().registerNBTProvider(new HUDHandlerIElectricityHandler(), IElectricityReceiver.class);
			}
		}
	}
}
