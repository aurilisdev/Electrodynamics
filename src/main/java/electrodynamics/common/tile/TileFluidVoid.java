package electrodynamics.common.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.inventory.container.tile.ContainerFluidVoid;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.CapabilityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistries;

public class TileFluidVoid extends GenericTile {

	private static Fluid[] fluids = new Fluid[0];

	static {
		List<Fluid> list = new ArrayList<>(ForgeRegistries.FLUIDS.getValues());
		fluids = new Fluid[list.size()];
		for (int i = 0; i < list.size(); i++) {
			fluids[i] = list.get(i);
		}
	}

	public TileFluidVoid(BlockPos worldPos, BlockState blockState) {
		super(DeferredRegisters.TILE_FLUIDVOID.get(), worldPos, blockState);
		addComponent(new ComponentTickable().tickServer(this::tickServer));
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentFluidHandlerSimple(this).relativeInput(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.UP, Direction.WEST, Direction.DOWN).setManualFluids(1, true, 128000, fluids));
		addComponent(new ComponentInventory(this).size(1).valid((slot, stack, i) -> CapabilityUtils.hasFluidItemCap(stack)));
		addComponent(new ComponentContainerProvider("container.fluidvoid").createMenu((id, player) -> new ContainerFluidVoid(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tick) {
		ComponentInventory inv = getComponent(ComponentType.Inventory);
		ComponentFluidHandlerSimple handler = getComponent(ComponentType.FluidHandler);
		ItemStack input = inv.getItem(0);
		if (!input.isEmpty() && CapabilityUtils.hasFluidItemCap(input)) {
			FluidTank tank = handler.getInputTanks()[0];
			FluidStack containerFluid = CapabilityUtils.simDrain(input, Integer.MAX_VALUE);
			if (handler.getValidInputFluids().contains(containerFluid.getFluid()) && tank.isFluidValid(containerFluid)) {
				int amtDrained = tank.fill(containerFluid, FluidAction.SIMULATE);
				FluidStack drained = new FluidStack(containerFluid.getFluid(), amtDrained);
				CapabilityUtils.drain(input, drained);
				tank.fill(drained, FluidAction.EXECUTE);
				if (input.getItem() instanceof BucketItem) {
					inv.setItem(0, new ItemStack(Items.BUCKET, 1));
				}
			}
		}
		FluidTank tank = handler.getInputTanks()[0];
		tank.drain(tank.getFluidAmount(), FluidAction.EXECUTE);
	}

}
