package electrodynamics.common.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.gear.tools.ItemCanister;
import electrodynamics.common.tile.generic.TileGenericTank;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag.INamedTag;

public class TileTankReinforced extends TileGenericTank {

    private static int capacity = 32000;
    private static List<Fluid> fluids = new ArrayList<>();
    private static String name = "reinforced";

    static {
		fluids.add(Fluids.WATER);
		fluids.add(Fluids.LAVA);
		ItemCanister.TAG_NAMES.forEach(h -> {
			for(INamedTag<Fluid> tag : FluidTags.getAllTags()) {
				if(tag.getName().equals(h)) {
					fluids.addAll(tag.getAllElements());
					break;
				}
			}
		});
    }

    public TileTankReinforced() {
	super(DeferredRegisters.TILE_TANKREINFORCED.get(), capacity, fluids, name);
    }
}
