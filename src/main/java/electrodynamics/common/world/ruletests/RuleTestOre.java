package electrodynamics.common.world.ruletests;

import javax.annotation.Nullable;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import electrodynamics.common.block.BlockOre;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.ElectrodynamicsRuleTestTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

public class RuleTestOre extends RuleTest {

    @Nullable
    private final SubtypeOre thisOre;
    @Nullable
    private final SubtypeOreDeepslate thisDeepOre;

    private final TagKey<Block> canSpawnIn;

    public static final MapCodec<RuleTestOre> CODEC = RecordCodecBuilder.mapCodec(
	    instance -> instance.group(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter(instance0 -> {
		if (instance0.thisOre != null) {
		    return ElectrodynamicsBlocks.BLOCKS_ORE.getValue(instance0.thisOre);
		}
		if (instance0.thisDeepOre != null) {
		    return ElectrodynamicsBlocks.BLOCKS_DEEPSLATEORE.getValue(instance0.thisDeepOre);
		}
		return Blocks.AIR;
	    }), TagKey.codec(Registries.BLOCK).fieldOf("canspawnintag").forGetter(instance0 -> instance0.canSpawnIn))
		    .apply(instance, (block, tag) -> {
			if (block instanceof BlockOre ore) {
			    return new RuleTestOre(ore.ore, ore.deepOre, tag);
			}
			return new RuleTestOre(null, null, tag);
		    }));

    public RuleTestOre(SubtypeOre thisOre, SubtypeOreDeepslate thisDeepOre, TagKey<Block> canSpawnIn) {
	this.thisOre = thisOre;
	this.thisDeepOre = thisDeepOre;
	this.canSpawnIn = canSpawnIn;
    }

    @Override
    public boolean test(BlockState state, RandomSource random) {

	return isEnabled() && state.is(this.canSpawnIn);

    }

    private boolean isEnabled() {

	if (ElectrodynamicsConfig.INSTANCE.DISABLE_ALL_ORES.isTrue()) {
	    return false;
	}

	if (thisOre != null) {

	    if (ElectrodynamicsConfig.INSTANCE.DISABLE_STONE_ORES.isTrue()) {
		return false;
	    }

	    return switch (thisOre) {
	    case aluminum -> ElectrodynamicsConfig.INSTANCE.SPAWN_ALUMINUM_ORE.isTrue();
	    case chromium -> ElectrodynamicsConfig.INSTANCE.SPAWN_CHROMIUM_ORE.isTrue();
	    case fluorite -> ElectrodynamicsConfig.INSTANCE.SPAWN_FLUORITE_ORE.isTrue();
	    case lead -> ElectrodynamicsConfig.INSTANCE.SPAWN_LEAD_ORE.isTrue();
	    case lithium -> ElectrodynamicsConfig.INSTANCE.SPAWN_LITHIUM_ORE.isTrue();
	    case molybdenum -> ElectrodynamicsConfig.INSTANCE.SPAWN_MOLYBDENUM_ORE.isTrue();
	    case monazite -> ElectrodynamicsConfig.INSTANCE.SPAWN_MONAZITE_ORE.isTrue();
	    case niter -> ElectrodynamicsConfig.INSTANCE.SPAWN_NITER_ORE.isTrue();
	    case salt -> ElectrodynamicsConfig.INSTANCE.SPAWN_SALT_ORE.isTrue();
	    case silver -> ElectrodynamicsConfig.INSTANCE.SPAWN_SILVER_ORE.isTrue();
	    case sulfur -> ElectrodynamicsConfig.INSTANCE.SPAWN_SULFUR_ORE.isTrue();
	    case sylvite -> ElectrodynamicsConfig.INSTANCE.SPAWN_SYLVITE_ORE.isTrue();
	    case thorium -> ElectrodynamicsConfig.INSTANCE.SPAWN_THORIUM_ORE.isTrue();
	    case tin -> ElectrodynamicsConfig.INSTANCE.SPAWN_TIN_ORE.isTrue();
	    case titanium -> ElectrodynamicsConfig.INSTANCE.SPAWN_TITANIUM_ORE.isTrue();
	    case uranium -> ElectrodynamicsConfig.INSTANCE.SPAWN_URANIUM_ORE.isTrue();
	    case vanadium -> ElectrodynamicsConfig.INSTANCE.SPAWN_VANADIUM_ORE.isTrue();
	    default -> false;
	    };
	}
	if (thisDeepOre != null) {

	    if (ElectrodynamicsConfig.INSTANCE.DISABLE_DEEPSLATE_ORES.isTrue()) {
		return false;
	    }

	    return switch (thisDeepOre) {
	    case aluminum -> ElectrodynamicsConfig.INSTANCE.SPAWN_DEEP_ALUMINUM_ORE.isTrue();
	    case chromium -> ElectrodynamicsConfig.INSTANCE.SPAWN_DEEP_CHROMIUM_ORE.isTrue();
	    case fluorite -> ElectrodynamicsConfig.INSTANCE.SPAWN_DEEP_FLUORITE_ORE.isTrue();
	    case lead -> ElectrodynamicsConfig.INSTANCE.SPAWN_DEEP_LEAD_ORE.isTrue();
	    case lithium -> ElectrodynamicsConfig.INSTANCE.SPAWN_DEEP_LITHIUM_ORE.isTrue();
	    case molybdenum -> ElectrodynamicsConfig.INSTANCE.SPAWN_DEEP_MOLYBDENUM_ORE.isTrue();
	    case monazite -> ElectrodynamicsConfig.INSTANCE.SPAWN_DEEP_MONAZITE_ORE.isTrue();
	    case niter -> ElectrodynamicsConfig.INSTANCE.SPAWN_DEEP_NITER_ORE.isTrue();
	    case salt -> ElectrodynamicsConfig.INSTANCE.SPAWN_DEEP_SALT_ORE.isTrue();
	    case silver -> ElectrodynamicsConfig.INSTANCE.SPAWN_DEEP_SILVER_ORE.isTrue();
	    case sulfur -> ElectrodynamicsConfig.INSTANCE.SPAWN_DEEP_SULFUR_ORE.isTrue();
	    case sylvite -> ElectrodynamicsConfig.INSTANCE.SPAWN_DEEP_SYLVITE_ORE.isTrue();
	    case thorium -> ElectrodynamicsConfig.INSTANCE.SPAWN_DEEP_THORIUM_ORE.isTrue();
	    case tin -> ElectrodynamicsConfig.INSTANCE.SPAWN_DEEP_TIN_ORE.isTrue();
	    case titanium -> ElectrodynamicsConfig.INSTANCE.SPAWN_DEEP_TITANIUM_ORE.isTrue();
	    case uranium -> ElectrodynamicsConfig.INSTANCE.SPAWN_DEEP_URANIUM_ORE.isTrue();
	    case vanadium -> ElectrodynamicsConfig.INSTANCE.SPAWN_DEEP_VANADIUM_ORE.isTrue();
	    default -> false;
	    };
	}
	return false;
    }

    @Override
    protected RuleTestType<?> getType() {
	return ElectrodynamicsRuleTestTypes.TEST_CONFIG_ORESPAWN.get();
    }

}
