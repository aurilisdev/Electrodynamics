package electrodynamics.common.block.subtype;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import electrodynamics.api.ISubtype;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public enum SubtypeOreDeepslate implements ISubtype {

	tin(SubtypeOre.tin.harvestLevel, SubtypeOre.tin.veinsPerChunk, SubtypeOre.tin.veinSize, SubtypeOre.tin.minY,
			SubtypeOre.tin.maxY, SubtypeOre.tin.hardness, SubtypeOre.tin.resistance, SubtypeOre.tin.minDrop,
			SubtypeOre.tin.maxDrop, SubtypeOre.tin.nonSilkLootItem, SubtypeOre.tin.smeltingItem,
			SubtypeOre.tin.smeltingXp, SubtypeOre.tin.smeltingTime, SubtypeOre.tin.itemTag, SubtypeOre.tin.blockTag),

	silver(SubtypeOre.silver.harvestLevel, SubtypeOre.silver.veinsPerChunk, SubtypeOre.silver.veinSize,
			SubtypeOre.silver.minY, SubtypeOre.silver.maxY, SubtypeOre.silver.hardness, SubtypeOre.silver.resistance,
			SubtypeOre.silver.minDrop, SubtypeOre.silver.maxDrop, SubtypeOre.silver.nonSilkLootItem,
			SubtypeOre.silver.smeltingItem, SubtypeOre.silver.smeltingXp, SubtypeOre.silver.smeltingTime,
			SubtypeOre.silver.itemTag, SubtypeOre.silver.blockTag),

	lead(SubtypeOre.lead.harvestLevel, SubtypeOre.lead.veinsPerChunk, SubtypeOre.lead.veinSize, SubtypeOre.lead.minY,
			SubtypeOre.lead.maxY, SubtypeOre.lead.hardness, SubtypeOre.lead.resistance, SubtypeOre.lead.minDrop,
			SubtypeOre.lead.maxDrop, SubtypeOre.lead.nonSilkLootItem, SubtypeOre.lead.smeltingItem,
			SubtypeOre.lead.smeltingXp, SubtypeOre.lead.smeltingTime, SubtypeOre.lead.itemTag,
			SubtypeOre.lead.blockTag),

	uraninite(SubtypeOre.uraninite.harvestLevel, SubtypeOre.uraninite.veinsPerChunk, SubtypeOre.uraninite.veinSize,
			SubtypeOre.uraninite.minY, SubtypeOre.uraninite.maxY, SubtypeOre.uraninite.hardness,
			SubtypeOre.uraninite.resistance, SubtypeOre.uraninite.minDrop, SubtypeOre.uraninite.maxDrop,
			SubtypeOre.uraninite.nonSilkLootItem, SubtypeOre.uraninite.smeltingItem, SubtypeOre.uraninite.smeltingXp,
			SubtypeOre.uraninite.smeltingTime, SubtypeOre.uraninite.itemTag, SubtypeOre.uraninite.blockTag),

	thorianite(SubtypeOre.thorianite.harvestLevel, SubtypeOre.thorianite.veinsPerChunk, SubtypeOre.thorianite.veinSize,
			SubtypeOre.thorianite.minY, SubtypeOre.thorianite.maxY, SubtypeOre.thorianite.hardness,
			SubtypeOre.thorianite.resistance, SubtypeOre.thorianite.minDrop, SubtypeOre.thorianite.maxDrop,
			SubtypeOre.thorianite.nonSilkLootItem, SubtypeOre.thorianite.smeltingItem, SubtypeOre.thorianite.smeltingXp,
			SubtypeOre.thorianite.smeltingTime, SubtypeOre.thorianite.itemTag, SubtypeOre.thorianite.blockTag),

	monazite(SubtypeOre.monazite.harvestLevel, SubtypeOre.monazite.veinsPerChunk, SubtypeOre.monazite.veinSize,
			SubtypeOre.monazite.minY, SubtypeOre.monazite.maxY, SubtypeOre.monazite.hardness,
			SubtypeOre.monazite.resistance, SubtypeOre.monazite.minDrop, SubtypeOre.monazite.maxDrop,
			SubtypeOre.monazite.nonSilkLootItem, SubtypeOre.monazite.smeltingItem, SubtypeOre.monazite.smeltingXp,
			SubtypeOre.monazite.smeltingTime, SubtypeOre.monazite.itemTag, SubtypeOre.monazite.blockTag),

	vanadinite(SubtypeOre.vanadinite.harvestLevel, SubtypeOre.vanadinite.veinsPerChunk, SubtypeOre.vanadinite.veinSize,
			SubtypeOre.vanadinite.minY, SubtypeOre.vanadinite.maxY, SubtypeOre.vanadinite.hardness,
			SubtypeOre.vanadinite.resistance, SubtypeOre.vanadinite.minDrop, SubtypeOre.vanadinite.maxDrop,
			SubtypeOre.vanadinite.nonSilkLootItem, SubtypeOre.vanadinite.smeltingItem, SubtypeOre.vanadinite.smeltingXp,
			SubtypeOre.vanadinite.smeltingTime, SubtypeOre.vanadinite.itemTag, SubtypeOre.vanadinite.blockTag),

	sulfur(SubtypeOre.sulfur.harvestLevel, SubtypeOre.sulfur.veinsPerChunk, SubtypeOre.sulfur.veinSize,
			SubtypeOre.sulfur.minY, SubtypeOre.sulfur.maxY, SubtypeOre.sulfur.hardness, SubtypeOre.sulfur.resistance,
			SubtypeOre.sulfur.minXP, SubtypeOre.sulfur.maxXP, SubtypeOre.sulfur.minDrop, SubtypeOre.sulfur.maxDrop,
			SubtypeOre.sulfur.nonSilkLootItem, SubtypeOre.sulfur.smeltingItem, SubtypeOre.sulfur.smeltingXp,
			SubtypeOre.sulfur.smeltingTime, SubtypeOre.sulfur.itemTag, SubtypeOre.sulfur.blockTag),

	niter(SubtypeOre.niter.harvestLevel, SubtypeOre.niter.veinsPerChunk, SubtypeOre.niter.veinSize,
			SubtypeOre.niter.minY, SubtypeOre.niter.maxY, SubtypeOre.niter.hardness, SubtypeOre.niter.resistance,
			SubtypeOre.niter.minXP, SubtypeOre.niter.maxXP, SubtypeOre.niter.minDrop, SubtypeOre.niter.maxDrop,
			SubtypeOre.niter.nonSilkLootItem, SubtypeOre.niter.smeltingItem, SubtypeOre.niter.smeltingXp,
			SubtypeOre.niter.smeltingTime, SubtypeOre.niter.itemTag, SubtypeOre.niter.blockTag),

	aluminum(SubtypeOre.aluminum.harvestLevel, SubtypeOre.aluminum.veinsPerChunk, SubtypeOre.aluminum.veinSize,
			SubtypeOre.aluminum.minY, SubtypeOre.aluminum.maxY, SubtypeOre.aluminum.hardness,
			SubtypeOre.aluminum.resistance, SubtypeOre.aluminum.minDrop, SubtypeOre.aluminum.maxDrop,
			SubtypeOre.aluminum.nonSilkLootItem, SubtypeOre.aluminum.smeltingItem, SubtypeOre.aluminum.smeltingXp,
			SubtypeOre.aluminum.smeltingTime, SubtypeOre.aluminum.itemTag, SubtypeOre.aluminum.blockTag),

	chromite(SubtypeOre.chromite.harvestLevel, SubtypeOre.chromite.veinsPerChunk, SubtypeOre.chromite.veinSize,
			SubtypeOre.chromite.minY, SubtypeOre.chromite.maxY, SubtypeOre.chromite.hardness,
			SubtypeOre.chromite.resistance, SubtypeOre.chromite.minDrop, SubtypeOre.chromite.maxDrop,
			SubtypeOre.chromite.nonSilkLootItem, SubtypeOre.chromite.smeltingItem, SubtypeOre.chromite.smeltingXp,
			SubtypeOre.chromite.smeltingTime, SubtypeOre.chromite.itemTag, SubtypeOre.chromite.blockTag),

	rutile(SubtypeOre.rutile.harvestLevel, SubtypeOre.rutile.veinsPerChunk, SubtypeOre.rutile.veinSize,
			SubtypeOre.rutile.minY, SubtypeOre.rutile.maxY, SubtypeOre.rutile.hardness, SubtypeOre.rutile.resistance,
			SubtypeOre.rutile.minDrop, SubtypeOre.rutile.maxDrop, SubtypeOre.rutile.nonSilkLootItem,
			SubtypeOre.rutile.smeltingItem, SubtypeOre.rutile.smeltingXp, SubtypeOre.rutile.smeltingTime,
			SubtypeOre.rutile.itemTag, SubtypeOre.rutile.blockTag),

	halite(SubtypeOre.halite.harvestLevel, SubtypeOre.halite.veinsPerChunk, SubtypeOre.halite.veinSize,
			SubtypeOre.halite.minY, SubtypeOre.halite.maxY, SubtypeOre.halite.hardness, SubtypeOre.halite.resistance,
			SubtypeOre.halite.minXP, SubtypeOre.halite.maxXP, SubtypeOre.halite.minDrop, SubtypeOre.halite.maxDrop,
			SubtypeOre.halite.nonSilkLootItem, SubtypeOre.halite.smeltingItem, SubtypeOre.halite.smeltingXp,
			SubtypeOre.halite.smeltingTime, SubtypeOre.halite.itemTag, SubtypeOre.halite.blockTag),

	lepidolite(SubtypeOre.lepidolite.harvestLevel, SubtypeOre.lepidolite.veinsPerChunk, SubtypeOre.lepidolite.veinSize,
			SubtypeOre.lepidolite.minY, SubtypeOre.lepidolite.maxY, SubtypeOre.lepidolite.hardness,
			SubtypeOre.lepidolite.resistance, SubtypeOre.lepidolite.minDrop, SubtypeOre.lepidolite.maxDrop,
			SubtypeOre.lepidolite.nonSilkLootItem, SubtypeOre.lepidolite.smeltingItem, SubtypeOre.lepidolite.smeltingXp,
			SubtypeOre.lepidolite.smeltingTime, SubtypeOre.lepidolite.itemTag, SubtypeOre.lepidolite.blockTag),

	molybdenum(SubtypeOre.molybdenum.harvestLevel, SubtypeOre.molybdenum.veinsPerChunk, SubtypeOre.molybdenum.veinSize,
			SubtypeOre.molybdenum.minY, SubtypeOre.molybdenum.maxY, SubtypeOre.molybdenum.hardness,
			SubtypeOre.molybdenum.resistance, SubtypeOre.molybdenum.minDrop, SubtypeOre.molybdenum.maxDrop,
			SubtypeOre.molybdenum.nonSilkLootItem, SubtypeOre.molybdenum.smeltingItem, SubtypeOre.molybdenum.smeltingXp,
			SubtypeOre.molybdenum.smeltingTime, SubtypeOre.molybdenum.itemTag, SubtypeOre.molybdenum.blockTag),

	fluorite(SubtypeOre.fluorite.harvestLevel, SubtypeOre.fluorite.veinsPerChunk, SubtypeOre.fluorite.veinSize,
			SubtypeOre.fluorite.minY, SubtypeOre.fluorite.maxY, SubtypeOre.fluorite.hardness,
			SubtypeOre.fluorite.resistance, SubtypeOre.fluorite.minDrop, SubtypeOre.fluorite.maxDrop,
			SubtypeOre.fluorite.nonSilkLootItem, SubtypeOre.fluorite.smeltingItem, SubtypeOre.fluorite.smeltingXp,
			SubtypeOre.fluorite.smeltingTime, SubtypeOre.fluorite.itemTag, SubtypeOre.fluorite.blockTag),

	sylvite(SubtypeOre.sylvite.harvestLevel, SubtypeOre.sylvite.veinsPerChunk, SubtypeOre.sylvite.veinSize,
			SubtypeOre.sylvite.minY, SubtypeOre.sylvite.maxY, SubtypeOre.sylvite.hardness,
			SubtypeOre.sylvite.resistance, SubtypeOre.sylvite.minXP, SubtypeOre.sylvite.maxXP,
			SubtypeOre.sylvite.minDrop, SubtypeOre.sylvite.maxDrop, SubtypeOre.sylvite.nonSilkLootItem,
			SubtypeOre.sylvite.smeltingItem, SubtypeOre.sylvite.smeltingXp, SubtypeOre.sylvite.smeltingTime,
			SubtypeOre.sylvite.itemTag, SubtypeOre.sylvite.blockTag);

	public final int harvestLevel;
	public final int veinsPerChunk;
	public final int veinSize;
	public final int minY;
	public final int maxY;
	public final float hardness;
	public final float resistance;
	public final int minXP;
	public final int maxXP;
	public final int minDrop;
	public final int maxDrop;
	// Leaving this null will result in the ore dropping itself when broken
	@Nullable
	public final Supplier<Item> nonSilkLootItem;
	// Leaving this null will result in the ore not smelting into anything
	@Nullable
	public final Supplier<Item> smeltingItem;
	public final double smeltingXp;
	public final int smeltingTime;
	public final TagKey<Item> itemTag;
	public final TagKey<Block> blockTag;

	SubtypeOreDeepslate(int harvestLevel, int veinsPerChunk, int veinSize, int minY, int maxY, float hardness,
			float resistance, int minDrop, int maxDrop, Supplier<Item> lootItem, Supplier<Item> smeltingItem,
			double smeltingXp, int smeltingTime, TagKey<Item> itemTag, TagKey<Block> blockTag) {
		this(harvestLevel, veinsPerChunk, veinSize, minY, maxY, hardness, resistance, 0, 0, minDrop, maxDrop, lootItem,
				smeltingItem, smeltingXp, smeltingTime, itemTag, blockTag);
	}

	SubtypeOreDeepslate(int harvestLevel, int veinsPerChunk, int veinSize, int minY, int maxY, float hardness,
			float resistance, int minXP, int maxXP, int minDrop, int maxDrop, Supplier<Item> lootItem,
			Supplier<Item> smeltingItem, double smeltingXp, int smeltingTime, TagKey<Item> itemTag,
			TagKey<Block> blockTag) {

		this.harvestLevel = harvestLevel;
		this.veinsPerChunk = veinsPerChunk;
		this.veinSize = veinSize;
		this.minY = minY;
		this.maxY = maxY;
		this.hardness = hardness;
		this.resistance = resistance;
		this.minXP = minXP;
		this.maxXP = maxXP;
		this.minDrop = minDrop;
		this.maxDrop = maxDrop;
		this.nonSilkLootItem = lootItem;
		this.smeltingItem = smeltingItem;
		this.smeltingXp = smeltingXp;
		this.smeltingTime = smeltingTime;
		this.itemTag = itemTag;
		this.blockTag = blockTag;
	}

	@Override
	public String tag() {
		return "deepslateore" + name();
	}

	@Override
	public String forgeTag() {
		return "deepslateores/" + name();
	}

	@Override
	public boolean isItem() {
		return false;
	}

	public static SubtypeOreDeepslate[] getOreForMiningLevel(int level) {
		List<SubtypeOreDeepslate> values = new ArrayList<>();
		for (SubtypeOreDeepslate value : values()) {
			if (value.harvestLevel == level) {
				values.add(value);
			}
		}
		return values.toArray(new SubtypeOreDeepslate[] {});
	}

}
