package electrodynamics.common.tile.machines.quarry;

import javax.annotation.Nonnull;

import electrodynamics.common.item.ItemDrillHead;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import voltaic.client.model.block.modelproperties.ModelPropertyConnections;
import voltaic.common.block.connect.EnumConnectType;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.tile.types.IConnectTile;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.prefab.utilities.Scheduler;

public class TileLogisticalManager extends GenericTile implements IConnectTile {

    private TileQuarry[] quarries = new TileQuarry[6];
    private TileEntity[] inventories = new TileEntity[6];

    // DUNSWE

    public static final int DOWN_MASK = 0b00000000000000000000000000001111;
    public static final int UP_MASK = 0b00000000000000000000000011110000;
    public static final int NORTH_MASK = 0b00000000000000000000111100000000;
    public static final int SOUTH_MASK = 0b00000000000000001111000000000000;
    public static final int WEST_MASK = 0b00000000000011110000000000000000;
    public static final int EAST_MASK = 0b00000000111100000000000000000000;

    public final SingleProperty<Integer> connections = property(new SingleProperty<>(PropertyTypes.INTEGER, "connections", 0).onChange((property, old) -> {
        requestModelDataUpdate();
        if(level != null && level.isClientSide()){
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 8); //
        }
    }).onTileLoaded(property -> requestModelDataUpdate())).setShouldUpdateOnChange();

    public TileLogisticalManager() {
        super(ElectrodynamicsTiles.TILE_LOGISTICALMANAGER.get());
        addComponent(new ComponentTickable(this).tickServer(this::tickServer));
    }

    private void tickServer(ComponentTickable tick) {
        for (int i = 0; i < 6; i++) {
            TileEntity inventory = inventories[i];

            if (inventory == null) {
                continue;
            }

            IItemHandler invHandler = inventory.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.values()[i].getOpposite()).orElse(CapabilityUtils.EMPTY_ITEM_HANDLER);

            if (invHandler == CapabilityUtils.EMPTY_ITEM_HANDLER) {
                continue;
            }

            for (TileQuarry quarry : quarries) {
                if (quarry != null) {
                    manipulateItems(quarry.getComponent(IComponentType.Inventory), invHandler);
                }
            }

        }

    }

    @Override
    public void onNeightborChanged(BlockPos neighbor, boolean blockStateTrigger) {
        if (level.isClientSide) {
            return;
        }
        refreshConnections();
    }

    @Override
    public void onPlace(BlockState oldState, boolean isMoving) {
        super.onPlace(oldState, isMoving);
        if (level.isClientSide) {
            return;
        }
        refreshConnections();
    }

    public void refreshConnections() {
        quarries = new TileQuarry[6];
        inventories = new TileEntity[6];
        for (Direction dir : Direction.values()) {
            TileEntity entity = level.getBlockEntity(getBlockPos().relative(dir));

            if (entity == null) {
                continue;
            }

            if (entity instanceof TileQuarry) {
                quarries[dir.ordinal()] = (TileQuarry) entity;
            } else if (entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.getOpposite()).orElse(CapabilityUtils.EMPTY_ITEM_HANDLER) != CapabilityUtils.EMPTY_ITEM_HANDLER) {
                inventories[dir.ordinal()] = entity;
            }

        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        Scheduler.schedule(1, this::refreshConnections);
    }

    private void manipulateItems(ComponentInventory quarryInventory, IItemHandler handler) {

        if (quarryInventory.getItem(TileQuarry.DRILL_HEAD_INDEX).isEmpty()) {
            restockDrillHead(quarryInventory, handler);
        }

        addItemsToInventory(quarryInventory, handler);

    }

    private void restockDrillHead(ComponentInventory quarryInventory, IItemHandler handler) {

        ItemStack stack;

        for (int i = 0; i < handler.getSlots(); i++) {

            stack = handler.getStackInSlot(i);

            if (!stack.isEmpty() && stack.getItem() instanceof ItemDrillHead) {
                quarryInventory.setItem(TileQuarry.DRILL_HEAD_INDEX, stack.copy());
                handler.extractItem(i, stack.getMaxStackSize(), false);
                break;
            }

        }

    }

    private void addItemsToInventory(ComponentInventory quarryInventory, IItemHandler handler) {
        for (int i = 0; i < quarryInventory.outputs(); i++) {
            int index = i + quarryInventory.getOutputStartIndex();
            ItemStack mined = quarryInventory.getItem(index);
            if (!mined.isEmpty()) {
                for (int j = 0; j < handler.getSlots(); j++) {
                    mined = handler.insertItem(j, mined, false);
                    quarryInventory.setItem(index, mined);
                    quarryInventory.setChanged(index);
                    if (mined.isEmpty()) {
                        break;
                    }
                }
            }
        }

    }

    public static boolean isQuarry(BlockPos pos, IWorldReader world) {
        TileEntity entity = world.getBlockEntity(pos);
        return entity instanceof TileQuarry;
    }

    public static boolean isValidInventory(BlockPos pos, IWorldReader world, Direction dir) {
        TileEntity entity = world.getBlockEntity(pos);
        if (entity == null) {
            return false;
        }

        if (entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir).orElse(CapabilityUtils.EMPTY_ITEM_HANDLER) != CapabilityUtils.EMPTY_ITEM_HANDLER) {
            return true;
        }

        return entity instanceof IInventory;
    }

    public EnumConnectType readConnection(Direction dir) {

        int connectionData = connections.getValue();

        if (connectionData == 0) {
            return EnumConnectType.NONE;
        }

        int extracted = 0;
        switch (dir) {
            case DOWN:
                extracted = connectionData & DOWN_MASK;
                break;
            case UP:
                extracted = connectionData & UP_MASK;
                break;
            case NORTH:
                extracted = connectionData & NORTH_MASK;
                break;
            case SOUTH:
                extracted = connectionData & SOUTH_MASK;
                break;
            case WEST:
                extracted = connectionData & WEST_MASK;
                break;
            case EAST:
                extracted = connectionData & EAST_MASK;
                break;
            default:
                break;
        }

        // return EnumConnectType.NONE;

        return EnumConnectType.values()[(extracted >> (dir.ordinal() * 4))];

    }

    public void writeConnection(Direction dir, EnumConnectType connection) {

        int connectionData = this.connections.getValue();
        int masked;

        switch (dir) {
            case DOWN:
                masked = connectionData & ~DOWN_MASK;
                break;
            case UP:
                masked = connectionData & ~UP_MASK;
                break;
            case NORTH:
                masked = connectionData & ~NORTH_MASK;
                break;
            case SOUTH:
                masked = connectionData & ~SOUTH_MASK;
                break;
            case WEST:
                masked = connectionData & ~WEST_MASK;
                break;
            case EAST:
                masked = connectionData & ~EAST_MASK;
                break;
            default:
                masked = 0;
                break;
        }

        connections.setValue(masked | (connection.ordinal() << (dir.ordinal() * 4)));
    }

    @Override
    public EnumConnectType[] readConnections() {
        EnumConnectType[] connections = new EnumConnectType[6];
        for (Direction dir : Direction.values()) {
            connections[dir.ordinal()] = readConnection(dir);
        }
        return connections;
    }

    @Override
    public @Nonnull IModelData getModelData() {
        return new ModelDataMap.Builder().withInitial(ModelPropertyConnections.INSTANCE, () -> readConnections()).build();
    }

}
