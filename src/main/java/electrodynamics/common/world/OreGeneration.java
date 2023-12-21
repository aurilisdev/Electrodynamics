package electrodynamics.common.world;

import java.util.Random;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.settings.OreConfig;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.ElectrodynamicsFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class OreGeneration {

	@SubscribeEvent
	public static void gen(BiomeLoadingEvent event) {

		if (event.getCategory() == BiomeCategory.NETHER || event.getCategory() == BiomeCategory.THEEND) {
			return;
		}

		ElectrodynamicsFeatures.PLACED_FEATURES.getEntries().forEach(reg -> {

			event.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, Holder.direct(reg.get()));

		});

	}

	public static void generateSulfurAround(Random random, BlockPos pos, WorldGenLevel level) {
		if (OreConfig.DISABLE_ALL_ORES) {
			return;
		}

		boolean allowSulfur = !OreConfig.DISABLE_STONE_ORES && OreConfig.SPAWN_SULFUR_ORE;
		boolean allowDeepSulfur = !OreConfig.DISABLE_DEEPSLATE_ORES && OreConfig.SPAWN_DEEP_SULFUR_ORE;

		if (!allowSulfur && !allowDeepSulfur) {
			return;
		}

		for (Direction direction : Direction.values()) {

			if (random.nextFloat() > 0.3) {
				continue;
			}

			BlockPos offset = pos.offset(direction.getNormal());

			if (allowSulfur && level.getBlockState(pos).is(BlockTags.STONE_ORE_REPLACEABLES)) {

				level.setBlock(offset, ElectrodynamicsBlocks.getBlock(SubtypeOre.sulfur).defaultBlockState(), 3);

			} else if (allowDeepSulfur && level.getBlockState(pos).is(BlockTags.DEEPSLATE_ORE_REPLACEABLES)) {

				level.setBlock(offset, ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.sulfur).defaultBlockState(), 3);

			}
		}
	}
}
