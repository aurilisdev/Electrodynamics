package electrodynamics.common.block;

import javax.annotation.Nullable;

import com.mojang.serialization.MapCodec;

import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;

public class BlockOre extends DropExperienceBlock {

    @Nullable
    public final SubtypeOre ore;
    @Nullable
    public final SubtypeOreDeepslate deepOre;

    public BlockOre(SubtypeOre ore) {
        super(UniformInt.of(ore.minXP, ore.maxXP), Blocks.STONE.properties().requiresCorrectToolForDrops().strength(ore.hardness, ore.resistance));
        this.ore = ore;
        deepOre = null;
    }

    public BlockOre(SubtypeOreDeepslate ore) {
        super(UniformInt.of(ore.minXP, ore.maxXP), Blocks.STONE.properties().sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops().strength(ore.hardness + 1.5f, ore.resistance + 1.5f));
        deepOre = ore;
        this.ore = null;
    }

    @Override
    public MapCodec<? extends DropExperienceBlock> codec() {
        throw new UnsupportedOperationException("Need to implement CODEC");
    }

}
