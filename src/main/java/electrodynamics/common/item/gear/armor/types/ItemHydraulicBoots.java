package electrodynamics.common.item.gear.armor.types;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import electrodynamics.api.References;
import electrodynamics.api.capability.types.fluid.RestrictedFluidHandlerItemStack;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.client.ClientRegister;
import electrodynamics.client.render.model.armor.types.ModelHydraulicBoots;
import electrodynamics.common.item.gear.armor.ICustomArmor;
import electrodynamics.common.item.gear.armor.ItemElectrodynamicsArmor;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsCreativeTabs;
import electrodynamics.registers.ElectrodynamicsFluids;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemHydraulicBoots extends ItemElectrodynamicsArmor {

	public static final int MAX_CAPACITY = 2000;

	private static final String TEXTURE_LOCATION = References.ID + ":textures/model/armor/hydraulicboots.png";

	public ItemHydraulicBoots() {
		super(HydraulicBoots.HYDRAULIC_BOOTS, Type.BOOTS, new Item.Properties().stacksTo(1), () -> ElectrodynamicsCreativeTabs.MAIN.get());
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
		return new RestrictedFluidHandlerItemStack(stack, MAX_CAPACITY).setValidator(getPredicate());
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			@Override
			public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> properties) {

				ModelHydraulicBoots<LivingEntity> model = new ModelHydraulicBoots<>(ClientRegister.HYDRAULIC_BOOTS.bakeRoot());

				model.crouching = properties.crouching;
				model.riding = properties.riding;
				model.young = properties.young;

				return model;
			}
		});
	}

	@Override
	public void addCreativeModeItems(CreativeModeTab tab, List<ItemStack> items) {

		super.addCreativeModeItems(tab, items);
		if (!CapabilityUtils.isFluidItemNull()) {
			ItemStack full = new ItemStack(this);
			full.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(h -> ((RestrictedFluidHandlerItemStack) h).setFluid(new FluidStack(ElectrodynamicsFluids.fluidHydraulic, MAX_CAPACITY)));
			items.add(full);

		}

	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flagIn) {
		if (!CapabilityUtils.isFluidItemNull()) {
			stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(h -> tooltip.add(ElectroTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(h.getFluidInTank(0).getAmount()), ChatFormatter.formatFluidMilibuckets(MAX_CAPACITY)).withStyle(ChatFormatting.GRAY)));
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
		return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).map(m -> {
			RestrictedFluidHandlerItemStack cap = (RestrictedFluidHandlerItemStack) m;
			return 13.0 * cap.getFluidInTank(0).getAmount() / cap.getTankCapacity(0) < 13.0;
		}).orElse(false);
	}

	@Override
	public int getBarWidth(ItemStack stack) {
		return staticGetBarWidth(stack);
	}

	protected static int staticGetBarWidth(ItemStack stack) {
		return (int) Math.round(stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).map(h -> {
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

	public static Predicate<FluidStack> getPredicate() {
		return fluid -> ForgeRegistries.FLUIDS.tags().getTag(ElectrodynamicsTags.Fluids.HYDRAULIC_FLUID).contains(fluid.getFluid());
	}

	public enum HydraulicBoots implements ICustomArmor {
		HYDRAULIC_BOOTS;

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

		@Override
		public int getDurabilityForType(Type pType) {
			return 100;
		}

		@Override
		public int getDefenseForType(Type pType) {
			return 1;
		}

	}

}
