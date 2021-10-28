package electrodynamics.common.tile.generic;

import java.util.List;

import electrodynamics.api.capability.CapabilityUtils;
import electrodynamics.common.inventory.container.ContainerTankGeneric;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class TileGenericTank extends GenericTileTicking {

    public TileGenericTank(BlockEntityType<?> tile, int capacity, List<Fluid> validFluids, String name) {
	super(tile);
	addComponent(new ComponentTickable().tickCommon(this::tickCommon));
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(((ComponentFluidHandlerSimple) new ComponentFluidHandlerSimple(this)
		.relativeInput(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.UP).relativeOutput(Direction.WEST, Direction.DOWN)
		.addFluidTank(Fluids.EMPTY, capacity, true)).setValidFluids(validFluids));
	addComponent(new ComponentInventory(this).size(2).valid((slot, stack) -> CapabilityUtils.hasFluidItemCap(stack)));
	addComponent(new ComponentContainerProvider("container.tank" + name)
		.createMenu((id, player) -> new ContainerTankGeneric(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
    }

    public void tickCommon(ComponentTickable tick) {
	ComponentFluidHandlerSimple handler = (ComponentFluidHandlerSimple) getComponent(ComponentType.FluidHandler);
	ComponentInventory inv = getComponent(ComponentType.Inventory);
	ComponentDirection direction = getComponent(ComponentType.Direction);
	BlockPos face = getBlockPos().relative(direction.getDirection().getClockWise().getOpposite());
	BlockEntity faceTile = getLevel().getBlockEntity(face);
	ItemStack input = inv.getItem(0);
	ItemStack output = inv.getItem(1);
	// try to drain slot 0
	if (!input.isEmpty() && CapabilityUtils.hasFluidItemCap(input)) {
	    boolean isInputBucket = input.getItem() instanceof BucketItem;
	    FluidStack stack = CapabilityUtils.simDrain(input, Integer.MAX_VALUE);
	    FluidTank tank = handler.getTankFromFluid(stack.getFluid(), true);
	    int room = tank.getCapacity() - tank.getFluidAmount();
	    if (room > 0 && handler.isFluidValid(0, stack) && !isInputBucket) {
		handler.addFluidToTank(stack, true);
		CapabilityUtils.drain(input, stack);
	    } else if (room >= 1000 && handler.isFluidValid(0, stack) && isInputBucket) {
		handler.addFluidToTank(stack, true);
		inv.setItem(0, new ItemStack(Items.BUCKET, 1));

	    }
	}
	// try to empty to slot 1
	if (!output.isEmpty() && CapabilityUtils.hasFluidItemCap(output)) {
	    boolean isBucket = output.getItem() instanceof BucketItem;
	    FluidStack tankFluid = handler.getFluidInTank(0);
	    int amtTaken = CapabilityUtils.simFill(output, handler.getFluidInTank(0));
	    Fluid fluid = tankFluid.getFluid();
	    if (amtTaken > 0 && !isBucket) {
		CapabilityUtils.fill(output, new FluidStack(fluid, amtTaken));
		handler.drainFluidFromTank(new FluidStack(fluid, amtTaken), false);
	    } else if (amtTaken >= 1000 && isBucket && (fluid.isSame(Fluids.WATER) || fluid.isSame(Fluids.LAVA))) {
		handler.drainFluidFromTank(new FluidStack(fluid, amtTaken), false);
		if (fluid.isSame(Fluids.WATER)) {
		    inv.setItem(1, new ItemStack(Items.WATER_BUCKET, 1));
		} else {
		    inv.setItem(1, new ItemStack(Items.LAVA_BUCKET, 1));
		}
	    }
	}
	// try to output to pipe
	if (faceTile != null) {
	    LazyOptional<IFluidHandler> cap = faceTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,
		    direction.getDirection().getClockWise().getOpposite().getOpposite());
	    if (cap.isPresent()) {
		IFluidHandler iHandler = cap.resolve().get();
		for (Fluid fluid : handler.getValidOutputFluids()) {
		    if (handler.getTankFromFluid(fluid, false).getFluidAmount() > 0) {
			handler.getStackFromFluid(fluid, false).shrink(iHandler.fill(handler.getStackFromFluid(fluid, false), FluidAction.EXECUTE));
			break;
		    }
		}
	    }
	}

	// Output to tank below
	BlockPos pos = getBlockPos();
	BlockPos below = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());

	if (level.getBlockState(below).hasTileEntity()) {
	    BlockEntity tile = level.getBlockEntity(below);
	    if (tile instanceof TileGenericTank) {
		TileGenericTank tankBelow = (TileGenericTank) tile;
		ComponentFluidHandlerSimple belowHandler = tankBelow.getComponent(ComponentType.FluidHandler);
		ComponentFluidHandlerSimple thisHandler = getComponent(ComponentType.FluidHandler);
		FluidTank belowTank = belowHandler.getTankFromFluid(null, true);
		FluidStack thisTankFluid = thisHandler.getFluidInTank(0);

		if (belowHandler.isFluidValid(0, thisTankFluid)) {
		    int room = belowTank.getCapacity() - belowTank.getFluidAmount();
		    if (room > 0) {
			int amtTaken = room >= thisTankFluid.getAmount() ? thisTankFluid.getAmount() : room;
			FluidStack stack = new FluidStack(thisTankFluid.getFluid(), amtTaken);
			belowHandler.addFluidToTank(stack, true);
			thisHandler.drainFluidFromTank(stack, true);
		    }
		}

	    }
	}

	this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
    }

}
