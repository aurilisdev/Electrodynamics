package physica.core.common.blockprefab.state;

import net.minecraft.util.IStringSerializable;

public interface IBlockStateInfo extends IStringSerializable {
	public int getHarvestLevel();

	public String getHarvestTool();

	public float getHardness();

	public float getResistance();

}
