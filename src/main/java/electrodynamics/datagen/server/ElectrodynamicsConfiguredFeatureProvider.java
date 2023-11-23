package electrodynamics.datagen.server;

import java.util.Map;

import com.mojang.serialization.JsonOps;

import electrodynamics.api.References;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;

public class ElectrodynamicsConfiguredFeatureProvider extends JsonCodecProvider<ConfiguredFeature<?, ?>> {

	public ElectrodynamicsConfiguredFeatureProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper, String directory, Map<ResourceLocation, ConfiguredFeature<?, ?>> entries) {
		super(dataGenerator, existingFileHelper, References.ID, JsonOps.INSTANCE, PackType.SERVER_DATA, directory, ConfiguredFeature.DIRECT_CODEC, entries);
		// TODO Auto-generated constructor stub
	}

}
