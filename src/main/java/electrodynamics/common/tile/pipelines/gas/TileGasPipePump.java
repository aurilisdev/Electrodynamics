package electrodynamics.common.tile.pipelines.gas;

import org.jetbrains.annotations.NotNull;

import electrodynamics.common.inventory.container.tile.ContainerGasPipePump;
import electrodynamics.common.network.type.GasNetwork;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import voltaic.api.gas.IGasHandler;
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

public class TileGasPipePump extends GenericTile {

	public static final BlockEntityUtils.MachineDirection INPUT_DIR = BlockEntityUtils.MachineDirection.FRONT;
	public static final BlockEntityUtils.MachineDirection OUTPUT_DIR = BlockEntityUtils.MachineDirection.BACK;
	
	private boolean isLocked = false;

	public final SingleProperty<Integer> priority = property(new SingleProperty<>(PropertyTypes.INTEGER, "pumppriority", 0).onChange((prop, oldval) -> {

		if (level == null || level.isClientSide) {
			return;
		}

		BlockEntity entity = level.getBlockEntity(worldPosition.relative(getFacing()));

		if (entity instanceof TileGasPipe pipe) {
			GasNetwork network = pipe.getNetwork();

			if (network != null) {
				network.updateGasPipePumpStats(this, prop.getValue(), oldval);
			}
		}

	}));

	public TileGasPipePump(BlockPos pos, BlockState state) {
		super(ElectrodynamicsTiles.TILE_GASPIPEPUMP.get(), pos, state);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, false, true).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE).maxJoules(ElectroConstants.PIPE_PUMP_USAGE_PER_TICK * 10).setInputDirections(BlockEntityUtils.MachineDirection.LEFT));
		addComponent(new ComponentContainerProvider("gaspipepump", this).createMenu((id, inv) -> new ContainerGasPipePump(id, inv, getCoordsArray())));
	}

	public void tickServer(ComponentTickable tick) {

		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

		electro.joules(Math.max(electro.getJoulesStored() - ElectroConstants.PIPE_PUMP_USAGE_PER_TICK, 0));

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

            BlockEntity output = level.getBlockEntity(worldPosition.relative(side.getOpposite()));
            if (output == null) {
                return LazyOptional.of(() -> CapabilityUtils.EMPTY_GAS).cast();
            }
            
            isLocked = true;
            
            IGasHandler gas = output.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_BLOCK, side).orElse(CapabilityUtils.EMPTY_GAS);
            
            isLocked = false;
            
            return gas == CapabilityUtils.EMPTY_GAS ? LazyOptional.of(() -> CapabilityUtils.EMPTY_GAS).cast() : LazyOptional.of(() -> gas).cast();

        }
		
		return super.getCapability(cap, side);
	}

	public boolean isPowered() {
		return this.<ComponentElectrodynamic>getComponent(IComponentType.Electrodynamic).getJoulesStored() >= ElectroConstants.PIPE_PUMP_USAGE_PER_TICK;
	}

}
