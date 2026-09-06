package electrodynamics.common.tile.electricitygrid.transformer;

import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.prefab.sound.SoundBarrierMethods;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.registers.ElectrodynamicsSounds;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import voltaic.api.electricity.ICapabilityElectrodynamic;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.sound.ITickableSound;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.object.TransferPack;
import voltaic.registers.VoltaicCapabilities;

public abstract class TileGenericTransformer extends GenericTile implements ITickableSound {

    public static final double MAX_VOLTAGE_CAP = VoltaicCapabilities.DEFAULT_VOLTAGE * Math.pow(2, 8); // 120 * 2 ^ 8 =
												       // 30,720
    public static final double MIN_VOLTAGE_CAP = VoltaicCapabilities.DEFAULT_VOLTAGE / Math.pow(2, 8); // 120 / 2 ^ 8 =
												       // 0.46875

    public final SingleProperty<TransferPack> lastTransfer = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.TRANSFER_PACK, "lasttransfer", TransferPack.EMPTY))
	    .setNoSave();

    public final SingleProperty<Long> lastTransferTime = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.LONG, "lasttransfertime", 0L)).setNoSave();

    public boolean locked;

    private boolean isPlayingSound;

    public static final BlockEntityUtils.MachineDirection OUTPUT = BlockEntityUtils.MachineDirection.FRONT;
    public static final BlockEntityUtils.MachineDirection INPUT = BlockEntityUtils.MachineDirection.BACK;

    public TileGenericTransformer(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
	super(type, worldPosition, blockState);
	if (ElectrodynamicsConfig.INSTANCE.SHOULD_TRANSFORMER_HUM.get())
	    addComponent(new ComponentTickable(this).tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this, true, true).receivePower(this::receivePower)
		.getConnectedLoad(this::getConnectedLoad).setOutputDirections(OUTPUT).setInputDirections(INPUT)
		.voltage(-1.0).getAmpacity(this::getAmpacity).getMinimumVoltage(this::getMinimumVoltage));
    }

    public void tickClient(Level level, ComponentTickable tickable) {
	if (level.getGameTime() - lastTransferTime.getValue() > 20L)
	    lastTransfer.setValue(TransferPack.EMPTY);
	if (!isPlayingSound && shouldPlaySound()) {
	    isPlayingSound = true;
	    SoundBarrierMethods.playTransformerSound(ElectrodynamicsSounds.SOUND_TRANSFORMERHUM.get(),
		    SoundSource.BLOCKS, this, 1.0F, 1.0F, true);
	}
    }

    public TransferPack receivePower(TransferPack transfer, boolean debug) {
	if (locked)
	    return TransferPack.EMPTY;
	Level level = this.level;
	if (level == null)
	    return TransferPack.EMPTY;
	Direction facing = getFacing();
	BlockEntity outputTile = level.getBlockEntity(worldPosition.relative(facing));
	if (outputTile == null || outputTile.isRemoved())
	    return TransferPack.EMPTY;
	double coilRatio = getCoilRatio();
	double efficiency = ElectrodynamicsConfig.INSTANCE.TRANSFORMER_EFFICIENCY.get();
	double resultVoltage = transfer.getVoltage() * coilRatio;
	if (resultVoltage != 0)
	    resultVoltage = Mth.clamp(resultVoltage, MIN_VOLTAGE_CAP, MAX_VOLTAGE_CAP);
	TransferPack returner;
	locked = true;
	try {
	    returner = ElectricityUtils.receivePower(outputTile, facing.getOpposite(),
		    TransferPack.joulesVoltage(transfer.getJoules() * efficiency, resultVoltage), debug);
	} finally {
	    locked = false;
	}
	TransferPack toReturn = TransferPack.joulesVoltage(returner.getJoules() / efficiency,
		returner.getVoltage() / coilRatio);
	if (!debug && toReturn.getVoltage() > 0) {
	    lastTransfer.setValue(toReturn);
	    lastTransferTime.setValue(level.getGameTime());
	}
	return toReturn;
    }

    public TransferPack getConnectedLoad(ICapabilityElectrodynamic.LoadProfile lastEnergy, Direction dir) {
	if (getFacing().getOpposite() != dir || locked)
	    return TransferPack.EMPTY;
	Level level = this.level;
	if (level == null)
	    return TransferPack.EMPTY;
	Direction facing = getFacing();
	BlockEntity outputTile = level.getBlockEntity(worldPosition.relative(facing));
	if (outputTile == null || outputTile.isRemoved())
	    return TransferPack.EMPTY;
	double coilRatio = getCoilRatio();
	double efficiency = ElectrodynamicsConfig.INSTANCE.TRANSFORMER_EFFICIENCY.get();
	ICapabilityElectrodynamic.LoadProfile transformed = new ICapabilityElectrodynamic.LoadProfile(
		TransferPack.joulesVoltage(lastEnergy.lastUsage().getJoules() * efficiency,
			lastEnergy.lastUsage().getVoltage() * coilRatio),
		TransferPack.joulesVoltage(lastEnergy.maximumAvailable().getJoules() * efficiency,
			lastEnergy.maximumAvailable().getVoltage() * coilRatio));
	TransferPack returner = TransferPack.EMPTY;
	locked = true;
	try {
	    ICapabilityElectrodynamic electro = level.getCapability(VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK,
		    outputTile.getBlockPos(), outputTile.getBlockState(), outputTile, dir);
	    if (electro != null)
		returner = electro.getConnectedLoad(transformed, dir);
	} finally {
	    locked = false;
	}
	return TransferPack.joulesVoltage(returner.getJoules() / efficiency, returner.getVoltage());
    }

    public double getMinimumVoltage() {
	if (locked)
	    return 0;
	Level level = this.level;
	if (level == null)
	    return 0;
	Direction facing = getFacing();
	BlockEntity outputTile = level.getBlockEntity(worldPosition.relative(facing));
	if (outputTile == null || outputTile.isRemoved())
	    return -1;
	double minimumVoltage = -1;
	locked = true;
	try {
	    ICapabilityElectrodynamic electro = level.getCapability(VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK,
		    outputTile.getBlockPos(), outputTile.getBlockState(), outputTile, facing.getOpposite());
	    if (electro != null)
		minimumVoltage = electro.getMinimumVoltage();
	} finally {
	    locked = false;
	}
	return minimumVoltage;
    }

    public double getAmpacity() {
	if (locked)
	    return 0;
	Level level = this.level;
	if (level == null)
	    return 0;
	Direction facing = getFacing();
	BlockEntity outputTile = level.getBlockEntity(worldPosition.relative(facing));
	if (outputTile == null || outputTile.isRemoved())
	    return -1;
	double ampacity = -1;
	locked = true;
	try {
	    ICapabilityElectrodynamic electro = level.getCapability(VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK,
		    outputTile.getBlockPos(), outputTile.getBlockState(), outputTile, facing.getOpposite());
	    if (electro != null)
		ampacity = electro.getAmpacity();
	} finally {
	    locked = false;
	}
	return ampacity;
    }

    @Override
    public void onEntityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
	if (level.isClientSide || lastTransfer.getValue().getJoules() <= 0
		|| level.getGameTime() - lastTransferTime.getValue() > 20L)
	    return;
	ElectricityUtils.electrecuteEntity(entity, lastTransfer.getValue());
	lastTransfer.setValue(TransferPack.EMPTY);
	lastTransferTime.setValue(0L);
    }

    @Override
    public void setNotPlaying() {
	isPlayingSound = false;
    }

    @Override
    public boolean shouldPlaySound() {
	return lastTransfer.getValue().getVoltage() > 0 && lastTransfer.getValue().getJoules() > 0;
    }

    // I eliminated world access as that is costly when it doesn't need to be in
    // this case
    public abstract double getCoilRatio();

    public static final class TileDowngradeTransformer extends TileGenericTransformer {

	public TileDowngradeTransformer(BlockPos worldPosition, BlockState blockState) {
	    super(ElectrodynamicsTiles.TILE_DOWNGRADETRANSFORMER.get(), worldPosition, blockState);
	}

	@Override
	public double getCoilRatio() {
	    return 0.5;
	}

	@Override
	public InteractionResult useWithoutItem(Level level, Player player, BlockHitResult hit) {
	    return InteractionResult.FAIL;
	}

	@Override
	public ItemInteractionResult useWithItem(Level level, ItemStack used, Player player, InteractionHand hand,
		BlockHitResult hit) {
	    return ItemInteractionResult.FAIL;
	}

    }

    public static final class TileUpgradeTransformer extends TileGenericTransformer {

	public TileUpgradeTransformer(BlockPos worldPosition, BlockState blockState) {
	    super(ElectrodynamicsTiles.TILE_UPGRADETRANSFORMER.get(), worldPosition, blockState);
	}

	@Override
	public double getCoilRatio() {
	    return 2;
	}

	@Override
	public InteractionResult useWithoutItem(Level level, Player player, BlockHitResult hit) {
	    return InteractionResult.FAIL;
	}

	@Override
	public ItemInteractionResult useWithItem(Level level, ItemStack used, Player player, InteractionHand hand,
		BlockHitResult hit) {
	    return ItemInteractionResult.FAIL;
	}

    }

}
