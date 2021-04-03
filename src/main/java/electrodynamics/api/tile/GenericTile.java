package electrodynamics.api.tile;

import electrodynamics.api.References;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.api.tile.components.Component;
import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentName;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.INameable;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public class GenericTile extends TileEntity implements INameable {
    private Component[] components = new Component[ComponentType.values().length];

    public boolean hasComponent(ComponentType type) {
	return components[type.ordinal()] != null;
    }

    public <T extends Component> T getComponent(ComponentType type) {
	return !hasComponent(type) ? null : (T) components[type.ordinal()];
    }

    public GenericTile addComponent(Component component) {
	component.holder(this);
	if (hasComponent(component.getType())) {
	    throw new ExceptionInInitializerError("Component of type: " + component.getType().name() + " already registered!");
	}
	components[component.getType().ordinal()] = component;
	return this;
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
	super.read(state, compound);
	for (Component component : components) {
	    if (component != null) {
		component.holder(this);
		component.loadFromNBT(state, compound);
	    }
	}
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
	for (Component component : components) {
	    if (component != null) {
		component.holder(this);
		component.saveToNBT(compound);
	    }
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
	if (cap == CapabilityElectrodynamic.ELECTRODYNAMIC) {
	    if (components[ComponentType.Electrodynamic.ordinal()] != null) {
		return components[ComponentType.Electrodynamic.ordinal()].getCapability(cap, side);
	    }
	}
	if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
	    if (components[ComponentType.FluidHandler.ordinal()] != null) {
		return components[ComponentType.FluidHandler.ordinal()].getCapability(cap, side);
	    }
	}
	if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
	    if (components[ComponentType.Inventory.ordinal()] != null) {
		return components[ComponentType.Inventory.ordinal()].getCapability(cap, side);
	    }
	}
	return super.getCapability(cap, side);
    }

    @Override
    public void remove() {
	super.remove();
	for (Component component : components) {
	    if (component != null) {
		component.holder(this);
		component.remove();
	    }
	}
    }

    public IntArray getCoordsArray() {
	IntArray array = new IntArray(3);
	array.set(0, pos.getX());
	array.set(1, pos.getY());
	array.set(2, pos.getZ());
	return array;
    }

    @Override
    public BlockPos getPos() {
	return pos;
    }

    @Override
    public double getMaxRenderDistanceSquared() {
	return 256;
    }
}
