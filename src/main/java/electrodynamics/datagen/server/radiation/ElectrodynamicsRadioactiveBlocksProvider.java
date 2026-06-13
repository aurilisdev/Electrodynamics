package electrodynamics.datagen.server.radiation;

import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import net.minecraft.data.PackOutput;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.radiation.BaseRadioactiveBlocksProvider;

public class ElectrodynamicsRadioactiveBlocksProvider extends BaseRadioactiveBlocksProvider {

    public ElectrodynamicsRadioactiveBlocksProvider(PackOutput output) {
        super(output, Electrodynamics.ID);
    }

    @Override
    public void getRadioactiveBlocks(JsonObject json) {

        addTag(VoltaicTags.Blocks.ORE_THORIUM, 5, 1, json);
        addTag(VoltaicTags.Blocks.ORE_URANIUM, 10, 1, json);

        addTag(VoltaicTags.Blocks.BLOCK_RAW_ORE_THORIUM, 20, 1, json);
        addTag(VoltaicTags.Blocks.BLOCK_RAW_ORE_URANIUM, 30, 1, json);

    }
}
