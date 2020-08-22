package electrodynamics.common.recipe;

import java.util.HashMap;
import java.util.HashSet;

import electrodynamics.api.tile.processing.DO2OProcessingRecipe;
import electrodynamics.api.tile.processing.IDO2OProcessor;
import electrodynamics.api.tile.processing.IElectricProcessor;
import electrodynamics.api.tile.processing.IO2OProcessor;
import electrodynamics.api.tile.processing.O2OProcessingRecipe;
import net.minecraft.tileentity.TileEntityType;

public class MachineRecipes {
	private static final HashMap<TileEntityType<?>, HashSet<O2OProcessingRecipe>> o2orecipemap = new HashMap<>();
	private static final HashMap<TileEntityType<?>, HashSet<DO2OProcessingRecipe>> do2orecipemap = new HashMap<>();

	public static void registerRecipe(TileEntityType<?> type, O2OProcessingRecipe recipe) {
		HashSet<O2OProcessingRecipe> list;
		if (!o2orecipemap.containsKey(type)) {
			o2orecipemap.put(type, list = new HashSet<>());
		} else {
			list = o2orecipemap.get(type);
		}
		list.add(recipe);
	}

	public static void registerRecipe(TileEntityType<?> type, DO2OProcessingRecipe recipe) {
		HashSet<DO2OProcessingRecipe> list;
		if (!do2orecipemap.containsKey(type)) {
			do2orecipemap.put(type, list = new HashSet<>());
		} else {
			list = do2orecipemap.get(type);
		}
		list.add(recipe);
	}

	public static boolean canProcess(IElectricProcessor processor) {
		if (processor.getJoulesStored() >= processor.getJoulesPerTick()) {
			if (processor instanceof IO2OProcessor) {
				IO2OProcessor stackProcessor = (IO2OProcessor) processor;
				TileEntityType<?> type = stackProcessor.getType();
				if (!stackProcessor.getInput().isEmpty()) {
					if (o2orecipemap.containsKey(type)) {
						for (O2OProcessingRecipe recipe : o2orecipemap.get(type)) {
							if (recipe.getInput().getItem() == stackProcessor.getInput().getItem() && recipe.getInput().getCount() <= stackProcessor.getInput().getCount()) {
								if (stackProcessor.getOutput().isEmpty() ? true
										: stackProcessor.getOutput().getItem() == recipe.getOutput().getItem()
												&& stackProcessor.getOutput().getCount() + recipe.getOutput().getCount() <= stackProcessor.getOutput().getMaxStackSize()) {
									return true;
								}
							}
						}
					}
				}
			} else if (processor instanceof IDO2OProcessor) {
				IDO2OProcessor stackProcessor = (IDO2OProcessor) processor;
				TileEntityType<?> type = stackProcessor.getType();
				if (!stackProcessor.getInput1().isEmpty() && !stackProcessor.getInput2().isEmpty()) {
					if (do2orecipemap.containsKey(type)) {
						for (DO2OProcessingRecipe recipe : do2orecipemap.get(type)) {
							if (recipe.getInput1().getItem() == stackProcessor.getInput1().getItem() && recipe.getInput1().getCount() <= stackProcessor.getInput1().getCount()
									&& recipe.getInput2().getItem() == stackProcessor.getInput2().getItem() && recipe.getInput2().getCount() <= stackProcessor.getInput2().getCount()) {
								if (stackProcessor.getOutput().isEmpty() ? true
										: stackProcessor.getOutput().getItem() == recipe.getOutput().getItem()
												&& stackProcessor.getOutput().getCount() + recipe.getOutput().getCount() <= stackProcessor.getOutput().getMaxStackSize()) {
									return true;
								}
							}
						}
					}
				}
			} else {
				return true;
			}
		}
		return false;
	}

	public static void process(IElectricProcessor processor) {
		if (processor.getJoulesStored() >= processor.getJoulesPerTick()) {
			if (processor instanceof IO2OProcessor) {
				IO2OProcessor stackProcessor = (IO2OProcessor) processor;
				TileEntityType<?> type = stackProcessor.getType();
				if (o2orecipemap.containsKey(type)) {
					if (!stackProcessor.getInput().isEmpty()) {
						for (O2OProcessingRecipe recipe : o2orecipemap.get(type)) {
							if (recipe.getInput().getItem() == stackProcessor.getInput().getItem() && recipe.getInput().getCount() <= stackProcessor.getInput().getCount()) {
								if (stackProcessor.getOutput().isEmpty() ? true
										: stackProcessor.getOutput().getItem() == recipe.getOutput().getItem()
												&& stackProcessor.getOutput().getCount() + recipe.getOutput().getCount() <= stackProcessor.getOutput().getMaxStackSize()) {
									if (stackProcessor.getOutput().isEmpty()) {
										stackProcessor.setOutput(recipe.getOutput().copy());
									} else {
										stackProcessor.getOutput().setCount(stackProcessor.getOutput().getCount() + recipe.getOutput().getCount());
									}
									stackProcessor.getInput().setCount(stackProcessor.getInput().getCount() - recipe.getInput().getCount());
								}
							}
						}
					}
				}
			} else if (processor instanceof IDO2OProcessor) {
				IDO2OProcessor stackProcessor = (IDO2OProcessor) processor;
				TileEntityType<?> type = stackProcessor.getType();
				if (do2orecipemap.containsKey(type)) {
					if (!stackProcessor.getInput1().isEmpty() && !stackProcessor.getInput2().isEmpty()) {
						for (DO2OProcessingRecipe recipe : do2orecipemap.get(type)) {
							if (recipe.getInput1().getItem() == stackProcessor.getInput1().getItem() && recipe.getInput1().getCount() <= stackProcessor.getInput1().getCount()
									&& recipe.getInput2().getItem() == stackProcessor.getInput2().getItem() && recipe.getInput2().getCount() <= stackProcessor.getInput2().getCount()) {
								if (stackProcessor.getOutput().isEmpty() ? true
										: stackProcessor.getOutput().getItem() == recipe.getOutput().getItem()
												&& stackProcessor.getOutput().getCount() + recipe.getOutput().getCount() <= stackProcessor.getOutput().getMaxStackSize()) {
									if (stackProcessor.getOutput().isEmpty()) {
										stackProcessor.setOutput(recipe.getOutput().copy());
									} else {
										stackProcessor.getOutput().setCount(stackProcessor.getOutput().getCount() + recipe.getOutput().getCount());
									}
									stackProcessor.getInput1().setCount(stackProcessor.getInput1().getCount() - recipe.getInput1().getCount());
									stackProcessor.getInput2().setCount(stackProcessor.getInput2().getCount() - recipe.getInput2().getCount());
								}
							}
						}
					}
				}
			}
		}
	}
}
