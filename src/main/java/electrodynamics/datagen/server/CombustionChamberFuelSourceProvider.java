package electrodynamics.datagen.server;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.prefab.utilities.object.CombustionFuelSource;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import voltaic.common.tags.VoltaicTags;

public class CombustionChamberFuelSourceProvider implements DataProvider {

	public static final String LOC = "data/" + Electrodynamics.ID + "/" + CombustionFuelRegister.FOLDER + "/";

	private final DataGenerator dataGenerator;

	private final Map<String, JsonObject> jsons = new HashMap<>();

	public CombustionChamberFuelSourceProvider(DataGenerator gen) {
		dataGenerator = gen;
	}

	@Override
	public void run(CachedOutput cache) throws IOException {
		addFuels();

		Path parent = dataGenerator.getOutputFolder().resolve(LOC);
		try {
			for (Entry<String, JsonObject> json : jsons.entrySet()) {
				DataProvider.saveStable(cache, json.getValue(), parent.resolve(json.getKey() + ".json"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addFuels() {
		jsons.put("ethanol", CombustionFuelSource.toJson(VoltaicTags.Fluids.ETHANOL, 1, 1));
		jsons.put("hydrogen", CombustionFuelSource.toJson(VoltaicTags.Fluids.HYDROGEN, 1000, 1));
	}

	@Override
	public String getName() {
		return "Combustion Chamber Fuel Sources";
	}

}
