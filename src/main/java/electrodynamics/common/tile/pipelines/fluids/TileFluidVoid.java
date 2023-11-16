package electrodynamics.common.tile.pipelines.fluids;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerFluidVoid;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.tile.types.GenericMaterialTile;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class TileFluidVoid extends GenericMaterialTile {

	public TileFluidVoid(BlockPos worldPos, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_FLUIDVOID.get(), worldPos, blockState);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentFluidHandlerSimple(128000, this, "").setInputDirections(Direction.values()));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().bucketInputs(1)).valid((slot, stack, i) -> CapabilityUtils.hasFluidItemCap(stack)));
		addComponent(new ComponentContainerProvider(SubtypeMachine.fluidvoid, this).createMenu((id, player) -> new ContainerFluidVoid(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tick) {
		ComponentInventory inv = getComponent(IComponentType.Inventory);
		ComponentFluidHandlerSimple handler = getComponent(IComponentType.FluidHandler);
		ItemStack input = inv.getItem(0);
		if (!input.isEmpty() && CapabilityUtils.hasFluidItemCap(input)) {
			CapabilityUtils.drainFluidItem(input, Integer.MAX_VALUE, FluidAction.EXECUTE);
			if (input.getItem() instanceof BucketItem) {
				inv.setItem(0, new ItemStack(Items.BUCKET, 1));
			}
		}

		handler.drain(handler.getFluidAmount(), FluidAction.EXECUTE);
	}

}
