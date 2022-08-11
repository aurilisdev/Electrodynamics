package physica.nuclear.client.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import physica.nuclear.PhysicaNuclearPhysics;
import physica.nuclear.client.gui.GuiCentrifuge;
import physica.nuclear.client.gui.GuiChemicalBoiler;
import physica.nuclear.client.gui.GuiChemicalExtractor;
import physica.nuclear.client.gui.GuiNeutronCaptureChamber;
import physica.nuclear.client.gui.GuiParticleAccelerator;

public class NEINuclearConfig implements IConfigureNEI {

	@Override
	public void loadConfig()
	{
		API.registerRecipeHandler(new ChemicalBoilerRecipeHandler());
		API.registerUsageHandler(new ChemicalBoilerRecipeHandler());
		API.registerRecipeHandler(new ChemicalExtractorRecipeHandler());
		API.registerUsageHandler(new ChemicalExtractorRecipeHandler());
		API.registerRecipeHandler(new CentrifugeRecipeHandler());
		API.registerUsageHandler(new CentrifugeRecipeHandler());
		API.registerRecipeHandler(new NeutronCaptureRecipeHandler());
		API.registerUsageHandler(new NeutronCaptureRecipeHandler());
		API.registerRecipeHandler(new ParticleAcceleratorRecipehelper());
		API.registerUsageHandler(new ParticleAcceleratorRecipehelper());

		API.setGuiOffset(GuiChemicalBoiler.class, 0, 13);
		API.setGuiOffset(GuiChemicalExtractor.class, 0, 13);
		API.setGuiOffset(GuiCentrifuge.class, 0, 13);
		API.setGuiOffset(GuiNeutronCaptureChamber.class, 0, 13);
		API.setGuiOffset(GuiParticleAccelerator.class, 0, 13);
	}

	@Override
	public String getName()
	{
		return PhysicaNuclearPhysics.metadata.name;
	}

	@Override
	public String getVersion()
	{
		return PhysicaNuclearPhysics.metadata.version;
	}
}
