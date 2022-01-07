package electrodynamics.common.item.gear.tools.electric;

import java.util.List;
import java.util.function.Predicate;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.prefab.item.ElectricItemProperties;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ItemMechanizedCrossbow extends ProjectileWeaponItem implements IItemElectric {

	private final ElectricItemProperties properties;
	
	public static final int PROJECTILE_RANGE = 20;
	public static final int PROJECTILE_SPEED = 5;
	
	public ItemMechanizedCrossbow(ElectricItemProperties properties) {
		super(properties);
		this.properties = properties;
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		 if (!world.isClientSide) {
	        ItemStack crossbow = player.getItemInHand(hand); 
			ItemStack arrow = getAmmo(player);
	        Projectile projectile = getArrow(world, player, crossbow, arrow);
	        if(!arrow.isEmpty()) {
	        	Vec3 vec31 = player.getUpVector(1.0F);
	            Quaternion quaternion = new Quaternion(new Vector3f(vec31), 0, true);
	            Vec3 vec3 = player.getViewVector(1.0F);
	            Vector3f vector3f = new Vector3f(vec3);
	            vector3f.transform(quaternion);
	            projectile.shoot((double)vector3f.x(), (double)vector3f.y(), (double)vector3f.z(), PROJECTILE_SPEED, 1);
	            world.addFreshEntity(projectile);
		        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, 1);
		        return InteractionResultHolder.consume(crossbow);
	        } 
	    }
		return super.use(world, player, hand);
	}
	
	private static AbstractArrow getArrow(Level world_, LivingEntity entity, ItemStack crossbow, ItemStack ammo) {
	      ArrowItem arrowitem = (ArrowItem)(ammo.getItem() instanceof ArrowItem ? ammo.getItem() : Items.ARROW);
	      AbstractArrow abstractarrow = arrowitem.createArrow(world_, ammo, entity);
	      if (entity instanceof Player) {
	         abstractarrow.setCritArrow(true);
	      }

	      abstractarrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);
	      abstractarrow.setShotFromCrossbow(true);
	      int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, crossbow);
	      if (i > 0) {
	         abstractarrow.setPierceLevel((byte)i);
	      }

	      return abstractarrow;
	}
	
	private ItemStack getAmmo(Player player) {
		Inventory playerInv = player.getInventory();
		for(Item item : ItemTags.ARROWS.getValues()) {
			int loc = playerInv.findSlotMatchingItem(new ItemStack(item));
			if(loc > -1) {
				ItemStack arrow = playerInv.getItem(loc);
				if(getAllSupportedProjectiles().test(arrow)) {
					return arrow;
				}
			}
		}
		return ItemStack.EMPTY;
	}
	
	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		if (allowdedIn(group)) {
			ItemStack charged = new ItemStack(this);
			IItemElectric.setEnergyStored(charged, properties.capacity);
			items.add(charged);
			ItemStack empty = new ItemStack(this);
			IItemElectric.setEnergyStored(empty, 0);
			items.add(empty);
		}
	}
	
	@Override
	public int getBarWidth(ItemStack stack) {
		return (int) Math.round(13.0f * getJoulesStored(stack) / properties.capacity);
	}

	@Override
	public boolean isBarVisible(ItemStack stack) {
		return getJoulesStored(stack) < properties.capacity;
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslatableComponent("tooltip.item.electric.info").withStyle(ChatFormatting.GRAY)
				.append(new TextComponent(ChatFormatter.getElectricDisplayShort(getJoulesStored(stack), ElectricUnit.JOULES))));
		tooltip.add(new TranslatableComponent("tooltip.item.electric.voltage",
				ChatFormatter.getElectricDisplayShort(properties.receive.getVoltage(), ElectricUnit.VOLTAGE) + " / "
						+ ChatFormatter.getElectricDisplayShort(properties.extract.getVoltage(), ElectricUnit.VOLTAGE))
								.withStyle(ChatFormatting.RED));
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
