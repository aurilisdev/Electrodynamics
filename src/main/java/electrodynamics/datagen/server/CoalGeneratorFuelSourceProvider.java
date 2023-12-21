package electrodynamics.datagen.server;

import java.io.IOException;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import electrodynamics.api.References;
import electrodynamics.common.reloadlistener.CoalGeneratorFuelRegister;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;

public class CoalGeneratorFuelSourceProvider implements DataProvider {

	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
	public static final String LOC = "data/" + References.ID + "/" + CoalGeneratorFuelRegister.FOLDER + "/" + CoalGeneratorFuelRegister.FILE_NAME;

	private final DataGenerator dataGenerator;

	public CoalGeneratorFuelSourceProvider(DataGenerator gen) {
		dataGenerator = gen;
	}

	@Override
	public void run(HashCache cache) throws IOException {
		JsonObject json = new JsonObject();
		getFuels(json);

		Path parent = dataGenerator.getOutputFolder().resolve(LOC + ".json");
		try {

			DataProvider.save(GSON, cache, json, parent);

		} catch (IOException e) {
			e.printStackTrace();
		}
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
