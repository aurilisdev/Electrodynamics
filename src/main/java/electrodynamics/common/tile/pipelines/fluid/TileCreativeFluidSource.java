package electrodynamics.common.tile.pipelines.fluid;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerCreativeFluidSource;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerSimple;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.CapabilityUtils;

public class TileCreativeFluidSource extends GenericTile {

    public TileCreativeFluidSource(BlockPos worldPos, BlockState blockState) {
        super(ElectrodynamicsTiles.TILE_CREATIVEFLUIDSOURCE.get(), worldPos, blockState);
        addComponent(new ComponentTickable(this).tickServer(this::tickServer));
        addComponent(new ComponentPacketHandler(this));
        addComponent(new ComponentFluidHandlerSimple(128000, this, "").setOutputDirections(BlockEntityUtils.MachineDirection.values()));
        addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().bucketInputs(1).bucketOutputs(1)).valid((slot, stack, i) -> stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(CapabilityUtils.EMPTY_FLUID_ITEM) != CapabilityUtils.EMPTY_FLUID_ITEM));
        addComponent(new ComponentContainerProvider(SubtypeMachine.creativefluidsource.tag(), this).createMenu((id, player) -> new ContainerCreativeFluidSource(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
    }

    private void tickServer(ComponentTickable tick) {

        ComponentFluidHandlerSimple simple = (ComponentFluidHandlerSimple) getComponent(IComponentType.FluidHandler);
        ComponentInventory inv = getComponent(IComponentType.Inventory);
        ItemStack input = inv.getItem(0);
        ItemStack output = inv.getItem(1);

        simple.setFluid(new FluidStack(simple.getFluid(), simple.getCapacity()));

        // set tank fluid from slot 1
        if (!input.isEmpty()) {

            IFluidHandlerItem handler = input.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(CapabilityUtils.EMPTY_FLUID_ITEM);

            if (handler != CapabilityUtils.EMPTY_FLUID_ITEM) {

                simple.setFluid(new FluidStack(handler.drain(Integer.MAX_VALUE, FluidAction.SIMULATE), simple.getCapacity()));

            }

        }

        // fill item in slot 2
        if (!output.isEmpty()) {

            IFluidHandlerItem handler = output.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(CapabilityUtils.EMPTY_FLUID_ITEM);

            if (handler != CapabilityUtils.EMPTY_FLUID_ITEM) {

                handler.fill(simple.getFluid().copy(), FluidAction.EXECUTE);

                inv.setItem(1, handler.getContainer());

            }

        }

        Direction facing = getFacing();

        for (Direction relative : simple.outputDirections) {

            Direction direction = BlockEntityUtils.getRelativeSide(facing, relative.getOpposite());

            BlockPos face = getBlockPos().relative(direction.getOpposite());

            BlockEntity faceTile = getLevel().getBlockEntity(face);

            if (faceTile == null) {
                continue;
            }

            IFluidHandler handler = faceTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction).orElse(CapabilityUtils.EMPTY_FLUID);

            if (handler == CapabilityUtils.EMPTY_FLUID) {
                continue;
            }

            for (FluidTank fluidTank : simple.asArray()) {

                FluidStack tankFluid = fluidTank.getFluid();

                handler.fill(tankFluid, FluidAction.EXECUTE);

            }
        }
    }

}
