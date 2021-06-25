package electrodynamics.common.block;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.common.item.subtype.SubtypeDust;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext.Builder;

public class BlockOre extends OreBlock {
    public SubtypeOre ore;

    public BlockOre(SubtypeOre subtype) {
	super(Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(subtype.hardness, subtype.resistance)
		.harvestLevel(subtype.harvestLevel).harvestTool(subtype.harvestTool));
	ore = subtype;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, Builder builder) {

	Item oreItem;
	int count;

	// This makes it easier to follow and add on to
	// Plus it gives you a little more wiggle room with drops!
	switch (ore) {
	case sulfur:
	    oreItem = DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeDust.sulfur);
	    count = new Random().nextInt(2) + 1;
	    break;
	case niter:
	    oreItem = DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeDust.niter);
	    count = new Random().nextInt(2) + 1;
	    break;
	case halite:
	    oreItem = DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeCrystal.halite);
	    count = new Random().nextInt(3) + 1;
	    break;
	default:
	    oreItem = DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(ore);
	    count = 1;
	}

	return Arrays.asList(new ItemStack(oreItem, count));

    }

}
