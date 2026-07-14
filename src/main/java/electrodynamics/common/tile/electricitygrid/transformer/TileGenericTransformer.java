package electrodynamics.common.tile.electricitygrid.transformer;

import electrodynamics.common.settings.ElectroConstants;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
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
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.prefab.utilities.object.CachedTileOutput;
import voltaic.prefab.utilities.object.TransferPack;
import voltaic.registers.VoltaicCapabilities;

public abstract class TileGenericTransformer extends GenericTile implements ITickableSound {

    public static final double MAX_VOLTAGE_CAP = VoltaicCapabilities.DEFAULT_VOLTAGE * Math.pow(2, 8); // 120 * 2 ^ 8 = 30,720
    public static final double MIN_VOLTAGE_CAP = VoltaicCapabilities.DEFAULT_VOLTAGE / Math.pow(2, 8); // 120 / 2 ^ 8 = 0.46875

    public CachedTileOutput output;

    public final SingleProperty<TransferPack> lastTransfer = property(new SingleProperty<>(PropertyTypes.TRANSFER_PACK, "lasttransfer", TransferPack.EMPTY)).setNoSave();
    public final SingleProperty<Long> lastTransferTime = property(new SingleProperty<>(PropertyTypes.LONG, "lasttransfertime", 0L)).setNoSave();

    public boolean locked = false;

    private boolean isPlayingSound = false;

    public static final BlockEntityUtils.MachineDirection OUTPUT = BlockEntityUtils.MachineDirection.FRONT;
    public static final BlockEntityUtils.MachineDirection INPUT = BlockEntityUtils.MachineDirection.BACK;

    public TileGenericTransformer(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
        addComponent(new ComponentPacketHandler(this));
        if (ElectroConstants.SHOULD_TRANSFORMER_HUM) {
            addComponent(new ComponentTickable(this).tickClient(this::tickClient));
        }
        addComponent(new ComponentElectrodynamic(this, true, true).receivePower(this::receivePower).getConnectedLoad(this::getConnectedLoad).setOutputDirections(OUTPUT).setInputDirections(INPUT).voltage(-1.0).getAmpacity(this::getAmpacity).getMinimumVoltage(this::getMinimumVoltage));
    }

    public void tickClient(ComponentTickable tickable) {
        if (level.getGameTime() - lastTransferTime.getValue() > 20L) {
            lastTransfer.setValue(TransferPack.EMPTY);
        }
        if (!isPlayingSound && shouldPlaySound()) {
            isPlayingSound = true;
            SoundBarrierMethods.playTransformerSound(ElectrodynamicsSounds.SOUND_TRANSFORMERHUM.get(), SoundSource.BLOCKS, this, 1.0F, 1.0F, true);
        }
    }

    // We can assume this runs on the server
    public TransferPack receivePower(TransferPack transfer, boolean debug) {
        Direction facing = getFacing();
        if (locked) {
            return TransferPack.EMPTY;
        }
        if (output == null) {
            output = new CachedTileOutput(level, worldPosition.relative(facing));
        }
        if (output.getSafe() == null) {
            return TransferPack.EMPTY;
        }
        double resultVoltage = transfer.getVoltage() * getCoilRatio();
        if (resultVoltage != 0) {
            resultVoltage = Mth.clamp(resultVoltage, MIN_VOLTAGE_CAP, MAX_VOLTAGE_CAP);
        }
        locked = true;
        TransferPack returner = ElectricityUtils.receivePower(output.getSafe(), facing.getOpposite(), TransferPack.joulesVoltage(transfer.getJoules() * ElectroConstants.TRANSFORMER_EFFICIENCY, resultVoltage), debug);
        locked = false;
        TransferPack toReturn = TransferPack.joulesVoltage(returner.getJoules() / ElectroConstants.TRANSFORMER_EFFICIENCY, returner.getVoltage() / getCoilRatio());
        if (!debug && toReturn.getVoltage() > 0) {
            lastTransfer.setValue(toReturn);
            lastTransferTime.setValue(level.getGameTime());

        }
        return toReturn;
    }

