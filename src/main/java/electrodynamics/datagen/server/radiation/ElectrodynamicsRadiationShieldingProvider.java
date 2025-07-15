package electrodynamics.datagen.server.radiation;

import com.google.gson.JsonObject;
import electrodynamics.Electrodynamics;
import net.minecraft.data.DataGenerator;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.radiation.BaseRadiationShieldingProvider;

public class ElectrodynamicsRadiationShieldingProvider extends BaseRadiationShieldingProvider {

    public ElectrodynamicsRadiationShieldingProvider(DataGenerator gen) {
        super(gen, Electrodynamics.ID);
    }

    @Override
    public void getRadiationShielding(JsonObject json) {

        addTag(VoltaicTags.Blocks.STORAGE_BLOCK_LEAD, 20000, 1, json);

    }
}
