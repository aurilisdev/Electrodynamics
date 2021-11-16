package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

public class TileSolarPanel extends GenericTile {
    private CachedTileOutput output;
    private boolean generating;

    public TileSolarPanel(BlockPos worldPosition, BlockState blockState) {
	super(DeferredRegisters.TILE_SOLARPANEL.get(), worldPosition, blockState);
	addComponent(new ComponentTickable().tickServer(this::tickServer));
	addComponent(new ComponentElectrodynamic(this).output(Direction.DOWN));
    }

    protected void tickServer(ComponentTickable tickable) {
	if (output == null) {
	    output = new CachedTileOutput(level, worldPosition.relative(Direction.DOWN));
	}
	if (tickable.getTicks() % 20 == 0) {
	    output.update();
	    generating = level.canSeeSky(worldPosition.offset(0, 1, 0));
	}
	if (level.isDay() && generating && output.valid()) {

	    float mod = 1.0f - Mth.clamp(1.0F - (Mth.cos(level.getTimeOfDay(1f) * ((float) Math.PI * 2f)) * 2.0f + 0.2f), 0.0f, 1.0f);
	    mod *= 1.0f - level.getRainLevel(1f) * 5.0f / 16.0f;
	    mod *= (1.0f - level.getThunderLevel(1f) * 5.0F / 16.0f) * 0.8f + 0.2f;
	    Biome b = level.getBiomeManager().getBiome(getBlockPos());
	    TransferPack pack = TransferPack.ampsVoltage(
		    Constants.SOLARPANEL_AMPERAGE * (b.getTemperature(getBlockPos()) / 2.0) * mod
			    * (level.isRaining() || level.isThundering() ? 0.7f : 1),
		    this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getVoltage());
	    ElectricityUtilities.receivePower(output.getSafe(), Direction.UP, pack, false);
	}
    }
}
