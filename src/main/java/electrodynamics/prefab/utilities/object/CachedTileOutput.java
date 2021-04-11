package electrodynamics.prefab.utilities.object;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CachedTileOutput {
    private World world;
    private BlockPos pos;
    private TileEntity cache;

    public CachedTileOutput(World world, BlockPos pos) {
	this.world = world;
	this.pos = pos;

    }

    public <T> T getSafe() {
	if (cache == null) {
	    cache = world.getTileEntity(pos);
	}
	if (cache != null && cache.isRemoved()) {
	    cache = null;
	}
	return (T) cache;
    }

    public boolean valid() {
	return cache != null;
    }

    public void update() {
	getSafe();
    }

    public BlockPos getPos() {
	return pos;
    }
}
