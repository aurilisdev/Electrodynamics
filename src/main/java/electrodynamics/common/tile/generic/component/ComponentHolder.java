package electrodynamics.common.tile.generic.component;

import net.minecraft.util.math.BlockPos;

public interface ComponentHolder {
    public BlockPos getPos();

    public boolean valid();

}
