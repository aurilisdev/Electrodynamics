package electrodynamics.common.item.gear.tools.electric;

import java.util.function.Supplier;

import electrodynamics.common.entity.projectile.EntityCustomProjectile;
import electrodynamics.common.entity.projectile.types.EntityEnergyBlast;
import electrodynamics.common.item.gear.tools.electric.utils.ItemRailgun;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import voltaic.api.item.IItemTemperate;
import voltaic.prefab.item.ElectricItemProperties;

public class ItemRailgunPlasma extends ItemRailgun {

	public static final double JOULES_PER_SHOT = 250000.0;
	private static final int OVERHEAT_TEMPERATURE = 1250;
	private static final int TEMPERATURE_PER_SHOT = 300;
	private static final double TEMPERATURE_REDUCED_PER_TICK = 2.5;
	private static final double OVERHEAT_WARNING_THRESHOLD = 0.5;

	public ItemRailgunPlasma(ElectricItemProperties properties, Supplier<ItemGroup> creativeTab) {
		super(properties, creativeTab, OVERHEAT_TEMPERATURE, OVERHEAT_WARNING_THRESHOLD, TEMPERATURE_REDUCED_PER_TICK);
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack gunStack;

		if (handIn == Hand.MAIN_HAND) {
			gunStack = playerIn.getMainHandItem();
		} else {
			gunStack = playerIn.getOffhandItem();
		}

		if (worldIn.isClientSide) {
			return ActionResult.pass(gunStack);
		}

		ItemRailgunPlasma railgun = (ItemRailgunPlasma) gunStack.getItem();

		if (railgun.getJoulesStored(gunStack) < JOULES_PER_SHOT || IItemTemperate.getTemperature(gunStack) > OVERHEAT_TEMPERATURE - TEMPERATURE_PER_SHOT) {
			worldIn.playSound(null, playerIn.blockPosition(), ElectrodynamicsSounds.SOUND_RAILGUNKINETIC_NOAMMO.get(), SoundCategory.PLAYERS, 1, 1);
			return ActionResult.pass(gunStack);
		}

		EntityCustomProjectile projectile = new EntityEnergyBlast(playerIn, worldIn);
		projectile.setNoGravity(true);
		projectile.setOwner(playerIn);
		shootNoY(projectile, playerIn, playerIn.xRot, playerIn.yRot, 0F, 5f, 1.0F);
		worldIn.addFreshEntity(projectile);

		railgun.extractPower(gunStack, JOULES_PER_SHOT, false);
		worldIn.playSound(null, playerIn.blockPosition(), ElectrodynamicsSounds.SOUND_RAILGUNPLASMA_FIRE.get(), SoundCategory.PLAYERS, 1, 1);
		railgun.recieveHeat(gunStack, TEMPERATURE_PER_SHOT, false);

		return ActionResult.pass(gunStack);
	}

	private void shootNoY(ProjectileEntity projectile, Entity shooter, float xRot, float yRot, float zRot, float velocity, float inaccuracy) {
		float velX = -MathHelper.sin(yRot * ((float) Math.PI / 180F)) * MathHelper.cos(xRot * ((float) Math.PI / 180F));
		float velY = -MathHelper.sin((xRot + zRot) * ((float) Math.PI / 180F));
		float velZ = MathHelper.cos(yRot * ((float) Math.PI / 180F)) * MathHelper.cos(xRot * ((float) Math.PI / 180F));
		projectile.shoot(velX, velY, velZ, velocity, inaccuracy);
		Vector3d deltaMove = shooter.getDeltaMovement();
		projectile.setDeltaMovement(projectile.getDeltaMovement().add(deltaMove.x, 0.0D, deltaMove.z));
	}

}
