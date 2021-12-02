package electrodynamics.common.block;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.common.item.subtype.SubtypeDust;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;

public class BlockDeepslateOre extends OreBlock {
    public SubtypeOreDeepslate ore;

    public BlockDeepslateOre(SubtypeOreDeepslate subtype) {
	super(Properties.of(Material.STONE).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops().strength(subtype.hardness + 1.5f,
		subtype.resistance + 1.5f));
	ore = subtype;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, Builder builder) {
	Item oreItem;
	int count = switch (ore) {
	case sulfur, niter, sylvite -> {
	    oreItem = DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(ore == SubtypeOreDeepslate.sulfur ? SubtypeDust.sulfur
		    : ore == SubtypeOreDeepslate.sylvite ? SubtypeCrystal.potassiumchloride : SubtypeDust.niter);
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
