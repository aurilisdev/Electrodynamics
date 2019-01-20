package physica.core.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import physica.CoreReferences;
import physica.core.common.CoreTabRegister;
import physica.core.common.configuration.ConfigCore;

public class BlockOre extends Block {

	public BlockOre(String name) {
		super(Material.rock);
		setHardness(3F);
		setResistance(5f);
		setStepSound(soundTypeStone);
		setHarvestLevel("pickaxe", ConfigCore.TIN_ORE_HARVEST_LEVEL);
		setBlockTextureName(CoreReferences.PREFIX + name);
		setBlockName(CoreReferences.PREFIX + name);
		setCreativeTab(CoreTabRegister.coreTab);
	}

}
