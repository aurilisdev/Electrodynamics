package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.formatting.MeasurementUnit;
import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.api.tile.electric.CapabilityElectrodynamic;
import electrodynamics.api.tile.electric.IElectrodynamic;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.inventory.container.ContainerBatteryBox;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.tile.generic.GenericTileInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
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

public class TileBatteryBox extends GenericTileInventory implements ITickableTileBase, IEnergyStorage, IElectrodynamic {
    public static final double DEFAULT_VOLTAGE = 120.0;
    public static final double DEFAULT_OUTPUT_JOULES_PER_TICK = 359.0 * DEFAULT_VOLTAGE / 20.0;
    public static final double DEFAULT_MAX_JOULES = MeasurementUnit.MEGA.value * 5;
    protected double currentCapacityMultiplier = 1;
    protected double receiveLimitLeft = DEFAULT_OUTPUT_JOULES_PER_TICK * currentCapacityMultiplier;
    protected double joules = 0;
    private CachedTileOutput output;

    public TileBatteryBox() {
	super(DeferredRegisters.TILE_BATTERYBOX.get());
    }

    @Override
    public void tickServer() {
	if (output == null) {
	    output = new CachedTileOutput(world, new BlockPos(pos).offset(getFacing().getOpposite()));
	}
	receiveLimitLeft = DEFAULT_OUTPUT_JOULES_PER_TICK * currentCapacityMultiplier;
	if (joules > 0) {
	    joules -= ElectricityUtilities.receivePower(output.get(), getFacing(),
		    TransferPack.joulesVoltage(
			    Math.min(joules, DEFAULT_OUTPUT_JOULES_PER_TICK * currentCapacityMultiplier),
			    DEFAULT_VOLTAGE),
		    false).getJoules();
	}
	currentCapacityMultiplier = 1;
	for (ItemStack stack : items) {
	    if (!stack.isEmpty() && stack.getItem() instanceof ItemProcessorUpgrade) {
		ItemProcessorUpgrade upgrade = (ItemProcessorUpgrade) stack.getItem();
		currentCapacityMultiplier *= upgrade.subtype.capacityMultiplier;
	    }
	}
	if (joules > DEFAULT_MAX_JOULES * currentCapacityMultiplier) {
	    joules = DEFAULT_MAX_JOULES * currentCapacityMultiplier;
	}
	if (world.getWorldInfo().getDayTime() % 20 == 0) {
	    sendGUIPacket();
	}
    }

    @Override
    public CompoundNBT writeGUIPacket() {
	CompoundNBT nbt = super.writeGUIPacket();
	nbt.putDouble("joules", joules);
	nbt.putDouble("currentCapacityMultiplier", currentCapacityMultiplier);
	return nbt;
    }

    @Override
    public void readGUIPacket(CompoundNBT nbt) {
	super.readGUIPacket(nbt);
	joules = nbt.getDouble("joules");
	currentCapacityMultiplier = nbt.getDouble("currentCapacityMultiplier");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
	compound.putDouble("joules", joules);
	return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
	super.read(state, compound);
	this.joules = compound.getDouble("joules");
    }

    @Override
    public int getSizeInventory() {
	return 3;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
	return SLOTS_EMPTY;
    }

    @Override
    public ITextComponent getDisplayName() {
	return new TranslationTextComponent("container.batterybox");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
	return new ContainerBatteryBox(id, player, this, getInventoryData());
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
	Direction facAc = getFacing();
	if ((capability == CapabilityElectrodynamic.ELECTRODYNAMIC || capability == CapabilityEnergy.ENERGY)
		&& (facing != null && facing == facAc || facing == facAc.getOpposite())) {
	    lastDir = facing;
	    return (LazyOptional<T>) LazyOptional.of(() -> this);
	}
	return super.getCapability(capability, facing);
    }

    private Direction lastDir = null;

    @Override
    public TransferPack receivePower(TransferPack transfer, boolean debug) {
	if (lastDir == getFacing()) {
	    double received = Math.min(Math.min(receiveLimitLeft, transfer.getJoules()),
		    DEFAULT_MAX_JOULES * currentCapacityMultiplier - joules);
	    if (!debug) {
		if (transfer.getVoltage() == DEFAULT_VOLTAGE) {
		    joules += received;
		    receiveLimitLeft -= received;
		}
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
	return (int) Math.min(Integer.MAX_VALUE, joules);
    }

    @Override
    @Deprecated
    public int getMaxEnergyStored() {
	return (int) Math.min(Integer.MAX_VALUE, DEFAULT_MAX_JOULES * currentCapacityMultiplier);
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
	this.joules = joules;
    }

    @Override
    public double getJoulesStored() {
	return joules;
    }

    @Override
    public double getMaxJoulesStored() {
	return DEFAULT_MAX_JOULES * currentCapacityMultiplier;
    }

    public double getCapacityMultiplier() {
	return currentCapacityMultiplier;
    }

}
