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

public class SteelRod extends MetalRod {

    private static final float DAMAGE_DEALT = 16f;
    private static DamageSource DAMAGE_SOURCE = DamageSources.ACCELERATED_BOLT;
    private static Item ITEM = DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeRod.steel);

    public SteelRod(EntityType<? extends ProjectileItemEntity> type, World world) {
	super(type, world, ITEM, DAMAGE_SOURCE, DAMAGE_DEALT);
    }

    public SteelRod(LivingEntity entity, World world) {
	super(EntityRegistry.PROJECTILE_STEELROD.get(), entity, world, ITEM, DAMAGE_SOURCE, DAMAGE_DEALT);
    }

    public SteelRod(double x, double y, double z, World worldIn) {
	super(EntityRegistry.PROJECTILE_STEELROD.get(), x, y, z, worldIn, ITEM, DAMAGE_SOURCE, DAMAGE_DEALT);
    }

}
