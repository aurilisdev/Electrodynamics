package electrodynamics.datagen.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import electrodynamics.Electrodynamics;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public abstract class AbstractLootTableProvider extends LootTableProvider {

	protected final Map<Block, LootTable.Builder> lootTables = new HashMap<>();
	private final DataGenerator generator;

	public AbstractLootTableProvider(DataGenerator dataGeneratorIn) {
		super(dataGeneratorIn);
		generator = dataGeneratorIn;
	}

	protected abstract void addTables();

	public LootTable.Builder machineTable(String name, Block block, BlockEntityType<?> type, boolean items, boolean fluids, boolean gases, boolean energy, boolean additional) {
		CopyNbtFunction.Builder function = CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY);

		if (items) {
			function = function.copy("Items", "BlockEntityTag", CopyNbtFunction.MergeStrategy.REPLACE);
			function = function.copy(ComponentInventory.SAVE_KEY + "_size", "BlockEntityTag", CopyNbtFunction.MergeStrategy.REPLACE);
		}

		if (fluids) {
			function = function.copy("fluid", "BlockEntityTag", CopyNbtFunction.MergeStrategy.REPLACE);
		}

		if (gases) {
			// function = function
		}

		if (energy) {
			function = function.copy("joules", "BlockEntityTag.joules", CopyNbtFunction.MergeStrategy.REPLACE);
		}

		if (additional) {
			function = function.copy("additional", "BlockEntityTag.additional", CopyNbtFunction.MergeStrategy.REPLACE);
		}

		LootPool.Builder builder = LootPool.lootPool().name(name).setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(block).apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)).apply(function).apply(SetContainerContents.setContents(type).withEntry(DynamicLoot.dynamicEntry(new ResourceLocation("minecraft", "contents")))));
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
		LootPool.Builder builder = LootPool.lootPool().name(name).setRolls(ConstantValue.exactly(1)).add(AlternativesEntry.alternatives(LootItem.lootTableItem(block).when(MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))))), LootItem.lootTableItem(lootItem).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 1)).apply(ApplyExplosionDecay.explosionDecay())));
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
		LootPool.Builder builder = LootPool.lootPool().name(name).setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(block).when(MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1)))))

		);
		return LootTable.lootTable().withPool(builder);
	}

	protected LootTable.Builder createSimpleBlockTable(String name, Block block) {
		LootPool.Builder builder = LootPool.lootPool().name(name).setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(block));
		return LootTable.lootTable().withPool(builder);
	}

	@Override
	public void run(CachedOutput cache) {
		addTables();

		Map<ResourceLocation, LootTable> tables = new HashMap<>();
		for (Map.Entry<Block, LootTable.Builder> entry : lootTables.entrySet()) {
			tables.put(entry.getKey().getLootTable(), entry.getValue().setParamSet(LootContextParamSets.BLOCK).build());
		}

		Path outputFolder = generator.getOutputFolder();
		tables.forEach((key, lootTable) -> {
			Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
			try {
				DataProvider.saveStable(cache, LootTables.serialize(lootTable), path);
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
