package physica.core.common.integration;

import li.cil.oc.api.driver.NamedBlock;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.prefab.DriverSidedTileEntity;
import li.cil.oc.api.prefab.ManagedEnvironment;
import li.cil.oc.api.network.ManagedPeripheral;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Arrays;

public class OCDriver extends DriverSidedTileEntity {
    @Override
    public Class<?> getTileEntityClass() {
        return IComputerIntegration.class;
    }

    @Override
    public ManagedEnvironment createEnvironment(World world, int x, int y, int z, ForgeDirection side) {
        TileEntity tile = world.getTileEntity(x,y,z);

        if (tile instanceof IComputerIntegration) {
            return new OCManagedEnvironment((IComputerIntegration) tile);
        }

        return null;
    }

    @Override
    public boolean worksWith(World world, int x, int y, int z, ForgeDirection side) {
        return super.worksWith(world, x, y, z, side);
    }

    public class OCManagedEnvironment extends ManagedEnvironment implements NamedBlock, ManagedPeripheral {

        IComputerIntegration tile;

        public OCManagedEnvironment(IComputerIntegration tile) {
            this.tile = tile;
        }

        @Override
        public String preferredName() {
            return this.tile.getComponentName();
        }

        @Override
        public int priority() {
            return 4;
        }

        @Override
        public String[] methods() {
            return this.tile.methods();
        }

        @Override
        public Object[] invoke(String method, Context context, Arguments args) throws Exception {
            return this.tile.invoke(Arrays.asList(methods()).indexOf(method), args.toArray());
        }
    }
}
