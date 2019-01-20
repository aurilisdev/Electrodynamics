package physica.forcefield.common.effect.damage;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;

public class DamageSourceForcefield extends DamageSource {

	public static final DamageSourceForcefield INSTANCE = new DamageSourceForcefield();

	protected DamageSourceForcefield() {
		super("forcefield");
		setDamageBypassesArmor();
		setDamageIsAbsolute();
	}

	@Override
	public IChatComponent func_151519_b(EntityLivingBase entity) {
		if (entity instanceof EntityPlayer) {
			return new ChatComponentText(((EntityPlayer) entity).getDisplayName() + " was disintegrated by a forcefield!");
		}
		return super.func_151519_b(entity);

	}
}
