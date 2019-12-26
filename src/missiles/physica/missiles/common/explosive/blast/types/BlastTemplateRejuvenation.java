package physica.missiles.common.explosive.blast.types;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderServer;
import physica.library.location.GridLocation;
import physica.missiles.common.explosive.blast.BlastTemplate;
import physica.missiles.common.explosive.blast.IStateHolder;

public class BlastTemplateRejuvenation extends BlastTemplate {

	public BlastTemplateRejuvenation(int fuseTime, int tier, int callCount) {
		super(fuseTime, tier, callCount);
	}

	@Override
	public void call(World world, GridLocation loc, int callCount, IStateHolder holder)
	{
		if (!world.isRemote)
		{
			Chunk oldChunk = world.getChunkFromBlockCoords(loc.xCoord, loc.zCoord);
			if (world instanceof WorldServer)
			{
				WorldServer worldServer = (WorldServer) world;
				ChunkProviderServer chunkProviderServer = worldServer.theChunkProviderServer;
				IChunkProvider chunkProviderGenerate = chunkProviderServer.currentChunkProvider;
				Chunk newChunk = chunkProviderGenerate.provideChunk(oldChunk.xPosition, oldChunk.zPosition);
				for (int x = 0; x < 16; x++)
				{
					for (int z = 0; z < 16; z++)
					{
						for (int y = 0; y < worldServer.getHeight(); y++)
						{
							Block block = newChunk.getBlock(x, y, z);
							int meta = newChunk.getBlockMetadata(x, y, z);
							worldServer.setBlock(x + oldChunk.xPosition * 16, y, z + oldChunk.zPosition * 16, block, meta, 2);
							TileEntity tile = newChunk.getTileEntityUnsafe(x, y, z);
							if (tile != null)
							{
								worldServer.setTileEntity(x + oldChunk.xPosition * 16, y, z + oldChunk.zPosition * 16, tile);
							}
						}
					}
				}
				oldChunk.isTerrainPopulated = false;
				chunkProviderGenerate.populate(chunkProviderGenerate, oldChunk.xPosition, oldChunk.zPosition);
			}
		}
	}
}
