package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.fluid.FluidMineral;
import electrodynamics.common.inventory.container.ContainerChemicalCrystallizer;
import electrodynamics.common.item.ItemProcessorUpgrade;
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
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;

public class TileChemicalCrystallizer extends GenericTileTicking {
    public static final int TANKCAPACITY_MINERAL = 5000;

    public boolean hasFluid;
    public FluidMineral firstFluid;

    public TileChemicalCrystallizer() {
	super(DeferredRegisters.TILE_CHEMICALCRYSTALLIZER.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentTickable().tickClient(this::tickClient).tickCommon(this::tickCommon));
	addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 2)
		.maxJoules(Constants.CHEMICALCRYSTALLIZER_USAGE_PER_TICK * 10));
	addComponent(new ComponentInventory(this).size(4).relativeSlotFaces(0, Direction.values())
		.valid((slot, stack) -> slot < 1 || stack.getItem() instanceof ItemProcessorUpgrade).shouldSendInfo());
	addComponent(new ComponentContainerProvider("container.chemicalcrystallizer")
		.createMenu((id, player) -> new ContainerChemicalCrystallizer(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	addComponent(new ComponentProcessor(this).upgradeSlots(1, 2, 3).canProcess(this::canProcess).process(this::process)
		.requiredTicks(Constants.CHEMICALCRYSTALLIZER_REQUIRED_TICKS).usage(Constants.CHEMICALCRYSTALLIZER_USAGE_PER_TICK)
		.type(ComponentProcessorType.ObjectToObject).outputSlot(0));
	ComponentFluidHandler fluids = new ComponentFluidHandler(this).relativeInput(Direction.values());
	for (FluidMineral fluid : DeferredRegisters.SUBTYPEMINERALFLUID_MAPPINGS.values()) {
	    fluids.fluidTank(fluid, TANKCAPACITY_MINERAL);
	}
	addComponent(fluids);
    }

    protected void tickCommon(ComponentTickable tickable) {
	hasFluid = false;
	ComponentFluidHandler fluids = getComponent(ComponentType.FluidHandler);
	for (FluidMineral fluid : DeferredRegisters.SUBTYPEMINERALFLUID_MAPPINGS.values()) {
	    if (!fluids.getStackFromFluid(fluid).isEmpty()) {
		hasFluid = true;
		firstFluid = fluid;
		break;
	    }
	}
    }

    protected void process(ComponentProcessor component) {
	ItemStack output = component.getOutput();
	if (!output.isEmpty()) {
	    output.setCount(output.getCount() + 1);
	} else {
	    component.output(new ItemStack(TileMineralWasher.getItemFromMineral(firstFluid)));
	}
	ComponentFluidHandler fluids = getComponent(ComponentType.FluidHandler);
	fluids.getStackFromFluid(firstFluid).shrink(200);
    }

    protected boolean canProcess(ComponentProcessor component) {
	FluidMineral in = TileMineralWasher.getFluidFromInput(component);
	ComponentFluidHandler fluids = getComponent(ComponentType.FluidHandler);
	return in != null && fluids.getStackFromFluid(in).getAmount() > 200 && component.getInput().getCount() < 64 || hasFluid;
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
