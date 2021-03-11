package electrodynamics.common.tile.generic.component;

import java.util.function.Supplier;

import net.minecraft.nbt.CompoundNBT;

public class ComponentPacketHandler implements Component {
    private Supplier<CompoundNBT> customPacketSupplier;
    private Supplier<CompoundNBT> guiPacketSupplier;
    private Supplier<Void> customPacketWriter;
    private Supplier<Void> guiPacketWriter;

    public ComponentPacketHandler setCustomPacketSupplier(Supplier<CompoundNBT> customPacketSupplier) {
	this.customPacketSupplier = customPacketSupplier;
	return this;
    }

    public ComponentPacketHandler setGuiPacketSupplier(Supplier<CompoundNBT> guiPacketSupplier) {
	this.guiPacketSupplier = guiPacketSupplier;
	return this;
    }

    public ComponentPacketHandler setCustomPacketWriter(Supplier<Void> customPacketWriter) {
	this.customPacketWriter = customPacketWriter;
	return this;
    }

    public ComponentPacketHandler setGuiPacketWriter(Supplier<Void> guiPacketWriter) {
	this.guiPacketWriter = guiPacketWriter;
	return this;
    }

    public Supplier<CompoundNBT> getCustomPacketSupplier() {
	return customPacketSupplier;
    }

    public Supplier<CompoundNBT> getGuiPacketSupplier() {
	return guiPacketSupplier;
    }

    public Supplier<Void> getCustomPacketWriter() {
	return customPacketWriter;
    }

    public Supplier<Void> getGuiPacketWriter() {
	return guiPacketWriter;
    }

    @Override
    public ComponentType getType() {
	return ComponentType.PacketHandler;
    }
}
