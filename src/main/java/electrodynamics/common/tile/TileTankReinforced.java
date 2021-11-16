package electrodynamics.common.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.gear.tools.ItemCanister;
import electrodynamics.common.tile.generic.TileGenericTank;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class TileTankReinforced extends TileGenericTank {

    private static int capacity = 32000;
    private static List<Fluid> fluids = new ArrayList<>();
    private static String name = "reinforced";

    static {
		fluids.add(Fluids.WATER);
		fluids.add(Fluids.LAVA);
		ItemCanister.TAG_NAMES.forEach(h -> {
			fluids.addAll(FluidTags.getAllTags().getTag(h).getValues());
		});
    }

    public TileTankReinforced(BlockPos pos, BlockState state) {
	super(DeferredRegisters.TILE_TANKREINFORCED.get(), capacity, fluids, name, pos, state);
    }
}
