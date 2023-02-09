package electrodynamics.prefab.utilities.object;

import java.util.List;

import com.mojang.datafixers.util.Pair;

import electrodynamics.common.item.subtype.SubtypeDrillHead;
import electrodynamics.prefab.utilities.math.PrecisionVector;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

public record QuarryArmDataHolder(List<Pair<PrecisionVector, AABB>> lightParts, List<Pair<PrecisionVector, AABB>> darkParts, List<Pair<PrecisionVector, AABB>> titaniumParts, Pair<PrecisionVector, AABB> drillHead, SubtypeDrillHead headType, QuarryWheelDataHolder leftWheel, QuarryWheelDataHolder rightWheel, QuarryWheelDataHolder topWheel, QuarryWheelDataHolder bottomWheel, boolean running, int progress, int speed, List<BlockPos> corners, int[] signs) {

}
