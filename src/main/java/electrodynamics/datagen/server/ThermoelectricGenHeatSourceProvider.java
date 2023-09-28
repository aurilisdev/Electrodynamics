package electrodynamics.datagen.server;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonObject;

import electrodynamics.api.References;
import electrodynamics.common.reloadlistener.ThermoelectricGeneratorHeatRegister;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class ThermoelectricGenHeatSourceProvider implements DataProvider {

	public static final String LOC = "data/" + References.ID + "/" + ThermoelectricGeneratorHeatRegister.FOLDER + "/" + ThermoelectricGeneratorHeatRegister.FILE_NAME;

	private final PackOutput output;

	public ThermoelectricGenHeatSourceProvider(PackOutput output) {
		this.output = output;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		JsonObject json = new JsonObject();
		getFuels(json);

		Path parent = output.getOutputFolder().resolve(LOC + ".json");

		return CompletableFuture.allOf(DataProvider.saveStable(cache, json, parent));
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
