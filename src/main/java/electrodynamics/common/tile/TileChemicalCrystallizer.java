package electrodynamics.common.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.fluid.FluidMineral;
import electrodynamics.common.inventory.container.ContainerChemicalCrystallizer;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.fluid2item.Fluid2ItemRecipe;
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
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;

public class TileChemicalCrystallizer extends GenericTileTicking {
    public static final int MAX_TANK_CAPACITY = 5000;

    public static Fluid[] SUPPORTED_INPUT_FLUIDS;
    public static Fluid[] OTHER_INPUT_FLUIDS = new Fluid[] { DeferredRegisters.fluidPolyethylene };
    static {
	List<FluidMineral> mineralFluids = new ArrayList<>(DeferredRegisters.SUBTYPEMINERALFLUID_MAPPINGS.values());
	SUPPORTED_INPUT_FLUIDS = new Fluid[mineralFluids.size() + OTHER_INPUT_FLUIDS.length];
	int pos = 0;
	for (int i = 0; i < mineralFluids.size(); i++) {
	    SUPPORTED_INPUT_FLUIDS[i] = mineralFluids.get(i);
	    pos = i;
	}
	for (int i = 0; i + pos + 1 < SUPPORTED_INPUT_FLUIDS.length; i++) {
	    SUPPORTED_INPUT_FLUIDS[i + pos + 1] = OTHER_INPUT_FLUIDS[i];
	}
    }

    public TileChemicalCrystallizer() {
	super(DeferredRegisters.TILE_CHEMICALCRYSTALLIZER.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentTickable().tickClient(this::tickClient)/* .tickCommon(this::tickCommon) */);
	addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 2)
		.maxJoules(Constants.CHEMICALCRYSTALLIZER_USAGE_PER_TICK * 10));
	addComponent(new ComponentFluidHandler(this).relativeInput(Direction.values()).addMultipleFluidTanks(SUPPORTED_INPUT_FLUIDS,
		MAX_TANK_CAPACITY, true));
	addComponent(new ComponentInventory(this).size(5).relativeSlotFaces(0, Direction.values())
		.valid((slot, stack) -> slot < 2 || stack.getItem() instanceof ItemProcessorUpgrade).shouldSendInfo());
	addComponent(new ComponentProcessor(this).upgradeSlots(2, 3, 4).canProcess(component -> canProcessChemCryst(component))
		.process(component -> component.processFluid2ItemRecipe(component, Fluid2ItemRecipe.class))
		.requiredTicks(Constants.CHEMICALCRYSTALLIZER_REQUIRED_TICKS).usage(Constants.CHEMICALCRYSTALLIZER_USAGE_PER_TICK)
		.type(ComponentProcessorType.ObjectToObject).outputSlot(0));
	addComponent(new ComponentContainerProvider("container.chemicalcrystallizer")
		.createMenu((id, player) -> new ContainerChemicalCrystallizer(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
    }

    public boolean canProcessChemCryst(ComponentProcessor processor) {
	processor.consumeBucket(MAX_TANK_CAPACITY, SUPPORTED_INPUT_FLUIDS, 1);
	return processor.canProcessFluid2ItemRecipe(processor, Fluid2ItemRecipe.class, ElectrodynamicsRecipeInit.CHEMICAL_CRYSTALIZER_TYPE);
    }

    protected void tickClient(ComponentTickable tickable) {
	ComponentProcessor processor = getComponent(ComponentType.Processor);
	if (processor.operatingTicks > 0 && world.rand.nextDouble() < 0.15) {
	    Direction direction = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	    double d4 = world.rand.nextDouble();
	    double d5 = direction.getAxis() == Direction.Axis.X ? direction.getXOffset() * (direction.getXOffset() == -1 ? 0 : 1) : d4;
	    double d6 = world.rand.nextDouble();
	    double d7 = direction.getAxis() == Direction.Axis.Z ? direction.getZOffset() * (direction.getZOffset() == -1 ? 0 : 1) : d4;
	    world.addParticle(ParticleTypes.SMOKE, pos.getX() + d5, pos.getY() + d6, pos.getZ() + d7, 0.0D, 0.0D, 0.0D);
	}
	if (processor.operatingTicks > 0 && tickable.getTicks() % 200 == 0) {
	    SoundAPI.playSound(SoundRegister.SOUND_HUM.get(), SoundCategory.BLOCKS, 1, 1, pos);
	}
    }
}
