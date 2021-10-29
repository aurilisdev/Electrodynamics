package electrodynamics.common.block;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.common.item.subtype.SubtypeDust;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;

public class BlockOre extends OreBlock {
    public SubtypeOre ore;

    public BlockOre(SubtypeOre subtype) {
	super(Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(subtype.hardness, subtype.resistance));
	ore = subtype;
    }

    @Override
    @Deprecated(since = "since overriden method is", forRemoval = false)
    public List<ItemStack> getDrops(BlockState state, Builder builder) {

	Item oreItem;
	int count;

	// This makes it easier to follow and add on to
	// Plus it gives you a little more wiggle room with drops!
	switch (ore) {
	case sulfur, niter, sylvite:
	    oreItem = DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(
		    ore == SubtypeOre.sulfur ? SubtypeDust.sulfur : ore == SubtypeOre.sylvite ? SubtypeCrystal.potassiumchloride : SubtypeDust.niter);
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
