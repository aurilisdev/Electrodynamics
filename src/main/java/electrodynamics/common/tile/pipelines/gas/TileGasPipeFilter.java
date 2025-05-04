package electrodynamics.common.tile.pipelines.gas;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import electrodynamics.common.inventory.container.tile.ContainerGasPipeFilter;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import voltaic.api.gas.Gas;
import voltaic.api.gas.GasAction;
import voltaic.api.gas.GasStack;
import voltaic.api.gas.IGasHandler;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileGasPipeFilter extends GenericTile {

    public static final BlockEntityUtils.MachineDirection INPUT_DIR = BlockEntityUtils.MachineDirection.FRONT;
    public static final BlockEntityUtils.MachineDirection OUTPUT_DIR = BlockEntityUtils.MachineDirection.BACK;

    private boolean isLocked = false;

    public final SingleProperty[] filteredGases = {
            //
            property(new SingleProperty<>(PropertyTypes.GAS_STACK, "gasone", GasStack.EMPTY)),
            //
            property(new SingleProperty<>(PropertyTypes.GAS_STACK, "gastwo", GasStack.EMPTY)),
            //
            property(new SingleProperty<>(PropertyTypes.GAS_STACK, "gasthree", GasStack.EMPTY)),
            //
            property(new SingleProperty<>(PropertyTypes.GAS_STACK, "gasfour", GasStack.EMPTY)) };

    public final SingleProperty<Boolean> isWhitelist = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "iswhitelist", false));

    public TileGasPipeFilter(BlockPos worldPos, BlockState blockState) {
        super(ElectrodynamicsTiles.TILE_GASPIPEFILTER.get(), worldPos, blockState);
        addComponent(new ComponentPacketHandler(this));
        addComponent(new ComponentContainerProvider("gaspipefilter", this).createMenu((id, inv) -> new ContainerGasPipeFilter(id, inv, getCoordsArray())));
    }
    
    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
    	if(cap != VoltaicCapabilities.CAPABILITY_GASHANDLER_BLOCK || side == null || isLocked) {
    		return LazyOptional.empty();
    	}
    	
    	Direction facing = getFacing();

        if (side == BlockEntityUtils.getRelativeSide(facing, OUTPUT_DIR.mappedDir)) {
            return LazyOptional.of(() -> CapabilityUtils.EMPTY_GAS).cast();
        }

        if (side == BlockEntityUtils.getRelativeSide(facing, INPUT_DIR.mappedDir)) {

            BlockEntity output = level.getBlockEntity(getBlockPos().relative(side.getOpposite()));

            if (output == null) {
                return LazyOptional.of(() -> CapabilityUtils.EMPTY_GAS).cast();
            }

            isLocked = true;

            IGasHandler gas = output.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_BLOCK, side).orElse(CapabilityUtils.EMPTY_GAS);

            isLocked = false;

            return gas == CapabilityUtils.EMPTY_GAS ? LazyOptional.of(() -> CapabilityUtils.EMPTY_GAS).cast() : LazyOptional.of(() -> new FilteredGasCap(gas, getFilteredGases(), isWhitelist.getValue())).cast();

        }
        
    	return LazyOptional.empty();
    }

    private List<Gas> getFilteredGases() {
        List<Gas> gases = new ArrayList<>();

        for (SingleProperty<GasStack> prop : filteredGases) {
            if (!prop.getValue().isEmpty()) {
                gases.add(prop.getValue().getGas());
            }
        }

        return gases;
    }

    private class FilteredGasCap implements IGasHandler {

        private final IGasHandler outputCap;
        private final List<Gas> validGases;
        private final boolean whitelist;

        private FilteredGasCap(IGasHandler outputCap, List<Gas> validGases, boolean whitelist) {
            this.outputCap = outputCap;
            this.validGases = validGases;
            this.whitelist = whitelist;
        }

        @Override
        public int getTanks() {
            if (isLocked) {
                return 0;
            }
            isLocked = true;
            int count = outputCap.getTanks();
            isLocked = false;
            return count;
        }

        @Override
        public GasStack getGasInTank(int tank) {
            if (isLocked) {
                return GasStack.EMPTY;
            }
            isLocked = true;
            GasStack stack = outputCap.getGasInTank(tank);
            isLocked = false;
            return stack;
        }

        @Override
        public int getTankCapacity(int tank) {
            if (isLocked) {
                return 0;
            }
            isLocked = true;
            int cap = outputCap.getTankCapacity(tank);
            isLocked = false;
            return cap;
        }

        @Override
        public int getTankMaxTemperature(int tank) {
            if (isLocked) {
                return 0;
            }
            isLocked = true;
            int temp = outputCap.getTankMaxTemperature(tank);

            isLocked = false;
            return temp;
        }

        @Override
        public int getTankMaxPressure(int tank) {
            if (isLocked) {
                return 0;
            }
            isLocked = true;
            int pres = outputCap.getTankMaxPressure(tank);
            isLocked = false;
            return pres;
        }

        @Override
        public boolean isGasValid(int tank, GasStack gas) {

            if (isLocked) {
                return false;
            }

            if (whitelist) {

                if (validGases.isEmpty()) {
                    return false;
                }

                if (validGases.contains(gas.getGas())) {
                    isLocked = true;
                    boolean valid = outputCap.isGasValid(tank, gas);

                    isLocked = false;
                    return valid;
                }

                return false;

            }

            if (validGases.isEmpty() || !validGases.contains(gas.getGas())) {

                isLocked = true;
                boolean valid = outputCap.isGasValid(tank, gas);

                isLocked = false;

                return valid;
            }

            return false;
        }

        @Override
        public int fill(GasStack gas, GasAction action) {
            if (isLocked) {
                return 0;
            }
            for(int i = 0; i < outputCap.getTanks(); i++){
                if (isGasValid(i, gas)) {
                    isLocked = true;
                    int fill = outputCap.fill(gas, action);

                    isLocked = false;
                    return fill;
                }
            }

            return 0;
        }

        @Override
        public GasStack drain(GasStack gas, GasAction action) {
            if (isLocked) {
                return GasStack.EMPTY;
            }
            isLocked = true;
            GasStack drain = outputCap.drain(gas, action);

            isLocked = false;
            return drain;
        }

        @Override
        public GasStack drain(int maxFill, GasAction action) {
            if (isLocked) {
                return GasStack.EMPTY;
            }
            isLocked = true;
            GasStack drain = outputCap.drain(maxFill, action);
            isLocked = false;
            return drain;
        }

        @Override
        public int heat(int tank, int deltaTemperature, GasAction action) {
            if (isLocked) {
                return -1;
            }
            isLocked = true;
            int heat = outputCap.heat(tank, deltaTemperature, action);

            isLocked = false;
            return heat;
        }

        @Override
        public int bringPressureTo(int tank, int atm, GasAction action) {
            if (isLocked) {
                return -1;
            }
            isLocked = true;
            int pres = outputCap.bringPressureTo(tank, atm, action);

            isLocked = false;
            return pres;
        }

    }

}
