package electrodynamics.common.tile.pipelines.fluid;

import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.registers.ElectrodynamicsSounds;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import voltaic.common.network.utils.FluidUtilities;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.sound.ITickableSound;
import voltaic.prefab.sound.SoundBarrierMethods;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;

public class TileElectricPump extends GenericTile implements ITickableSound {

    private final SingleProperty<Boolean> isGenerating = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "isGenerating", false));
    private boolean isSoundPlaying;

    public TileElectricPump(BlockPos worldPosition, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_ELECTRICPUMP.get(), worldPosition, blockState);
	addComponent(new ComponentElectrodynamic(this, false, true)
		.maxJoules(ElectrodynamicsConfig.INSTANCE.ELECTRICPUMP_USAGE_PER_TICK.get() * 20)
		.setInputDirections(BlockEntityUtils.MachineDirection.TOP));
	addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
	addComponent(new ComponentFluidHandlerMulti(this).setOutputTanks(1, 0)
		.setOutputDirections(BlockEntityUtils.MachineDirection.RIGHT).setOutputFluidTags(FluidTags.WATER));
    }

    protected void tickServer(Level level, ComponentTickable tickable) {
	ComponentElectrodynamic electro = requireComponent(IComponentType.Electrodynamic);
	if (electro.getJoulesStored() < ElectrodynamicsConfig.INSTANCE.ELECTRICPUMP_USAGE_PER_TICK.get()) {
	    isGenerating.setValue(false);
	    return;
	}
	if (tickable.getTicks() % 10 == 0) {
	    FluidState state = level.getFluidState(worldPosition.below());
	    isGenerating.setValue(state.isSource() && state.getType() == Fluids.WATER);
	}
	if (!isGenerating.getValue())
	    return;
	Direction outputDirection = getFacing().getClockWise();
	BlockEntity target = level.getBlockEntity(worldPosition.relative(outputDirection));
	if (target == null || target.isRemoved())
	    return;
	electro.joules(electro.getJoulesStored() - ElectrodynamicsConfig.INSTANCE.ELECTRICPUMP_USAGE_PER_TICK.get());
	FluidUtilities.receiveFluid(target, outputDirection.getOpposite(), new FluidStack(Fluids.WATER, 200), false);
    }

    protected void tickClient(Level level, ComponentTickable tickable) {
	if (!shouldPlaySound())
	    return;
	if (level.random.nextDouble() < 0.15) {
	    level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(),
		    worldPosition.getY() + level.random.nextDouble() * 0.2 + 0.8,
		    worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
	}
	level.addParticle(ParticleTypes.BUBBLE, worldPosition.getX() + level.random.nextDouble(),
		worldPosition.getY() - level.random.nextDouble() * 0.2 - .1,
		worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);

	if (!isSoundPlaying) {
	    isSoundPlaying = true;
	    SoundBarrierMethods.playTileSound(ElectrodynamicsSounds.SOUND_ELECTRICPUMP.get(), this, true);
	}
    }

    @Override
    public void setNotPlaying() {
	isSoundPlaying = false;
    }

    @Override
    public boolean shouldPlaySound() {
	return isGenerating.getValue();
    }

    @Override
    public int getComparatorSignal(Level level) {
	return isGenerating.getValue() ? 15 : 0;
    }

}
