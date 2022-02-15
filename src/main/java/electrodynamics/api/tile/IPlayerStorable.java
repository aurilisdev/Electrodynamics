package electrodynamics.api.tile;

import java.util.UUID;

import net.minecraft.world.entity.LivingEntity;

public interface IPlayerStorable {

	void setPlayer(LivingEntity player);

	UUID getPlayerID();

}
