package electrodynamics.api.tile;

import java.util.UUID;

import net.minecraft.world.entity.LivingEntity;

public interface IPlayerStorable {

	public void setPlayer(LivingEntity player);

	public UUID getPlayerID();

}
