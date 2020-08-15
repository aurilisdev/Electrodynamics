package electrodynamics.common.recipe;

import java.util.HashMap;
import java.util.HashSet;

import electrodynamics.api.tile.processing.IElectricProcessor;
import electrodynamics.api.tile.processing.IO2OProcessor;
import electrodynamics.api.tile.processing.O2OProcessingRecipe;
import net.minecraft.tileentity.TileEntityType;

public class MachineRecipes {
	private static final HashMap<TileEntityType<?>, HashSet<O2OProcessingRecipe>> o2orecipemap = new HashMap<>();

	public static void registerRecipe(TileEntityType<?> type, O2OProcessingRecipe recipe) {
		HashSet<O2OProcessingRecipe> list;
		if (!o2orecipemap.containsKey(type)) {
			o2orecipemap.put(type, list = new HashSet<>());
		} else {
			list = o2orecipemap.get(type);
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
			}
		}
	}
}
