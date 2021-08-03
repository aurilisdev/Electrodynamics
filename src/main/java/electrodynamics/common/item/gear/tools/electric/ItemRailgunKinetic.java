package electrodynamics.common.item.gear.tools.electric;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.common.entity.projectile.ElectrodynamicsProjectile;
import electrodynamics.common.entity.projectile.types.metalrod.HSLASteelRod;
import electrodynamics.common.entity.projectile.types.metalrod.StainlessSteelRod;
import electrodynamics.common.entity.projectile.types.metalrod.SteelRod;
import electrodynamics.common.item.gear.tools.electric.utils.Railgun;
import electrodynamics.common.item.subtype.SubtypeRod;
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemRailgunKinetic extends Railgun {

    public static final double JOULES_PER_SHOT = 100000.0;
    private static final int OVERHEAT_TEMPERATURE = 400;
    private static final int TEMPERATURE_PER_SHOT = 300;
    private static final double TEMPERATURE_REDUCED_PER_TICK = 2.0;
    private static final double OVERHEAT_WARNING_THRESHOLD = 0.75;

    public ItemRailgunKinetic(ElectricItemProperties properties) {
	super(properties, OVERHEAT_TEMPERATURE, OVERHEAT_WARNING_THRESHOLD, TEMPERATURE_REDUCED_PER_TICK);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
	ItemStack gunStack;
	ItemStack ammoStack;

	if (handIn == Hand.MAIN_HAND) {
	    gunStack = playerIn.getHeldItemMainhand();
	    ammoStack = playerIn.getHeldItemOffhand();
	} else {
	    gunStack = playerIn.getHeldItemOffhand();
	    ammoStack = playerIn.getHeldItemMainhand();
	}

	if (!worldIn.isRemote) {
	    ItemRailgunKinetic railgun = (ItemRailgunKinetic) gunStack.getItem();

	    if (railgun.getJoulesStored(gunStack) >= JOULES_PER_SHOT) {
		if (validAmmo(ammoStack) && railgun.getTemperatureStored(gunStack) <= OVERHEAT_TEMPERATURE - TEMPERATURE_PER_SHOT) {
		    ElectrodynamicsProjectile projectile = null;

		    if (ItemStack.areItemsEqual(ammoStack, new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeRod.steel)))) {
			projectile = new SteelRod(playerIn, worldIn);
		    } else if (ItemStack.areItemsEqual(ammoStack,
			    new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeRod.stainlesssteel)))) {
			projectile = new StainlessSteelRod(playerIn, worldIn);
		    } else if (ItemStack.areItemsEqual(ammoStack, new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeRod.hslasteel)))) {
			projectile = new HSLASteelRod(playerIn, worldIn);
		    }

		    if (projectile != null) {
			railgun.extractPower(gunStack, JOULES_PER_SHOT, false);
			worldIn.playSound(null, playerIn.getPosition(), SoundRegister.SOUND_RAILGUNKINETIC.get(), SoundCategory.PLAYERS, 1, 1);
			projectile.setItem(ammoStack);
			projectile.setNoGravity(true);
			projectile.setShooter(playerIn);
			projectile.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0f, 20f, 1.0F);
			if (!worldIn.isRemote) {
			    worldIn.addEntity(projectile);
			}
			railgun.recieveHeat(gunStack, TransferPack.temperature(TEMPERATURE_PER_SHOT), false);
			ammoStack.shrink(1);
		    }
		} else {
		    worldIn.playSound(null, playerIn.getPosition(), SoundRegister.SOUND_RAILGUNKINETIC_NOAMMO.get(), SoundCategory.PLAYERS, 1, 1);
		}
	    } else {
		worldIn.playSound(null, playerIn.getPosition(), SoundRegister.SOUND_RAILGUNKINETIC_NOAMMO.get(), SoundCategory.PLAYERS, 1, 1);
	    }
	}
	return ActionResult.resultPass(gunStack);
    }

    private static boolean validAmmo(ItemStack ammoStack) {
	if (ammoStack.isEmpty()) {
	    return false;
	}
	Ingredient steelRod = Ingredient.fromStacks(new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeRod.steel)));
	Ingredient stainlessSteelRod = Ingredient.fromStacks(new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeRod.stainlesssteel)));
	Ingredient hslaSteelRod = Ingredient.fromStacks(new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeRod.hslasteel)));

	/*
	 * for(ItemStack stack: steelRod.getMatchingStacks()) {
	 * Electrodynamics.LOGGER.info(stack.toString()); } for(ItemStack stack:
	 * stainlessSteelRod.getMatchingStacks()) {
	 * Electrodynamics.LOGGER.info(stack.toString()); } for(ItemStack stack:
	 * hslaSteelRod.getMatchingStacks()) {
	 * Electrodynamics.LOGGER.info(stack.toString()); }
	 */
	return steelRod.test(ammoStack) || stainlessSteelRod.test(ammoStack) || hslaSteelRod.test(ammoStack);
    }
}
