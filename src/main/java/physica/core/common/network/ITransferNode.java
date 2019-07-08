package physica.core.common.network;

import net.minecraft.world.World;
import physica.library.location.BlockLocation;

public interface ITransferNode<T> {

	public void setTransferNetwork(EnergyTransferNetwork network);

	public EnergyTransferNetwork getTransferNetwork();

	public boolean isValid();

	public World getWorld();

	public BlockLocation getNodeLocation();
}
