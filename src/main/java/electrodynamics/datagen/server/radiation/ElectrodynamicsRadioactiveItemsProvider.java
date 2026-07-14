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

        addTag(VoltaicTags.Items.ORE_THORIUM, 500, 1, json);
        addTag(VoltaicTags.Items.ORE_URANIUM, 100, 1, json);

    }
}
