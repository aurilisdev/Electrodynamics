package electrodynamics.common.tile.generic.component.type;

import java.util.function.Supplier;

import electrodynamics.common.tile.generic.component.Component;
import electrodynamics.common.tile.generic.component.ComponentType;
import net.minecraft.nbt.CompoundNBT;

public class ComponentPacketHandler implements Component {
    protected Supplier<CompoundNBT> customPacketSupplier;
    protected Supplier<CompoundNBT> guiPacketSupplier;
    protected Runnable customPacketWriter;
    protected Runnable guiPacketWriter;

    public ComponentPacketHandler setCustomPacketSupplier(Supplier<CompoundNBT> customPacketSupplier) {
	this.customPacketSupplier = customPacketSupplier;
	return this;
    }

    public ComponentPacketHandler setGuiPacketSupplier(Supplier<CompoundNBT> guiPacketSupplier) {
	this.guiPacketSupplier = guiPacketSupplier;
	return this;
    }

    public ComponentPacketHandler setCustomPacketWriter(Runnable customPacketWriter) {
	this.customPacketWriter = customPacketWriter;
	return this;
    }

    public ComponentPacketHandler setGuiPacketWriter(Runnable guiPacketWriter) {
	this.guiPacketWriter = guiPacketWriter;
	return this;
    }

    public Supplier<CompoundNBT> getCustomPacketSupplier() {
	return customPacketSupplier;
    }

    public Supplier<CompoundNBT> getGuiPacketSupplier() {
	return guiPacketSupplier;
    }

    public Runnable getCustomPacketWriter() {
	return customPacketWriter;
    }

    public Runnable getGuiPacketWriter() {
	return guiPacketWriter;
    }

    @Override
    public ComponentType getType() {
	return ComponentType.PacketHandler;
    }
}
