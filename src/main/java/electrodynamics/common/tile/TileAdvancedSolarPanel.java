package electrodynamics.common.tile;

import java.util.HashSet;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.multiblock.IMultiblockTileNode;
import electrodynamics.common.multiblock.Subnode;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TargetValue;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TileAdvancedSolarPanel extends GenericTileTicking implements IMultiblockTileNode {
    public TargetValue currentRotation = new TargetValue(0);
    protected CachedTileOutput output;
    private boolean generating = false;

    public TileAdvancedSolarPanel() {
	super(DeferredRegisters.TILE_ADVANCEDSOLARPANEL.get());
	addComponent(new ComponentTickable().tickServer(this::tickServer));
	addComponent(new ComponentElectrodynamic(this).output(Direction.DOWN).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 2));
    }

    protected void tickServer(ComponentTickable tickable) {
	if (output == null) {
	    output = new CachedTileOutput(world, pos.offset(Direction.DOWN));
	}
	if (tickable.getTicks() % 40 == 0) {
	    output.update();
	    generating = world.canSeeSky(pos.add(0, 1, 0));
	}
	if (world.isDaytime() && generating && output.valid()) {
	    float mod = 1.0f - MathHelper.clamp(1.0F - (MathHelper.cos(world.func_242415_f(1f) * ((float) Math.PI * 2f)) * 2.0f + 0.2f), 0.0f, 1.0f);
	    mod *= 1.0f - world.getRainStrength(1f) * 5.0f / 16.0f;
	    mod *= (1.0f - world.getThunderStrength(1f) * 5.0F / 16.0f) * 0.8f + 0.2f;
	    Biome b = world.getBiomeManager().getBiome(getPos());
	    TransferPack pack = TransferPack.ampsVoltage(
		    Constants.ADVANCEDSOLARPANEL_AMPERAGE * (b.getTemperature(getPos()) / 2.0) * mod
			    * (world.isRaining() || world.isThundering() ? 0.7f : 1),
		    this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getVoltage());
	    ElectricityUtilities.receivePower(output.getSafe(), Direction.UP, pack, false);
	}
    }

    @Override
    @OnlyIn(value = Dist.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
	return super.getRenderBoundingBox().grow(2);
    }

    @Override
    public HashSet<Subnode> getSubNodes() {
	return BlockMachine.advancedsolarpanelsubnodes;
    }
}
