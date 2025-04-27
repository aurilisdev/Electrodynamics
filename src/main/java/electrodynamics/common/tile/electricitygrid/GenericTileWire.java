package electrodynamics.common.tile.electricitygrid;

import java.util.ArrayList;
import java.util.Set;

import org.jetbrains.annotations.NotNull;

import electrodynamics.common.network.type.ElectricNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import voltaic.api.electricity.ICapabilityElectrodynamic;
import voltaic.api.network.cable.type.IWire;
import voltaic.prefab.tile.types.GenericRefreshingConnectTile;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.prefab.utilities.object.TransferPack;
import voltaic.registers.VoltaicCapabilities;

public abstract class GenericTileWire extends GenericRefreshingConnectTile<IWire, GenericTileWire, ElectricNetwork> {

    private final ICapabilityElectrodynamic[] handler = new ICapabilityElectrodynamic[6];

    protected GenericTileWire(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
        for (Direction dir : Direction.values()) {
            handler[dir.ordinal()] = new ICapabilityElectrodynamic() {
                @Override
                public double getMaxJoulesStored() {
                    return 0;
                }

                @Override
                public double getJoulesStored() {
                    return 0;
                }

                @Override
                public double getVoltage() {
                    return getNetwork().getVoltage();
                }

                @Override
                public double getMinimumVoltage() {
                    return getNetwork().getMinimumVoltage();
                }

                @Override
                public TransferPack receivePower(TransferPack transfer, boolean debug) {
                    ArrayList<BlockEntity> ignored = new ArrayList<>();
                    BlockEntity entity = level.getBlockEntity(new BlockPos(worldPosition).relative(dir));
                    if (entity == null) {
                        return TransferPack.EMPTY;
                    }

                    ICapabilityElectrodynamic electro = entity.getCapability(VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK, dir.getOpposite()).orElse(CapabilityUtils.EMPTY_ELECTRO);

                    boolean isReciever = electro != CapabilityUtils.EMPTY_ELECTRO && electro.isEnergyReceiver();
                    ignored.add(entity);
                    if (!debug) {
                        getNetwork().addProducer(ignored.get(0), transfer.getVoltage(), isReciever);
                    }
                    return getNetwork().receivePower(transfer, debug);
                }

                @Override
                public void onChange() {

                }

                @Override
                public TransferPack getConnectedLoad(LoadProfile loadProfile, Direction dir) {
                    return getNetwork().getConnectedLoad(loadProfile, dir);
                }

                @Override
                public double getMaximumVoltage() {
                    return getNetwork().getActiveVoltage();
                }

                @Override
                public double getAmpacity() {
                    return getNetwork().getAmpacity();
                }

                @Override
                public boolean isEnergyReceiver() {
                    return getNetwork().isEnergyReceiver();
                }

                @Override
                public boolean isEnergyProducer() {
                    return getNetwork().isEnergyProducer();
                }

                @Override
                public void setJoulesStored(double joules) {
                    getNetwork().setJoulesStored(joules);
                }

            };
        }
    }
    
    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
    	if(cap == VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK && side != null) {
    		return LazyOptional.of(() -> handler[side.ordinal()]).cast();
    	}
    	return LazyOptional.empty();
    }
    
    @Override
    public void destroyViolently() {
        level.setBlockAndUpdate(worldPosition, Blocks.FIRE.defaultBlockState());
    }

    @Override
    public double getMaxTransfer() {
        return getCableType().getAmpacity();
    }

    @Override
    public ElectricNetwork createInstance(Set<ElectricNetwork> electricNetworks) {
        return new ElectricNetwork(electricNetworks);
    }

    @Override
    public ElectricNetwork createInstanceConductor(Set<GenericTileWire> genericTileWires) {
        return new ElectricNetwork(genericTileWires);
    }

    public abstract IWire.IWireColor getWireColor();

}
