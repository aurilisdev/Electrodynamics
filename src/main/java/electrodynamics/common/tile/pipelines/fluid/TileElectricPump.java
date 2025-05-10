package electrodynamics.common.tile.pipelines.fluid;

import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.registers.ElectrodynamicsSounds;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraftforge.fluids.FluidStack;
import voltaic.common.network.utils.FluidUtilities;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.sound.ITickableSound;
import voltaic.prefab.sound.SoundBarrierMethods;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.object.CachedTileOutput;

public class TileElectricPump extends GenericTile implements ITickableSound {

	private SingleProperty<Boolean> isGenerating = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "isGenerating", false));

	protected CachedTileOutput output;
	private boolean isSoundPlaying = false;

	public TileElectricPump() {
		super(ElectrodynamicsTiles.TILE_ELECTRICPUMP.get());
		addComponent(new ComponentElectrodynamic(this, false, true).maxJoules(ElectroConstants.ELECTRICPUMP_USAGE_PER_TICK * 20).setInputDirections(BlockEntityUtils.MachineDirection.TOP));
		addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentFluidHandlerMulti(this).setOutputTanks(1, 0).setOutputDirections(BlockEntityUtils.MachineDirection.RIGHT).setOutputFluidTags(FluidTags.WATER));
	}

	protected void tickServer(ComponentTickable tickable) {
		Direction direction = getFacing().getClockWise();
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(direction));
		}
		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

		if (tickable.getTicks() % 20 == 0) {
			output.update(worldPosition.relative(direction));
			FluidState state = level.getFluidState(worldPosition.relative(Direction.DOWN));
			if (isGenerating.getValue() != (state.isSource() && state.getType() == Fluids.WATER)) {
				isGenerating.setValue(electro.getJoulesStored() > ElectroConstants.ELECTRICPUMP_USAGE_PER_TICK && state.isSource() && state.getType() == Fluids.WATER);
			}
		}
		if (isGenerating.getValue() && output.valid()) {
			electro.joules(electro.getJoulesStored() - ElectroConstants.ELECTRICPUMP_USAGE_PER_TICK);
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
		return isGenerating.getValue();
	}

	@Override
	public int getComparatorSignal() {
		return isGenerating.getValue() ? 15 : 0;
	}

}
