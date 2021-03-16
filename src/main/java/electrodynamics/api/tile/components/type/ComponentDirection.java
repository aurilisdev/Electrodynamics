package electrodynamics.api.tile.components.type;

import electrodynamics.api.tile.GenericTile;
import electrodynamics.api.tile.components.Component;
import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.common.block.BlockGenericMachine;
import net.minecraft.util.Direction;

public class ComponentDirection implements Component {
    private GenericTile holder;

    @Override
    public void setHolder(GenericTile holder) {
	this.holder = holder;
    }

    public Direction getDirection() {
	return holder.getBlockState().get(BlockGenericMachine.FACING);
    }

    @Override
    public ComponentType getType() {
	return ComponentType.Direction;
    }

}
