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
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class TileFermentationPlant extends GenericTileTicking {

    public static final int MAX_TANK_CAPACITY = 5000;

    public static Fluid[] SUPPORTED_INPUT_FLUIDS = new Fluid[] {

	    Fluids.WATER

    };
    public static Fluid[] SUPPORTED_OUTPUT_FLUIDS = new Fluid[] {

	    DeferredRegisters.fluidEthanol

    };

    public TileFermentationPlant() {
	super(DeferredRegisters.TILE_FERMENTATIONPLANT.get());
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentElectrodynamic(this).input(Direction.DOWN).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE)
		.maxJoules(Constants.FERMENTATIONPLANT_USAGE_PER_TICK * 10));
	addComponent(
		new ComponentFluidHandler(this).relativeInput(Direction.EAST).addMultipleFluidTanks(SUPPORTED_INPUT_FLUIDS, MAX_TANK_CAPACITY, true)
			.addMultipleFluidTanks(SUPPORTED_OUTPUT_FLUIDS, MAX_TANK_CAPACITY, false).relativeOutput(Direction.WEST));
	addComponent(new ComponentInventory(this).size(5).faceSlots(Direction.DOWN, 1).relativeSlotFaces(0, Direction.EAST, Direction.UP)
		.valid((slot, stack) -> slot < 2 || stack.getItem() instanceof ItemProcessorUpgrade));
	addComponent(new ComponentProcessor(this).upgradeSlots(2, 3, 4).usage(Constants.FERMENTATIONPLANT_USAGE_PER_TICK)
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
	ComponentDirection direction = getComponent(ComponentType.Direction);
	ComponentFluidHandler tank = getComponent(ComponentType.FluidHandler);
	BlockPos face = getPos().offset(direction.getDirection().getOpposite().rotateY());
	TileEntity faceTile = world.getTileEntity(face);
	if (faceTile != null) {
	    LazyOptional<IFluidHandler> cap = faceTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,
		    direction.getDirection().getOpposite().rotateY().getOpposite());
	    if (cap.isPresent()) {
		IFluidHandler handler = cap.resolve().get();
		if (handler.isFluidValid(0, tank.getStackFromFluid(DeferredRegisters.fluidEthanol))) {
		    tank.getStackFromFluid(DeferredRegisters.fluidEthanol)
			    .shrink(handler.fill(tank.getStackFromFluid(DeferredRegisters.fluidEthanol), FluidAction.EXECUTE));
		}
	    }
	}
	processor.consumeBucket(MAX_TANK_CAPACITY, SUPPORTED_INPUT_FLUIDS, 1);
	boolean canProcess = processor.canProcessFluidItem2FluidRecipe(processor, FluidItem2FluidRecipe.class,
		ElectrodynamicsRecipeInit.FERMENTATION_PLANT_TYPE);
	return canProcess;
    }

}
