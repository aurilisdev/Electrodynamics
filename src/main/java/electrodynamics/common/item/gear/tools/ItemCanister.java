package electrodynamics.common.item.gear.tools;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.References;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Acts as a universal canister container. Can hold a maximum of 2000 mB of
 * fluid.
 * 
 * @author skip999
 *
 */
public class ItemCanister extends Item {

    public static final int MAX_FLUID_CAPACITY = 1000;
    public static final Fluid EMPTY_FLUID = Fluids.EMPTY;

    public ItemCanister() {
	super(new Item.Properties().maxStackSize(1).group(References.CORETAB));
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
	if (isInGroup(group)) {
	    items.add(new ItemStack(this));
	    ArrayList<Fluid> FLUIDS = new ArrayList<>(ForgeRegistries.FLUIDS.getValues());
	    if (CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY != null) {
		for (int i = 0; i < FLUIDS.size(); i++) {
		    ItemStack temp = new ItemStack(this);
		    Fluid fluid = FLUIDS.get(i);
		    if (!fluid.isEquivalentTo(EMPTY_FLUID)) {
			temp.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(h -> {
			    h.fill(new FluidStack(fluid, MAX_FLUID_CAPACITY), FluidAction.EXECUTE);
			});
			items.add(temp);
		    }
		}
	    }
	}
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
	return new FluidHandlerItemStack.SwapEmpty(stack, stack, 1000);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
	if (CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY != null) {
	    stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(h -> {
		if (!((FluidHandlerItemStack.SwapEmpty) h).getFluid().getFluid().isEquivalentTo(EMPTY_FLUID)) {
		    FluidHandlerItemStack.SwapEmpty cap = (FluidHandlerItemStack.SwapEmpty) h;
		    tooltip.add(new StringTextComponent(cap.getFluidInTank(0).getAmount() + " mB"));
		    tooltip.add(new StringTextComponent(cap.getFluid().getDisplayName().getString()));
		}
	    });
	}
	super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
	return ActionResult.resultFail(playerIn.getHeldItem(handIn));
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
	boolean show = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).map(h -> {
	    FluidHandlerItemStack.SwapEmpty cap = (FluidHandlerItemStack.SwapEmpty) h;
	    return !cap.getFluid().getFluid().isEquivalentTo(EMPTY_FLUID);
	}).orElse(false);
	return show;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
	double amt = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).map(h -> {
	    FluidHandlerItemStack.SwapEmpty cap = (FluidHandlerItemStack.SwapEmpty) h;
	    return cap.getFluid().getAmount() / cap.getTankCapacity(0);
	}).orElse(1);
	return 1.0 - amt;
    }

    @Override
    public boolean hasContainerItem() {
	return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
	return new ItemStack(new ItemCanister());
    }

}
