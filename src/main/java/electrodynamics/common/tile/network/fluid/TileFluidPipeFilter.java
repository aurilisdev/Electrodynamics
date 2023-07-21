package electrodynamics.common.tile.network.fluid;

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
import electrodynamics.prefab.tile.components.type.ComponentInventory;
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
		addComponent(new ComponentInventory(this));
		addComponent(new ComponentContainerProvider("container.fluidpipefilter", this).createMenu((id, inv) -> new ContainerFluidPipeFilter(id, inv, getComponent(ComponentType.Inventory), getCoordsArray())));
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
			return outputCap.getTanks();
		}

		@Override
		public @NotNull FluidStack getFluidInTank(int tank) {
			return outputCap.getFluidInTank(tank);
		}

		@Override
		public int getTankCapacity(int tank) {
			return outputCap.getTankCapacity(tank);
		}

		@Override
		public boolean isFluidValid(int tank, @NotNull FluidStack stack) {

			if (whitelist) {

				if (validFluids.isEmpty()) {
					return false;
				}

				if (validFluids.contains(stack.getFluid())) {
					return outputCap.isFluidValid(tank, stack);
				}

				return false;

			}

			if (validFluids.isEmpty() || !validFluids.contains(stack.getFluid())) {
				return outputCap.isFluidValid(tank, stack);
			}

			return false;
		}

		@Override
		public int fill(FluidStack stack, FluidAction action) {

			if (isFluidValid(getTanks(), stack)) {
				return outputCap.fill(stack, action);
			}

			return 0;
		}

		@Override
		public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
			return outputCap.drain(resource, action);
		}

		@Override
		public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
			return outputCap.drain(maxDrain, action);
		}

	}

}
