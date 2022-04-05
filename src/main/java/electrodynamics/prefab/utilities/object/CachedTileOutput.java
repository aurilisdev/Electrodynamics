package electrodynamics.prefab.utilities.object;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * @author aurilisdev Used so you don't check tileentities all the time. Don't know if this is useful after MC V 1.7.10 but it is definitely useful in that version.
 */
public class CachedTileOutput {
	private Level world;
	private BlockPos pos;
	private BlockEntity cache;

	public CachedTileOutput(Level world, BlockPos pos) {
		this.world = world;
		this.pos = pos;

	}

	public <T> T getSafe() {
		if (cache == null) {
			cache = world.getBlockEntity(pos);
		}
		if (cache != null && cache.isRemoved()) {
			cache = null;
		}
		return (T) cache;
	}

	public boolean valid() {
		return cache != null;
	}

	public void update(BlockPos pos) {
		if (!this.pos.equals(pos)) {
			cache = null;
			this.pos = pos;
		}
		getSafe();
	}

	public BlockPos getPos() {
		return pos;
	}
}
