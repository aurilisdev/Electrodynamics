package electrodynamics.common.tile.pipelines.fluid;

import java.util.Set;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Lists;

import electrodynamics.common.network.type.FluidNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import voltaic.api.network.cable.type.IFluidPipe;
import voltaic.prefab.tile.types.GenericRefreshingConnectTile;

public abstract class GenericTileFluidPipe extends GenericRefreshingConnectTile<IFluidPipe, GenericTileFluidPipe, FluidNetwork> {

    private final IFluidHandler[] handler = new IFluidHandler[6];
    
    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
    	if(cap == ForgeCapabilities.FLUID_HANDLER && side != null) {
    		return LazyOptional.of(() -> handler[side.ordinal()]).cast();
    	}
    	return LazyOptional.empty();
    }
    
    protected GenericTileFluidPipe(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
        for (Direction dir : Direction.values()) {
            handler[dir.ordinal()] = new IFluidHandler() {

                @Override
                public int getTanks() {
                    return 1;
                }

                @Override
                public FluidStack getFluidInTank(int tank) {
                    return new FluidStack(Fluids.WATER, 0);
                }

                @Override
                public int getTankCapacity(int tank) {
                    return 0;
                }

                @Override
                public boolean isFluidValid(int tank, FluidStack stack) {
                    return stack != null;
                }

                @Override
                public int fill(FluidStack resource, FluidAction action) {
                    if (action == FluidAction.SIMULATE || getNetwork() == null || resource.isEmpty()) {
                        return 0;
                    }
                    return getNetwork().emit(resource, Lists.newArrayList(level.getBlockEntity(new BlockPos(worldPosition).relative(dir))), false).getAmount();
                }

                @Override
                public FluidStack drain(FluidStack resource, FluidAction action) {
                    return FluidStack.EMPTY;
                }

                @Override
                public FluidStack drain(int maxDrain, FluidAction action) {
                    return FluidStack.EMPTY;
                }
            };
        }
    }

    @Override
    public FluidNetwork createInstance(Set<FluidNetwork> fluidNetworks) {
        return new FluidNetwork(fluidNetworks);
    }

    @Override
    public FluidNetwork createInstanceConductor(Set<GenericTileFluidPipe> genericTileFluidPipes) {
        return new FluidNetwork(genericTileFluidPipes);
    }

    @Override
    public double getMaxTransfer() {
        return getCableType().getMaxTransfer();
    }

    @Override
    public void destroyViolently() {

    }
}
