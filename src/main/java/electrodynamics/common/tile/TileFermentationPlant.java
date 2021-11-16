package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.common.inventory.container.ContainerFermentationPlant;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
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
import electrodynamics.prefab.tile.components.type.ComponentProcessorType;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TileFermentationPlant extends GenericTile {

    public static final int MAX_TANK_CAPACITY = 5000;

    public TileFermentationPlant(BlockPos worldPosition, BlockState blockState) {
	super(DeferredRegisters.TILE_FERMENTATIONPLANT.get(), worldPosition, blockState);
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentElectrodynamic(this).input(Direction.DOWN).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE)
		.maxJoules(Constants.FERMENTATIONPLANT_USAGE_PER_TICK * 10));
	addComponent(((ComponentFluidHandlerMulti) new ComponentFluidHandlerMulti(this).relativeInput(Direction.EAST).relativeOutput(Direction.WEST))
		.setAddFluidsValues(FluidItem2FluidRecipe.class, ElectrodynamicsRecipeInit.FERMENTATION_PLANT_TYPE, MAX_TANK_CAPACITY, true, true));
	addComponent(new ComponentInventory(this).size(6).faceSlots(Direction.DOWN, 1).relativeSlotFaces(0, Direction.EAST, Direction.UP)
		.valid((slot, stack) -> slot < 3 || stack.getItem() instanceof ItemProcessorUpgrade));
	addComponent(new ComponentProcessor(this).upgradeSlots(3, 4, 5).usage(Constants.FERMENTATIONPLANT_USAGE_PER_TICK)
		.type(ComponentProcessorType.ObjectToObject).canProcess(this::canProcessFermPlan)
		.process(component -> component.processFluidItem2FluidRecipe(component, FluidItem2FluidRecipe.class))
		.requiredTicks(Constants.FERMENTATIONPLANT_REQUIRED_TICKS));
	addComponent(new ComponentContainerProvider("container.fermentationplant")
		.createMenu((id, player) -> new ContainerFermentationPlant(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));

    }

    @Override
    public AABB getRenderBoundingBox() {
	return super.getRenderBoundingBox().inflate(1);
    }

    protected void tickClient(ComponentTickable tickable) {
	if (this.<ComponentProcessor>getComponent(ComponentType.Processor).operatingTicks > 0) {
	    if (level.random.nextDouble() < 0.15) {
		level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(),
			worldPosition.getY() + level.random.nextDouble() * 0.4 + 0.5, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D,
			0.0D);
	    }
	    Direction dir = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection().getClockWise();
	    double x = worldPosition.getX() + 0.55 + dir.getStepX() * 0.2;
	    double z = worldPosition.getZ() + 0.55 + dir.getStepZ() * 0.2;
	    level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, worldPosition.getY() + 0.4, z, 0.0D, 0.0D, 0.0D);
	}
    }

    protected boolean canProcessFermPlan(ComponentProcessor processor) {

	return processor.outputToPipe(processor).consumeBucket(1).dispenseBucket(2).canProcessFluidItem2FluidRecipe(processor,
		FluidItem2FluidRecipe.class, ElectrodynamicsRecipeInit.FERMENTATION_PLANT_TYPE);
    }

}
