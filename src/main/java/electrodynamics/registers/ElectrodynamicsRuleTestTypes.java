package electrodynamics.registers;

import electrodynamics.api.References;
import electrodynamics.common.world.ruletest.RuleTestOre;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import com.mojang.serialization.Codec;

public class ElectrodynamicsRuleTestTypes {
	
	public static final DeferredRegister<RuleTestType<?>> RULE_TEST_TYPES = DeferredRegister.create(Registry.RULE_TEST_REGISTRY, References.ID);

	public static final RegistryObject<RuleTestType<?>> TEST_CONFIG_ORESPAWN = RULE_TEST_TYPES.register("configorespawn", () -> new RuleTestType<RuleTestOre>() {

		@Override
		public Codec<RuleTestOre> codec() {
			return RuleTestOre.CODEC;
		}

	});

}
