package physica.nuclear.common.effect.damage;

import physica.library.effect.PhysicaDamageSource;

public class DamageSourceRadiation extends PhysicaDamageSource {

	public static final DamageSourceRadiation INSTANCE = new DamageSourceRadiation();

	protected DamageSourceRadiation() {
		super("radiation");
		setDamageBypassesArmor();
		setDamageIsAbsolute();

		setDeathMessages(
				"died from radiation exposure",
				"held too much uranium");
	}
}
