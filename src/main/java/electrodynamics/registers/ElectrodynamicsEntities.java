package electrodynamics.registers;

import electrodynamics.api.References;
import electrodynamics.common.entity.projectile.types.EntityEnergyBlast;
import electrodynamics.common.entity.projectile.types.EntityMetalRod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ElectrodynamicsEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, References.ID);

	public static final DeferredHolder<EntityType<?>, EntityType<EntityMetalRod>> ENTITY_METALROD = ENTITIES.register("metalrod", () -> EntityType.Builder.<EntityMetalRod>of(EntityMetalRod::new, MobCategory.MISC).sized(0.25f, 0.25f).fireImmune().build(References.ID + ".metalrod"));
	public static final DeferredHolder<EntityType<?>, EntityType<EntityEnergyBlast>> ENTITY_ENERGYBLAST = ENTITIES.register("energyblast", () -> EntityType.Builder.<EntityEnergyBlast>of(EntityEnergyBlast::new, MobCategory.MISC).sized(0.25f, 0.25f).fireImmune().build(References.ID + ".energyblast"));

}
