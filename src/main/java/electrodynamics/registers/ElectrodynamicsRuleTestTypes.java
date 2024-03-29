package electrodynamics.registers;

import com.mojang.serialization.Codec;

import electrodynamics.api.References;
import electrodynamics.common.world.ruletests.RuleTestOre;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ElectrodynamicsRuleTestTypes {

	public static final DeferredRegister<RuleTestType<?>> RULE_TEST_TYPES = DeferredRegister.create(Registries.RULE_TEST, References.ID);

	public static final RegistryObject<RuleTestType<?>> TEST_CONFIG_ORESPAWN = RULE_TEST_TYPES.register("configorespawn", () -> new RuleTestType<RuleTestOre>() {

		@Override
		public Codec<RuleTestOre> codec() {
			return RuleTestOre.CODEC;
		}

	});

}
