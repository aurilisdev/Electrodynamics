package electrodynamics.datagen.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import electrodynamics.Electrodynamics;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.AlternativesLootEntry;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.DynamicLootEntry;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.CopyName;
import net.minecraft.loot.functions.CopyNbt;
import net.minecraft.loot.functions.ExplosionDecay;
import net.minecraft.loot.functions.SetContents;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractLootTableProvider extends LootTableProvider {

	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
	protected final Map<Block, LootTable.Builder> lootTables = new HashMap<>();
	private final DataGenerator generator;

	public AbstractLootTableProvider(DataGenerator dataGeneratorIn) {
		super(dataGeneratorIn);
		generator = dataGeneratorIn;
	}

	protected abstract void addTables();

	protected LootTable.Builder itemTable(String name, Block block, TileEntityType<?> type) {
		LootPool.Builder builder = LootPool.lootPool().name(name).setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(block).apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY)).apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("Items", "BlockEntityTag", CopyNbt.Action.REPLACE).copy(ComponentInventory.SAVE_KEY + "_size", "BlockEntityTag", CopyNbt.Action.REPLACE).copy("additional", "BlockEntityTag.additional", CopyNbt.Action.REPLACE)).apply(SetContents.setContents().withEntry(DynamicLootEntry.dynamicEntry(new ResourceLocation("minecraft", "contents")))));
		return LootTable.lootTable().withPool(builder);
	}

	protected LootTable.Builder energyTable(String name, Block block, TileEntityType<?> type) {
		LootPool.Builder builder = LootPool.lootPool().name(name).setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(block).apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY)).apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("joules", "BlockEntityTag.joules", CopyNbt.Action.REPLACE).copy("additional", "BlockEntityTag.additional", CopyNbt.Action.REPLACE)));
		return LootTable.lootTable().withPool(builder);
	}

	protected LootTable.Builder fluidTable(String name, Block block, TileEntityType<?> type) {
		LootPool.Builder builder = LootPool.lootPool().name(name).setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(block).apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY)).apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("fluid", "BlockEntityTag", CopyNbt.Action.REPLACE).copy("additional", "BlockEntityTag.additional", CopyNbt.Action.REPLACE)));
		return LootTable.lootTable().withPool(builder);
	}

	protected LootTable.Builder itemEnergyTable(String name, Block block, TileEntityType<?> type) {
		LootPool.Builder builder = LootPool.lootPool().name(name).setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(block).apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY)).apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("Items", "BlockEntityTag", CopyNbt.Action.REPLACE).copy(ComponentInventory.SAVE_KEY + "_size", "BlockEntityTag", CopyNbt.Action.REPLACE).copy("joules", "BlockEntityTag.joules", CopyNbt.Action.REPLACE).copy("additional", "BlockEntityTag.additional", CopyNbt.Action.REPLACE)).apply(SetContents.setContents().withEntry(DynamicLootEntry.dynamicEntry(new ResourceLocation("minecraft", "contents")))));
		return LootTable.lootTable().withPool(builder);
	}

	protected LootTable.Builder itemFluidTable(String name, Block block, TileEntityType<?> type) {
		LootPool.Builder builder = LootPool.lootPool().name(name).setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(block).apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY)).apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("Items", "BlockEntityTag", CopyNbt.Action.REPLACE).copy(ComponentInventory.SAVE_KEY + "_size", "BlockEntityTag", CopyNbt.Action.REPLACE).copy("fluid", "BlockEntityTag", CopyNbt.Action.REPLACE).copy("additional", "BlockEntityTag.additional", CopyNbt.Action.REPLACE)).apply(SetContents.setContents().withEntry(DynamicLootEntry.dynamicEntry(new ResourceLocation("minecraft", "contents")))));
		return LootTable.lootTable().withPool(builder);
	}

	protected LootTable.Builder itemEnergyFluidTable(String name, Block block, TileEntityType<?> type) {
		LootPool.Builder builder = LootPool.lootPool().name(name).setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(block).apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY)).apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("Items", "BlockEntityTag", CopyNbt.Action.REPLACE).copy(ComponentInventory.SAVE_KEY + "_size", "BlockEntityTag", CopyNbt.Action.REPLACE).copy("joules", "BlockEntityTag.joules", CopyNbt.Action.REPLACE).copy("fluid", "BlockEntityTag", CopyNbt.Action.REPLACE).copy("additional", "BlockEntityTag.additional", CopyNbt.Action.REPLACE)).apply(SetContents.setContents().withEntry(DynamicLootEntry.dynamicEntry(new ResourceLocation("minecraft", "contents")))));
		return LootTable.lootTable().withPool(builder);
	}

	/**
	 * Creates a silk touch and fortune loottable for a block
	 *
	 * @author SeaRobber69
	 * @param name     Name of the block
	 * @param block    The block that will be added
	 * @param lootItem The alternative item that is dropped when silk is not used
	 * @param min      The minimum amount dropped
	 * @param max      The maximum amount dropped
	 */
	protected LootTable.Builder createSilkTouchAndFortuneTable(String name, Block block, Item lootItem, float min, float max) {
		LootPool.Builder builder = LootPool.lootPool().name(name).setRolls(ConstantRange.exactly(1)).add(AlternativesLootEntry.alternatives(ItemLootEntry.lootTableItem(block).when(MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))))), ItemLootEntry.lootTableItem(lootItem).apply(SetCount.setCount(RandomValueRange.between(min, max))).apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 1)).apply(ExplosionDecay.explosionDecay())));
		return LootTable.lootTable().withPool(builder);
	}

	/**
	 * Creates a silk touch only loottable for a block
	 *
	 * @author SeaRobber69
	 * @param name  Name of the block
	 * @param block The block that will be added
	 */
	protected LootTable.Builder createSilkTouchOnlyTable(String name, Block block) {
		LootPool.Builder builder = LootPool.lootPool().name(name).setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(block).when(MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)))))

		);
		return LootTable.lootTable().withPool(builder);
	}

	protected LootTable.Builder createSimpleBlockTable(String name, Block block) {
		LootPool.Builder builder = LootPool.lootPool().name(name).setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(block));
		return LootTable.lootTable().withPool(builder);
	}

	@Override
	public void run(DirectoryCache cache) {
		addTables();

		Map<ResourceLocation, LootTable> tables = new HashMap<>();
		for (Map.Entry<Block, LootTable.Builder> entry : lootTables.entrySet()) {
			tables.put(entry.getKey().getLootTable(), entry.getValue().setParamSet(LootParameterSets.BLOCK).build());
		}

		Path outputFolder = generator.getOutputFolder();
		tables.forEach((key, lootTable) -> {
			Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
			try {
				
				IDataProvider.save(GSON, cache, LootTableManager.serialize(lootTable), path);
				
			} catch (IOException e) {
				Electrodynamics.LOGGER.error("Couldn't write loot table {}", path, e);
			}
		});
	}

	@Override
	public String getName() {
		return "Electrodynamics LootTables";
	}

}
