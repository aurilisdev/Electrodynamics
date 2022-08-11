package physica.core.client.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import physica.Physica;
import physica.core.client.gui.GuiBlastFurnace;
import physica.core.client.gui.GuiCircuitPress;

public class NEICoreConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		API.registerRecipeHandler(new BlastFurnaceRecipeHelper());
		API.registerUsageHandler(new BlastFurnaceRecipeHelper());

		API.registerRecipeHandler(new CircuitPressRecipeHelper());
		API.registerUsageHandler(new CircuitPressRecipeHelper());

		API.setGuiOffset(GuiBlastFurnace.class, 5, 13);
		API.setGuiOffset(GuiCircuitPress.class, 5, 13);
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
