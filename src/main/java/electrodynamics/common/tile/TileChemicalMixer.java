package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.capability.electrodynamic.CapabilityElectrodynamic;
import electrodynamics.common.inventory.container.ContainerChemicalMixer;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TileChemicalMixer extends GenericTile {
	public static final int MAX_TANK_CAPACITY = 5000;
	public long clientTicks = 0;

	public TileChemicalMixer(BlockPos worldPosition, BlockState blockState) {
		super(DeferredRegisters.TILE_CHEMICALMIXER.get(), worldPosition, blockState);
		addComponent(new ComponentTickable().tickClient(this::tickClient));
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 2)
				.maxJoules(Constants.CHEMICALMIXER_USAGE_PER_TICK * 10));
		addComponent(((ComponentFluidHandlerMulti) new ComponentFluidHandlerMulti(this).relativeInput(Direction.EAST).relativeOutput(Direction.WEST))
				.setAddFluidsValues(ElectrodynamicsRecipeInit.CHEMICAL_MIXER_TYPE, MAX_TANK_CAPACITY, true, true));
		addComponent(new ComponentInventory(this).size(6).relativeSlotFaces(0, Direction.EAST, Direction.UP).relativeSlotFaces(1, Direction.DOWN)
				.inputs(1).bucketInputs(1).bucketOutputs(1).upgrades(3).processors(1).processorInputs(1).valid(machineValidator()));
		addComponent(new ComponentProcessor(this).setProcessorNumber(0)
				.canProcess(component -> component.outputToPipe(component).consumeBucket().dispenseBucket().canProcessFluidItem2FluidRecipe(component,
						ElectrodynamicsRecipeInit.CHEMICAL_MIXER_TYPE))
				.process(component -> component.processFluidItem2FluidRecipe(component)).usage(Constants.CHEMICALMIXER_USAGE_PER_TICK)
				.requiredTicks(Constants.CHEMICALMIXER_REQUIRED_TICKS));
		addComponent(new ComponentContainerProvider("container.chemicalmixer")
				.createMenu((id, player) -> new ContainerChemicalMixer(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));

	}

	@Override
	public AABB getRenderBoundingBox() {
		return super.getRenderBoundingBox().inflate(1);
	}

	protected void tickClient(ComponentTickable tickable) {
		boolean running = this.<ComponentProcessor>getComponent(ComponentType.Processor).operatingTicks > 0;
		if (running) {
			if (level.random.nextDouble() < 0.15) {
				level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(),
						worldPosition.getY() + level.random.nextDouble() * 0.4 + 0.5, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D,
						0.0D);
			}
			clientTicks++;
		}
	}

}
