package electrodynamics.common.tile.pipelines.fluid;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerFluidVoid;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerSimple;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.tile.types.GenericMaterialTile;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.CapabilityUtils;

public class TileFluidVoid extends GenericMaterialTile {
	
	public static final int CAPACITY = 128000;
	
	public static final int INPUT_SLOT = 0;

	public TileFluidVoid(BlockPos worldPos, BlockState blockState) {
		super(ElectrodynamicsTiles.TILE_FLUIDVOID.get(), worldPos, blockState);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentFluidHandlerSimple(CAPACITY, this, "").setInputDirections(BlockEntityUtils.MachineDirection.values()));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().bucketInputs(1)).valid((slot, stack, i) -> stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(CapabilityUtils.EMPTY_FLUID_ITEM) != CapabilityUtils.EMPTY_FLUID_ITEM));
		addComponent(new ComponentContainerProvider(SubtypeMachine.fluidvoid.tag(), this).createMenu((id, player) -> new ContainerFluidVoid(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tick) {
		
		ComponentInventory inv = getComponent(IComponentType.Inventory);
		
		ComponentFluidHandlerSimple simple = getComponent(IComponentType.FluidHandler);
		
		simple.drain(simple.getFluidAmount(), FluidAction.EXECUTE);
		
		ItemStack input = inv.getItem(INPUT_SLOT);
		
		if(input.isEmpty()) {
			return;
		}
		
		IFluidHandlerItem handler = input.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(CapabilityUtils.EMPTY_FLUID_ITEM);
	
		if(handler == CapabilityUtils.EMPTY_FLUID_ITEM) {
		    return;
		}
		
		handler.drain(Integer.MAX_VALUE, FluidAction.EXECUTE);
		
		inv.setItem(INPUT_SLOT, handler.getContainer());
		
	}

}
