package electrodynamics.common.block.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeOreDeepslate implements ISubtype {
    tin(SubtypeOre.tin.harvestLevel, SubtypeOre.tin.veinsPerChunk, SubtypeOre.tin.veinSize, SubtypeOre.tin.minY, SubtypeOre.tin.maxY, SubtypeOre.tin.hardness, SubtypeOre.tin.resistance),
    silver(SubtypeOre.silver.harvestLevel, SubtypeOre.silver.veinsPerChunk, SubtypeOre.silver.veinSize, SubtypeOre.silver.minY, SubtypeOre.silver.maxY, SubtypeOre.silver.hardness, SubtypeOre.silver.resistance),
    lead(SubtypeOre.lead.harvestLevel, SubtypeOre.lead.veinsPerChunk, SubtypeOre.lead.veinSize, SubtypeOre.lead.minY, SubtypeOre.lead.maxY, SubtypeOre.lead.hardness, SubtypeOre.lead.resistance),
    uraninite(SubtypeOre.uraninite.harvestLevel, SubtypeOre.uraninite.veinsPerChunk, SubtypeOre.uraninite.veinSize, SubtypeOre.uraninite.minY, SubtypeOre.uraninite.maxY, SubtypeOre.uraninite.hardness, SubtypeOre.uraninite.resistance),
    thorianite(SubtypeOre.thorianite.harvestLevel, SubtypeOre.thorianite.veinsPerChunk, SubtypeOre.thorianite.veinSize, SubtypeOre.thorianite.minY, SubtypeOre.thorianite.maxY, SubtypeOre.thorianite.hardness, SubtypeOre.thorianite.resistance),
    monazite(SubtypeOre.monazite.harvestLevel, SubtypeOre.monazite.veinsPerChunk, SubtypeOre.monazite.veinSize, SubtypeOre.monazite.minY, SubtypeOre.monazite.maxY, SubtypeOre.monazite.hardness, SubtypeOre.monazite.resistance),
    vanadinite(SubtypeOre.vanadinite.harvestLevel, SubtypeOre.vanadinite.veinsPerChunk, SubtypeOre.vanadinite.veinSize, SubtypeOre.vanadinite.minY, SubtypeOre.vanadinite.maxY, SubtypeOre.vanadinite.hardness, SubtypeOre.vanadinite.resistance),
    sulfur(SubtypeOre.sulfur.harvestLevel, SubtypeOre.sulfur.veinsPerChunk, SubtypeOre.sulfur.veinSize, SubtypeOre.sulfur.minY, SubtypeOre.sulfur.maxY, SubtypeOre.sulfur.hardness, SubtypeOre.sulfur.resistance),
    niter(SubtypeOre.niter.harvestLevel, SubtypeOre.niter.veinsPerChunk, SubtypeOre.niter.veinSize, SubtypeOre.niter.minY, SubtypeOre.niter.maxY, SubtypeOre.niter.hardness, SubtypeOre.niter.resistance),
    aluminum(SubtypeOre.aluminum.harvestLevel, SubtypeOre.aluminum.veinsPerChunk, SubtypeOre.aluminum.veinSize, SubtypeOre.aluminum.minY, SubtypeOre.aluminum.maxY, SubtypeOre.aluminum.hardness, SubtypeOre.aluminum.resistance),
    chromite(SubtypeOre.chromite.harvestLevel, SubtypeOre.chromite.veinsPerChunk, SubtypeOre.chromite.veinSize, SubtypeOre.chromite.minY, SubtypeOre.chromite.maxY, SubtypeOre.chromite.hardness, SubtypeOre.chromite.resistance),
    rutile(SubtypeOre.rutile.harvestLevel, SubtypeOre.rutile.veinsPerChunk, SubtypeOre.rutile.veinSize, SubtypeOre.rutile.minY, SubtypeOre.rutile.maxY, SubtypeOre.rutile.hardness, SubtypeOre.rutile.resistance),
    halite(SubtypeOre.halite.harvestLevel, SubtypeOre.halite.veinsPerChunk, SubtypeOre.halite.veinSize, SubtypeOre.halite.minY, SubtypeOre.halite.maxY, SubtypeOre.halite.hardness, SubtypeOre.halite.resistance),
    lepidolite(SubtypeOre.lepidolite.harvestLevel, SubtypeOre.lepidolite.veinsPerChunk, SubtypeOre.lepidolite.veinSize, SubtypeOre.lepidolite.minY, SubtypeOre.lepidolite.maxY, SubtypeOre.lepidolite.hardness, SubtypeOre.lepidolite.resistance),
    molybdenum(SubtypeOre.molybdenum.harvestLevel, SubtypeOre.molybdenum.veinsPerChunk, SubtypeOre.molybdenum.veinSize, SubtypeOre.molybdenum.minY, SubtypeOre.molybdenum.maxY, SubtypeOre.molybdenum.hardness, SubtypeOre.molybdenum.resistance),
    fluorite(SubtypeOre.fluorite.harvestLevel, SubtypeOre.fluorite.veinsPerChunk, SubtypeOre.fluorite.veinSize, SubtypeOre.fluorite.minY, SubtypeOre.fluorite.maxY, SubtypeOre.fluorite.hardness, SubtypeOre.fluorite.resistance),
    sylvite(SubtypeOre.sylvite.harvestLevel, SubtypeOre.sylvite.veinsPerChunk, SubtypeOre.sylvite.veinSize, SubtypeOre.sylvite.minY, SubtypeOre.sylvite.maxY, SubtypeOre.sylvite.hardness, SubtypeOre.sylvite.resistance);

    public final int harvestLevel;
    public final int veinsPerChunk;
    public final int veinSize;
    public final int minY;
    public final int maxY;
    public final float hardness;
    public final float resistance;

    SubtypeOreDeepslate(int harvestLevel, int veinsPerChunk, int veinSize, int minY, int maxY, float hardness, float resistance) {
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
	return "deepslateore" + name();
    }

    @Override
    public String forgeTag() {
	return "deepslateores/" + name();
    }

    @Override
    public boolean isItem() {
	return false;
    }
}
