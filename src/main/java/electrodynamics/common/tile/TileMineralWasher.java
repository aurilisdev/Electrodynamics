package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.ISubtype;
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
import electrodynamics.common.block.BlockOre;
import electrodynamics.common.blockitem.BlockItemDescriptable;
import electrodynamics.common.fluid.FluidMineral;
import electrodynamics.common.inventory.container.ContainerMineralWasher;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.common.item.subtype.SubtypeMineralFluid;
import electrodynamics.common.settings.Constants;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class TileMineralWasher extends GenericTileTicking {
    public static final int TANKCAPACITY_SULFURICACID = 5000;
    public static final int TANKCAPACITY_MINERAL = 5000;
    public static final int REQUIRED_SULFURICACID = 1000;

    public TileMineralWasher() {
	super(DeferredRegisters.TILE_MINERALWASHER.get());
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 4)
		.maxJoules(Constants.MINERALWASHER_USAGE_PER_TICK * 10));
	addComponent(new ComponentFluidHandler(this).relativeInput(Direction.values()).fluidTank(DeferredRegisters.fluidSulfuricAcid,
		TANKCAPACITY_SULFURICACID));
	ComponentFluidHandler fluids = getComponent(ComponentType.FluidHandler);
	for (FluidMineral fluid : DeferredRegisters.SUBTYPEMINERALFLUID_MAPPINGS.values()) {
	    fluids.fluidTank(fluid, TANKCAPACITY_MINERAL);
	}
	addComponent(new ComponentInventory(this).size(4).relativeSlotFaces(0, Direction.values())
		.valid((slot, stack) -> slot < 1 || stack.getItem() instanceof ItemProcessorUpgrade).shouldSendInfo());
	addComponent(new ComponentProcessor(this).upgradeSlots(1, 2, 3).usage(Constants.MINERALWASHER_USAGE_PER_TICK)
		.type(ComponentProcessorType.ObjectToObject).canProcess(this::canProcess).process(this::process)
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

    protected boolean canProcess(ComponentProcessor processor) {
	FluidMineral fluid = getFluidFromInput(processor);
	if (fluid == null) {
	    return false;
	}
	ComponentDirection direction = getComponent(ComponentType.Direction);
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	ComponentFluidHandler tank = getComponent(ComponentType.FluidHandler);
	BlockPos face = getPos().offset(direction.getDirection().getOpposite().rotateY());
	TileEntity faceTile = world.getTileEntity(face);
	if (faceTile != null) {
	    LazyOptional<IFluidHandler> cap = faceTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,
		    direction.getDirection().getOpposite().rotateY().getOpposite());
	    if (cap.isPresent()) {
		IFluidHandler handler = cap.resolve().get();
		if (handler.isFluidValid(0, tank.getStackFromFluid(fluid))) {
		    tank.getStackFromFluid(getFluidFromInput(processor)).shrink(handler.fill(tank.getStackFromFluid(fluid), FluidAction.EXECUTE));
		}
	    }
	}
	if (processor.getInput().isEmpty() || TANKCAPACITY_MINERAL < tank.getStackFromFluid(fluid).getAmount() + 1000) {
	    return false;
	}
	return electro.getJoulesStored() >= processor.getUsage() && !processor.getInput().isEmpty() && processor.getInput().getCount() > 0
		&& tank.getStackFromFluid(DeferredRegisters.fluidSulfuricAcid).getAmount() >= REQUIRED_SULFURICACID;
    }

    public void process(ComponentProcessor processor) {
	ComponentFluidHandler handler = getComponent(ComponentType.FluidHandler);
	ItemStack stack = processor.getInput();
	handler.getStackFromFluid(DeferredRegisters.fluidSulfuricAcid).shrink(REQUIRED_SULFURICACID);
	handler.getStackFromFluid(getFluidFromInput(processor)).grow(1000);
	stack.setCount(stack.getCount() - 1);
	this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
    }

    public static FluidMineral getFluidFromInput(ComponentProcessor processor) {
	ItemStack stack = processor.getInput();
	String name = "";
	if (stack.getItem() instanceof BlockItemDescriptable) {
	    BlockItemDescriptable descriptable = (BlockItemDescriptable) stack.getItem();
	    if (descriptable.getBlock() instanceof BlockOre) {
		BlockOre ore = (BlockOre) descriptable.getBlock();
		name = ore.ore.name();
	    }
	} else if (stack.getItem() == Items.GOLD_ORE) {
	    name = "gold";
	} else if (stack.getItem() == Items.IRON_ORE) {
	    name = "iron";
	} else if (DeferredRegisters.SUBTYPEITEM_MAPPINGS.values().contains(stack.getItem())) {
	    ISubtype sub = DeferredRegisters.ITEMSUBTYPE_MAPPINGS.get(stack.getItem());
	    if (sub instanceof SubtypeCrystal) {
		name = ((SubtypeCrystal) sub).name();
	    }
	}
	for (SubtypeMineralFluid fluid : SubtypeMineralFluid.values()) {
	    if (fluid.name().equalsIgnoreCase(name)) {
		return DeferredRegisters.SUBTYPEMINERALFLUID_MAPPINGS.get(fluid);
	    }
	}
	return null;
    }

    public static Item getItemFromMineral(FluidMineral mineral) {
	return DeferredRegisters.SUBTYPEITEM_MAPPINGS
		.get(SubtypeCrystal.valueOf(((SubtypeMineralFluid) DeferredRegisters.MINERALFLUIDSUBTYPE_MAPPINGS.get(mineral)).name()));
    }
}
