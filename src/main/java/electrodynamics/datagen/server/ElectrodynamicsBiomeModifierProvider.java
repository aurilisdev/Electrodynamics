package electrodynamics.datagen.server;

import java.util.HashMap;
import java.util.Map;

import com.mojang.serialization.JsonOps;

import electrodynamics.api.References;
import electrodynamics.registers.ElectrodynamicsFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;

public class ElectrodynamicsBiomeModifierProvider extends JsonCodecProvider<BiomeModifier> {

	public ElectrodynamicsBiomeModifierProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper) {
		super(dataGenerator, existingFileHelper, References.ID, JsonOps.INSTANCE, PackType.SERVER_DATA, "forge/biome_modifier", AddFeaturesBiomeModifier.DIRECT_CODEC, getModifiers());
	}
	
	private static Map<ResourceLocation, BiomeModifier> getModifiers() {
		
		Map<ResourceLocation, BiomeModifier> modifiers = new HashMap<>();
		
		addModifier(modifiers, ElectrodynamicsFeatures.ORE_ALUMINUM_MODIFIER, ElectrodynamicsFeatures.ORE_ALUMINUM_PLACED.get());
		addModifier(modifiers, ElectrodynamicsFeatures.ORE_CHROMIUM_MODIFIER, ElectrodynamicsFeatures.ORE_CHROMIUM_PLACED.get());
		addModifier(modifiers, ElectrodynamicsFeatures.ORE_FLUORITE_MODIFIER, ElectrodynamicsFeatures.ORE_FLUORITE_PLACED.get());
		addModifier(modifiers, ElectrodynamicsFeatures.ORE_LEAD_MODIFIER, ElectrodynamicsFeatures.ORE_LEAD_PLACED.get());
		addModifier(modifiers, ElectrodynamicsFeatures.ORE_LITHIUM_MODIFIER, ElectrodynamicsFeatures.ORE_LITHIUM_PLACED.get());
		addModifier(modifiers, ElectrodynamicsFeatures.ORE_MOLYBDENUM_MODIFIER, ElectrodynamicsFeatures.ORE_MOLYBDENUM_PLACED.get());
		addModifier(modifiers, ElectrodynamicsFeatures.ORE_MONAZITE_MODIFIER, ElectrodynamicsFeatures.ORE_MONAZITE_PLACED.get());
		addModifier(modifiers, ElectrodynamicsFeatures.ORE_NITER_MODIFIER, ElectrodynamicsFeatures.ORE_NITER_PLACED.get());
		addModifier(modifiers, ElectrodynamicsFeatures.ORE_SALT_MODIFIER, ElectrodynamicsFeatures.ORE_SALT_PLACED.get());
		addModifier(modifiers, ElectrodynamicsFeatures.ORE_SILVER_MODIFIER, ElectrodynamicsFeatures.ORE_SILVER_PLACED.get());
		addModifier(modifiers, ElectrodynamicsFeatures.ORE_SULFUR_MODIFIER, ElectrodynamicsFeatures.ORE_SULFUR_PLACED.get());
		addModifier(modifiers, ElectrodynamicsFeatures.ORE_SYLVITE_MODIFIER, ElectrodynamicsFeatures.ORE_SYLVITE_PLACED.get());
		addModifier(modifiers, ElectrodynamicsFeatures.ORE_TIN_MODIFIER, ElectrodynamicsFeatures.ORE_TIN_PLACED.get());
		addModifier(modifiers, ElectrodynamicsFeatures.ORE_TITANIUM_MODIFIER, ElectrodynamicsFeatures.ORE_TITANIUM_PLACED.get());
		addModifier(modifiers, ElectrodynamicsFeatures.ORE_THORIUM_MODIFIER, ElectrodynamicsFeatures.ORE_THORIUM_PLACED.get());
		addModifier(modifiers, ElectrodynamicsFeatures.ORE_URANIUM_MODIFIER, ElectrodynamicsFeatures.ORE_URANIUM_PLACED.get());
		addModifier(modifiers, ElectrodynamicsFeatures.ORE_VANADIUM_MODIFIER, ElectrodynamicsFeatures.ORE_VANADIUM_PLACED.get());
		
		
		return modifiers;
	}
	
	private static void addModifier(Map<ResourceLocation, BiomeModifier> map, ResourceLocation id, PlacedFeature feature) {
		map.put(id, new AddFeaturesBiomeModifier(new HolderSet.Named<>(BuiltinRegistries.BIOME, BiomeTags.IS_OVERWORLD), HolderSet.direct(new Holder.Direct<>(feature)), Decoration.UNDERGROUND_ORES));
	}

}
