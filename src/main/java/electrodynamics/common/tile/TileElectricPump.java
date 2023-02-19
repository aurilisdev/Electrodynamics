package electrodynamics.common.tile;

import electrodynamics.common.block.VoxelShapes;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.network.FluidUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.sound.SoundBarrierMethods;
import electrodynamics.prefab.sound.utils.ITickableSound;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
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
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;

public class TileElectricPump extends GenericTile implements ITickableSound {

	private Property<Boolean> isGenerating = property(new Property<>(PropertyType.Boolean, "isGenerating", false));

	private boolean isSoundPlaying = false;

	public TileElectricPump(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_ELECTRICPUMP.get(), worldPosition, blockState);
		addComponent(new ComponentElectrodynamic(this).maxJoules(Constants.ELECTRICPUMP_USAGE_PER_TICK * 20).input(Direction.UP));
		addComponent(new ComponentDirection());
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentFluidHandlerMulti(this).setOutputTanks(1, 0).setOutputDirections(Direction.EAST).setOutputFluidTags(FluidTags.WATER));
	}

	protected CachedTileOutput output;

	protected void tickServer(ComponentTickable tickable) {
		Direction direction = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection().getClockWise();
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(direction));
		}
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);

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

	static {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.125, 0.5625, 0.375, 0.9375, 0.75, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.28125, 0.125, 0.375, 0.875, 0.5625, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.0625, 0.3125, 0.8125, 0.8125, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5, 0.125, 0.125, 0.6875, 0.1875, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.375, 0.25, 0.75, 0.6875, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.3125, 0.6875, 0.6875, 0.6875, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.53125, 0.1875, 0.125, 0.65625, 0.625, 0.1875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.0625, 0.625, 0.3125, 0.3125, 0.8125, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1328125, 0.8125, 0.25, 0.2734375, 0.859375, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5, 0.6875, 0.1875, 0.6875, 0.75, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.14453125, 0.66015625, 0.25, 0.26171875, 0.77734375, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.515625, 0.484375, 0.1875, 0.671875, 0.640625, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1328125, 0.8125, 0.25, 0.2734375, 0.859375, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5, 0.6875, 0.1875, 0.6875, 0.75, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.0625, 0.6484375, 0.25, 0.109375, 0.7890625, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.40625, 0.46875, 0.1875, 0.46875, 0.65625, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.0625, 0.6484375, 0.25, 0.109375, 0.7890625, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.40625, 0.46875, 0.1875, 0.46875, 0.65625, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1328125, 0.578125, 0.25, 0.2734375, 0.625, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5, 0.375, 0.1875, 0.6875, 0.4375, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1328125, 0.578125, 0.25, 0.2734375, 0.625, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5, 0.375, 0.1875, 0.6875, 0.4375, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.296875, 0.6484375, 0.25, 0.34375, 0.7890625, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.71875, 0.46875, 0.1875, 0.78125, 0.65625, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.296875, 0.6484375, 0.25, 0.34375, 0.7890625, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.71875, 0.46875, 0.1875, 0.78125, 0.65625, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.8125, 0.375, 0.6875, 0.875, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.375, 0.75, 0.0625, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.875, 0.3125, 0.6875, 1.0, 0.6875), BooleanOp.OR);
		VoxelShapes.registerShape(SubtypeMachine.electricpump, shape, Direction.EAST);
	}
}
