package electrodynamics.common.tile.electricitygrid;

import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.api.electricity.ICapabilityElectrodynamic;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.object.TransferPack;
import voltaic.registers.VoltaicCapabilities;

public class TileCurrentRegulator extends GenericTile {

    private boolean isLocked = false;

    public static final BlockEntityUtils.MachineDirection OUTPUT = BlockEntityUtils.MachineDirection.FRONT;
    public static final BlockEntityUtils.MachineDirection INPUT = BlockEntityUtils.MachineDirection.BACK;

    public TileCurrentRegulator(BlockPos worldPos, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_CURRENTREGULATOR.get(), worldPos, blockState);
	addComponent(new ComponentElectrodynamic(this, true, true).receivePower(this::receivePower)
		.getConnectedLoad(this::getConnectedLoad).setOutputDirections(OUTPUT).setInputDirections(INPUT)
		.voltage(-1)
		//
		.getAmpacity(this::getAmpacity).getMinimumVoltage(this::getMinimumVoltage));
    }

    public TransferPack receivePower(TransferPack transfer, boolean debug) {
	Level level = this.level;
	if (level == null || isLocked)
	    return TransferPack.EMPTY;

	Direction output = BlockEntityUtils.getRelativeSide(getFacing(), OUTPUT.mappedDir);
	BlockEntity tile = level.getBlockEntity(worldPosition.relative(output));
	if (tile == null)
	    return TransferPack.EMPTY;

	isLocked = true;

	ICapabilityElectrodynamic electro = level.getCapability(VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK,
		tile.getBlockPos(), tile.getBlockState(), tile, output.getOpposite());
	if (electro == null) {
	    isLocked = false;
	    return TransferPack.EMPTY;
	}

	TransferPack accepted = electro.receivePower(TransferPack.joulesVoltage(
		transfer.getJoules() * ElectrodynamicsConfig.INSTANCE.CURRENTREGULATOR_EFFICIENCY.get(),
		transfer.getVoltage()), debug);

	isLocked = false;

	TransferPack adjusted = TransferPack.joulesVoltage(
		accepted.getJoules() / ElectrodynamicsConfig.INSTANCE.CURRENTREGULATOR_EFFICIENCY.get(),
		accepted.getVoltage());

	double ampacity = electro.getAmpacity();

	if (ampacity < 0)
	    return adjusted;

	if (adjusted.getAmpsInTicks() > ampacity) {
	    adjusted = TransferPack.ampsVoltage(ampacity, adjusted.getVoltage());
	}

	return adjusted;
    }

    public TransferPack getConnectedLoad(ICapabilityElectrodynamic.LoadProfile lastEnergy, Direction dir) {
	Level level = this.level;
	if (level == null || isLocked)
	    return TransferPack.EMPTY;

	Direction output = BlockEntityUtils.getRelativeSide(getFacing(), OUTPUT.mappedDir);
	if (dir != output.getOpposite())
	    return TransferPack.EMPTY;

	BlockEntity tile = level.getBlockEntity(worldPosition.relative(output));
	if (tile == null)
	    return TransferPack.EMPTY;

	isLocked = true;

	ICapabilityElectrodynamic electro = level.getCapability(VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK,
		tile.getBlockPos(), tile.getBlockState(), tile, output.getOpposite());

	if (electro == null) {
	    isLocked = false;
	    return TransferPack.EMPTY;
	}

	ICapabilityElectrodynamic.LoadProfile transformed = new ICapabilityElectrodynamic.LoadProfile(
		TransferPack.joulesVoltage(
			lastEnergy.lastUsage().getJoules()
				* ElectrodynamicsConfig.INSTANCE.CIRCUITBREAKER_EFFICIENCY.get(),
			lastEnergy.lastUsage().getVoltage()),
		TransferPack.joulesVoltage(
			lastEnergy.maximumAvailable().getJoules()
				* ElectrodynamicsConfig.INSTANCE.CIRCUITBREAKER_EFFICIENCY.get(),
			lastEnergy.maximumAvailable().getVoltage()));

	TransferPack returner = electro.getConnectedLoad(transformed, dir);

	isLocked = false;

	TransferPack adjusted = TransferPack.joulesVoltage(
		returner.getJoules() / ElectrodynamicsConfig.INSTANCE.CIRCUITBREAKER_EFFICIENCY.get(),
		returner.getVoltage());

	double ampacity = electro.getAmpacity();

	if (ampacity < 0)
	    return adjusted;

	if (adjusted.getAmpsInTicks() > ampacity) {
	    adjusted = TransferPack.ampsVoltage(ampacity, adjusted.getVoltage());
	}

	return adjusted;
    }

    public double getMinimumVoltage() {
	Direction facing = getFacing();
	if (isLocked)
	    return 0;

	Level level = this.level;
	if (level == null)
	    return 0;

	BlockEntity output = level.getBlockEntity(worldPosition.relative(facing));
	if (output == null)
	    return -1;
	isLocked = true;

	ICapabilityElectrodynamic electro = level.getCapability(VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK,
		output.getBlockPos(), output.getBlockState(), output, facing.getOpposite());

	if (electro == null) {
	    isLocked = false;
	    return -1;
	}

	double minimumVoltage = electro.getMinimumVoltage();
	isLocked = false;
	return minimumVoltage;
    }

    public double getAmpacity() {
	Direction facing = getFacing();
	if (isLocked)
	    return 0;

	Level level = this.level;
	if (level == null)
	    return 0;

	BlockEntity output = level.getBlockEntity(worldPosition.relative(facing));
	if (output == null)
	    return -1;
	isLocked = true;

	ICapabilityElectrodynamic electro = level.getCapability(VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK,
		output.getBlockPos(), output.getBlockState(), output, facing.getOpposite());

	if (electro == null) {
	    isLocked = false;
	    return -1;
	}
	double ampacity = electro.getAmpacity();
	isLocked = false;
	return ampacity;
    }

}
