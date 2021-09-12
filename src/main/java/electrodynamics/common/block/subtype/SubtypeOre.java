package electrodynamics.common.block.subtype;

import electrodynamics.api.ISubtype;
import net.minecraftforge.common.ToolType;

public enum SubtypeOre implements ISubtype {
    copper(ToolType.PICKAXE, 1, 9, 9, 0, 64, 3f, 5f), tin(ToolType.PICKAXE, 1, 5, 9, 0, 64, 3f, 5f),
    silver(ToolType.PICKAXE, 2, 3, 9, 0, 48, 4f, 5.5f), lead(ToolType.PICKAXE, 2, 3, 7, 0, 64, 4f, 8f),
    uraninite(ToolType.PICKAXE, 3, 3, 8, 0, 32, 10f, 6.5f), thorianite(ToolType.PICKAXE, 3, 3, 8, 0, 32, 10f, 6.5f),
    monazite(ToolType.PICKAXE, 2, 5, 8, 0, 32, 6f, 4.5f), vanadinite(ToolType.PICKAXE, 2, 5, 8, 0, 32, 6f, 4.5f),
    sulfur(ToolType.PICKAXE, 1, 7, 13, 0, 28, 6f, 4.5f), niter(ToolType.PICKAXE, 1, 6, 13, 0, 28, 6f, 4.5f),
    aluminum(ToolType.PICKAXE, 2, 5, 5, 0, 64, 4f, 4.4f), chromite(ToolType.PICKAXE, 3, 3, 8, 0, 32, 10f, 6.5f),
    rutile(ToolType.PICKAXE, 3, 3, 8, 0, 32, 10f, 6.5f), halite(ToolType.PICKAXE, 1, 9, 9, 32, 64, 3f, 5f),
    lepidolite(ToolType.PICKAXE, 2, 2, 9, 0, 64, 4f, 8f), molybdenum(ToolType.PICKAXE, 1, 9, 4, 50, 60, 3f, 3f),
    fluorite(ToolType.PICKAXE, 1, 3, 4, 10, 50, 2f, 2f);

    public final ToolType harvestTool;
    public final int harvestLevel;
    public final int veinsPerChunk;
    public final int veinSize;
    public final int minY;
    public final int maxY;
    public final float hardness;
    public final float resistance;

    private SubtypeOre(ToolType harvestTool, int harvestLevel, int veinsPerChunk, int veinSize, int minY, int maxY, float hardness,
	    float resistance) {
	this.harvestTool = harvestTool;
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
