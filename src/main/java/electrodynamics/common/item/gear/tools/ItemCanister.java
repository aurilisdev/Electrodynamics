package electrodynamics.common.item.gear.tools;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.capability.CapabilityUtils;
import electrodynamics.api.fluid.RestrictedFluidHandlerItemStack;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemCanister extends Item {

    public static final int MAX_FLUID_CAPACITY = 5000;
    public static final Fluid EMPTY_FLUID = Fluids.EMPTY;

    public ItemCanister(Item.Properties itemProperty) {
	super(itemProperty);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
	if (isInGroup(group)) {
	    items.add(new ItemStack(this));
	    if (!CapabilityUtils.isFluidItemNull()) {
		for (Fluid fluid : getWhitelistedFluids()) {
		    ItemStack temp = new ItemStack(this);
		    CapabilityUtils.fill(temp, new FluidStack(fluid, MAX_FLUID_CAPACITY));
		    items.add(temp);
		}

	    }
	}
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
	return new RestrictedFluidHandlerItemStack(stack, stack, MAX_FLUID_CAPACITY, getWhitelistedFluids());
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
	if (!CapabilityUtils.isFluidItemNull()) {
	    stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(h -> {
		if (!((FluidHandlerItemStack.SwapEmpty) h).getFluid().getFluid().isEquivalentTo(EMPTY_FLUID)) {
		    FluidHandlerItemStack.SwapEmpty cap = (FluidHandlerItemStack.SwapEmpty) h;
		    tooltip.add(new StringTextComponent(cap.getFluidInTank(0).getAmount() + " / " + MAX_FLUID_CAPACITY + " mB")
			    .mergeStyle(TextFormatting.GRAY));
		    tooltip.add(new StringTextComponent(cap.getFluid().getDisplayName().getString()).mergeStyle(TextFormatting.DARK_GRAY));
		}
	    });
	}
	super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
	return stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY)
		.map(h -> !((RestrictedFluidHandlerItemStack) h).getFluid().getFluid().isEquivalentTo(EMPTY_FLUID)).orElse(false);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
	return 1.0 - stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).map(h -> {
	    RestrictedFluidHandlerItemStack cap = (RestrictedFluidHandlerItemStack) h;
	    return (double) cap.getFluidInTank(0).getAmount() / (double) cap.getTankCapacity(0);
	}).orElse(1.0);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
	useCanister(worldIn, playerIn, handIn);
	return ActionResult.resultPass(playerIn.getHeldItem(handIn));
    }

    public void useCanister(World world, PlayerEntity player, Hand hand) {
	ItemStack stack = player.getHeldItem(hand);
	RayTraceResult trace = rayTrace(world, player, FluidMode.ANY);
	if (!world.isRemote && trace.getType() != Type.MISS && trace.getType() != Type.ENTITY) {
	    BlockRayTraceResult blockTrace = (BlockRayTraceResult) trace;
	    BlockPos pos = blockTrace.getPos();
	    BlockState state = world.getBlockState(pos);
	    if (state.getFluidState().isSource() && !state.getFluidState().getFluid().isEquivalentTo(Fluids.EMPTY)) {
		FluidStack sourceFluid = new FluidStack(state.getFluidState().getFluid(), 1000);
		boolean validFluid = CapabilityUtils.canFillItemStack(stack, sourceFluid);
		if (validFluid) {
		    int amtFilled = CapabilityUtils.simFill(stack, sourceFluid);
		    if (amtFilled >= 1000) {
			CapabilityUtils.fill(stack, sourceFluid);
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
			world.playSound(null, player.getPosition(), SoundEvents.ITEM_BUCKET_FILL, SoundCategory.PLAYERS, 1, 1);
		    }
		}
	    }
	}
    }

    public ArrayList<Fluid> getWhitelistedFluids() {
	ArrayList<Fluid> whitelisted = new ArrayList<>();
	for (Fluid fluid : ForgeRegistries.FLUIDS.getValues()) {
	    // have to compare registry name otherwise will have major loading error
	    if (fluid.getFilledBucket().getRegistryName().equals(DeferredRegisters.ITEM_CANISTERREINFORCED.get().getRegistryName())) {
		whitelisted.add(fluid);
	    }
	}
	whitelisted.add(Fluids.WATER);
	return whitelisted;
    }

}
