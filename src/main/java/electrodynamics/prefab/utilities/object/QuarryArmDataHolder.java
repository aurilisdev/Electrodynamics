package electrodynamics.prefab.utilities.object;

import java.util.List;

import javax.annotation.Nullable;

import com.mojang.datafixers.util.Pair;

import electrodynamics.common.item.subtype.SubtypeDrillHead;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import voltaic.prefab.utilities.math.PrecisionVector;

public record QuarryArmDataHolder(List<Pair<PrecisionVector, AABB>> lightParts,
	List<Pair<PrecisionVector, AABB>> darkParts, List<Pair<PrecisionVector, AABB>> titaniumParts,
	@Nullable Pair<PrecisionVector, AABB> drillHead, @Nullable SubtypeDrillHead headType,
	QuarryWheelDataHolder leftWheel, QuarryWheelDataHolder rightWheel, QuarryWheelDataHolder topWheel,
	QuarryWheelDataHolder bottomWheel, boolean running, int progress, int speed, List<BlockPos> corners,
	int[] signs) {

}
