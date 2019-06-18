package physica.nuclear.common.effect.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import physica.nuclear.common.effect.damage.DamageSourceRadiation;

public class PotionRadiation extends Potion {

	public static final PotionRadiation INSTANCE = new PotionRadiation(21, true, 5149489, "radiation");

	public PotionRadiation(int id, boolean isBadEffect, int color, String name) {
		super(id, isBadEffect, color);
		setPotionName("potion." + name);
		Potion.potionTypes[getId()] = this;
		setIconIndex(6, 0);
	}

	@Override
	public Potion setIconIndex(int par1, int par2) {
		super.setIconIndex(par1, par2);
		return this;
	}

	@Override
	protected Potion setEffectiveness(double par1) {
		super.setEffectiveness(par1);
		return this;
	}

	@Override
	public void performEffect(EntityLivingBase par1EntityLiving, int amplifier) {
		if (par1EntityLiving.worldObj.rand.nextFloat() <= (amplifier + 1) / 10f && par1EntityLiving.worldObj.rand.nextFloat() > 0.8 - amplifier * 0.08D) {
			par1EntityLiving.attackEntityFrom(DamageSourceRadiation.INSTANCE, amplifier + 1);
		}
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}
}
