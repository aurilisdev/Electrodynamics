package electrodynamics.common.tile;

import java.util.HashSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.api.tile.electric.CapabilityElectrodynamic;
import electrodynamics.api.tile.electric.IElectrodynamic;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.multiblock.IMultiblockTileNode;
import electrodynamics.common.multiblock.Subnode;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.generic.GenericTileBase;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class TileAdvancedSolarPanel extends GenericTileBase
	implements ITickableTileBase, IElectrodynamic, IMultiblockTileNode {

    public TileAdvancedSolarPanel() {
	super(DeferredRegisters.TILE_ADVANCEDSOLARPANEL.get());
    }

    protected CachedTileOutput output;

    @Override
    public void tickServer() {
	if (world.isDaytime() && world.canSeeSky(pos.add(0, 1, 0))) {
	    if (output == null) {
		output = new CachedTileOutput(world, new BlockPos(pos).offset(Direction.DOWN));
	    }
	    ElectricityUtilities.receivePower(output.get(), Direction.UP, getOutput(), false);
	}

    }

    public float getSunBrightness() {
	float mod = 1.0F - (MathHelper.cos(world.func_242415_f(1f) * ((float) Math.PI * 2F)) * 2.0F + 0.2F);
	mod = MathHelper.clamp(mod, 0.0F, 1.0F);
	mod = 1.0F - mod;
	mod = (float) (mod * (1.0D - world.getRainStrength(1f) * 5.0F / 16.0D));
	mod = (float) (mod * (1.0D - world.getThunderStrength(1f) * 5.0F / 16.0D));
	return mod * 0.8F + 0.2F;
    }

    public TransferPack getOutput() {
	Biome b = world.getBiomeManager().getBiome(getPos());
	return TransferPack.ampsVoltage(Constants.ADVANCEDSOLARPANEL_AMPERAGE * (b.getTemperature(getPos()) / 2.0)
		* getSunBrightness() * (world.isRaining() || world.isThundering() ? 0.7f : 1), getVoltage());
    }

    @Override
    public HashSet<Subnode> getSubNodes() {
	return BlockMachine.advancedsolarpanelsubnodes;
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
	if (capability == CapabilityElectrodynamic.ELECTRODYNAMIC && facing == Direction.DOWN) {
	    return (LazyOptional<T>) LazyOptional.of(() -> this);
	}
	return super.getCapability(capability, facing);
    }

    @Override
    public double getVoltage() {
	return IElectrodynamic.super.getVoltage() * 2;
    }

    @Override
    public void setJoulesStored(double joules) {
    }

    @Override
    public double getJoulesStored() {
	return 0;
    }

    @Override
    public double getMaxJoulesStored() {
	return 0;
    }
}
