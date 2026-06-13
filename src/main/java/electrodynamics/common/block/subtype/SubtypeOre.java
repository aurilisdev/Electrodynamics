package electrodynamics.common.block.subtype;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.common.item.subtype.SubtypeDust;
import electrodynamics.common.item.subtype.SubtypeIngot;
import electrodynamics.common.item.subtype.SubtypeRawOre;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import voltaic.api.ISubtype;
import voltaic.common.tags.VoltaicTags;

public enum SubtypeOre implements ISubtype {

	// default mc values in OrePlacements.class

	aluminum(//
			2, //
			5, //
			5, //
			16, //
			48, //
			4f, //
			4.4f, //
			0, //
			0, //
			1, //
			1, //
			null, //
			null, //
			0, //
			0, //
			VoltaicTags.Items.ORE_ALUMINUM, //
			VoltaicTags.Blocks.ORE_ALUMINUM//
	), chromium(
			//
			3,
			//
			4,
			//
			8,
			//
			-10,
			//
			32,
			//
			10f,
			//
			6.5f,
			//
			0,
			//
			0,
			//
			1,
			//
			1,
			//
			() -> ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.chromium), //
			null, //
			0, //
			0, //
			VoltaicTags.Items.ORE_CHROMIUM, //
			VoltaicTags.Blocks.ORE_CHROMIUM//
	), fluorite(
			//
			1,
			//
			4,
			//
			4,
			//
			-10,
			//
			30,
			//
			2f,
			//
			2f,
			//
			0,
			//
			0,
			//
			1,
			//
			1,
			//
			() -> ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.fluorite), //
			null, //
			0, //
			0, //
			VoltaicTags.Items.ORE_FLUORITE, //
			VoltaicTags.Blocks.ORE_FLUORITE//
	), lead(
			//
			2,
			//
			10,
			//
			6,
			//
			-64,
			//
			32,
			//
			4f,
			//
			8f,
			//
			0,
			//
			0,
			//
			1,
			//
			1,
			//
			() -> ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.lead),
			//
			() -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.lead), //
			0.7, //
			200, //
			VoltaicTags.Items.ORE_LEAD, //
			VoltaicTags.Blocks.ORE_LEAD//
	), lithium(
			//
			2,
			//
			3,
			//
			9,
			//
			-32,
			//
			32,
			//
			4f,
			//
			8f,
			//
			0,
			//
			0,
			//
			1,
			//
			1,
			//
			() -> ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.lepidolite), //
			null, //
			0, //
			0, //
			VoltaicTags.Items.ORE_LITHIUM, //
			VoltaicTags.Blocks.ORE_LITHIUM//
	), molybdenum(
			//
			1,
			//
			9,
			//
			4,
			//
			50,
			//
			60,
			//
			3f,
			//
			3f,
			//
			0,
			//
			0,
			//
			1,
			//
			1,
			//
			null,
			//
			() -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.molybdenum), //
			0.1, //
			200, //
			VoltaicTags.Items.ORE_MOLYBDENUM, //
			VoltaicTags.Blocks.ORE_MOLYBDENUM//
	), monazite(
			//
			2,
			//
			5,
			//
			8,
			//
			0,
			//
			32,
			//
			6f,
			//
			4.5f,
			//
			0,
			//
			0,
			//
			1,
			//
			1,
			//
			() -> Items.STONE, //
			null, //
			0, //
			0, //
			VoltaicTags.Items.ORE_MONAZITE, //
			VoltaicTags.Blocks.ORE_MONAZITE//
	), niter(
			//
			1,
			//
			5,
			//
			13,
			//
			0,
			//
			28,
			//
			6f,
			//
			4.5f,
			//
			1,
			//
			2,
			//
			1,
			//
			3,
			//
			() -> ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.niter), //
			null, //
			0, //
			0, //
			VoltaicTags.Items.ORE_SALTPETER, //
			VoltaicTags.Blocks.ORE_SALTPETER//
	), salt(
			//
			1,
			//
			8,
			//
			9,
			//
			32,
			//
			64,
			//
			3f,
			//
			5f,
			//
			0,
			//
			2,
			//
			1,
			//
			3,
			//
			() -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.halite), //
			null, //
			0, //
			0, //
			VoltaicTags.Items.ORE_SALT, //
			VoltaicTags.Blocks.ORE_SALT//
	), silver(
			//
			2,
			//
			6,
			//
			9,
			//
			-64,
			//
			48,
			//
			4f,
			//
			5.5f,
			//
			0,
			//
			0,
			//
			1,
			//
			1,
			//
			() -> ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.silver),
			//
			() -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.silver), //
			1.0, //
			200, //
			VoltaicTags.Items.ORE_SILVER, //
			VoltaicTags.Blocks.ORE_SILVER//
	), sulfur(
			//
			1,
			//
			5,
			//
			13,
			//
			-28,
			//
			28,
			//
			6f,
			//
			4.5f,
			//
			1,
			//
			2,
			//
			3,
			//
			5,
			//
			() -> ElectrodynamicsItems.ITEMS_DUST.getValue(SubtypeDust.sulfur), //
			null, //
			0, //
			0, //
			VoltaicTags.Items.ORE_SULFUR, //
			VoltaicTags.Blocks.ORE_SULFUR//
	), sylvite(
			//
			1,
			//
			5,
			//
			4,
			//
			-20,
			//
			50,
			//
			2f,
			//
			2f,
			//
			3,
			//
			7,
			//
			1,
			//
			3,
			//
			() -> ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.potassiumchloride), //
			null, //
			0, //
			0, //
			VoltaicTags.Items.ORE_POTASSIUMCHLORIDE, //
			VoltaicTags.Blocks.ORE_POTASSIUMCHLORIDE//
	), tin(
			//
			1,
			//
			12,
			//
			9,
			//
			-16,
			//
			112,
			//
			3f,
			//
			5f,
			//
			0,
			//
			0,
			//
			1,
			//
			1,
			//
			() -> ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.tin),
			//
			() -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.tin), //
			0.7, //
			200, //
			VoltaicTags.Items.ORE_TIN, //
			VoltaicTags.Blocks.ORE_TIN//
	), titanium(
			//
			3,
			//
			5,
			//
			8,
			//
			-48,
			//
			-8,
			//
			10f,
			//
			6.5f,
			//
			0,
			//
			0,
			//
			1,
			//
			1,
			//
			() -> ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.titanium), //
			null, //
			0, //
			0, //
			VoltaicTags.Items.ORE_TITANIUM, //
			VoltaicTags.Blocks.ORE_TITANIUM//
	), thorium(
			//
			3,
			//
			10,
			//
			8,
			//
			-32,
			//
			32,
			//
			10f,
			//
			6.5f,
			//
			0,
			//
			0,
			//
			1,
			//
			1,
			//
			() -> ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.thorium), //
			null, //
			0, //
			0, //
			VoltaicTags.Items.ORE_THORIUM, //
			VoltaicTags.Blocks.ORE_THORIUM//
	), uranium(
			//
			3,
			//
			2,
			//
			8,
			//
			-64,
			//
			-32,
			//
			10f,
			//
			6.5f,
			//
			0,
			//
			0,
			//
			1,
			//
			1,
			//
			() -> ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.uranium), //
			null, //
			0, //
			0, //
			VoltaicTags.Items.ORE_URANIUM, //
			VoltaicTags.Blocks.ORE_URANIUM//
	), vanadium(
			//
			2,
			//
			8,
			//
			8,
			//
			-32,
			//
			32,
			//
			6f,
			//
			4.5f,
			//
			0,
			//
			0,
			//
			1,
			//
			1,
			//
			() -> ElectrodynamicsItems.ITEMS_RAWORE.getValue(SubtypeRawOre.vanadinite),
			//
			() -> ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.vanadium), //
			0.7, //
			200, //
			VoltaicTags.Items.ORE_VANADIUM, //
			VoltaicTags.Blocks.ORE_VANADIUM//
	);

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

	SubtypeOre(int harvestLevel, int veinsPerChunk, int veinSize, int minY, int maxY, float hardness, float resistance,
			int minXP, int maxXP, int minDrop, int maxDrop, @Nullable Supplier<Item> lootItem,
			@Nullable Supplier<Item> smeltingItem, double smeltingXp, int smeltingTime, TagKey<Item> itemTag,
			TagKey<Block> blockTag) {

		this.harvestLevel = harvestLevel;
		this.veinsPerChunk = veinsPerChunk;
		this.veinSize = veinSize;
		this.resistance = resistance;
		this.minY = minY;
		this.maxY = maxY;
		this.hardness = hardness;
		this.minXP = minXP;
		this.maxXP = maxXP;
		this.minDrop = minDrop;
		this.maxDrop = maxDrop;
		nonSilkLootItem = lootItem;
		this.smeltingItem = smeltingItem;
		this.smeltingXp = smeltingXp;
		this.smeltingTime = smeltingTime;
		this.itemTag = itemTag;
		this.blockTag = blockTag;
	}

	@Override
	public String tag() {
		return "ore" + name();
	}

	@Override
	public String forgeTag() {
		return "ores/" + name();
	}

	@Override
	public boolean isItem() {
		return false;
	}

	public static SubtypeOre[] getOreForMiningLevel(int level) {
		List<SubtypeOre> values = new ArrayList<>();
		for (SubtypeOre value : values()) {
			if (value.harvestLevel == level) {
				values.add(value);
			}
		}
		return values.toArray(new SubtypeOre[] {});
	}

}
