package electrodynamics.common.tile.electricitygrid;

import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.api.electricity.ICapabilityElectrodynamic;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.object.TransferPack;
import voltaic.registers.VoltaicCapabilities;

public class TileRelay extends GenericTile {

    private boolean recievedRedstoneSignal = false;

    private boolean isLocked = false;

    public static final BlockEntityUtils.MachineDirection OUTPUT = BlockEntityUtils.MachineDirection.FRONT;
    public static final BlockEntityUtils.MachineDirection INPUT = BlockEntityUtils.MachineDirection.BACK;

    public TileRelay(BlockPos worldPos, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_RELAY.get(), worldPos, blockState);
	addComponent(new ComponentElectrodynamic(this, true, true).receivePower(this::receivePower)
		.getConnectedLoad(this::getConnectedLoad).setOutputDirections(OUTPUT).setInputDirections(INPUT)
		//
		.voltage(-1).getAmpacity(this::getAmpacity).getMinimumVoltage(this::getMinimumVoltage));
    }

    public TransferPack receivePower(TransferPack transfer, boolean debug) {
	if (recievedRedstoneSignal || isLocked)
	    return TransferPack.EMPTY;

	Level level = this.level;
	if (level == null)
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

	TransferPack accepted = electro.receivePower(
		TransferPack.joulesVoltage(transfer.getJoules() * ElectrodynamicsConfig.INSTANCE.RELAY_EFFICIENCY.get(),
			transfer.getVoltage()),
		debug);

	isLocked = false;

	return TransferPack.joulesVoltage(accepted.getJoules() / ElectrodynamicsConfig.INSTANCE.RELAY_EFFICIENCY.get(),
		accepted.getVoltage());
    }

    public TransferPack getConnectedLoad(ICapabilityElectrodynamic.LoadProfile lastEnergy, Direction dir) {
	if (recievedRedstoneSignal || isLocked)
	    return TransferPack.EMPTY;

	Level level = this.level;
	if (level == null)
	    return TransferPack.EMPTY;

	Direction output = BlockEntityUtils.getRelativeSide(getFacing(), OUTPUT.mappedDir);

	if (dir != output.getOpposite())
	    return TransferPack.EMPTY;

	BlockEntity tile = level.getBlockEntity(worldPosition.relative(output));

	if (tile == null)
	    return TransferPack.EMPTY;

	ICapabilityElectrodynamic.LoadProfile transformed = new ICapabilityElectrodynamic.LoadProfile(
		TransferPack.joulesVoltage(
			lastEnergy.lastUsage().getJoules() * ElectrodynamicsConfig.INSTANCE.RELAY_EFFICIENCY.get(),
			lastEnergy.lastUsage().getVoltage()),
		TransferPack.joulesVoltage(
			lastEnergy.maximumAvailable().getJoules()
				* ElectrodynamicsConfig.INSTANCE.RELAY_EFFICIENCY.get(),
			lastEnergy.maximumAvailable().getVoltage()));

	isLocked = true;

	ICapabilityElectrodynamic electro = level.getCapability(VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK,
		tile.getBlockPos(), tile.getBlockState(), tile, dir);

	if (electro == null) {
	    isLocked = false;
	    return TransferPack.EMPTY;
	}

	TransferPack returner = electro.getConnectedLoad(transformed, dir);

	isLocked = false;
	return TransferPack.joulesVoltage(returner.getJoules() / ElectrodynamicsConfig.INSTANCE.RELAY_EFFICIENCY.get(),
		returner.getVoltage());

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

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
	super.saveAdditional(compound, registries);
	compound.putBoolean("hasredstonesignal", recievedRedstoneSignal);
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
	super.loadAdditional(compound, registries);
	recievedRedstoneSignal = compound.getBoolean("hasredstonesignal");
    }

    @Override
    public void onNeighbourChanged(LevelReader reader, BlockPos neighbor, boolean blockStateTrigger) {
	if (reader instanceof Level level) {
	    if (level.isClientSide)
		return;

	    recievedRedstoneSignal = level.hasNeighborSignal(getBlockPos());
	    if (BlockEntityUtils.isLit(this) ^ recievedRedstoneSignal) {
		BlockEntityUtils.updateLit(this, recievedRedstoneSignal);
		if (recievedRedstoneSignal) {
		    level.playSound(null, getBlockPos(), SoundEvents.IRON_TRAPDOOR_OPEN, SoundSource.BLOCKS);
		} else {
		    level.playSound(null, getBlockPos(), SoundEvents.IRON_TRAPDOOR_CLOSE, SoundSource.BLOCKS);
		}
	    }
	}
    }

    @Override
    public void onPlace(Level level, BlockState oldState, boolean isMoving) {
	super.onPlace(level, oldState, isMoving);
	if (level.isClientSide)
	    return;
	recievedRedstoneSignal = level.hasNeighborSignal(getBlockPos());
	if (BlockEntityUtils.isLit(this) ^ recievedRedstoneSignal) {
	    BlockEntityUtils.updateLit(this, recievedRedstoneSignal);
	    if (recievedRedstoneSignal) {
		level.playSound(null, getBlockPos(), SoundEvents.IRON_TRAPDOOR_OPEN, SoundSource.BLOCKS);
	    }
	}
    }

}
