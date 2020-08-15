package electrodynamics.common.electricity.damage;

import net.minecraft.util.DamageSource;

public class ElectricDamageSource {
	public static final DamageSource ELECTRICITY = new DamageSource("electricity").setDamageBypassesArmor().setMagicDamage();
}
