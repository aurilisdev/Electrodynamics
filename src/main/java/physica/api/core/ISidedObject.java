package physica.api.core;

import net.minecraft.world.World;

public interface ISidedObject {

	default void updateServer(int ticks)
	{
	}

	default void updateClient(int ticks)
	{
	}

	default void updateCommon(int ticks)
	{
	}

	default void handleUpdate(World world, int ticks)
	{
		if (world.isRemote) {
			updateClient(ticks);
		} else {
			updateServer(ticks);
		}
		updateCommon(ticks);
	}
}
