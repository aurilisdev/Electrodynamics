package mcp.mobius.waila.api.impl;

import java.util.HashMap;

public class ConfigModule {

	String					modName;
	HashMap<String, String>	options;

	public ConfigModule(String _modName) {
		modName = _modName;
		options = new HashMap<>();
	}

	public ConfigModule(String _modName, HashMap<String, String> _options) {
		modName = _modName;
		options = _options;
	}

	public void addOption(String key, String name)
	{
		options.put(key, name);
	}

}
