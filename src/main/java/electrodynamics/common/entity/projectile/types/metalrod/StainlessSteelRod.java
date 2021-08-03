package electrodynamics.common.entity.projectile.types.metalrod;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.damage.DamageSources;
import electrodynamics.common.entity.EntityRegistry;
import electrodynamics.common.item.subtype.SubtypeRod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class StainlessSteelRod extends MetalRod{

	private static final float DAMAGE_DEALT = 20f;
	private static final DamageSource DAMAGE_SOURCE = DamageSources.ACCELERATED_BOLT;
	private static Item ITEM = DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeRod.stainlesssteel);
	
	public StainlessSteelRod(EntityType<? extends ProjectileItemEntity> type, World world) {
		super(type, world, ITEM, DAMAGE_SOURCE, DAMAGE_DEALT);
	}
	
	public StainlessSteelRod(LivingEntity entity, World world) {
		super(EntityRegistry.PROJECTILE_STAINLESSSTEELROD.get(), entity, world, ITEM, DAMAGE_SOURCE, DAMAGE_DEALT);
	}
	
	public StainlessSteelRod(double x, double y, double z, World worldIn) {
		super(EntityRegistry.PROJECTILE_STAINLESSSTEELROD.get(), x, y, z, worldIn, ITEM, DAMAGE_SOURCE, DAMAGE_DEALT);
	}
	
	
	
}
