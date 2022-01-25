package electrodynamics.prefab.block;

import net.minecraft.core.BlockPos;

public class HashDistanceBlockPos extends BlockPos {
	public int hash = 0;

	public HashDistanceBlockPos(double xIn, double yIn, double zIn, int hash) {
		super(xIn, yIn, zIn);
		this.hash = hash;
	}

	@Override
	public int hashCode() {
		return hash;
	}
}
