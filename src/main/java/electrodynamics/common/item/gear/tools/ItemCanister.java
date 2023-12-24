package electrodynamics.common.item.gear.tools;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.capability.types.fluid.RestrictedFluidHandlerItemStack;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.inventory.InventoryTickConsumer;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemCanister extends Item {

	public static final int MAX_FLUID_CAPACITY = 5000;
	public static final Fluid EMPTY_FLUID = Fluids.EMPTY;

	public static final List<InventoryTickConsumer> INVENTORY_TICK_CONSUMERS = new ArrayList<>();

	public ItemCanister(Item.Properties itemProperty) {
		super(itemProperty);
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {

		if (!allowdedIn(group)) {
			return;
		}

		items.add(new ItemStack(this));
		if (!CapabilityUtils.isFluidItemNull()) {
			for (Fluid liq : ForgeRegistries.FLUIDS.getValues()) {
				if (liq == Fluids.EMPTY || liq == Fluids.FLOWING_LAVA || liq == Fluids.FLOWING_WATER) {
					continue;
				}
				ItemStack temp = new ItemStack(this);
				temp.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(h -> {
					((RestrictedFluidHandlerItemStack) h).fill(new FluidStack(liq, MAX_FLUID_CAPACITY), FluidAction.EXECUTE);
				});
				items.add(temp);

			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World level, Entity entity, int slot, boolean isSelected) {
		super.inventoryTick(stack, level, entity, slot, isSelected);
		INVENTORY_TICK_CONSUMERS.forEach(consumer -> consumer.apply(stack, level, entity, slot, isSelected));
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
		return new RestrictedFluidHandlerItemStack(stack, stack, MAX_FLUID_CAPACITY);
	}

	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if (!CapabilityUtils.isFluidItemNull()) {
			stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(h -> {
				if (!((RestrictedFluidHandlerItemStack) h).getFluid().isEmpty()) {
					RestrictedFluidHandlerItemStack cap = (RestrictedFluidHandlerItemStack) h;
					tooltip.add(ElectroTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(cap.getFluidInTank(0).getAmount()), ChatFormatter.formatFluidMilibuckets(MAX_FLUID_CAPACITY)).withStyle(TextFormatting.GRAY));
					tooltip.add(cap.getFluid().getDisplayName().copy().withStyle(TextFormatting.DARK_GRAY));
				}
			});
		}
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return (int) Math.round(stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).map(h -> {
			RestrictedFluidHandlerItemStack cap = (RestrictedFluidHandlerItemStack) h;
			return 1.0 - (double) cap.getFluidInTank(0).getAmount() / (double) cap.getTankCapacity(0);
		}).orElse(1.0));
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).map(h -> !((RestrictedFluidHandlerItemStack) h).getFluid().getFluid().isSame(EMPTY_FLUID)).orElse(false);
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		useCanister(worldIn, playerIn, handIn);
		return ActionResult.pass(playerIn.getItemInHand(handIn));
	}

	public void useCanister(World world, PlayerEntity player, Hand hand) {

		ItemStack stack = player.getItemInHand(hand);

		RayTraceResult trace = getPlayerPOVHitResult(world, player, FluidMode.ANY);

		if (world.isClientSide || trace.getType() == Type.MISS || trace.getType() == Type.ENTITY) {
			return;
		}

		BlockRayTraceResult blockTrace = (BlockRayTraceResult) trace;

		BlockPos pos = blockTrace.getBlockPos();

		BlockState state = world.getBlockState(pos);

		if (!state.getFluidState().isSource() || state.getFluidState().isEmpty()) {
			return;
		}

		FluidStack sourceFluid = new FluidStack(state.getFluidState().getType(), 1000);

		if (!CapabilityUtils.hasFluidItemCap(stack)) {
			return;
		}

		IFluidHandlerItem handler = CapabilityUtils.getFluidHandlerItem(stack);

		int accepted = handler.fill(sourceFluid, FluidAction.SIMULATE);

		if (accepted < 1000) {
			return;
		}

		handler.fill(sourceFluid, FluidAction.EXECUTE);

		world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

		world.playSound(null, player.blockPosition(), SoundEvents.BUCKET_FILL, SoundCategory.PLAYERS, 1, 1);
	}

}