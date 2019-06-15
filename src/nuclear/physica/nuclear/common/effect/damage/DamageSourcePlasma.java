package physica.nuclear.common.effect.damage;

import physica.library.effect.PhysicaDamageSource;

public class DamageSourcePlasma extends PhysicaDamageSource {

	public static final DamageSourcePlasma INSTANCE = new DamageSourcePlasma();

	protected DamageSourcePlasma() {
		super("plasma");
		setDamageBypassesArmor();
		setDamageIsAbsolute();

		setDeathMessages(
			"'s electrons flew off their body",
			"fell into a fusion reactor",
			"tried to swim in plasma",
			"was vaporized by plasma"
		);
	}
}
