package electrodynamics.common.world.ruletest;

import java.util.Random;

import javax.annotation.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import electrodynamics.common.block.BlockOre;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.settings.OreConfig;
import electrodynamics.common.world.ElectrodynamicsFeatures;
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.template.IRuleTestType;
import net.minecraft.world.gen.feature.template.RuleTest;

public class RuleTestOre extends RuleTest {

	@Nullable
	private final SubtypeOre thisOre;

	private final ITag<Block> canSpawnIn;

	public static final Codec<RuleTestOre> CODEC = RecordCodecBuilder.create(instance ->
//
	instance.group(Registry.BLOCK.fieldOf("block").forGetter(instance0 -> ElectrodynamicsBlocks.SUBTYPEBLOCKREGISTER_MAPPINGS.get(instance0.thisOre).get()),
			//
			ITag.codec(() -> TagCollectionManager.getInstance().getBlocks()).fieldOf("canspawnintag").forGetter(instance0 -> instance0.canSpawnIn)).apply(instance, (block, tag) -> {
				if (block instanceof BlockOre) {
					return new RuleTestOre(((BlockOre) block).ore, tag);
				}
				return new RuleTestOre(null, tag);
			}));

	public RuleTestOre(SubtypeOre thisOre, ITag<Block> canSpawnIn) {
		this.thisOre = thisOre;
		this.canSpawnIn = canSpawnIn;
	}

	@Override
	public boolean test(BlockState state, Random random) {

		return isEnabled() && state.is(this.canSpawnIn);

	}

	private boolean isEnabled() {

		if (OreConfig.DISABLE_ALL_ORES) {
			return false;
		}

		if (thisOre == null) {

			return false;
		}

		if (OreConfig.DISABLE_ALL_ORES) {
			return false;
		}

		switch (thisOre) {
		case aluminum:
			return OreConfig.SPAWN_ALUMINUM_ORE;
		case chromite:
			return OreConfig.SPAWN_CHROMIUM_ORE;
		case copper:
			return OreConfig.SPAWN_COPPER_ORE;
		case fluorite:
			return OreConfig.SPAWN_FLUORITE_ORE;
		case lead:
			return OreConfig.SPAWN_LEAD_ORE;
		case lepidolite:
			return OreConfig.SPAWN_LITHIUM_ORE;
		case molybdenum:
			return OreConfig.SPAWN_MOLYBDENUM_ORE;
		case monazite:
			return OreConfig.SPAWN_MONAZITE_ORE;
		case niter:
			return OreConfig.SPAWN_NITER_ORE;
		case halite:
			return OreConfig.SPAWN_SALT_ORE;
		case silver:
			return OreConfig.SPAWN_SILVER_ORE;
		case sulfur:
			return OreConfig.SPAWN_SULFUR_ORE;
		case sylvite:
			return OreConfig.SPAWN_SYLVITE_ORE;
		case thorianite:
			return OreConfig.SPAWN_THORIUM_ORE;
		case tin:
			return OreConfig.SPAWN_TIN_ORE;
		case rutile:
			return OreConfig.SPAWN_TITANIUM_ORE;
		case uraninite:
			return OreConfig.SPAWN_URANIUM_ORE;
		case vanadinite:
			return OreConfig.SPAWN_VANADIUM_ORE;
		default:
			return false;
		}

	}

	@Override
	protected IRuleTestType<?> getType() {
		return ElectrodynamicsFeatures.RULE_TEST_ORE;
	}

}