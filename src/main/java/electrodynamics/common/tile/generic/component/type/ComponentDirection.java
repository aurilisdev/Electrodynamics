package electrodynamics.common.tile.generic.component.type;

import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.common.tile.generic.GenericTile;
import electrodynamics.common.tile.generic.component.Component;
import electrodynamics.common.tile.generic.component.ComponentType;
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
