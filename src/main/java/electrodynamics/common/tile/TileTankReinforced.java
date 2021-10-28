package electrodynamics.common.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.tile.generic.TileGenericTank;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class TileTankReinforced extends TileGenericTank {

    private static int capacity = 32000;
    private static List<Fluid> fluids = new ArrayList<>();
    private static String name = "reinforced";

    static {
	fluids.add(Fluids.WATER);
	fluids.add(Fluids.LAVA);
	DeferredRegisters.FLUIDS.getEntries().forEach(h -> fluids.add(h.get()));
    }

    public TileTankReinforced() {
	super(DeferredRegisters.TILE_TANKREINFORCED.get(), capacity, fluids, name);
    }
}
