package electrodynamics.common.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.common.tile.generic.TileGenericTank;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class TileTankSteel extends TileGenericTank {

    private static int capacity = 8000;
    private static Fluid[] fluids = new Fluid[0];
    private static String name = "steel";

    static {
    	List<Fluid> list = new ArrayList<>();
		list.add(Fluids.WATER);
		list.add(Fluids.LAVA);
		list.addAll(ElectrodynamicsTags.Fluids.ETHANOL.getValues());
		fluids = new Fluid[list.size()];
		for(int i = 0; i < list.size(); i++) {
			fluids[i] = list.get(i);
		}
    }

    public TileTankSteel(BlockPos pos, BlockState state) {
	super(DeferredRegisters.TILE_TANKSTEEL.get(), capacity, fluids, name, pos, state);
    }

}
