package electrodynamics.common.block;

import java.util.Random;

import javax.annotation.Nullable;

import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.api.radiation.RadiationSystem;
import voltaic.api.radiation.SimpleRadiationSource;
import voltaic.api.radiation.util.IRadiationRecipient;
import voltaic.api.radiation.util.RadioactiveObject;
import voltaic.common.reloadlistener.RadioactiveBlockRegister;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.registers.VoltaicCapabilities;

public class BlockOre extends OreBlock {

	@Nullable
	public final SubtypeOre ore;
	@Nullable
	public final SubtypeOreDeepslate deepOre;

	public BlockOre(SubtypeOre ore) {
		super(Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(ore.hardness, ore.resistance).randomTicks(), UniformInt.of(ore.minXP, ore.maxXP));
		this.ore = ore;
		deepOre = null;
	}

	public BlockOre(SubtypeOreDeepslate ore) {
		super(Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops().strength(ore.hardness + 1.5f, ore.resistance + 1.5f).randomTicks(), UniformInt.of(ore.minXP, ore.maxXP));
		deepOre = ore;
		this.ore = null;
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
