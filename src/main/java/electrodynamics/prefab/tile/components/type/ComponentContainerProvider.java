package electrodynamics.prefab.tile.components.type;

import java.util.function.BiFunction;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.Component;
import electrodynamics.prefab.tile.components.ComponentType;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class ComponentContainerProvider implements Component, MenuProvider {
    protected GenericTile holder = null;

    @Override
    public void holder(GenericTile holder) {
	this.holder = holder;
    }

    protected BiFunction<Integer, Inventory, AbstractContainerMenu> createMenuFunction;
    protected String name = "";

    public ComponentContainerProvider(String name) {
	this.name = name;
    }

    public ComponentContainerProvider createMenu(BiFunction<Integer, Inventory, AbstractContainerMenu> createMenuFunction) {
	this.createMenuFunction = createMenuFunction;
	return this;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player pl) {
	if (createMenuFunction != null) {
	    if (holder.hasComponent(ComponentType.Inventory)) {
		ComponentInventory componentinv = holder.getComponent(ComponentType.Inventory);
		if (componentinv.stillValid(pl)) {
		    componentinv.startOpen(pl);
		} else {
		    return null;
		}
	    }
	    return createMenuFunction.apply(id, inv);
	}
	return null;
    }

    @Override
    public net.minecraft.network.chat.Component getDisplayName() {
	return new TranslatableComponent(name);
    }

    @Override
    public ComponentType getType() {
	return ComponentType.ContainerProvider;
    }
}
