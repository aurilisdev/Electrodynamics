package electrodynamics.common.block.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeOre implements ISubtype {
    // default mc values in OrePlacements.class
    tin(1, 16, 9, -16, 112, 3f, 5f), // min + max + perChunk from copper
    silver(2, 8, 9, -64, 48, 4f, 5.5f), // min + max + perChunk based on gold
    lead(2, 15, 6, -64, 32, 4f, 8f), // min + max + perChunk based on silver
    uraninite(3, 6, 8, -64, -32, 10f, 6.5f), // min + max + perChunk based on diamond
    thorianite(3, 15, 8, -32, 32, 10f, 6.5f), // min + max + perChunk based on thorianite
    monazite(2, 5, 8, 0, 32, 6f, 4.5f), // not changed
    vanadinite(2, 12, 8, -32, 32, 6f, 4.5f), // doubled depth + amount
    sulfur(1, 11, 13, -28, 28, 6f, 4.5f), // increased depth + amount
    niter(1, 6, 13, 0, 28, 6f, 4.5f), // not changed
    aluminum(2, 5, 5, 16, 48, 4f, 4.4f), // increased height
    chromite(3, 5, 8, -10, 32, 10f, 6.5f), // increased depth + amount
    rutile(3, 5, 8, -48, -8, 10f, 6.5f), // min + max + perChunk based on diamond
    halite(1, 9, 9, 32, 64, 3f, 5f), // nonchanged
    lepidolite(2, 3, 9, -32, 32, 4f, 8f), // increased depth
    molybdenum(1, 9, 4, 50, 60, 3f, 3f), // unchanged
    fluorite(1, 3, 4, -10, 30, 2f, 2f), // increased depth
    sylvite(1, 5, 4, -20, 50, 2f, 2f); // increased depth + amount

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
