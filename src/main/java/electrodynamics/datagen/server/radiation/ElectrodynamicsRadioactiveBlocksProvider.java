package electrodynamics.datagen.server.radiation;

import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import net.minecraft.data.DataGenerator;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.radiation.BaseRadioactiveBlocksProvider;

public class ElectrodynamicsRadioactiveBlocksProvider extends BaseRadioactiveBlocksProvider {

    public ElectrodynamicsRadioactiveBlocksProvider(DataGenerator gen) {
        super(gen, Electrodynamics.ID);
    }

    @Override
    public void getRadioactiveBlocks(JsonObject json) {

        addTag(VoltaicTags.Blocks.ORE_THORIUM, 500, 1, json);
        addTag(VoltaicTags.Blocks.ORE_URANIUM, 100, 1, json);

    }
}
