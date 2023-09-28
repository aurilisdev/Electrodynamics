package electrodynamics.common.tile.pipelines.gas;

import electrodynamics.api.gas.GasAction;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerGasVent;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentGasHandlerSimple;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.tile.types.GenericMaterialTile;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class TileGasVent extends GenericMaterialTile {

	public TileGasVent(BlockPos worldPos, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_GASVENT.get(), worldPos, blockState);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentDirection(this));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentGasHandlerSimple(this, "", 128000, 1000000, 1000000).universalInput());
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().gasInputs(1)).valid((slot, stack, i) -> CapabilityUtils.hasGasItemCap(stack)));
		addComponent(new ComponentContainerProvider(SubtypeMachine.gasvent, this).createMenu((id, player) -> new ContainerGasVent(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	public void tickServer(ComponentTickable tickable) {
		ComponentInventory inv = getComponent(ComponentType.Inventory);
		ComponentGasHandlerSimple handler = getComponent(ComponentType.GasHandler);
		ItemStack input = inv.getItem(0);
		if (!input.isEmpty() && CapabilityUtils.hasGasItemCap(input)) {
			CapabilityUtils.drainGasItem(input, Integer.MAX_VALUE, GasAction.EXECUTE);
		}

		handler.drain(handler.getGasAmount(), GasAction.EXECUTE);
	}

}
