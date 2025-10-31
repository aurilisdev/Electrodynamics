package electrodynamics.common.tile.pipelines.gas;

import org.jetbrains.annotations.Nullable;

import electrodynamics.common.inventory.container.tile.ContainerGasPipePump;
import electrodynamics.common.network.type.GasNetwork;
import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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
		addComponent(new ComponentElectrodynamic(this, false, true).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE).maxJoules(ElectrodynamicsConfig.INSTANCE.PIPE_PUMP_USAGE_PER_TICK.get() * 10).setInputDirections(BlockEntityUtils.MachineDirection.LEFT));
		addComponent(new ComponentContainerProvider("gaspipepump", this).createMenu((id, inv) -> new ContainerGasPipePump(id, inv, getCoordsArray())));
	}

	public void tickServer(ComponentTickable tick) {

		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

		electro.joules(Math.max(electro.getJoulesStored() - ElectrodynamicsConfig.INSTANCE.PIPE_PUMP_USAGE_PER_TICK.get(), 0));

	}
	
	@Override
	public @Nullable IGasHandler getGasHandlerCapability(@Nullable Direction side) {
	    if(side == null || isLocked) {
	        return null;
	    }
	    Direction facing = getFacing();

        if (side == BlockEntityUtils.getRelativeSide(facing, OUTPUT_DIR.mappedDir)) {
            return CapabilityUtils.EMPTY_GAS;
        }

        if (side == BlockEntityUtils.getRelativeSide(facing, INPUT_DIR.mappedDir)) {

            BlockEntity output = level.getBlockEntity(worldPosition.relative(side.getOpposite()));
            if (output == null) {
		return CapabilityUtils.EMPTY_GAS;
	    }

	    isLocked = true;

            IGasHandler gas = output.getLevel().getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_BLOCK, output.getBlockPos(), output.getBlockState(), output, side);
            
            isLocked = false;
            
            return gas == null ? CapabilityUtils.EMPTY_GAS : gas;

        }
        
        return null;
	}

	public boolean isPowered() {
		return this.<ComponentElectrodynamic>getComponent(IComponentType.Electrodynamic).getJoulesStored() >= ElectrodynamicsConfig.INSTANCE.PIPE_PUMP_USAGE_PER_TICK.get();
	}

}
