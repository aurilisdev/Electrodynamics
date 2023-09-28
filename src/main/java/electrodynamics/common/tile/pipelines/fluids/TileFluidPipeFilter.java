package electrodynamics.common.tile.pipelines.fluids;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import electrodynamics.common.inventory.container.tile.ContainerFluidPipeFilter;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TileFluidPipeFilter extends GenericTile {

	public static final Direction INPUT_DIR = Direction.SOUTH;
	public static final Direction OUTPUT_DIR = Direction.NORTH;
	
	private boolean isLocked = false;

	@SuppressWarnings("rawtypes")
	public final Property[] filteredFluids = {
			//
			property(new Property<>(PropertyType.Fluidstack, "fluidone", FluidStack.EMPTY)),
			//
			property(new Property<>(PropertyType.Fluidstack, "fluidtwo", FluidStack.EMPTY)),
			//
			property(new Property<>(PropertyType.Fluidstack, "fluidthree", FluidStack.EMPTY)),
			//
			property(new Property<>(PropertyType.Fluidstack, "fluidfour", FluidStack.EMPTY)) };

	public final Property<Boolean> isWhitelist = property(new Property<>(PropertyType.Boolean, "iswhitelist", false));

	public TileFluidPipeFilter(BlockPos worldPos, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_FLUIDPIPEFILTER.get(), worldPos, blockState);
		addComponent(new ComponentDirection(this));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentContainerProvider("container.fluidpipefilter", this).createMenu((id, inv) -> new ContainerFluidPipeFilter(id, inv, getCoordsArray())));
	}

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
		if (cap != ForgeCapabilities.FLUID_HANDLER || side == null) {
			return LazyOptional.empty();
		}

		Direction facing = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();

		if (side == BlockEntityUtils.getRelativeSide(facing, OUTPUT_DIR)) {
			return LazyOptional.of(() -> CapabilityUtils.EMPTY_FLUID).cast();
		}

		if (side == BlockEntityUtils.getRelativeSide(facing, INPUT_DIR)) {

			BlockEntity output = level.getBlockEntity(getBlockPos().relative(side.getOpposite()));

			if (output == null) {
				return LazyOptional.of(() -> CapabilityUtils.EMPTY_FLUID).cast();
			}

			LazyOptional<IFluidHandler> lazy = output.getCapability(ForgeCapabilities.FLUID_HANDLER, side);

			if (!lazy.isPresent()) {
				return LazyOptional.of(() -> CapabilityUtils.EMPTY_FLUID).cast();
			}

			return LazyOptional.of(() -> new FilteredFluidCap(lazy.resolve().get(), getFilteredFluids(), isWhitelist.get())).cast();

		}

		return LazyOptional.empty();
	}

	private List<Fluid> getFilteredFluids() {
		List<Fluid> fluids = new ArrayList<>();
		for (Property<FluidStack> prop : filteredFluids) {
			if (!prop.get().isEmpty()) {
				fluids.add(prop.get().getFluid());
			}
		}
		return fluids;
	}

	private class FilteredFluidCap implements IFluidHandler {

		private final IFluidHandler outputCap;
		private final List<Fluid> validFluids;
		private final boolean whitelist;

		private FilteredFluidCap(IFluidHandler outputCap, List<Fluid> validFluids, boolean whitelist) {
			this.outputCap = outputCap;
			this.validFluids = validFluids;
			this.whitelist = whitelist;
		}

		@Override
		public int getTanks() {
			if(isLocked) {
				return 0;
			}
			isLocked = true;
			int count = outputCap.getTanks();
			isLocked = false;
			return count;
		}

		@Override
		public @NotNull FluidStack getFluidInTank(int tank) {
			if(isLocked) {
				return FluidStack.EMPTY;
			}
			isLocked = true;
			FluidStack stack = outputCap.getFluidInTank(tank);
			isLocked = false;
			return stack;
		}

		@Override
		public int getTankCapacity(int tank) {
			if(isLocked) {
				return 0;
			}
			isLocked = true;
			int cap = outputCap.getTankCapacity(tank);
			isLocked = false;
			return cap;
		}

		@Override
		public boolean isFluidValid(int tank, @NotNull FluidStack stack) {

			if(isLocked) {
				return false;
			}
			
			if (whitelist) {

				if (validFluids.isEmpty()) {
					return false;
				}

				if (validFluids.contains(stack.getFluid())) {
					isLocked = true;
					boolean valid = outputCap.isFluidValid(tank, stack);
					isLocked = false;
					return valid;
				}

				return false;

			}

			if (validFluids.isEmpty() || !validFluids.contains(stack.getFluid())) {
				isLocked = true;
				boolean valid = outputCap.isFluidValid(tank, stack);
				isLocked = false;
				return valid;
			}

			return false;
		}

		@Override
		public int fill(FluidStack stack, FluidAction action) {

			if(isLocked) {
				return 0;
			}
			
			if (isFluidValid(getTanks(), stack)) {
				isLocked = true;
				int fill = outputCap.fill(stack, action);
				isLocked = false;
				return fill;
			}

			return 0;
		}

		@Override
		public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
			isLocked = true;
			FluidStack drain = outputCap.drain(resource, action);
			isLocked = false;
			return drain;
		}

		@Override
		public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
			isLocked = true;
			FluidStack drain = outputCap.drain(maxDrain, action);
			isLocked = false;
			return drain;
		}

	}

}
