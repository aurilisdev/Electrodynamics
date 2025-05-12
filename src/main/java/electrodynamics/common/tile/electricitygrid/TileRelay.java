package electrodynamics.common.tile.electricitygrid;

import javax.annotation.Nonnull;

import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
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

    public TileRelay() {
        super(ElectrodynamicsTiles.TILE_RELAY.get());
        addComponent(new ComponentElectrodynamic(this, true, true).receivePower(this::receivePower).getConnectedLoad(this::getConnectedLoad).setOutputDirections(OUTPUT).setInputDirections(INPUT)
                //
                .voltage(-1).getAmpacity(this::getAmpacity).getMinimumVoltage(this::getMinimumVoltage));
    }

    public TransferPack receivePower(TransferPack transfer, boolean debug) {
        if (recievedRedstoneSignal || isLocked) {
            return TransferPack.EMPTY;
        }
        Direction output = BlockEntityUtils.getRelativeSide(getFacing(), OUTPUT.mappedDir);

        TileEntity tile = level.getBlockEntity(worldPosition.relative(output));

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

        TileEntity tile = level.getBlockEntity(worldPosition.relative(output));

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
        TileEntity output = level.getBlockEntity(worldPosition.relative(facing));
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
        TileEntity output = level.getBlockEntity(worldPosition.relative(facing));
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
	public CompoundNBT save(@Nonnull CompoundNBT compound) {
		compound.putBoolean("hasredstonesignal", recievedRedstoneSignal);
		return super.save(compound);
	}

	@Override
	public void load(BlockState state, @Nonnull CompoundNBT compound) {
		super.load(state, compound);
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
                level.playSound(null, getBlockPos(), SoundEvents.IRON_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
            } else {
                level.playSound(null, getBlockPos(), SoundEvents.IRON_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, 1.0F);
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
                level.playSound(null, getBlockPos(), SoundEvents.IRON_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
    }

}
