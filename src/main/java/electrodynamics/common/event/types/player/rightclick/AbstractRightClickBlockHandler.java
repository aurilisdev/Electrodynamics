package electrodynamics.common.event.types.player.rightclick;

import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;

public abstract class AbstractRightClickBlockHandler {

	public abstract void handle(RightClickBlock event);
	
}
