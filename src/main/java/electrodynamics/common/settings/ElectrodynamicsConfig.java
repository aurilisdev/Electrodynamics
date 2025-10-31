package electrodynamics.common.settings;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ElectrodynamicsConfig {
    public static ElectrodynamicsConfig INSTANCE;

    public ModConfigSpec SPEC;

    public ModConfigSpec.DoubleValue TRANSFORMER_EFFICIENCY;
    public ModConfigSpec.DoubleValue CIRCUITBREAKER_EFFICIENCY;
    public ModConfigSpec.DoubleValue RELAY_EFFICIENCY;
    public ModConfigSpec.DoubleValue CURRENTREGULATOR_EFFICIENCY;
    public ModConfigSpec.DoubleValue COALGENERATOR_AMPERAGE;
    public ModConfigSpec.DoubleValue THERMOELECTRICGENERATOR_AMPERAGE;
    public ModConfigSpec.DoubleValue HYDROELECTRICGENERATOR_AMPERAGE;
    public ModConfigSpec.DoubleValue WINDMILL_MAX_AMPERAGE;
    public ModConfigSpec.DoubleValue SOLARPANEL_AMPERAGE;
    public ModConfigSpec.DoubleValue ELECTRICPUMP_USAGE_PER_TICK;
    public ModConfigSpec.DoubleValue ADVANCEDSOLARPANEL_AMPERAGE;
    public ModConfigSpec.DoubleValue ELECTRICFURNACE_USAGE_PER_TICK;
    public ModConfigSpec.IntValue ELECTRICFURNACE_REQUIRED_TICKS;
    public ModConfigSpec.DoubleValue ELECTRICARCFURNACE_USAGE_PER_TICK;
    public ModConfigSpec.IntValue ELECTRICARCFURNACE_REQUIRED_TICKS;
    public ModConfigSpec.DoubleValue WIREMILL_USAGE_PER_TICK;
    public ModConfigSpec.DoubleValue ROTARY_UNIFIER_USAGE;
    public ModConfigSpec.IntValue ROTARY_UNIFIER_CONVERSION_RATE;
    public ModConfigSpec.IntValue WIREMILL_REQUIRED_TICKS;
    public ModConfigSpec.DoubleValue COMBUSTIONCHAMBER_JOULES_PER_TICK;
    public ModConfigSpec.DoubleValue CHARGER_USAGE_PER_TICK;
    public ModConfigSpec.DoubleValue GAS_COLLECTOR_USAGE_PER_TICK;
    public ModConfigSpec.DoubleValue COMPRESSOR_USAGE_PER_TICK;
    public ModConfigSpec.IntValue COMPRESSOR_CONVERSION_RATE;
    public ModConfigSpec.DoubleValue DECOMPRESSOR_USAGE_PER_TICK;
    public ModConfigSpec.IntValue DECOMPRESSOR_CONVERSION_RATE;
    public ModConfigSpec.DoubleValue ADVANCED_COMPRESSOR_USAGE_PER_TICK;
    public ModConfigSpec.IntValue ADVANCED_COMPRESSOR_CONVERSION_RATE;
    public ModConfigSpec.DoubleValue ADVANCED_DECOMPRESSOR_USAGE_PER_TICK;
    public ModConfigSpec.IntValue ADVANCED_DECOMPRESSOR_CONVERSION_RATE;
    public ModConfigSpec.DoubleValue THERMOELECTRIC_MANIPULATOR_USAGE_PER_TICK;
    public ModConfigSpec.IntValue THERMOELECTRIC_MANIPULATOR_HEAT_TRANSFER;
    public ModConfigSpec.IntValue THERMOELECTRIC_MANIPULATOR_CONVERSION_RATE;
    public ModConfigSpec.DoubleValue ADVANCED_THERMOELECTRIC_MANIPULATOR_USAGE_PER_TICK;
    public ModConfigSpec.IntValue ADVANCED_THERMOELECTRIC_MANIPULATOR_HEAT_TRANSFER;
    public ModConfigSpec.IntValue ADVANCED_THERMOELECTRIC_MANIPULATOR_CONVERSION_RATE;
    public ModConfigSpec.IntValue GAS_TRANSFORMER_INPUT_PRESSURE_CAP;
    public ModConfigSpec.IntValue GAS_TRANSFORMER_INPUT_TEMP_CAP;
    public ModConfigSpec.IntValue GAS_TRANSFORMER_BASE_INPUT_CAPACITY;
    public ModConfigSpec.IntValue GAS_TRANSFORMER_OUTPUT_PRESSURE_CAP;
    public ModConfigSpec.IntValue GAS_TRANSFORMER_OUTPUT_TEMP_CAP;
    public ModConfigSpec.IntValue GAS_TRANSFORMER_BASE_OUTPUT_CAPACITY;
    public ModConfigSpec.IntValue GAS_TRANSFORMER_ADDON_TANK_CAPACITY;
    public ModConfigSpec.IntValue GAS_TRANSFORMER_ADDON_TANK_LIMIT;
    public ModConfigSpec.DoubleValue MOTORCOMPLEX_USAGE_PER_TICK;
    public ModConfigSpec.DoubleValue PIPE_PUMP_USAGE_PER_TICK;
    public ModConfigSpec.DoubleValue ELECTROLOSIS_CHAMBER_TARGET_JOULES;
    public ModConfigSpec.DoubleValue QUARRY_USAGE_PER_TICK;
    public ModConfigSpec.IntValue QUARRY_WATERUSAGE_PER_BLOCK;
    public ModConfigSpec.IntValue MARKER_RADIUS;
    public ModConfigSpec.IntValue MIN_TICKS_PER_QUARRYBLOCK;
    public ModConfigSpec.IntValue MAX_TICKS_PER_QUARRYBLOCK;
    public ModConfigSpec.IntValue CLEARING_AIR_SKIP;
    public ModConfigSpec.BooleanValue MAINTAIN_MINING_AREA;
    public ModConfigSpec.BooleanValue BYPASS_CLAIMS;
    public ModConfigSpec.BooleanValue SHOULD_TRANSFORMER_HUM;
    public ModConfigSpec.DoubleValue TRANSFORMER_SOUND_LOAD_TARGET;
    public ModConfigSpec.BooleanValue CONDUCTORS_BURN_SURROUNDINGS;
    public ModConfigSpec.DoubleValue BLOCK_VAPORIZATION_HARDNESS;
    public ModConfigSpec.BooleanValue RENDER_COMBAT_ARMOR_STATUS;
    public ModConfigSpec.BooleanValue DISABLE_ALL_ORES;
    public ModConfigSpec.BooleanValue DISABLE_STONE_ORES;
    public ModConfigSpec.BooleanValue SPAWN_ALUMINUM_ORE;
    public ModConfigSpec.BooleanValue SPAWN_CHROMIUM_ORE;
    public ModConfigSpec.BooleanValue SPAWN_FLUORITE_ORE;
    public ModConfigSpec.BooleanValue SPAWN_LEAD_ORE;
    public ModConfigSpec.BooleanValue SPAWN_LITHIUM_ORE;
    public ModConfigSpec.BooleanValue SPAWN_MOLYBDENUM_ORE;
    public ModConfigSpec.BooleanValue SPAWN_MONAZITE_ORE;
    public ModConfigSpec.BooleanValue SPAWN_NITER_ORE;
    public ModConfigSpec.BooleanValue SPAWN_SALT_ORE;
    public ModConfigSpec.BooleanValue SPAWN_SILVER_ORE;
    public ModConfigSpec.BooleanValue SPAWN_SULFUR_ORE;
    public ModConfigSpec.BooleanValue SPAWN_SYLVITE_ORE;
    public ModConfigSpec.BooleanValue SPAWN_TIN_ORE;
    public ModConfigSpec.BooleanValue SPAWN_TITANIUM_ORE;
    public ModConfigSpec.BooleanValue SPAWN_THORIUM_ORE;
    public ModConfigSpec.BooleanValue SPAWN_URANIUM_ORE;
    public ModConfigSpec.BooleanValue SPAWN_VANADIUM_ORE;
    public ModConfigSpec.BooleanValue DISABLE_DEEPSLATE_ORES;
    public ModConfigSpec.BooleanValue SPAWN_DEEP_ALUMINUM_ORE;
    public ModConfigSpec.BooleanValue SPAWN_DEEP_CHROMIUM_ORE;
    public ModConfigSpec.BooleanValue SPAWN_DEEP_FLUORITE_ORE;
    public ModConfigSpec.BooleanValue SPAWN_DEEP_LEAD_ORE;
    public ModConfigSpec.BooleanValue SPAWN_DEEP_LITHIUM_ORE;
    public ModConfigSpec.BooleanValue SPAWN_DEEP_MOLYBDENUM_ORE;
    public ModConfigSpec.BooleanValue SPAWN_DEEP_MONAZITE_ORE;
    public ModConfigSpec.BooleanValue SPAWN_DEEP_NITER_ORE;
    public ModConfigSpec.BooleanValue SPAWN_DEEP_SALT_ORE;
    public ModConfigSpec.BooleanValue SPAWN_DEEP_SILVER_ORE;
    public ModConfigSpec.BooleanValue SPAWN_DEEP_SULFUR_ORE;
    public ModConfigSpec.BooleanValue SPAWN_DEEP_SYLVITE_ORE;
    public ModConfigSpec.BooleanValue SPAWN_DEEP_TIN_ORE;
    public ModConfigSpec.BooleanValue SPAWN_DEEP_TITANIUM_ORE;
    public ModConfigSpec.BooleanValue SPAWN_DEEP_THORIUM_ORE;
    public ModConfigSpec.BooleanValue SPAWN_DEEP_URANIUM_ORE;
    public ModConfigSpec.BooleanValue SPAWN_DEEP_VANADIUM_ORE;

    public ModConfigSpec.DoubleValue ORE_GENERATION_MULTIPLIER;

    public ElectrodynamicsConfig() {
	var builder = new ModConfigSpec.Builder();

	builder.push("common");
	TRANSFORMER_EFFICIENCY = builder.defineInRange("transformer_efficiency", 0.9925f, 0, 1);
	CIRCUITBREAKER_EFFICIENCY = builder.defineInRange("circuitbreaker_efficiency", 0.995f, 0, 1);
	RELAY_EFFICIENCY = builder.defineInRange("relay_efficiency", 1.0F, 0, 1); // the relay is a dumb switch; no need
										  // to penalize its use
	CURRENTREGULATOR_EFFICIENCY = builder.defineInRange("currentregulator_efficiency", 0.995f, 0, 1);
	COALGENERATOR_AMPERAGE = builder.defineInRange("coalgenerator_amperage", 34.0, 0, Double.MAX_VALUE);
	THERMOELECTRICGENERATOR_AMPERAGE = builder.defineInRange("thermoelectricgenerator_amperage", 4.5, 0,
		Double.MAX_VALUE);
	HYDROELECTRICGENERATOR_AMPERAGE = builder.defineInRange("hydroelectricgenerator_amperage", 6, 0,
		Double.MAX_VALUE);
	WINDMILL_MAX_AMPERAGE = builder.defineInRange("windmill_max_amperage", 10, 0, Double.MAX_VALUE);
	SOLARPANEL_AMPERAGE = builder.defineInRange("solarpanel_amperage", 7, 0, Double.MAX_VALUE);
	ELECTRICPUMP_USAGE_PER_TICK = builder.defineInRange("electricpump_usage", 50.0, 0, Double.MAX_VALUE);
	ADVANCEDSOLARPANEL_AMPERAGE = builder.defineInRange("adv_solarpanel_amperage", 25.0, 0, Double.MAX_VALUE);
	ELECTRICFURNACE_USAGE_PER_TICK = builder.defineInRange("electricfurnace_usage", 175.0, 0, Double.MAX_VALUE);
	ELECTRICFURNACE_REQUIRED_TICKS = builder.defineInRange("electricfurnace_ticks", 100, 1, Integer.MAX_VALUE);
	ELECTRICARCFURNACE_USAGE_PER_TICK = builder.defineInRange("electricarcfurnace_usage", 175.0, 0,
		Double.MAX_VALUE);
	ELECTRICARCFURNACE_REQUIRED_TICKS = builder.defineInRange("electricarcfurnace_ticks", 50, 1, Integer.MAX_VALUE);
	WIREMILL_USAGE_PER_TICK = builder.defineInRange("wiremill_usage", 125.0, 0, Double.MAX_VALUE);
	ROTARY_UNIFIER_USAGE = builder.defineInRange("rotaryunifier_usage", 1000, 0, Double.MAX_VALUE);
	ROTARY_UNIFIER_CONVERSION_RATE = builder.defineInRange("rotaryunifier_conversion", 1, 1, Integer.MAX_VALUE);
	WIREMILL_REQUIRED_TICKS = builder.defineInRange("wiremill_ticks", 200, 1, Integer.MAX_VALUE);
	COMBUSTIONCHAMBER_JOULES_PER_TICK = builder.defineInRange("combustionchamber_usage", 350.0, 0,
		Double.MAX_VALUE);
	CHARGER_USAGE_PER_TICK = builder.defineInRange("charger_usage", 1000.0, 0, Double.MAX_VALUE);
	GAS_COLLECTOR_USAGE_PER_TICK = builder.defineInRange("gascollector_usage", 100, 0, Double.MAX_VALUE);
	COMPRESSOR_USAGE_PER_TICK = builder.defineInRange("compressor_usage", 100.0, 0, Double.MAX_VALUE);
	COMPRESSOR_CONVERSION_RATE = builder.defineInRange("compressor_conversion", 20, 1, Integer.MAX_VALUE);
	DECOMPRESSOR_USAGE_PER_TICK = builder.defineInRange("decompressor_usage", 100.0, 0, Double.MAX_VALUE);
	DECOMPRESSOR_CONVERSION_RATE = builder.defineInRange("decompressor_conversion", 20, 1, Integer.MAX_VALUE);
	ADVANCED_COMPRESSOR_USAGE_PER_TICK = builder.defineInRange("adv_compressor_usage", 100.0, 0, Double.MAX_VALUE);
	ADVANCED_COMPRESSOR_CONVERSION_RATE = builder.defineInRange("adv_compressor_conversion", 80, 1,
		Integer.MAX_VALUE);
	ADVANCED_DECOMPRESSOR_USAGE_PER_TICK = builder.defineInRange("adv_decompressor_usage", 100.0, 0,
		Double.MAX_VALUE);
	ADVANCED_DECOMPRESSOR_CONVERSION_RATE = builder.defineInRange("adv_decompressor_conversion", 80, 1,
		Integer.MAX_VALUE);
	THERMOELECTRIC_MANIPULATOR_USAGE_PER_TICK = builder.defineInRange("thermomanipulator_usage", 100.0, 0,
		Double.MAX_VALUE);
	THERMOELECTRIC_MANIPULATOR_HEAT_TRANSFER = builder.defineInRange("thermomanipulator_heat_transfer", 10, 1,
		Integer.MAX_VALUE);
	THERMOELECTRIC_MANIPULATOR_CONVERSION_RATE = builder.defineInRange("thermomanipulator_conversion", 20, 1,
		Integer.MAX_VALUE);
	ADVANCED_THERMOELECTRIC_MANIPULATOR_USAGE_PER_TICK = builder.defineInRange("adv_thermomanipulator_usage", 100.0,
		0, Double.MAX_VALUE);
	ADVANCED_THERMOELECTRIC_MANIPULATOR_HEAT_TRANSFER = builder.defineInRange("adv_thermomanipulator_heat_transfer",
		80, 1, Integer.MAX_VALUE);
	ADVANCED_THERMOELECTRIC_MANIPULATOR_CONVERSION_RATE = builder.defineInRange("adv_thermomanipulator_conversion",
		80, 1, Integer.MAX_VALUE);
	GAS_TRANSFORMER_INPUT_PRESSURE_CAP = builder.defineInRange("gastransformer_input_pressure_cap", 1048576, 1,
		Integer.MAX_VALUE);// 2^20
	GAS_TRANSFORMER_INPUT_TEMP_CAP = builder.defineInRange("gastransformer_input_temp_cap", 1000000, 1,
		Integer.MAX_VALUE);
	GAS_TRANSFORMER_BASE_INPUT_CAPACITY = builder.defineInRange("gastransformer_base_input_capacity", 5000, 1,
		Integer.MAX_VALUE);
	GAS_TRANSFORMER_OUTPUT_PRESSURE_CAP = builder.defineInRange("gastransformer_output_pressure_cap", 1048576, 1,
		Integer.MAX_VALUE);// 2^20
	GAS_TRANSFORMER_OUTPUT_TEMP_CAP = builder.defineInRange("gastransformer_output_temp_cap", 1000000, 1,
		Integer.MAX_VALUE);
	GAS_TRANSFORMER_BASE_OUTPUT_CAPACITY = builder.defineInRange("gastransformer_base_output_capacity", 5000, 1,
		Integer.MAX_VALUE);
	GAS_TRANSFORMER_ADDON_TANK_CAPACITY = builder.defineInRange("gastransformer_addon_tank_capacity", 5000, 1,
		Integer.MAX_VALUE);
	GAS_TRANSFORMER_ADDON_TANK_LIMIT = builder.defineInRange("gastransformer_addon_tank_limit", 5, 1,
		Integer.MAX_VALUE);
	MOTORCOMPLEX_USAGE_PER_TICK = builder.defineInRange("motorcomplex_usage", 100.0, 0, Double.MAX_VALUE);
	PIPE_PUMP_USAGE_PER_TICK = builder.defineInRange("pipepump_usage", 10.0, 0, Double.MAX_VALUE);
	ELECTROLOSIS_CHAMBER_TARGET_JOULES = builder.defineInRange("electrolosischamber_target", 100000.0, 0,
		Double.MAX_VALUE);
	SHOULD_TRANSFORMER_HUM = builder.comment(
		"When set to true, this will make transformers tick, but give them the ability to hum as they do in real life. If you need to gain performance, you can disable this to stop transformers from ticking and thus producing sound.")
		.define("should_transformer_hum", true);
	TRANSFORMER_SOUND_LOAD_TARGET = builder.comment(
		"The Watts a transformer needs to see to be considered under \"full load\" and thus hum as loud as it can. Set to 0 to have it hum under any load greater than 0 Watts")
		.defineInRange("transformer_sound_load_target", 5000.0, 1.0, Double.MAX_VALUE);
	CONDUCTORS_BURN_SURROUNDINGS = builder.comment(
		"Whether or not wires should set things on fire around them if their voltage exceeds their insulation value.")
		.define("conductors_burn_surroundings", true);
	BLOCK_VAPORIZATION_HARDNESS = builder.comment(
		"The hardness value that a block must have to not be instantly vaporized by a wire over 30,720V, which is the maximum voltage achievable by default electrodynamics. 6 is the explosion resistance of an Iron Block")
		.defineInRange("block_vaporisation_hardness", 6.0F, 0, Double.MAX_VALUE);
	RENDER_COMBAT_ARMOR_STATUS = builder.comment("Set to false to disable the HUD rendering for combat armor.")
		.define("render_combat_armor_status", true);
	builder.pop();
	builder.push("quarry");
	QUARRY_USAGE_PER_TICK = builder.defineInRange("quarry_usage", 100.0, 0, Double.MAX_VALUE);
	QUARRY_WATERUSAGE_PER_BLOCK = builder.defineInRange("quarry_waterusage", 10, 1, Integer.MAX_VALUE);
	MARKER_RADIUS = builder.comment("max radius = 128, min radius = 2").defineInRange("marker_radius", 64, 1,
		Integer.MAX_VALUE);
	MIN_TICKS_PER_QUARRYBLOCK = builder.comment("max possible is 1 tick / block")
		.defineInRange("min_ticks_per_quarryblock", 1, 1, Integer.MAX_VALUE);
	MAX_TICKS_PER_QUARRYBLOCK = builder.comment("min possible speed is 100 ticks / block")
		.defineInRange("max_ticks_per_quarryblock", 100, 1, Integer.MAX_VALUE);
	CLEARING_AIR_SKIP = builder
		.comment("how many air blocks the quarry can skip over in one clearing tick; max is 128, min is zero")
		.defineInRange("clearing_air_skip", 64, 1, Integer.MAX_VALUE);
	MAINTAIN_MINING_AREA = builder.comment(
		"controls whether or not the quarry will mine blocks that have been placed into the mining area")
		.define("maintain_mining_area", true);
	BYPASS_CLAIMS = builder.comment("Controls whether the quarry can bypass claims or not").define("bypass_claims",
		false);
	builder.pop();
	builder.push("ores");
	DISABLE_ALL_ORES = builder.define("disable_all_ores", false);
	DISABLE_STONE_ORES = builder.define("disable_stone_ores", false);
	SPAWN_ALUMINUM_ORE = builder.define("spawn_aluminum_ore", true);
	SPAWN_CHROMIUM_ORE = builder.define("spawn_chromium_ore", true);
	SPAWN_FLUORITE_ORE = builder.define("spawn_fluorite_ore", true);
	SPAWN_LEAD_ORE = builder.define("spawn_lead_ore", true);
	SPAWN_LITHIUM_ORE = builder.define("spawn_lithium_ore", true);
	SPAWN_MOLYBDENUM_ORE = builder.define("spawn_molybdenum_ore", true);
	SPAWN_MONAZITE_ORE = builder.define("spawn_monazite_ore", true);
	SPAWN_NITER_ORE = builder.define("spawn_niter_ore", true);
	SPAWN_SALT_ORE = builder.define("spawn_salt_ore", true);
	SPAWN_SILVER_ORE = builder.define("spawn_silver_ore", true);
	SPAWN_SULFUR_ORE = builder.define("spawn_sulfur_ore", true);
	SPAWN_SYLVITE_ORE = builder.define("spawn_sylvite_ore", true);
	SPAWN_TIN_ORE = builder.define("spawn_tin_ore", true);
	SPAWN_TITANIUM_ORE = builder.define("spawn_titanium_ore", true);
	SPAWN_THORIUM_ORE = builder.define("spawn_thorium_ore", true);
	SPAWN_URANIUM_ORE = builder.define("spawn_uranium_ore", true);
	SPAWN_VANADIUM_ORE = builder.define("spawn_vanadium_ore", true);
	DISABLE_DEEPSLATE_ORES = builder.define("disable_deepslate_ores", false);
	SPAWN_DEEP_ALUMINUM_ORE = builder.define("spawn_deep_aluminum_ore", true);
	SPAWN_DEEP_CHROMIUM_ORE = builder.define("spawn_deep_chromium_ore", true);
	SPAWN_DEEP_FLUORITE_ORE = builder.define("spawn_deep_fluorite_ore", true);
	SPAWN_DEEP_LEAD_ORE = builder.define("spawn_deep_lead_ore", true);
	SPAWN_DEEP_LITHIUM_ORE = builder.define("spawn_deep_lithium_ore", true);
	SPAWN_DEEP_MOLYBDENUM_ORE = builder.define("spawn_deep_molybdenum_ore", true);
	SPAWN_DEEP_MONAZITE_ORE = builder.define("spawn_deep_monazite_ore", true);
	SPAWN_DEEP_NITER_ORE = builder.define("spawn_deep_niter_ore", true);
	SPAWN_DEEP_SALT_ORE = builder.define("spawn_deep_salt_ore", true);
	SPAWN_DEEP_SILVER_ORE = builder.define("spawn_deep_silver_ore", true);
	SPAWN_DEEP_SULFUR_ORE = builder.define("spawn_deep_sulfur_ore", true);
	SPAWN_DEEP_SYLVITE_ORE = builder.define("spawn_deep_sylvite_ore", true);
	SPAWN_DEEP_TIN_ORE = builder.define("spawn_deep_tin_ore", true);
	SPAWN_DEEP_TITANIUM_ORE = builder.define("spawn_deep_titanium_ore", true);
	SPAWN_DEEP_THORIUM_ORE = builder.define("spawn_deep_thorium_ore", true);
	SPAWN_DEEP_URANIUM_ORE = builder.define("spawn_deep_uranium_ore", true);
	SPAWN_DEEP_VANADIUM_ORE = builder.define("spawn_deep_vanadium_ore", true);
	ORE_GENERATION_MULTIPLIER = builder.defineInRange("ore_generation_multiplier", 1, 0, Double.MAX_VALUE);
	builder.pop();
	SPEC = builder.build();
    }
}
