package physica.core.common.integration;

public interface IComputerIntegration {
    public String getComponentName();

    public String[] methods();

    public Object[] invoke(int method, Object[] args) throws Exception;
}
