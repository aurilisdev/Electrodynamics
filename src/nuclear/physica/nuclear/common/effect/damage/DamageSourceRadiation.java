package physica.nuclear.common.effect.damage;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;

public class DamageSourceRadiation extends DamageSource {

	public static final DamageSourceRadiation INSTANCE = new DamageSourceRadiation();

	protected DamageSourceRadiation() {
		super("radiation");
		setDamageBypassesArmor();
		setDamageIsAbsolute();
	}

	@Override
	public IChatComponent func_151519_b(EntityLivingBase entity) {
		if (entity instanceof EntityPlayer) {
			return new ChatComponentText(((EntityPlayer) entity).getDisplayName() + " died from radiation exposure");
		}
		return super.func_151519_b(entity);

	}
}
