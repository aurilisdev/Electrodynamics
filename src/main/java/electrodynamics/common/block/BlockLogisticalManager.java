package electrodynamics.common.block;

import electrodynamics.common.tile.machines.quarry.TileLogisticalManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import voltaic.common.block.connect.AbstractConnectBlock;
import voltaic.common.block.connect.EnumConnectType;
import voltaic.prefab.block.GenericEntityBlockWaterloggable;
import voltaic.prefab.tile.types.IConnectTile;
import voltaic.prefab.utilities.BlockEntityUtils;

/**
 * INVENTORY corresponds to a chest WIRE corresponds to a Quarry
 * 
 * @author skip999
 *
 */
public class BlockLogisticalManager extends GenericEntityBlockWaterloggable {

	protected final VoxelShape[] boundingBoxes = new VoxelShape[7];

    int maxValue = 0b1000000;
    protected VoxelShape[] shapestates = new VoxelShape[maxValue];


    public BlockLogisticalManager() {
        super(Properties.copy(Blocks.IRON_BLOCK).strength(3.5F).sound(SoundType.METAL).noOcclusion().requiresCorrectToolForDrops().harvestLevel(1).harvestTool(ToolType.PICKAXE));
        generateBoundingBoxes(3);
    }

    public void generateBoundingBoxes(double radius) {
        double w = radius;
        double sm = 8 - w;
        double lg = 8 + w;
        // down
        boundingBoxes[0] = Block.box(sm, 0, sm, lg, lg, lg);
        // up
        boundingBoxes[1] = Block.box(sm, sm, sm, lg, 16, lg);
        // north
        boundingBoxes[2] = Block.box(sm, sm, 0, lg, lg, lg);
        // south
        boundingBoxes[3] = Block.box(sm, sm, sm, lg, lg, 16);
        // west
        boundingBoxes[4] = Block.box(0, sm, sm, lg, lg, lg);
        // east
        boundingBoxes[5] = Block.box(sm, sm, sm, 16, lg, lg);
        // center
        boundingBoxes[6] = Block.box(sm, sm, sm, lg, lg, lg);
    }


    @Override
    public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {


        TileEntity entity = level.getBlockEntity(pos);

        if(!(entity instanceof IConnectTile)) {
            return VoxelShapes.empty();
        }

        EnumConnectType[] connections = ((IConnectTile) entity).readConnections();
        int hash = AbstractConnectBlock.hashPresentSides(connections);

        if (shapestates[hash] != null) {
            return shapestates[hash];
        }

        VoxelShape shape = boundingBoxes[6];

        for (int i = 0; i < 6; i++) {
            if (connections[i] == EnumConnectType.NONE) {
                continue;
            }

            shape = VoxelShapes.join(shape, boundingBoxes[i], IBooleanFunction.OR);
        }
        shapestates[hash] = shape;
        if (shape == null) {
            return VoxelShapes.empty();
        } else {
            return shape;
        }
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileLogisticalManager();
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }


    @Override
    public void onPlace(BlockState newState, World level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(newState, level, pos, oldState, isMoving);
        if (level.isClientSide) {
            return;
        }
        TileLogisticalManager tile = (TileLogisticalManager) level.getBlockEntity(pos);
        if (tile == null) {
            return;
        }
        for (Direction dir : Direction.values()) {
            if (TileLogisticalManager.isQuarry(pos.relative(dir), level)) {
                tile.writeConnection(dir, EnumConnectType.WIRE);
            } else if (TileLogisticalManager.isValidInventory(pos.relative(dir), level, dir.getOpposite())) {
                tile.writeConnection(dir, EnumConnectType.INVENTORY);
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, world, currentPos, facingPos);
        TileLogisticalManager tile = (TileLogisticalManager) world.getBlockEntity(currentPos);
        EnumConnectType connection = EnumConnectType.NONE;
        if (tile == null) {
            return stateIn;
        }
        if (TileLogisticalManager.isQuarry(facingPos, world)) {
            connection = EnumConnectType.WIRE;
        } else if (TileLogisticalManager.isValidInventory(facingPos, world, facing.getOpposite())) {
            connection = EnumConnectType.INVENTORY;
        }
        tile.writeConnection(facing, connection);
        return stateIn;
    }

    @Override
    public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(state, world, pos, neighbor);
        if (world.isClientSide()) {
            return;
        }
        TileLogisticalManager tile = (TileLogisticalManager) world.getBlockEntity(pos);
        EnumConnectType connection = EnumConnectType.NONE;
        if (tile == null) {
            return;
        }
        Direction facing = BlockEntityUtils.directionFromPos(pos, neighbor);
        if (TileLogisticalManager.isQuarry(neighbor, world)) {
            connection = EnumConnectType.WIRE;
        } else if (TileLogisticalManager.isValidInventory(neighbor, world, facing.getOpposite())) {
            connection = EnumConnectType.INVENTORY;
        }
        tile.writeConnection(facing, connection);
    }

}
