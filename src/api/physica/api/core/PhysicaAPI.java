package physica.api.core;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PhysicaAPI {

	public static final Logger logger = LogManager.getLogger("PhysicaAPI");
	public static final Random random = new Random();

	public static boolean isDebugMode = false;
}
