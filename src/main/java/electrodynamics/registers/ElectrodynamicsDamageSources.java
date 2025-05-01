package electrodynamics.registers;

import net.minecraft.world.damagesource.DamageSource;

public class ElectrodynamicsDamageSources {
	public static final DamageSource ACCELERATED_BOLT = new DamageSource("accelerated_bolt").setProjectile();
	public static final DamageSource ACCELERATED_BOLT_IGNOREARMOR = new DamageSource("accelerated_bolt_ia").setProjectile().bypassArmor();
	public static final DamageSource PLASMA_BOLT = new DamageSource("plasma_bolt").setProjectile().bypassArmor();
}
