package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.common.inventory.container.ContainerFermentationPlant;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandler;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentProcessorType;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;

public class TileFermentationPlant extends GenericTileTicking {

    public static final int MAX_TANK_CAPACITY = 5000;

    public TileFermentationPlant() {
	super(DeferredRegisters.TILE_FERMENTATIONPLANT.get());
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentElectrodynamic(this).input(Direction.DOWN).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE)
		.maxJoules(Constants.FERMENTATIONPLANT_USAGE_PER_TICK * 10));
	addComponent(new ComponentFluidHandler(this).relativeInput(Direction.EAST).relativeOutput(Direction.WEST)
		.setAddFluidsValues(FluidItem2FluidRecipe.class, ElectrodynamicsRecipeInit.FERMENTATION_PLANT_TYPE, MAX_TANK_CAPACITY, true, true));
	addComponent(new ComponentInventory(this).size(6).faceSlots(Direction.DOWN, 1).relativeSlotFaces(0, Direction.EAST, Direction.UP)
		.valid((slot, stack) -> slot < 3 || stack.getItem() instanceof ItemProcessorUpgrade));
	addComponent(new ComponentProcessor(this).upgradeSlots(3, 4, 5).usage(Constants.FERMENTATIONPLANT_USAGE_PER_TICK)
		.type(ComponentProcessorType.ObjectToObject).canProcess(component -> canProcessFermPlan(component))
		.process(component -> component.processFluidItem2FluidRecipe(component, FluidItem2FluidRecipe.class))
		.requiredTicks(Constants.FERMENTATIONPLANT_REQUIRED_TICKS));
	addComponent(new ComponentContainerProvider("container.fermentationplant")
		.createMenu((id, player) -> new ContainerFermentationPlant(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));

    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
	return super.getRenderBoundingBox().grow(1);
    }

    protected void tickClient(ComponentTickable tickable) {
	if (this.<ComponentProcessor>getComponent(ComponentType.Processor).operatingTicks > 0) {
	    if (world.rand.nextDouble() < 0.15) {
		world.addParticle(ParticleTypes.SMOKE, pos.getX() + world.rand.nextDouble(), pos.getY() + world.rand.nextDouble() * 0.4 + 0.5,
			pos.getZ() + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
	    }
	    Direction dir = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection().rotateY();
	    double x = pos.getX() + 0.55 + dir.getXOffset() * 0.2;
	    double z = pos.getZ() + 0.55 + dir.getZOffset() * 0.2;
	    world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, pos.getY() + 0.4, z, 0.0D, 0.0D, 0.0D);
	}
    }

    protected boolean canProcessFermPlan(ComponentProcessor processor) {

	return processor.outputToPipe(processor).consumeBucket(1).dispenseBucket(2)
		.canProcessFluidItem2FluidRecipe(processor, FluidItem2FluidRecipe.class, ElectrodynamicsRecipeInit.FERMENTATION_PLANT_TYPE);
    }

}
