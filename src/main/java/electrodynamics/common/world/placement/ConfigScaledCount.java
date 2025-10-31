package electrodynamics.common.world.placement;

import com.mojang.serialization.MapCodec;

import electrodynamics.common.settings.ElectrodynamicsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.placement.RepeatingPlacement;

public class ConfigScaledCount extends RepeatingPlacement {
    // Keep the JSON field name "count" so your generated JSON stays tiny and
    // familiar
    public static final MapCodec<ConfigScaledCount> CODEC = IntProvider.codec(0, 256).fieldOf("count")
	    .xmap(ConfigScaledCount::new, m -> m.base);

    private final IntProvider base;

    public ConfigScaledCount(IntProvider base) {
	this.base = base;
    }

    public static ConfigScaledCount of(IntProvider base) {
	return new ConfigScaledCount(base);
    }

    public static ConfigScaledCount of(int base) {
	return of(ConstantInt.of(base));
    }

    @Override
    protected int count(RandomSource random, BlockPos pos) {
	// runs at worldgen time (not during runData)
	double mult = ElectrodynamicsConfig.INSTANCE.ORE_GENERATION_MULTIPLIER.get();
	int raw = base.sample(random);
	int scaled = (int) Math.max(0, Math.round(raw * mult));
	return Math.min(scaled, 256);
    }

    @Override
    public PlacementModifierType<?> type() {
	return electrodynamics.registers.ElectrodynamicsPlacementTypes.CONFIG_SCALED_COUNT.get();
    }
}
