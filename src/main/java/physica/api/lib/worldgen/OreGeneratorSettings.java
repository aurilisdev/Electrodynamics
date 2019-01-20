package physica.api.lib.worldgen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class OreGeneratorSettings {
	public int		minGenerateHeight;
	public int		maxGenerateHeight;
	public int		amountPerChunk;
	public int		amountPerBranch;

	public Block	replaceBlock	= Blocks.stone;

	public OreGeneratorSettings(int min, int max, int amountPerBranch, int amountPerChunk) {
		this.minGenerateHeight = min;
		this.maxGenerateHeight = max;
		this.amountPerBranch = amountPerBranch;
		this.amountPerChunk = amountPerChunk;
	}
}
