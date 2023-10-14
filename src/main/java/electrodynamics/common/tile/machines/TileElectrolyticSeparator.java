package electrodynamics.common.tile.machines;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.block.VoxelShapes;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerElectrolyticSeparator;
import electrodynamics.common.network.utils.GasUtilities;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.prefab.sound.SoundBarrierMethods;
import electrodynamics.prefab.sound.utils.ITickableSound;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentGasHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.tile.types.GenericGasTile;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TileElectrolyticSeparator extends GenericGasTile implements ITickableSound {

	public static final int MAX_INPUT_TANK_CAPACITY = 5000;
	public static final double MAX_OUTPUT_TANK_CAPACITY = 5000.0;
	public long clientTicks = 0;

	private static final Direction OXYGEN_DIRECTION = Direction.EAST;
	private static final Direction HYDROGEN_DIRECTION = Direction.WEST;

	private boolean isSoundPlaying = false;

	public TileElectrolyticSeparator(BlockPos worldPos, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_ELECTROLYTICSEPARATOR.get(), worldPos, blockState);
		addComponent(new ComponentTickable(this).tickClient(this::tickClient).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(Direction.SOUTH).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2));
		addComponent(new ComponentFluidHandlerMulti(this).setInputDirections(Direction.NORTH).setInputTanks(1, arr(MAX_INPUT_TANK_CAPACITY)).setRecipeType(ElectrodynamicsRecipeInit.ELECTROLYTIC_SEPERATOR_TYPE.get()));
		addComponent(new ComponentGasHandlerMulti(this).setOutputDirections(OXYGEN_DIRECTION, HYDROGEN_DIRECTION).setOutputTanks(2, arr(MAX_OUTPUT_TANK_CAPACITY, MAX_OUTPUT_TANK_CAPACITY), arr(1000.0, 1000.0), arr(1024, 1024)).setRecipeType(ElectrodynamicsRecipeInit.ELECTROLYTIC_SEPERATOR_TYPE.get()).setCondensedHandler(getCondensedHandler()));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().bucketInputs(1).gasOutputs(2).upgrades(3)).validUpgrades(ContainerElectrolyticSeparator.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentProcessor(this).canProcess(component -> component.consumeBucket().dispenseGasCylinder().canProcessFluid2GasRecipe(component, ElectrodynamicsRecipeInit.ELECTROLYTIC_SEPERATOR_TYPE.get())).process(component -> component.processFluid2GasRecipe(component)));
		addComponent(new ComponentContainerProvider(SubtypeMachine.electrolyticseparator, this).createMenu((id, player) -> new ContainerElectrolyticSeparator(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	public void tickServer(ComponentTickable tickable) {
		ComponentGasHandlerMulti handler = getComponent(IComponentType.GasHandler);
		GasUtilities.outputToPipe(this, handler.getOutputTanks()[0].asArray(), OXYGEN_DIRECTION);
		GasUtilities.outputToPipe(this, handler.getOutputTanks()[1].asArray(), HYDROGEN_DIRECTION);
	}

	protected void tickClient(ComponentTickable tickable) {
		if (!shouldPlaySound()) {
			return;
		}
		if (level.random.nextDouble() < 0.15) {
			level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble() * 0.4 + 0.5, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
		}

		if (!isSoundPlaying) {
			isSoundPlaying = true;
			SoundBarrierMethods.playTileSound(ElectrodynamicsSounds.SOUND_ELECTROLYTICSEPARATOR.get(), this, true);
		}
	}

	@Override
	public void setNotPlaying() {
		isSoundPlaying = false;
	}

	@Override
	public boolean shouldPlaySound() {
		return this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive();
	}

	@Override
	public int getComparatorSignal() {
		return this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive() ? 15 : 0;
	}

	static {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.0057870370370369795, 0, 0, 1.0, 0.1875, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.318287037037037, 0.3125, 0.8125, 0.693287037037037, 0.6875, 0.9375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.318287037037037, 0.3125, 0.0625, 0.693287037037037, 0.6875, 0.1875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.443287037037037, 0.8125, 0.25, 0.568287037037037, 0.875, 0.375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.443287037037037, 0.8125, 0.625, 0.568287037037037, 0.875, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.818287037037037, 0.25, 0.3125, 0.943287037037037, 0.625, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.0057870370370369795, 0.25, 0.25, 0.06828703703703698, 0.75, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.255787037037037, 0.1875, 0.9375, 0.755787037037037, 0.75, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.255787037037037, 0.1875, 0, 0.755787037037037, 0.75, 0.0625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.943287037037037, 0.1875, 0.25, 1.0, 0.75, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.443287037037037, 0.125, 0.25, 0.568287037037037, 0.8125, 0.375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.443287037037037, 0.1875, 0.625, 0.568287037037037, 0.8125, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.255787037037037, 0.1875, 0.25, 0.693287037037037, 0.3125, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.693287037037037, 0.1875, 0.25, 0.755787037037037, 0.75, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.255787037037037, 0.1875, 0.75, 0.755787037037037, 0.75, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.255787037037037, 0.1875, 0.1875, 0.755787037037037, 0.75, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.06828703703703698, 0.1875, 0.0625, 0.255787037037037, 1, 0.9375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.474537037037037, 0.875, 0.6875, 0.537037037037037, 0.9375, 0.859375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.474537037037037, 0.6875, 0.859375, 0.537037037037037, 0.9375, 0.921875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.474537037037037, 0.6875, 0.078125, 0.537037037037037, 0.9375, 0.140625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.474537037037037, 0.875, 0.140625, 0.537037037037037, 0.9375, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.849537037037037, 0.1875, 0.6875, 0.912037037037037, 0.5625, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.849537037037037, 0.1875, 0.75, 0.912037037037037, 0.25, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.474537037037037, 0.1875, 0.8125, 0.849537037037037, 0.25, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.849537037037037, 0.1875, 0.25, 0.912037037037037, 0.5625, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.849537037037037, 0.1875, 0.125, 0.912037037037037, 0.25, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.474537037037037, 0.1875, 0.125, 0.849537037037037, 0.25, 0.1875), BooleanOp.OR);
		VoxelShapes.registerShape(SubtypeMachine.electrolyticseparator, shape, Direction.EAST);
	}

}
