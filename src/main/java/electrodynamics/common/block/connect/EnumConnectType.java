package electrodynamics.common.block.connect;

import net.minecraft.state.EnumProperty;
import net.minecraft.util.IStringSerializable;

public enum EnumConnectType implements IStringSerializable {
    NONE, WIRE, INVENTORY;
    public static final EnumProperty<EnumConnectType> DOWN = EnumProperty.create("down", EnumConnectType.class);
    public static final EnumProperty<EnumConnectType> UP = EnumProperty.create("up", EnumConnectType.class);
    public static final EnumProperty<EnumConnectType> NORTH = EnumProperty.create("north", EnumConnectType.class);
    public static final EnumProperty<EnumConnectType> SOUTH = EnumProperty.create("south", EnumConnectType.class);
    public static final EnumProperty<EnumConnectType> WEST = EnumProperty.create("west", EnumConnectType.class);
    public static final EnumProperty<EnumConnectType> EAST = EnumProperty.create("east", EnumConnectType.class);

    @Override
    public String getString() {
	return name().toLowerCase();
    }
}
