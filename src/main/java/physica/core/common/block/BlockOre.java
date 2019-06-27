package physica.core.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import physica.CoreReferences;
import physica.core.common.CoreTabRegister;

public class BlockOre extends Block {

	public BlockOre(String name, int harvestLevel) {
		super(Material.rock);
		setHardness(3F);
		setResistance(5f * harvestLevel);
		setStepSound(soundTypeStone);
		setHarvestLevel("pickaxe", harvestLevel);
		setBlockTextureName(CoreReferences.PREFIX + name);
		setBlockName(CoreReferences.PREFIX + name);
		setCreativeTab(CoreTabRegister.coreTab);
	}

}
