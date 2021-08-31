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
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;

public class TileChemicalMixer extends GenericTileTicking {
    public static final int MAX_TANK_CAPACITY = 5000;
    public long clientTicks = 0;

    public TileChemicalMixer() {
	super(DeferredRegisters.TILE_CHEMICALMIXER.get());
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 2)
		.maxJoules(Constants.CHEMICALMIXER_USAGE_PER_TICK * 10));
	addComponent(new ComponentFluidHandler(this).relativeInput(Direction.EAST).relativeOutput(Direction.WEST)
		.setAddFluidsValues(FluidItem2FluidRecipe.class, ElectrodynamicsRecipeInit.CHEMICAL_MIXER_TYPE, MAX_TANK_CAPACITY, true, true));
	addComponent(new ComponentInventory(this).size(6).relativeSlotFaces(0, Direction.EAST, Direction.UP).relativeSlotFaces(1, Direction.DOWN)
		.valid((slot, stack) -> slot < 3 || stack.getItem() instanceof ItemProcessorUpgrade));
	addComponent(new ComponentProcessor(this).upgradeSlots(3, 4, 5)
		.canProcess(component -> component.outputToPipe(component).consumeBucket(1).dispenseBucket(2)
			.canProcessFluidItem2FluidRecipe(component, FluidItem2FluidRecipe.class, ElectrodynamicsRecipeInit.CHEMICAL_MIXER_TYPE))
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

}
