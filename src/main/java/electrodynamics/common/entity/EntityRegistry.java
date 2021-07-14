package electrodynamics.common.entity;

import electrodynamics.api.References;
import electrodynamics.common.entity.projectile.types.HSLASteelRod;
import electrodynamics.common.entity.projectile.types.StainlessSteelRod;
import electrodynamics.common.entity.projectile.types.SteelRod;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityRegistry {

	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,References.ID);
	
	public static final RegistryObject<EntityType<SteelRod>> PROJECTILE_STEELROD = ENTITIES.register(
		"projectile_steelrod", 
		() -> EntityType.Builder.<SteelRod>create(SteelRod::new, EntityClassification.MISC)
			.size(0.25f, 0.25f).build("projectile_steelrod"));
	
	public static final RegistryObject<EntityType<StainlessSteelRod>> PROJECTILE_STAINLESSSTEELROD = ENTITIES.register(
			"projectile_stainlesssteelrod", 
			() -> EntityType.Builder.<StainlessSteelRod>create(StainlessSteelRod::new, EntityClassification.MISC)
				.size(0.25f, 0.25f).build("projectile_stainlesssteelrod"));
	
	public static final RegistryObject<EntityType<HSLASteelRod>> PROJECTILE_HSLASTEELROD = ENTITIES.register(
			"projectile_hslasteelrod", 
			() -> EntityType.Builder.<HSLASteelRod>create(HSLASteelRod::new, EntityClassification.MISC)
				.size(0.25f, 0.25f).build("projectile_hslasteelrod"));
	
	
}
