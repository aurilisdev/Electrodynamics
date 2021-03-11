package electrodynamics.common.tile.generic.component;

import java.util.function.BiFunction;

import electrodynamics.common.tile.generic.GenericTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ComponentContainerProvider implements Component, INamedContainerProvider {
    protected ComponentHolder holder = null;

    @Override
    public void setHolder(ComponentHolder holder) {
	this.holder = holder;
    }

    private BiFunction<Integer, PlayerInventory, Container> createMenuFunction;
    private String name = "";

    public ComponentContainerProvider(String name) {
	this.name = name;
    }

    public void setCreateMenuFunction(BiFunction<Integer, PlayerInventory, Container> createMenuFunction) {
	this.createMenuFunction = createMenuFunction;
    }

    @Override
    public Container createMenu(int id, PlayerInventory inv, PlayerEntity pl) {
	if (createMenuFunction != null) {
	    GenericTile tile = (GenericTile) holder;
	    if (tile.hasComponent(ComponentType.Inventory)) {
		ComponentInventory componentinv = tile.getComponent(ComponentType.Inventory);
		if (!componentinv.isUsableByPlayer(pl)) {
		    componentinv.openInventory(pl);
		    return createMenuFunction.apply(id, inv);
		}
		return null;
	    }
	    return createMenuFunction.apply(id, inv);
	}
	return null;
    }

    @Override
    public ITextComponent getDisplayName() {
	return new TranslationTextComponent(name);
    }

    @Override
    public ComponentType getType() {
	return ComponentType.ContainerProvider;
    }
}
