package electrodynamics.datagen.server.radiation;

import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import net.minecraft.data.DataGenerator;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.radiation.BaseRadioactiveItemsProvider;

public class ElectrodynamicsRadioactiveItemsProvider extends BaseRadioactiveItemsProvider {

    public ElectrodynamicsRadioactiveItemsProvider(DataGenerator gen) {
        super(gen, Electrodynamics.ID);
    }

    @Override
    public void getRadioactiveItems(JsonObject json) {
        addTag(VoltaicTags.Items.ORE_THORIUM, 2, 1, json);
        addTag(VoltaicTags.Items.ORE_URANIUM, 3, 1, json);

        addTag(VoltaicTags.Items.RAW_ORE_THORIUM, 4, 1, json);
        addTag(VoltaicTags.Items.RAW_ORE_URANIUM, 6, 1, json);

        addTag(VoltaicTags.Items.BLOCK_RAW_ORE_THORIUM, 20, 1, json);
        addTag(VoltaicTags.Items.BLOCK_RAW_ORE_URANIUM, 30, 1, json);
    }
}
