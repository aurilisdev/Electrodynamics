package electrodynamics.common.tile.generic;

import java.util.EnumMap;

import electrodynamics.api.References;
import electrodynamics.common.tile.generic.component.Component;
import electrodynamics.common.tile.generic.component.ComponentHolder;
import electrodynamics.common.tile.generic.component.ComponentType;
import electrodynamics.common.tile.generic.component.type.ComponentName;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.INameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class GenericTile extends TileEntity implements INameable, ComponentHolder {
    private EnumMap<ComponentType, Component> componentMap = new EnumMap<>(ComponentType.class);

    public boolean hasComponent(ComponentType type) {
	return componentMap.containsKey(type);
    }

    public <T extends Component> T getComponent(ComponentType type) {
	return !hasComponent(type) ? null : (T) componentMap.get(type);
    }

    public GenericTile addComponent(Component component) {
	if (hasComponent(component.getType())) {
	    new Exception("Component of type: " + component.getType().name() + " already registered!")
		    .printStackTrace();
	}
	componentMap.put(component.getType(), component);
	return this;
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
	super.read(state, compound);
	for (Component component : componentMap.values()) {
	    component.setHolder(this);
	    component.saveToNBT(compound);
	}
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
	for (Component component : componentMap.values()) {
	    component.setHolder(this);
	    component.saveToNBT(compound);
	}
	return super.write(compound);
    }

    protected GenericTile(TileEntityType<?> tileEntityTypeIn) {
	super(tileEntityTypeIn);
    }

    @Override
    public ITextComponent getName() {
	return hasComponent(ComponentType.Name) ? this.<ComponentName>getComponent(ComponentType.Name).getName()
		: new StringTextComponent(References.ID + ".default.tile.name");
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
	for (Component component : componentMap.values()) {
	    component.setHolder(this);
	    if (component.hasCapability(cap, side)) {
		return component.getCapability(cap, side);
	    }
	}
	return super.getCapability(cap, side);
    }

    @Override
    public void remove() {
	super.remove();
	for (Component component : componentMap.values()) {
	    component.setHolder(this);
	    component.remove();
	}
    }

    @Override
    public BlockPos getPos() {
	return pos;
    }

    @Override
    public boolean valid() {
	return !isRemoved();
    }
}
