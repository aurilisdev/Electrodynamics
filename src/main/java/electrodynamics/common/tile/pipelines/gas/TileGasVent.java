package electrodynamics.common.tile.pipelines.gas;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerGasVent;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.api.gas.GasAction;
import voltaic.api.gas.IGasHandlerItem;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentGasHandlerSimple;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.tile.types.GenericMaterialTile;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileGasVent extends GenericMaterialTile {

    public TileGasVent(BlockPos worldPos, BlockState blockState) {
        super(ElectrodynamicsTiles.TILE_GASVENT.get(), worldPos, blockState);
        addComponent(new ComponentTickable(this).tickServer(this::tickServer));
        addComponent(new ComponentPacketHandler(this));
        addComponent(new ComponentGasHandlerSimple(this, "", 128000, 1000000, 1000000).universalInput());
        addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().gasInputs(1)).valid((slot, stack, i) -> stack.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM) != null));
        addComponent(new ComponentContainerProvider(SubtypeMachine.gasvent.tag(), this).createMenu((id, player) -> new ContainerGasVent(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
    }

    public void tickServer(ComponentTickable tickable) {

        ComponentInventory inv = getComponent(IComponentType.Inventory);

        ComponentGasHandlerSimple simple = getComponent(IComponentType.GasHandler);

        simple.drain(simple.getGasAmount(), GasAction.EXECUTE);

        ItemStack input = inv.getItem(0);

        if (input.isEmpty()) {

            return;

        }

        IGasHandlerItem handler = input.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM).orElse(CapabilityUtils.EMPTY_GAS_ITEM);

        if (handler == CapabilityUtils.EMPTY_GAS_ITEM) {
            return;
        }

        for (int i = 0; i < handler.getTanks(); i++) {
            handler.drain(Integer.MAX_VALUE, GasAction.EXECUTE);
        }

    }

}
