package electrodynamics.prefab.tile.components.type;

import java.util.function.BiFunction;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponent;
import electrodynamics.prefab.tile.components.IComponentType;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class ComponentContainerProvider implements IComponent, MenuProvider {

	protected GenericTile holder = null;
	protected BiFunction<Integer, Inventory, AbstractContainerMenu> createMenuFunction;
	protected String name = "";

	public ComponentContainerProvider(String name, GenericTile holder) {
		this.name = name;
		this.holder = holder;
	}

	public ComponentContainerProvider(SubtypeMachine machine, GenericTile holder) {
		this("container." + machine.name(), holder);
	}

	@Override
	public void holder(GenericTile holder) {
		this.holder = holder;
	}

	@Override
	public GenericTile getHolder() {
		return holder;
	}

	public ComponentContainerProvider createMenu(BiFunction<Integer, Inventory, AbstractContainerMenu> createMenuFunction) {
		this.createMenuFunction = createMenuFunction;
		return this;
	}

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player pl) {
		if (createMenuFunction != null) {
			if (holder.hasComponent(IComponentType.Inventory)) {
				ComponentInventory componentinv = holder.getComponent(IComponentType.Inventory);
				if (!componentinv.stillValid(pl)) {
					return null;
				}
				componentinv.startOpen(pl);
			}
			return createMenuFunction.apply(id, inv);
		}
		return null;
	}

	@Override
	public net.minecraft.network.chat.Component getDisplayName() {
		return net.minecraft.network.chat.Component.translatable(name);
	}

	@Override
	public IComponentType getType() {
		return IComponentType.ContainerProvider;
	}
}
