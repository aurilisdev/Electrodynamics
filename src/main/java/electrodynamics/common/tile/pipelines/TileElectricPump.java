package electrodynamics.common.tile.pipelines;

import electrodynamics.common.network.utils.FluidUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.sound.SoundBarrierMethods;
import electrodynamics.prefab.sound.utils.ITickableSound;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

public class TileElectricPump extends GenericTile implements ITickableSound {

	private Property<Boolean> isGenerating = property(new Property<>(PropertyType.Boolean, "isGenerating", false));

	private boolean isSoundPlaying = false;

	public TileElectricPump(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_ELECTRICPUMP.get(), worldPosition, blockState);
		addComponent(new ComponentElectrodynamic(this, false, true).maxJoules(Constants.ELECTRICPUMP_USAGE_PER_TICK * 20).setInputDirections(Direction.UP));
		addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentFluidHandlerMulti(this).setOutputTanks(1, 0).setOutputDirections(Direction.EAST).setOutputFluidTags(FluidTags.WATER));
	}

	protected CachedTileOutput output;

	protected void tickServer(ComponentTickable tickable) {
		Direction direction = getFacing().getClockWise();
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(direction));
		}
		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

		if (tickable.getTicks() % 20 == 0) {
			output.update(worldPosition.relative(direction));
			FluidState state = level.getFluidState(worldPosition.relative(Direction.DOWN));
			if (isGenerating.get() != (state.isSource() && state.getType() == Fluids.WATER)) {
				isGenerating.set(electro.getJoulesStored() > Constants.ELECTRICPUMP_USAGE_PER_TICK && state.isSource() && state.getType() == Fluids.WATER);
			}
		}
		if (isGenerating.get() == Boolean.TRUE && output.valid()) {
			electro.joules(electro.getJoulesStored() - Constants.ELECTRICPUMP_USAGE_PER_TICK);
			FluidUtilities.receiveFluid(output.getSafe(), direction.getOpposite(), new FluidStack(Fluids.WATER, 200), false);
		}
	}

	protected void tickClient(ComponentTickable tickable) {
		if (!shouldPlaySound()) {
			return;
		}
		if (level.random.nextDouble() < 0.15) {
			level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble() * 0.2 + 0.8, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
		level.addParticle(ParticleTypes.BUBBLE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() - level.random.nextDouble() * 0.2 - .1, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);

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
		return isGenerating.get();
	}

	@Override
	public int getComparatorSignal() {
		return isGenerating.get() ? 15 : 0;
	}

}