    public TransferPack getConnectedLoad(ICapabilityElectrodynamic.LoadProfile lastEnergy, Direction dir) {
        Direction facing = getFacing();
        if ((facing.getOpposite() != dir) || locked) {
            return TransferPack.EMPTY;
        }
        if (output == null) {
            output = new CachedTileOutput(level, worldPosition.relative(facing));
        }
        if (output.getSafe() == null) {
            return TransferPack.EMPTY;
        }
        ICapabilityElectrodynamic.LoadProfile transformed = new ICapabilityElectrodynamic.LoadProfile(TransferPack.joulesVoltage(lastEnergy.lastUsage().getJoules() * ElectroConstants.TRANSFORMER_EFFICIENCY, lastEnergy.lastUsage().getVoltage() * getCoilRatio()), TransferPack.joulesVoltage(lastEnergy.maximumAvailable().getJoules() * ElectroConstants.TRANSFORMER_EFFICIENCY, lastEnergy.maximumAvailable().getVoltage() * getCoilRatio()));

        locked = true;

        BlockEntity outputTile = output.getSafe();

        ICapabilityElectrodynamic electro = outputTile.getCapability(VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK, dir).orElse(CapabilityUtils.EMPTY_ELECTRO);

        TransferPack returner = TransferPack.EMPTY;

        if (electro != CapabilityUtils.EMPTY_ELECTRO) {
            returner = electro.getConnectedLoad(transformed, dir);
        }

        // TransferPack returner = ((BlockEntity) output.getSafe()).getCapability(VoltaicCapabilities.ELECTRODYNAMIC,
        // dir).map(cap -> cap.getConnectedLoad(transformed, dir)).orElse(TransferPack.EMPTY);
        locked = false;
        return TransferPack.joulesVoltage(returner.getJoules() / ElectroConstants.TRANSFORMER_EFFICIENCY, returner.getVoltage());
    }

    public double getMinimumVoltage() {
        Direction facing = getFacing();
        if (locked) {
            return 0;
        }
        if (output == null) {
            output = new CachedTileOutput(level, worldPosition.relative(facing));
        }
        if (output.getSafe() == null) {
            return -1;
        }
        locked = true;

        BlockEntity outputTile = output.getSafe();

        ICapabilityElectrodynamic electro = outputTile.getCapability(VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK, facing.getOpposite()).orElse(CapabilityUtils.EMPTY_ELECTRO);

        double minimumVoltage = -1;

        if (electro != CapabilityUtils.EMPTY_ELECTRO) {
            minimumVoltage = electro.getMinimumVoltage();
        }

        // double minimumVoltage = ((BlockEntity) output.getSafe()).getCapability(VoltaicCapabilities.ELECTRODYNAMIC,
        // facing).map(@NotNull ICapabilityElectrodynamic::getMinimumVoltage).orElse(-1.0) / getCoilRatio();
        locked = false;
        return minimumVoltage;
    }

    public double getAmpacity() {
        Direction facing = getFacing();
        if (locked) {
            return 0;
        }
        if (output == null) {
            output = new CachedTileOutput(level, worldPosition.relative(facing));
        }
        if (output.getSafe() == null) {
            return -1;
        }
        locked = true;

        BlockEntity outputTile = output.getSafe();

        ICapabilityElectrodynamic electro = outputTile.getCapability(VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK, facing.getOpposite()).orElse(CapabilityUtils.EMPTY_ELECTRO);

        double ampacity = -1;

        if (electro != CapabilityUtils.EMPTY_ELECTRO) {
            ampacity = electro.getAmpacity();
        }

        // double ampacity = ((BlockEntity) output.getSafe()).getCapability(VoltaicCapabilities.ELECTRODYNAMIC,
        // facing).map(@NotNull ICapabilityElectrodynamic::getAmpacity).orElse(-1.0) * getCoilRatio();
        locked = false;
        return ampacity;
    }

    @Override
    public void onEntityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (level.isClientSide || lastTransfer.getValue().getJoules() <= 0 || level.getGameTime() - lastTransferTime.getValue() > 20L) {
            return;
        }
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

    // I eliminated world access as that is costly when it doesn't need to be in this case
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
        public InteractionResult use(Player arg0, InteractionHand arg1, BlockHitResult arg2) {
        	return InteractionResult.FAIL;
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
        public InteractionResult use(Player arg0, InteractionHand arg1, BlockHitResult arg2) {
        	return InteractionResult.FAIL;
        }

    }

}
