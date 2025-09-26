package electrodynamics.common.block;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.api.radiation.RadiationSystem;
import voltaic.api.radiation.SimpleRadiationSource;
import voltaic.api.radiation.util.IRadiationRecipient;
import voltaic.api.radiation.util.RadioactiveObject;
import voltaic.common.reloadlistener.RadioactiveBlockRegister;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.registers.VoltaicCapabilities;

public class BlockRawOre extends Block {

    public BlockRawOre(Properties properties) {
        super(properties.randomTicks());
    }

    @Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);
        if (level.getLevelData().getGameTime() % 10 == 0 && !level.isClientSide && entity instanceof LivingEntity living) {
            IRadiationRecipient cap = living.getCapability(VoltaicCapabilities.CAPABILITY_RADIATIONRECIPIENT).orElse(CapabilityUtils.EMPTY_RADIATION_REPIPIENT);
            if (cap == CapabilityUtils.EMPTY_RADIATION_REPIPIENT) {
                return;
            }
            RadioactiveObject rad = RadioactiveBlockRegister.getValue(state.getBlock());
            if(rad.amount() <= 0) {
                return;
            }
            cap.recieveRadiation(living, rad.amount(), rad.strength());
        }
    }

    //adds permanent radiation source at this blocks location until the block is destroyed
    @Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        super.randomTick(state, level, pos, random);

        if(level.getLevelData().getGameTime() % 10 != 0) {
            return;
        }
        RadioactiveObject rad = RadioactiveBlockRegister.getValue(state.getBlock());

        if(rad.amount() <= 0 || RadiationSystem.getRadiationSources(level).contains(pos)) {
            return;
        }

        RadiationSystem.addRadiationSource(level, new SimpleRadiationSource(rad.amount(), rad.strength(), 10, false, 1, pos, false, false));
    }

    @Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        if(!level.isClientSide) {
            RadiationSystem.removeRadiationSource(level, pos, false);
        }
    }

    @Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if(level.isClientSide) {
            return;
        }
        RadioactiveObject rad = RadioactiveBlockRegister.getValue(state.getBlock());

        if(rad.amount() <= 0 || RadiationSystem.getRadiationSources(level).contains(pos)) {
            return;
        }
        RadiationSystem.addRadiationSource(level, new SimpleRadiationSource(rad.amount(), rad.strength(), 10, false, 1, pos, false, false));
    }

}
