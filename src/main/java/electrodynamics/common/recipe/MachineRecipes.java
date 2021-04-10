package electrodynamics.common.recipe;

import java.util.HashMap;
import java.util.HashSet;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentProcessorType;
import electrodynamics.prefab.tile.processing.DO2OProcessingRecipe;
import electrodynamics.prefab.tile.processing.O2OProcessingRecipe;
import net.minecraft.tileentity.TileEntityType;

public class MachineRecipes {
    private static final HashMap<TileEntityType<?>, HashSet<O2OProcessingRecipe>> o2orecipemap = new HashMap<>();
    private static final HashMap<TileEntityType<?>, HashSet<DO2OProcessingRecipe>> do2orecipemap = new HashMap<>();

    public static void registerRecipe(TileEntityType<?> type, O2OProcessingRecipe recipe) {
	HashSet<O2OProcessingRecipe> list = new HashSet<>();
	if (!o2orecipemap.containsKey(type)) {
	    o2orecipemap.put(type, list);
	} else {
	    list = o2orecipemap.get(type);
	}
	list.add(recipe);
    }

    public static void registerRecipe(TileEntityType<?> type, DO2OProcessingRecipe recipe) {
	HashSet<DO2OProcessingRecipe> list = new HashSet<>();
	if (!do2orecipemap.containsKey(type)) {
	    do2orecipemap.put(type, list);
	} else {
	    list = do2orecipemap.get(type);
	}
	list.add(recipe);
	list.add(new DO2OProcessingRecipe(recipe.getInput2(), recipe.getInput1(), recipe.getOutput()));
    }

    public static boolean canProcess(GenericTile tile) {
	ComponentElectrodynamic electro = tile.getComponent(ComponentType.Electrodynamic);
	ComponentProcessor processor = tile.getComponent(ComponentType.Processor);
	if (electro.getJoulesStored() > processor.getUsage()) {
	    if (processor.getProcessorType() == ComponentProcessorType.ObjectToObject) {
		TileEntityType<?> type = tile.getType();
		if (!processor.getInput().isEmpty() && o2orecipemap.containsKey(type)) {
		    for (O2OProcessingRecipe recipe : o2orecipemap.get(type)) {
			if (recipe.getInput().getItem() == processor.getInput().getItem()
				&& recipe.getInput().getCount() <= processor.getInput().getCount()
				&& (processor.getOutput().isEmpty()
					|| processor.getOutput().getItem() == recipe.getOutput().getItem() && processor.getOutput().getCount()
						+ recipe.getOutput().getCount() <= processor.getOutput().getMaxStackSize())) {
			    return true;
			}
		    }
		}
	    } else if (processor.getProcessorType() == ComponentProcessorType.DoubleObjectToObject) {
		TileEntityType<?> type = tile.getType();
		if (!processor.getInput().isEmpty() && !processor.getSecondInput().isEmpty() && do2orecipemap.containsKey(type)) {
		    for (DO2OProcessingRecipe recipe : do2orecipemap.get(type)) {
			if (recipe.getInput1().getItem() == processor.getInput().getItem()
				&& recipe.getInput1().getCount() <= processor.getInput().getCount()
				&& recipe.getInput2().getItem() == processor.getSecondInput().getItem()
				&& recipe.getInput2().getCount() <= processor.getSecondInput().getCount()
				&& (processor.getOutput().isEmpty()
					|| processor.getOutput().getItem() == recipe.getOutput().getItem() && processor.getOutput().getCount()
						+ recipe.getOutput().getCount() <= processor.getOutput().getMaxStackSize())) {
			    return true;
			}
		    }
		}
	    } else {
		return true;
	    }
	}
	return false;
    }

    public static void process(GenericTile tile) {
	ComponentElectrodynamic electro = tile.getComponent(ComponentType.Electrodynamic);
	ComponentProcessor processor = tile.getComponent(ComponentType.Processor);
	if (electro.getJoulesStored() > processor.getUsage()) {
	    TileEntityType<?> type = tile.getType();
	    if (processor.getProcessorType() == ComponentProcessorType.ObjectToObject) {
		if (o2orecipemap.containsKey(type) && !processor.getInput().isEmpty()) {
		    for (O2OProcessingRecipe recipe : o2orecipemap.get(type)) {
			if (recipe.getInput().getItem() == processor.getInput().getItem()
				&& recipe.getInput().getCount() <= processor.getInput().getCount()
				&& (processor.getOutput().isEmpty()
					|| processor.getOutput().getItem() == recipe.getOutput().getItem() && processor.getOutput().getCount()
						+ recipe.getOutput().getCount() <= processor.getOutput().getMaxStackSize())) {
			    if (processor.getOutput().isEmpty()) {
				processor.output(recipe.getOutput().copy());
			    } else {
				processor.getOutput().setCount(processor.getOutput().getCount() + recipe.getOutput().getCount());
			    }
			    processor.getInput().setCount(processor.getInput().getCount() - recipe.getInput().getCount());
			}
		    }
		}
	    } else if (processor.getProcessorType() == ComponentProcessorType.DoubleObjectToObject && do2orecipemap.containsKey(type)
		    && !processor.getInput().isEmpty() && !processor.getSecondInput().isEmpty()) {
		for (DO2OProcessingRecipe recipe : do2orecipemap.get(type)) {
		    if (recipe.getInput1().getItem() == processor.getInput().getItem()
			    && recipe.getInput1().getCount() <= processor.getInput().getCount()
			    && recipe.getInput2().getItem() == processor.getSecondInput().getItem()
			    && recipe.getInput2().getCount() <= processor.getSecondInput().getCount()
			    && (processor.getOutput().isEmpty() || processor.getOutput().getItem() == recipe.getOutput().getItem()
				    && processor.getOutput().getCount() + recipe.getOutput().getCount() <= processor.getOutput().getMaxStackSize())) {
			if (processor.getOutput().isEmpty()) {
			    processor.output(recipe.getOutput().copy());
			} else {
			    processor.getOutput().setCount(processor.getOutput().getCount() + recipe.getOutput().getCount());
			}
			processor.getInput().setCount(processor.getInput().getCount() - recipe.getInput1().getCount());
			processor.getSecondInput().setCount(processor.getSecondInput().getCount() - recipe.getInput2().getCount());
		    }
		}
	    }
	}

    }
}
