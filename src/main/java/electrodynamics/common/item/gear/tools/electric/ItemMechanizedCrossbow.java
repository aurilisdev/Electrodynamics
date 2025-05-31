package electrodynamics.common.item.gear.tools.electric;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.item.IItemElectric;
import voltaic.prefab.item.ElectricItemProperties;
import voltaic.prefab.utilities.VoltaicTextUtils;

public class ItemMechanizedCrossbow extends ShootableItem implements IItemElectric {

	private final ElectricItemProperties properties;

	private final Supplier<ItemGroup> creativeTab;

	public static final int JOULES_PER_SHOT = 5000;
	public static final int NUMBER_OF_SHOTS = 200;

	public static final int PROJECTILE_RANGE = 20;
	public static final int PROJECTILE_SPEED = 3;

	public ItemMechanizedCrossbow(ElectricItemProperties properties, Supplier<ItemGroup> creativeTab) {
		super(properties);
		this.properties = properties;
		this.creativeTab = creativeTab;
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack crossbow = player.getItemInHand(hand);

		if (world.isClientSide) {
			return ActionResult.pass(crossbow);
		}

		ItemMechanizedCrossbow mechanized = (ItemMechanizedCrossbow) crossbow.getItem();
		if (mechanized.getJoulesStored(crossbow) < JOULES_PER_SHOT) {
			world.playSound(null, player.blockPosition(), ElectrodynamicsSounds.SOUND_RAILGUNKINETIC_NOAMMO.get(), SoundCategory.PLAYERS, 1, 1);
			return ActionResult.pass(crossbow);
		}

		ItemStack arrow = getAmmo(player);
		ProjectileEntity projectile = getArrow(world, player, crossbow, arrow);

		if (arrow.isEmpty()) {
			world.playSound(null, player.blockPosition(), ElectrodynamicsSounds.SOUND_RAILGUNKINETIC_NOAMMO.get(), SoundCategory.PLAYERS, 1, 1);
			return ActionResult.pass(crossbow);
		}

		mechanized.extractPower(crossbow, JOULES_PER_SHOT, false);

		if (!player.isCreative()) {
			arrow.shrink(1);
		}

		Vector3d playerUpVector = player.getUpVector(1.0F);

		Quaternion quaternion = new Quaternion(new Vector3f(playerUpVector), 0, true);

		Vector3d playerViewVector = player.getViewVector(1.0F);

		Vector3f viewVector = new Vector3f(playerViewVector);

		viewVector.transform(quaternion);

		projectile.shoot(viewVector.x(), viewVector.y(), viewVector.z(), PROJECTILE_SPEED, 1);

		world.addFreshEntity(projectile);

		world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1);

		return ActionResult.pass(crossbow);
	}

	@Override
	public boolean canBeDepleted() {
		return false;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	private static AbstractArrowEntity getArrow(World world, LivingEntity entity, ItemStack crossbow, ItemStack ammo) {
		ArrowItem arrowitem = (ArrowItem) (ammo.getItem() instanceof ArrowItem ? ammo.getItem() : Items.ARROW);
		AbstractArrowEntity abstractarrow = arrowitem.createArrow(world, ammo, entity);
		if (entity instanceof PlayerEntity) {
			abstractarrow.setCritArrow(true);
		}

		abstractarrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);
		abstractarrow.setShotFromCrossbow(true);
		int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, crossbow);
		if (i > 0) {
			abstractarrow.setPierceLevel((byte) i);
		}

		return abstractarrow;
	}

	private ItemStack getAmmo(PlayerEntity player) {
		PlayerInventory playerInv = player.inventory;
		for (ItemStack stack : playerInv.items) {
			if (getAllSupportedProjectiles().test(stack)) {
				return stack;
			}
		}
		if (player.isCreative()) {
			return new ItemStack(Items.ARROW);
		}
		return ItemStack.EMPTY;
	}
	
	@Override
	protected boolean allowdedIn(ItemGroup category) {
		return creativeTab != null && (category == creativeTab.get() || category == ItemGroup.TAB_SEARCH);
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {

		if(!allowdedIn(group)) {
			return;
		}
		
		ItemStack empty = new ItemStack(this);
		IItemElectric.setEnergyStored(empty, 0);
		items.add(empty);

		ItemStack charged = new ItemStack(this);
		IItemElectric.setEnergyStored(charged, getMaximumCapacity(charged));
		items.add(charged);

	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1.0D - (getJoulesStored(stack) / getMaximumCapacity(stack));
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getJoulesStored(stack) < getMaximumCapacity(stack);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return oldStack.getItem() != newStack.getItem();
	}

	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(ElectroTextUtils.tooltip("item.electric.info", VoltaicTextUtils.ratio(ChatFormatter.getChatDisplayShort(getJoulesStored(stack), DisplayUnits.JOULES), ChatFormatter.getChatDisplayShort(getMaximumCapacity(stack), DisplayUnits.JOULES)).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY));
		tooltip.add(ElectroTextUtils.tooltip("item.electric.voltage", ChatFormatter.getChatDisplayShort(properties.receive.getVoltage(), DisplayUnits.VOLTAGE).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY));
	}

	@Override
	public ElectricItemProperties getElectricProperties() {
		return properties;
	}

	@Override
	public Predicate<ItemStack> getAllSupportedProjectiles() {
		return ARROW_ONLY;
	}

	@Override
	public int getDefaultProjectileRange() {
		return PROJECTILE_RANGE;
	}


}
