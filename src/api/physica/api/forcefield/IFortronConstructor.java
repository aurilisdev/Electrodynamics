package physica.api.forcefield;

public interface IFortronConstructor {

	public boolean isInForcefield(double x, double y, double z);

	public boolean isFinishedConstructing();
}
