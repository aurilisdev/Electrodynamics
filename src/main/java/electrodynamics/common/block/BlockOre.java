package electrodynamics.common.block;

import javax.annotation.Nullable;

import com.mojang.serialization.MapCodec;

import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.AABB;
import voltaic.api.radiation.RadiationManager;
import voltaic.api.radiation.util.IRadiationRecipient;
import voltaic.api.radiation.util.RadioactiveObject;
import voltaic.common.reloadlistener.RadioactiveBlockRegister;
import voltaic.common.settings.VoltaicConfig;
import voltaic.registers.VoltaicCapabilities;

public class BlockOre extends DropExperienceBlock {

    @Nullable
    public final SubtypeOre ore;
    @Nullable
    public final SubtypeOreDeepslate deepOre;

    public BlockOre(SubtypeOre ore) {
        super(UniformInt.of(ore.minXP, ore.maxXP), Properties.ofFullCopy(Blocks.STONE).requiresCorrectToolForDrops().strength(ore.hardness, ore.resistance).randomTicks());
        this.ore = ore;
        deepOre = null;
    }

    public BlockOre(SubtypeOreDeepslate ore) {
        super(UniformInt.of(ore.minXP, ore.maxXP), Properties.ofFullCopy(Blocks.STONE).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops().strength(ore.hardness + 1.5f, ore.resistance + 1.5f).randomTicks());
        deepOre = ore;
        this.ore = null;
    }

    @Override
    public MapCodec<? extends DropExperienceBlock> codec() {
        throw new UnsupportedOperationException("Need to implement CODEC");
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);

        if(!VoltaicConfig.INSTANCE.ORES_EMIT_RADIATION.get() || level.getLevelData().getGameTime() % VoltaicConfig.INSTANCE.ORE_RADIATION_ADMIT_RATE.get() != 0) {
            return;
        }

        RadioactiveObject rad = RadioactiveBlockRegister.getValue(state.getBlock());

        if(rad.amount() <= 0) {
            return;
        }

        for(Entity entity : level.getAllEntities()) {

            AABB box = new AABB(pos).inflate(5);

            if(!entity.isAlive() || !(entity instanceof LivingEntity) || !entity.getBoundingBox().intersects(box)) {
                continue;
            }

            IRadiationRecipient cap = entity.getCapability(VoltaicCapabilities.CAPABILITY_RADIATIONRECIPIENT);
            if (cap == null) {
                continue;
            }

            double recieved = RadiationManager.getAppliedRadiation(level, pos, entity.getOnPos().above(), rad.amount(), rad.strength());

            cap.recieveRadiation((LivingEntity) entity, recieved, rad.strength());

        }
    }

}
