package electrodynamics.common.block.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeOre implements ISubtype {
    copper(1, 9, 9, 0, 64, 3f, 5f), tin(1, 5, 9, 0, 64, 3f, 5f), silver(2, 3, 9, 0, 48, 4f, 5.5f), lead(2, 3, 7, 0, 64, 4f, 8f),
    uraninite(3, 3, 8, 0, 32, 10f, 6.5f), thorianite(3, 3, 8, 0, 32, 10f, 6.5f), monazite(2, 5, 8, 0, 32, 6f, 4.5f),
    vanadinite(2, 5, 8, 0, 32, 6f, 4.5f), sulfur(1, 7, 13, 0, 28, 6f, 4.5f), niter(1, 6, 13, 0, 28, 6f, 4.5f), aluminum(2, 5, 5, 0, 64, 4f, 4.4f),
    chromite(3, 3, 8, 0, 32, 10f, 6.5f), rutile(3, 3, 8, 0, 32, 10f, 6.5f), halite(1, 9, 9, 32, 64, 3f, 5f), lepidolite(2, 2, 9, 0, 64, 4f, 8f),
    molybdenum(1, 9, 4, 50, 60, 3f, 3f), fluorite(1, 3, 4, 10, 50, 2f, 2f), sylvite(1, 3, 4, 10, 50, 2f, 2f);

    public final int harvestLevel;
    public final int veinsPerChunk;
    public final int veinSize;
    public final int minY;
    public final int maxY;
    public final float hardness;
    public final float resistance;

    SubtypeOre(int harvestLevel, int veinsPerChunk, int veinSize, int minY, int maxY, float hardness, float resistance) {
	this.harvestLevel = harvestLevel;
	this.veinsPerChunk = veinsPerChunk;
	this.veinSize = veinSize;
	this.minY = minY;
	this.maxY = maxY;
	this.hardness = hardness;
	this.resistance = resistance;
    }

    @Override
    public String tag() {
	return "ore" + name();
    }

    @Override
    public String forgeTag() {
	return "ores/" + name();
    }

    @Override
    public boolean isItem() {
	return false;
    }
}
