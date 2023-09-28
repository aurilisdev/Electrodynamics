package electrodynamics.common.tile.pipelines;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerCreativeFluidSource;
import electrodynamics.common.tile.pipelines.fluids.GenericTileFluidPipe;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
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
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class TileCreativeFluidSource extends GenericTile {

	public TileCreativeFluidSource(BlockPos worldPos, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_CREATIVEFLUIDSOURCE.get(), worldPos, blockState);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentDirection(this));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentFluidHandlerSimple(128000, this, "").universalOutput());
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().bucketInputs(1).bucketOutputs(1)).valid((slot, stack, i) -> CapabilityUtils.hasFluidItemCap(stack)));
		addComponent(new ComponentContainerProvider(SubtypeMachine.creativefluidsource, this).createMenu((id, player) -> new ContainerCreativeFluidSource(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tick) {
		ComponentFluidHandlerSimple handler = (ComponentFluidHandlerSimple) getComponent(ComponentType.FluidHandler);
		ComponentInventory inv = getComponent(ComponentType.Inventory);
		ItemStack input = inv.getItem(0);
		ItemStack output = inv.getItem(1);

		handler.setFluid(new FluidStack(handler.getFluid(), handler.getCapacity()));

		// set tank fluid from slot 1
		if (!input.isEmpty()) {
			handler.setFluid(new FluidStack(CapabilityUtils.drainFluidItem(input, Integer.MAX_VALUE, FluidAction.SIMULATE).getFluid(), handler.getCapacity()));
		}
		// fill item in slot 2
		if (!output.isEmpty()) {
			boolean isBucket = output.getItem() instanceof BucketItem;
			FluidStack tankFluid = handler.getFluidInTank(0);
			int amtTaken = CapabilityUtils.fillFluidItem(output, handler.getFluidInTank(0), FluidAction.SIMULATE);
			Fluid fluid = tankFluid.getFluid();
			if (amtTaken > 0 && !isBucket) {
				CapabilityUtils.fillFluidItem(output, tankFluid, FluidAction.EXECUTE);
			} else if (amtTaken >= 1000 && isBucket && (fluid.isSame(Fluids.WATER) || fluid.isSame(Fluids.LAVA))) {
				if (fluid.isSame(Fluids.WATER)) {
					inv.setItem(1, new ItemStack(Items.WATER_BUCKET, 1));
				} else {
					inv.setItem(1, new ItemStack(Items.LAVA_BUCKET, 1));
				}
			}
		}
		// try to output to pipe
		ComponentDirection componentDirection = getComponent(ComponentType.Direction);
		for (Direction relative : handler.outputDirections) {
			Direction direction = BlockEntityUtils.getRelativeSide(componentDirection.getDirection(), relative.getOpposite());
			BlockPos face = getBlockPos().relative(direction.getOpposite());
			BlockEntity faceTile = getLevel().getBlockEntity(face);
			if (faceTile != null) {
				boolean electroPipe = faceTile instanceof GenericTileFluidPipe;
				LazyOptional<IFluidHandler> cap = faceTile.getCapability(ForgeCapabilities.FLUID_HANDLER, direction);
				if (cap.isPresent()) {
					IFluidHandler fHandler = cap.resolve().get();
					FluidStack tankFluid = handler.getFluid();
					if (electroPipe) {
						if (handler.getFluidAmount() > 0) {
							fHandler.fill(tankFluid, FluidAction.EXECUTE);
						}
					} else {
						int amtAccepted = fHandler.fill(tankFluid, FluidAction.SIMULATE);
						FluidStack taken = new FluidStack(tankFluid.getFluid(), amtAccepted);
						fHandler.fill(taken, FluidAction.EXECUTE);
					}
				}

			}
		}
	}

}
