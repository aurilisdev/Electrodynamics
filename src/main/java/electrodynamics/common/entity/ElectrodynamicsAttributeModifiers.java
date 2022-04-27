package electrodynamics.common.entity;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class ElectrodynamicsAttributeModifiers {

	public static final AttributeModifier JETPACK_SPEED = new AttributeModifier("jetpack_speed", 1, Operation.MULTIPLY_TOTAL);

	public static void init() {
	}

}
