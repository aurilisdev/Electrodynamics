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
    public List<ItemStack> getDrops(BlockState state, Builder builder) {

	Item oreItem;
	int count = switch (ore) {
	case sulfur, niter, sylvite -> {
	    oreItem = DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(
		    ore == SubtypeOre.sulfur ? SubtypeDust.sulfur : ore == SubtypeOre.sylvite ? SubtypeCrystal.potassiumchloride : SubtypeDust.niter);
	    yield new Random().nextInt(2) + 1;
	}
	case halite -> {
	    oreItem = DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeCrystal.halite);
	    yield new Random().nextInt(3) + 1;
	}
	default -> {
	    oreItem = DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(ore);
	    yield 1;
	}
	};

	return Arrays.asList(new ItemStack(oreItem, count));

    }

}
