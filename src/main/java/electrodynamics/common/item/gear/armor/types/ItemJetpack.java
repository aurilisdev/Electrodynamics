package electrodynamics.common.item.gear.armor.types;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.mojang.datafixers.util.Pair;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.References;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.multicapability.JetpackCapability;
import electrodynamics.api.capability.types.intstorage.CapabilityIntStorage;
import electrodynamics.api.fluid.RestrictedFluidHandlerItemStack;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.client.ClientRegister;
import electrodynamics.client.KeyBinds;
import electrodynamics.client.render.model.armor.types.ModelJetpack;
import electrodynamics.common.item.gear.armor.ICustomArmor;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.prefab.utilities.CapabilityUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
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
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

public class ItemJetpack extends ArmorItem {
	
	public static final Fluid EMPTY_FLUID = Fluids.EMPTY;
	public static final int MAX_CAPACITY = 30000;
	
	public static final int USAGE_PER_TICK = 1;
	public static final double VERT_SPEED_INCREASE = 0.5;
	public static final double TERMINAL_VELOCITY = 1;
	
	private static final String ARMOR_TEXTURE_LOCATION = References.ID + ":textures/model/armor/jetpack.png";
	
	public ItemJetpack() {
		super(Jetpack.JETPACK, EquipmentSlot.CHEST, new Item.Properties().tab(References.CORETAB).stacksTo(1));
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		consumer.accept(new IItemRenderProperties() {
			@Override
			public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, A properties) {

				ModelJetpack<LivingEntity> model = new ModelJetpack<>(ClientRegister.JETPACK.bakeRoot());

				model.crouching = properties.crouching;
				model.riding = properties.riding;
				model.young = properties.young;

				return (A) model;
			}
		});
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
		CapabilityIntStorage number = new CapabilityIntStorage(2);
		number.setInt(0, 0);
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
		
		int mode = stack.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).map(m -> m.getInt(0)).orElse(1);
		
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
				stack.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).ifPresent(h -> h.setInt(1, h.getInt(1) + 1));
				int mode = stack.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).map(m -> m.getInt(0)).orElse(2);
				boolean enoughFuel = stack.getCapability(CapabilityUtils.getFluidItemCap()).map(m -> m.getFluidInTank(0).getAmount() >= ItemJetpack.USAGE_PER_TICK).orElse(false);
				boolean isDown = KeyBinds.jetpackAscend.isDown();
				if(enoughFuel) {
					int ticks = stack.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).map(m -> m.getInt(1)).orElse(10);
					if(mode == 0 && isDown) {
						if(ticks % 10 == 0) {
							player.playSound(SoundRegister.SOUND_JETPACK.get(), 1, 1);
						}
						ascendWithJetpack(ItemJetpack.VERT_SPEED_INCREASE, ItemJetpack.TERMINAL_VELOCITY, player);
						useGas(stack);
					} else if(mode == 1 && isDown) {
						if(ticks % 10 == 0) {
							player.playSound(SoundRegister.SOUND_JETPACK.get(), 1, 1);
						}
						ascendWithJetpack(ItemJetpack.VERT_SPEED_INCREASE / 2, ItemJetpack.TERMINAL_VELOCITY / 2, player);
						useGas(stack);
					} else if(mode == 1 && player.fallDistance > 0) {
						if(ticks % 10 == 0) {
							player.playSound(SoundRegister.SOUND_JETPACK.get(), 1, 1);
						}
						hoverWithJetpack(player);
						useGas(stack);
					}
				} 
				stack.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).ifPresent(h -> {
					if(h.getInt(1) > 100) {
						h.setInt(1, 0);
					}
				});
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
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return ARMOR_TEXTURE_LOCATION;
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
			return References.ID + ":jetpack";
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
