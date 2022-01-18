package electrodynamics.common.advancement;

import electrodynamics.common.advancement.triggers.GuidebookTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class ElectrodynamicsAdvancements {

	public static void init() {}
	
	public static final GuidebookTrigger GUIDEBOOK_TIGGER = CriteriaTriggers.register(new GuidebookTrigger());
	
}
