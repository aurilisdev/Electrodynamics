package electrodynamics.common.tile.electricitygrid;

import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import voltaic.api.electricity.ICapabilityElectrodynamic;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.prefab.utilities.object.TransferPack;
import voltaic.registers.VoltaicCapabilities;

public class TileCurrentRegulator extends GenericTile {

    private boolean isLocked = false;

    public static final BlockEntityUtils.MachineDirection OUTPUT = BlockEntityUtils.MachineDirection.FRONT;
    public static final BlockEntityUtils.MachineDirection INPUT = BlockEntityUtils.MachineDirection.BACK;

    public TileCurrentRegulator() {
        super(ElectrodynamicsTiles.TILE_CURRENTREGULATOR.get());
        addComponent(new ComponentElectrodynamic(this, true, true).receivePower(this::receivePower).getConnectedLoad(this::getConnectedLoad).setOutputDirections(OUTPUT).setInputDirections(INPUT).voltage(-1)
                //
                .getAmpacity(this::getAmpacity).getMinimumVoltage(this::getMinimumVoltage));
    }

    public TransferPack receivePower(TransferPack transfer, boolean debug) {

        if (isLocked) {
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

        TransferPack accepted = electro.receivePower(TransferPack.joulesVoltage(transfer.getJoules() * ElectroConstants.CURRENTREGULATOR_EFFICIENCY, transfer.getVoltage()), debug);

        isLocked = false;

        TransferPack adjusted = TransferPack.joulesVoltage(accepted.getJoules() / ElectroConstants.CURRENTREGULATOR_EFFICIENCY, accepted.getVoltage());

        double ampacityInTicks = electro.getAmpacity();

        if (ampacityInTicks < 0) {
            return adjusted;
        }

        double currentInTicks = adjusted.getAmpsInTicks();

        if (currentInTicks > ampacityInTicks) {

            adjusted = TransferPack.ampsVoltage(ampacityInTicks, adjusted.getVoltage());

        }

        return adjusted;
    }

    public TransferPack getConnectedLoad(ICapabilityElectrodynamic.LoadProfile lastEnergy, Direction dir) {

        if (isLocked) {
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

        isLocked = true;

        ICapabilityElectrodynamic electro = tile.getCapability(VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK, output.getOpposite()).orElse(CapabilityUtils.EMPTY_ELECTRO);

        if (electro == CapabilityUtils.EMPTY_ELECTRO) {
            isLocked = false;
            return TransferPack.EMPTY;
        }

        ICapabilityElectrodynamic.LoadProfile transformed = new ICapabilityElectrodynamic.LoadProfile(TransferPack.joulesVoltage(lastEnergy.lastUsage().getJoules() * ElectroConstants.CIRCUITBREAKER_EFFICIENCY, lastEnergy.lastUsage().getVoltage()), TransferPack.joulesVoltage(lastEnergy.maximumAvailable().getJoules() * ElectroConstants.CIRCUITBREAKER_EFFICIENCY, lastEnergy.maximumAvailable().getVoltage()));

        TransferPack returner = electro.getConnectedLoad(transformed, dir);

        isLocked = false;

        TransferPack adjusted = TransferPack.joulesVoltage(returner.getJoules() / ElectroConstants.CIRCUITBREAKER_EFFICIENCY, returner.getVoltage());

        double ampacityInTicks = electro.getAmpacity();

        if (ampacityInTicks < 0) {
            return adjusted;
        }

        double currentInTicks = adjusted.getAmpsInTicks();

        if (currentInTicks > ampacityInTicks) {

            adjusted = TransferPack.ampsVoltage(ampacityInTicks, adjusted.getVoltage());

        }

        return adjusted;
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

        if (electro == CapabilityUtils.EMPTY_ELECTRO) {
            isLocked = false;
            return -1;
        }
        double ampacity = electro.getAmpacity();
        isLocked = false;
        return ampacity;
    }

}
