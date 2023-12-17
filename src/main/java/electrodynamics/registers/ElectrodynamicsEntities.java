package electrodynamics.registers;

import electrodynamics.api.References;
import electrodynamics.common.entity.projectile.types.EntityEnergyBlast;
import electrodynamics.common.entity.projectile.types.EntityMetalRod;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ElectrodynamicsEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, References.ID);

	public static final RegistryObject<EntityType<EntityMetalRod>> ENTITY_METALROD = ENTITIES.register("metalrod", () -> EntityType.Builder.<EntityMetalRod>of(EntityMetalRod::new, EntityClassification.MISC).sized(0.25f, 0.25f).fireImmune().build(new ResourceLocation(References.ID, "metalrod").toString()));
	public static final RegistryObject<EntityType<EntityEnergyBlast>> ENTITY_ENERGYBLAST = ENTITIES.register("energyblast", () -> EntityType.Builder.<EntityEnergyBlast>of(EntityEnergyBlast::new, EntityClassification.MISC).sized(0.25f, 0.25f).fireImmune().build(new ResourceLocation(References.ID, "energyblast").toString()));

}
