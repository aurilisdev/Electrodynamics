package physica;

import java.util.Arrays;
import java.util.List;

public class CoreReferences {

	public static final String	NAME					= "Physica";
	public static final String	DOMAIN					= "physica";
	public static final String	PREFIX					= DOMAIN + ":";
	public static final String	PREFIX_TEXTURE_MACHINE	= DOMAIN + ":machine/";
	public static final String	VERSION					= "@VERSION@";

	public static final String	TEXTURE_DIRECTORY		= "textures/";
	public static final String	MODEL_DIRECTORY			= "models/";
	public static final String	GUI_TEXTURE_DIRECTORY	= TEXTURE_DIRECTORY + "gui/";
	public static final String	MODEL_TEXTURE_DIRECTORY	= TEXTURE_DIRECTORY + MODEL_DIRECTORY;

	public static class Metadata {

		public static final List<String>	AUTHORS		= Arrays.asList("aurilisdev");
		public static final String			CREDITS		= "Thanks to everyone who has contributed to this project.";
		public static final String			DESCRIPTION	= "Physica is a Minecraft Mod focused around science and technology that introduces many new machines/blocks and items into the game.";
		public static final String			URL			= "http://aurilisdev.com/software";
		public static final String			UPDATE_URL	= "http://aurilisdev.com/download";
	}
}
