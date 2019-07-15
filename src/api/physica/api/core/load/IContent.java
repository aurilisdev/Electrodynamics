package physica.api.core.load;

public interface IContent {

	default void register(LoadPhase phase)
	{
	}

	default void registerAdvanced(LoadPhase phase, Object... args)
	{
	}
}
