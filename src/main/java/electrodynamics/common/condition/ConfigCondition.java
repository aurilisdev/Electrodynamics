package electrodynamics.common.condition;

import com.google.gson.JsonObject;

import electrodynamics.api.References;
import electrodynamics.common.settings.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class ConfigCondition implements ICondition {

	// make this work for multiple config values
	private static final ResourceLocation NAME = new ResourceLocation(References.ID, "config");

	public ConfigCondition() {
	}

	@Override
	public ResourceLocation getID() {
		return NAME;
	}

	@Override
	public boolean test(IContext context) {
		return Constants.DISPENSE_GUIDEBOOK;
	}

	public static class Serializer implements IConditionSerializer<ConfigCondition> {

		public static final Serializer INSTANCE = new Serializer();

		@Override
		public void write(JsonObject json, ConfigCondition value) {
			// for data gen
		}
 
		@Override
		public ConfigCondition read(JsonObject json) {
			// specify config fields
			return new ConfigCondition();
		}

		@Override
		public ResourceLocation getID() {
			return ConfigCondition.NAME;
		}
	}
}
