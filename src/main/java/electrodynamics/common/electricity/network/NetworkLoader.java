package electrodynamics.common.electricity.network;

import electrodynamics.References;
import electrodynamics.api.conductor.IConductor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class NetworkLoader {
	@SubscribeEvent
	public void onChunkLoad(ChunkEvent.Load event) {
		if (event.getChunk() != null) {
			for (BlockPos pos : event.getChunk().getTileEntitiesPos()) {
				TileEntity obj = event.getWorld().getTileEntity(pos);
				if (obj instanceof TileEntity) {
					TileEntity tileEntity = obj;
					if (tileEntity instanceof IConductor) {
						((IConductor) tileEntity).refreshNetwork();
					}
				}
			}
		}
	}
}