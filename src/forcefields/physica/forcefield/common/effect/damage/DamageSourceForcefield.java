package physica.forcefield.common.effect.damage;

import physica.library.effect.PhysicaDamageSource;

public class DamageSourceForcefield extends PhysicaDamageSource {

	public static final DamageSourceForcefield INSTANCE = new DamageSourceForcefield();

	protected DamageSourceForcefield() {
		super("forcefield");
		setDamageBypassesArmor();
		setDamageIsAbsolute();

		setDeathMessages(
				"tried to walk through a force field",
				"was disintegrated by a forcefield",
				"walked into an energy field");
	}
}
