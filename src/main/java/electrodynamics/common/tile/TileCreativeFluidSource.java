package electrodynamics.common.tile;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerCreativeFluidSource;
import electrodynamics.common.tile.generic.GenericTilePipe;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
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
		addComponent(new ComponentTickable().tickServer(this::tickServer));
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentFluidHandlerSimple(128000).setProperty(this).universalOutput());
		addComponent(new ComponentInventory(this).size(2).valid((slot, stack, i) -> CapabilityUtils.hasFluidItemCap(stack)));
		addComponent(new ComponentContainerProvider(SubtypeMachine.creativefluidsource).createMenu((id, player) -> new ContainerCreativeFluidSource(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tick) {
		ComponentFluidHandlerSimple handler = (ComponentFluidHandlerSimple) getComponent(ComponentType.FluidHandler);
		ComponentInventory inv = getComponent(ComponentType.Inventory);
		ItemStack input = inv.getItem(0);
		ItemStack output = inv.getItem(1);

		handler.setFluid(new FluidStack(handler.getFluid(), handler.getCapacity()));

		// set tank fluid from slot 1
		if (!input.isEmpty() && CapabilityUtils.hasFluidItemCap(input)) {
			handler.setFluid(new FluidStack(CapabilityUtils.simDrain(input, Integer.MAX_VALUE).getFluid(), handler.getCapacity()));
		}
		// fill item in slot 2
		if (!output.isEmpty() && CapabilityUtils.hasFluidItemCap(output)) {
			boolean isBucket = output.getItem() instanceof BucketItem;
			FluidStack tankFluid = handler.getFluidInTank(0);
			int amtTaken = CapabilityUtils.simFill(output, handler.getFluidInTank(0));
			Fluid fluid = tankFluid.getFluid();
			if (amtTaken > 0 && !isBucket) {
				CapabilityUtils.fill(output, new FluidStack(fluid, amtTaken));
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
				boolean electroPipe = faceTile instanceof GenericTilePipe;
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
