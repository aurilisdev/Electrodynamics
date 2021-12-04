package electrodynamics.common.tile.generic;

import electrodynamics.api.capability.CapabilityUtils;
import electrodynamics.common.inventory.container.ContainerTankGeneric;
import electrodynamics.prefab.tile.GenericTile;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class TileGenericTank extends GenericTile {

    public TileGenericTank(BlockEntityType<?> tile, int capacity, Fluid[] validFluids, String name, BlockPos pos, BlockState state) {
	super(tile, pos, state);
	addComponent(new ComponentTickable().tickServer(this::tickServer));
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentFluidHandlerSimple(this).relativeInput(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.UP)
		.relativeOutput(Direction.WEST, Direction.DOWN).setManualFluids(1, true, capacity, validFluids));
	addComponent(new ComponentInventory(this).size(2).valid((slot, stack) -> CapabilityUtils.hasFluidItemCap(stack)));
	addComponent(new ComponentContainerProvider("container.tank" + name)
		.createMenu((id, player) -> new ContainerTankGeneric(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
    }

    public void tickServer(ComponentTickable tick) {
	ComponentFluidHandlerSimple handler = (ComponentFluidHandlerSimple) getComponent(ComponentType.FluidHandler);
	ComponentInventory inv = getComponent(ComponentType.Inventory);
	ComponentDirection direction = getComponent(ComponentType.Direction);
	BlockPos face = getBlockPos().relative(direction.getDirection().getClockWise().getOpposite());
	BlockEntity faceTile = getLevel().getBlockEntity(face);
	ItemStack input = inv.getItem(0);
	ItemStack output = inv.getItem(1);
	// try to drain slot 0
	if (!input.isEmpty() && CapabilityUtils.hasFluidItemCap(input)) {
		FluidTank tank = handler.getInputTanks()[0];
		FluidStack containerFluid = CapabilityUtils.simDrain(input, Integer.MAX_VALUE);
		if (handler.getValidInputFluids().contains(containerFluid.getFluid()) && tank.isFluidValid(containerFluid)) {
		    int amtDrained = tank.fill(containerFluid, FluidAction.SIMULATE);
		    FluidStack drained = new FluidStack(containerFluid.getFluid(), amtDrained);
		    CapabilityUtils.drain(input, drained);
		    tank.fill(drained, FluidAction.EXECUTE);
		    if (input.getItem() instanceof BucketItem) {
		    	inv.setItem(0, new ItemStack(Items.BUCKET, 1));
		    }
	    }
	}
	// try to fill to slot 1
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
		boolean electroPipe = faceTile instanceof GenericTilePipe ? true : false;
		LazyOptional<IFluidHandler> cap = faceTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,
		    direction.getDirection().getClockWise().getOpposite().getOpposite());
	    if (cap.isPresent()) {
		IFluidHandler fHandler = cap.resolve().get();
		boolean outputFluid = false;
		for (FluidTank tank : handler.getOutputTanks()) {
			if (outputFluid) {
		    	break;
		    }
		    FluidStack tankFluid = tank.getFluid();
		    if(electroPipe) { 
		    	if(tank.getFluidAmount() > 0) {
		    		fHandler.fill(tankFluid, FluidAction.EXECUTE);
			    	tank.drain(tankFluid, FluidAction.EXECUTE);
		    	}
		    } else {
		    	int amtAccepted = fHandler.fill(tankFluid, FluidAction.SIMULATE);
			    FluidStack taken = new FluidStack(tankFluid.getFluid(), amtAccepted);
			    fHandler.fill(taken, FluidAction.EXECUTE);
			    tank.drain(taken, FluidAction.EXECUTE);
			    if (amtAccepted > 0) {
			    	outputFluid = true;
			    }
		    } 
		}
	    }
	}

	// Output to tank below
	BlockPos pos = getBlockPos();
	BlockPos below = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());

	if (level.getBlockState(below).hasBlockEntity()) {
	    BlockEntity tile = level.getBlockEntity(below);
	    if (tile instanceof TileGenericTank tankBelow) {
		ComponentFluidHandlerSimple belowHandler = tankBelow.getComponent(ComponentType.FluidHandler);
		ComponentFluidHandlerSimple thisHandler = getComponent(ComponentType.FluidHandler);
		FluidTank belowTank = belowHandler.getTankFromFluid(FluidStack.EMPTY.getFluid(), true);
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
