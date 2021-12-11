package electrodynamics.common.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.tile.generic.GenericTileTank;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public class TileTankHSLA extends GenericTileTank {

    private static int capacity = 128000;
    private static Fluid[] fluids = new Fluid[0];
    private static String name = "hsla";

    static {
	List<Fluid> list = new ArrayList<>(ForgeRegistries.FLUIDS.getValues());
	fluids = new Fluid[list.size()];
	for (int i = 0; i < list.size(); i++) {
	    fluids[i] = list.get(i);
	}
    }

    public TileTankHSLA(BlockPos pos, BlockState state) {
	super(DeferredRegisters.TILE_TANKHSLA.get(), capacity, fluids, name, pos, state);
    }

}
