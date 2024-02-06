package electrodynamics.datagen.server;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import electrodynamics.api.References;
import electrodynamics.common.reloadlistener.CoalGeneratorFuelRegister;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.Tags;

public class CoalGeneratorFuelSourceProvider implements DataProvider {

	public static final String LOC = "data/" + References.ID + "/" + CoalGeneratorFuelRegister.FOLDER + "/" + CoalGeneratorFuelRegister.FILE_NAME;

	private final PackOutput output;

	public CoalGeneratorFuelSourceProvider(PackOutput output) {
		this.output = output;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		JsonObject json = new JsonObject();

		getFuels(json);

		Path parent = output.getOutputFolder().resolve(LOC + ".json");

		return CompletableFuture.allOf(DataProvider.saveStable(cache, json, parent));
	}

	private void getFuels(JsonObject object) {
		JsonArray json = new JsonArray();

		addTag(ItemTags.COALS, json);
		addTag(Tags.Items.STORAGE_BLOCKS_COAL, json);

		object.add(CoalGeneratorFuelRegister.KEY, json);
	}

	private void addTag(TagKey<Item> item, JsonArray json) {
		json.add("#" + item.location().toString());
	}

	@Override
	public String getName() {
		return "Electrodynamics Coal Generator Fuel Provider";
	}

}
