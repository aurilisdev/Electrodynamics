package electrodynamics.prefab.utilities.object;

import java.util.List;

import electrodynamics.common.item.subtype.SubtypeDrillHead;
import net.minecraft.world.phys.AABB;

public class QuarryArmDataHolder {
	
	public List<AABB> armParts;
	public List<AABB> titaniumParts;
	public AABB drillHead;
	public SubtypeDrillHead headType;
	
	public QuarryArmDataHolder(List<AABB> armParts, List<AABB> titaniumParts, AABB drillHead, SubtypeDrillHead head) {
		this.armParts = armParts;
		this.titaniumParts = titaniumParts;
		this.drillHead = drillHead;
		this.headType = head;
	}

}
