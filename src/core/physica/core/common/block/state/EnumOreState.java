package physica.core.common.block.state;

import physica.core.common.blockprefab.state.IBlockStateInfo;

public enum EnumOreState implements IBlockStateInfo {
	copper("oreCopper", "pickaxe", 1, 20, 9, 0, 64, 3f, 5f), tin("oreTin", "pickaxe", 1, 20, 9, 0, 64, 3f, 5f),
	silver("oreSilver", "pickaxe", 2, 5, 9, 0, 48, 4f, 5.5f), lead("oreLead", "pickaxe", 2, 14, 9, 0, 64, 4f, 8f),
	uraninite("oreUraninite", "pickaxe", 3, 4, 8, 0, 32, 10f, 6.5f),
	thorianite("oreThorianite", "pickaxe", 3, 4, 8, 0, 32, 10f, 6.5f),
	monazite("oreMonazite", "pickaxe", 2, 16, 8, 0, 32, 6f, 4.5f),
	vanadinite("oreVanadinite", "pickaxe", 2, 13, 8, 0, 32, 6f, 4.5f),
	sulfur("oreSulfur", "pickaxe", 2, 8, 14, 0, 28, 6f, 4.5f);

	private String ore;
	private String harvestTool;
	private int harvestLevel;
	private int veinsPerChunk;
	private int veinSize;
	private int minY;
	private int maxY;
	private float hardness;
	private float resistance;

	private EnumOreState(String ore, String harvestTool, int harvestLevel, int veinsPerChunk, int veinSize, int minY,
			int maxY, float hardness, float resistance) {
		this.ore = ore;
		this.harvestTool = harvestTool;
		this.harvestLevel = harvestLevel;
		this.veinsPerChunk = veinsPerChunk;
		this.veinSize = veinSize;
		this.minY = minY;
		this.maxY = maxY;
		this.hardness = hardness;
		this.resistance = resistance;
	}

	public String getOre() {
		return ore;
	}

	@Override
	public String getName() {
		return name();
	}

	@Override
	public int getHarvestLevel() {
		return harvestLevel;
	}

	@Override
	public String getHarvestTool() {
		return harvestTool;
	}

	@Override
	public float getHardness() {
		return hardness;
	}

	@Override
	public float getResistance() {
		return resistance;
	}

	public int getVeinSize() {
		return veinSize;
	}

	public int getVeinsPerChunk() {
		return veinsPerChunk;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getMinY() {
		return minY;
	}

}
