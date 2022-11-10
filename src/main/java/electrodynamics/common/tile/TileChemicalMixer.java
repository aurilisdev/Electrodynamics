package electrodynamics.common.tile;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.inventory.container.tile.ContainerChemicalMixer;
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
import electrodynamics.prefab.utilities.InventoryUtils;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TileChemicalMixer extends GenericTile {
	public static final int MAX_TANK_CAPACITY = 5000;
	public long clientTicks = 0;

	public TileChemicalMixer(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_CHEMICALMIXER.get(), worldPosition, blockState);
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2).maxJoules(Constants.CHEMICALMIXER_USAGE_PER_TICK * 10));
		addComponent(((ComponentFluidHandlerMulti) new ComponentFluidHandlerMulti(this).relativeInput(Direction.EAST).relativeOutput(Direction.WEST)).setAddFluidsValues(ElectrodynamicsRecipeInit.CHEMICAL_MIXER_TYPE.get(), MAX_TANK_CAPACITY, true, true));
		addComponent(new ComponentInventory(this).size(6).relativeSlotFaces(0, Direction.EAST, Direction.UP).relativeSlotFaces(1, Direction.DOWN).inputs(1).bucketInputs(1).bucketOutputs(1).upgrades(3).processors(1).processorInputs(1).validUpgrades(ContainerChemicalMixer.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentProcessor(this).setProcessorNumber(0).canProcess(component -> component.outputToPipe().consumeBucket().dispenseBucket().canProcessFluidItem2FluidRecipe(component, ElectrodynamicsRecipeInit.CHEMICAL_MIXER_TYPE.get())).process(component -> component.processFluidItem2FluidRecipe(component)).usage(Constants.CHEMICALMIXER_USAGE_PER_TICK).requiredTicks(Constants.CHEMICALMIXER_REQUIRED_TICKS));
		addComponent(new ComponentContainerProvider("container.chemicalmixer").createMenu((id, player) -> new ContainerChemicalMixer(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));

	}

	protected void tickServer(ComponentTickable tick) {
		InventoryUtils.handleExperienceUpgrade(this);
	}

	@Override
	public AABB getRenderBoundingBox() {
		return super.getRenderBoundingBox().inflate(1);
	}

	protected void tickClient(ComponentTickable tickable) {
		boolean running = this.<ComponentProcessor>getComponent(ComponentType.Processor).operatingTicks > 0;
		if (running) {
			if (level.random.nextDouble() < 0.15) {
				level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble() * 0.4 + 0.5, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
			}
			clientTicks++;
		}
	}

}
