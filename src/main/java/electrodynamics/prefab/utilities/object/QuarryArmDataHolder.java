package electrodynamics.prefab.utilities.object;

import java.util.List;

import electrodynamics.common.item.subtype.SubtypeDrillHead;
import net.minecraft.world.phys.AABB;

public record QuarryArmDataHolder(List<AABB> armParts, List<AABB> titaniumParts, List<AABB> verticalShaftRotateParts, AABB drillHead,
		SubtypeDrillHead headType, boolean shouldRotate, int speed) {

}
