package electrodynamics.datagen.server.radiation;

import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import net.minecraft.data.PackOutput;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.radiation.BaseRadioactiveItemsProvider;

public class ElectrodynamicsRadioactiveItemsProvider extends BaseRadioactiveItemsProvider {

    public ElectrodynamicsRadioactiveItemsProvider(PackOutput output) {
        super(output, Electrodynamics.ID);
    }

    @Override
    public void getRadioactiveItems(JsonObject json) {

        addTag(VoltaicTags.Items.ORE_THORIUM, 6, 1, json);
        addTag(VoltaicTags.Items.ORE_URANIUM, 4, 1, json);

        addTag(VoltaicTags.Items.RAW_ORE_THORIUM, 30, 1, json);
        addTag(VoltaicTags.Items.RAW_ORE_URANIUM, 20, 1, json);

        addTag(VoltaicTags.Items.BLOCK_RAW_ORE_THORIUM, 15, 1, json);
        addTag(VoltaicTags.Items.BLOCK_RAW_ORE_URANIUM, 10, 1, json);

    }
}
