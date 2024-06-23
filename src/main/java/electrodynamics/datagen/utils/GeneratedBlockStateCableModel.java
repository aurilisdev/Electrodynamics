package electrodynamics.datagen.utils;

import com.google.gson.JsonObject;

import electrodynamics.api.References;
import electrodynamics.client.modelbakers.bakerypes.CableModelLoader;
import electrodynamics.common.block.connect.util.EnumConnectType;
import net.minecraftforge.client.model.generators.IGeneratedBlockState;
import net.minecraftforge.client.model.generators.ModelFile;

public class GeneratedBlockStateCableModel implements IGeneratedBlockState {

	private final ModelFile none;
	private final ModelFile wire;
	private final ModelFile inventory;

	public GeneratedBlockStateCableModel(ModelFile none, ModelFile wire, ModelFile inventory) {

		this.none = none;
		this.wire = wire;
		this.inventory = inventory;

	}

	@Override
	public JsonObject toJson() {
		JsonObject json = new JsonObject();

		json.addProperty("loader", References.ID + ":" + CableModelLoader.ID);

		JsonObject noneElement = new JsonObject();
		noneElement.addProperty("model", none.getLocation().toString());
		json.add(EnumConnectType.NONE.toString(), noneElement);

		JsonObject wireElement = new JsonObject();
		wireElement.addProperty("model", wire.getLocation().toString());
		json.add(EnumConnectType.WIRE.toString(), wireElement);

		JsonObject inventoryElement = new JsonObject();
		inventoryElement.addProperty("model", inventory.getLocation().toString());
		json.add(EnumConnectType.INVENTORY.toString(), inventoryElement);

		return json;
	}

}
