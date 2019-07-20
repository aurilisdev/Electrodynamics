package physica;

import java.lang.reflect.Method;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.Loader;
import mcp.mobius.waila.api.IWailaDataProvider;
import physica.api.core.PhysicaAPI;
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
				PhysicaAPI.logger.log(Level.INFO, "Attempting to integrate Physica with WAILA.");
				try
				{
					Class<?> moduleRegistrar = Class.forName("mcp.mobius.waila.api.impl.ModuleRegistrar");
					Object instance = moduleRegistrar.getMethod("instance").invoke(null);
					moduleRegistrar.getMethod("addConfigRemote", String.class, String.class).invoke(instance, "Physica", "physica.electricityhandler");
					Method registerBodyProvider = moduleRegistrar.getMethod("registerBodyProvider", IWailaDataProvider.class, Class.class);
					Method registerNBTProvider = moduleRegistrar.getMethod("registerNBTProvider", IWailaDataProvider.class, Class.class);
					registerBodyProvider.invoke(instance, new HUDHandlerIElectricityHandler(), IElectricityProvider.class);
					registerNBTProvider.invoke(instance, new HUDHandlerIElectricityHandler(), IElectricityProvider.class);
					registerBodyProvider.invoke(instance, new HUDHandlerIElectricityHandler(), IElectricityReceiver.class);
					registerNBTProvider.invoke(instance, new HUDHandlerIElectricityHandler(), IElectricityReceiver.class);
					PhysicaAPI.logger.log(Level.INFO, "Integrated Physica with WAILA.");
				} catch (Exception e)
				{
					PhysicaAPI.logger.log(Level.ERROR, "Failed to integrate Physica with WAILA.");
				}
			}
		}
	}
}
