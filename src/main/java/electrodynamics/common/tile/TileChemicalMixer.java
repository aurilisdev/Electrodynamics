package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.common.inventory.container.ContainerChemicalMixer;
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

public class TileChemicalMixer extends GenericTileTicking {
    public static final int MAX_TANK_CAPACITY = 5000;
    public long clientTicks = 0;
    /**
     * Currently, there is no way to tell if the fluid is part of the input tank or
     * the output tank, so for now you cannot have fluids that are outputs as inputs
     * as well and vis a versa
     * 
     * This can be changed ofc later
     */
    public static Fluid[] SUPPORTED_INPUT_FLUIDS = new Fluid[] {

	    Fluids.WATER, DeferredRegisters.fluidEthanol

    };
    public static Fluid[] SUPPORTED_OUTPUT_FLUIDS = new Fluid[] {

	    DeferredRegisters.fluidSulfuricAcid

    };

    public TileChemicalMixer() {
	super(DeferredRegisters.TILE_CHEMICALMIXER.get());
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 2)
		.maxJoules(Constants.CHEMICALMIXER_USAGE_PER_TICK * 10));
	addComponent(new ComponentFluidHandler(this).relativeInput(Direction.EAST).relativeOutput(Direction.WEST)
		.addMultipleFluidTanks(SUPPORTED_INPUT_FLUIDS, MAX_TANK_CAPACITY, true)
		.addMultipleFluidTanks(SUPPORTED_OUTPUT_FLUIDS, MAX_TANK_CAPACITY, false));
	addComponent(new ComponentInventory(this).size(5).relativeSlotFaces(0, Direction.EAST, Direction.UP).relativeSlotFaces(1, Direction.DOWN)
		.valid((slot, stack) -> slot < 2 || stack.getItem() instanceof ItemProcessorUpgrade));
	addComponent(new ComponentProcessor(this).upgradeSlots(2, 3, 4).canProcess(component -> canProcessChemMix(component))
		.process(component -> component.processFluidItem2FluidRecipe(component, FluidItem2FluidRecipe.class))
		.usage(Constants.CHEMICALMIXER_USAGE_PER_TICK).type(ComponentProcessorType.ObjectToObject)
		.requiredTicks(Constants.CHEMICALMIXER_REQUIRED_TICKS));
	addComponent(new ComponentContainerProvider("container.chemicalmixer")
		.createMenu((id, player) -> new ContainerChemicalMixer(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));

    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
	return super.getRenderBoundingBox().grow(1);
    }

    protected void tickClient(ComponentTickable tickable) {
	boolean running = this.<ComponentProcessor>getComponent(ComponentType.Processor).operatingTicks > 0;
	if (running) {
	    if (world.rand.nextDouble() < 0.15) {
		world.addParticle(ParticleTypes.SMOKE, pos.getX() + world.rand.nextDouble(), pos.getY() + world.rand.nextDouble() * 0.4 + 0.5,
			pos.getZ() + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
	    }
	    clientTicks++;
	}
    }

    protected boolean canProcessChemMix(ComponentProcessor processor) {

	ComponentDirection direction = getComponent(ComponentType.Direction);
	ComponentFluidHandler tank = getComponent(ComponentType.FluidHandler);
	BlockPos face = getPos().offset(direction.getDirection().rotateY().getOpposite());
	TileEntity faceTile = world.getTileEntity(face);
	if (faceTile != null) {
	    LazyOptional<IFluidHandler> cap = faceTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,
		    direction.getDirection().rotateY().getOpposite().getOpposite());
	    if (cap.isPresent()) {
		IFluidHandler handler = cap.resolve().get();
		if (handler.isFluidValid(0, tank.getStackFromFluid(DeferredRegisters.fluidSulfuricAcid))) {
		    tank.getStackFromFluid(DeferredRegisters.fluidSulfuricAcid)
			    .shrink(handler.fill(tank.getStackFromFluid(DeferredRegisters.fluidSulfuricAcid), FluidAction.EXECUTE));
		}
	    }
	}

	processor.consumeBucket(MAX_TANK_CAPACITY, SUPPORTED_INPUT_FLUIDS, 1);
	return processor.canProcessFluidItem2FluidRecipe(processor, FluidItem2FluidRecipe.class, ElectrodynamicsRecipeInit.CHEMICAL_MIXER_TYPE);
    }

}
