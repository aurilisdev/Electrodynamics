package electrodynamics.common.tile.electricitygrid;

import org.jetbrains.annotations.NotNull;

import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.api.electricity.ICapabilityElectrodynamic;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.prefab.utilities.object.TransferPack;
import voltaic.registers.VoltaicCapabilities;

public class TileRelay extends GenericTile {

    private boolean recievedRedstoneSignal = false;

    private boolean isLocked = false;

    public static final BlockEntityUtils.MachineDirection OUTPUT = BlockEntityUtils.MachineDirection.FRONT;
    public static final BlockEntityUtils.MachineDirection INPUT = BlockEntityUtils.MachineDirection.BACK;

    public TileRelay(BlockPos worldPos, BlockState blockState) {
        super(ElectrodynamicsTiles.TILE_RELAY.get(), worldPos, blockState);
        addComponent(new ComponentElectrodynamic(this, true, true).receivePower(this::receivePower).getConnectedLoad(this::getConnectedLoad).setOutputDirections(OUTPUT).setInputDirections(INPUT)
                //
                .voltage(-1).getAmpacity(this::getAmpacity).getMinimumVoltage(this::getMinimumVoltage));
    }

    public TransferPack receivePower(TransferPack transfer, boolean debug) {
        if (recievedRedstoneSignal || isLocked) {
            return TransferPack.EMPTY;
        }
        Direction output = BlockEntityUtils.getRelativeSide(getFacing(), OUTPUT.mappedDir);

        BlockEntity tile = level.getBlockEntity(worldPosition.relative(output));

        if (tile == null) {
            return TransferPack.EMPTY;
        }

        isLocked = true;

        ICapabilityElectrodynamic electro = tile.getCapability(VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK, output.getOpposite()).orElse(CapabilityUtils.EMPTY_ELECTRO);

        if (electro == CapabilityUtils.EMPTY_ELECTRO) {
            isLocked = false;
            return TransferPack.EMPTY;
        }

        TransferPack accepted = electro.receivePower(TransferPack.joulesVoltage(transfer.getJoules() * ElectroConstants.RELAY_EFFICIENCY, transfer.getVoltage()), debug);

        isLocked = false;

        return TransferPack.joulesVoltage(accepted.getJoules() / ElectroConstants.RELAY_EFFICIENCY, accepted.getVoltage());
    }

    public TransferPack getConnectedLoad(ICapabilityElectrodynamic.LoadProfile lastEnergy, Direction dir) {

        if (recievedRedstoneSignal || isLocked) {
            return TransferPack.EMPTY;
        }

        Direction output = BlockEntityUtils.getRelativeSide(getFacing(), OUTPUT.mappedDir);

        if (dir != output.getOpposite()) {
            return TransferPack.EMPTY;
        }

        BlockEntity tile = level.getBlockEntity(worldPosition.relative(output));

        if (tile == null) {
            return TransferPack.EMPTY;
        }

        ICapabilityElectrodynamic.LoadProfile transformed = new ICapabilityElectrodynamic.LoadProfile(TransferPack.joulesVoltage(lastEnergy.lastUsage().getJoules() * ElectroConstants.RELAY_EFFICIENCY, lastEnergy.lastUsage().getVoltage()), TransferPack.joulesVoltage(lastEnergy.maximumAvailable().getJoules() * ElectroConstants.RELAY_EFFICIENCY, lastEnergy.maximumAvailable().getVoltage()));

        isLocked = true;

        ICapabilityElectrodynamic electro = tile.getCapability(VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK, dir).orElse(CapabilityUtils.EMPTY_ELECTRO);

        if (electro == CapabilityUtils.EMPTY_ELECTRO) {
            isLocked = false;
            return TransferPack.EMPTY;
        }

        TransferPack returner = electro.getConnectedLoad(transformed, dir);

        isLocked = false;
        return TransferPack.joulesVoltage(returner.getJoules() / ElectroConstants.RELAY_EFFICIENCY, returner.getVoltage());

    }

    public double getMinimumVoltage() {
        Direction facing = getFacing();
        if (isLocked) {
            return 0;
        }
        BlockEntity output = level.getBlockEntity(worldPosition.relative(facing));
        if (output == null) {
            return -1;
        }
        isLocked = true;

        ICapabilityElectrodynamic electro = output.getCapability(VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK, facing.getOpposite()).orElse(CapabilityUtils.EMPTY_ELECTRO);

        if (electro == CapabilityUtils.EMPTY_ELECTRO) {
            isLocked = false;
            return -1;
        }

        double minimumVoltage = electro.getMinimumVoltage();

        isLocked = false;
        return minimumVoltage;
    }

    public double getAmpacity() {
        Direction facing = getFacing();
        if (isLocked) {
            return 0;
        }
        BlockEntity output = level.getBlockEntity(worldPosition.relative(facing));
        if (output == null) {
            return -1;
        }
        isLocked = true;

        ICapabilityElectrodynamic electro = output.getCapability(VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK, facing.getOpposite()).orElse(CapabilityUtils.EMPTY_ELECTRO);

        if (electro == null) {
            isLocked = false;
            return -1;
        }
        double ampacity = electro.getAmpacity();

        isLocked = false;
        return ampacity;
    }

    @Override
	public void saveAdditional(@NotNull CompoundTag compound) {
		super.saveAdditional(compound);
		compound.putBoolean("hasredstonesignal", recievedRedstoneSignal);
	}

	@Override
	public void load(@NotNull CompoundTag compound) {
		super.load(compound);
		recievedRedstoneSignal = compound.getBoolean("hasredstonesignal");
	}

    @Override
    public void onNeightborChanged(BlockPos neighbor, boolean blockStateTrigger) {
        if (level.isClientSide) {
            return;
        }
        recievedRedstoneSignal = level.hasNeighborSignal(getBlockPos());
        if (BlockEntityUtils.isLit(this) ^ recievedRedstoneSignal) {
            BlockEntityUtils.updateLit(this, recievedRedstoneSignal);
            if (recievedRedstoneSignal) {
                level.playSound(null, getBlockPos(), SoundEvents.IRON_TRAPDOOR_OPEN, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else {
                level.playSound(null, getBlockPos(), SoundEvents.IRON_TRAPDOOR_CLOSE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public void onPlace(BlockState oldState, boolean isMoving) {
        super.onPlace(oldState, isMoving);
        if (level.isClientSide) {
            return;
        }
        recievedRedstoneSignal = level.hasNeighborSignal(getBlockPos());
        if (BlockEntityUtils.isLit(this) ^ recievedRedstoneSignal) {
            BlockEntityUtils.updateLit(this, recievedRedstoneSignal);
            if (recievedRedstoneSignal) {
                level.playSound(null, getBlockPos(), SoundEvents.IRON_TRAPDOOR_OPEN, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
    }

}
