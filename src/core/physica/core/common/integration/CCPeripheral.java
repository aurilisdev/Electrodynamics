package physica.core.common.integration;

import cpw.mods.fml.common.Optional;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@Optional.Interface(iface = "dan200.computercraft.api.peripheral.IPeripheral", modid = "ComputerCraft")
public class CCPeripheral implements IPeripheral {

    public IComputerIntegration tile;

    public CCPeripheral(IComputerIntegration tile) {
        this.tile = tile;
    }

    @Override
    @Optional.Method(modid="ComputerCraft")
    public String getType() {
        return this.tile.getComponentName();
    }

    @Override
    @Optional.Method(modid="ComputerCraft")
    public String[] getMethodNames() {
        return tile.methods();
    }

    @Override
    @Optional.Method(modid="ComputerCraft")
    public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws LuaException, InterruptedException {
        try {
            return tile.invoke(method, arguments);
        } catch (NoSuchMethodException e) {
            return new Object[]{"Unknown Command."};
        } catch (Exception e) {
            e.printStackTrace();
            return new Object[]{"Error"};
        }
    }

    @Override
    public void attach(IComputerAccess computer) {}

    @Override
    public void detach(IComputerAccess computer) {}

    @Override
    public boolean equals(IPeripheral that) {
        return this == that;
    }

    public static class CCPeripheralProvider implements IPeripheralProvider {

        @Override
        public IPeripheral getPeripheral(World world, int x, int y, int z, int side) {
            TileEntity tile = world.getTileEntity(x,y,z);

            if (tile instanceof IComputerIntegration) {
                return new CCPeripheral((IComputerIntegration) tile);
            }
            return null;
        }
    }

}
