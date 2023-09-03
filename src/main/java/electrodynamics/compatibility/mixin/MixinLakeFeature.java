package electrodynamics.compatibility.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import electrodynamics.common.world.OreGeneration;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.LakeFeature.Configuration;

@Mixin(LakeFeature.class)
public class MixinLakeFeature {

	@Inject(method = "place(Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z", ordinal = 0), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/tags/TagKey;)Z", shift = At.Shift.BEFORE, ordinal = 0), to = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/feature/LakeFeature;markAboveForPostProcessing(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/core/BlockPos;)V", ordinal = 1)), locals = LocalCapture.CAPTURE_FAILHARD)
	private void spawnSulfurInlakes(FeaturePlaceContext<Configuration> context, CallbackInfoReturnable<Boolean> cir, BlockPos blockpos, WorldGenLevel worldgenlevel, RandomSource random, LakeFeature.Configuration config, boolean[] aboolean, int i, BlockState blockstate1, BlockState blockstate2, int j2, int j3, int l3) {
		BlockPos blockpos2 = blockpos.offset(j2, l3, j3);
		if (context.config().fluid().getState(random, blockpos2).getFluidState().is(FluidTags.LAVA)) {
			OreGeneration.generateSulfurAround(random, blockpos2, worldgenlevel);
		}
	}
}