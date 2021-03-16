package electrodynamics.common.block;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.item.subtype.SubtypeDust;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext.Builder;

public class BlockOre extends OreBlock {
    private SubtypeOre ore;

    public BlockOre(SubtypeOre subtype) {
	super(Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(subtype.hardness, subtype.resistance)
		.harvestLevel(subtype.harvestLevel).harvestTool(subtype.harvestTool));
	ore = subtype;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, Builder builder) {
	return Arrays.asList(new ItemStack(ore == SubtypeOre.sulfur ? DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeDust.sulfur)
		: DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(ore), ore == SubtypeOre.sulfur ? new Random().nextInt(2) + 1 : 1));
    }

}
