package electrodynamics.common.tile;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerFermentationPlant;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.tile.types.GenericFluidTile;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TileFermentationPlant extends GenericFluidTile {

	public static final int MAX_TANK_CAPACITY = 5000;

	public TileFermentationPlant(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_FERMENTATIONPLANT.get(), worldPosition, blockState);
		addComponent(new ComponentTickable().tickClient(this::tickClient));
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentElectrodynamic(this).input(Direction.DOWN).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE));
		addComponent(new ComponentFluidHandlerMulti(this).setTanks(1, 1, MAX_TANK_CAPACITY).setInputDirections(Direction.WEST).setOutputDirections(Direction.EAST).setRecipeType(ElectrodynamicsRecipeInit.FERMENTATION_PLANT_TYPE.get()));
		addComponent(new ComponentInventory(this).size(6).faceSlots(Direction.DOWN, 1).relativeSlotFaces(0, Direction.EAST, Direction.UP).inputs(1).bucketInputs(1).bucketOutputs(1).upgrades(3).processors(1).processorInputs(1).validUpgrades(ContainerFermentationPlant.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentProcessor(this).canProcess(processor -> processor.outputToPipe().consumeBucket().dispenseBucket().canProcessFluidItem2FluidRecipe(processor, ElectrodynamicsRecipeInit.FERMENTATION_PLANT_TYPE.get())).process(component -> component.processFluidItem2FluidRecipe(component)));
		addComponent(new ComponentContainerProvider(SubtypeMachine.fermentationplant).createMenu((id, player) -> new ContainerFermentationPlant(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));

	}

	@Override
	public AABB getRenderBoundingBox() {
		return super.getRenderBoundingBox().inflate(1);
	}

	protected void tickClient(ComponentTickable tickable) {
		if (this.<ComponentProcessor>getComponent(ComponentType.Processor).operatingTicks.get() > 0) {
			if (level.random.nextDouble() < 0.15) {
				level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble() * 0.4 + 0.5, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
			}
			Direction dir = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection().getClockWise();
			double x = worldPosition.getX() + 0.55 + dir.getStepX() * 0.2;
			double z = worldPosition.getZ() + 0.55 + dir.getStepZ() * 0.2;
			level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, worldPosition.getY() + 0.4, z, 0.0D, 0.0D, 0.0D);
		}
	}
}
