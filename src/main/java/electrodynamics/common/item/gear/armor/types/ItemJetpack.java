package electrodynamics.common.item.gear.armor.types;

import java.util.ArrayList;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.multicapability.JetpackCapability;
import electrodynamics.api.capability.types.intstorage.CapabilityIntStorage;
import electrodynamics.api.fluid.RestrictedFluidHandlerItemStack;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.client.KeyBinds;
import electrodynamics.common.item.gear.armor.ICustomArmor;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.prefab.utilities.CapabilityUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

public class ItemJetpack extends ArmorItem {

	public static final Fluid EMPTY_FLUID = Fluids.EMPTY;
	public static final int MAX_CAPACITY = 2000;
	
	public static final int USAGE_PER_TICK = 1;
	public static final double VERT_SPEED_INCREASE = 0.5;
	public static final double TERMINAL_VELOCITY = 1;
	
	public ItemJetpack() {
		super(Jetpack.JETPACK, EquipmentSlot.CHEST, new Item.Properties().tab(References.CORETAB).stacksTo(1));
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
		CapabilityIntStorage number = new CapabilityIntStorage();
		number.setInt(0);
		return new JetpackCapability(new RestrictedFluidHandlerItemStack(stack, stack, MAX_CAPACITY, getWhitelistedFluids()), number);
	}
	
	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
		if(allowdedIn(tab)) {
			items.add(new ItemStack(this));
			if (!CapabilityUtils.isFluidItemNull()) {
				ItemStack full = new ItemStack(this);
				Fluid fluid = getWhitelistedFluids().getSecond().get(0);
				full.getCapability(CapabilityUtils.getFluidItemCap())
					.ifPresent(h -> ((RestrictedFluidHandlerItemStack) h).fillInit(new FluidStack(fluid, MAX_CAPACITY)));
				full.getCapability(CapabilityUtils.getFluidItemCap()).ifPresent(h -> ((RestrictedFluidHandlerItemStack) h).hasInitHappened(true));
				items.add(full);

			}
		}
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flagIn) {
		if (!CapabilityUtils.isFluidItemNull()) {
			stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(h -> {
				if (!((FluidHandlerItemStack.SwapEmpty) h).getFluid().getFluid().isSame(EMPTY_FLUID)) {
					FluidHandlerItemStack.SwapEmpty cap = (FluidHandlerItemStack.SwapEmpty) h;
					tooltip.add(
							new TextComponent(cap.getFluidInTank(0).getAmount() + " / " + MAX_CAPACITY + " mB").withStyle(ChatFormatting.GRAY));
				}
			});
		}
		
		int mode = stack.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).map(m -> m.getInt()).orElse(1);
		
		Component modeTip  = switch(mode) {
		case 0 -> new TranslatableComponent("tooltip.jetpack.mode").withStyle(ChatFormatting.GRAY).append(new TranslatableComponent("tooltip.jetpack.moderegular").withStyle(ChatFormatting.GREEN));
		case 1 -> new TranslatableComponent("tooltip.jetpack.mode").withStyle(ChatFormatting.GRAY).append(new TranslatableComponent("tooltip.jetpack.modehover").withStyle(ChatFormatting.AQUA));
		case 2 -> new TranslatableComponent("tooltip.jetpack.mode").withStyle(ChatFormatting.GRAY).append(new TranslatableComponent("tooltip.jetpack.modeoff").withStyle(ChatFormatting.RED));
		default -> new TextComponent("");
		};
		
		tooltip.add(modeTip);
		
		super.appendHoverText(stack, world, tooltip, flagIn);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
		super.inventoryTick(stack, world, entity, slot, isSelected);
		if(entity instanceof Player player) {
			//slot check catches ~99% of issues; still bugs if on hot bar slot #2
			if(slot == 2 && ItemUtils.testItems(player.getItemBySlot(EquipmentSlot.CHEST).getItem(), stack.getItem())) {
				int mode = stack.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).map(m -> m.getInt()).orElse(2);
				boolean enoughFuel = stack.getCapability(CapabilityUtils.getFluidItemCap()).map(m -> m.getFluidInTank(0).getAmount() >= ItemJetpack.USAGE_PER_TICK).orElse(false);
				boolean isDown = KeyBinds.jetpackAscend.isDown();
				if(enoughFuel) {
					if(mode == 0 && isDown) {
						ascendWithJetpack(ItemJetpack.VERT_SPEED_INCREASE, ItemJetpack.TERMINAL_VELOCITY, player);
						useGas(stack);
					} else if(mode == 1 && player.fallDistance > 0) {
						hoverWithJetpack(player);
						if(isDown) {
							ascendWithJetpack(ItemJetpack.VERT_SPEED_INCREASE / 2, ItemJetpack.TERMINAL_VELOCITY, player);
						}
						useGas(stack);
					}
				} 
			}
		}
	}
	
	@Override
	public boolean canBeDepleted() {
		return false;
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}
	
	@Override
	public boolean isValidRepairItem(ItemStack stack1, ItemStack stack2) {
		return false;
	}
	
	@Override
	public int getBarWidth(ItemStack stack) {
		return (int) Math.round(stack.getCapability(CapabilityUtils.getFluidItemCap()).map(h -> {
			RestrictedFluidHandlerItemStack cap = (RestrictedFluidHandlerItemStack) h;
			return 13.0 * cap.getFluidInTank(0).getAmount() / cap.getTankCapacity(0);
		}).orElse(13.0));
	}
	
	public Pair<List<ResourceLocation>, List<Fluid>> getWhitelistedFluids() {
		List<ResourceLocation> tags = new ArrayList<>();
		List<Fluid> fluids = new ArrayList<>();
		tags.add(ElectrodynamicsTags.Fluids.HYDROGEN.getName());
		fluids.add(DeferredRegisters.fluidHydrogen);
		return Pair.of(tags, fluids);
	}
	
	private static void ascendWithJetpack(double speed, double termVelocity, Player player) {
		player.resetFallDistance();
		Vec3 currMovement = player.getDeltaMovement();
		double y = currMovement.y + speed >= termVelocity ? termVelocity : currMovement.y + speed;
		currMovement = new Vec3(currMovement.x, y, currMovement.z);
		player.setDeltaMovement(currMovement);
	}
	
	private static void hoverWithJetpack(Player player) {
		if(player.isShiftKeyDown()) {
			player.resetFallDistance();
			Vec3 currMovement = player.getDeltaMovement();
			currMovement = new Vec3(currMovement.x, -0.3, currMovement.z);
			player.setDeltaMovement(currMovement);
		} else {
			Vec3 currMovement = player.getDeltaMovement();
			currMovement = new Vec3(currMovement.x, 0, currMovement.z);
			player.setDeltaMovement(currMovement);
		}
		
	}
	
	private void useGas(ItemStack stack) {
		stack.getCapability(CapabilityUtils.getFluidItemCap()).ifPresent(h -> h.drain(ItemJetpack.USAGE_PER_TICK, FluidAction.EXECUTE));
	}
	
	public enum Jetpack implements ICustomArmor {
		JETPACK;

		@Override
		public int getDurabilityForSlot(EquipmentSlot slotIn) {
			return 100;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlot slotIn) {
			return 1;
		}

		@Override
		public SoundEvent getEquipSound() {
			return SoundEvents.ARMOR_EQUIP_IRON;
		}

		@Override
		public String getName() {
			return References.ID + ":nvgs";
		}

		@Override
		public float getToughness() {
			return 0.0F;
		}

		@Override
		public float getKnockbackResistance() {
			return 0.0F;
		}
		
	}

}
