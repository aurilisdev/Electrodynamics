package electrodynamics.common.tile.pipelines.fluid;

import org.jetbrains.annotations.NotNull;

import electrodynamics.common.inventory.container.tile.ContainerFluidPipePump;
import electrodynamics.common.network.type.FluidNetwork;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileFluidPipePump extends GenericTile {

    public static final BlockEntityUtils.MachineDirection INPUT_DIR = BlockEntityUtils.MachineDirection.FRONT;
    public static final BlockEntityUtils.MachineDirection OUTPUT_DIR = BlockEntityUtils.MachineDirection.BACK;

    private boolean isLocked = false;

    public final SingleProperty<Integer> priority = property(new SingleProperty<>(PropertyTypes.INTEGER, "pumppriority", 0).onChange((prop, oldval) -> {

        if (level == null || level.isClientSide) {
            return;
        }

        BlockEntity entity = level.getBlockEntity(worldPosition.relative(getFacing()));

        if (entity != null && entity instanceof TileFluidPipe pipe) {
            FluidNetwork network = pipe.getNetwork();

            if (network != null) {
                network.updateFluidPipePumpStats(this, prop.getValue(), oldval);
            }
        }

    }));

    public TileFluidPipePump(BlockPos pos, BlockState state) {
        super(ElectrodynamicsTiles.TILE_FLUIDPIPEPUMP.get(), pos, state);
        addComponent(new ComponentTickable(this).tickServer(this::tickServer));
        addComponent(new ComponentPacketHandler(this));
        addComponent(new ComponentElectrodynamic(this, false, true).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE).maxJoules(ElectroConstants.PIPE_PUMP_USAGE_PER_TICK * 10).setInputDirections(BlockEntityUtils.MachineDirection.LEFT));
        addComponent(new ComponentContainerProvider("fluidpipepump", this).createMenu((id, inv) -> new ContainerFluidPipePump(id, inv, getCoordsArray())));
    }

    public void tickServer(ComponentTickable tick) {

        ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

        electro.joules(Math.max(electro.getJoulesStored() - ElectroConstants.PIPE_PUMP_USAGE_PER_TICK, 0));

    }
    
    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
    	if(cap != ForgeCapabilities.FLUID_HANDLER || side == null || isLocked) {
    		return super.getCapability(cap, side);
    	}
    	
    	Direction facing = getFacing();

        if (side == BlockEntityUtils.getRelativeSide(facing, OUTPUT_DIR.mappedDir)) {
            return LazyOptional.of(() -> CapabilityUtils.EMPTY_FLUID).cast();
        }

        if (side == BlockEntityUtils.getRelativeSide(facing, INPUT_DIR.mappedDir)) {

            BlockEntity output = level.getBlockEntity(worldPosition.relative(side.getOpposite()));
            if (output == null) {
                return LazyOptional.of(() -> CapabilityUtils.EMPTY_FLUID).cast();
            }

            isLocked = true;

            IFluidHandler fluid = output.getCapability(ForgeCapabilities.FLUID_HANDLER, side).orElse(CapabilityUtils.EMPTY_FLUID);

            isLocked = false;

            return fluid == CapabilityUtils.EMPTY_FLUID ? LazyOptional.of(() -> CapabilityUtils.EMPTY_FLUID).cast() : LazyOptional.of(() -> fluid).cast();

        }

    	
    	return LazyOptional.empty();
    }

    public boolean isPowered() {
        return this.<ComponentElectrodynamic>getComponent(IComponentType.Electrodynamic).getJoulesStored() >= ElectroConstants.PIPE_PUMP_USAGE_PER_TICK;
    }

}
