package electrodynamics.prefab.tile;

import electrodynamics.api.References;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.prefab.tile.components.Component;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentName;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.utilities.Scheduler;
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
    private ComponentProcessor[] processors = new ComponentProcessor[5];

    public boolean hasComponent(ComponentType type) {
	return components[type.ordinal()] != null;
    }

    public <T extends Component> T getComponent(ComponentType type) {
	return !hasComponent(type) ? null : (T) components[type.ordinal()];
    }

    public ComponentProcessor getProcessor(int id) {
	return processors[id];
    }

    public GenericTile addProcessor(ComponentProcessor processor) {
	for (int i = 0; i < processors.length; i++) {
	    if (processors[i] == null) {
		processors[i] = processor;
		processor.holder(this);
		break;
	    }
	}
	return this;
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
	for (ComponentProcessor pr : processors) {
	    if (pr != null) {
		pr.holder(this);
		pr.loadFromNBT(state, compound);
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
	for (ComponentProcessor pr : processors) {
	    if (pr != null) {
		pr.holder(this);
		pr.saveToNBT(compound);
	    }
	}
	return super.write(compound);
    }

    protected GenericTile(TileEntityType<?> tileEntityTypeIn) {
	super(tileEntityTypeIn);
    }

    @Override
    public void onLoad() {
	super.onLoad();
	if (hasComponent(ComponentType.PacketHandler)) {
	    Scheduler.schedule(1, () -> {
		this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
		this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	    });

	}
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
	for (ComponentProcessor pr : processors) {
	    if (pr != null) {
		pr.holder(this);
		pr.remove();
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
