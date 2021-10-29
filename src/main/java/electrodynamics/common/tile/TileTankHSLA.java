package electrodynamics.common.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.tile.generic.TileGenericTank;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public class TileTankHSLA extends TileGenericTank {

    private static int capacity = 128000;
    private static List<Fluid> fluids = new ArrayList<>();
    private static String name = "hsla";

    static {
	ForgeRegistries.FLUIDS.getValues().forEach(h -> fluids.add(h));
    }

    public TileTankHSLA(BlockPos pos, BlockState state) {
	super(DeferredRegisters.TILE_TANKHSLA.get(), capacity, fluids, name, pos, state);
    }

}
