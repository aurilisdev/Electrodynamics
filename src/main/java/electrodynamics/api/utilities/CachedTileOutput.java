package electrodynamics.api.utilities;

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

	@SuppressWarnings("unchecked")
	public <T> T get() {
		if (cache == null) {
			cache = world.getTileEntity(pos);
		}
		if (cache != null && cache.isRemoved()) {
			cache = null;
		}
		return (T) cache;
	}

	public BlockPos getPos() {
		return pos;
	}
}
