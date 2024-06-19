package electrodynamics.common.event.types.player.rightclick;

import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;

public abstract class AbstractRightClickBlockHandler {

	public abstract void handle(RightClickBlock event);

}
