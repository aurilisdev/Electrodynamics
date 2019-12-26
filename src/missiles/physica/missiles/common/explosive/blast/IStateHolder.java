package physica.missiles.common.explosive.blast;

public interface IStateHolder {
	public void addObject(String register, Object object);

	public Object getObject(String register);
}
