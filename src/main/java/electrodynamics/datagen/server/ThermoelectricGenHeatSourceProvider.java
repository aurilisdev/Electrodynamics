package electrodynamics.datagen.server;

import java.io.IOException;
import java.nio.file.Path;

import com.google.gson.JsonObject;

import electrodynamics.api.References;
import electrodynamics.common.reloadlistener.ThermoelectricGeneratorHeatRegister;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class ThermoelectricGenHeatSourceProvider implements DataProvider {

	public static final String LOC = "data/" + References.ID + "/" + ThermoelectricGeneratorHeatRegister.FOLDER + "/" + ThermoelectricGeneratorHeatRegister.FILE_NAME;

	private final DataGenerator dataGenerator;

	public ThermoelectricGenHeatSourceProvider(DataGenerator gen) {
		dataGenerator = gen;
	}

	@Override
	public void run(CachedOutput cache) throws IOException {
		JsonObject json = new JsonObject();
		getFuels(json);

		Path parent = dataGenerator.getOutputFolder().resolve(LOC + ".json");
		try {

			DataProvider.saveStable(cache, json, parent);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getFuels(JsonObject json) {

		addTag(FluidTags.LAVA, 1.0, json);

	}

	private void addTag(TagKey<Fluid> fluid, double multiplier, JsonObject json) {
		json.addProperty("#" + fluid.location().toString(), multiplier);
	}

	@Override
	public String getName() {
		return "Electrodynamics Thermoelectric Generator Heat Source Provider";
	}

}
