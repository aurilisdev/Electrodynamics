package electrodynamics.common.event.types.player.starttracking;

import net.minecraftforge.event.entity.player.PlayerEvent;

public abstract class AbstractPlayerStartTrackingHandler {

	public abstract void handle(PlayerEvent.StartTracking event);

}
