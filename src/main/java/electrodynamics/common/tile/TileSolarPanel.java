package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.electric.CapabilityElectrodynamic;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.generic.GenericTileTickable;
import electrodynamics.common.tile.generic.component.type.ComponentElectrodynamic;
import electrodynamics.common.tile.generic.component.type.ComponentTickable;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;

public class TileSolarPanel extends GenericTileTickable {
    private CachedTileOutput output;

    public TileSolarPanel() {
	super(DeferredRegisters.TILE_SOLARPANEL.get());
	addComponent(new ComponentTickable().setTickServer(() -> tickServer()));
	addComponent(new ComponentElectrodynamic().addOutputDirection(Direction.DOWN));
    }

    public void tickServer() {
	if (world.isDaytime() && world.canSeeSky(pos.add(0, 1, 0))) {
	    if (output == null) {
		output = new CachedTileOutput(world, new BlockPos(pos).offset(Direction.DOWN));
	    }
	    float mod = (float) (1.0 - MathHelper.clamp(
		    1.0F - (MathHelper.cos(world.func_242415_f(1f) * ((float) Math.PI * 2f)) * 2.0f + 0.2f), 0.0, 1.0));
	    mod = (float) (mod * (1.0d - world.getRainStrength(1f) * 5.0f / 16.0d));
	    mod = (float) (mod * (1.0d - world.getThunderStrength(1f) * 5.0F / 16.0d)) * 0.8f + 0.2f;
	    Biome b = world.getBiomeManager().getBiome(getPos());
	    TransferPack pack = TransferPack.ampsVoltage(
		    Constants.SOLARPANEL_AMPERAGE * (b.getTemperature(getPos()) / 2.0) * mod
			    * (world.isRaining() || world.isThundering() ? 0.7f : 1),
		    CapabilityElectrodynamic.DEFAULT_VOLTAGE);
	    ElectricityUtilities.receivePower(output.get(), Direction.UP, pack, false);
	}
    }

}
