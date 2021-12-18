package electrodynamics.common.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.gear.tools.ItemCanister;
import electrodynamics.common.tile.generic.GenericTileTank;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class TileTankReinforced extends GenericTileTank {

	private static int capacity = 32000;
	private static Fluid[] fluids = new Fluid[0];
	private static String name = "reinforced";

	static {
		List<Fluid> list = new ArrayList<>();
		list.add(Fluids.WATER);
		list.add(Fluids.LAVA);
		ItemCanister.TAG_NAMES.forEach(h -> {
			list.addAll(FluidTags.getAllTags().getTag(h).getValues());
		});
		fluids = new Fluid[list.size()];
		for (int i = 0; i < list.size(); i++) {
			fluids[i] = list.get(i);
		}
	}

	public TileTankReinforced(BlockPos pos, BlockState state) {
		super(DeferredRegisters.TILE_TANKREINFORCED.get(), capacity, fluids, name, pos, state);
	}
}
