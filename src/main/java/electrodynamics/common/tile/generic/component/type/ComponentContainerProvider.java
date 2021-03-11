package electrodynamics.common.tile.generic.component.type;

import java.util.function.BiFunction;

import electrodynamics.common.tile.generic.GenericTile;
import electrodynamics.common.tile.generic.component.Component;
import electrodynamics.common.tile.generic.component.ComponentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ComponentContainerProvider implements Component, INamedContainerProvider {
    protected GenericTile holder = null;

    @Override
    public void setHolder(GenericTile holder) {
	this.holder = holder;
    }

    protected BiFunction<Integer, PlayerInventory, Container> createMenuFunction;
    protected String name = "";

    public ComponentContainerProvider(String name) {
	this.name = name;
    }

    public void setCreateMenuFunction(BiFunction<Integer, PlayerInventory, Container> createMenuFunction) {
	this.createMenuFunction = createMenuFunction;
    }

    @Override
    public Container createMenu(int id, PlayerInventory inv, PlayerEntity pl) {
	if (createMenuFunction != null) {
	    if (holder.hasComponent(ComponentType.Inventory)) {
		ComponentInventory componentinv = holder.getComponent(ComponentType.Inventory);
		if (!componentinv.isUsableByPlayer(pl)) {
		    componentinv.openInventory(pl);
		} else {
		    return null;
		}
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
