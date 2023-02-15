package electrodynamics.common.tile;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.block.VoxelShapes;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerO2OProcessor;
import electrodynamics.common.inventory.container.tile.ContainerO2OProcessorDouble;
import electrodynamics.common.inventory.container.tile.ContainerO2OProcessorTriple;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.sound.SoundBarrierMethods;
import electrodynamics.prefab.sound.utils.ITickableSound;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TileWireMill extends GenericTile implements ITickableSound {

	private boolean isSoundPlaying = false;

	public TileWireMill(BlockPos worldPosition, BlockState blockState) {
		this(SubtypeMachine.wiremill, 0, worldPosition, blockState);
	}

	public TileWireMill(SubtypeMachine machine, int extra, BlockPos worldPosition, BlockState blockState) {
		super(extra == 1 ? ElectrodynamicsBlockTypes.TILE_WIREMILLDOUBLE.get() : extra == 2 ? ElectrodynamicsBlockTypes.TILE_WIREMILLTRIPLE.get() : ElectrodynamicsBlockTypes.TILE_WIREMILL.get(), worldPosition, blockState);
		int processorInputs = 1;
		int processorCount = extra + 1;
		int inputCount = processorInputs * (extra + 1);
		int outputCount = 1 * (extra + 1);
		int biproducts = 1 + extra;
		int invSize = 3 + inputCount + outputCount + biproducts;

		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentTickable().tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * Math.pow(2, extra)).joules(Constants.WIREMILL_USAGE_PER_TICK * 20 * (extra + 1)));

		int[] ints = new int[extra + 1];
		for (int i = 0; i <= extra; i++) {
			ints[i] = i * 2;
		}

		addComponent(new ComponentInventory(this).size(invSize).inputs(inputCount).outputs(outputCount).upgrades(3).processors(processorCount).processorInputs(processorInputs).biproducts(biproducts).validUpgrades(ContainerO2OProcessor.VALID_UPGRADES).valid(machineValidator(ints)).setMachineSlots(extra));
		addComponent(new ComponentContainerProvider(machine).createMenu((id, player) -> (extra == 0 ? new ContainerO2OProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray()) : extra == 1 ? new ContainerO2OProcessorDouble(id, player, getComponent(ComponentType.Inventory), getCoordsArray()) : extra == 2 ? new ContainerO2OProcessorTriple(id, player, getComponent(ComponentType.Inventory), getCoordsArray()) : null)));

		for (int i = 0; i <= extra; i++) {
			addProcessor(new ComponentProcessor(this, i, extra + 1).canProcess(component -> component.canProcessItem2ItemRecipe(component, ElectrodynamicsRecipeInit.WIRE_MILL_TYPE.get())).process(component -> component.processItem2ItemRecipe(component)).requiredTicks(Constants.WIREMILL_REQUIRED_TICKS).usage(Constants.WIREMILL_USAGE_PER_TICK));
		}
	}

	protected void tickClient(ComponentTickable tickable) {
		if (!isProcessorActive()) {
			return;
		}

		if (level.random.nextDouble() < 0.15) {
			level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble() * 0.5 + 0.5, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
		}

		if (!isSoundPlaying) {
			isSoundPlaying = true;
			SoundBarrierMethods.playTileSound(ElectrodynamicsSounds.SOUND_HUM.get(), this, true);
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
		shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.3125, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.0625, 0.3125, 0, 0.3125, 1, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.3125, 0.125, 0.6875, 0.375, 0.5625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.625, 0.9375, 0.4375, 0.75, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.5625, 0.625, 0.9375, 0.625, 0.75, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.75, 0.9375, 0.5625, 0.8125, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.421875, 0.640625, 0.125, 0.578125, 0.796875, 0.5625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.421875, 0.421875, 0.125, 0.578125, 0.578125, 0.5625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, 0.9375, 0.5625, 0.625, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.3125, 0.25, 0.0625, 0.75, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.3125, 0, 0.0625, 1, 0.125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.875, 0.125, 0.0625, 1, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.3125, 0.875, 0.0625, 1, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.3125, 0.6875, 0.6875, 0.9375, 0.9375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.3125, 0, 0.6875, 0.9375, 0.125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.3125, 0.5625, 0.6875, 0.9375, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.6875, 0.3125, 0.6875, 1, 0.625, 0.9375), BooleanOp.OR);
		VoxelShapes.registerShape(SubtypeMachine.wiremill, shape, Direction.EAST);
	}
}
