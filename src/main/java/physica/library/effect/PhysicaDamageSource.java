package physica.library.effect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;

import java.util.concurrent.ThreadLocalRandom;

public abstract class PhysicaDamageSource extends DamageSource {

	private String[] deathMessages;

	public PhysicaDamageSource(String damageType) {
		super(damageType);
	}

	protected void setDeathMessages(String... deathMessages) {
		this.deathMessages = deathMessages;
		for (int i = 0; i < deathMessages.length; i++) {
			String current = deathMessages[i];
			if (current.isEmpty()) {
				continue;
			}
			deathMessages[i] = current.startsWith(" ") || current.startsWith("'s") ? current : " " + current;
		}
	}

	@Override
	public IChatComponent func_151519_b(EntityLivingBase entity) {
		if (entity instanceof EntityPlayer && deathMessages != null && deathMessages.length > 0) {
			String message = deathMessages[ThreadLocalRandom.current().nextInt(0, deathMessages.length)];
			return new ChatComponentText(((EntityPlayer) entity).getDisplayName() + message);
		}
		return super.func_151519_b(entity);
	}

}
