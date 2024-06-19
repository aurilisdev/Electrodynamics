package electrodynamics.common.block.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeGlass implements ISubtype {
    clear(2, 100),
    aluminum(7, 2000);

    public final float hardness;
    public final float resistance;

    SubtypeGlass(float hardness, float resistance) {
        this.hardness = hardness;
        this.resistance = resistance;
    }

    @Override
    public String tag() {
        return "glass" + name();
    }

    @Override
    public String forgeTag() {
        return "forge:glass/" + tag();
    }

    @Override
    public boolean isItem() {
        return false;
    }

}
