package electrodynamics.common.item.gear.armor.types;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.mojang.datafixers.util.Pair;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.api.fluid.RestrictedFluidHandlerItemStack;
import electrodynamics.client.ClientRegister;
import electrodynamics.client.render.model.armor.types.ModelHydraulicBoots;
import electrodynamics.common.item.gear.armor.ICustomArmor;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.prefab.utilities.CapabilityUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class ItemHydraulicBoots extends ArmorItem {

	public static final Fluid EMPTY_FLUID = Fluids.EMPTY;
	public static final int MAX_CAPACITY = 2000;

	private static final String TEXTURE_LOCATION = References.ID + ":textures/model/armor/hydraulicboots.png";

	public ItemHydraulicBoots() {
		super(HydraulicBoots.HYDRAULIC_BOOTS, EquipmentSlot.FEET, new Item.Properties().tab(References.CORETAB).stacksTo(1));
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
		return new RestrictedFluidHandlerItemStack(stack, stack, MAX_CAPACITY, getWhitelistedFluids());
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		consumer.accept(new IItemRenderProperties() {
			@Override
			public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, A properties) {

				ModelHydraulicBoots<LivingEntity> model = new ModelHydraulicBoots<>(ClientRegister.HYDRAULIC_BOOTS.bakeRoot());

				model.crouching = properties.crouching;
				model.riding = properties.riding;
				model.young = properties.young;

				return (A) model;
			}
		});
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
		if (allowdedIn(tab)) {
			items.add(new ItemStack(this));
			if (!CapabilityUtils.isFluidItemNull()) {
				ItemStack full = new ItemStack(this);
				Fluid fluid = getWhitelistedFluids().getSecond().get(0);
				full.getCapability(CapabilityUtils.getFluidItemCap()).ifPresent(h -> ((RestrictedFluidHandlerItemStack) h).fillInit(new FluidStack(fluid, MAX_CAPACITY)));
				full.getCapability(CapabilityUtils.getFluidItemCap()).ifPresent(h -> ((RestrictedFluidHandlerItemStack) h).hasInitHappened(true));
				items.add(full);

			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flagIn) {
		if (!CapabilityUtils.isFluidItemNull()) {
			stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(h -> {
				tooltip.add(new TextComponent(h.getFluidInTank(0).getAmount() + " / " + MAX_CAPACITY + " mB").withStyle(ChatFormatting.GRAY));
			});
		}
		super.appendHoverText(stack, world, tooltip, flagIn);
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
	public boolean isBarVisible(ItemStack stack) {
		return staticIsBarVisible(stack);
	}
	
	protected static boolean staticIsBarVisible(ItemStack stack) {
		return stack.getCapability(CapabilityUtils.getFluidItemCap()).map(m -> {
			RestrictedFluidHandlerItemStack cap = (RestrictedFluidHandlerItemStack) m;
			return 13.0 * cap.getFluidInTank(0).getAmount() / cap.getTankCapacity(0) < 13.0;
		}).orElse(false);
	}

	@Override
	public int getBarWidth(ItemStack stack) {
		return staticGetBarWidth(stack);
	}
	
	protected static int staticGetBarWidth(ItemStack stack) {
		return (int) Math.round(stack.getCapability(CapabilityUtils.getFluidItemCap()).map(h -> {
			RestrictedFluidHandlerItemStack cap = (RestrictedFluidHandlerItemStack) h;
			return 13.0 * cap.getFluidInTank(0).getAmount() / cap.getTankCapacity(0);
		}).orElse(13.0));
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return TEXTURE_LOCATION;
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged;
	}

	public Pair<List<ResourceLocation>, List<Fluid>> getWhitelistedFluids() {
		return staticGetWhitelistedFluids();
	}
	
	protected static Pair<List<ResourceLocation>, List<Fluid>> staticGetWhitelistedFluids() {
		List<ResourceLocation> tags = new ArrayList<>();
		List<Fluid> fluids = new ArrayList<>();
		tags.add(ElectrodynamicsTags.Fluids.HYDRAULIC_FLUID.getName());
		fluids.add(DeferredRegisters.fluidHydraulic);
		return Pair.of(tags, fluids);
	}

	public enum HydraulicBoots implements ICustomArmor {
		HYDRAULIC_BOOTS;

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
			return References.ID + ":hydraulic_boots";
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
