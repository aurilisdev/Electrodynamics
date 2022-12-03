package electrodynamics.common.tile;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerFluidVoid;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.tile.types.GenericFluidTile;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class TileFluidVoid extends GenericFluidTile {

	public TileFluidVoid(BlockPos worldPos, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_FLUIDVOID.get(), worldPos, blockState);
		addComponent(new ComponentTickable().tickServer(this::tickServer));
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentFluidHandlerSimple(128000).setInputDirections(Direction.NORTH, Direction.SOUTH,
				Direction.EAST, Direction.UP, Direction.WEST, Direction.DOWN));
		addComponent(
				new ComponentInventory(this).size(1).valid((slot, stack, i) -> CapabilityUtils.hasFluidItemCap(stack)));
		addComponent(new ComponentContainerProvider(SubtypeMachine.fluidvoid)
				.createMenu((id, player) -> new ContainerFluidVoid(id, player, getComponent(ComponentType.Inventory),
						getCoordsArray())));
	}

	private void tickServer(ComponentTickable tick) {
		ComponentInventory inv = getComponent(ComponentType.Inventory);
		ComponentFluidHandlerSimple handler = getComponent(ComponentType.FluidHandler);
		ItemStack input = inv.getItem(0);
		if (!input.isEmpty() && CapabilityUtils.hasFluidItemCap(input)) {
			CapabilityUtils.drain(input, CapabilityUtils.simDrain(input, Integer.MAX_VALUE));
			if (input.getItem() instanceof BucketItem) {
				inv.setItem(0, new ItemStack(Items.BUCKET, 1));
			}
		}

		handler.drain(handler.getFluidAmount(), FluidAction.EXECUTE);
	}

}
