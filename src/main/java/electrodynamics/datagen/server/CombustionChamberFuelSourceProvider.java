package electrodynamics.datagen.server;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonObject;

import electrodynamics.api.References;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.prefab.utilities.object.CombustionFuelSource;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

public class CombustionChamberFuelSourceProvider implements DataProvider {

	public static final String LOC = "data/" + References.ID + "/" + CombustionFuelRegister.FOLDER + "/";

	private final PackOutput output;

	private final Map<String, JsonObject> jsons = new HashMap<>();

	public CombustionChamberFuelSourceProvider(PackOutput output) {
		this.output = output;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		addFuels();

		Path parent = output.getOutputFolder().resolve(LOC);

		List<CompletableFuture<?>> completed = new ArrayList<>();

		for (Entry<String, JsonObject> json : jsons.entrySet()) {
			completed.add(DataProvider.saveStable(cache, json.getValue(), parent.resolve(json.getKey() + ".json")));
		}

		return CompletableFuture.allOf(completed.toArray(size -> new CompletableFuture[size]));
	}

	private void addFuels() {
		jsons.put("ethanol", CombustionFuelSource.toJson(ElectrodynamicsTags.Fluids.ETHANOL, 1, 1));
		jsons.put("hydrogen", CombustionFuelSource.toJson(ElectrodynamicsTags.Fluids.HYDROGEN, 1000, 1));
	}

	@Override
	public String getName() {
		return "Combustion Chamber Fuel Sources";
	}

}
