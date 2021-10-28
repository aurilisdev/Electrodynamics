package electrodynamics.common.damage;

import net.minecraft.world.damagesource.DamageSource;

public class DamageSources {
    public static final DamageSource ELECTRICITY = new DamageSource("electricity").bypassArmor().setMagic();
    public static final DamageSource RADIATION = new DamageSource("radiation").bypassArmor().setMagic();

    public static final DamageSource ACCELERATED_BOLT = new DamageSource("accelerated_bolt").setProjectile();
    public static final DamageSource ACCELERATED_BOLT_IGNOREARMOR = new DamageSource("accelerated_bolt_ia").setProjectile().bypassArmor();
    public static final DamageSource PLASMA_BOLT = new DamageSource("plasma_bolt").setProjectile().bypassArmor();

}
