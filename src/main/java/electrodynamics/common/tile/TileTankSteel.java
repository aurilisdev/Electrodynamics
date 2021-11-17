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
    private static List<Fluid> fluids = new ArrayList<>();
    private static String name = "steel";

    static {
	fluids.add(Fluids.WATER);
	fluids.add(Fluids.LAVA);
	fluids.addAll(ElectrodynamicsTags.Fluids.ETHANOL.getValues());
    }

    public TileTankSteel(BlockPos pos, BlockState state) {
	super(DeferredRegisters.TILE_TANKSTEEL.get(), capacity, fluids, name, pos, state);
    }

}
