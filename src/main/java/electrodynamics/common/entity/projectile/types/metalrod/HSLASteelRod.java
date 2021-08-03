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

public class HSLASteelRod extends MetalRod {

    private static final float DAMAGE_DEALT = 4f;
    private static DamageSource DAMAGE_SOURCE = DamageSources.ACCELERATED_BOLT_IGNOREARMOR;
    private static Item ITEM = DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeRod.hslasteel);

    public HSLASteelRod(EntityType<? extends ProjectileItemEntity> type, World world) {
	super(type, world, ITEM, DAMAGE_SOURCE, DAMAGE_DEALT);
    }

    public HSLASteelRod(LivingEntity entity, World world) {
	super(EntityRegistry.PROJECTILE_HSLASTEELROD.get(), entity, world, ITEM, DAMAGE_SOURCE, DAMAGE_DEALT);
    }

    public HSLASteelRod(double x, double y, double z, World worldIn) {
	super(EntityRegistry.PROJECTILE_HSLASTEELROD.get(), x, y, z, worldIn, ITEM, DAMAGE_SOURCE, DAMAGE_DEALT);
    }

}
