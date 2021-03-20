package electrodynamics.common.tile;

import java.util.HashMap;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.api.tile.GenericTileTicking;
import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentContainerProvider;
import electrodynamics.api.tile.components.type.ComponentDirection;
import electrodynamics.api.tile.components.type.ComponentElectrodynamic;
import electrodynamics.api.tile.components.type.ComponentFluidHandler;
import electrodynamics.api.tile.components.type.ComponentInventory;
import electrodynamics.api.tile.components.type.ComponentPacketHandler;
import electrodynamics.api.tile.components.type.ComponentProcessor;
import electrodynamics.api.tile.components.type.ComponentProcessorType;
import electrodynamics.api.tile.components.type.ComponentTickable;
import electrodynamics.common.inventory.container.ContainerFermentationPlant;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.settings.Constants;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
    public static HashMap<Item, Integer> RECIPE_MAPPINGS = new HashMap<>();
    static {
	RECIPE_MAPPINGS.put(Items.WHEAT_SEEDS, 9);
	RECIPE_MAPPINGS.put(Items.PUMPKIN_SEEDS, 9);
	RECIPE_MAPPINGS.put(Items.MELON_SEEDS, 9);
	RECIPE_MAPPINGS.put(Items.BEETROOT_SEEDS, 9);
	RECIPE_MAPPINGS.put(Items.CARROT, 12);
	RECIPE_MAPPINGS.put(Items.RED_MUSHROOM, 11);
	RECIPE_MAPPINGS.put(Items.BROWN_MUSHROOM, 11);
	RECIPE_MAPPINGS.put(Items.POTATO, 13);
	RECIPE_MAPPINGS.put(Items.WHEAT, 13);
	RECIPE_MAPPINGS.put(Items.PUMPKIN, 25);
	RECIPE_MAPPINGS.put(Items.MELON_SLICE, 3);
	RECIPE_MAPPINGS.put(Items.SUGAR_CANE, 15);
    }

    public static final int TANKCAPACITY_WATER = 5000;
    public static final int TANKCAPACITY_ETHANOL = 100;
    public static final int REQUIRED_WATER_CAP = 1000;

    public TileFermentationPlant() {
	super(DeferredRegisters.TILE_FERMENTATIONPLANT.get());
	addComponent(new ComponentTickable().addTickClient(this::tickClient));
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentElectrodynamic(this).addInputDirection(Direction.DOWN).setVoltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE)
		.setMaxJoules(Constants.FERMENTATIONPLANT_USAGE_PER_TICK * 10));
	addComponent(new ComponentFluidHandler(this).addRelativeInputDirection(Direction.EAST).addFluidTank(Fluids.WATER, TANKCAPACITY_WATER)
		.addFluidTank(DeferredRegisters.fluidEthanol, TANKCAPACITY_ETHANOL).addRelativeOutputDirection(Direction.WEST));
	addComponent(new ComponentInventory().setInventorySize(5).addSlotsOnFace(Direction.UP, 0).addSlotsOnFace(Direction.DOWN, 1)
		.addRelativeSlotsOnFace(Direction.EAST, 0)
		.setItemValidPredicate((slot, stack) -> slot < 2 || stack.getItem() instanceof ItemProcessorUpgrade));
	addComponent(new ComponentProcessor(this).addUpgradeSlots(2, 3, 4).setJoulesPerTick(Constants.FERMENTATIONPLANT_USAGE_PER_TICK)
		.setType(ComponentProcessorType.ObjectToObject).setCanProcess(this::canProcess).setProcess(this::process)
		.setRequiredTicks(Constants.FERMENTATIONPLANT_REQUIRED_TICKS));
	addComponent(new ComponentContainerProvider("container.fermentationplant").setCreateMenuFunction(
		(id, player) -> new ContainerFermentationPlant(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
	return super.getRenderBoundingBox().grow(1);
    }

    protected void tickClient(ComponentTickable tickable) {
	if (this.<ComponentProcessor>getComponent(ComponentType.Processor).operatingTicks > 0 && world.rand.nextDouble() < 0.15) {
	    world.addParticle(ParticleTypes.SMOKE, pos.getX() + world.rand.nextDouble(), pos.getY() + world.rand.nextDouble() * 0.4 + 0.5,
		    pos.getZ() + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
	}
    }

    protected boolean canProcess(ComponentProcessor processor) {
	ComponentDirection direction = getComponent(ComponentType.Direction);
	ComponentInventory inv = getComponent(ComponentType.Inventory);
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
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
	ItemStack bucketStack = inv.getStackInSlot(1);
	if (!bucketStack.isEmpty() && bucketStack.getCount() > 0 && bucketStack.getItem() == Items.WATER_BUCKET
		&& tank.getStackFromFluid(Fluids.WATER).getAmount() <= TANKCAPACITY_WATER - 1000) {
	    inv.setInventorySlotContents(1, new ItemStack(Items.BUCKET));
	    tank.getStackFromFluid(Fluids.WATER).setAmount(Math.min(tank.getStackFromFluid(Fluids.WATER).getAmount() + 1000, TANKCAPACITY_WATER));
	}

	if (REQUIRED_WATER_CAP <= 0 || TANKCAPACITY_ETHANOL < tank.getStackFromFluid(DeferredRegisters.fluidEthanol).getAmount()
		+ RECIPE_MAPPINGS.getOrDefault(processor.getInput().getItem(), Integer.MAX_VALUE)) {
	    return false;
	}
	return electro.getJoulesStored() >= processor.getJoulesPerTick() && !processor.getInput().isEmpty() && processor.getInput().getCount() > 0
		&& tank.getStackFromFluid(Fluids.WATER).getAmount() >= 2000;
    }

    public void process(ComponentProcessor processor) {
	ComponentFluidHandler handler = getComponent(ComponentType.FluidHandler);
	ItemStack stack = processor.getInput();
	stack.setCount(stack.getCount() - 1);
	handler.getStackFromFluid(Fluids.WATER).shrink(REQUIRED_WATER_CAP);
	handler.getStackFromFluid(DeferredRegisters.fluidEthanol).grow(RECIPE_MAPPINGS.get(stack.getItem()));
	this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
    }
}
