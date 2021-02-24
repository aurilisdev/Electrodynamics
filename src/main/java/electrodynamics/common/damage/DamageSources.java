package electrodynamics.common.damage;

import net.minecraft.util.DamageSource;

public class DamageSources {
    public static final DamageSource ELECTRICITY = new DamageSource("electricity").setDamageBypassesArmor()
	    .setMagicDamage();
    public static final DamageSource RADIATION = new DamageSource("radiation").setDamageBypassesArmor()
	    .setMagicDamage();
}
