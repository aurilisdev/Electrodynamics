package electrodynamics.common.tile.quantumcapacitor;

import java.util.UUID;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.api.tile.electric.CapabilityElectrodynamic;
import electrodynamics.api.tile.electric.IElectrodynamic;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.inventory.container.ContainerQuantumCapacitor;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.tile.generic.GenericTileInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion.Mode;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileQuantumCapacitor extends GenericTileInventory
	implements ITickableTileBase, IEnergyStorage, IElectrodynamic {
    public static final double DEFAULT_MAX_JOULES = Double.MAX_VALUE;
    public static final double DEFAULT_VOLTAGE = 1920.0;
    public double outputJoules = 359.0;
    public int frequency = 0;
    public UUID uuid = null;
    private CachedTileOutput outputCache;
    private CachedTileOutput outputCache2;

    public TileQuantumCapacitor() {
	super(DeferredRegisters.TILE_QUANTUMCAPACITOR.get());
    }

    public double getOutputJoules() {
	return outputJoules;
    }

    @Override
    public void tickServer() {
	if (outputCache == null) {
	    outputCache = new CachedTileOutput(world, new BlockPos(pos).offset(Direction.UP));
	}
	if (outputCache2 == null) {
	    outputCache2 = new CachedTileOutput(world, new BlockPos(pos).offset(Direction.DOWN));
	}
	double joules = QuantumCapacitorData.get(world).getJoules(uuid, frequency);
	if (joules > 0) {
	    double sent = ElectricityUtilities
		    .receivePower(outputCache.get(), Direction.DOWN,
			    TransferPack.joulesVoltage(Math.min(joules, outputJoules), DEFAULT_VOLTAGE), false)
		    .getJoules();
	    QuantumCapacitorData.get(world).setJoules(uuid, frequency,
		    QuantumCapacitorData.get(world).getJoules(uuid, frequency) - sent);
	}
	joules = QuantumCapacitorData.get(world).getJoules(uuid, frequency);
	if (joules > 0) {
	    double sent = ElectricityUtilities
		    .receivePower(outputCache2.get(), Direction.UP,
			    TransferPack.joulesVoltage(Math.min(joules, outputJoules), DEFAULT_VOLTAGE), false)
		    .getJoules();
	    QuantumCapacitorData.get(world).setJoules(uuid, frequency,
		    QuantumCapacitorData.get(world).getJoules(uuid, frequency) - sent);
	}
	if (world.getWorldInfo().getDayTime() % 50 == 0) {
	    sendGUIPacket();
	}
    }

    public double joulesClient = 0;

    @Override
    public CompoundNBT writeGUIPacket() {
	CompoundNBT nbt = super.writeGUIPacket();
	nbt.putDouble("joulesClient", QuantumCapacitorData.get(world).getJoules(uuid, frequency));
	nbt.putInt("frequency", frequency);
	nbt.putUniqueId("uuid", uuid);
	nbt.putDouble("outputJoules", outputJoules);
	return nbt;
    }

    @Override
    public void readGUIPacket(CompoundNBT nbt) {
	super.readGUIPacket(nbt);
	joulesClient = nbt.getDouble("joulesClient");
	frequency = nbt.getInt("frequency");
	uuid = nbt.getUniqueId("uuid");
	outputJoules = nbt.getDouble("outputJoules");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
	super.write(compound);
	compound.putInt("frequency", frequency);
	compound.putDouble("outputJoules", outputJoules);
	compound.putUniqueId("uuid", uuid);
	return compound;
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
	super.read(state, compound);
	outputJoules = compound.getDouble("outputJoules");
	frequency = compound.getInt("frequency");
	if (compound.hasUniqueId("uuid")) {
	    uuid = compound.getUniqueId("uuid");
	}
    }

    @Override
    public int getSizeInventory() {
	return 0;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
	return SLOTS_EMPTY;
    }

    @Override
    public ITextComponent getDisplayName() {
	return new TranslationTextComponent("container.quantumcapacitor");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
	return new ContainerQuantumCapacitor(id, player, this, getInventoryData());
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
	if (capability == CapabilityElectrodynamic.ELECTRODYNAMIC || capability == CapabilityEnergy.ENERGY) {
	    lastDir = facing;
	    return (LazyOptional<T>) LazyOptional.of(() -> this);
	}
	return super.getCapability(capability, facing);
    }

    private Direction lastDir = null;

    @Override
    public TransferPack receivePower(TransferPack transfer, boolean debug) {
	double joules = QuantumCapacitorData.get(world).getJoules(uuid, frequency);
	if (lastDir != Direction.UP && lastDir != Direction.DOWN) {
	    double received = Math.min(Math.min(DEFAULT_MAX_JOULES, transfer.getJoules()), DEFAULT_MAX_JOULES - joules);
	    if (!debug) {
		if (transfer.getVoltage() == DEFAULT_VOLTAGE) {
		    joules += received;
		}
		QuantumCapacitorData.get(world).setJoules(uuid, frequency, joules);
		if (transfer.getVoltage() > DEFAULT_VOLTAGE) {
		    world.setBlockState(pos, Blocks.AIR.getDefaultState());
		    world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(),
			    (float) Math.log10(10 + transfer.getVoltage() / DEFAULT_VOLTAGE), Mode.DESTROY);

		    return TransferPack.EMPTY;
		}
	    }
	    return TransferPack.joulesVoltage(received, transfer.getVoltage());
	}
	return TransferPack.EMPTY;
    }

    @Override
    @Deprecated
    public int receiveEnergy(int maxReceive, boolean simulate) {
	int calVoltage = 120;
	TransferPack pack = receivePower(TransferPack.joulesVoltage(maxReceive, calVoltage), simulate);
	return (int) Math.min(Integer.MAX_VALUE, pack.getJoules());
    }

    @Override
    @Deprecated
    public int extractEnergy(int maxExtract, boolean simulate) {
	int calVoltage = 120;
	TransferPack pack = extractPower(TransferPack.joulesVoltage(maxExtract, calVoltage), simulate);
	return (int) Math.min(Integer.MAX_VALUE, pack.getJoules());
    }

    @Override
    @Deprecated
    public int getEnergyStored() {
	return (int) Math.min(Integer.MAX_VALUE, QuantumCapacitorData.get(world).getJoules(uuid, frequency));
    }

    @Override
    @Deprecated
    public int getMaxEnergyStored() {
	return (int) Math.min(Integer.MAX_VALUE, DEFAULT_MAX_JOULES);
    }

    @Override
    @Deprecated
    public boolean canExtract() {
	return true;
    }

    @Override
    @Deprecated
    public boolean canReceive() {
	return true;
    }

    @Override
    @Deprecated
    public void setJoulesStored(double joules) {
	QuantumCapacitorData data = QuantumCapacitorData.get(world);
	if (data != null) {
	    data.setJoules(uuid, frequency, joules);
	}
    }

    @Override
    public double getJoulesStored() {
	return QuantumCapacitorData.get(world).getJoules(uuid, frequency);
    }

    @Override
    public double getMaxJoulesStored() {
	return DEFAULT_MAX_JOULES;
    }

}
