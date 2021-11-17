package electrodynamics.common.item.gear.tools;

import java.util.ArrayList;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.capability.CapabilityUtils;
import electrodynamics.api.fluid.RestrictedFluidHandlerItemStack;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemCanister extends Item {

    public static final int MAX_FLUID_CAPACITY = 5000;
    public static final Fluid EMPTY_FLUID = Fluids.EMPTY;

    public static List<ResourceLocation> TAG_NAMES = new ArrayList<>();

    public ItemCanister(Item.Properties itemProperty) {
	super(itemProperty);
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
	if (allowdedIn(group)) {
	    items.add(new ItemStack(this));
	    if (!CapabilityUtils.isFluidItemNull()) {
		for (Fluid liq : getWhitelistedFluids().getSecond()) {
		    ItemStack temp = new ItemStack(this);
		    // For init only; do not use anywhere else!
		    temp.getCapability(CapabilityUtils.getFluidItemCap()).ifPresent(h -> {
			((RestrictedFluidHandlerItemStack) h).fillInit(new FluidStack(liq, MAX_FLUID_CAPACITY));
		    });
		    temp.getCapability(CapabilityUtils.getFluidItemCap()).ifPresent(h -> {
			((RestrictedFluidHandlerItemStack) h).hasInitHappened(true);
		    });
		    items.add(temp);

		}
	    }
	}
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
	return new RestrictedFluidHandlerItemStack(stack, stack, MAX_FLUID_CAPACITY, getWhitelistedFluids());
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
	if (!CapabilityUtils.isFluidItemNull()) {
	    stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(h -> {
		if (!((FluidHandlerItemStack.SwapEmpty) h).getFluid().getFluid().isSame(EMPTY_FLUID)) {
		    FluidHandlerItemStack.SwapEmpty cap = (FluidHandlerItemStack.SwapEmpty) h;
		    tooltip.add(
			    new TextComponent(cap.getFluidInTank(0).getAmount() + " / " + MAX_FLUID_CAPACITY + " mB").withStyle(ChatFormatting.GRAY));
		    tooltip.add(new TextComponent(cap.getFluid().getDisplayName().getString()).withStyle(ChatFormatting.DARK_GRAY));
		}
	    });
	}
	super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
	return stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY)
		.map(h -> !((RestrictedFluidHandlerItemStack) h).getFluid().getFluid().isSame(EMPTY_FLUID)).orElse(false);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
	return 1.0 - stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).map(h -> {
	    RestrictedFluidHandlerItemStack cap = (RestrictedFluidHandlerItemStack) h;
	    return (double) cap.getFluidInTank(0).getAmount() / (double) cap.getTankCapacity(0);
	}).orElse(1.0);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
	useCanister(worldIn, playerIn, handIn);
	return InteractionResultHolder.pass(playerIn.getItemInHand(handIn));
    }

    public void useCanister(Level world, Player player, InteractionHand hand) {
	ItemStack stack = player.getItemInHand(hand);
	HitResult trace = getPlayerPOVHitResult(world, player, net.minecraft.world.level.ClipContext.Fluid.ANY);
	if (!world.isClientSide && trace.getType() != Type.MISS && trace.getType() != Type.ENTITY) {
	    BlockHitResult blockTrace = (BlockHitResult) trace;
	    BlockPos pos = blockTrace.getBlockPos();
	    BlockState state = world.getBlockState(pos);
	    if (state.getFluidState().isSource() && !state.getFluidState().getType().isSame(Fluids.EMPTY)) {
		FluidStack sourceFluid = new FluidStack(state.getFluidState().getType(), 1000);
		boolean validFluid = CapabilityUtils.canFillItemStack(stack, sourceFluid);
		if (validFluid) {
		    int amtFilled = CapabilityUtils.simFill(stack, sourceFluid);
		    if (amtFilled >= 1000) {
			CapabilityUtils.fill(stack, sourceFluid);
			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			world.playSound(null, player.blockPosition(), SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1, 1);
		    }
		}
	    }
	}
    }

    public Pair<List<ResourceLocation>, List<Fluid>> getWhitelistedFluids() {
	List<Fluid> whitelisted = new ArrayList<>();
	for (Fluid fluid : ForgeRegistries.FLUIDS.getValues()) {
	    if (fluid.getBucket().getRegistryName().equals(DeferredRegisters.ITEM_CANISTERREINFORCED.get().getRegistryName())) {
		whitelisted.add(fluid);
	    }
	}
	whitelisted.add(Fluids.WATER);
	return Pair.of(TAG_NAMES, whitelisted);
    }

    public static void addTag(Tags.IOptionalNamedTag<Fluid> tag) {
	TAG_NAMES.add(tag.getName());
    }

}
