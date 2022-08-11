package physica.api.core.inventory;

import java.util.Collection;

import net.minecraft.entity.player.EntityPlayer;

public interface IPlayerUsing {

	default boolean addPlayerUsingGui(EntityPlayer player) {
		return getPlayersUsingGui().contains(player) ? true : getPlayersUsingGui().add(player);
	}

	Collection<EntityPlayer> getPlayersUsingGui();

	default boolean removePlayerUsingGui(EntityPlayer player) {
		return getPlayersUsingGui().contains(player) ? getPlayersUsingGui().remove(player) : true;
	}
}
