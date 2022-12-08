package electrodynamics.common.tile;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.particle.ParticleAPI;
import electrodynamics.common.block.VoxelShapes;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerO2OProcessor;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.sound.SoundBarrierMethods;
import electrodynamics.prefab.sound.utils.ITickableSoundTile;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.InventoryUtils;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TileLathe extends GenericTile implements ITickableSoundTile {

	private boolean isSoundPlaying = false;

	public TileLathe(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_LATHE.get(), worldPosition, blockState);
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH)
				.voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2)
				.maxJoules(Constants.LATHE_USAGE_PER_TICK * 20));
		addComponent(new ComponentInventory(this).size(6).inputs(1).outputs(1).upgrades(3).processors(1)
				.processorInputs(1).valid(machineValidator()).biproducts(1));
		addComponent(new ComponentContainerProvider(SubtypeMachine.lathe)
				.createMenu((id, player) -> new ContainerO2OProcessor(id, player, getComponent(ComponentType.Inventory),
						getCoordsArray())));
		addProcessor(new ComponentProcessor(this).setProcessorNumber(0)
				.canProcess(component -> component.canProcessItem2ItemRecipe(component,
						ElectrodynamicsRecipeInit.LATHE_TYPE.get()))
				.process(component -> component.processItem2ItemRecipe(component))
				.requiredTicks(Constants.LATHE_REQUIRED_TICKS).usage(Constants.LATHE_USAGE_PER_TICK));
	}

	protected void tickServer(ComponentTickable tick) {
		InventoryUtils.handleExperienceUpgrade(this);
	}

	protected void tickClient(ComponentTickable tickable) {
		if (!isProcessorActive()) {
			return;
		}
		Direction direction = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
		if (level.random.nextDouble() < 0.10) {
			for (int i = 0; i < 5; i++) {
				double d4 = level.random.nextDouble() * 4.0 / 16.0 + 0.5 - 2.0 / 16.0;
				double d6 = level.random.nextDouble() * 4.0 / 16.0 + 0.5 - 2.0 / 16.0;
				ParticleAPI.addGrindedParticle(level, worldPosition.getX() + d4 + direction.getStepX() * 0.2,
						worldPosition.getY() + 0.7, worldPosition.getZ() + d6 + direction.getStepZ() * 0.2, 0.0D, 0.0D,
						0.0D, Blocks.IRON_BLOCK.defaultBlockState(), worldPosition);
			}
		}
		if (!isSoundPlaying) {
			isSoundPlaying = true;
			SoundBarrierMethods.playTileSound(ElectrodynamicsSounds.SOUND_LATHEPLAYING.get(), this, true);
		}
	}

	@Override
	public void setNotPlaying() {
		isSoundPlaying = false;
	}

	@Override
	public boolean shouldPlaySound() {
		return isProcessorActive();
	}

	static {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.1875, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.1875, 0.25, 0.125, 0.75, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.1875, 0.1875, 0.3125, 0.9375, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.1875, 0.25, 0.25, 0.9375, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.6875, 0.1875, 0.1875, 0.75, 0.9375, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.1875, 0.25, 0.8125, 0.9375, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.6875, 0.1875, 0.75, 0.75, 0.9375, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.1875, 0.6875, 0.8125, 0.9375, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.1875, 0.75, 0.3125, 0.9375, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.1875, 0.6875, 0.25, 0.9375, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.9375, 0.25, 0.75, 1, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.40625, 0.875, 0.34375, 0.59375, 0.9375, 0.65625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.34375, 0.875, 0.40625, 0.40625, 0.9375, 0.59375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.59375, 0.875, 0.40625, 0.65625, 0.9375, 0.59375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.34375, 0.1875, 0.21875, 0.65625, 0.5625, 0.78125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.21875, 0.1875, 0.34375, 0.34375, 0.5625, 0.65625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.65625, 0.1875, 0.34375, 0.78125, 0.5625, 0.65625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.28125, 0.1875, 0.28125, 0.34375, 0.5625, 0.34375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.28125, 0.1875, 0.65625, 0.34375, 0.5625, 0.71875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.65625, 0.1875, 0.28125, 0.71875, 0.5625, 0.34375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.65625, 0.1875, 0.65625, 0.71875, 0.5625, 0.71875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.40625, 0.5625, 0.34375, 0.59375, 0.625, 0.65625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.34375, 0.5625, 0.40625, 0.40625, 0.625, 0.59375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.59375, 0.5625, 0.40625, 0.65625, 0.625, 0.59375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46875, 0.84375, 0.40625, 0.53125, 0.875, 0.59375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.40625, 0.8421875, 0.46875, 0.59375, 0.875, 0.53125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46875, 0.8328125, 0.46875, 0.53125, 0.8421875, 0.53125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4921874999999999, 0.8015625, 0.4921875000000001, 0.5078124999999999,
				0.8578125, 0.5078125000000001), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.1875, 0.125, 0.875, 0.25, 0.875), BooleanOp.OR);
		VoxelShapes.registerShape(SubtypeMachine.lathe, shape, Direction.EAST);
	}
}
