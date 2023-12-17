package electrodynamics.datagen.server;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import electrodynamics.api.References;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.prefab.utilities.object.CombustionFuelSource;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;

public class CombustionChamberFuelSourceProvider implements IDataProvider {

	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
	public static final String LOC = "data/" + References.ID + "/" + CombustionFuelRegister.FOLDER + "/";

	private final DataGenerator dataGenerator;

	private final Map<String, JsonObject> jsons = new HashMap<>();

	public CombustionChamberFuelSourceProvider(DataGenerator gen) {
		dataGenerator = gen;
	}

	@Override
	public void run(DirectoryCache cache) throws IOException {
		addFuels();

		Path parent = dataGenerator.getOutputFolder().resolve(LOC);
		try {
			for (Entry<String, JsonObject> json : jsons.entrySet()) {
				IDataProvider.save(GSON, cache, json.getValue(), parent.resolve(json.getKey() + ".json"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addFuels() {
		jsons.put("ethanol", CombustionFuelSource.toJson(ElectrodynamicsTags.Fluids.ETHANOL, 1, 1));

	}

	@Override
	public String getName() {
		return "Combustion Chamber Fuel Sources";
	}

}
