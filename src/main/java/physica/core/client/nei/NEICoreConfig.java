package physica.core.client.nei;

import codechicken.nei.api.IConfigureNEI;
import physica.Physica;

public class NEICoreConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
	}

	@Override
	public String getName() {
		return Physica.metadata.name;
	}

	@Override
	public String getVersion() {
		return Physica.metadata.version;
	}
}
