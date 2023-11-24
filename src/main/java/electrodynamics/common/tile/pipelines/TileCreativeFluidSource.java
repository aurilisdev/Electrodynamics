package electrodynamics.common.tile.pipelines;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerCreativeFluidSource;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class TileCreativeFluidSource extends GenericTile {

	public TileCreativeFluidSource(BlockPos worldPos, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_CREATIVEFLUIDSOURCE.get(), worldPos, blockState);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentFluidHandlerSimple(128000, this, "").setOutputDirections(Direction.values()));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().bucketInputs(1).bucketOutputs(1)).valid((slot, stack, i) -> CapabilityUtils.hasFluidItemCap(stack)));
		addComponent(new ComponentContainerProvider(SubtypeMachine.creativefluidsource, this).createMenu((id, player) -> new ContainerCreativeFluidSource(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tick) {

		ComponentFluidHandlerSimple simple = (ComponentFluidHandlerSimple) getComponent(IComponentType.FluidHandler);
		ComponentInventory inv = getComponent(IComponentType.Inventory);
		ItemStack input = inv.getItem(0);
		ItemStack output = inv.getItem(1);

		simple.setFluid(new FluidStack(simple.getFluid(), simple.getCapacity()));

		// set tank fluid from slot 1
		if (!input.isEmpty() && CapabilityUtils.hasFluidItemCap(input)) {

			IFluidHandlerItem handler = CapabilityUtils.getFluidHandlerItem(input);

			simple.setFluid(new FluidStack(handler.drain(Integer.MAX_VALUE, FluidAction.SIMULATE), simple.getCapacity()));

		}

		// fill item in slot 2
		if (!output.isEmpty() && CapabilityUtils.hasFluidItemCap(output)) {

			IFluidHandlerItem handler = CapabilityUtils.getFluidHandlerItem(output);

			handler.fill(simple.getFluid().copy(), FluidAction.EXECUTE);

			inv.setItem(1, handler.getContainer());

		}

		Direction facing = getFacing();

		for (Direction relative : simple.outputDirections) {

			Direction direction = BlockEntityUtils.getRelativeSide(facing, relative.getOpposite());

			BlockPos face = getBlockPos().relative(direction.getOpposite());

			BlockEntity faceTile = getLevel().getBlockEntity(face);

			if (faceTile == null) {
				continue;
			}

			LazyOptional<IFluidHandler> cap = faceTile.getCapability(ForgeCapabilities.FLUID_HANDLER, direction);

			if (!cap.isPresent()) {
				continue;
			}

			IFluidHandler fHandler = cap.resolve().get();

			for (FluidTank fluidTank : simple.asArray()) {

				FluidStack tankFluid = fluidTank.getFluid();

				fHandler.fill(tankFluid, FluidAction.EXECUTE);

			}
		}
	}

}
