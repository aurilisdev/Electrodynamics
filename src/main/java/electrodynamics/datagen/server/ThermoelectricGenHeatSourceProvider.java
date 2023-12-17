package electrodynamics.datagen.server;

import java.io.IOException;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import electrodynamics.api.References;
import electrodynamics.common.reloadlistener.ThermoelectricGeneratorHeatRegister;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag.INamedTag;

public class ThermoelectricGenHeatSourceProvider implements IDataProvider {

	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
	public static final String LOC = "data/" + References.ID + "/" + ThermoelectricGeneratorHeatRegister.FOLDER + "/" + ThermoelectricGeneratorHeatRegister.FILE_NAME;

	private final DataGenerator dataGenerator;

	public ThermoelectricGenHeatSourceProvider(DataGenerator gen) {
		dataGenerator = gen;
	}

	@Override
	public void run(DirectoryCache cache) throws IOException {
		JsonObject json = new JsonObject();
		getFuels(json);

		Path parent = dataGenerator.getOutputFolder().resolve(LOC + ".json");
		try {

			IDataProvider.save(GSON, cache, json, parent);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getFuels(JsonObject json) {

		addTag(FluidTags.LAVA, 1.0, json);

	}

	private void addTag(INamedTag<Fluid> fluid, double multiplier, JsonObject json) {
		json.addProperty("#" + fluid.getName().toString(), multiplier);
	}

	@Override
	public String getName() {
		return "Electrodynamics Thermoelectric Generator Heat Source Provider";
	}

}
