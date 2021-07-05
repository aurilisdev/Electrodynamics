package electrodynamics.common.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.common.fluid.FluidMineral;
import electrodynamics.common.inventory.container.ContainerMineralWasher;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.recipe.ElectrodynamicsRecipe;
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
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;

public class TileMineralWasher extends GenericTileTicking {
    public static final int MAX_TANK_CAPACITY = 5000;

    public static Fluid[] SUPPORTED_INPUT_FLUIDS = new Fluid[] {

	    DeferredRegisters.fluidSulfuricAcid

    };
    public static Fluid[] SUPPORTED_OUTPUT_FLUIDS;
    static {
	List<FluidMineral> mineralFluids = new ArrayList<>(DeferredRegisters.SUBTYPEMINERALFLUID_MAPPINGS.values());
	SUPPORTED_OUTPUT_FLUIDS = new Fluid[mineralFluids.size()];
	for (int i = 0; i < mineralFluids.size(); i++) {
	    SUPPORTED_OUTPUT_FLUIDS[i] = mineralFluids.get(i);
	}
    }

    public TileMineralWasher() {
		super(DeferredRegisters.TILE_MINERALWASHER.get());
		ElectrodynamicsRecipe.findRecipesbyType(ElectrodynamicsRecipeInit.MINERAL_WASHER_TYPE, this.world);
		
		addComponent(new ComponentTickable().tickClient(this::tickClient));
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 4)
			.maxJoules(Constants.MINERALWASHER_USAGE_PER_TICK * 10));
		addComponent(new ComponentFluidHandler(this).relativeInput(Direction.values())
			.addMultipleFluidTanks(SUPPORTED_INPUT_FLUIDS, MAX_TANK_CAPACITY, true)
			.addMultipleFluidTanks(SUPPORTED_OUTPUT_FLUIDS, MAX_TANK_CAPACITY, false));
		addComponent(new ComponentInventory(this).size(5).relativeSlotFaces(0, Direction.values())
			.valid((slot, stack) -> slot < 2 || stack.getItem() instanceof ItemProcessorUpgrade).shouldSendInfo());
		addComponent(new ComponentProcessor(this).upgradeSlots(2, 3, 4).usage(Constants.MINERALWASHER_USAGE_PER_TICK)
			.type(ComponentProcessorType.ObjectToObject)
			.canProcess
			(component -> component.outputToPipe(component, SUPPORTED_OUTPUT_FLUIDS)
						.consumeBucket(MAX_TANK_CAPACITY, SUPPORTED_INPUT_FLUIDS, 1)
						.canProcessFluidItem2FluidRecipe(component, FluidItem2FluidRecipe.class, ElectrodynamicsRecipeInit.MINERAL_WASHER_TYPE)
			)
			.process(component -> component.processFluidItem2FluidRecipe(component, FluidItem2FluidRecipe.class))
			.requiredTicks(Constants.MINERALWASHER_REQUIRED_TICKS));
		addComponent(new ComponentContainerProvider("container.mineralwasher")
			.createMenu((id, player) -> new ContainerMineralWasher(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
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
	    for (int i = 0; i < 2; i++) {
		double x = 0.5 + world.rand.nextDouble() * 0.4 - 0.2;
		double y = 0.5 + world.rand.nextDouble() * 0.3 - 0.15;
		double z = 0.5 + world.rand.nextDouble() * 0.4 - 0.2;
		world.addParticle(new RedstoneParticleData(1f, 1f, 0, 1), pos.getX() + x, pos.getY() + y, pos.getZ() + z,
			world.rand.nextDouble() * 0.2 - 0.1, world.rand.nextDouble() * 0.2 - 0.1, world.rand.nextDouble() * 0.2 - 0.1);
	    }
	}
    }
    


}
