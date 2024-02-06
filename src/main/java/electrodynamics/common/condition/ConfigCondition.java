package electrodynamics.common.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import electrodynamics.common.settings.Constants;
import net.neoforged.neoforge.common.conditions.ICondition;

public class ConfigCondition implements ICondition {

    public static final ConfigCondition INSTANCE = new ConfigCondition();
	
	public static final Codec<ConfigCondition> CODEC = MapCodec.unit(INSTANCE).stable().codec();

	public ConfigCondition() {
	    
	}

	@Override
	public boolean test(IContext context) {
		return Constants.DISPENSE_GUIDEBOOK;
	}

    @Override
    public Codec<? extends ICondition> codec() {
        return CODEC;
    }
    
    @Override
    public String toString() {
        return "Guidebook toggle config";
    }
}
